package g.model.player.vo;

import g.model.player.so.VSysUserSo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.player.po.VSysUser;


/**
 * 玩家交易表值对象
 * <p/>
 * Created by cheery using soul-code-generator on 2015-6-23 11:41:43
 */
public class VSysUserVo extends BaseObjectVo<VSysUser, VSysUserSo, VSysUserVo.VSysUserQuery> {

    private static final long serialVersionUID = 3191286724751752192L;


    /**
     * 玩家交易表查询逻辑
     */
    public static class VSysUserQuery extends AbstractQuery<VSysUserSo> {

        private static final long serialVersionUID = -9223372036854775808L;

        @Override
        public Criteria getCriteria() {

            Criteria criteria = Criteria.and(
                    Criteria.add(VSysUser.PROP_ID, Operator.EQ, this.searchObject.getId())
            );
            return criteria;
        }

    }


}