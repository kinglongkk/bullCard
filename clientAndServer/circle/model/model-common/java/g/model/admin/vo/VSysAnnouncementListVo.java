package g.model.admin.vo;

import g.model.admin.po.VSysAnnouncement;
import g.model.admin.so.VSysAnnouncementSo;
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
 * 系统公告视图列表页值对象
 *
 * @author orange
 * @time 2015-11-19 3:00:56
 */
//region your codes 1
public class VSysAnnouncementListVo extends BaseListVo<VSysAnnouncement, VSysAnnouncementSo, VSysAnnouncementListVo.VSysAnnouncementQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 5718388420352408347L;
    //endregion your codes 5

    /**
     *  系统公告视图列表查询逻辑
     */
    public static class VSysAnnouncementQuery extends AbstractQuery<VSysAnnouncementSo> {

        //region your codes 6
        private static final long serialVersionUID = 515017360658048816L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria = Criteria.add(VSysAnnouncement.PROP_LOCAL, Operator.EQ,this.searchObject.getLocal());
            criteria.addAnd(VSysAnnouncement.PROP_ID,Operator.EQ,this.searchObject.getId());
            criteria.addAnd(VSysAnnouncement.PROP_ANNOUNCEMENT_TYPE,Operator.EQ,this.searchObject.getAnnouncementType());
            criteria.addAnd(VSysAnnouncement.PROP_RECIPIENT_USER_ID,Operator.EQ,this.searchObject.getRecipientUserId());
            return criteria;
            //endregion your codes 2
        }

        //region your codes 3
        public Sort getDefaultSort() {
            Sort sort = new Sort(Order.desc(VSysAnnouncement.PROP_ID));
            return sort;
        }
        /**
         * 获取未读公告数量
         * @return
         */
        public Criteria unReadCount() {
            return Criteria.add(VSysAnnouncement.PROP_LOCAL, Operator.EQ,searchObject.getLocal());
        }
        //endregion your codes 3
    }

    //region your codes 4
    //判断已读未读
    public void changeReadState(Integer userId){
        List<VSysAnnouncement> vSysAnnouncementList = getResult();
        for (VSysAnnouncement vSysAnnouncement : vSysAnnouncementList) {
            List<Map> params = JsonTool.fromJson(vSysAnnouncement.getReadUserId(), List.class);
            List<Integer> readUserIdList = CollectionTool.extractToList(params, "sys_user_id");
            vSysAnnouncement.setRead(readUserIdList.contains(userId));
        }
        setResult(vSysAnnouncementList);
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