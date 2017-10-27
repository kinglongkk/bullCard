package g.model.admin.vo;

import org.soul.model.msg.notice.vo.NoticeLocaleTmpl;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.soul.model.sys.po.SysDict;
import g.model.enums.FreezeTimeEnum;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jeff on 15-12-18.
 * 
 */
public class AccountVo extends SysUserVo {

    /*模板信息*/
    private List<NoticeLocaleTmpl> noticeLocaleTmpls;

    /*冻结时间字典*/
    private Map<String,SysDict> freezeTime;

    /*是否冻结*/
    private boolean isFreeze;

    /*当前时间*/
    private Date now;
    /*选择的模板code*/
    private String groupCode;
    /*当前用户是否登录*/
    private Boolean isLogin;
    /*选中的冻结时间*/
    private String chooseFreezeTime;
    /*备注*/
    private String remark;
    /**
     * 备注标题
     */
    private String remarkTitle;
    private String operator;

    private String freezeTimeForerve = FreezeTimeEnum.FORERVE.getCode();
    //region your codes 2
    /**
     * 账号类型
     * 区分账号停用、冻结等操作账号类型
     * player   玩家
     * agent    代理
     * topagent 总代
     */
    public static final String TYPE_PLAYER = "player";
    public static final String TYPE_AGENT = "agent";
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    //region your codes 2


    public String getChooseFreezeTime() {

        return chooseFreezeTime;
    }

    public void setChooseFreezeTime(String chooseFreezeTime) {
        this.chooseFreezeTime = chooseFreezeTime;
    }

    public Boolean getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Boolean isLogin) {
        this.isLogin = isLogin;
    }

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    public boolean isFreeze() {
        return isFreeze;
    }

    public void setIsFreeze(boolean isFreeze) {
        this.isFreeze = isFreeze;
    }

    public Map<String, SysDict> getFreezeTime() {
        return freezeTime;
    }

    public void setFreezeTime(Map<String, SysDict> freezeTime) {
        this.freezeTime = freezeTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<NoticeLocaleTmpl> getNoticeLocaleTmpls() {
        return noticeLocaleTmpls;
    }

    public void setNoticeLocaleTmpls(List<NoticeLocaleTmpl> noticeLocaleTmpls) {
        this.noticeLocaleTmpls = noticeLocaleTmpls;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getFreezeTimeForerve() {
        return freezeTimeForerve;
    }

    public String getRemarkTitle() {
        return remarkTitle;
    }

    public void setRemarkTitle(String remarkTitle) {
        this.remarkTitle = remarkTitle;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
