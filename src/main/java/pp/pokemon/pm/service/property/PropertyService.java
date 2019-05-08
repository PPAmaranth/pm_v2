package pp.pokemon.pm.service.property;

import pp.pokemon.pm.dao.entity.pokemon.Property;
import pp.pokemon.pm.dao.vo.property.PropertyListReqVo;
import pp.pokemon.pm.web.vo.property.BatchAddPropertyReqVo;
import pp.pokemon.pm.web.vo.property.PropertyDetailReqVo;
import pp.pokemon.pm.web.vo.property.PropertyDetailRespVo;

import java.util.List;

public interface PropertyService {

    void batchAddProperty(List<BatchAddPropertyReqVo> reqVos);

    List<Property> list(PropertyListReqVo reqVo);

    PropertyDetailRespVo detail(PropertyDetailReqVo reqVo);
}
