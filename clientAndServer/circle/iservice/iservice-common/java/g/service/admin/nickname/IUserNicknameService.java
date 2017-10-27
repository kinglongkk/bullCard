package g.service.admin.nickname;

import g.model.admin.nickname.po.UserNickname;
import g.model.admin.nickname.vo.UserNicknameListVo;
import g.model.admin.nickname.vo.UserNicknameVo;
import org.soul.iservice.support.IBaseService;


/**
 * 昵称服务接口
 *
 * @author black
 * @time 2017-2-8 15:41:50
 */
//region your codes 1
public interface IUserNicknameService extends IBaseService<UserNicknameListVo, UserNicknameVo, UserNickname, Integer> {
//endregion your codes 1

    //region your codes 2
    /**
     * 新增用户昵称
     * @param nickname
     * @return boolean
     */
    boolean addNewNickname(String nickname);

    /**
     * 随机选取昵称
     * @return
     */
    String selectUserNickname();
    //endregion your codes 2

}