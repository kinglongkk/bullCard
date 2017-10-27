package g.service.playerstatistics;

import org.soul.iservice.support.IBaseService;
import g.model.playerstatistics.po.AgentDateActual;
import g.model.playerstatistics.vo.AgentDateActualListVo;
import g.model.playerstatistics.vo.AgentDateActualVo;

import java.util.Date;


/**
 * 代理商每日实时统计表服务接口
 *
 * @author lenovo
 * @time 2017-1-21 9:43:02
 */
//region your codes 1
public interface IAgentDateActualService extends IBaseService<AgentDateActualListVo, AgentDateActualVo, AgentDateActual, Integer> {

    /**
     * 获取总的盈亏
     * @param startTime
     * @param endTime
     * @return
     */
    Number getProfitAmount(Date startTime, Date endTime);

    /**
     * 获取单个代理商的盈亏
     * @param startTime
     * @param endTime
     * @param username
     * @return
     */
    AgentDateActual getAgentDateActual(Date startTime,Date endTime,String username);


    /**
     * 定时任务掉存储过程
     * @return
     */
    Boolean  quartzAgentDateActual();

}