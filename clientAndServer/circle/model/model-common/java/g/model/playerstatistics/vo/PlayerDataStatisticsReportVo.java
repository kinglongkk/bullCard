package g.model.playerstatistics.vo;

import g.model.playerstatistics.po.PlayerDataStatisticsReport;
import g.model.playerstatistics.so.PlayerDataStatisticsReportSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;

/**
 * Created by lenovo on 2017/3/28.
 */
public class PlayerDataStatisticsReportVo extends BaseObjectVo<PlayerDataStatisticsReport, PlayerDataStatisticsReportSo,PlayerDataStatisticsReportListVo.PlayerDataStatisticsReportQuery> {

    private static final long serialVersionUID = -4559992057652374825L;

    public static class PlayerDataStatisticsReportQuery extends AbstractQuery<PlayerDataStatisticsReportSo> {

        //region your codes 6
        private static final long serialVersionUID = -2374554902629716362L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            return null;
        }
    }
}
