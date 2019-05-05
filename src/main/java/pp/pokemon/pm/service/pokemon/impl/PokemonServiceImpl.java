package pp.pokemon.pm.service.pokemon.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import pp.pokemon.pm.common.constant.RetException;
import pp.pokemon.pm.common.enums.file.PokemonKind;
import pp.pokemon.pm.common.enums.file.PokemonType;
import pp.pokemon.pm.common.message.PokemonMessage;
import pp.pokemon.pm.dao.entity.pokemon.*;
import pp.pokemon.pm.dao.mapper.pokemon.*;
import pp.pokemon.pm.dao.vo.pokemon.InsertPokemonReqVo;
import pp.pokemon.pm.dao.vo.pokemon.MachineSkillVo;
import pp.pokemon.pm.service.pokemon.PokemonService;
import pp.pokemon.pm.dao.vo.file.BatchInsertPokemonVo;
import pp.pokemon.pm.dao.vo.pokemon.QueryAllPokemonReqVo;
import pp.pokemon.pm.dao.vo.pokemon.PokemonAttachmentReqVo;
import pp.pokemon.pm.web.vo.pokemon.PokemonAttachmentRespVo;
import pp.pokemon.pm.web.vo.pokemon.QueryPokemonRespVo;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {

    @Autowired
    private PokemonMapper pokemonMapper;

    @Autowired
    private EvolvePokemonRelMapper evolvePokemonRelMapper;

    @Autowired
    private EvolveSkillRelMapper evolveSkillRelMapper;

    @Autowired
    private MachineSkillRelMapper machineSkillRelMapper;

    @Autowired
    private PokemonAttachmentRelMapper attachmentRelMapper;

    @Autowired
    private AttachmentMapper attachmentMapper;

    /**
     * 获取所有精灵
     */
    @Override
    public PageInfo<QueryPokemonRespVo> pokemonList(QueryAllPokemonReqVo reqVo) {
        PageHelper.startPage(reqVo.getPageNum(), reqVo.getPageSize());
        List<Pokemon> pokemons = pokemonMapper.queryByParam(reqVo);
        PageInfo<Pokemon> pokemonPageInfo = new PageInfo<>(pokemons);

        List<QueryPokemonRespVo> respVos = pokemons.stream()
                .map(pokemon -> {
                    QueryPokemonRespVo respVo = new QueryPokemonRespVo();
                    BeanUtils.copyProperties(pokemon, respVo);
                    // 精灵主图
                    PokemonAttachmentReqVo pokemonAttachmentReqVo = new PokemonAttachmentReqVo();
                    pokemonAttachmentReqVo.setId(pokemon.getId());
                    pokemonAttachmentReqVo.setType(PokemonType.LIST.getType());
                    PokemonAttachmentRel rel = attachmentRelMapper.selectByParam(pokemonAttachmentReqVo)
                            .stream()
                            .findAny()
                            .orElseGet(() -> new PokemonAttachmentRel());

                    String imgUrl = Optional.ofNullable(attachmentMapper.selectByPrimaryKey(rel.getAttachmentId()))
                            .map(Attachment::getFileUri)
                            .orElse("");
                    respVo.setImgUrl(imgUrl);
                    return respVo;
                }).collect(Collectors.toList());

        PageInfo<QueryPokemonRespVo> pageInfo = new PageInfo<>();
        BeanUtils.copyProperties(pokemonPageInfo, pageInfo);
        pageInfo.setList(respVos);
        return pageInfo;
    }

    @Override
    public void batchInsertPokemon(List<BatchInsertPokemonVo> pokemonVos) {
        if(!CollectionUtils.isEmpty(pokemonVos)) {
            pokemonVos.stream().forEach(pokemonVo -> {
                Pokemon pokemon = new Pokemon();
                BeanUtils.copyProperties(pokemonVo, pokemon);
                // 特殊攻击&防御
                pokemon.setsAttack(pokemonVo.getS_attack());
                pokemon.setsDefense(pokemonVo.getS_defense());
                pokemon.setEthnicValue(pokemonVo.getEthnic_value());
                // 属性
                if (!CollectionUtils.isEmpty(pokemonVo.getProperty())) {
                    pokemon.setPropertyOne(pokemonVo.getProperty().get(0).getId());
                    if (pokemonVo.getProperty().size() > 1) {
                        pokemon.setPropertyTwo(pokemonVo.getProperty().get(1).getId());
                    }
                }
                pokemonMapper.insert(pokemon);
            });
        }
    }

    @Override
    public List<PokemonAttachmentRespVo> getPokemonAttachment(PokemonAttachmentReqVo reqVo) {
        Pokemon pokemon = getPokemon(reqVo.getId());

        List<PokemonAttachmentRel> rels = attachmentRelMapper.selectByParam(reqVo);
        List<PokemonAttachmentRespVo> respVos = rels.stream()
                .map(rel -> {
                    PokemonAttachmentRespVo respVo = new PokemonAttachmentRespVo();
                    Attachment attachment = attachmentMapper.selectByPrimaryKey(rel.getAttachmentId());

                    respVo.setId(pokemon.getId());
                    respVo.setAttachmentId(attachment.getId());
                    respVo.setFileUri(attachment.getFileUri());
                    respVo.setType(rel.getType());
                    respVo.setTypeName(getTypeName(rel.getType()));
                    respVo.setKind(rel.getKind());
                    respVo.setKindName(getKindName(rel.getKind()));
                    return respVo;
                }).collect(Collectors.toList());
        return respVos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertPokemon(InsertPokemonReqVo reqVo) {
        // 插入主表
        Pokemon pokemon = new Pokemon();
        BeanUtils.copyProperties(reqVo, pokemon);
        pokemonMapper.insert(pokemon);

        // 插入进化关系表
        if (null != reqVo.getEvolutionRelationship()) {
            EvolvePokemonRel rel = new EvolvePokemonRel();
            rel.setPokemonId(pokemon.getId());
            rel.setBeforeId(reqVo.getEvolutionRelationship().getBeforeId());
            rel.setCondition(reqVo.getEvolutionRelationship().getCondition());
            evolvePokemonRelMapper.insert(rel);
        }

        // 插入进化技能表
        if (!CollectionUtils.isEmpty(reqVo.getEvolutionSkillVos())) {
            reqVo.getEvolutionSkillVos().stream().forEach(skillVo -> {
                EvolveSkillRel rel = new EvolveSkillRel();
                rel.setPokemonId(pokemon.getId());
                rel.setSkillId(skillVo.getId());
                rel.setLevel(skillVo.getLevel());
                evolveSkillRelMapper.insert(rel);
            });
        }

        // 插入技能机技能关系表
        if (!CollectionUtils.isEmpty(reqVo.getMachineSkillVos())) {
            reqVo.getMachineSkillVos().stream().forEach(skillVo -> {
                MachineSkillRel rel = new MachineSkillRel();
                rel.setPokemonId(pokemon.getId());
                rel.setSkillId(skillVo.getId());
                machineSkillRelMapper.insert(rel);
            });
        }
    }

    /**
     * 根据id获取精灵
     */
    private Pokemon getPokemon(Integer id) {
        return Optional.ofNullable(pokemonMapper.selectByPrimaryKey(id))
                .orElseThrow(() -> new RetException(PokemonMessage.INVALID_POKEMON_CODE, PokemonMessage.INVALID_POKEMON_MSG));
    }

    /**
     * 根据type获取typeName
     */
    private String getTypeName(Integer type) {
        Map<Integer, String> map = PokemonType.getMap();
        return Optional.ofNullable(type)
                .map(id -> map.get(id))
                .orElse(null);
    }

    /**
     * 根据kind获取kindName
     */
    private String getKindName(Integer kind) {
        Map<Integer, String> map = PokemonKind.getMap();
        return Optional.ofNullable(kind)
                .map(id -> map.get(id))
                .orElse(null);
    }
}
