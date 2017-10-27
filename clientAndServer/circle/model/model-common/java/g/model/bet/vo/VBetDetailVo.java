package g.model.bet.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.bet.po.VBetDetail;
import g.model.bet.so.VBetDetailSo;


/**
 * 值对象
 *
 * @author mark
 * @time 2016-7-12 14:22:28
 */
//region your codes 1
public class VBetDetailVo extends BaseObjectVo<VBetDetail, VBetDetailSo, VBetDetailVo.VBetDetailQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -2703932087372017554L;
    //endregion your codes 5

    /**
     *  查询逻辑
     */
    public static class VBetDetailQuery extends AbstractQuery<VBetDetailSo> {

        //region your codes 6
        private static final long serialVersionUID = 4525051237068638383L;
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