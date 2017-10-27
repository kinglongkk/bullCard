package g.service.playerstatistics;

import g.data.playerstatistics.AgentDateActualMapper;
import g.model.playerstatistics.po.AgentDateActual;
import g.model.playerstatistics.vo.AgentDateActualListVo;
import g.model.playerstatistics.vo.AgentDateActualVo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.service.support.BaseService;

import java.util.Date;
import java.util.List;


/**
 * 代理商每日实时统计表服务
 *
 * @author lenovo
 * @time 2017-1-21 9:43:02
 */
//region your codes 1
public class AgentDateActualService extends BaseService<AgentDateActualMapper, AgentDateActualListVo, AgentDateActualVo, AgentDateActual, Integer> implements IAgentDateActualService {

    @Override
    public Number getProfitAmount(Date startTime, Date endTime) {
        Criteria criteria = Criteria.and(
                Criteria.add(AgentDateActual.PROP_STATISTICS_DATETIME, Operator.GE,  startTime),
                Criteria.add(AgentDateActual.PROP_STATISTICS_DATETIME, Operator.LE,  endTime)
        );
        Number num = this.mapper.sum(criteria,"profitAmount");
        return num;
    }

    @Override
    public AgentDateActual getAgentDateActual(Date startTime, Date endTime, String username) {
        Criteria criteria = Criteria.and(
                Criteria.add(AgentDateActual.PROP_STATISTICS_DATETIME, Operator.GE,  startTime),
                Criteria.add(AgentDateActual.PROP_STATISTICS_DATETIME, Operator.LE,  endTime),
                Criteria.add(AgentDateActual.PROP_IS_DELETE, Operator.EQ,  false),
                Criteria.add(AgentDateActual.PROP_USERNAME, Operator.EQ,  username)
        );
        List<AgentDateActual> agentDateActual = this.mapper.search(criteria);
        if(agentDateActual.size()==0)
            return null;
        return agentDateActual.get(0);
    }

    @Override
    public Boolean quartzAgentDateActual() {

        return this.mapper.playerAgentActual();
    }
}