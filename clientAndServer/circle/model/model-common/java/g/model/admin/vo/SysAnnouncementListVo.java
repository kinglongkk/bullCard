package g.model.admin.vo;

import g.model.admin.po.SysAnnouncement;
import g.model.admin.so.SysAnnouncementSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;


/**
 * 系统公告列表页值对象
 *
 * @author orange
 * @time 2015-11-17 14:57:53
 */
//region your codes 1
public class SysAnnouncementListVo extends BaseListVo<SysAnnouncement, SysAnnouncementSo, SysAnnouncementListVo.SystemAnnouncementQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -9150806003176367280L;
    //endregion your codes 5

    /**
     *  系统公告列表查询逻辑
     */
    public static class SystemAnnouncementQuery extends AbstractQuery<SysAnnouncementSo> {

        //region your codes 6
        private static final long serialVersionUID = -6265443523082832034L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return null;
            //endregion your codes 2
        }


        //region your codes 3

        //endregion your codes 3
    }

    //region your codes 4
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    //endregion your codes 4

}