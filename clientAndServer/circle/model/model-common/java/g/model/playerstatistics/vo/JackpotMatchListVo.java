package g.model.playerstatistics.vo;

import g.model.playerstatistics.po.JackpotMatch;
import g.model.playerstatistics.so.JackpotMatchSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;

/**
 * Created by lenovo on 2017/3/23.
 */
public class JackpotMatchListVo extends BaseListVo<JackpotMatch, JackpotMatchSo,JackpotMatchListVo.JackpotMatchQuery> {

    private static final long serialVersionUID = 5092252426327323174L;

    public static class JackpotMatchQuery extends AbstractQuery<JackpotMatchSo> {

        private static final long serialVersionUID = 7869525192573075255L;

        @Override
        public Criteria getCriteria() {
            return null;
        }

    }


}

