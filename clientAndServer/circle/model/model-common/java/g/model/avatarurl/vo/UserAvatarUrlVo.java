package g.model.avatarurl.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.avatarurl.po.UserAvatarUrl;
import g.model.avatarurl.so.UserAvatarUrlSo;


/**
 * 用户头像值对象
 *
 * @author black
 * @time 2017-4-21 11:23:26
 */
//region your codes 1
public class UserAvatarUrlVo extends BaseObjectVo<UserAvatarUrl, UserAvatarUrlSo, UserAvatarUrlVo.UserAvatarUrlQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -4831160301554850161L;
    //endregion your codes 5

    /**
     *  用户头像查询逻辑
     */
    public static class UserAvatarUrlQuery extends AbstractQuery<UserAvatarUrlSo> {

        //region your codes 6
        private static final long serialVersionUID = 3399284458591093507L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return null;
            //endregion your codes 2
        }

        //region your codes 3

        //endregion your codes 3

    }

    //region your codes 4

    //endregion your codes 4

}