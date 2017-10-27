package g.model.agent.vo;

import g.model.admin.po.*;
import g.model.admin.vo.VUserGroupBetLimitMultipleListVo;
import g.model.agent.po.VUserPlayerGroup;
import g.model.agent.so.VUserPlayerGroupSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.sys.po.SysParam;

import java.util.List;
import java.util.Map;


/**
 * 值对象
 *
 * @author orange
 * @time 2016-4-27 15:56:13
 */
//region your codes 1
public class VUserPlayerGroupVo extends BaseObjectVo<VUserPlayerGroup, VUserPlayerGroupSo, VUserPlayerGroupVo.VUserPlayerGroupQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 6628480587145044486L;
    //endregion your codes 5

    /**
     *  查询逻辑
     */
    public static class VUserPlayerGroupQuery extends AbstractQuery<VUserPlayerGroupSo> {

        //region your codes 6
        private static final long serialVersionUID = 2961080999883988838L;
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

    private VUserGroupBetLimitMultipleListVo vUserGroupBetLimitMultipleListVo;

    private List<VUserBetLimit> userBetLimit;

    private List<VUserGroupBetLimit> vUserGroupBetLimitList;

    private List<BetLimit> betLimitList;

    private SysParam sysParam;

    private Map<Integer, List<VUserBetLimitMultiple>> betNumKeyMap;

    private List<BetLimitMultiple> betLimitMultipleList;

    private SysUser operatorUser;

    public Map<Integer, List<VUserBetLimitMultiple>> getBetNumKeyMap() {
        return betNumKeyMap;
    }

    public void setBetNumKeyMap(Map<Integer, List<VUserBetLimitMultiple>> betNumKeyMap) {
        this.betNumKeyMap = betNumKeyMap;
    }

    public SysParam getSysParam() {
        return sysParam;
    }

    public void setSysParam(SysParam sysParam) {
        this.sysParam = sysParam;
    }

    public VUserGroupBetLimitMultipleListVo getvUserGroupBetLimitMultipleListVo() {
        return vUserGroupBetLimitMultipleListVo;
    }

    public void setvUserGroupBetLimitMultipleListVo(VUserGroupBetLimitMultipleListVo vUserGroupBetLimitMultipleListVo) {
        this.vUserGroupBetLimitMultipleListVo = vUserGroupBetLimitMultipleListVo;
    }

    public List<VUserBetLimit> getUserBetLimit() {
        return userBetLimit;
    }

    public void setUserBetLimit(List<VUserBetLimit> userBetLimit) {
        this.userBetLimit = userBetLimit;
    }

    public List<VUserGroupBetLimit> getvUserGroupBetLimitList() {
        return vUserGroupBetLimitList;
    }

    public void setvUserGroupBetLimitList(List<VUserGroupBetLimit> vUserGroupBetLimitList) {
        this.vUserGroupBetLimitList = vUserGroupBetLimitList;
    }

    public List<BetLimit> getBetLimitList() {
        return betLimitList;
    }

    public void setBetLimitList(List<BetLimit> betLimitList) {
        this.betLimitList = betLimitList;
    }

    public List<BetLimitMultiple> getBetLimitMultipleList() {
        return betLimitMultipleList;
    }

    public void setBetLimitMultipleList(List<BetLimitMultiple> betLimitMultipleList) {
        this.betLimitMultipleList = betLimitMultipleList;
    }

    public SysUser getOperatorUser() {
        return operatorUser;
    }

    public void setOperatorUser(SysUser operatorUser) {
        this.operatorUser = operatorUser;
    }
    //endregion your codes 4

}