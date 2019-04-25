package pp.pokemon.pm.service.util;

import com.github.pagehelper.PageInfo;
import pp.pokemon.pm.dao.entity.pokemon.PkmAttachment;
import pp.pokemon.pm.web.vo.base.BaseReqWithPageVo;
import pp.pokemon.pm.web.vo.file.BatchFilesReqVo;
import pp.pokemon.pm.web.vo.file.FileReqVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FileService {
    PkmAttachment publicUpload(HttpServletRequest request);

    List<PkmAttachment> publicBatchUpload(HttpServletRequest request);

    PkmAttachment publicDownload(FileReqVo reqVo);

    PageInfo<PkmAttachment> publicFileList(BaseReqWithPageVo req);

    void publicFileDelete(FileReqVo reqVo);

    List<PkmAttachment> batchDeletePublicFiles(BatchFilesReqVo reqVo);
}
