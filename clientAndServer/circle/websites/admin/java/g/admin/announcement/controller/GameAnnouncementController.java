package g.admin.announcement.controller;

import org.soul.web.controller.BaseCrudController;
import g.service.announcement.IGameAnnouncementService;
import g.model.announcement.po.GameAnnouncement;
import g.model.announcement.vo.GameAnnouncementListVo;
import g.model.announcement.vo.GameAnnouncementVo;
import g.admin.announcement.form.GameAnnouncementSearchForm;
import g.admin.announcement.form.GameAnnouncementForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 游戏公告表控制器
 *
 * @author lenovo
 * @time 2016-10-28 10:53:18
 */
@Controller
//region your codes 1
@RequestMapping("/gameAnnouncement")
public class GameAnnouncementController extends BaseCrudController<IGameAnnouncementService, GameAnnouncementListVo, GameAnnouncementVo, GameAnnouncementSearchForm, GameAnnouncementForm, GameAnnouncement, Integer> {
//endregion your codes 1

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/announcement/";
        //endregion your codes 2
    }

    //region your codes 3

    //endregion your codes 3

}