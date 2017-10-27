package g.service.admin.nickname;

import g.data.admin.nickname.UserNicknameMapper;
import g.model.admin.nickname.po.UserNickname;
import g.model.admin.nickname.vo.UserNicknameListVo;
import g.model.admin.nickname.vo.UserNicknameVo;
import org.soul.service.support.BaseService;

import java.util.HashMap;
import java.util.Map;


/**
 * 昵称服务
 *
 * @author black
 * @time 2017-2-8 15:41:50
 */
//region your codes 1
public class UserNicknameService extends BaseService<UserNicknameMapper, UserNicknameListVo, UserNicknameVo, UserNickname, Integer> implements IUserNicknameService {
//endregion your codes 1

    //region your codes 2
    public boolean addNewNickname(String nickname) {

        Map map = new HashMap();
        map.put("nickname", nickname);
        return this.mapper.addNewNickname(map);
    }

    public String selectUserNickname() {

        return this.mapper.selectUserNickname();
    }
    //endregion your codes 2

}