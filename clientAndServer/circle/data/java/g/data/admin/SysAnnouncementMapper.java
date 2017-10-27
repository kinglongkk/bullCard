package g.data.admin;


import org.soul.data.rdb.mybatis.IBaseMapper;
import g.model.admin.po.SysAnnouncement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 系统公告数据访问对象
 *
 * @author orange
 * @time 2015-11-26 21:12:24
 */
//region your codes 1
public interface SysAnnouncementMapper extends IBaseMapper<SysAnnouncement, Integer> {
//endregion your codes 1

    //region your codes 2
    boolean updateDefaultParam(Integer id);

    void resetPreference();

    /**
     * 查询该代理下面的所有player
     * @param map
     * @return List
     */
    List searchPlayerCountBalance(Map map);
    //endregion your codes 2

    /**
     * 查找该代理下的未结算注单
     * @param ownerId 该玩家所属代理者的ID
     * @return Double
     */
    Double selectPreOrderMoney(Integer ownerId);

}