package g.model.admin.agent.message.vo;

import g.model.admin.agent.message.po.UserAgent;
import g.model.admin.agent.message.so.UserAgentSo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;


/**
 * 代理商扩展表值对象
 *
 * @author black
 * @time 2016-11-28 16:41:26
 */
public class UserAgentVo extends BaseObjectVo<UserAgent, UserAgentSo, UserAgentVo.UserAgentQuery> {

    private static final long serialVersionUID = -2120420026638890758L;

    /**
     *  代理商扩展表查询逻辑
     */
    public static class UserAgentQuery extends AbstractQuery<UserAgentSo> {

        private static final long serialVersionUID = -1081087872536640326L;


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