package g.model.agent.vo;

import g.model.agent.po.VSubAccount;
import g.model.agent.so.VSubAccountSo;
import org.soul.commons.collections.ListTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Direction;
import org.soul.commons.query.sort.Sort;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import org.soul.model.security.privilege.po.SysUserStatus;
import g.model.SubSysCodeEnum;

import java.util.List;


/**
 * 子账户视图列表页值对象
 *
 * @author jeff
 * @time 2015-10-20 10:49:13
 */
//region your codes 1
public class VSubAccountListVo extends BaseListVo<VSubAccount, VSubAccountSo, VSubAccountListVo.VSubAccountQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -5791599967380374271L;
    private List<Integer> ids;
    private List<String> userStatus;
    private String resourceKey;
    private String subSysCode;
//    private
    //endregion your codes 5

    /**
     *  子账户视图列表查询逻辑
     */
    public static class VSubAccountQuery extends AbstractQuery<VSubAccountSo> {

        //region your codes 6
        private static final long serialVersionUID = -7788662407572970278L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2

            Criteria userType = Criteria.add(VSubAccount.PROP_USER_TYPE, Operator.EQ, searchObject.getUserType());
            Criteria username = Criteria.add(VSubAccount.PROP_USERNAME, Operator.ILIKE, searchObject.getUsername());
            Criteria status = Criteria.add(VSubAccount.PROP_STATUS, Operator.EQ, searchObject.getStatus());
            Criteria owner = Criteria.add(VSubAccount.PROP_OWNER_ID, Operator.EQ, searchObject.getOwnerId());
            return Criteria.and(username, userType, status, owner);
            //endregion your codes 2
        }


        //region your codes 3

        @Override
        public Sort getDefaultSort() {
            return Sort.add(VSubAccount.PROP_CREATE_TIME, Direction.DESC);
        }


        //endregion your codes 3
    }

    //region your codes 4

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public List<String> getUserStatus() {
        return ListTool.newArrayList(SysUserStatus.NORMAL.getCode(), SysUserStatus.DISABLED.getCode());
    }

    public void setUserStatus(List<String> userStatus) {
        this.userStatus = userStatus;
    }

    public String getResourceKey() {
        String trans = null;
        SubSysCodeEnum subSysCodeEnum = SubSysCodeEnum.enumOf(getSubSysCode());
        switch (subSysCodeEnum){
            case ADMIN:
                trans =  "系统设置";
                break;
            case AGENT:
                trans = "账号管理";
                break;
        }
        return trans;
    }

    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public String getSubSysCode() {
        return subSysCode;
    }

    public void setSubSysCode(String subSysCode) {
        this.subSysCode = subSysCode;
    }

    //endregion your codes 4

}