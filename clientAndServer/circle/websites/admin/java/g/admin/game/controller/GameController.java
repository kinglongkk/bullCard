package g.admin.game.controller;

import g.admin.game.form.GameIconForm;
import g.model.enums.BallTypeEnum;
import g.model.enums.ChessCardEnum;
import g.model.enums.GameTypeEnum;
import g.model.enums.LotteryTypeEnum;
import g.web.admin.session.SessionManager;
import g.web.admin.tools.ServiceTool;
import org.soul.commons.collections.ListTool;
import org.soul.commons.collections.MapTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.locale.LocaleTool;
import org.soul.commons.support._Module;
import org.soul.web.controller.BaseCrudController;
import g.service.game.IGameService;
import g.model.game.po.Game;
import g.model.game.vo.GameListVo;
import g.model.game.vo.GameVo;
import g.admin.game.form.GameSearchForm;
import g.admin.game.form.GameForm;
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
import java.util.*;


/**
 * 游戏表控制器
 *
 * @author lenovo
 * @time 2016-8-25 14:17:12
 */
@Controller
//region your codes 1
@RequestMapping("/game")
public class GameController extends BaseCrudController<IGameService, GameListVo, GameVo, GameSearchForm, GameForm, Game, Integer> {

    private static final String INDEX = "game/Index";

