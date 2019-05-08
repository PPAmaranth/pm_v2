package pp.pokemon.pm.service.property.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pp.pokemon.pm.common.constant.RetException;
import pp.pokemon.pm.common.message.PropertyMessage;
import pp.pokemon.pm.dao.entity.pokemon.Property;
import pp.pokemon.pm.dao.mapper.pokemon.PropertyMapper;
import pp.pokemon.pm.dao.vo.property.PropertyListReqVo;
import pp.pokemon.pm.service.property.PropertyService;
import pp.pokemon.pm.web.vo.property.BatchAddPropertyReqVo;
import pp.pokemon.pm.web.vo.property.PropertyDetailReqVo;
import pp.pokemon.pm.web.vo.property.PropertyDetailRespVo;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyMapper propertyMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchAddProperty(List<BatchAddPropertyReqVo> reqVos) {
        reqVos.stream().forEach(reqVo -> {
            Property property = new Property();
            BeanUtils.copyProperties(reqVo, property);
            property.setEnName(reqVo.getEn_name());
            propertyMapper.insert(property);
        });
    }

    @Override
    public List<Property> list(PropertyListReqVo reqVo) {
        return propertyMapper.selectByParam(reqVo);
    }

    @Override
    public PropertyDetailRespVo detail(PropertyDetailReqVo reqVo) {
        Property property = getProperty(reqVo.getId());
        PropertyDetailRespVo respVo = new PropertyDetailRespVo();
        BeanUtils.copyProperties(property,respVo);
        return respVo;
    }

    /**
     * 根据属性id获得属性
     */
    private Property getProperty(Integer id) {
        return Optional.ofNullable(propertyMapper.selectByPrimaryKey(id))
                .orElseThrow(() -> new RetException(PropertyMessage.INVALID_PROPERTY_CODE, PropertyMessage.INVALID_PROPERTY_MSG));
    }

}
