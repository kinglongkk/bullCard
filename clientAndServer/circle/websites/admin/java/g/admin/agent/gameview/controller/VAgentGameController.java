package g.admin.agent.gameview.controller;

import org.soul.web.controller.BaseCrudController;
import g.service.admin.agent.gameview.IVAgentGameService;
import g.model.admin.agent.gameview.po.VAgentGame;
import g.model.admin.agent.gameview.vo.VAgentGameListVo;
import g.model.admin.agent.gameview.vo.VAgentGameVo;
import g.admin.agent.gameview.form.VAgentGameSearchForm;
import g.admin.agent.gameview.form.VAgentGameForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 控制器
 *
 * @author black
 * @time 2016-12-19 11:01:49
 */
@Controller
//region your codes 1
@RequestMapping("/vAgentGame")
public class VAgentGameController extends BaseCrudController<IVAgentGameService, VAgentGameListVo, VAgentGameVo, VAgentGameSearchForm, VAgentGameForm, VAgentGame, Integer> {
//endregion your codes 1

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/agent/gameview/";
        //endregion your codes 2
    }

    //region your codes 3

    //endregion your codes 3

}