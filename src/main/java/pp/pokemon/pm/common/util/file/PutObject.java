package pp.pokemon.pm.common.util.file;

import com.aliyun.oss.OSSClient;
import com.github.pagehelper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pp.pokemon.pm.common.constant.RetException;
import pp.pokemon.pm.common.message.FileMessage;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Component
public class PutObject {

    private static Logger logger= LoggerFactory.getLogger(PutObject.class);

    @Autowired
    private AliyunOssClientFactory aliyunOssClientFactory;

    /**
     * 上传字符串
     *
     * @param bucketName
     *            Bucket名称
     * @param key
     *            由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt
     * @param content
     *            待上传的字符串
     * @return 返回上传结果<br/>
     *         上传成功：返回对象的访问地址(url)<br/>
     *         上传失败：返回null
     */
    public String uploadByString(String bucketName, String key, String content){

        OSSClient ossClient = aliyunOssClientFactory.getOssClient();

        try {
            ossClient.putObject(bucketName, key, new ByteArrayInputStream(content.getBytes()));
        } catch (Exception e1) {
            logger.error(e1.getMessage());
        } finally {
            try {
                ossClient.shutdown();
            } catch (Exception e2) {
                logger.error(e2.getMessage());
            }
        }

        String url = OssUtil.getUrl(ossClient, bucketName, key);
        if (StringUtil.isEmpty(url)) {
            throw new RetException(FileMessage.OSS_UPLOAD_FAILURE_CODE, FileMessage.OSS_UPLOAD_FAILURE_MSG);
        }
        return url;
    }

    /**
     * 上传流对象
     *
     * @param bucketName
     *            Bucket名称
     * @param key
     *            由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt
     * @param inputStream
     *            待上传的流对象
     * @return 返回上传结果<br/>
     *         上传成功：返回对象的访问地址(url)<br/>
     *         上传失败：返回null
     */
    public String uploadByInputStream(String bucketName, String key, InputStream inputStream){

        OSSClient ossClient = aliyunOssClientFactory.getOssClient();

        try {
            ossClient.putObject(bucketName, key, inputStream);
        } catch (Exception e1) {
            logger.error(e1.getMessage());
            throw new RetException(FileMessage.OSS_UPLOAD_FAILURE_CODE, FileMessage.OSS_UPLOAD_FAILURE_MSG);
        } finally {
            try {
                ossClient.shutdown();
            } catch (Exception e2) {
                logger.error(e2.getMessage());
            }
        }

        String url = OssUtil.getUrl(ossClient, bucketName, key);
        if (StringUtil.isEmpty(url)) {
            throw new RetException(FileMessage.OSS_UPLOAD_FAILURE_CODE, FileMessage.OSS_UPLOAD_FAILURE_MSG);
        }
        return url;
    }
}
