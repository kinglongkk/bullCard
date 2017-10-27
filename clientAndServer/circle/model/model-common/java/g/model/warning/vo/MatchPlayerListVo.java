package g.model.warning.vo;

import g.model.warning.po.MatchPlayer;
import g.model.warning.so.MatchPlayerSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;

/**
 * Created by lenovo on 2017/3/1.
 */
public class MatchPlayerListVo  extends BaseListVo<MatchPlayer, MatchPlayerSo,MatchPlayerListVo.MatchPlayerQuery> {

    private static final long serialVersionUID = 5092252426327323174L;

    public static class MatchPlayerQuery extends AbstractQuery<MatchPlayerSo> {

        private static final long serialVersionUID = 7869525192573075255L;

        @Override
        public Criteria getCriteria() {
            return null;
        }

    }


}

