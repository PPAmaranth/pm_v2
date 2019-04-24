package pp.pokemon.pm.service.util;

import pp.pokemon.pm.dao.entity.pokemon.PkmAttachment;
import pp.pokemon.pm.web.vo.file.PublicDownloadReqVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FileService {
    PkmAttachment publicUpload(HttpServletRequest request);

    List<PkmAttachment> publicBatchUpload(HttpServletRequest request);

    PkmAttachment publicDownload(PublicDownloadReqVo reqVo);
}
