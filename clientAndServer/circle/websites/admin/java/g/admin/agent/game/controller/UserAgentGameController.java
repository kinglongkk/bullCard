package g.admin.agent.game.controller;

import g.admin.agent.game.form.UserAgentGameForm;
import g.admin.agent.game.form.UserAgentGameLogoForm;
import g.admin.agent.game.form.UserAgentGameSearchForm;
import g.model.admin.agent.game.po.UserAgentGame;
import g.model.admin.agent.game.vo.UserAgentGameListVo;
import g.model.admin.agent.game.vo.UserAgentGameVo;
import g.model.enums.GameTypeEnum;
import g.model.game.po.Game;
import g.model.game.vo.GameListVo;
import g.service.admin.agent.game.IUserAgentGameService;
import g.service.game.IGameService;
import org.soul.commons.cache.locale.LocaleTool;
import org.soul.commons.collections.ListTool;
import org.soul.commons.collections.MapTool;
import org.soul.commons.lang.GenericTool;
import org.soul.commons.net.ServletTool;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.validation.form.annotation.FormModel;
import org.soul.web.validation.form.js.JsRuleCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 代理游戏控制器
 *
 * @author black
 * @time 2016-12-5 12:01:04
 */
@Controller
@RequestMapping("/userAgentGame")
public class UserAgentGameController extends BaseCrudController<IUserAgentGameService, UserAgentGameListVo, UserAgentGameVo, UserAgentGameSearchForm, UserAgentGameForm, UserAgentGame, Integer> {

    @Autowired
    private IUserAgentGameService agentGameService;

    @Autowired
    private IGameService gameService;

    private static final String GAME_LIST_URI = "detail.include/AgentGame";
    private static final String GAME_LOGO_URI = "detail.include/UploadGameLogoPortrait";
    private static final String GAME_EDIT_URI = "detail.include/AgentGameEdit";

    @Override
    protected String getViewBasePath() {
        return "sys/agent/";
    }

    @Override
    public String list(UserAgentGameListVo listVo, @FormModel("search") @Valid UserAgentGameSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        //代理游戏信息
        UserAgentGameListVo gameListVo = new UserAgentGameListVo();
        gameListVo.getQuery().setCriterions(new Criterion[]{

                new Criterion(UserAgentGame.PROP_AGENT_ID, Operator.EQ, listVo.getSearch().getAgentId())
        });
        gameListVo = agentGameService.search(gameListVo);
        model.addAttribute("command", gameListVo);
        Map map = new HashMap();
        map.put("agentId", listVo.getSearch().getAgentId());
        //代理游戏
        GameListVo agentGameListVo = new GameListVo();
        List<Game> agentGameListResult =  selectGameTran(gameService.selectAgentGame(map));
        agentGameListVo.setResult(agentGameListResult);
        model.addAttribute("agentGameList", agentGameListVo);
        model.addAttribute("agentId", listVo.getSearch().getAgentId());
        return getViewBasePath() + GAME_LIST_URI ;
    }

    @Override
    public String create(UserAgentGameVo objectVo, Model model, HttpServletRequest request, HttpServletResponse response) {

        Integer agentId = objectVo.getSearch().getAgentId();
        model.addAttribute("agentId", agentId);
        objectVo = doCreate(objectVo, model);
        objectVo.setValidateRule(JsRuleCreator.create(UserAgentGameForm.class, "result"));
        Map map = new HashMap();
        map.put("agentId", agentId);
        //未代理游戏 一级类型
        GameListVo firstTypeList = new GameListVo();
        List<Game> firstTypeResult = selectGameTran(gameService.selectFirstType());
        firstTypeList.setResult(firstTypeResult);
        model.addAttribute("firstTypeList", firstTypeList);
        model.addAttribute("command", objectVo);
        return getViewBasePath() + GAME_EDIT_URI;
    }

    @Override
    public String edit(UserAgentGameVo objectVo, Integer id, Model model, HttpServletRequest request, HttpServletResponse response) {

        if (id != null) {
            objectVo.getSearch().setId(id);
        }
        objectVo = doEdit(objectVo, model);
        model.addAttribute("command", objectVo);
        if (ServletTool.isAjaxSoulRequest(request)) {
            return getViewBasePath() + GAME_EDIT_URI + "Partial";
        } else {
            objectVo.setValidateRule(JsRuleCreator.create(GenericTool.getSuperClassGenricType(getClass(), 4), "result"));
            return getViewBasePath() + GAME_EDIT_URI;
        }
    }

    @Override
    public Map persist(UserAgentGameVo objectVo, @FormModel("result") @Valid UserAgentGameForm form, BindingResult result) {

        if (!result.hasErrors()) {
            if (objectVo.getResult().getId() != null) {
                objectVo.setProperties(UserAgentGame.PROP_GAME_LINK);
                objectVo = agentGameService.updateOnly(objectVo);
            } else {
                objectVo = agentGameService.insert(objectVo);
            }
        } else {
            objectVo.setSuccess(false);
        }
        return getVoMessage(objectVo);
    }

    /**
     * 查询代理未曾代理的游戏
     * @param firstType
     * @param agentId
     * @return
     */
    @RequestMapping("selectSecondType")
    @ResponseBody
    public Map selectGameWithoutAgent(String firstType, Integer agentId){

        Map map = new HashMap();
        map.put("firstType", firstType);
        map.put("agentId", agentId);
        List<Game> gameWithoutAgentListResult =  selectGameTran(gameService.selectGameWithoutAgent(map));
        map.clear();
        map.put("secondType", gameWithoutAgentListResult);
        return map;
    }

    /**
     * 上传图标--打开页面
     * @param vo
     * @param model
     * @return
     */
    @RequestMapping(value = "/toUploadLogoPortrait")
    public String toUploadLogoPortrait(UserAgentGameVo vo, Model model) {

        //表单校验
        model.addAttribute("command", vo);
        model.addAttribute("validate", JsRuleCreator.create(UserAgentGameLogoForm.class));
        return this.getViewBasePath() + GAME_LOGO_URI;
    }

    /**
     * 上传图标--保存
     * @param vo
     * @return
     */
    @RequestMapping(value = "/uploadLogoPortrait")
    @ResponseBody
    public Map uploadLogoPortrait(UserAgentGameVo vo) {

        Map map = MapTool.newHashMap();
        List<String> properties = ListTool.newArrayList();
        properties.add(UserAgentGame.PROP_GAME_LOGO);
        vo.setProperties(properties.toArray(new String[1]));
        boolean success = agentGameService.updateOnly(vo).isSuccess();
        map.put("state", success);
        if (success) {
            map.put("msg", LocaleTool.tranMessage("common", "myAccount.updatePassword.success"));
        } else {
            map.put("msg", LocaleTool.tranMessage("common", "myAccount.updatePassword.failed"));
        }
        return map;
    }

    /**
     * 游戏枚举翻译
     * @param types
     * @return
     */
    public List<Game> selectGameTran(List<Game> types)  {

        if (types != null && !types.isEmpty()) {
            for (int i = 0, length = types.size(); i < length; i ++) {
                for (GameTypeEnum gameTypeEnum : GameTypeEnum.values()) {
                    if (gameTypeEnum.getCode().equals(types.get(i).getFirstType())) {
                        types.get(i).setFirstType(gameTypeEnum.getTrans());
                        types.get(i).setType(gameTypeEnum.getCode());
                        break;
                    }
                }
            }
        }
        return types;
    }

}