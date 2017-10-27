package g.admin.gameroom.controller;

import g.admin.gameroom.form.GameRoomForm;
import g.admin.gameroom.form.GameRoomSearchForm;
import g.model.admin.gameroomconfig.po.GameRoomConfigBull100;
import g.model.admin.gameroomconfig.vo.GameRoomConfigBull100Vo;
import g.model.game.vo.GameListVo;
import g.model.gamemodel.vo.GameModelListVo;
import g.model.gameroom.po.GameRoom;
import g.model.gameroom.vo.GameRoomListVo;
import g.model.gameroom.vo.GameRoomVo;
import g.service.game.IGameService;
import g.service.gamemodel.IGameModelService;
import g.service.gameroom.IGameRoomService;
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
import java.util.Map;


/**
 * 游戏房间表控制器
 *
 * @author lenovo
 * @time 2016-8-25 14:22:00
 */
@Controller
//region your codes 1
@RequestMapping("/gameRoom")
public class GameRoomController extends BaseCrudController<IGameRoomService, GameRoomListVo, GameRoomVo, GameRoomSearchForm, GameRoomForm, GameRoom, Integer> {
//endregion your codes 1

    private static final String INDEX = "/gameroom/Index";

    @Autowired
    private IGameService gameService;

    @Autowired
    private IGameModelService gameModelService;

    @Autowired
    private IGameRoomService gameRoomService;

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/gameroom/";
        //endregion your codes 2
    }

    @Override
    public String list(GameRoomListVo listVo, GameRoomSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        listVo = gameRoomService.search(listVo);

        GameListVo gameListVo = selectGameListVo();
        GameModelListVo gameModelListVo =selectGameModelListVo();

        model.addAttribute("command", listVo);
        model.addAttribute("gameListVo", gameListVo);
        model.addAttribute("gameModelListVo", gameModelListVo);
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

            return "gameroom/View" + "Partial";
        } else {

            return "gameroom/View";
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
            return "gameroom/Edit";
        }
    }

    /**
     * 房间配置
     * @param objectVo
     * @return
     */
    @Override
    public Map persist(GameRoomVo objectVo, @FormModel("result") @Valid GameRoomForm form, BindingResult result) {
        if (!result.hasErrors()) {
            objectVo.getResult().setJackpotOverflow(objectVo.getResult().getJackpotOverflow()==null?0:objectVo.getResult().getJackpotOverflow());
            objectVo.getResult().setMaxLimitPlayerBlance(objectVo.getResult().getMaxLimitPlayerBlance()==null?999999990:objectVo.getResult().getMaxLimitPlayerBlance());
            objectVo.getResult().setSendMsgCondition(objectVo.getResult().getSendMsgCondition()==null?1:objectVo.getResult().getSendMsgCondition());
            objectVo = doPersist(objectVo);
        }else{
            objectVo.setSuccess(false);
        }
        return getVoMessage(objectVo);
    }

    //@RequestMapping(value="addGameRoom",method= RequestMethod.POST)
    @RequestMapping("/addGameRoom")
    @ResponseBody
    public Map addGameRoom(GameRoomVo objectVo, GameRoomConfigBull100Vo confVo, @FormModel("result") @Valid GameRoomForm form, BindingResult result) {

        if (!result.hasErrors()) {
            objectVo.getResult().setJackpotOverflow(objectVo.getResult().getJackpotOverflow()==null?0:objectVo.getResult().getJackpotOverflow());
            objectVo.getResult().setMaxLimitPlayerBlance(objectVo.getResult().getMaxLimitPlayerBlance()==null?999999990:objectVo.getResult().getMaxLimitPlayerBlance());
            objectVo.getResult().setSendMsgCondition(objectVo.getResult().getSendMsgCondition()==null?1:objectVo.getResult().getSendMsgCondition());
            getService().addGameRoom(objectVo,confVo);
        }else{
            objectVo.setSuccess(false);
        }
        Map<String,Object> map = new HashedMap();
        if(objectVo.isSuccess()) {

            map.put("msg",objectVo.getOkMsg()==null ? objectVo.getOkMsg():objectVo.getErrMsg());
            map.put("state",objectVo.isSuccess());
        }
        map.put("msg","保存成功");
        map.put("state", true);
       // String str = "gameBull100Room/bull100Room";

        return map;
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