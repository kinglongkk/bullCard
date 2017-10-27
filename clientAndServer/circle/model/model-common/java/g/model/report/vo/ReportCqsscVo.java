package g.model.report.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.report.po.ReportCqssc;
import g.model.report.so.ReportCqsscSo;


/**
 * 值对象
 *
 * @author tom
 * @time 2016-8-1 15:34:21
 */
//region your codes 1
public class ReportCqsscVo extends BaseObjectVo<ReportCqssc, ReportCqsscSo, ReportCqsscVo.ReportCqsscQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 7999556088553159623L;
    //endregion your codes 5

    /**
     *  查询逻辑
     */
    public static class ReportCqsscQuery extends AbstractQuery<ReportCqsscSo> {

        //region your codes 6
        private static final long serialVersionUID = 1879579031513461413L;
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