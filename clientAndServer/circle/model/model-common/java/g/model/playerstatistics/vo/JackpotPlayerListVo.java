package g.model.playerstatistics.vo;

import g.model.playerstatistics.po.JackpotPlayer;
import g.model.playerstatistics.so.JackpotPlayerSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;

/**
 * Created by lenovo on 2017/3/24.
 */
public class JackpotPlayerListVo  extends BaseListVo<JackpotPlayer, JackpotPlayerSo,JackpotPlayerListVo.JackpotPlayerQuery> {

    private static final long serialVersionUID = 5092252466327323174L;

    public static class JackpotPlayerQuery extends AbstractQuery<JackpotPlayerSo> {

        private static final long serialVersionUID = 7869525198573075255L;

        @Override
        public Criteria getCriteria() {
            return null;
        }

    }

}
