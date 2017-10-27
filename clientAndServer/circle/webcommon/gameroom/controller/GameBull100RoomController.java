package g.admin.gameroom.controller;

import g.admin.gameroom.form.GameRoomForm;
import g.admin.gameroom.form.GameRoomSearchForm;
import g.model.admin.gamebull100room.vo.VRoomBull100Vo;
import g.model.admin.gameroomconfig.vo.GameRoomConfigBull100ListVo;
import g.model.admin.gameroomconfig.vo.GameRoomConfigBull100Vo;
import g.model.game.vo.GameListVo;
import g.model.gamemodel.vo.GameModelListVo;
import g.model.gameroom.po.GameRoom;
import g.model.gameroom.vo.GameRoomListVo;
import g.model.gameroom.vo.GameRoomVo;
import g.service.admin.gamebull100room.VRoomBull100Service;
import g.service.game.IGameService;
import g.service.gamemodel.IGameModelService;
import g.service.gameroom.IGameRoomService;
import g.web.admin.tools.ServiceTool;
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
import g.model.admin.gamebull100room.vo.VRoomBull100ListVo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
    private IGameRoomService gameRoomService;

    @Autowired
    private VRoomBull100Service roomBull100Service;

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/gamebull100room/";
        //endregion your codes 2
    }

    @RequestMapping("/bull100Room")
    public String bull(Model model, VRoomBull100ListVo vRoomBull100ListVo,HttpServletRequest request) {
        vRoomBull100ListVo.getSearch().setGameCode("BET");
        vRoomBull100ListVo = ServiceTool.vRoomBull100Service().search(vRoomBull100ListVo);

        model.addAttribute("command", vRoomBull100ListVo);
//        return INDEX ;
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
     * @param objectVo
     * @param id
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/configEdit")
    public String ConfigEdit(GameRoomVo objectVo, Integer id, Model model, HttpServletRequest request, HttpServletResponse response) {

        if (id != null) {

            objectVo.getSearch().setId(id);
        }
        objectVo = doEdit(objectVo, model);

        GameRoomConfigBull100ListVo gameRoomConfigBull100ListVo = new GameRoomConfigBull100ListVo();
        gameRoomConfigBull100ListVo.getSearch().setRoomId(objectVo.getResult().getId());
        gameRoomConfigBull100ListVo = ServiceTool.gameRoomConfigBull100Service().search(gameRoomConfigBull100ListVo);
        if(!gameRoomConfigBull100ListVo.getResult().isEmpty()){
            GameRoomConfigBull100Vo gameRoomConfigBull100Vo = new GameRoomConfigBull100Vo();
            gameRoomConfigBull100Vo.setResult(gameRoomConfigBull100ListVo.getResult().get(0));
            model.addAttribute("config", gameRoomConfigBull100Vo);
        }
        model.addAttribute("command", objectVo);
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

}