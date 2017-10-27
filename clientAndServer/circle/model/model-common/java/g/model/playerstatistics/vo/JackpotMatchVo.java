package g.model.playerstatistics.vo;

import g.model.playerstatistics.po.JackpotMatch;
import g.model.playerstatistics.so.JackpotMatchSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;

/**
 * Created by lenovo on 2017/3/23.
 */
public class JackpotMatchVo extends BaseObjectVo<JackpotMatch, JackpotMatchSo,JackpotMatchListVo.JackpotMatchQuery> {

    private static final long serialVersionUID = -4559998057652374825L;

    public static class JackpotMatchQuery extends AbstractQuery<JackpotMatchSo> {

        //region your codes 6
        private static final long serialVersionUID = -2344554902629716362L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            return null;
        }
    }
}

