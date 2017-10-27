package g.model.playerstatistics.vo;

import g.model.playerstatistics.po.JackpotPlayer;
import g.model.playerstatistics.so.JackpotPlayerSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;

/**
 * Created by lenovo on 2017/3/24.
 */
public class JackpotPlayerVo extends BaseObjectVo<JackpotPlayer, JackpotPlayerSo,JackpotPlayerListVo.JackpotPlayerQuery> {

    private static final long serialVersionUID = -4559998057652374325L;

    public static class JackpotPlayerQuery extends AbstractQuery<JackpotPlayerSo> {

        //region your codes 6
        private static final long serialVersionUID = -2344554902629716372L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            return null;
        }
    }
}
