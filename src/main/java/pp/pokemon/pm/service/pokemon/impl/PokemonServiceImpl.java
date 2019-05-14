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
import pp.pokemon.pm.dao.vo.pokemon.*;
import pp.pokemon.pm.service.pokemon.PokemonService;
import pp.pokemon.pm.dao.vo.file.BatchInsertPokemonVo;
import pp.pokemon.pm.web.vo.pokemon.*;

import java.util.*;
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
    private PropertyMapper propertyMapper;

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
                    // 精灵名字
                    respVo.setCnName(pokemon.getName());
                    // 精灵主图
                    respVo.setImgUrl(getImgUrl(pokemon.getId()));
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
    public void add(AddPokemonReqVo reqVo) {

        // 插入主表
        Pokemon pokemon = new Pokemon();
        BeanUtils.copyProperties(reqVo, pokemon);
        pokemon.setName(reqVo.getCnName());
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
        if (!CollectionUtils.isEmpty(reqVo.getEvolutionSkillReqVos())) {
            reqVo.getEvolutionSkillReqVos().stream().forEach(skillVo -> {
                EvolveSkillRel rel = new EvolveSkillRel();
                rel.setPokemonId(pokemon.getId());
                rel.setSkillId(skillVo.getId());
                rel.setLevel(skillVo.getLevel());
                evolveSkillRelMapper.insert(rel);
            });
        }

        // 插入技能机技能关系表
        if (!CollectionUtils.isEmpty(reqVo.getMachineSkillReqVos())) {
            reqVo.getMachineSkillReqVos().stream().forEach(skillVo -> {
                MachineSkillRel rel = new MachineSkillRel();
                rel.setPokemonId(pokemon.getId());
                rel.setSkillId(skillVo.getId());
                machineSkillRelMapper.insert(rel);
            });
        }
    }

    @Override
    public PokemonDetailRespVo detail(PokemonDetailReqVo reqVo) {
        // 验参 : 精灵存在
        Pokemon pokemon = getPokemon(reqVo.getId());

        PokemonDetailRespVo respVo = new PokemonDetailRespVo();
        BeanUtils.copyProperties(pokemon, respVo);
        // 精灵名称
        respVo.setCnName(pokemon.getName());
        // 精灵属性
        respVo.setPropertyOneName(getPropertyName(pokemon.getPropertyOne()));
        respVo.setPropertyTwoName(getPropertyName(pokemon.getPropertyTwo()));
        // 精灵附件
        respVo.setImgUrl(getImgUrl(pokemon.getId()));
        // 进化前精灵id与进化条件
        EvolvePokemonRel rel = getEvolveRelByPokemonId(pokemon.getId());
        respVo.setBeforeId(null != rel ? rel.getBeforeId() : null);
        respVo.setCondition(null != rel ? rel.getCondition() : "");

        // 进化关系全图
        List<EvolutionRelationshipRespVo> relationshipRespVos = getEvolutionRelationship(pokemon.getId());
        respVo.setEvolutionRelationship(relationshipRespVos);

        // 进化技能
        List<EvolutionSkillRespVo> evolutionSkillRespVos = evolveSkillRelMapper.selectSkillsByPokemonId(pokemon.getId());
        respVo.setEvolutionSkills(evolutionSkillRespVos);

        // 技能机技能
        List<MachineSkillRespVo> machineSkillRespVos = machineSkillRelMapper.selectSkillsByPokemonId(pokemon.getId());
        respVo.setMachineSkills(machineSkillRespVos);
        
        return respVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(EditPokemonReqVo reqVo) {
        // 验参 : 精灵存在
        Pokemon pokemon = getPokemon(reqVo.getId());
        BeanUtils.copyProperties(reqVo, pokemon);
        pokemon.setName(reqVo.getCnName());
        pokemonMapper.updateByPrimaryKey(pokemon);

        // 清除精灵进化,技能关系
        deletePokemonRels(pokemon.getId());

        // 更新进化关系表
        if (null != reqVo.getEvolutionRelationship()) {
            EvolvePokemonRel rel = new EvolvePokemonRel();
            rel.setPokemonId(pokemon.getId());
            rel.setBeforeId(reqVo.getEvolutionRelationship().getBeforeId());
            rel.setCondition(reqVo.getEvolutionRelationship().getCondition());
            evolvePokemonRelMapper.insert(rel);
        }

        // 更新进化技能表
        if (!CollectionUtils.isEmpty(reqVo.getEvolutionSkills())) {
            reqVo.getEvolutionSkills().stream().forEach(skillVo -> {
                EvolveSkillRel rel = new EvolveSkillRel();
                rel.setPokemonId(pokemon.getId());
                rel.setSkillId(skillVo.getId());
                rel.setLevel(skillVo.getLevel());
                evolveSkillRelMapper.insert(rel);
            });
        }

        // 更新技能机技能关系表
        if (!CollectionUtils.isEmpty(reqVo.getMachineSkills())) {
            reqVo.getMachineSkills().stream().forEach(skillVo -> {
                MachineSkillRel rel = new MachineSkillRel();
                rel.setPokemonId(pokemon.getId());
                rel.setSkillId(skillVo.getId());
                machineSkillRelMapper.insert(rel);
            });
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(PokemonDeleteReqVo reqVo) {
        // 清除精灵进化,技能关系
        deletePokemonRels(reqVo.getId());

        // 删除精灵
        pokemonMapper.deleteByPrimaryKey(reqVo.getId());
    }

    /**
     * 根据id清除精灵进化,技能关系
     */
    private void deletePokemonRels(Integer pokemonId) {
        evolvePokemonRelMapper.deleteByPokemonId(pokemonId);
        evolveSkillRelMapper.deleteByPokemonId(pokemonId);
        machineSkillRelMapper.deleteByPokemonId(pokemonId);
    }


    /**
     * 根据id获取精灵
     */
    private Pokemon getPokemon(Integer id) {
        return Optional.ofNullable(pokemonMapper.selectByPrimaryKey(id))
                .orElseThrow(() -> new RetException(PokemonMessage.INVALID_POKEMON_CODE, PokemonMessage.INVALID_POKEMON_MSG));
    }

    /**
     * 根据精灵id获取精灵主图
     */
    private String getImgUrl(Integer pokemonId) {
        // 精灵主图
        PokemonAttachmentReqVo pokemonAttachmentReqVo = new PokemonAttachmentReqVo();
        pokemonAttachmentReqVo.setId(pokemonId);
        pokemonAttachmentReqVo.setType(PokemonType.LIST.getType());
        PokemonAttachmentRel rel = attachmentRelMapper.selectByParam(pokemonAttachmentReqVo)
                .stream()
                .findAny()
                .orElseGet(() -> new PokemonAttachmentRel());

        return Optional.ofNullable(attachmentMapper.selectByPrimaryKey(rel.getAttachmentId()))
                .map(Attachment::getFileUri)
                .orElse("");
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

    /**
     * 根据property_id获取property_name
     */
    private String getPropertyName(Integer id) {
        return Optional.ofNullable(propertyMapper.selectByPrimaryKey(id))
                .map(Property::getName)
                .orElse(null);
    }

    /**
     * 根据精灵id获取进化关系(获取直系父系)
     */
    private EvolvePokemonRel getEvolveRelByPokemonId(Integer pokemonId) {
        return evolvePokemonRelMapper.selectByPokemonId(pokemonId);
    }

    /**
     * 根据精灵id获取进化关系(获取直系子系)
     */
    private EvolvePokemonRel getEvolveRelByBeforeId(Integer beforeId) {
        return evolvePokemonRelMapper.selectByBeforeId(beforeId);
    }

    /**
     * 根据精灵id获取进化关系(获取所有父系)
     */
    private void getParentEvolutionRelationship(Integer pokemonId, List<EvolvePokemonRel> pokemonRels) {
        EvolvePokemonRel rel = getEvolveRelByPokemonId(pokemonId);
        if (null != rel) {
            pokemonRels.add(0, rel);
            getParentEvolutionRelationship(rel.getBeforeId(), pokemonRels);
        }
    }

    /**
     * 根据精灵id获取进化关系(获取所有子系)
     */
    private void getChildEvolutionRelationship(Integer beforeId, List<EvolvePokemonRel> pokemonRels) {
        EvolvePokemonRel rel = getEvolveRelByBeforeId(beforeId);
        if (null != rel) {
            pokemonRels.add(rel);
            getChildEvolutionRelationship(rel.getPokemonId(), pokemonRels);
        }
    }

    /**
     * 根据精灵id获取进化关系全图
     */
    private List<EvolutionRelationshipRespVo> getEvolutionRelationship(Integer pokemonId) {
        // 进化关系链
        List<EvolvePokemonRel> pokemonRels = new ArrayList<>();
        // 获取父系进化链
        getParentEvolutionRelationship(pokemonId, pokemonRels);
        // 获取子系进化链
        getChildEvolutionRelationship(pokemonId, pokemonRels);

        List<EvolutionRelationshipRespVo> respVos = new ArrayList<>();
        Integer seq = 1;
        for (EvolvePokemonRel rel : pokemonRels) {
            EvolutionRelationshipRespVo respVo = new EvolutionRelationshipRespVo();
            // 序号
            respVo.setSeq(seq++);
            // 精灵信息
            Pokemon pokemon = getPokemon(rel.getBeforeId());
            respVo.setId(pokemon.getId());
            respVo.setName(pokemon.getName());
            // 进化条件
            respVo.setCondition(rel.getCondition());
            // 精灵图片
            String imgUrl = getImgUrl(pokemon.getId());
            respVo.setImgUrl(imgUrl);
            respVos.add(respVo);
        }

        // 加上进化的最终形态
        if (!CollectionUtils.isEmpty(respVos)) {
            EvolutionRelationshipRespVo respVo = new EvolutionRelationshipRespVo();
            // 序号
            respVo.setSeq(seq++);
            EvolvePokemonRel rel = pokemonRels.get(pokemonRels.size()-1);
            // 精灵信息
            Pokemon pokemon = getPokemon(rel.getPokemonId());
            respVo.setId(pokemon.getId());
            respVo.setName(pokemon.getName());
            // 精灵图片
            String imgUrl = getImgUrl(pokemon.getId());
            respVo.setImgUrl(imgUrl);
            respVos.add(respVo);
        }
        return respVos;
    }
}
