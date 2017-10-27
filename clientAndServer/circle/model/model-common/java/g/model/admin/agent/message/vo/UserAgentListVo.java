package g.model.admin.agent.message.vo;

import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.admin.agent.message.po.UserAgent;
import g.model.admin.agent.message.so.UserAgentSo;


/**
 * 代理商扩展表列表页值对象
 *
 * @author black
 * @time 2016-11-28 16:41:26
 */
public class UserAgentListVo extends BaseListVo<UserAgent, UserAgentSo, UserAgentListVo.UserAgentQuery> {

    private static final long serialVersionUID = -2332244514358197200L;

    /**
     *  代理商扩展表列表查询逻辑
     */
    public static class UserAgentQuery extends AbstractQuery<UserAgentSo> {

        private static final long serialVersionUID = -1540289899360551102L;

        @Override
        public Criteria getCriteria() {

            Criteria criteria = Criteria.and(

                    Criteria.add(UserAgent.PROP_ID, Operator.EQ, this.searchObject.getId()),
                    Criteria.add(UserAgent.PROP_AGENT_ID, Operator.EQ, this.searchObject.getAgentId()),
                    Criteria.add(UserAgent.PROP_WARN_TYPE, Operator.EQ, this.searchObject.getWarnType()),
                    Criteria.add(UserAgent.PROP_QUOTA_STATUS, Operator.EQ, this.searchObject.getQuotaStatus())
            );
            return criteria;
        }

    }

}