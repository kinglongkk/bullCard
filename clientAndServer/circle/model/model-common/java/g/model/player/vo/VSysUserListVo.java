package g.model.player.vo;

import g.model.player.po.VSysUser;
import g.model.player.so.VSysUserSo;
import org.apache.commons.lang3.StringUtils;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;

/**
 * 玩家交易表列表页值对象
 *
 * @author cheery
 * @time 2015-8-17 10:20:34
 */
public class VSysUserListVo extends BaseListVo<VSysUser, VSysUserSo, VSysUserListVo.VSysUserQuery> {

    private static final long serialVersionUID = -5885743353797133959L;
    /**
     *  玩家交易表列表查询逻辑
     */
    public static class VSysUserQuery extends AbstractQuery<VSysUserSo> {

        private static final long serialVersionUID = -4576154452239636167L;

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria = Criteria.add(VSysUser.PROP_SUBSYS_CODE, Operator.EQ,this.searchObject.getSubsysCode());
            criteria.addAnd(VSysUser.PROP_STATUS,Operator.EQ,this.searchObject.getStatus());
            criteria.addAnd(VSysUser.PROP_USERNAME,Operator.ILIKE,this.searchObject.getUsername());
            criteria.addAnd(VSysUser.PROP_OWNER_ID,Operator.EQ,this.searchObject.getOwnerId());
            criteria.addAnd(VSysUser.PROP_USER_TYPE,Operator.EQ, this.searchObject.getUserType());
            criteria.addAnd(VSysUser.PROP_ID, Operator.EQ, this.searchObject.getId());
            if(StringUtils.isNotEmpty(this.searchObject.getNickname())){
                criteria.addAnd(VSysUser.PROP_NICKNAME,Operator.ILIKE, this.searchObject.getNickname());
            }
            return criteria;
            //endregion your codes 2
        }

    }
        //region your codes 3

    //endregion your codes 4

}