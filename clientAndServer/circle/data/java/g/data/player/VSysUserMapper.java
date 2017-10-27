package g.data.player;

import org.soul.data.rdb.mybatis.IBaseMapper;
import g.model.player.po.VSysUser;

/**
 * Created by mark on 16-4-1.
 */
public interface VSysUserMapper extends IBaseMapper<VSysUser, Integer> {
    /**
     * 查询账号在线状态
     * @param userId
     * @return
     */
    VSysUser findOnLineUser(Integer userId);

    /**
     * 查询代理商密钥
     * @param username
     * @return
     */
    String findAgentKey(String username);

}
