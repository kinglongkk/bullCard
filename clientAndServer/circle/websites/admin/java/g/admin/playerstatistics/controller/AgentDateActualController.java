package g.admin.playerstatistics.controller;

import g.admin.playerstatistics.form.AgentDateActualForm;
import g.admin.playerstatistics.form.AgentDateActualSearchForm;
import g.common.tool.DateTimeTool;
import g.model.playerstatistics.po.AgentDateActual;
import g.model.playerstatistics.vo.AgentDateActualListVo;
import g.model.playerstatistics.vo.AgentDateActualVo;
import g.service.playerstatistics.IAgentDateActualService;
import org.apache.commons.lang3.StringUtils;
import org.soul.web.controller.BaseCrudController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * 代理商每日实时统计表控制器
 *
 * @author lenovo
 * @time 2017-1-21 9:43:02
 */
@Controller
//region your codes 1
@RequestMapping("/agentDateActual")
public class AgentDateActualController extends BaseCrudController<IAgentDateActualService, AgentDateActualListVo, AgentDateActualVo, AgentDateActualSearchForm, AgentDateActualForm, AgentDateActual, Integer> {

    @Override
    protected String getViewBasePath() {
        return "/playerstatistics/summery/";
    }

    /**
     * 获取实时统计盈亏
     * @param request
     * @return
     */
    @RequestMapping("/getProfitAmount")
    @ResponseBody
    public Number getProfitAmount(HttpServletRequest request){
        String username = request.getParameter("username");
        if(StringUtils.isNotEmpty(username)){
            AgentDateActual agentDateActual = getService().getAgentDateActual(DateTimeTool.getTodayStart(),DateTimeTool.getTodayEnd(),username);
            if(agentDateActual == null)
                return 0;
            return agentDateActual.getProfitAmount().longValue();
        }else{
            Number proffit = getService().getProfitAmount(DateTimeTool.getTodayStart(),DateTimeTool.getTodayEnd());
            return proffit;
        }

    }

}