package g.service.ai.bull.manager;

import g.model.UserTypeEnum;
import org.apache.shiro.session.mgt.SessionManager;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.lang.string.RandomStringTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.data.mapper.security.privilege.SysUserMapper;
import org.soul.model.security.privilege.po.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springside.modules.nosql.redis.JedisTemplate;

import java.util.List;

/**
 * Created by KXZ on 2016/12/17.
 */
@Service
public class AiUserMng {

    @Autowired
    @Qualifier("jedisTemplateGame")
    private JedisTemplate jedisTemplateGame;

    @Autowired
    private SysUserMapper sysUserMapper;

    public List<SysUser> getUsers(){
        Criteria criteria = Criteria.add(SysUser.PROP_USERNAME, Operator.LIKE, "mkk%");
        List<SysUser> rs = sysUserMapper.search(criteria);
        return rs;
    }

    public String getLoginToken(String userId){
        String token = RandomStringTool.uuid();
        jedisTemplateGame.set(CacheKey.getCacheKey(g.model.cache.CacheKey.TOKEN, token),
                userId,10);
        return token;
    }

}
