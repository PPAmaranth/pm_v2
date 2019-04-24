package pp.pokemon.pm.service.util.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import pp.pokemon.pm.common.constant.RetException;
import pp.pokemon.pm.common.message.FileMessage;
import pp.pokemon.pm.common.util.file.OssUtil;
import pp.pokemon.pm.common.util.file.PutObject;
import pp.pokemon.pm.dao.entity.pokemon.PkmAttachment;
import pp.pokemon.pm.dao.mapper.pokemon.PkmAttachmentMapper;
import pp.pokemon.pm.service.util.FileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private PutObject putObject;

    @Autowired
    private PkmAttachmentMapper attachmentMapper;

    @Value("${aliyun.oss.publicBucket}")
    private String publicBucketName;

    @Override
    public PkmAttachment publicUpload(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        //这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
        response.setCharacterEncoding("UTF-8");
        PkmAttachment attachment = new PkmAttachment();
        // TODO: 后缀名过滤, 待优化
        // final String[] allowFileSuffixs = uploadFileFormat.split(",");
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        String url = "";
        if (null == file) {
            throw new RetException(FileMessage.OSS_UPLOAD_FAILURE_CODE, FileMessage.OSS_UPLOAD_FAILURE_MSG);
        }
        try {
            // TODO: 目前无子目录+使用原始文件名, 待优化
            url = putObject.uploadByInputStream(publicBucketName,file.getOriginalFilename(),file.getInputStream());
        } catch (IOException e) {
            throw new RetException(FileMessage.INVALID_INPUT_STREAM_CODE, FileMessage.INVALID_INPUT_STREAM_MSG);
        }
        attachment.setFilePath(url);
        attachment.setFileName(file.getName());
        attachment.setOriName(file.getOriginalFilename());
        attachment.setSuffix(OssUtil.getSuffix(file.getOriginalFilename()));
        attachment.setCreateDate(new Date());
        attachmentMapper.insert(attachment);
        return attachment;
    }
}