    @Autowired
    private IGameService gameService;

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/game/";
        //endregion your codes 2
    }

    //region your codes 3
    /**
     * 逻辑删除game
     * @param objectVo
     * @param id
     * @return boolean
     */
    @Override
    public Map delete(GameVo objectVo, Integer id) {

        Map queryMap = new HashMap();
        queryMap.put("gameId", id);
        queryMap.put("updateUser", SessionManager.getUser().getUsername());
        if (gameService.updateGameIsDelete(queryMap)) {

            objectVo.setOkMsg(LocaleTool.tranMessage(_Module.COMMON, "delete.success"));
        } else{

            objectVo.setErrMsg(LocaleTool.tranMessage(_Module.COMMON, "delete.failed"));
        }
        return getVoMessage(objectVo);
    }

    /**
     * 保存
     * @param objectVo
     * @param form
     * @param result
     * @return
     */
    @Override
    public Map persist(GameVo objectVo, @FormModel("result") @Valid GameForm form, BindingResult result) {


        if (!result.hasErrors()) {

            if (StringTool.isBlank(objectVo.getResult().getCreateUser())){

                objectVo.getResult().setCreateUser(SessionManager.getUser().getUsername());
                objectVo.getResult().setCreateTime(new Date());
            }

            try {

                if (StringTool.isBlank(objectVo.getResult().getIsDeleted().toString())){

                    objectVo.getResult().setIsDeleted(false);
                }
            }catch (NullPointerException e){

                objectVo.getResult().setIsDeleted(false);
            }
            objectVo.getResult().setUpdateUser(SessionManager.getUser().getUsername());
            objectVo.getResult().setUpdateTime(new Date());
            objectVo = doPersist(objectVo);
        }else{

            objectVo.setSuccess(false);
        }
        return getVoMessage(objectVo);
    }

    /**
     * 列表展示
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @param request
     * @param response
     * @return
     */
    @Override
    public String list(GameListVo listVo, @FormModel("search") @Valid GameSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        model.addAttribute("firstName",GameTypeEnum.values());
        model.addAttribute("sportType", BallTypeEnum.values());
        model.addAttribute("lotteryType", LotteryTypeEnum.values());
        model.addAttribute("chessCardType", ChessCardEnum.values());
        return super.list(listVo, form, result, model, request, response);
    }

    /**
     * 单项编辑
     * @param objectVo
     * @param id
     * @param model
     * @param request
     * @param response
     * @return
     */
    @Override
    public String edit(GameVo objectVo, Integer id, Model model, HttpServletRequest request, HttpServletResponse response) {

        model.addAttribute("firstName",GameTypeEnum.values());
        if (id != null) {

            objectVo.getSearch().setId(id);
        }
        objectVo = doEdit(objectVo, model);
        String firstType = objectVo.getResult().getFirstType();
        if (firstType.equals(GameTypeEnum.SPORT.getCode())){

            model.addAttribute("secondType",BallTypeEnum.values());
        }
        if (firstType.equals(GameTypeEnum.LOTTERY.getCode())){

            model.addAttribute("secondType", LotteryTypeEnum.values());
        }
        if (firstType.equals(GameTypeEnum.CHESS_CARD.getCode())){

            model.addAttribute("secondType", ChessCardEnum.values());
        }
        return super.edit(objectVo, id, model, request, response);
    }

    /**
     * 获取二级类型
     * @param typeCode
     * @return
     */
    @RequestMapping(value = "/getSecondType",method = RequestMethod.POST)
    @ResponseBody
    public Map getSecondType(String typeCode){

        Map map = new HashMap();
        List secondTypeCodes = new ArrayList();
        List secondTypeTranss = new ArrayList();
        if (typeCode.equals(GameTypeEnum.SPORT.getCode())){

            for (BallTypeEnum ballTypeEnum : BallTypeEnum.values()) {

                String secondTypeCode = ballTypeEnum.getCode();
                String secondTypeTrans = ballTypeEnum.getTrans();
                secondTypeCodes.add(secondTypeCode);
                secondTypeTranss.add(secondTypeTrans);
            }
            map.put("secondTypeCodes",secondTypeCodes);
            map.put("secondTypeTranss",secondTypeTranss);
            return map;
        }
        if (typeCode.equals(GameTypeEnum.LOTTERY.getCode())){
            for (LotteryTypeEnum lotteryTypeEnum : LotteryTypeEnum.values()) {

                String secondTypeCode = lotteryTypeEnum.getCode();
                String secondTypeTrans = lotteryTypeEnum.getTrans();
                secondTypeCodes.add(secondTypeCode);
                secondTypeTranss.add(secondTypeTrans);
            }
            map.put("secondTypeCodes",secondTypeCodes);
            map.put("secondTypeTranss",secondTypeTranss);
            return map;
        }
        if (typeCode.equals(GameTypeEnum.CHESS_CARD.getCode())){

            for (ChessCardEnum chessCardEnum : ChessCardEnum.values()) {

                String secondTypeCode = chessCardEnum.getCode();
                String secondTypeTrans = chessCardEnum.getTrans();
                secondTypeCodes.add(secondTypeCode);
                secondTypeTranss.add(secondTypeTrans);
            }
            map.put("secondTypeCodes",secondTypeCodes);
            map.put("secondTypeTranss",secondTypeTranss);
            return map;
        }
        return null;
    }

    /**
     * 详细查询
     * @param objectVo
     * @param id
     * @param model
     * @param request
     * @param response
     * @return
     */
    @Override
    public String view(GameVo objectVo, Integer id, Model model, HttpServletRequest request, HttpServletResponse response) {

        model.addAttribute("firstName",GameTypeEnum.values());
        if (id != null) {

            objectVo.getSearch().setId(id);
        }
        objectVo = doEdit(objectVo, model);
        String firstType = objectVo.getResult().getFirstType();
        if (firstType.equals(GameTypeEnum.SPORT.getCode())){

            model.addAttribute("secondType",BallTypeEnum.values());
        }
        if (firstType.equals(GameTypeEnum.LOTTERY.getCode())){

            model.addAttribute("secondType", LotteryTypeEnum.values());
        }
        if (firstType.equals(GameTypeEnum.CHESS_CARD.getCode())){

            model.addAttribute("secondType", ChessCardEnum.values());
        }
        return super.view(objectVo, id, model, request, response);
    }

    /**
     * 新增游戏
     * @param objectVo
     * @param model
     * @param request
     * @param response
     * @return
     */
    @Override
    public String create(GameVo objectVo, Model model, HttpServletRequest request, HttpServletResponse response) {

        model.addAttribute("firstName",GameTypeEnum.values());
        model.addAttribute("sportType", BallTypeEnum.values());
        return super.create(objectVo, model, request, response);
    }

    /**
     * 上传图标--打开页面
     *
     * @param vo
     * @param model
     * @return
     */
    @RequestMapping(value = "/toUploadIconPortrait")
    public String toUploadIconPortrait(GameVo vo, Model model) {

        //表单校验
        model.addAttribute("command", vo);
        model.addAttribute("validate", JsRuleCreator.create(GameIconForm.class));
        return this.getViewBasePath() + "UploadIconPortrait";
    }

    /**
     * 上传图标--保存
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/uploadIconPortrait")
    @ResponseBody
    public Map uploadIconPortrait(GameVo vo) {

        Map map = MapTool.newHashMap();
        List<String> properties = ListTool.newArrayList();
        properties.add(Game.PROP_ICON);
        vo.setProperties(properties.toArray(new String[1]));
        boolean success;
        success = ServiceTool.gameService().updateOnly(vo).isSuccess();
        map.put("state", success);
        if (success) {

            map.put("msg", LocaleTool.tranMessage("common", "myAccount.updatePassword.success"));
        } else {

            map.put("msg", LocaleTool.tranMessage("common", "myAccount.updatePassword.failed"));
        }
        return map;
    }

}