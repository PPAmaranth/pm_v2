package pp.pokemon.pm.dao.mapper.pokemon;

import org.apache.ibatis.annotations.Param;
import pp.pokemon.pm.dao.entity.pokemon.Attachment;

import java.util.List;

public interface AttachmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Attachment record);

    int insertSelective(Attachment record);

    Attachment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Attachment record);

    int updateByPrimaryKey(Attachment record);

    List<Attachment> queryAllPublicAttachment();

    List<Attachment> queryPublicAttachmentByIds(@Param("ids") List<Integer> ids);
}