package g.admin.gameroomconfig.controller;

import g.admin.gameroomconfig.form.GameRoomJackpotForm;
import g.admin.gameroomconfig.form.GameRoomJackpotSearchForm;
import g.model.admin.gameroomconfig.po.GameRoomConfigBull100;
import g.model.admin.gameroomconfig.vo.GameRoomConfigBull100ListVo;
import g.model.admin.gameroomconfig.vo.GameRoomConfigBull100Vo;
import g.model.gameroom.po.GameRoom;
import g.model.gameroom.vo.GameRoomListVo;
import g.model.gameroom.vo.GameRoomVo;
import g.service.admin.gameroomconfig.IGameRoomConfigBull100Service;
import g.service.bet.IVBetDetailService;
import g.service.gameroom.IGameRoomService;
import org.apache.commons.collections.map.HashedMap;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.net.ServletTool;
import org.soul.commons.spring.utils.SpringTool;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.validation.form.annotation.FormModel;
import org.soul.web.validation.form.js.JsRuleCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.nosql.redis.JedisTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

/**
 * 奖池配置
 * Created by black on 2017/3/23.
 */
@Controller
//region your codes 1
@RequestMapping("/gameRoomJackpot")
public class GameRoomJackpotController extends BaseCrudController<IGameRoomConfigBull100Service, GameRoomConfigBull100ListVo, GameRoomConfigBull100Vo, GameRoomJackpotSearchForm, GameRoomJackpotForm, GameRoomConfigBull100, Integer> {
//endregion your codes 1

    public final static String LIST_URL = "Index";
    public final static String EDIT_URL = "Edit";

    public final static String ROOM = "room";
    public final static String R = "r";

    @Autowired
    private IVBetDetailService detailService;

    @Autowired
    private IGameRoomService roomService;

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/gameroomjackpot/";
        //endregion your codes 2
    }

    public String list(GameRoomConfigBull100ListVo listVo, @FormModel("search") @Valid GameRoomJackpotSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        listVo = getService().selectRoomList(listVo);
        model.addAttribute("command", listVo);
        if (ServletTool.isAjaxSoulRequest(request)) {
            return getViewBasePath() + LIST_URL + "Partial";
        } else {
            return getViewBasePath() + LIST_URL;
        }
    }

    public String edit(GameRoomConfigBull100Vo objectVo, Integer id, Model model, HttpServletRequest request, HttpServletResponse response) {

        GameRoomConfigBull100ListVo listVo = new GameRoomConfigBull100ListVo();
        listVo.getSearch().setId(id);
        listVo = getService().search(listVo);
        int roomId = listVo.getResult().get(0).getRoomId();
        objectVo.setResult(listVo.getResult().get(0));
        //获取变量奖池数据
        GameRoomListVo roomListVo = new GameRoomListVo();
        roomListVo.getSearch().setId(roomId);
        roomListVo = roomService.search(roomListVo);
        if (roomListVo.getResult() != null && !roomListVo.getResult().isEmpty()) {
            objectVo.getResult().setJackpot(roomListVo.getResult().get(0).getJackpot());
            objectVo.getResult().setJackpotOverflow(roomListVo.getResult().get(0).getJackpotOverflow());
        }
        //设置赛事截止时间
        Date confirmTime = detailService.selectLastConfirmTime();
        objectVo.getResult().setConfirmTime(confirmTime == null ? new Date() : confirmTime);
        model.addAttribute("command", objectVo);
        if (ServletTool.isAjaxSoulRequest(request)) {
            return getViewBasePath() + EDIT_URL + "Partial";
        } else {
            objectVo.setValidateRule(JsRuleCreator.create(GameRoomJackpotForm.class, "result"));
            return getViewBasePath() + EDIT_URL;
        }
    }

    public Map persist(GameRoomConfigBull100Vo objectVo, @FormModel("result") @Valid GameRoomJackpotForm form, BindingResult result) {
        Map map = checkForm(objectVo);
        if(map != null) {
            return map;
        }
        //更新Redis的奖池数据
        JedisTemplate template = (JedisTemplate) SpringTool.getBean("jedisTemplateGame");
        String key = CacheKey.getCacheKey(ROOM, R, objectVo.getResult().getRoomId());
        template.hset(key, GameRoomConfigBull100.PROP_JACKPOT_SUM, objectVo.getResult().getJackpotSum().toString());
        template.hset(key, GameRoomConfigBull100.PROP_MAX_LIMIT_GAME_LOSE, objectVo.getResult().getMaxLimitGameLose().toString());
        template.hset(key, GameRoomConfigBull100.PROP_MIN_JACKPOT_LIMIT, objectVo.getResult().getMinJackpotLimit().toString());
        template.hset(key, GameRoomConfigBull100.PROP_MAX_JACKPOT_LIMIT, objectVo.getResult().getMaxJackpotLimit().toString());
        template.hset(key, GameRoomConfigBull100.PROP_MAX_JACKPOT_AMATCH, objectVo.getResult().getMaxJackpotAmatch().toString());
        template.hset(key, GameRoom.PROP_JACKPOT, objectVo.getResult().getJackpotSum().toString());
        template.hset(key, GameRoom.PROP_JACKPOT_OVERFLOW, "0");
        if (!result.hasErrors()) {
            //更新数据库奖池信息
            objectVo.setProperties(GameRoomConfigBull100.PROP_JACKPOT_SUM, GameRoomConfigBull100.PROP_MAX_LIMIT_GAME_LOSE,
                    GameRoomConfigBull100.PROP_MIN_JACKPOT_LIMIT, GameRoomConfigBull100.PROP_MAX_JACKPOT_LIMIT,
                    GameRoomConfigBull100.PROP_MAX_JACKPOT_AMATCH);
            getService().updateOnly(objectVo);
            //更新变量奖池信息
            GameRoomVo gameRoomVo = new GameRoomVo();
            gameRoomVo.setProperties(GameRoom.PROP_JACKPOT, GameRoom.PROP_JACKPOT_OVERFLOW);
            GameRoom room = new GameRoom();
            room.setId(objectVo.getResult().getRoomId());
            room.setJackpot(objectVo.getResult().getJackpotSum());
            room.setJackpotOverflow(0L);
            gameRoomVo.setResult(room);
            roomService.updateOnly(gameRoomVo);
        }else{
            objectVo.setSuccess(false);
        }
        return getVoMessage(objectVo);
    }

    public Map<String,Object> checkForm(GameRoomConfigBull100Vo objectVo) {
        Map<String,Object> map = null;
        if(objectVo.getResult().getMaxJackpotLimit() < objectVo.getResult().getJackpotSum()) {
            map = new HashedMap();
            map.put("msg","奖池最高积累金额不能小于奖池总额");
            map.put("state",false);
            return map;
        }
        if(objectVo.getResult().getMinJackpotLimit() > objectVo.getResult().getMaxLimitGameLose()) {
            map = new HashedMap();
            map.put("msg","最低下限金额不能大于最高可输金额");
            map.put("state",false);
            return map;
        }
        if(objectVo.getResult().getMaxLimitGameLose() > objectVo.getResult().getMaxJackpotLimit()) {
            map = new HashedMap();
            map.put("msg","当局最高可输金额不能大于奖池最高累计金额");
            map.put("state",false);
            return map;
        }

        return map;
    }
}
