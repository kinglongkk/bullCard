package g.admin.gameroom.controller;

import g.model.game.vo.GameListVo;
import g.model.gamemodel.vo.GameModelListVo;
import org.soul.commons.data.json.JsonTool;
import org.soul.commons.net.ServletTool;
import org.soul.web.controller.BaseCrudController;
import g.service.gameroom.IVGameRoomService;
import g.model.gameroom.po.VGameRoom;
import g.model.gameroom.vo.VGameRoomListVo;
import g.model.gameroom.vo.VGameRoomVo;
import g.admin.gameroom.form.VGameRoomSearchForm;
import g.admin.gameroom.form.VGameRoomForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;


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
    public String currentPlayer(HttpServletRequest request){
        Random rand = new Random();
        int randomNum = rand.nextInt((50000 - 1000) + 1) + 1000;


        return JsonTool.toJson(randomNum);
    }

}