package pp.pokemon.pm.common.util.file;

import com.aliyun.oss.OSSClient;
import com.github.pagehelper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pp.pokemon.pm.common.constant.RetException;
import pp.pokemon.pm.common.enums.file.HandbookKind;
import pp.pokemon.pm.common.enums.file.FileModule;
import pp.pokemon.pm.common.enums.file.HandbookType;
import pp.pokemon.pm.common.message.FileMessage;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

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

    /**
     * 获取文件名的后缀, 如001.jpg -> jpg
     *
     */
    public static String getSuffix(String fileName) {
        String suffix = "";
        if (StringUtil.isNotEmpty(fileName)) {
            suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return suffix;
    }


    /**
     * 校验文件名的后缀是否符合格式
     *
     */
    public static void suffixFilter(String fileName) {
        String fileSuffix = getSuffix(fileName);
        long count = Arrays.stream(uploadFileFormat.split(","))
                .filter(suffix -> suffix.equals(fileSuffix))
                .count();
        if (0 == count) {
            throw new RetException(FileMessage.INVALID_FILE_SUFFIX_CODE, FileMessage.INVALID_FILE_SUFFIX_MSG);
        }
    }

    /**
     * 对(module=1)精灵模块拼接类型名, 种类名和原始文件名
     * 如果传入错误的类型名, 种类名则抛出错误
     */
    public static String getHandBookKey(String type, String kind, String oriFilename) {
        StringBuilder sb = new StringBuilder();
        String module = FileModule.ILLUSTRATED_HANDBOOK.getPath();
        sb.append(module);

        if (StringUtil.isNotEmpty(type)) {
            Map<Integer, String> typeMap = HandbookType.getMap();
            String typeStr = Optional.ofNullable(typeMap.get(Integer.valueOf(type)))
                    .orElseThrow(() -> new RetException(FileMessage.INVALID_HANDBOOK_TYPE_CODE, FileMessage.INVALID_HANDBOOK_TYPE_MSG));
            sb.append(typeStr);

            if (StringUtil.isNotEmpty(kind)) {
                Map<Integer, String> kindMap = HandbookKind.getMap();
                String kindStr = Optional.ofNullable(kindMap.get(Integer.valueOf(kind)))
                        .orElseThrow(() -> new RetException(FileMessage.INVALID_HANDBOOK_KIND_CODE, FileMessage.INVALID_HANDBOOK_KIND_MSG));
                sb.append(kindStr);
            }
        }
        sb.append(oriFilename);
        return sb.toString();
    }


}
