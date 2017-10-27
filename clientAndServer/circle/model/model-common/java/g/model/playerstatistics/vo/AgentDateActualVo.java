package g.model.playerstatistics.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.playerstatistics.po.AgentDateActual;
import g.model.playerstatistics.so.AgentDateActualSo;


/**
 * 代理商每日实时统计表值对象
 *
 * @author lenovo
 * @time 2017-1-21 9:43:02
 */
//region your codes 1
public class AgentDateActualVo extends BaseObjectVo<AgentDateActual, AgentDateActualSo, AgentDateActualVo.AgentDateActualQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -5582722040962370062L;
    //endregion your codes 5

    /**
     *  代理商每日实时统计表查询逻辑
     */
    public static class AgentDateActualQuery extends AbstractQuery<AgentDateActualSo> {

        //region your codes 6
        private static final long serialVersionUID = 5690718321986761854L;
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