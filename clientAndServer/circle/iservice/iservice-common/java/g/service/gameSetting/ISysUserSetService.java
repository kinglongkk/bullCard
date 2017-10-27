package g.service.gameSetting;

import org.soul.iservice.support.IBaseService;
import g.model.gameSetting.po.SysUserSet;
import g.model.gameSetting.vo.SysUserSetListVo;
import g.model.gameSetting.vo.SysUserSetVo;


/**
 * 服务接口
 *
 * @author lenovo
 * @time 2016-11-2 17:11:53
 */
//region your codes 1
public interface ISysUserSetService extends IBaseService<SysUserSetListVo, SysUserSetVo, SysUserSet, Integer> {
//endregion your codes 1

    //region your codes 2
    /**
     * 游戏类型是否已经存在
     *
     * @return true: 存在，false：不存在
     */
    boolean isExists(SysUserSetVo O);

    /**
     * 根据游戏类型查找游戏类型
     * @param userSetVo
     * @param
     * @return
     */
    SysUserSet findByGameType(SysUserSetVo userSetVo);



    //endregion your codes 2

}