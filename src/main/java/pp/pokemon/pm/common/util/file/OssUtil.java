package pp.pokemon.pm.common.util.file;

import com.aliyun.oss.OSSClient;
import com.github.pagehelper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OssUtil {

    private static Logger logger= LoggerFactory.getLogger(OssUtil.class);
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
            suffix = fileName.substring(fileName.lastIndexOf("."));
        }
        return suffix;
    }

    public static void main(String[] args) {
        System.out.println(OssUtil.getSuffix("https://pp-pokemon.oss-cn-shenzhen.aliyuncs.com/%E7%99%BB%E5%93%A5.jpg"));
    }
}
