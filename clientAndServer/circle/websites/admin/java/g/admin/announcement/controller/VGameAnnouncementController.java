package g.admin.announcement.controller;

import g.model.admin.po.SysAnnouncementI18n;
import g.model.admin.vo.SysAnnouncementI18nVo;
import g.model.announcement.po.GameAnnouncement;
import g.model.announcement.po.VGameAnnouncement;
import g.model.announcement.vo.GameAnnouncementVo;
import g.model.announcement.vo.VGameAnnouncementListVo;
import g.model.announcement.vo.VGameAnnouncementVo;
import g.model.cache.CacheBase;
import g.model.game.po.Game;
import g.model.game.vo.GameListVo;
import g.model.site.SiteLanguage;
import g.service.common.IVGameAnnouncementService;
import g.service.game.IGameService;
import g.web.admin.controller.announcement.form.VGameAnnouncementForm;
import g.web.admin.controller.announcement.form.VGameAnnouncementSearchForm;
import g.web.admin.session.SessionManager;
import g.web.admin.tools.ServiceTool;
import org.soul.commons.lang.DateTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.net.ServletTool;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.validation.form.js.JsRuleCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 游戏公告控制器
 * Created by lance on 2016/11/2.
 */
@Controller
@RequestMapping("/vGameAnnouncement")
public class VGameAnnouncementController extends BaseCrudController<IVGameAnnouncementService, VGameAnnouncementListVo, VGameAnnouncementVo, VGameAnnouncementSearchForm, VGameAnnouncementForm, VGameAnnouncement, Integer> {

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/announcement/";
        //endregion your codes 2
    }

    //region your codes 3
    //游戏公告
    private static final String GAME_NOTICE_URL = "announcement/GameNotice";
    //进入预览页面
    private static final String GAME_NOTICE_PREVIEW_URL = "announcement/GameNoticePreview";
    //发送信息
    private static final String SAVE_GAME_NOTICE_URL = "announcement/SaveGameNotice";
    //历史记录
    private static final String GAME_NOTICE_HISTORY_URL = "announcement/GameNoticeHistory";
    //详情
    private static final String GAME_NOTICE_DETAIL_URL = "announcement/GameNoticeDetail";
    //region your codes 3

    @Autowired
    private IGameService gameService;

    /**
     * 第一次进入 and 返回上一页
     *
     * @param model
     * @param gameAnnouncementVo
     * @return
     */
    @RequestMapping("/gameNotice")
    public String GameNotice(Model model, GameAnnouncementVo gameAnnouncementVo) {

        model.addAttribute("validate", JsRuleCreator.create(VGameAnnouncementForm.class));
        Map<String, SiteLanguage> siteLanguage = CacheBase.getSiteLanguage();
        model.addAttribute("languageList", siteLanguage.keySet());
        model.addAttribute("gameAnnouncementVo", gameAnnouncementVo);
        //查找所有游戏
        GameListVo gameListVo = new GameListVo();
        gameListVo.setProperties(Game.PROP_ID,Game.PROP_NAME);
        List<Map<String, Object>> result = gameService.searchProperties(gameListVo);
        model.addAttribute("game",result);

        return GAME_NOTICE_URL;
    }

    //进入预览页面
    @RequestMapping("gameNoticePreview")
    public String GameNoticePreview(Model model, GameAnnouncementVo gameAnnouncementVo) {

        Map<String, SiteLanguage> siteLanguage = CacheBase.getSiteLanguage();
        model.addAttribute("languageList", siteLanguage.keySet());
        model.addAttribute("gameAnnouncementVo", gameAnnouncementVo);
        model.addAttribute("games", selectAllGame());
        return GAME_NOTICE_PREVIEW_URL;
    }

    /**
     * 编辑公告
     * @param model
     * @return
     */
    @RequestMapping("/editNotice")
    public String editNotice2(Model model,VGameAnnouncementListVo vGameAnnouncementListVo) {

        vGameAnnouncementListVo = ServiceTool.vGameAnnouncementService().search(vGameAnnouncementListVo);
        GameAnnouncementVo gameAnnouncementVo = new GameAnnouncementVo();
        gameAnnouncementVo.setResult(new GameAnnouncement());
        String[] language = new String[vGameAnnouncementListVo.getResult().size()];
        String[] title = new String[vGameAnnouncementListVo.getResult().size()];
        String[] content = new String[vGameAnnouncementListVo.getResult().size()];
        if(vGameAnnouncementListVo.getResult().size()>0){
            for(int i=0;i<vGameAnnouncementListVo.getResult().size();i++){
                language[i] = vGameAnnouncementListVo.getResult().get(i).getLocal();
                title[i] = vGameAnnouncementListVo.getResult().get(i).getTitle();
                content[i] = vGameAnnouncementListVo.getResult().get(i).getContent();
            }
            gameAnnouncementVo.getResult().setId(vGameAnnouncementListVo.getResult().get(0).getId());
            gameAnnouncementVo.getResult().setTitle(vGameAnnouncementListVo.getResult().get(0).getTitle());
            gameAnnouncementVo.getResult().setContent(vGameAnnouncementListVo.getResult().get(0).getContent());
            gameAnnouncementVo.getResult().setValidityStartTime(vGameAnnouncementListVo.getResult().get(0).getValidityStartTime());
            gameAnnouncementVo.getResult().setValidityEndTime(vGameAnnouncementListVo.getResult().get(0).getValidityEndTime());
            gameAnnouncementVo.getResult().setGameId(vGameAnnouncementListVo.getResult().get(0).getGameId());
            gameAnnouncementVo.getResult().setGameName(vGameAnnouncementListVo.getResult().get(0).getGameName());
            gameAnnouncementVo.getResult().setAnnouncementType(vGameAnnouncementListVo.getResult().get(0).getAnnouncementType());
            gameAnnouncementVo.getResult().setPublishTime(vGameAnnouncementListVo.getResult().get(0).getPublishTime());
            gameAnnouncementVo.getResult().setRepeat(vGameAnnouncementListVo.getResult().get(0).getRepeat());
            gameAnnouncementVo.getResult().setRepeatTime(vGameAnnouncementListVo.getResult().get(0).getRepeatTime());
            gameAnnouncementVo.getResult().setRepeatUnit(vGameAnnouncementListVo.getResult().get(0).getRepeatUnit());
            gameAnnouncementVo.setLanguage(language);
        }
        model.addAttribute("game", selectAllGame());
        model.addAttribute("validate", JsRuleCreator.create(VGameAnnouncementForm.class));
        model.addAttribute("languageList", gameAnnouncementVo.getLanguage());
        model.addAttribute("gameAnnouncementVo", gameAnnouncementVo);
        return GAME_NOTICE_URL;
    }

    /**
     * 发布公告
     *
     * @return
     */
    @RequestMapping("/saveGameNotice")
    public String saveGameNotice(GameAnnouncementVo vo) {

        //添加游戏公告表
        vo.setSuccess(false);
        //设置的时间已经小于当前时间,则改为立即发送,定时发送无效
        if (DateTool.truncatedCompareTo(vo.getResult().getPublishTime() == null ? new Date() : vo.getResult().getPublishTime(), SessionManager.getDate().getNow(), Calendar.SECOND) <= 0)
            vo.getResult().setPublishTime(new Date());
        //将多余的“，”去掉
        String gameNames = vo.getResult().getGameName();
        if (StringTool.isNotEmpty(gameNames)) {
            if (gameNames.endsWith(",")) {
                gameNames = gameNames.substring(0, gameNames.length() - 1);
            }
        }
        vo.getResult().setGameName(gameNames);
        vo.getResult().setPublishUserId(SessionManager.getUserId());
        vo.getResult().setPublishUserName(SessionManager.getUserName());
        vo.getResult().setSaveTime(new Date());
        vo.getResult().setLocal("zh_CN");
        vo.getResult().setMsgType("BACKSTAGE");
        vo.getResult().setIsSend(0);
        if(vo.getSearch().getId() != null){
            ServiceTool.gameAnnouncementService().delete(vo);
            ServiceTool.gameAnnouncementService().insert(vo);
        }else {
            ServiceTool.gameAnnouncementService().insert(vo);
        }
        return SAVE_GAME_NOTICE_URL;
    }

    private void saveI18nLanguage(GameAnnouncementVo vo) {
        //添加游戏公告国际化表
        if (vo.isSuccess()) {
            for (int i = 0; i < vo.getLanguage().length; i++) {
                SysAnnouncementI18nVo i18nVo = new SysAnnouncementI18nVo();
                i18nVo.setResult(new SysAnnouncementI18n());
                i18nVo.getResult().setSysAnnouncementId(vo.getResult().getId());
                i18nVo.getResult().setLocal(vo.getLanguage()[i]);
                i18nVo.getResult().setTitle(vo.getTitle()[i]);
                i18nVo.getResult().setContent(vo.getContent()[i]);
                if(vo.getSearch().getId()!=null){
                    i18nVo.getSearch().setSysAnnouncementId(vo.getSearch().getId());
                    i18nVo.getSearch().setLocal(vo.getLanguage()[i]);
                    Integer in = ServiceTool.sysAnnouncementI18nService().deleteI18nSysAnnouncementId(i18nVo);
                    ServiceTool.sysAnnouncementI18nService().insert(i18nVo);
                }else{
                    ServiceTool.sysAnnouncementI18nService().insert(i18nVo);
                }
            }
        }
    }

    /**
     * 历史记录
     *
     * @param request
     * @param vGameAnnouncementListVo
     * @param model
     * @return
     */
    @RequestMapping("/gameNoticeHistory")
    public String gameNoticeHistory(HttpServletRequest request, VGameAnnouncementListVo vGameAnnouncementListVo, Model model) {
        vGameAnnouncementListVo.getSearch().setLocal(SessionManager.getLocale().toString());
        vGameAnnouncementListVo = ServiceTool.vGameAnnouncementService().search(vGameAnnouncementListVo);
        vGameAnnouncementListVo.changeReadState(SessionManager.getUserId());
        model.addAttribute("command", vGameAnnouncementListVo);
        return ServletTool.isAjaxSoulRequest(request) ? GAME_NOTICE_HISTORY_URL + "Partial" : GAME_NOTICE_HISTORY_URL;
    }

    /**
     * 历史记录详细
     *
     * @param model
     * @param vGameAnnouncementListVo
     * @return
     */
    @RequestMapping("/gameNoticeDetail")
    public String gameNoticeDetail(Model model, VGameAnnouncementListVo vGameAnnouncementListVo) {
        vGameAnnouncementListVo = ServiceTool.vGameAnnouncementService().search(vGameAnnouncementListVo);
        model.addAttribute("vGameAnnouncementListVo", vGameAnnouncementListVo);
        return GAME_NOTICE_DETAIL_URL;
    }

    /**
     * 查找所有游戏
     * @return
     */
    public List<Map<String, Object>> selectAllGame() {

        GameListVo gameListVo = new GameListVo();
        gameListVo.setProperties(Game.PROP_ID,Game.PROP_NAME);
        return gameService.searchProperties(gameListVo);
    }
}
