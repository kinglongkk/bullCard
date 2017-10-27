package g.web.admin.report;

/**
 * Created by tom on 16-8-1.
 */

import g.model.SiteParamEnum;
import g.model.common.Const;
import g.service.report.IReportCqsscService;
import g.web.common.cache.ParamTool;
import org.aspectj.lang.JoinPoint;
import org.soul.commons.init.context.CommonContext;
import org.soul.commons.init.context.ContextParam;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.model.sys.po.SysParam;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: tom
 * @date: 16-8-1
 */
public class ReportServiceAspect {
    private final static Log log = LogFactory.getLog(ReportServiceAspect.class);

    @Autowired
    private IReportCqsscService reportCqsscService;

    public void after(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        Map<String, Object> map = new HashMap<>();
        ContextParam contextParam = new ContextParam();
        contextParam.setSiteId(Const.Default_Site_Id);
        CommonContext.set(contextParam);
        SysParam sysParam = ParamTool.getSysParam(SiteParamEnum.RAKEBACK_POINT);
        map.put("rakebackPoint", Float.parseFloat(sysParam.getParamValue()));
        map.put("matchId", args[0]);
        //todo mark 现在统计是按彩期整期统计生成报表，如果后面数据量大，该统计方法较慢，可再按代理统计，就是每个代理每期的报表单独统计。
        Integer integer = reportCqsscService.genReport(map);

    }
}
