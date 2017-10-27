package g.service.chesscard.engine.manager;

import com.sleepycat.persist.impl.SimpleFormat;
import g.model.announcement.po.GameAnnouncement;
import g.model.announcement.vo.GameAnnouncementVo;
import g.model.chesscard.mo.UserBetResult;
import g.model.gamemodel.po.GameModel;
import g.model.gamemodel.vo.GameModelListVo;
import g.model.gamemodel.vo.GameModelVo;
import g.model.gameroom.po.GameRoom;
import g.service.announcement.GameAnnouncementService;
import g.service.chesscard.engine.model.Room;
import g.service.chesscard.engine.model.SettleResult;
import g.service.engine.manager.IBroadcastMessageManager;
import g.service.game.GameService;
import g.service.gamemodel.GameModelService;
import g.service.gameroom.GameRoomService;
import org.soul.commons.lang.DateTool;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.vo.SysUserListVo;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.soul.service.security.privilege.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPubSub;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by solo on 2017/3/18.
 *
 * 把所有生成的消息保存到数据库表  gameAnnouncement 中，然后然后调度去统一去分配发送的消息
 *
 */

public class BroadcastMessageManager implements IBroadcastMessageManager{

    final int maxMsgNum = 450;          //每小时的最多发送消息记录数

    @Autowired
    private GameAnnouncementService gameAnnouncementService;

    @Autowired
    private GameRoomService gameRoomService;

    @Autowired
    private GameModelService gameModelService;

    @Autowired
    private SysUserService sysUserService;

    //游戏结算生成的消息
    public void createMsgFromGameSettle(SettleResult settleResult, int roomId) {
        String content = "";
        Map<String,String> map = new HashMap<String,String>();      //存放替换消息模板的信息

        GameRoom gameRoom = gameRoomService.getGameRom(roomId);
        GameModel gameModel = getGameModel(gameRoom.getGameModelId());      //获取游戏模块名称信息
        double num = gameRoom.getSendMsgCondition()*gameRoom.getMaxLimitPlayerNumber();    //是否生成公告消息的平衡值
        GameAnnouncementVo gameAnnouncementVo = new GameAnnouncementVo();
        GameAnnouncement gameAnnouncement = new GameAnnouncement();

        List<UserBetResult> list = settleResult.getUserBetResultList();
        for(UserBetResult ubr : list){

            if(ubr.getCoin() >= num){
                ubr.getUserId();
                gameRoom.getName();
                //组合赢得奖励的消息模板
                map.put("name",getSysUserById(ubr.getUserId()).getUsername());
                map.put("gameName",gameModel.getName());
                map.put("inCoin",ubr.getIncCoin()+"");
                content = getMsgTemplate("GAME_SETTLE",map);        //获取组合后的完整消息

                if(null != content && content.length() > 0) {
                    String str = DateTool.currentDate("yyyy-MM-dd hh:00:00");
                    System.out.println(str + ":" + new Date().getTime());
                    gameAnnouncement.setPublishTime(new Date());       //发布时间为当前时间
                    gameAnnouncement.setPublishUserId(1);              //发布人ID默认admin
                    gameAnnouncement.setAnnouncementType("banner");   //公告类型（banner跑马灯，popup弹窗）
                    gameAnnouncement.setPublishUserName("admin");     //发布人账号
                    gameAnnouncement.setTitle(content);                 //发布的标题
                    gameAnnouncement.setContent(content);               //发布的内容
                    gameAnnouncement.setValidityStartTime(new Date());  //有效开始时间
                    gameAnnouncement.setSaveTime(new Date());           //保存时间
                    gameAnnouncement.setValidityEndTime(DateTool.addMinutes(new Date(),getEffective()));//有效结束时间
                    gameAnnouncement.setRepeat(false);                //是否重复
                    gameAnnouncement.setRepeatTime(0);                //间隔时间
                    gameAnnouncement.setRepeatUnit("");               //间隔时间单位
                    gameAnnouncement.setGameId(getGameIds());         //游戏ID
                    gameAnnouncement.setGameName(gameModel.getName());     //游戏名称
                    gameAnnouncement.setLocal("zh_CN");
                    gameAnnouncement.setMsgType("WINGAME");         //后台赢得比赛的公告
                    gameAnnouncement.setIsSend(0);                  //是否发送
                    gameAnnouncement.setGainGoldNum(ubr.getIncCoin());      //赢得的金币
                    gameAnnouncementVo.setResult(gameAnnouncement);
//                    gameAnnouncementService.insert(gameAnnouncementVo);
                    saveMsg(gameAnnouncementVo);
                }
            }
        }
    }

    //产生的消息有效时长,以分钟为单位
    private int getEffective() {
        int effe = 60;
        return effe;
    }

    //发送公告到哪些游戏模块
    private String getGameIds() {
        String gameIds = "1,29";
        return gameIds;
    }

    /**
     *  根据传入的参数info,获取完整的消息模板
     *
     * info参数值为
     *  GAME_SETTLE   :  游戏结算消息模板
     *  GAME_CHEAT    :  作弊消息模板
     *
     * map的参数
     *      当info为GAME_SETTLE （玩家赢得一定金币产生的信息）:map有3个有效key （name:玩家姓名，game:游戏模块名,num:赢得的金币数量）
     */
    private String getMsgTemplate(String info,Map<String,String> map) {
        String content = "天啊！{name}在{gameName}，单局赢取了{inCoin}金币，快快膜拜吧";
        switch (info){
            case "GAME_SETTLE":
                content = content.replace("{name}",map.get("name"));
                content = content.replace("{gameName}",map.get("gameName"));
                content = content.replace("{inCoin}",map.get("inCoin"));
                break;
            default:
                content = null;
        }
        return content;
    }

    //获取用户信息
    private SysUser getSysUserById(int userId) {
        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.getSearch().setId(userId);
        sysUserVo = sysUserService.get(sysUserVo);
        return sysUserVo.getResult();
    }

    //根据游戏模块ID获取游戏模块信息
    private GameModel getGameModel(int gameModelId) {

        GameModelVo gameModelVo = new GameModelVo();
        gameModelVo.getSearch().setId(gameModelId);
        gameModelVo = gameModelService.get(gameModelVo);
        return gameModelVo.getResult();
    }

    //把生成的消息保存到数据库表  gameAnnouncement
    private void saveMsg(GameAnnouncementVo gameAnnouncementVo){
        int msgTotal = 0;
        //判断当前时间节点消息数量是否已经达到上限
        Date stateTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:00:00");
        try {
            stateTime = sdf.parse(sdf.format(stateTime));
        } catch (ParseException e) {

        }
        Date endTime = DateTool.addHours(stateTime,1);
        int count = gameAnnouncementService.selectCountByPublishTime(stateTime,endTime);
        if(count < maxMsgNum) {
            gameAnnouncementService.insert(gameAnnouncementVo);
        }
    }
}
