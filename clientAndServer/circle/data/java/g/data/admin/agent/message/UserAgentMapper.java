package g.data.admin.agent.message;

import g.model.admin.agent.message.po.UserAgent;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.Map;


/**
 * 代理商扩展表数据访问对象
 *
 * @author black
 * @time 2016-11-28 16:41:26
 */
//region your codes 1
public interface UserAgentMapper extends IBaseMapper<UserAgent, Integer> {
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