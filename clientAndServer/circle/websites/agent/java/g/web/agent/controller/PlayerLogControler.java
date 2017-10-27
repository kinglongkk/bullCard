package g.web.agent.controller;

import g.model.DictEnum;
import g.model.ModuleType;
import g.web.agent.session.SessionManager;
import g.web.agent.tools.ServiceTool;
import org.soul.commons.dict.DictTool;
import org.soul.commons.lang.DateTool;
import org.soul.commons.net.ServletTool;
import org.soul.model.sys.vo.SysAuditLogListVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by orange on 16-4-15.
 */
@Controller
@RequestMapping("/playerLog")
public class PlayerLogControler {

    private static final String JOURNAL_URI = "/player/log/Journal";
    /**
     * 玩家信息详情-日志
     *
     * @return
     */
    @RequestMapping("/view/journal")
    public String journal(SysAuditLogListVo listVo, Model model, HttpServletRequest request) {
        //日期不等
        if(listVo.getSearch().getOperatorBegin()!=null && listVo.getSearch().getOperatorEnd()!=null && DateTool.truncatedEquals(listVo.getSearch().getOperatorBegin(), listVo.getSearch().getOperatorEnd(), Calendar.SECOND)==false){
            listVo.getSearch().setOperatorEnd(DateTool.addDays(listVo.getSearch().getOperatorEnd(), +1));
        }
        //日期相等
        if(listVo.getSearch().getOperatorBegin()!=null && listVo.getSearch().getOperatorEnd()!=null && DateTool.truncatedCompareTo(listVo.getSearch().getOperatorBegin(), listVo.getSearch().getOperatorEnd(), Calendar.SECOND)==0){
            listVo.getSearch().setOperatorEnd(DateTool.addDays(listVo.getSearch().getOperatorEnd(), +1));
        }

        listVo = ServiceTool.auditLogService().queryLogs(listVo);
        if(listVo.getSearch().getOperatorBegin()!=null && listVo.getSearch().getOperatorEnd()!=null){
            listVo.getSearch().setOperatorEnd(DateTool.addDays(listVo.getSearch().getOperatorEnd(), -1));
        }
        Date nowTime = SessionManager.getDate().getNow();
        model.addAttribute("command", listVo);
        model.addAttribute("nowTime", nowTime);
        model.addAttribute("userType", DictTool.get(DictEnum.COMMON_USER_TYPE));//用户类型
        Map<String,Serializable> map = DictTool.get(DictEnum.Log_OpType);
        map.remove(ModuleType.PASSPORT_LOGIN_FAIL);
        model.addAttribute("opType", map);//操作类型
        return ServletTool.isAjaxSoulRequest(request) ? JOURNAL_URI + "Partial" : JOURNAL_URI;
    }

}
