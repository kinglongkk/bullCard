package g.model.admin.vo;

import g.model.admin.po.Remark;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.admin.so.RemarkSo;


/**
 * 值对象
 *
 * Created by cheery using soul-code-generator on 2015-6-30 18:30:06
 */
//region your codes 1
public class RemarkVo extends BaseObjectVo<Remark, RemarkSo, RemarkVo.PlayerRemarkQuery> {
//endregion your codes 1

    private static final long serialVersionUID = 3375754165488847872L;

    private String groupCode;

    private Integer onLineId;

    /**
     *  查询逻辑
     */
    public static class PlayerRemarkQuery extends AbstractQuery<RemarkSo> {

        private static final long serialVersionUID = -9223372036854175818L;

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria = Criteria.add(Remark.PROP_ENTITY_USER_ID, Operator.EQ, this.searchObject.getEntityUserId())
                    .addAnd(Remark.PROP_ENTITY_ID, Operator.EQ,this.searchObject.getEntityId())
                    .addAnd(Remark.PROP_REMARK_TYPE, Operator.EQ,this.searchObject.getRemarkType());
            return criteria;
            //endregion your codes 2
        }

        //region your codes 3

        //endregion your codes 3

    }

    //region your codes 4

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public Integer getOnLineId() {
        return onLineId;
    }

    public void setOnLineId(Integer onLineId) {
        this.onLineId = onLineId;
    }
//endregion your codes 4

}