package pp.pokemon.pm.web.controller.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pp.pokemon.pm.common.util.BeanValidators;
import pp.pokemon.pm.service.util.FileService;
import pp.pokemon.pm.web.controller.BaseController;
import pp.pokemon.pm.web.vo.base.DefaultApiResult;
import pp.pokemon.pm.web.vo.file.PublicDownloadReqVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/publicUpload", method = {RequestMethod.POST})
    public DefaultApiResult publicUpload(HttpServletRequest request) {
        return success(fileService.publicUpload(request));
    }

    @RequestMapping(value = "/publicBatchUpload", method = {RequestMethod.POST})
    public DefaultApiResult publicBatchUpload(HttpServletRequest request) {
        return success(fileService.publicBatchUpload(request));
    }

    @RequestMapping(value = "/publicDownload", method = {RequestMethod.POST})
    public DefaultApiResult publicDownload(@RequestBody PublicDownloadReqVo reqVo) {
        BeanValidators.validateWithParameterException(validator, reqVo);
        return success(fileService.publicDownload(reqVo));
    }

}
