package g.model.announcement.vo;


import g.model.announcement.po.VGameAnnouncement;
import g.model.announcement.so.VGameAnnouncementSo;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.data.json.JsonTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.commons.query.sort.Sort;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;

import java.util.List;
import java.util.Map;

/**
 * 游戏公告视图列表页值对象
 *
 * @author lance
 * @time 2016-10-28 10:53:18
 */
//region your codes 1
public class VGameAnnouncementListVo extends BaseListVo<VGameAnnouncement, VGameAnnouncementSo, VGameAnnouncementListVo.VGameAnnouncementQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -5844451651048960640L;
    //endregion your codes 5

    /**
     *  系统公告视图列表查询逻辑
     */
    public static class VGameAnnouncementQuery extends AbstractQuery<VGameAnnouncementSo> {

        //region your codes 6
        private static final long serialVersionUID = 515017360658048816L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria = Criteria.add(VGameAnnouncement.PROP_LOCAL, Operator.EQ,this.searchObject.getLocal());
            criteria.addAnd(VGameAnnouncement.PROP_ID,Operator.EQ,this.searchObject.getId());
            criteria.addAnd(VGameAnnouncement.PROP_ANNOUNCEMENT_TYPE,Operator.EQ,this.searchObject.getAnnouncementType());
            return criteria;
            //endregion your codes 2
        }

        //region your codes 3
        public Sort getDefaultSort() {
            Sort sort = new Sort(Order.desc(VGameAnnouncement.PROP_ID));
            return sort;
        }
        /**
         * 获取未读公告数量
         * @return
         */
        public Criteria unReadCount() {
            return Criteria.add(VGameAnnouncement.PROP_LOCAL, Operator.EQ,searchObject.getLocal());
        }
        //endregion your codes 3
    }

    //region your codes 4
    //判断已读未读
    public void changeReadState(Integer userId){
        List<VGameAnnouncement> vGameAnnouncementList = getResult();
        for (VGameAnnouncement vGameAnnouncement : vGameAnnouncementList) {
            List<Map> params = JsonTool.fromJson(vGameAnnouncement.getReadUserId(), List.class);
            List<Integer> readUserIdList = CollectionTool.extractToList(params, "sys_user_id");
            vGameAnnouncement.setRead(readUserIdList.contains(userId));
        }
        setResult(vGameAnnouncementList);
    }


    private String agentIds;//发布个人公告给指定代理id

    public String getAgentIds() {
        return agentIds;
    }

    public void setAgentIds(String agentIds) {
        this.agentIds = agentIds;
    }

    //endregion your codes 4

}