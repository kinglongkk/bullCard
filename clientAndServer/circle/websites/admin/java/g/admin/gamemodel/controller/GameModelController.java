package g.admin.gamemodel.controller;

import g.model.game.vo.GameListVo;
import g.service.game.IGameService;
import org.soul.commons.lang.GenericTool;
import org.soul.commons.net.ServletTool;
import org.soul.web.controller.BaseCrudController;
import g.service.gamemodel.IGameModelService;
import g.model.gamemodel.po.GameModel;
import g.model.gamemodel.vo.GameModelListVo;
import g.model.gamemodel.vo.GameModelVo;
import g.admin.gamemodel.form.GameModelSearchForm;
import g.admin.gamemodel.form.GameModelForm;
import org.soul.web.validation.form.js.JsRuleCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 游戏表控制器
 *
 * @author lenovo
 * @time 2016-8-25 14:20:28
 */
@Controller
//region your codes 1
@RequestMapping("/gameModel")
public class GameModelController extends BaseCrudController<IGameModelService, GameModelListVo, GameModelVo, GameModelSearchForm, GameModelForm, GameModel, Integer> {
//endregion your codes 1

    private static final String INDEX = "/gamemodel/Index";

    @Autowired
    private IGameService gameService;

    @Autowired
    private IGameModelService gameModelService;

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/gamemodel/";
        //endregion your codes 2
    }

    @Override
    public String list(GameModelListVo listVo, GameModelSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        listVo = gameModelService.search(listVo);

        GameListVo gameListVo = selectGameListVo();

        model.addAttribute("command", listVo);
        model.addAttribute("gameListVo", gameListVo);
        return ServletTool.isAjaxSoulRequest(request) ? INDEX + "Partial" : INDEX;
    }

    @Override
    public String view(GameModelVo objectVo, Integer id, Model model, HttpServletRequest request, HttpServletResponse response) {

        if (id != null) {

            objectVo.getSearch().setId(id);
        }
        objectVo = doView(objectVo, model);

        GameListVo gameListVo = selectGameListVo();

        model.addAttribute("command", objectVo);
        model.addAttribute("gameListVo", gameListVo);
        if (ServletTool.isAjaxSoulRequest(request)) {

            return "gamemodel/View" + "Partial";
        } else {

            return "gamemodel/View";
        }
    }

    @Override
    public String edit(GameModelVo objectVo, Integer id, Model model, HttpServletRequest request, HttpServletResponse response) {

        if (id != null) {

            objectVo.getSearch().setId(id);
        }
        objectVo = doEdit(objectVo, model);

        GameListVo gameListVo = selectGameListVo();

        model.addAttribute("command", objectVo);
        model.addAttribute("gameListVo", gameListVo);
        if (ServletTool.isAjaxSoulRequest(request)) {

            return "gamemodel/Edit" + "Partial";
        } else {

            objectVo.setValidateRule(JsRuleCreator.create(GenericTool.getSuperClassGenricType(getClass(), 4), "result"));
            return "gamemodel/Edit";
        }
    }

    @Override
    public String create(GameModelVo objectVo, Model model, HttpServletRequest request, HttpServletResponse response) {

        objectVo = doCreate(objectVo, model);
        GameListVo gameListVo = selectGameListVo();

        model.addAttribute("command", objectVo);
        model.addAttribute("gameListVo", gameListVo);
        if (ServletTool.isAjaxSoulRequest(request)) {

            return "gamemodel/Edit" + "Partial";
        } else {

            objectVo.setValidateRule(JsRuleCreator.create(GenericTool.getSuperClassGenricType(getClass(), 4), "result"));
            return "gamemodel/Edit";
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

}