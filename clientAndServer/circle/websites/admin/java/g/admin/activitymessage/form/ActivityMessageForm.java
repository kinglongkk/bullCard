package g.admin.activitymessage.form;

import org.hibernate.validator.constraints.Range;
import org.soul.web.support.IForm;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * 游戏信息表表单验证对象
 *
 * @author black
 * @time 2016-9-5 14:46:12
 */
//region your codes 1
public class ActivityMessageForm implements IForm {
//endregion your codes 1


    /**
     * 活动信息表
     */
    /** 开始时间 */
    private java.util.Date result_startTime;
    /** 结束时间 */
    private java.util.Date result_endTime;
    /** 活动分类 */
    private String result_activityClassifyKey;
    /** 活动类型代码 */
    private String result_activityTypeCode;
    /** 是否展示 */
    private Boolean result_isDisplay;
    /** 排序编号 */
    private Integer result_orderNum;
    /** 活动状态 */
    private String result_status;


    /**
     * 活动规则表
     */
    /** 限制次数,无限制次数为0 */
    private Integer activityRule_limitNumber;
    /** 有效时间 */
    private String activityRule_effectiveTime;
    /** 是否要求首充 */
    private Boolean activityRule_isDemandFirst;
    /** 参与游戏 */
    private String activityRule_gameType;
    /** 是否设置不同享 */
    private Boolean activityRule_isExclusive;
    /** 不同享设置 */
    private String activityRule_exclusiveActivity;


    /**
     * 活动优惠表
     */
    /** 优惠形式 */
    private String activityWayRelation_preferentialForm;
    /** 满足优惠值 */
    private Double activityWayRelation_preferentialValue;
    /** 优惠稽核倍数 */
    private Double activityWayRelation_preferentialAudit;
    /** 顺序 */
    private Integer activityWayRelation_orderColumn;
    /** 是否为实物 */
    private Boolean activityWayRelation_isArticle;
    /** 实物 */
    private String activityWayRelation_article;
    /** 优惠触发值 */
    private Double activityWayRelation_triggerValue;
    /** 优惠比例 */
    private Double activityWayRelation_preferentialRatio;


    @NotNull
    public Date getResult_startTime() {
        return result_startTime;
    }

    public void setResult_startTime(Date result_startTime) {
        this.result_startTime = result_startTime;
    }

    @NotNull
    public Date getResult_endTime() {
        return result_endTime;
    }

    public void setResult_endTime(Date result_endTime) {
        this.result_endTime = result_endTime;
    }

    @NotNull
    public String getResult_activityClassifyKey() {
        return result_activityClassifyKey;
    }

    public void setResult_activityClassifyKey(String result_activityClassifyKey) {
        this.result_activityClassifyKey = result_activityClassifyKey;
    }

    @NotNull
    public String getResult_activityTypeCode() {
        return result_activityTypeCode;
    }

    public void setResult_activityTypeCode(String result_activityTypeCode) {
        this.result_activityTypeCode = result_activityTypeCode;
    }

    @NotNull
    public Boolean getResult_isDisplay() {
        return result_isDisplay;
    }

    public void setResult_isDisplay(Boolean result_isDisplay) {
        this.result_isDisplay = result_isDisplay;
    }

    @NotNull
    @Range
    public Integer getResult_orderNum() {
        return result_orderNum;
    }

    public void setResult_orderNum(Integer result_orderNum) {
        this.result_orderNum = result_orderNum;
    }

    @NotNull
    public String getResult_status() {
        return result_status;
    }

    public void setResult_status(String result_status) {
        this.result_status = result_status;
    }

    @NotNull
    @Range
    public Integer getActivityRule_limitNumber() {
        return activityRule_limitNumber;
    }

    public void setActivityRule_limitNumber(Integer activityRule_limitNumber) {
        this.activityRule_limitNumber = activityRule_limitNumber;
    }

