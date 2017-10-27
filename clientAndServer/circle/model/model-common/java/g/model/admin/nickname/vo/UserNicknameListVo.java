package g.model.admin.nickname.vo;

import g.model.admin.nickname.po.UserNickname;
import g.model.admin.nickname.so.UserNicknameSo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;


/**
 * 昵称列表页值对象
 *
 * @author black
 * @time 2017-2-8 15:41:50
 */
//region your codes 1
public class UserNicknameListVo extends BaseListVo<UserNickname, UserNicknameSo, UserNicknameListVo.UserNicknameQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 2228979196668872075L;
    //endregion your codes 5

    /**
     *  昵称列表查询逻辑
     */
    public static class UserNicknameQuery extends AbstractQuery<UserNicknameSo> {

        //region your codes 6
        private static final long serialVersionUID = 642395304967680177L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria = Criteria.and(

                    Criteria.add(UserNickname.PROP_ID, Operator.EQ, this.searchObject.getId()),
                    Criteria.add(UserNickname.PROP_NICKNAME, Operator.EQ, this.searchObject.getNickname())
            );
            return criteria;
            //endregion your codes 2
        }


        //region your codes 3

        //endregion your codes 3
    }

    //region your codes 4

    //endregion your codes 4

}