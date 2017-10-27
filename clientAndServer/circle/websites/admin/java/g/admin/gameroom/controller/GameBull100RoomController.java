package g.admin.gameroom.controller;

import g.admin.gameroom.form.GameRoomForm;
import g.admin.gameroom.form.GameRoomSearchForm;
import g.model.admin.gamebull100room.vo.VRoomBull100ListVo;
import g.model.admin.gameroomconfig.po.GameRoomConfigBull100;
import g.model.admin.gameroomconfig.vo.GameRoomConfigBull100ListVo;
import g.model.admin.gameroomconfig.vo.GameRoomConfigBull100Vo;
import g.model.game.vo.GameListVo;
import g.model.gamemodel.vo.GameModelListVo;
import g.model.gameroom.po.GameRoom;
import g.model.gameroom.vo.GameRoomListVo;
import g.model.gameroom.vo.GameRoomVo;
import g.service.admin.gameroomconfig.IGameRoomConfigBull100Service;
import g.service.game.IGameService;
import g.service.gamemodel.IGameModelService;
import g.service.gameroom.IGameRoomService;
import g.web.admin.tools.ServiceTool;
import org.apache.commons.collections.map.HashedMap;
import org.soul.commons.lang.GenericTool;
import org.soul.commons.net.ServletTool;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.validation.form.annotation.FormModel;
import org.soul.web.validation.form.js.JsRuleCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 游戏房间表控制器
 *
 * @author lenovo
 * @time 2016-8-25 14:22:00
 */
@Controller
//region your codes 1
@RequestMapping("/gameBull100Room")
public class GameBull100RoomController extends BaseCrudController<IGameRoomService, GameRoomListVo, GameRoomVo, GameRoomSearchForm, GameRoomForm, GameRoom, Integer> {
//endregion your codes 1

    private static final String INDEX = "/gamebull100room/Index";

    @Autowired
    private IGameService gameService;

    @Autowired
    private IGameModelService gameModelService;
    @Autowired
    private IGameRoomConfigBull100Service gameRoomConfigBull100Service;

    @Autowired
    private IGameRoomService gameRoomService;

    @Override
    protected String getViewBasePath() {
        return "/gamebull100room/";
    }

    @RequestMapping("/bull100Room")
    public String bull(Model model, VRoomBull100ListVo vRoomBull100ListVo,HttpServletRequest request) {

        vRoomBull100ListVo = ServiceTool.vRoomBull100Service().search(vRoomBull100ListVo);
        model.addAttribute("command", vRoomBull100ListVo);
        return ServletTool.isAjaxSoulRequest(request) ? INDEX + "Partial" : INDEX;
    }

    @Override
    public String view(GameRoomVo objectVo, Integer id, Model model, HttpServletRequest request, HttpServletResponse response) {

        if (id != null) {
            objectVo.getSearch().setId(id);
        }
        objectVo = doView(objectVo, model);
        GameListVo gameListVo = selectGameListVo();
        GameModelListVo gameModelListVo =selectGameModelListVo();

        model.addAttribute("command", objectVo);
        model.addAttribute("gameListVo", gameListVo);
        model.addAttribute("gameModelListVo", gameModelListVo);
        if (ServletTool.isAjaxSoulRequest(request)) {
            return "gamebull100room/View" + "Partial";
        } else {
            return "gamebull100room/View";
        }
    }

    @Override
    public String edit(GameRoomVo objectVo, Integer id, Model model, HttpServletRequest request, HttpServletResponse response) {

        if (id != null) {
            objectVo.getSearch().setId(id);
        }
        objectVo = doEdit(objectVo, model);

        GameListVo gameListVo = selectGameListVo();
        GameModelListVo gameModelListVo =selectGameModelListVo();

        model.addAttribute("command", objectVo);
        model.addAttribute("gameListVo", gameListVo);
        model.addAttribute("gameModelListVo", gameModelListVo);
        if (ServletTool.isAjaxSoulRequest(request)) {
            return "gameroom/Edit" + "Partial";
        } else {
            objectVo.setValidateRule(JsRuleCreator.create(GenericTool.getSuperClassGenricType(getClass(), 4), "result"));
            return "gamebull100room/Edit";
        }
    }

    /**
     * 房间配置
     * @return
     */
    @RequestMapping("/configEdit")
    public String ConfigEdit(Integer id, Model model, HttpServletRequest request, HttpServletResponse response) {
        GameRoomConfigBull100 config = gameRoomConfigBull100Service.getGameRoomConfigBull100(id);
        model.addAttribute("config", config);
        return "gamebull100room/ConfigEdit";
    }

    @Override
    public Map persist(GameRoomVo objectVo, @FormModel("result") @Valid GameRoomForm form, BindingResult result) {

        if (!result.hasErrors()) {
            objectVo = doPersist(objectVo);
        }else{
            objectVo.setSuccess(false);
        }
        return getVoMessage(objectVo);
    }

    @Override
    public String create(GameRoomVo objectVo, Model model, HttpServletRequest request, HttpServletResponse response) {

        objectVo = doCreate(objectVo, model);
        GameListVo gameListVo = selectGameListVo();
        GameModelListVo gameModelListVo =selectGameModelListVo();

        model.addAttribute("command", objectVo);
        model.addAttribute("gameListVo", gameListVo);
        model.addAttribute("gameModelListVo", gameModelListVo);
        if (ServletTool.isAjaxSoulRequest(request)) {
            return "gameroom/Edit" + "Partial";
        } else {
            objectVo.setValidateRule(JsRuleCreator.create(GenericTool.getSuperClassGenricType(getClass(), 4), "result"));
            return "gameroom/Edit";
        }
    }

    /**
     * 查询游戏
     * @return listVo
     */
    public GameListVo selectGameListVo(){

        GameListVo gameListVo = new GameListVo();
        GameListVo listVo = gameService.search(gameListVo);
        return listVo;
    }

    /**
     * 查询游戏模式
     * @return listVo
     */
    public GameModelListVo selectGameModelListVo(){

        GameModelListVo gameModelListVo = new GameModelListVo();
        GameModelListVo listVo = gameModelService.search(gameModelListVo);
        return listVo;
    }

    @ResponseBody
    @RequestMapping(value="queryGameRoomConf",method= RequestMethod.POST)
    public Map<String, Object> queryGameRoomConf(int modelId) {
        Map<String,Object> map = new HashedMap();
        List<GameRoomConfigBull100> list = gameRoomConfigBull100Service.queryRoomList(modelId);
        map.put("bullConf",list);
        return map;
    }

    @ResponseBody
    @RequestMapping("queryGameRoomConfByRoomId")
    public Map<String,Object> queryGameRoomConfById(int roomId) {
        Map<String,Object> map = new HashMap();
        GameRoomConfigBull100 gameRoomConfigBull100 = gameRoomConfigBull100Service.getGameRoomConfigBull100(roomId);
        map.put("roomConfModel",gameRoomConfigBull100);
        return map;
    }
}