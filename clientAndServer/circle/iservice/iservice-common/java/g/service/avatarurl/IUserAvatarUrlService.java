package g.service.avatarurl;

import org.soul.iservice.support.IBaseService;
import g.model.avatarurl.po.UserAvatarUrl;
import g.model.avatarurl.vo.UserAvatarUrlListVo;
import g.model.avatarurl.vo.UserAvatarUrlVo;


/**
 * 用户头像服务接口
 *
 * @author black
 * @time 2017-4-21 11:23:25
 */
//region your codes 1
public interface IUserAvatarUrlService extends IBaseService<UserAvatarUrlListVo, UserAvatarUrlVo, UserAvatarUrl, Integer> {
//endregion your codes 1

    //region your codes 2
    /**
     * 随机取用户头像
     * @return
     */
    String selectUserAvatarUrl();
    //endregion your codes 2

}