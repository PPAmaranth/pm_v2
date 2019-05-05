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
import pp.pokemon.pm.common.enums.file.FileModule;
import pp.pokemon.pm.common.message.FileMessage;
import pp.pokemon.pm.common.message.PokemonMessage;
import pp.pokemon.pm.common.util.file.DeleteObject;
import pp.pokemon.pm.common.util.file.OssUtil;
import pp.pokemon.pm.common.util.file.PutObject;
import pp.pokemon.pm.dao.entity.pokemon.Attachment;
import pp.pokemon.pm.dao.entity.pokemon.Pokemon;
import pp.pokemon.pm.dao.entity.pokemon.PokemonAttachmentRel;
import pp.pokemon.pm.dao.mapper.pokemon.AttachmentMapper;
import pp.pokemon.pm.dao.mapper.pokemon.PokemonAttachmentRelMapper;
import pp.pokemon.pm.dao.mapper.pokemon.PokemonMapper;
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
    private AttachmentMapper attachmentMapper;

    @Autowired
    private PokemonMapper pokemonMapper;

    @Autowired
    private PokemonAttachmentRelMapper pokemonAttachmentRelMapper;

    @Value("${aliyun.oss.publicBucket}")
    private String publicBucketName;

    /**
     *  oss文件流, 附件单个上传, 公有库
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Attachment publicUpload(HttpServletRequest request) {

        // 将文件流转为文件
        MultipartFile file = Optional.ofNullable(
                    ((MultipartHttpServletRequest) request).getFile("file") )
                .orElseThrow(()->new RetException(FileMessage.OSS_UPLOAD_FAILURE_CODE, FileMessage.OSS_UPLOAD_FAILURE_MSG));

        // 后缀名过滤
        Attachment attachment = uploadAndInsertFile(request, file, DownloadType.PUBLIC.getType());

        return attachment;
    }

    /**
     *  oss文件流, 附件批量上传, 公有库
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Attachment> publicBatchUpload(HttpServletRequest request) {
        // 将文件流转为文件列表
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        if (CollectionUtils.isEmpty(files)) {
            throw new RetException(FileMessage.OSS_UPLOAD_FAILURE_CODE, FileMessage.OSS_UPLOAD_FAILURE_MSG);
        }

        List<Attachment> attachments = new ArrayList<>();
        for (MultipartFile file : files) {
            Attachment attachment = uploadAndInsertFile(request, file, DownloadType.PUBLIC.getType());
            attachments.add(attachment);
        }
        return attachments;
    }

    /**
     *  校验后缀名是否正确, 根据不同的module进行上传
     */
    private Attachment uploadAndInsertFile(HttpServletRequest request, MultipartFile file, Integer downloadType) {
        // 后缀名过滤
        OssUtil.suffixFilter(file.getOriginalFilename());

        String module = request.getParameter("module");
        Attachment attachment = null;
        // 上传图鉴
        if (null != module
                && module.equals(FileModule.ILLUSTRATED_HANDBOOK.getModule().toString())) {
            attachment = uploadPokemonAttachment(request, file, downloadType);
        }

        return attachment;
    }

    /**
     *  (module=1) -> 精灵图片的上传
     */
    private Attachment uploadPokemonAttachment(HttpServletRequest request, MultipartFile file, Integer downloadType) {
        // 验参pokemon_id是否存在
        String pokemonId = request.getParameter("id");
        Pokemon pokemon = getPokemon(Integer.valueOf(pokemonId));

        String type = request.getParameter("type");
        String kind = request.getParameter("kind");
        String key = OssUtil.getHandBookKey(type, kind, file.getOriginalFilename());

        // 上传至oss
        String url = uploadStreamToOss(file, key);
        // 插入attachment表
        Attachment attachment = insertAttachment(file, key, url, downloadType);

        // 插入pokemonAttachmentRel表
        PokemonAttachmentRel rel = new PokemonAttachmentRel();
        rel.setPokemonId(pokemon.getId());
        rel.setAttachmentId(attachment.getId());
        if (StringUtil.isNotEmpty(type)) {
            rel.setType(Integer.valueOf(type));
            
            if (StringUtil.isNotEmpty(kind)) {
                rel.setKind(Integer.valueOf(kind));
            }
        }
        pokemonAttachmentRelMapper.insert(rel);

        return attachment;
    }

    /**
     *  插入到附件表
     */
    private Attachment insertAttachment(MultipartFile file, String key, String url, Integer downloadType) {
        // 上传oss成功后将附件信息保存在attachment表中
        Attachment attachment = new Attachment();
        attachment.setFileUri(url);
        attachment.setFilePath(key.replaceAll(file.getOriginalFilename(), ""));
        attachment.setFileName(key);
        attachment.setOriName(file.getOriginalFilename());
        attachment.setSuffix(OssUtil.getSuffix(file.getOriginalFilename()));
        attachment.setDownloadType(downloadType);
        attachment.setCreateDate(new Date());
        attachmentMapper.insert(attachment);

        return attachment;
    }


    /**
     *  新增将文件上传至oss, 使用putObject
     */
    private String uploadStreamToOss(MultipartFile file, String key) {
        // 上传文件至oss
        String url;
        try {
            url = putObject.uploadByInputStream(publicBucketName, key, file.getInputStream());
        } catch (IOException e) {
            throw new RetException(FileMessage.INVALID_INPUT_STREAM_CODE, FileMessage.INVALID_INPUT_STREAM_MSG);
        }
        return url;
    }

    /**
     *  公有附件下载
     */
    @Override
    public Attachment publicDownload(FileReqVo reqVo) {
        return getPubAttachment(reqVo.getId());
    }

    /**
     *  公有附件列表
     */
    @Override
    public PageInfo<Attachment> publicFileList(BaseReqWithPageVo req) {
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        List<Attachment> list = attachmentMapper.queryAllPublicAttachment();
        return new PageInfo<>(list);
    }

    /**
     *  单个公有附件删除
     */
    @Override
    public void publicFileDelete(FileReqVo reqVo) {
        // 验参: 附件存在
        Attachment attachment = getPubAttachment(reqVo.getId());

        // 删除oss上对应的文件
        deleteObject.deleteObject(publicBucketName, attachment.getOriName());

        // 删除数据库中对应的数据
        attachmentMapper.deleteByPrimaryKey(attachment.getId());
    }

    /**
     *  批量公有附件删除
     */
    @Override
    public List<Attachment> batchDeletePublicFiles(BatchFilesReqVo reqVo) {

        // 根据入参获得所有文件
        List<Attachment> attachments = attachmentMapper.queryPublicAttachmentByIds(reqVo.getList());
        List<String> keys = attachments.stream()
                .map(Attachment::getOriName)
                .collect(Collectors.toList());

        // oss批量删除, 返回未被删除的文件名列表
        List<String> undeletedKeys = deleteObject.batchDeleteObject(publicBucketName, keys, true);

        // 根据文件名返回所有未被删除的文件
        List<Attachment> undeletedAttachments = attachments.stream()
                .filter(attachment -> undeletedKeys.contains(attachment.getOriName()))
                .collect(Collectors.toList());
        return undeletedAttachments;
    }

    /**
     *  获得公有下载附件, 如果附件不存在或为私有附件则抛出错误
     */
    private Attachment getPubAttachment(Integer id) {
        return Optional.ofNullable(attachmentMapper.selectByPrimaryKey(id))
                .filter(Attachment -> Attachment.getDownloadType().equals(DownloadType.PUBLIC.getType()))
                .orElseThrow(() -> new RetException(FileMessage.INVALID_ATTACHMENT_CODE, FileMessage.INVALID_ATTACHMENT_MSG));
    }

    /**
     *  获得精灵主信息
     */
    private Pokemon getPokemon(Integer id) {
        return Optional.ofNullable(pokemonMapper.selectByPrimaryKey(id))
                .orElseThrow(() -> new RetException(PokemonMessage.INVALID_POKEMON_CODE, PokemonMessage.INVALID_POKEMON_MSG));
    }
}
