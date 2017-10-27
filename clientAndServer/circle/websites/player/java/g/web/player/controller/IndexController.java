package g.web.player.controller;

import g.model.player.po.UserPlayer;
import g.service.common.IUserPlayerService;
import g.web.common.common.controller.BaseIndexController;
import g.web.player.session.SessionManager;
import org.soul.commons.lang.string.RandomStringTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;


/**
 * Created by tony on 15-4-29.
 */
@Controller
public class IndexController extends BaseIndexController {

    private static final String INDEX_URI = "index";

    @Autowired
    private IUserPlayerService userPlayerService;

    @RequestMapping(value = "/index")
    public String index(Model model) {

        //邀请码操作
        UserPlayer userPlayer = userPlayerService.selectUserPlayerInfoById(SessionManager.getUserId());
        if (userPlayer != null && (userPlayer.getInvitationCode() == null || userPlayer.getInvitationCode().equals(""))){

            String invitationCode =  RandomStringTool.randomAlphanumeric(8);
            HashMap updateInfoMap = new HashMap();
            updateInfoMap.put("id", userPlayer.getId());
            updateInfoMap.put("invitationCode", invitationCode);
            userPlayerService.updateUserPlayerInfo(updateInfoMap);
        }
        model.addAttribute("userName", SessionManager.getUserName());
        return INDEX_URI;
    }

    @RequestMapping(value = "/proxy")
    public String proxy(Model model) {
        return "app/Proxy";
    }


}
