package g.service.admin.agent.message;

import g.data.admin.agent.message.UserAgentMapper;
import g.model.admin.agent.message.po.UserAgent;
import g.model.admin.agent.message.vo.UserAgentListVo;
import g.model.admin.agent.message.vo.UserAgentVo;
import org.soul.service.support.BaseService;

import java.util.Map;


/**
 * 代理商扩展表服务
 *
 * @author black
 * @time 2016-11-28 16:41:26
 */
//region your codes 1
public class UserAgentService extends BaseService<UserAgentMapper, UserAgentListVo, UserAgentVo, UserAgent, Integer> implements IUserAgentService {
//endregion your codes 1

    //region your codes 2
    public UserAgent selectInfoByAgentId(Map map) {

        return this.mapper.selectInfoByAgentId(map);
    }
    //endregion your codes 2

}