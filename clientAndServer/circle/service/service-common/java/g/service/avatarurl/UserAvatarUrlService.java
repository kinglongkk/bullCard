package g.service.avatarurl;

import g.data.avatarurl.UserAvatarUrlMapper;
import g.model.avatarurl.po.UserAvatarUrl;
import g.model.avatarurl.vo.UserAvatarUrlListVo;
import g.model.avatarurl.vo.UserAvatarUrlVo;
import org.soul.service.support.BaseService;


/**
 * 用户头像服务
 *
 * @author black
 * @time 2017-4-21 11:23:26
 */
//region your codes 1
public class UserAvatarUrlService extends BaseService<UserAvatarUrlMapper, UserAvatarUrlListVo, UserAvatarUrlVo, UserAvatarUrl, Integer> implements IUserAvatarUrlService {
//endregion your codes 1

    //region your codes 2
    public String selectUserAvatarUrl() {

        return this.mapper.selectUserAvatarUrl();
    }
    //endregion your codes 2

}