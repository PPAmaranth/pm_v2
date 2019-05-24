package pp.pokemon.pm.dao.mapper.user;

import org.apache.ibatis.annotations.Param;
import pp.pokemon.pm.dao.entity.user.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectExistedByUsername(@Param("userId") Integer userId, @Param("username") String username);

    User selectExistedByMobile(@Param("userId") Integer userId, @Param("mobile") String mobile);

    User selectExistedByEmail(@Param("userId") Integer userId, @Param("email") String email);

    User selectByWord(@Param("word") String word);
}