package pp.pokemon.pm.service.util.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import pp.pokemon.pm.common.constant.RetException;
import pp.pokemon.pm.common.enums.file.DownloadType;
import pp.pokemon.pm.common.message.FileMessage;
import pp.pokemon.pm.common.util.file.OssUtil;
import pp.pokemon.pm.common.util.file.PutObject;
import pp.pokemon.pm.dao.entity.pokemon.PkmAttachment;
import pp.pokemon.pm.dao.mapper.pokemon.PkmAttachmentMapper;
import pp.pokemon.pm.service.util.FileService;
import pp.pokemon.pm.web.vo.file.PublicDownloadReqVo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private PutObject putObject;

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
        OssUtil.suffixFilter(file.getOriginalFilename());

        // 上传文件至oss
        String url;
        try {
            // TODO: 目前无子目录+使用原始文件名, 待优化
            url = putObject.uploadByInputStream(publicBucketName,file.getOriginalFilename(),file.getInputStream());
        } catch (IOException e) {
            throw new RetException(FileMessage.INVALID_INPUT_STREAM_CODE, FileMessage.INVALID_INPUT_STREAM_MSG);
        }

        // 上传oss成功后将附件信息保存在attachment表中
        PkmAttachment attachment = new PkmAttachment();
        attachment.setFilePath(url);
        attachment.setFileName(file.getName());
        attachment.setOriName(file.getOriginalFilename());
        attachment.setSuffix(OssUtil.getSuffix(file.getOriginalFilename()));
        attachment.setType(DownloadType.PUBLIC.getType());
        attachment.setCreateDate(new Date());
        attachmentMapper.insert(attachment);
        return attachment;
    }

    /**
     *  oss文件流, 附件批量上传, 公有库
     */
    @Override
    public List<PkmAttachment> publicBatchUpload(HttpServletRequest request) {
        // 将文件流转为文件列表
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        if (CollectionUtils.isEmpty(files)) {
            throw new RetException(FileMessage.OSS_UPLOAD_FAILURE_CODE, FileMessage.OSS_UPLOAD_FAILURE_MSG);
        }

        List<PkmAttachment> attachments = new ArrayList<>();
        for (MultipartFile file : files) {
            // 后缀名校验
            OssUtil.suffixFilter(file.getOriginalFilename());
            // 上传至oss
            String url;
            try {
                url = putObject.uploadByInputStream(publicBucketName, file.getOriginalFilename(), file.getInputStream());
            } catch (IOException e) {
                throw new RetException(FileMessage.INVALID_INPUT_STREAM_CODE, FileMessage.INVALID_INPUT_STREAM_MSG);
            }

            // 上传oss成功后将附件信息保存在attachment表中
            PkmAttachment attachment = new PkmAttachment();
            attachment.setFilePath(url);
            attachment.setFileName(file.getName());
            attachment.setOriName(file.getOriginalFilename());
            attachment.setSuffix(OssUtil.getSuffix(file.getOriginalFilename()));
            attachment.setType(DownloadType.PUBLIC.getType());
            attachment.setCreateDate(new Date());
            attachmentMapper.insert(attachment);

            attachments.add(attachment);
        }
        return attachments;
    }

    /**
     *  公有下载
     */
    @Override
    public PkmAttachment publicDownload(PublicDownloadReqVo reqVo) {
        return Optional.ofNullable(attachmentMapper.selectByPrimaryKey(reqVo.getId()))
                .filter(pkmAttachment -> pkmAttachment.getType().equals(DownloadType.PUBLIC.getType()))
                .orElseThrow(() -> new RetException(FileMessage.INVALID_ATTACHMENT_CODE, FileMessage.INVALID_ATTACHMENT_MSG));
    }
}
