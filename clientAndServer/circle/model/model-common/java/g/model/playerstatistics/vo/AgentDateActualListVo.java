package g.model.playerstatistics.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.playerstatistics.po.AgentDateActual;
import g.model.playerstatistics.so.AgentDateActualSo;


/**
 * 代理商每日实时统计表列表页值对象
 *
 * @author lenovo
 * @time 2017-1-21 9:43:02
 */
//region your codes 1
public class AgentDateActualListVo extends BaseListVo<AgentDateActual, AgentDateActualSo, AgentDateActualListVo.AgentDateActualQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 6586992503551784694L;
    //endregion your codes 5

    /**
     *  代理商每日实时统计表列表查询逻辑
     */
    public static class AgentDateActualQuery extends AbstractQuery<AgentDateActualSo> {

        //region your codes 6
        private static final long serialVersionUID = -6761498544426383802L;
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