package g.model.admin.agent.currency.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.admin.agent.currency.po.AgentCurrencyExchange;
import g.model.admin.agent.currency.so.AgentCurrencyExchangeSo;


/**
 * 货币汇率值对象
 *
 * @author black
 * @time 2016-11-28 11:26:51
 */
//region your codes 1
public class AgentCurrencyExchangeVo extends BaseObjectVo<AgentCurrencyExchange, AgentCurrencyExchangeSo, AgentCurrencyExchangeVo.AgentCurrencyExchangeQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -4873202380821227862L;
    //endregion your codes 5

    /**
     *  货币汇率查询逻辑
     */
    public static class AgentCurrencyExchangeQuery extends AbstractQuery<AgentCurrencyExchangeSo> {

        //region your codes 6
        private static final long serialVersionUID = 8905147101413849355L;
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