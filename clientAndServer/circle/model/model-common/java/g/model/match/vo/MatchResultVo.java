package g.model.match.vo;

import g.model.match.po.MatchResultInfo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import org.soul.model.security.privilege.po.SysUser;
import g.model.match.po.MatchResult;
import g.model.match.so.MatchResultSo;


/**
 * 比赛结果表值对象
 *
 * @author longer
 * @time May 17, 2016 4:38:24 PM
 */
//region your codes 1
public class MatchResultVo extends BaseObjectVo<MatchResult, MatchResultSo, MatchResultVo.MatchResultQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 7958774560495703354L;
    //endregion your codes 5

    /**
     *  比赛结果表查询逻辑
     */
    public static class MatchResultQuery extends AbstractQuery<MatchResultSo> {

        //region your codes 6
        private static final long serialVersionUID = -7014304969994426819L;
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
    private MatchResultInfo resultInfo;

    private SysUser operator;

    public MatchResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(MatchResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public SysUser getOperator() {
        return operator;
    }

    public void setOperator(SysUser operator) {
        this.operator = operator;
    }
    //endregion your codes 4

}