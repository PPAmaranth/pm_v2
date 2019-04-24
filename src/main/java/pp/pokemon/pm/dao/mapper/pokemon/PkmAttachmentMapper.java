package pp.pokemon.pm.dao.mapper.pokemon;

import pp.pokemon.pm.dao.entity.pokemon.PkmAttachment;

public interface PkmAttachmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PkmAttachment record);

    int insertSelective(PkmAttachment record);

    PkmAttachment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PkmAttachment record);

    int updateByPrimaryKey(PkmAttachment record);
}