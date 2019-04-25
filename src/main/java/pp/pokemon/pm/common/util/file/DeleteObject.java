package pp.pokemon.pm.common.util.file;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pp.pokemon.pm.common.constant.RetException;

import java.util.List;

@Component
public class DeleteObject {

    private static Logger logger= LoggerFactory.getLogger(PutObject.class);

    @Autowired
    private AliyunOssClientFactory aliyunOssClientFactory;

    /**
     * 删除单个文件
     *
     * @param bucketName
     *            Bucket名称
     * @param key
     *            由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt
     *
     * @return 返回删除结果<br/>
     *         删除成功：返回null<br/>
     *         删除失败：抛出错误
     */
    public void deleteObject(String bucketName, String key){
        OSSClient client = aliyunOssClientFactory.getOssClient();
        try {
            client.deleteObject(bucketName, key);
        } catch (Exception e) {
            throw new RetException(e.getCause().getMessage(), e.getMessage());
        } finally {
            client.shutdown();
        }
    }


    /**
     * 删除多个文件
     *
     * @param bucketName
     *            Bucket名称
     * @param keys
     *            需要删除的文件
     * @param quiet
     *            返回模式。true表示简单模式，false表示详细模式。默认为详细模式。
     * //@param encodingType
     *            对返回的文件名称进行编码。编码类型目前仅支持url
     * @return 返回删除结果<br/>
     *         删除成功：返回空list<br/>
     *         删除失败：返回删除失败的名称list
     */
    public List<String> batchDeleteObject(String bucketName, List<String> keys, boolean quiet){
        OSSClient client = aliyunOssClientFactory.getOssClient();

        DeleteObjectsRequest request = new DeleteObjectsRequest(bucketName);
        request.setKeys(keys);
        request.setQuiet(quiet);

        DeleteObjectsResult result;
        try {
             result = client.deleteObjects(request);
        } catch (Exception e) {
            throw new RetException(e.getCause().getMessage(), e.getMessage());
        } finally {
            client.shutdown();
        }
        return result.getDeletedObjects();
    }
}
