package g.service.gameSetting;

import org.apache.shiro.session.mgt.SessionManager;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.service.support.BaseService;
import g.data.gameSetting.SysUserSetMapper;
import g.model.gameSetting.po.SysUserSet;
import g.model.gameSetting.vo.SysUserSetListVo;
import g.model.gameSetting.vo.SysUserSetVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 服务
 *
 * @author lenovo
 * @time 2016-11-2 17:11:54
 */
//region your codes 1
public class SysUserSetService extends BaseService<SysUserSetMapper, SysUserSetListVo, SysUserSetVo, SysUserSet, Integer> implements ISysUserSetService {

//endregion your codes 1

    //region your codes 2
    @Override
    public boolean isExists(SysUserSetVo O) {
        return findByGameType(O) != null;
    }

    @Override
    public SysUserSet findByGameType(SysUserSetVo userSetVo) {
        Map<String, Object> map = new HashMap();
        map.put(SysUserSet.PROP_PLAYER_ID,userSetVo.getSearch().getPlayerId());
        map.put(SysUserSet.PROP_GAMETYPE, userSetVo.getSearch().getGametype());
        SysUserSetListVo L=new SysUserSetListVo();
        L.setConditions(map);
        List<SysUserSet> results = andSearch(L);
        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }
    //endregion your codes 2

}