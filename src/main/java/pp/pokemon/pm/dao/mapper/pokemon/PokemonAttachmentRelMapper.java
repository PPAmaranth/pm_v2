package pp.pokemon.pm.dao.mapper.pokemon;

import pp.pokemon.pm.dao.entity.pokemon.PokemonAttachmentRel;
import pp.pokemon.pm.dao.vo.pokemon.PokemonAttachmentReqVo;

import java.util.List;

public interface PokemonAttachmentRelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PokemonAttachmentRel record);

    int insertSelective(PokemonAttachmentRel record);

    PokemonAttachmentRel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PokemonAttachmentRel record);

    int updateByPrimaryKey(PokemonAttachmentRel record);

    List<PokemonAttachmentRel> selectByParam(PokemonAttachmentReqVo reqVo);
}