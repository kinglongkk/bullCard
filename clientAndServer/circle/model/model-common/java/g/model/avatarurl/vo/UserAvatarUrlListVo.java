package g.model.avatarurl.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.avatarurl.po.UserAvatarUrl;
import g.model.avatarurl.so.UserAvatarUrlSo;


/**
 * 用户头像列表页值对象
 *
 * @author black
 * @time 2017-4-21 11:23:26
 */
//region your codes 1
public class UserAvatarUrlListVo extends BaseListVo<UserAvatarUrl, UserAvatarUrlSo, UserAvatarUrlListVo.UserAvatarUrlQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 1564783471345649310L;
    //endregion your codes 5

    /**
     *  用户头像列表查询逻辑
     */
    public static class UserAvatarUrlQuery extends AbstractQuery<UserAvatarUrlSo> {

        //region your codes 6
        private static final long serialVersionUID = -1335696229713448032L;
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