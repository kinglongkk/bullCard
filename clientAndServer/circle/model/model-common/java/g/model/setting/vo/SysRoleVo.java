package g.model.setting.vo;

import g.model.setting.po.SysRole;
import g.model.setting.so.SysRoleSo;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;


/**
 * @author: tom
 * @date: 15-7-10
 */
public class SysRoleVo extends BaseObjectVo<SysRole, SysRoleSo, SysRoleVo.SysRoleQuery> {

    private static final long serialVersionUID = -1189684886568722263L;

    public SysRoleVo() {
    }

    protected static class SysRoleQuery extends AbstractQuery<SysRoleSo> {
        private static final long serialVersionUID = -3218615596114712080L;

        protected SysRoleQuery() {
        }

        public Criteria getCriteria() {
            Criteria criteria = new Criteria();
            if (StringTool.isNotEmpty(this.searchObject.getName())) {
                criteria.addAnd(new Criterion("name", Operator.ILIKE, ((SysRoleSo)this.searchObject).getName()));
            }
            return criteria.addAnd(new Criterion("builtIn", Operator.EQ,"f"));
        }
    }
}