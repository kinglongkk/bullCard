package g.model.report.vo;

import g.model.SubSysCodeEnum;
import org.soul.commons.lang.DateTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Direction;
import org.soul.commons.query.sort.Sort;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.report.po.ReportCqssc;
import g.model.report.so.ReportCqsscSo;

/**
 * 列表页值对象
 *
 * @author tom
 * @time 2016-8-1 15:34:21
 */
//region your codes 1
public class ReportCqsscListVo extends BaseListVo<ReportCqssc, ReportCqsscSo, ReportCqsscListVo.ReportCqsscQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -7212568798806946309L;
    //endregion your codes 5

    /**
     *  列表查询逻辑
     */
    public static class ReportCqsscQuery extends AbstractQuery<ReportCqsscSo> {

        //region your codes 6
        private static final long serialVersionUID = 545089199628662554L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2

            //判断是否为精确到天的时间查询
            //情况一 点击分组查询查看该天的详情
            if (this.searchObject.getEndTime() == null && this.searchObject.getStartTime() != null){

                this.searchObject.setEndTime(DateTool.addDays(this.searchObject.getStartTime(),1));

            //情况二 在每期详情页面按时间搜索
            }else if (this.searchObject.getEndTime() != null && this.searchObject.getStartTime().equals(this.searchObject.getEndTime())){

                this.searchObject.setEndTime(DateTool.addDays(this.searchObject.getStartTime(),1));
            }

            Criteria criteria = Criteria.and(
                    Criteria.add(ReportCqssc.PROP_USER_ID, Operator.EQ, this.searchObject.getUserId()),
                    Criteria.add(ReportCqssc.PROP_BEGIN_TIME, Operator.GE, this.searchObject.getStartTime()),
                    Criteria.add(ReportCqssc.PROP_BEGIN_TIME, Operator.LE, this.searchObject.getEndTime()),
                    Criteria.add(ReportCqssc.PROP_MATCH_CODE, Operator.LIKE, this.searchObject.getMatchCode())
            );

            //判断用户身份
            if (!this.searchObject.getUserType().equals(SubSysCodeEnum.ADMIN.getCode())){

                criteria.addAnd(ReportCqssc.PROP_USERNAME, Operator.EQ, this.searchObject.getUsername());
            }else {
                criteria.addAnd(ReportCqssc.PROP_USERNAME, Operator.LIKE, this.searchObject.getUsername());
            }

            criteria.addAnd(ReportCqssc.PROP_IS_DELETED, Operator.EQ, false);

            return criteria;
            //endregion your codes 2
        }

        @Override
        public Sort getDefaultSort() {
            return Sort.add(ReportCqssc.PROP_MATCH_CODE, Direction.DESC);
        }

        //region your codes 3

        //endregion your codes 3

    }

    /**
     * 统计页面各类总值 总场次
     */
    private Integer totalNumber;

    /**
     * 统计页面各类总值 总投注额
     */
    private Double totalSingleAmount;

    /**
     * 统计页面各类总值 总有效投注额
     */
    private Double totalEffectiveAmount;

    /**
     * 统计页面各类总值 总输赢数
     */
    private Double totalProfitAmount;

    /**
     * 统计页面各类总值 总返点数
     */
    private Double totalRakebackAmount;


    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer value) {
        this.totalNumber = value;
    }

    public Double getTotalSingleAmount() {
        return totalSingleAmount;
    }

    public void setTotalSingleAmount(Double value) {
        this.totalSingleAmount = value;
    }

    public Double getTotalEffectiveAmount() { return totalEffectiveAmount; }

    public void setTotalEffectiveAmount(Double value) { this.totalEffectiveAmount = value; }

    public Double getTotalProfitAmount() { return totalProfitAmount; }

    public void setTotalProfitAmount(Double value) { this.totalProfitAmount = value; }

    public Double getTotalRakebackAmount() { return totalRakebackAmount; }

    public void setTotalRakebackAmount(Double value) {this.totalRakebackAmount = value; }
    //region your codes 4

    //endregion your codes 4

}