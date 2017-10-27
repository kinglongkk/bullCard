package g.model.agent.vo;


import g.model.admin.po.BetLimit;
import g.model.admin.po.VUserBetLimit;
import g.model.admin.po.VUserBetLimitMultiple;
import g.model.admin.vo.BetLimitListVo;
import g.model.agent.po.VAgentManage;
import g.model.agent.so.VAgentManageSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import org.soul.model.msg.notice.po.NoticeContactWay;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.sys.po.SysParam;
import g.model.admin.po.BetLimitMultiple;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * 代理管理值对象
 *
 * @author tom
 * @time 2015-9-9 16:57:12
 */
//region your codes 1
public class VAgentManageVo extends BaseObjectVo<VAgentManage, VAgentManageSo, VAgentManageVo.VUserAgentManageQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -8945981572177974815L;
    //endregion your codes 5

    /**
     *  代理（总代）管理查询逻辑
     */
    public static class VUserAgentManageQuery extends AbstractQuery<VAgentManageSo> {

        //region your codes 6
        private static final long serialVersionUID = -7438977803060998664L;
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

    private SysUser sysUser;

    private NoticeContactWay email;

    private NoticeContactWay mobilePhone;

    private NoticeContactWay skype;

    private String confirmPassword;

    private Map<String, Serializable> sex;

    private Map<String, Serializable> country;

    private Map<String, Serializable> nation;

    private Map<String, Serializable> userType;

    private BetLimitListVo betLimits;

    private List<BetLimit> betLimitList;

    private List<BetLimitMultiple> betLimitMultipleList;

    private List<VUserBetLimit> userBetLimit;

    private SysParam sysParam;

    private List<VUserBetLimitMultiple> vUserBetLimitMultipleList;

    private SysUser operatorUser;

    private Map<String, Serializable> status;

    private Map<Integer, List<VUserBetLimitMultiple>> betNumKeyMap;

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public NoticeContactWay getEmail() {
        return email;
    }

    public void setEmail(NoticeContactWay email) {
        this.email = email;
    }

    public NoticeContactWay getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(NoticeContactWay mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Map<String, Serializable> getSex() {
        return sex;
    }

    public void setSex(Map<String, Serializable> sex) {
        this.sex = sex;
    }

    public Map<String, Serializable> getCountry() {
        return country;
    }

    public void setCountry(Map<String, Serializable> country) {
        this.country = country;
    }

    public Map<String, Serializable> getNation() {
        return nation;
    }

    public void setNation(Map<String, Serializable> nation) {
        this.nation = nation;
    }

    public Map<String, Serializable> getUserType() {
        return userType;
    }

    public void setUserType(Map<String, Serializable> userType) {
        this.userType = userType;
    }

    public Map<String, Serializable> getStatus() {
        return status;
    }

    public void setStatus(Map<String, Serializable> status) {
        this.status = status;
    }

    public NoticeContactWay getSkype() {
        return skype;
    }

    public void setSkype(NoticeContactWay skype) {
        this.skype = skype;
    }

    public BetLimitListVo getBetLimits() {
        return betLimits;
    }

    public void setBetLimits(BetLimitListVo betLimits) {
        this.betLimits = betLimits;
    }

    public List<BetLimit> getBetLimitList() {
        return betLimitList;
    }

    public void setBetLimitList(List<BetLimit> betLimitList) {
        this.betLimitList = betLimitList;
    }

    public SysParam getSysParam() {
        return sysParam;
    }

    public void setSysParam(SysParam sysParam) {
        this.sysParam = sysParam;
    }

    public List<VUserBetLimit> getUserBetLimit() {
        return userBetLimit;
    }

    public void setUserBetLimit(List<VUserBetLimit> userBetLimit) {
        this.userBetLimit = userBetLimit;
    }

    public List<BetLimitMultiple> getBetLimitMultipleList() {
        return betLimitMultipleList;
    }

    public void setBetLimitMultipleList(List<BetLimitMultiple> betLimitMultipleList) {
        this.betLimitMultipleList = betLimitMultipleList;
    }

    public List<VUserBetLimitMultiple> getvUserBetLimitMultipleList() {
        return vUserBetLimitMultipleList;
    }

    public void setvUserBetLimitMultipleList(List<VUserBetLimitMultiple> vUserBetLimitMultipleList) {
        this.vUserBetLimitMultipleList = vUserBetLimitMultipleList;
    }

    public SysUser getOperatorUser() {
        return operatorUser;
    }

    public void setOperatorUser(SysUser operatorUser) {
        this.operatorUser = operatorUser;
    }

    public Map<Integer, List<VUserBetLimitMultiple>> getBetNumKeyMap() {
        return betNumKeyMap;
    }

    public void setBetNumKeyMap(Map<Integer, List<VUserBetLimitMultiple>> betNumKeyMap) {
        this.betNumKeyMap = betNumKeyMap;
    }
    //endregion your codes 4

}