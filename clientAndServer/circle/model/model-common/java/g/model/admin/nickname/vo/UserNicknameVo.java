package g.model.admin.nickname.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.admin.nickname.po.UserNickname;
import g.model.admin.nickname.so.UserNicknameSo;


/**
 * 昵称值对象
 *
 * @author black
 * @time 2017-2-8 15:41:50
 */
//region your codes 1
public class UserNicknameVo extends BaseObjectVo<UserNickname, UserNicknameSo, UserNicknameVo.UserNicknameQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -1626166532115864647L;
    //endregion your codes 5

    /**
     *  昵称查询逻辑
     */
    public static class UserNicknameQuery extends AbstractQuery<UserNicknameSo> {

        //region your codes 6
        private static final long serialVersionUID = 5174995091022487956L;
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