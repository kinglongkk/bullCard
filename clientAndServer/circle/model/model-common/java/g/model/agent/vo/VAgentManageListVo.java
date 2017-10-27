package g.model.agent.vo;


import org.soul.commons.bean.Pair;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.locale.LocaleTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Direction;
import org.soul.commons.query.sort.Sort;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import org.soul.model.security.privilege.po.SysUserStatus;
import g.model.agent.po.VAgentManage;
import g.model.agent.so.VAgentManageSo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 代理（总代）管理列表页值对象
 *
 * @author tom
 * @time 2015-9-9 16:57:12
 */
//region your codes 1
public class VAgentManageListVo extends BaseListVo<VAgentManage, VAgentManageSo, VAgentManageListVo.VAgentManageQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -1837131336725219140L;
    //endregion your codes 5

    /**
     * 代理（总代）管理列表查询逻辑
     */
    public static class VAgentManageQuery extends AbstractQuery<VAgentManageSo> {

        private static final long serialVersionUID = -4041197335856372129L;

        @Override
        public Criteria getCriteria() {
            /*if (StringTool.isNotEmpty(this.searchObject.getMail()))
                this.searchObject.setMail(CryptoTool.aesEncrypt(this.searchObject.getMail(), CryptoKey.KEY_NOTICE_CONTACT_WAY));

            if (StringTool.isNotEmpty(this.searchObject.getMobilePhone()))
                this.searchObject.setMobilePhone(CryptoTool.aesEncrypt(this.searchObject.getMobilePhone(), CryptoKey.KEY_NOTICE_CONTACT_WAY));*/

            Criteria criteria = Criteria.or(Criteria.add(VAgentManage.PROP_USERNAME, Operator.ILIKE, this.searchObject.getUsername()),
                    Criteria.add(VAgentManage.PROP_MOBILE_PHONE, Operator.EQ, this.searchObject.getMobilePhone()),
                    Criteria.add(VAgentManage.PROP_MAIL, Operator.EQ, this.searchObject.getMail()),
                    Criteria.add(VAgentManage.PROP_REAL_NAME, Operator.ILIKE, this.searchObject.getRealName()));

            String status = this.searchObject.getStatus();
            if (StringTool.isNotBlank(status)) {
                if (SysUserStatus.LOCKED.getCode().equals(status)) {
                    Criteria criteria3 = Criteria.add(VAgentManage.PROP_FREEZE_START_TIME, Operator.LE, new Date());
                    Criteria criteria4 = Criteria.add(VAgentManage.PROP_FREEZE_END_TIME, Operator.GE, new Date());
                    return Criteria.and(criteria, criteria3, criteria4);
                } else {
                    Criteria criteria3 = Criteria.add(VAgentManage.PROP_STATUS, Operator.EQ, status);
                    return Criteria.and(criteria, criteria3);
                }
            }

            return criteria;
        }

        @Override
        public Sort getDefaultSort() {
            return Sort.add(VAgentManage.PROP_CREATE_TIME, Direction.DESC);
        }

        //endregion your codes 3
    }

    //region your codes 4

    public List<Pair> searchList() {
        String vAgentManager = VAgentManage.class.getSimpleName();
        List<Pair> searchList = new ArrayList<>();
        searchList.add(new Pair("search." + VAgentManage.PROP_USERNAME, LocaleTool.tranView("column", vAgentManager + "." + VAgentManage.PROP_USERNAME)));
        searchList.add(new Pair("search." + VAgentManage.PROP_MOBILE_PHONE, LocaleTool.tranView("column", vAgentManager + "." + VAgentManage.PROP_MOBILE_PHONE)));
        searchList.add(new Pair("search."+VAgentManage.PROP_MAIL, LocaleTool.tranView("column", vAgentManager + "." + VAgentManage.PROP_MAIL)));
        searchList.add(new Pair("search." + VAgentManage.PROP_REAL_NAME, LocaleTool.tranView("column", vAgentManager + "." + VAgentManage.PROP_REAL_NAME)));
        return searchList;
    }
    //endregion your codes 4

}