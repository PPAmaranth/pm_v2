package pp.pokemon.pm.service.util;

import com.github.pagehelper.PageInfo;
import pp.pokemon.pm.dao.entity.pokemon.Attachment;
import pp.pokemon.pm.web.vo.base.BaseReqWithPageVo;
import pp.pokemon.pm.web.vo.file.BatchFilesReqVo;
import pp.pokemon.pm.web.vo.file.FileReqVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FileService {
    Attachment publicUpload(HttpServletRequest request);

    List<Attachment> publicBatchUpload(HttpServletRequest request);

    Attachment publicDownload(FileReqVo reqVo);

    PageInfo<Attachment> publicFileList(BaseReqWithPageVo req);

    void publicFileDelete(FileReqVo reqVo);

    List<Attachment> batchDeletePublicFiles(BatchFilesReqVo reqVo);
}
