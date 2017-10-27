package g.data.avatarurl;

import g.model.avatarurl.po.UserAvatarUrl;
import org.soul.data.rdb.mybatis.IBaseMapper;

import javax.jws.soap.SOAPBinding;


/**
 * 用户头像数据访问对象
 *
 * @author black
 * @time 2017-4-21 11:23:25
 */
//region your codes 1
public interface UserAvatarUrlMapper extends IBaseMapper<UserAvatarUrl, Integer> {
//endregion your codes 1

    //region your codes 2

    /**
     * 随机取用户头像
     * @return
     */
    String selectUserAvatarUrl();
    //endregion your codes 2

}