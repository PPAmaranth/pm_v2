package pp.pokemon.pm.common.util.file;

import com.aliyun.oss.OSSClient;
import com.github.pagehelper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import pp.pokemon.pm.common.constant.RetException;
import pp.pokemon.pm.common.message.FileMessage;

import java.util.Arrays;
import java.util.List;

public class OssUtil {

    private static Logger logger= LoggerFactory.getLogger(OssUtil.class);

    private static String uploadFileFormat = "ppt,pptx,pdf,xls,xlsx,doc,docx,txt,rtf,jpg,jpeg,png,gif,tiff,bmp,zip,rar,mp3,7z";

    /**
     * 获取对应的访问地址(url)
     *
     * @param client
     *            OSS客户端对象
     * @param bucketName
     *            Bucket名称
     * @param key
     *            对象在OSS服务器上存储的路径，可包括子文件夹的路径；例如item/item_100101.jpg
     * @return 返回对象的访问地址
     */
    public static String getUrl(OSSClient client, String bucketName, String key){
        StringBuilder sb = new StringBuilder();
        sb.append("https://");
        sb.append(bucketName);
        sb.append(".");
        sb.append(client.getEndpoint().toString().replaceAll("http://",""));
        sb.append("/");
        sb.append(key);
        return sb.toString();
    }

    public static String getSuffix(String fileName) {
        String suffix = "";
        if (StringUtil.isNotEmpty(fileName)) {
            suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return suffix;
    }


    public static void suffixFilter(String fileName) {
        String fileSuffix = getSuffix(fileName);
        long count = Arrays.stream(uploadFileFormat.split(","))
                .filter(suffix -> suffix.equals(fileSuffix))
                .count();
        if (0 == count) {
            throw new RetException(FileMessage.INVALID_ATTACHMENT_SUFFIX_CODE, FileMessage.INVALID_ATTACHMENT_SUFFIX_MSG);
        }
    }
}
