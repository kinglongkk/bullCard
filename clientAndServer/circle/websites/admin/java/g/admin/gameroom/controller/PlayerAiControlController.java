package g.admin.gameroom.controller;

import g.admin.gameroom.form.PlayerAiControlForm;
import g.admin.gameroom.form.PlayerAiControlSearchForm;
import g.common.tool.DateTimeTool;
import g.model.gameroom.po.GameRoom;
import g.model.gameroom.po.PlayerAiControl;
import g.model.gameroom.vo.PlayerAiControlListVo;
import g.model.gameroom.vo.PlayerAiControlVo;
import g.service.gameroom.IGameRoomService;
import g.service.gameroom.IPlayerAiControlService;
import g.service.gameroom.IPlayerAiRatioControlService;
import g.web.admin.session.SessionManager;
import org.apache.commons.lang3.StringUtils;
import org.soul.commons.net.ServletTool;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.validation.form.annotation.FormModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Ai玩家设置控制器
 *
 * @author lenovo
 * @time 2017-2-15 15:59:48
 */
@Controller
//region your codes 1
@RequestMapping("/playerAiControl")
public class PlayerAiControlController extends BaseCrudController<IPlayerAiControlService, PlayerAiControlListVo, PlayerAiControlVo, PlayerAiControlSearchForm, PlayerAiControlForm, PlayerAiControl, Integer> {
//endregion your codes 1

    @Autowired
    private IGameRoomService gameRoomService;

    @Autowired
    private IPlayerAiRatioControlService playerAiRatioControlService;

    @Autowired
    private IPlayerAiControlService playerAiControlService;

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/gameroom/ai/";
        //endregion your codes 2
    }

    @RequestMapping("/ailist")
    public String airatiolist(PlayerAiControlListVo listVo, PlayerAiControlSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        String roomId = request.getParameter("roomId");
        listVo.getQuery().setCriterions(new Criterion[]{
                new Criterion(PlayerAiControl.PROP_ROOM_ID, Operator.EQ, Integer.parseInt(roomId)),
                new Criterion(PlayerAiControl.PROP_CONTROL_MODE, Operator.NE, "3")//排除AI基础设置
        });
        Order orders = Order.desc("createTime");
        listVo = playerAiControlService.search(listVo);
        model.addAttribute("command", listVo);
        model.addAttribute("roomId",roomId);
        return ServletTool.isAjaxSoulRequest(request)  ? "/gameroom/ai/Index" + "Partial" : "/gameroom/ai/Index";
    }

    @Override
    public Map delete(PlayerAiControlVo objectVo, Integer id){
        Map map = super.delete(objectVo,id);
       playerAiRatioControlService.deleteAiControl(id);
        return map;
    }

    @RequestMapping("/saveAi")
    @ResponseBody
    public Map persist(PlayerAiControlVo aiVo, @FormModel("result") @Valid PlayerAiControlForm form, BindingResult result) {
        //获取当前登录用户信息
        SysUser sysUser = SessionManager.getUser();
        if(aiVo.getResult().getId() == null){
            Integer roomId = aiVo.getResult().getRoomId();
            //保存之前先获取状态房间内的状态为10生效的数据
            PlayerAiControl playerAi = playerAiControlService.getPlayerAiControl(roomId);
            if(playerAi != null) {
                //发布AI调控消息
                playerAi.setStatus("20");
                playerAiControlService.publishAiPlayerMessage(playerAi);
            }
            //保存之前在把房间内调控状态设置为失效
            playerAiControlService.stopAi(roomId,sysUser.getUsername());
            //获取批次最大数据
            String maxBatchId = playerAiControlService.getTodayBatchId(roomId);
            String batchId="";
            if(StringUtils.isEmpty(maxBatchId)){
                 batchId = DateTimeTool.getYyMmDd(new Date())+"001";
            }else{
                Long mBa = Long.parseLong(maxBatchId)+1;
                batchId = String.valueOf(mBa);
            }
            GameRoom game =gameRoomService.getGameRom(roomId);
            aiVo.getResult().setStatus("10");
            aiVo.getResult().setRoomName(game.getName());
            aiVo.getResult().setCreateUser(sysUser.getUsername());
            aiVo.getResult().setCreateTime(new Date());
            aiVo.getResult().setUpdateUser(sysUser.getUsername());
            aiVo.getResult().setUpdateTime(new Date());
            aiVo.getResult().setGameModel(roomId);
            aiVo.getResult().setBatchId(batchId);

            //playerAiControlService.s
        }else{
            aiVo.getResult().setCreateUser(sysUser.getUsername());
            aiVo.getResult().setCreateTime(new Date());
            aiVo.getResult().setUpdateUser(sysUser.getUsername());
            aiVo.getResult().setUpdateTime(new Date());
        }
        Map map = super.persist(aiVo, form, result);
        //发布AI调控消息
        playerAiControlService.publishAiPlayerMessage( aiVo.getResult());
        return map;

    }

    /**
     * 停止调控
     * @return
     */
    @RequestMapping("/stopAi")
    @ResponseBody
    public Map stopAi(Integer roomId){
        Map map = new HashMap();
        SysUser sysUser = SessionManager.getUser();
        //保存之前先获取状态房间内的状态为10生效的数据
        PlayerAiControl playerAi = playerAiControlService.getPlayerAiControl(roomId);
        if(playerAi != null) {
            //发布AI调控消息
            playerAi.setStatus("20");
            playerAiControlService.publishAiPlayerMessage(playerAi);
        }
        //更新调控状态
        playerAiControlService.stopAi(roomId,sysUser.getUsername());
        map.put("msg", "操作成功");
        map.put("state", true);
        return map;
    }

}