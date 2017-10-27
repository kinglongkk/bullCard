package g.web.agent.controller;

import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import g.web.agent.session.SessionManager;
import g.web.agent.tools.ServiceTool;
import g.model.player.vo.VSysUserVo;
import java.util.Date;
import java.util.List;

/**
 * Created by orange on 16-4-20.
 */
@Controller
@RequestMapping("/agent")
public class AgentController {

    private static final String INDEX_URI = "/home/Index";

    @RequestMapping("/agentIndex")
    public String index(Model model,VSysUserVo vSysUserVo){
        //账户摘要
        vSysUserVo.getSearch().setId(SessionManager.getUserId());
        vSysUserVo = ServiceTool.vSysUserService().search(vSysUserVo);
        model.addAttribute("command", vSysUserVo);
        //未结算注单
        Double preOrderMoney = ServiceTool.myAccountService().selectPreOrderMoney(SessionManager.getUserId());
        model.addAttribute("preOrderMoney", preOrderMoney);
        //今日新增玩家
        //0时区的当前时间转换为美东时间0时的时间(今日的凌晨0时)
        Date zeroTime = SessionManager.getDate().getToday();
        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.setResult(new SysUser());
        sysUserVo.getSearch().setCreateTime(zeroTime);
        //今日新增玩家个数
        List<Integer> addUserIdList = ServiceTool.myAccountService().searchAddPlayer(sysUserVo);
        model.addAttribute("addPlayerCount", addUserIdList.size());
        //会员统计信息和余额信息
        List playerCountBalance =  ServiceTool.myAccountService().searchPlayerCountBalance(vSysUserVo);
        model.addAttribute("playerCountBalance", playerCountBalance);
        return INDEX_URI;
    }

}
