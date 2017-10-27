package g.model.ai.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.ai.po.AiUser;
import g.model.ai.so.AiUserSo;


/**
 * ai用户值对象
 *
 * @author LENOVO
 * @time 2016-12-20 15:58:34
 */
//region your codes 1
public class AiUserVo extends BaseObjectVo<AiUser, AiUserSo, AiUserVo.AiUserQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 5666912097672678815L;
    //endregion your codes 5

    /**
     *  ai用户查询逻辑
     */
    public static class AiUserQuery extends AbstractQuery<AiUserSo> {

        //region your codes 6
        private static final long serialVersionUID = 1368152676084406260L;
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