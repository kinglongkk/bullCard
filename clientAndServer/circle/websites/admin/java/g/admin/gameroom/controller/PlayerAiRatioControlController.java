package g.admin.gameroom.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import g.admin.gameroom.form.PlayerAiRatioControlForm;
import g.admin.gameroom.form.PlayerAiRatioControlSearchForm;
import g.admin.gameroom.form.VGameRoomSearchForm;
import g.common.tool.DateTimeTool;
import g.model.admin.agent.game.po.UserAgentGame;
import g.model.gameroom.po.GameRoom;
import g.model.gameroom.po.PlayerAiControl;
import g.model.gameroom.po.PlayerAiRatioControl;
import g.model.gameroom.vo.*;
import g.service.gameroom.IGameRoomService;
import g.service.gameroom.IPlayerAiControlService;
import g.service.gameroom.IPlayerAiRatioControlService;
import g.web.admin.session.SessionManager;
import org.apache.commons.lang3.StringUtils;
import org.soul.commons.net.ServletTool;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
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
import java.util.*;


/**
 * Ai与玩家配比设置控制器
 *
 * @author lenovo
 * @time 2017-2-15 14:28:52
 */
@Controller
@RequestMapping("/playerAiRatioControl")
public class PlayerAiRatioControlController extends BaseCrudController<IPlayerAiRatioControlService, PlayerAiRatioControlListVo, PlayerAiRatioControlVo, PlayerAiRatioControlSearchForm, PlayerAiRatioControlForm, PlayerAiRatioControl, Integer> {

    @Autowired
    private IPlayerAiRatioControlService playerAiRatioControlService;
    @Autowired
    private IPlayerAiControlService playerAiControlService;
    @Autowired
    private IGameRoomService gameRoomService;

    @Override
    protected String getViewBasePath() {
        return "/gameroom/airatio/";
    }

    @RequestMapping("/airatiolist")
    public String airatiolist(PlayerAiRatioControlListVo listVo, PlayerAiRatioControlSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        String roomId = request.getParameter("roomId");
//        PlayerAiRatioControlListVo l = new PlayerAiRatioControlListVo();
        List<Integer> ids = playerAiControlService.getAiControlIds(Integer.parseInt(roomId));
        if(ids.size() > 0) {
            listVo.getQuery().setCriterions(new Criterion[]{
                    new Criterion(PlayerAiRatioControl.PROP_AI_PLAYER_CONTROL_ID, Operator.IN, ids)
            });

            listVo = playerAiRatioControlService.search(listVo);
        }
        model.addAttribute("command", listVo);
        model.addAttribute("roomId",roomId);
        return ServletTool.isAjaxSoulRequest(request)  ? "/gameroom/airatio/Index" + "Partial" : "/gameroom/airatio/Index";
    }

    /**
     * 跳转ai基础设置信息界面
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/editAdd")
    public String editAdd( Model model, HttpServletRequest request, HttpServletResponse response){
        String roomId = request.getParameter("roomId");
        PlayerAiControl aiControl= playerAiControlService.getDefaultPlayerAiControl(Integer.valueOf(roomId));
        if(aiControl != null) {
            List<PlayerAiRatioControl> aiRatioControl = playerAiRatioControlService.getDefaultPlayerAiRationControl(aiControl.getId());
            model.addAttribute("aiRatioControl",aiRatioControl);
        }
        model.addAttribute("aiControl",aiControl);
        return "/gameroom/airatio/Edit";
    }

    @RequestMapping("/saveAi")
    @ResponseBody
    public Map persist(PlayerAiControlVo aiVo,HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>(2);
        SysUser sysUser = SessionManager.getUser();
        Integer roomId = aiVo.getResult().getRoomId();
        Integer aiControlId = null;
        PlayerAiControl aiControl= playerAiControlService.getDefaultPlayerAiControl(roomId);
        if(aiControl == null) {
            GameRoom game = gameRoomService.getGameRom(roomId);
            //获取批次最大数据
            String maxBatchId = playerAiControlService.getTodayBatchId(roomId);
            String batchId = "";
            if (StringUtils.isEmpty(maxBatchId)) {
                batchId = DateTimeTool.getYyMmDd(new Date()) + "001";
            } else {
                Long mBa = Long.parseLong(maxBatchId) + 1;
                batchId = String.valueOf(mBa);
            }
            aiVo.getResult().setBatchId(batchId);
            aiVo.getResult().setStatus("10");
            aiVo.getResult().setAiQty(0);
            aiVo.getResult().setControlMode("3");
            aiVo.getResult().setRoomName(game.getName());
            aiVo.getResult().setGameModel(game.getGameModelId());
            aiVo.getResult().setCreateUser(sysUser.getUsername());
            aiVo.getResult().setCreateTime(new Date());
            aiVo.getResult().setUpdateUser(sysUser.getUsername());
            aiVo.getResult().setUpdateTime(new Date());
            aiVo = playerAiControlService.insert(aiVo);
            //发布AI调控消息
            playerAiControlService.publishAiPlayerMessage( aiVo.getResult());
            aiControlId = aiVo.getResult().getId();
        }else{
            playerAiControlService.update(aiVo);
            //发布AI调控消息
            playerAiControlService.publishAiPlayerMessage( aiVo.getResult());
            aiControlId = aiVo.getResult().getId();
        }
        String jsonStr = request.getParameter("aiControlJson");
        JSONArray jsonArray = JSONArray.parseArray(jsonStr);
        List<PlayerAiRatioControl> ratio = new ArrayList<>();
        List<Integer> aiRationId = new ArrayList<Integer>();
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            PlayerAiRatioControl ratioAi = new PlayerAiRatioControl();
            ratioAi.setAiPlayerControlId(aiControlId);
            ratioAi.setPlayerProportionMin(jsonObj.getInteger("playerProportionMin"));
            ratioAi.setPlayerProportionMax(jsonObj.getInteger("playerProportionMax"));
            ratioAi.setAiProportionMin(jsonObj.getInteger("aiProportionMin"));
            ratioAi.setAiProportionMax(jsonObj.getInteger("aiProportionMax"));
            if(jsonObj.getInteger("id") == null) {
                playerAiRatioControlService.saveRationAi(ratioAi);
                aiRationId.add(ratioAi.getId());
            }else {
                aiRationId.add(jsonObj.getInteger("id"));
            }
        }
        //比较删除ai基础设置
        if(aiRationId.size() > 0){
            List<PlayerAiRatioControl> aiRatioControl = playerAiRatioControlService.getDefaultPlayerAiRationControl(aiControlId);
            for(PlayerAiRatioControl p : aiRatioControl){
                boolean bool = false;
                for(Integer d : aiRationId){
                    if(p.getId().equals(d)){
                        bool = true;
                    }
                }
                if(!bool){
                    playerAiRatioControlService.deleteAiRation(p.getId());
                }
            }
        }


        map.put("msg", "保存成功");
        map.put("state", true);
        return map;
    }

    @Override
    public Map delete(PlayerAiRatioControlVo objectVo, Integer id){
        Map map = super.delete(objectVo,id);
//        playerAiRatioControlService.deleteAiControl(id);
        return map;
    }
}