package g.web.player.controller;


import g.model.gameSetting.po.SysUserSet;
import g.model.gameSetting.vo.SysUserSetListVo;
import g.service.gameSetting.ISysUserSetService;
import g.web.player.model.ResultData;
import g.web.player.session.SessionManager;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.lang.string.RandomStringTool;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.nosql.redis.JedisTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    ISysUserSetService sysUserSetService;

    @Autowired
    @Qualifier("jedisTemplateGame")
    private JedisTemplate jedisTemplateGame;

    // TODO: 实现功能：保持用户登录状态
    @RequestMapping("/alive")
    public Object alive(){
        return new ResultData(null);
    }


    @RequestMapping("/token")
    public Object token(){
        String token = RandomStringTool.uuid();
        jedisTemplateGame.set(CacheKey.getCacheKey(g.model.cache.CacheKey.TOKEN,token),
                String.valueOf(SessionManager.getUserId()),10);
        ResultData result = new ResultData();
        result.setToken(token);
        result.setUid(SessionManager.getUserId());
        return result;
    }

    //获取登录游戏的声音设置
    @RequestMapping("/voiceSet")
    public Object voiceSet() {
        SysUserSetListVo listVo = new SysUserSetListVo();
        listVo.setProperties(SysUserSet.PROP_MUSIC,SysUserSet.PROP_SOUND);
        listVo.getQuery().setCriterions(new Criterion[]{
                new Criterion(SysUserSet.PROP_PLAYER_ID, Operator.EQ, SessionManager.getUserId())
        });
        List<Map<String,Object>> result = sysUserSetService.searchProperties(listVo);

        return result;
        }




}
