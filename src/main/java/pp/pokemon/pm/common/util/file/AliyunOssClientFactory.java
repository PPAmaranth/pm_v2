package pp.pokemon.pm.common.util.file;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AliyunOssClientFactory {

    //private static AliOssClientFactory instance;
    private ClientConfiguration clientConfiguration;

    // endpoint是访问OSS的域名。如果您已经在OSS的控制台上 创建了Bucket，请在控制台上查看域名。
    // 如果您还没有创建Bucket，endpoint选择请参看文档中心的“开发人员指南 > 基本概念 > 访问域名”，
    // 链接地址是：https://help.aliyun.com/document_detail/oss/user_guide/oss_concept/endpoint.html?spm=5176.docoss/user_guide/endpoint_region
    // endpoint的格式形如“http://oss-cn-hangzhou.aliyuncs.com/”，注意http://后不带bucket名称，
    // 比如“http://bucket-name.oss-cn-hangzhou.aliyuncs.com”，是错误的endpoint，请去掉其中的“bucket-name”。
    @Value("${aliyun.oss.endpoint}")
    private String endPoint;

    // accessKeyId和accessKeySecret是OSS的访问密钥，您可以在控制台上创建和查看，
    // 创建和查看访问密钥的链接地址是：https://ak-console.aliyun.com/#/。
    // 注意：accessKeyId和accessKeySecret前后都没有空格，从控制台复制时请检查并去除多余的空格。
    @Value("${aliyun.oss.accesskeyid}")
    private String accessKeyId;

    @Value("${aliyun.oss.accesskeysecret}")
    private String accessKeySecret;

    private AliyunOssClientFactory(){
        this.initialize();
    }

    /**
     * 获取客户端对象
     *      要连接的OSS服务器信息
     * @return 返回客户端对象
     */
    public OSSClient getOssClient(){
        return new OSSClient(endPoint, accessKeyId, accessKeySecret, this.clientConfiguration);
    }

    private void initialize() {
        this.clientConfiguration = new ClientConfiguration();
        // 允许打开的最大HTTP连接数。默认为1024
        clientConfiguration.setMaxConnections(100);
        // 建立连接的超时时间（单位：毫秒）。默认为50000毫秒
        clientConfiguration.setConnectionTimeout(5000);
        // 请求失败后最大的重试次数。默认3次
        clientConfiguration.setMaxErrorRetry(5);
        // Socket层传输数据的超时时间（单位：毫秒）。默认为50000毫秒
        clientConfiguration.setSocketTimeout(5000);
    }


}
