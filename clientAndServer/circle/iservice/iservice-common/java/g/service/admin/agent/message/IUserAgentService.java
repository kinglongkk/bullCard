package g.service.admin.agent.message;

import org.soul.iservice.support.IBaseService;
import g.model.admin.agent.message.po.UserAgent;
import g.model.admin.agent.message.vo.UserAgentListVo;
import g.model.admin.agent.message.vo.UserAgentVo;

import java.util.Map;


/**
 * 代理商扩展表服务接口
 *
 * @author black
 * @time 2016-11-28 16:41:26
 */
//region your codes 1
public interface IUserAgentService extends IBaseService<UserAgentListVo, UserAgentVo, UserAgent, Integer> {
//endregion your codes 1

    //region your codes 2
    /**
     * 通过代理id查找商户信息
     * @param map
     * @return
     */
    UserAgent selectInfoByAgentId(Map map);
    //endregion your codes 2

}