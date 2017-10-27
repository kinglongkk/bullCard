package g.data.admin.nickname;

import g.model.admin.nickname.po.UserNickname;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.Map;


/**
 * 昵称数据访问对象
 *
 * @author black
 * @time 2017-2-8 15:41:49
 */
//region your codes 1
public interface UserNicknameMapper extends IBaseMapper<UserNickname, Integer> {
//endregion your codes 1

    //region your codes 2
    /**
     * 新增用户昵称
     * @param map
     * @return boolean
     */
    boolean addNewNickname(Map map);

    /**
     * 随机选取昵称
     * @return
     */
    String selectUserNickname();
    //endregion your codes 2

}