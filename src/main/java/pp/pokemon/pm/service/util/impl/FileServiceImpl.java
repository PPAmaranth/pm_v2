package pp.pokemon.pm.service.util.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import pp.pokemon.pm.common.constant.RetException;
import pp.pokemon.pm.common.enums.file.DownloadType;
import pp.pokemon.pm.common.message.FileMessage;
import pp.pokemon.pm.common.util.file.DeleteObject;
import pp.pokemon.pm.common.util.file.OssUtil;
import pp.pokemon.pm.common.util.file.PutObject;
import pp.pokemon.pm.dao.entity.pokemon.PkmAttachment;
import pp.pokemon.pm.dao.mapper.pokemon.PkmAttachmentMapper;
import pp.pokemon.pm.service.util.FileService;
import pp.pokemon.pm.web.vo.base.BaseReqWithPageVo;
import pp.pokemon.pm.web.vo.file.BatchFilesReqVo;
import pp.pokemon.pm.web.vo.file.FileReqVo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private PutObject putObject;

    @Autowired
    private DeleteObject deleteObject;

    @Autowired
    private PkmAttachmentMapper attachmentMapper;

    @Value("${aliyun.oss.publicBucket}")
    private String publicBucketName;

    /**
     *  oss文件流, 附件单个上传, 公有库
     */
    @Override
    public PkmAttachment publicUpload(HttpServletRequest request) {

        // 将文件流转为文件
        MultipartFile file = Optional.ofNullable(
                    ((MultipartHttpServletRequest) request).getFile("file") )
                .orElseThrow(()->new RetException(FileMessage.OSS_UPLOAD_FAILURE_CODE, FileMessage.OSS_UPLOAD_FAILURE_MSG));

        // 后缀名过滤
        PkmAttachment attachment = uploadAndInsertFile(request, file);

        return attachment;
    }

    /**
     *  oss文件流, 附件批量上传, 公有库
     */
    @Override
    @Transactional
    public List<PkmAttachment> publicBatchUpload(HttpServletRequest request) {
        // 将文件流转为文件列表
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        if (CollectionUtils.isEmpty(files)) {
            throw new RetException(FileMessage.OSS_UPLOAD_FAILURE_CODE, FileMessage.OSS_UPLOAD_FAILURE_MSG);
        }

        List<PkmAttachment> attachments = new ArrayList<>();
        for (MultipartFile file : files) {
            PkmAttachment attachment = uploadAndInsertFile(request, file);
            attachments.add(attachment);
        }
        return attachments;
    }

    private PkmAttachment uploadAndInsertFile(HttpServletRequest request, MultipartFile file) {
        // 后缀名过滤
        OssUtil.suffixFilter(file.getOriginalFilename());

        // 获取filePath filePath以"/"结尾, filePath传入"test/", 原始文件001.jpg最终路径为"test/001.jpg"
        String module = request.getParameter("module");
        String type = request.getParameter("type");
        String key = OssUtil.getKey(module, type, file.getOriginalFilename());

        // 上传文件至oss
        String url;
        try {
            url = putObject.uploadByInputStream(publicBucketName, key, file.getInputStream());
        } catch (IOException e) {
            throw new RetException(FileMessage.INVALID_INPUT_STREAM_CODE, FileMessage.INVALID_INPUT_STREAM_MSG);
        }

        // 上传oss成功后将附件信息保存在attachment表中
        PkmAttachment attachment = new PkmAttachment();
        attachment.setFileUri(url);
        attachment.setFilePath(key.replaceAll(file.getOriginalFilename(), ""));
        attachment.setFileName(key);
        attachment.setOriName(file.getOriginalFilename());
        attachment.setSuffix(OssUtil.getSuffix(file.getOriginalFilename()));
        attachment.setDownloadType(DownloadType.PUBLIC.getType());
        if (StringUtil.isNotEmpty(module)) {
            attachment.setModule(Integer.valueOf(module));
        }
        if (StringUtil.isNotEmpty(type)) {
            attachment.setType(Integer.valueOf(type));
        }
        attachment.setCreateDate(new Date());
        attachmentMapper.insert(attachment);
        return attachment;
    }

    /**
     *  公有下载
     */
    @Override
    public PkmAttachment publicDownload(FileReqVo reqVo) {
        return getPubAttachment(reqVo.getId());
    }

    /**
     *  公有附件列表
     */
    @Override
    public PageInfo<PkmAttachment> publicFileList(BaseReqWithPageVo req) {
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        List<PkmAttachment> list = attachmentMapper.queryAllPublicAttachment();
        return new PageInfo<>(list);
    }

    /**
     *  单个公有附件删除
     */
    @Override
    public void publicFileDelete(FileReqVo reqVo) {
        // 验参: 附件存在
        PkmAttachment attachment = getPubAttachment(reqVo.getId());

        // 删除oss上对应的文件
        deleteObject.deleteObject(publicBucketName, attachment.getOriName());

        // 删除数据库中对应的数据
        attachmentMapper.deleteByPrimaryKey(attachment.getId());
    }

    /**
     *  批量公有附件删除
     */
    @Override
    public List<PkmAttachment> batchDeletePublicFiles(BatchFilesReqVo reqVo) {

        // 根据入参获得所有文件
        List<PkmAttachment> attachments = attachmentMapper.queryPublicAttachmentByIds(reqVo.getList());
        List<String> keys = attachments.stream()
                .map(PkmAttachment::getOriName)
                .collect(Collectors.toList());

        // oss批量删除, 返回未被删除的文件名列表
        List<String> undeletedKeys = deleteObject.batchDeleteObject(publicBucketName, keys, true);

        // 根据文件名返回所有未被删除的文件
        List<PkmAttachment> undeletedAttachments = attachments.stream()
                .filter(attachment -> undeletedKeys.contains(attachment.getOriName()))
                .collect(Collectors.toList());
        return undeletedAttachments;
    }

    private PkmAttachment getPubAttachment(Integer id) {
        return Optional.ofNullable(attachmentMapper.selectByPrimaryKey(id))
                .filter(pkmAttachment -> pkmAttachment.getType().equals(DownloadType.PUBLIC.getType()))
                .orElseThrow(() -> new RetException(FileMessage.INVALID_ATTACHMENT_CODE, FileMessage.INVALID_ATTACHMENT_MSG));
    }
}
