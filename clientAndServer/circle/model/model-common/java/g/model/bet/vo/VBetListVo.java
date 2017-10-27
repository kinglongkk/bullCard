package g.model.bet.vo;

import g.model.admin.po.Remark;
import g.model.bet.po.VBetList;
import g.model.match.so.VBetListSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;


/**
 * 注单值对象
 *
 * @author tom
 * @time 2016-6-7 19:50:30
 */
//region your codes 1
public class VBetListVo extends BaseObjectVo<VBetList, VBetListSo, VBetListVo.VBetListQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -2721939712543948201L;
    //endregion your codes 5

    /**
     *  注单查询逻辑
     */
    public static class VBetListQuery extends AbstractQuery<VBetListSo> {

        //region your codes 6
        private static final long serialVersionUID = -5214372638719248512L;
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
    private Remark remark;

    public Remark getRemark() {
        return remark;
    }

    public void setRemark(Remark remark) {
        this.remark = remark;
    }
    //endregion your codes 4

}