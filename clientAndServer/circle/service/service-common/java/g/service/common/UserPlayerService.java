package g.service.common;

import g.model.player.po.VSysUser;
import org.soul.commons.lang.string.StringTool;
import org.soul.service.support.BaseService;
import g.data.player.UserPlayerMapper;
import g.model.player.po.UserPlayer;
import g.model.player.vo.UserPlayerListVo;
import g.model.player.vo.UserPlayerVo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 会员扩展信息服务
 *
 * @author tom
 * @time 2016-5-12 11:54:55
 */
//region your codes 1
public class UserPlayerService extends BaseService<UserPlayerMapper, UserPlayerListVo, UserPlayerVo, UserPlayer, Integer> implements IUserPlayerService {
//endregion your codes 1

    //region your codes 2

    //endregion your codes 2

    public UserPlayer selectUserPlayerInfoById (Integer id){

        return this.mapper.selectUserPlayerInfoById(id);
    }


    public boolean updateUserPlayerInfo (HashMap<String, Object> queryMap){


        return this.mapper.updateUserPlayerInfo(queryMap);
    }


    public Integer selectUserPlayerNumber(){

        return this.mapper.selectUserPlayerNumber();
    }


    public List<Map<String, Object>> selectPlayerOrderByWalletBalance(Map map){

        List<Map<String, Object>> result = this.mapper.selectPlayerOrderByWalletBalance(map);
        return result;
    }

    public List<Map<String, Object>> hiddenUsername(List<Map<String, Object>> list) {

        if (list != null && !list.isEmpty()) {
            for (int i = 0, length = list.size(); i < length; i ++) {
                Map<String, Object> map = list.get(i);
                String key = VSysUser.PROP_NICKNAME;
                //将账户信息保密
                list.get(i).put(key, StringTool.overlayString(map.get(key).toString()));
            }
        }
        return list;
    }

}