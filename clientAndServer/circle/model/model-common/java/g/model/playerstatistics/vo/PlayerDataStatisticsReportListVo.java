package g.model.playerstatistics.vo;

import g.model.playerstatistics.po.PlayerDataStatisticsReport;
import g.model.playerstatistics.so.PlayerDataStatisticsReportSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;

/**
 * Created by lenovo on 2017/3/28.
 */
public class PlayerDataStatisticsReportListVo extends BaseListVo<PlayerDataStatisticsReport, PlayerDataStatisticsReportSo,PlayerDataStatisticsReportListVo.PlayerDataStatisticsReportQuery> {

    private static final long serialVersionUID = 5092252426327323174L;

    public static class PlayerDataStatisticsReportQuery extends AbstractQuery<PlayerDataStatisticsReportSo> {

        private static final long serialVersionUID = 7869525192573075255L;

        @Override
        public Criteria getCriteria() {
            return null;
        }

    }


}
