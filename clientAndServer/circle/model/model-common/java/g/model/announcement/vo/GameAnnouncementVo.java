package g.model.announcement.vo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.commons.query.sort.Sort;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.announcement.po.GameAnnouncement;
import g.model.announcement.so.GameAnnouncementSo;

import java.util.Date;


/**
 * 游戏公告表值对象
 *
 * @author lenovo
 * @time 2016-10-28 10:53:18
 */
//region your codes 1
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class GameAnnouncementVo extends BaseObjectVo<GameAnnouncement, GameAnnouncementSo, GameAnnouncementVo.GameAnnouncementQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 2521883737775133128L;
    //endregion your codes 5

    /**
     *  游戏公告表查询逻辑
     */
    public static class GameAnnouncementQuery extends AbstractQuery<GameAnnouncementSo> {

        //region your codes 6
        private static final long serialVersionUID = -6336913332199512299L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria = Criteria.add(GameAnnouncement.PROP_ANNOUNCEMENT_TYPE, Operator.EQ,searchObject.getGameId());
            return criteria;
            //endregion your codes 2
        }

        //region your codes 3
        @Override
        public Sort getDefaultSort() {
            return new Sort(Order.desc(GameAnnouncement.PROP_PUBLISH_TIME));
        }
        //endregion your codes 3

    }

    //region your codes 4
    private String[] language;//语言版本
    private String[] title;//按语言版本顺序保存对应的标题
    private String[] content;//按语言版本顺序保存对应的内容
    private java.util.Date publishTime;//发布时间
    private String local;//语言版本
    private Integer publishUserId;//发布人id
    private String publishUserName;//发布人账号
    private String announcementType;//通知方式
    private java.util.Date saveTime;//保存时间
    private String gameId;//选择游戏id
    private String gameName;//游戏名称
    private java.util.Date validityStartTime;//有效期起始
    private java.util.Date validityEndTime;//有效期结束
    private Boolean repeat;//是否重复
    private Integer repeatTime;//间隔时间
    private String repeatUnit;//间隔时间单位
    //endregion your codes 4


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String[] getLanguage() {
        return language;
    }

    public void setLanguage(String[] language) {
        this.language = language;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public String[] getContent() {
        return content;
    }

    public void setContent(String[] content) {
        this.content = content;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Integer getPublishUserId() {
        return publishUserId;
    }

    public void setPublishUserId(Integer publishUserId) {
        this.publishUserId = publishUserId;
    }

    public String getPublishUserName() {
        return publishUserName;
    }

    public void setPublishUserName(String publishUserName) {
        this.publishUserName = publishUserName;
    }

    public String getAnnouncementType() {
        return announcementType;
    }

    public void setAnnouncementType(String announcementType) {
        this.announcementType = announcementType;
    }

    public Date getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Date saveTime) {
        this.saveTime = saveTime;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Date getValidityStartTime() {
        return validityStartTime;
    }

    public void setValidityStartTime(Date validityStartTime) {
        this.validityStartTime = validityStartTime;
    }

    public Date getValidityEndTime() {
        return validityEndTime;
    }

    public void setValidityEndTime(Date validityEndTime) {
        this.validityEndTime = validityEndTime;
    }

    public Boolean getRepeat() {
        return repeat;
    }

    public void setRepeat(Boolean repeat) {
        this.repeat = repeat;
    }

    public Integer getRepeatTime() {
        return repeatTime;
    }

    public void setRepeatTime(Integer repeatTime) {
        this.repeatTime = repeatTime;
    }

    public String getRepeatUnit() {
        return repeatUnit;
    }

    public void setRepeatUnit(String repeatUnit) {
        this.repeatUnit = repeatUnit;
    }
}