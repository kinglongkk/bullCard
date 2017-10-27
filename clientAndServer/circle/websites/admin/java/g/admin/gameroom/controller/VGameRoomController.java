package g.admin.gameroom.controller;

import g.admin.gameroom.form.VGameRoomForm;
import g.admin.gameroom.form.VGameRoomSearchForm;
import g.model.gameroom.po.VGameRoom;
import g.model.gameroom.po.WinGameAmount;
import g.model.gameroom.vo.VGameRoomListVo;
import g.model.gameroom.vo.VGameRoomVo;
import g.service.gameroom.IPlayerAiControlService;
import g.service.gameroom.IVGameRoomService;
import org.soul.commons.data.json.JsonTool;
import org.soul.commons.net.ServletTool;
import org.soul.web.controller.BaseCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 控制器
 *
 * @author lenovo
 * @time 2017-2-14 16:52:54
 */
@Controller
//region your codes 1
@RequestMapping("/vGameRoom")
public class VGameRoomController extends BaseCrudController<IVGameRoomService, VGameRoomListVo, VGameRoomVo, VGameRoomSearchForm, VGameRoomForm, VGameRoom, Integer> {

    @Autowired
    private IVGameRoomService vgameRoomService;
    @Autowired
    private IPlayerAiControlService playerAiControlService;

    @Override
    protected String getViewBasePath() {
        return "/monitor";
    }
    @Override
    public String list(VGameRoomListVo listVo, VGameRoomSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        listVo = vgameRoomService.search(listVo);
        model.addAttribute("command", listVo);
        return ServletTool.isAjaxSoulRequest(request)  ? "/gameroom/monitor/Index" + "Partial" : "/gameroom/monitor/Index";
    }

    /**
     * 当前玩家数
     * @param request
     * @return
     */
    @RequestMapping("/currentPlayer")
    @ResponseBody
    public Map currentPlayer(HttpServletRequest request){
        Integer roomId = Integer.parseInt(request.getParameter("roomId"));
        Map map =  playerAiControlService.getRoomMessage(roomId);
        return map;
    }

    /**
     * 获取每个房间的每天的输赢统计
     * @return
     */
    @RequestMapping("/searchWinGameAmount")
    @ResponseBody
    public String searchWinGameAmount(HttpServletRequest request){
        String[] gameIds = request.getParameter("gameIds").split(",");
        List<Integer> ids = new ArrayList<Integer>();
        for(String id :gameIds){
            ids.add(Integer.valueOf(id));
        }
        List<WinGameAmount> list = vgameRoomService.selectWinGameAmout(ids);
        return JsonTool.toJson(list);
    }

}