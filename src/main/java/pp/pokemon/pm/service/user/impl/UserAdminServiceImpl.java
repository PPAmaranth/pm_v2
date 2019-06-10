package pp.pokemon.pm.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pp.pokemon.pm.common.constant.RetException;
import pp.pokemon.pm.common.enums.user.UserStatus;
import pp.pokemon.pm.common.message.SecurityMessage;
import pp.pokemon.pm.dao.entity.user.User;
import pp.pokemon.pm.dao.mapper.user.UserMapper;
import pp.pokemon.pm.service.user.UserAdminService;
import pp.pokemon.pm.web.vo.userAdmin.UserAdminReqVo;

import java.util.Date;
import java.util.Optional;

@Service
public class UserAdminServiceImpl implements UserAdminService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 启用用户
     * @param reqVo
     */
    @Override
    public void enable(UserAdminReqVo reqVo) {
        // 并没有禁止已启用的用户继续启用
        toggle(reqVo.getUserId(), UserStatus.ENABLED.getValue());
    }

    /**
     * 禁用用户
     * @param reqVo
     */
    @Override
    public void disable(UserAdminReqVo reqVo) {
        toggle(reqVo.getUserId(), UserStatus.DISABLED.getValue());
    }

    /**
     * 切换用户状态
     * @param userId
     * @param status
     */
    private void toggle(Integer userId, Integer status) {
        User user = getUser(userId);
        user.setStatus(status);
        user.setUpdateDate(new Date());
        userMapper.updateByPrimaryKey(user);
    }

    /**
     * 根据id获取用户
     * @param userId
     * @return
     */
    private User getUser(Integer userId) {
        return Optional.ofNullable(userMapper.selectByPrimaryKey(userId))
                .orElseThrow(() -> new RetException(SecurityMessage.INVALID_USER_CODE, SecurityMessage.INVALID_USER_MSG));
    }
}
