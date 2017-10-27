package g.model.admin.agent.currency.vo;

import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.admin.agent.currency.po.AgentCurrencyExchange;
import g.model.admin.agent.currency.so.AgentCurrencyExchangeSo;


/**
 * 货币汇率列表页值对象
 *
 * @author black
 * @time 2016-11-28 11:26:51
 */
public class AgentCurrencyExchangeListVo extends BaseListVo<AgentCurrencyExchange, AgentCurrencyExchangeSo, AgentCurrencyExchangeListVo.AgentCurrencyExchangeQuery> {

    private static final long serialVersionUID = 5099934151690078630L;

    /**
     *  货币汇率列表查询逻辑
     */
    public static class AgentCurrencyExchangeQuery extends AbstractQuery<AgentCurrencyExchangeSo> {

        //region your codes 6
        private static final long serialVersionUID = -6868339435136639746L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {

            Criteria criteria = Criteria.and(

                    Criteria.add(AgentCurrencyExchange.PROP_ID, Operator.EQ, this.searchObject.getId()),
                    Criteria.add(AgentCurrencyExchange.PROP_CODE, Operator.EQ, this.searchObject.getCode()),
                    Criteria.add(AgentCurrencyExchange.PROP_NAME, Operator.EQ, this.searchObject.getName()),
                    Criteria.add(AgentCurrencyExchange.PROP_EXCHANGE, Operator.EQ, this.searchObject.getExchange())
            );
            return criteria;
        }

    }

}