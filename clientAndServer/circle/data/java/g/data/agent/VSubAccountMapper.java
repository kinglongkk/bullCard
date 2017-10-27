package g.data.agent;

import g.model.agent.po.VSubAccount;
import g.model.agent.vo.VSubAccountVo;
import org.soul.data.rdb.mybatis.IBaseQueryMapper;
import g.model.agent.vo.VSubAccountListVo;

import java.util.List;
import java.util.Map;


/**
 * 子账户视图数据访问对象
 *
 * @author jeff
 * @time 2015-10-20 10:49:12
 */
//region your codes 1
public interface VSubAccountMapper extends IBaseQueryMapper<VSubAccount, Integer> {
//endregion your codes 1

    //region your codes 2

    /**
     * 根据subsystem_code获取角色
     * @param vSubAccountListVo
     * @return
     */
    List<Map<String,Object>> getRoles(VSubAccountListVo vSubAccountListVo);

    /**
     * 插入数据如果不存在
     * @param params
     * @return
     */
    int insertRoleNotExist(Map<String, Object> params);

    /**
     * 改变状态 根据ids
     * @param vSubAccountVo
     * @return
     */
    int changeStatusByIds(VSubAccountVo vSubAccountVo);

    /**
     * 根据默认角色id 重置角色权限
     * @param params
     * @return
     */
    int resetRole2Default(Map<String, Object> params);

    /**
     * 获取序列下一个值
     * @return
     */
    int getNextVal();
    //endregion your codes 2

}