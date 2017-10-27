package g.service.common;

import g.model.agent.po.VUserPlayerGroup;
import g.model.agent.vo.VUserPlayerGroupListVo;
import g.model.agent.vo.VUserPlayerGroupVo;
import org.soul.iservice.support.IBaseService;


/**
 * 服务接口
 *
 * @author orange
 * @time 2016-4-27 15:56:13
 */
//region your codes 1
public interface IVUserPlayerGroupService extends IBaseService<VUserPlayerGroupListVo, VUserPlayerGroupVo, VUserPlayerGroup, Integer> {
//endregion your codes 1

    //region your codes 2

    /**
     * 编辑玩家分组
     * @param vo
     * @return
     */
    VUserPlayerGroupVo editUserGroup(VUserPlayerGroupVo vo);

    /**
     * 查询玩家分组名称数量
     * @param vo
     * @return
     */
    boolean countGroupname(VUserPlayerGroupVo vo);


    //endregion your codes 2

}