package g.admin.gameroomconfig.controller;

import org.apache.commons.collections.map.HashedMap;
import org.apache.velocity.util.StringUtils;
import org.soul.web.controller.BaseCrudController;
import g.service.admin.gameroomconfig.IGameRoomConfigBull100Service;
import g.model.admin.gameroomconfig.po.GameRoomConfigBull100;
import g.model.admin.gameroomconfig.vo.GameRoomConfigBull100ListVo;
import g.model.admin.gameroomconfig.vo.GameRoomConfigBull100Vo;
import g.admin.gameroomconfig.form.GameRoomConfigBull100SearchForm;
import g.admin.gameroomconfig.form.GameRoomConfigBull100Form;
import org.soul.web.validation.form.annotation.FormModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import g.web.admin.tools.ServiceTool;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


/**
 * 游戏房间配置信息表控制器
 *
 * @author lenovo
 * @time 2016-12-17 17:20:19
 */
@Controller
//region your codes 1
@RequestMapping("/gameRoomConfigBull100")
public class GameRoomConfigBull100Controller extends BaseCrudController<IGameRoomConfigBull100Service, GameRoomConfigBull100ListVo, GameRoomConfigBull100Vo, GameRoomConfigBull100SearchForm, GameRoomConfigBull100Form, GameRoomConfigBull100, Integer> {

    @Autowired
    private IGameRoomConfigBull100Service gameRoomConfigBull100Service;

    public static int counter = 0;

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/gameroomconfig/";
        //endregion your codes 2
    }

    @Override
    public Map persist(GameRoomConfigBull100Vo objectVo, @FormModel("result") @Valid GameRoomConfigBull100Form form, BindingResult result) {
            Map<String, Object> map = new HashedMap();
           if(objectVo.getResult().getId() == null){
               gameRoomConfigBull100Service.insert(objectVo);
               map.put("msg", "保存成功");
               map.put("state", true);
           }else{
               if(objectVo.getResult().getRoomId() == null){
                   map.put("msg", "房间ID不允许为空");
                   map.put("state", false);
               }else{
                   String betChip = objectVo.getResult().getBetChip();
                   if(org.apache.commons.lang3.StringUtils.isEmpty(betChip)){
                       map.put("msg", "请选择投注码");
                       map.put("state", false);
                       return map;
                   }
                   if(betChip.split(",").length<5){
                       map.put("msg", "至少选择5个筹码");
                       map.put("state", false);
                       return map;
                   }
                   GameRoomConfigBull100 vo = gameRoomConfigBull100Service.getGameRoomConfigBull100(objectVo.getResult().getRoomId());
                   vo.setDealerBlance(objectVo.getResult().getDealerBlance());
                   vo.setDealerBlanceTip(objectVo.getResult().getDealerBlanceTip());
                   vo.setDealerBlanceQuit(objectVo.getResult().getDealerBlanceQuit());
                   vo.setBetTimes(objectVo.getResult().getBetTimes());
                   vo.setBetChip(objectVo.getResult().getBetChip());
                   gameRoomConfigBull100Service.updateRoomConfig(vo);
                   map.put("msg", "更新成功");
                   map.put("state", true);
               }
           }
            return map;

    }

    /**
     * 计算字符串包含几个指定字符
     * @param str1
     * @param str2
     * @return
     */
    public static int countStr(String str1, String str2) {
        if (str1.indexOf(str2) == -1) {
            return 0;
        } else if (str1.indexOf(str2) != -1) {
            counter++;
            countStr(str1.substring(str1.indexOf(str2) +
                    str2.length()), str2);
            return counter;
        }
        return 0;
    }

}