package g.web.player.controller;


import g.model.admin.vo.SysAnnouncementListVo;
import g.service.common.ISysAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 总控-系统公告视图控制器
 *
 * @author orange
 * @time 2015-12-3 11:45:57
 */
@Controller
//region your codes 1
@RequestMapping("/SysAnnouncement")
public class SysAnnouncementController  {

    @Autowired
    private ISysAnnouncementService announcementService;


    public SysAnnouncementListVo indexView(SysAnnouncementListVo listVo){

        return listVo;
    }

}