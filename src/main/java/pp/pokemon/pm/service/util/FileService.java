package pp.pokemon.pm.service.util;

import pp.pokemon.pm.dao.entity.pokemon.PkmAttachment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FileService {
    PkmAttachment publicUpload(HttpServletRequest request, HttpServletResponse response);
}
