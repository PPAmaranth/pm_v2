package pp.pokemon.pm.dao.mapper.pokemon;

import org.apache.ibatis.annotations.Param;
import pp.pokemon.pm.dao.entity.pokemon.PkmAttachment;

import java.util.List;

public interface PkmAttachmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PkmAttachment record);

    int insertSelective(PkmAttachment record);

    PkmAttachment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PkmAttachment record);

    int updateByPrimaryKey(PkmAttachment record);

    List<PkmAttachment> queryAllPublicAttachment();

    List<PkmAttachment> queryPublicAttachmentByIds(@Param("ids")List<Integer> ids);
}