    @NotNull
    public String getActivityRule_effectiveTime() {
        return activityRule_effectiveTime;
    }

    public void setActivityRule_effectiveTime(String activityRule_effectiveTime) {
        this.activityRule_effectiveTime = activityRule_effectiveTime;
    }

    @NotNull
    public Boolean getActivityRule_isDemandFirst() {
        return activityRule_isDemandFirst;
    }

    public void setActivityRule_isDemandFirst(Boolean activityRule_isDemandFirst) {
        this.activityRule_isDemandFirst = activityRule_isDemandFirst;
    }

    @NotNull
    public String getActivityRule_gameType() {
        return activityRule_gameType;
    }

    public void setActivityRule_gameType(String activityRule_gameType) {
        this.activityRule_gameType = activityRule_gameType;
    }

    @NotNull
    public Boolean getActivityRule_isExclusive() {
        return activityRule_isExclusive;
    }

    public void setActivityRule_isExclusive(Boolean activityRule_isExclusive) {
        this.activityRule_isExclusive = activityRule_isExclusive;
    }

    public String getActivityRule_exclusiveActivity() {
        return activityRule_exclusiveActivity;
    }

    public void setActivityRule_exclusiveActivity(String activityRule_exclusiveActivity) {
        this.activityRule_exclusiveActivity = activityRule_exclusiveActivity;
    }

    @NotNull
    public String getActivityWayRelation_preferentialForm() {
        return activityWayRelation_preferentialForm;
    }

    public void setActivityWayRelation_preferentialForm(String activityWayRelation_preferentialForm) {
        this.activityWayRelation_preferentialForm = activityWayRelation_preferentialForm;
    }

    @NotNull
    @Range
    public Double getActivityWayRelation_preferentialValue() {
        return activityWayRelation_preferentialValue;
    }

    public void setActivityWayRelation_preferentialValue(Double activityWayRelation_preferentialValue) {
        this.activityWayRelation_preferentialValue = activityWayRelation_preferentialValue;
    }

    @NotNull
    @Range
    public Double getActivityWayRelation_preferentialAudit() {
        return activityWayRelation_preferentialAudit;
    }

    public void setActivityWayRelation_preferentialAudit(Double activityWayRelation_preferentialAudit) {
        this.activityWayRelation_preferentialAudit = activityWayRelation_preferentialAudit;
    }

    @NotNull
    @Range(min = 1)
    public Integer getActivityWayRelation_orderColumn() {
        return activityWayRelation_orderColumn;
    }

    public void setActivityWayRelation_orderColumn(Integer activityWayRelation_orderColumn) {
        this.activityWayRelation_orderColumn = activityWayRelation_orderColumn;
    }

    @NotNull
    public Boolean getActivityWayRelation_isArticle() {
        return activityWayRelation_isArticle;
    }

    public void setActivityWayRelation_isArticle(Boolean activityWayRelation_isArticle) {
        this.activityWayRelation_isArticle = activityWayRelation_isArticle;
    }

    @NotNull
    public String getActivityWayRelation_article() {
        return activityWayRelation_article;
    }

    public void setActivityWayRelation_article(String activityWayRelation_article) {
        this.activityWayRelation_article = activityWayRelation_article;
    }

    @NotNull
    @Range
    public Double getActivityWayRelation_triggerValue() {
        return activityWayRelation_triggerValue;
    }

    public void setActivityWayRelation_triggerValue(Double activityWayRelation_triggerValue) {
        this.activityWayRelation_triggerValue = activityWayRelation_triggerValue;
    }

    @NotNull
    @Range(max = 1)
    public Double getActivityWayRelation_preferentialRatio() {
        return activityWayRelation_preferentialRatio;
    }

    public void setActivityWayRelation_preferentialRatio(Double activityWayRelation_preferentialRatio) {
        this.activityWayRelation_preferentialRatio = activityWayRelation_preferentialRatio;
    }

}