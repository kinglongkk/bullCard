package g.admin.gameroom.controller;

import g.admin.gameroom.form.PlayerAiRatioControlForm;
import g.admin.gameroom.form.PlayerAiRatioControlSearchForm;
import g.admin.gameroom.form.VGameRoomSearchForm;
import g.model.admin.agent.game.po.UserAgentGame;
import g.model.gameroom.po.GameRoom;
import g.model.gameroom.po.PlayerAiRatioControl;
import g.model.gameroom.vo.*;
import g.service.gameroom.IGameRoomService;
import g.service.gameroom.IPlayerAiControlService;
import g.service.gameroom.IPlayerAiRatioControlService;
import org.soul.commons.net.ServletTool;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.validation.form.annotation.FormModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;


/**
 * Ai与玩家配比设置控制器
 *
 * @author lenovo
 * @time 2017-2-15 14:28:52
 */
@Controller
@RequestMapping("/playerAiRatioControl")
public class PlayerAiRatioControlController extends BaseCrudController<IPlayerAiRatioControlService, PlayerAiRatioControlListVo, PlayerAiRatioControlVo, PlayerAiRatioControlSearchForm, PlayerAiRatioControlForm, PlayerAiRatioControl, Integer> {

    @Autowired
    private IPlayerAiRatioControlService playerAiRatioControlService;
    @Autowired
    private IPlayerAiControlService playerAiControlService;
    @Autowired
    private IGameRoomService gameRoomService;

    @Override
    protected String getViewBasePath() {
        return "/gameroom/airatio/";
    }

    @RequestMapping("/airatiolist")
    public String airatiolist(PlayerAiRatioControlListVo listVo, PlayerAiRatioControlSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        String roomId = request.getParameter("roomId");
        PlayerAiRatioControlListVo l = new PlayerAiRatioControlListVo();
        List<Integer> ids = playerAiControlService.getAiControlIds(Integer.parseInt(roomId));
        if(ids.size() > 0) {
            listVo.getQuery().setCriterions(new Criterion[]{
                    new Criterion(PlayerAiRatioControl.PROP_AI_PLAYER_CONTROL_ID, Operator.IN, ids)
            });

            listVo = playerAiRatioControlService.search(listVo);
        }
        model.addAttribute("command", listVo);
        model.addAttribute("roomId",roomId);
        return ServletTool.isAjaxSoulRequest(request)  ? "/gameroom/airatio/Index" + "Partial" : "/gameroom/airatio/Index";
    }

    @RequestMapping("/saveAi")
    @ResponseBody
    public Map persist(PlayerAiRatioControlVo objectVo, PlayerAiControlVo aiVo, @FormModel("result") @Valid PlayerAiRatioControlForm form, BindingResult result) {
//        playerAiRatioControlService.batchInsert(objectVo);
        Integer roomId = aiVo.getResult().getRoomId();
        GameRoom game =gameRoomService.getGameRom(roomId);
        aiVo.getResult().setStatus("10");
        aiVo.getResult().setAiQty(0);
        aiVo.getResult().setControlMode("1");
        aiVo.getResult().setRoomName(game.getName());
        playerAiControlService.insert(aiVo);
        objectVo.getResult().setAiPlayerControlId(aiVo.getResult().getId());
        Map map = super.persist(objectVo, form, result);
        return map;
    }

    @Override
    public Map delete(PlayerAiRatioControlVo objectVo, Integer id){
        Map map = super.delete(objectVo,id);
//        playerAiRatioControlService.deleteAiControl(id);
        return map;
    }
}