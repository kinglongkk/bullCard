package g.web.player.controller;

import g.model.api.result.ListResults;
import g.model.enums.GameModelCodeEnum;
import g.model.gameSetting.po.SysUserSet;
import g.model.gameSetting.vo.SysUserSetListVo;
import g.model.gameSetting.vo.SysUserSetVo;
import g.model.player.po.UserPlayer;
import g.model.player.po.VSysUser;
import g.model.player.vo.VSysUserListVo;
import g.model.room.po.VRoom;
import g.model.room.vo.VRoomListVo;
import g.service.bet.IVBetDetailService;
import g.service.chesscard.engine.model.Player;
import g.service.common.IUserPlayerService;
import g.service.common.VSysUserService;
import g.service.engine.manager.IDeskManager;
import g.service.engine.manager.IPlayerManager;
import g.service.engine.manager.IRoomManager;
import g.service.gameSetting.ISysUserSetService;
import g.service.match.IMatchService;
import g.service.profitOrder.IPlayerProfitService;
import g.service.room.IVRoomService;
import g.web.player.model.ResultData;
import g.web.player.session.SessionManager;
import org.soul.commons.lang.DateTool;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private IUserPlayerService playerService;

    @Autowired
    private IVBetDetailService detailService;

    @Autowired
    private IMatchService matchService;

    @Autowired
    private VSysUserService sysUserService;

    @Autowired
    private IRoomManager roomManager;

    @Autowired
    private IPlayerManager playerManager;

    @Autowired
    private IDeskManager deskManager;

    @Autowired
    private ISysUserSetService sysUserSetService;

    @Autowired
    private IPlayerProfitService profitService;

    @Autowired
    private IVRoomService vRoomService;

    /**
     * 获取当前游戏的房间列表
     * @param gameModel 游戏玩法
     * @return result
     */
    @RequestMapping("/rooms")
    public Object rooms(String gameModel) {

        List<VRoom> gameRooms = new ArrayList<>();
        Integer bull100GameModelId = 1;
        Integer bullClassicGameModelId = 2;
        Integer bullBaoGameModeId = 3;
        if (gameModel.equals("bull100")) {
            gameRooms = this.roomManager.getRoomsByModelId(bull100GameModelId);
        }
        if (gameModel.equals("bullclassic")) {
            gameRooms = this.roomManager.getRoomsByModelId(bullClassicGameModelId);
        }
        if (gameModel.equals("bullBao")) {
            gameRooms = this.roomManager.getRoomsByModelId(bullBaoGameModeId);
        }
        if (gameRooms.isEmpty()) {
            return new ResultData("哎呀，这个玩法暂未开启，敬请期待！", 0);
        }
        //房间下限额小到大排序
        Collections.sort(gameRooms, gameRoomComparator);
        HashMap<String, Integer>[] rs = new HashMap[gameRooms.size()];
        for (int i = 0; i < gameRooms.size(); i++) {
            VRoom room = gameRooms.get(i);
            HashMap<String, Integer> map = new HashMap<>(3);
            map.put("id",room.getId());
            map.put("minLimitPlayerBlance",room.getMinLimitPlayerBlance());
            map.put("currentPlayerCount",room.getCurrentPlayerCount());
            map.put("maxLimitPlayerNumber",room.getMaxLimitPlayerNumber());
            rs[i] = map;
        }
        return new ResultData(rs);
    }
    
    private final Comparator<VRoom> gameRoomComparator = new Comparator<VRoom>() {

        @Override
        public int compare(VRoom room1, VRoom room2) {
            int rs = room1.getMinLimitPlayerBlance().compareTo(room2.getMinLimitPlayerBlance());
            if(rs == 0)return room1.getId().compareTo(room2.getId());
            return rs;
        }
    };

    /**
     * 玩家在当前游戏的投注记录
     * @param gameId
     * @param dateClass
     * @param pageNo
     * @param pageSize
     * @return result
     */
    @RequestMapping("/statistics/user/records")
    public Object statisticsUserRecords(Integer gameId, Integer dateClass, Integer pageNo, Integer pageSize) {

        //设置默认分页数据
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        String[] messages = {"您今天无投注记录！", "您昨天无投注记录！", "您前天无投注记录！"};
        String message;
        try {
            message = messages[dateClass];
        }catch (ArrayIndexOutOfBoundsException exception) {
            return new ResultData("非法查询", 0);
        }
        //日期设定
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -dateClass);
        Date queryDay = calendar.getTime();
        String dateString = DateTool.formatDate(queryDay, DateTool.FMT_HYPHEN_DAY) + " 00:00:00";
        Date endTime = DateTool.parseDate(dateString, DateTool.FMT_HYPHEN_DAY_CLN_SECOND);
        //查询所需参数
        Map map = new HashMap();
        map.put("gameId", gameId);
        map.put("playerId", SessionManager.getUserId());
        map.put("startTime", DateTool.addDays(endTime, -1));
        map.put("endTime", endTime);
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        //查询结果
        ListResults result = new ListResults();
        List<Map<String, Object>> listResults = detailService.selectPlayerBetRecords(map);
        if (listResults == null || listResults.isEmpty()) {
            return new ResultData(message, 0);
        }
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setPageTotalSize(detailService.selectPlayerBetTotalRecords(map));
        result.setDatas(listResults);
        return new ResultData(result);
    }

    /**
     * 玩家在当前游戏的盈利额-盈利榜
     * @param gameId
     * @param pageNo
     * @param pageSize
     * @return result
     */
    @RequestMapping("/statistics/profits")
    public Object statisticsProfits(Integer gameId, Integer pageNo, Integer pageSize) {

        //设置默认分页数据
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Map map = new HashMap();
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        List<Map<String, Object>> result = profitService.selectPlayerProfitOrder(map);
        int resultSize = result.size();
        if (resultSize < 1) {
            return new ResultData("哎呀，暂无盈利榜数据！", 0);
        }
        //账户信息保密
        result = playerService.hiddenUsername(result);
        return  new ResultData(result);
    }

    /**
     * 玩家在当前游戏的账户金额-富豪榜
     * @param pageNo
     * @param pageSize
     * @return result
     */
    @RequestMapping("/statistics/treasures")
    public Object statisticsTreasures(Integer pageNo, Integer pageSize) {

        //设置默认分页数据
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Map map = new HashMap();
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        //查找富豪榜数据
        List<Map<String, Object>> result = playerService.selectPlayerOrderByWalletBalance(map);
        if (result == null || result.isEmpty()) {
            return new ResultData("哎呀，暂无富豪榜数据！", 0);
        }
        //账户信息保密
        result = playerService.hiddenUsername(result);
        return new ResultData(result);
    }

    /**
     * 当前房间的胜负走势图
     * @param gameModelId 游戏玩法id
     * @param gameRoomId 游戏房间id
     * @return result
     */
    @RequestMapping("/chart/inning")
    public Object chartInning(Integer gameModelId, Integer gameRoomId) {

        //判断游戏玩法、游戏房间
        VRoomListVo listVo = new VRoomListVo();
        listVo.getSearch().setId(gameRoomId);
        listVo.getSearch().setGameModelId(gameModelId);
        listVo = vRoomService.search(listVo);
        if (listVo.getResult() != null && !listVo.getResult().isEmpty()) {
            //查询参数
            String dateString = DateTool.formatDate(new Date(), DateTool.FMT_HYPHEN_DAY) + " 00:00:00";
            Date endTime = DateTool.parseDate(dateString, DateTool.FMT_HYPHEN_DAY_CLN_SECOND);
            Map map = new HashMap();
            map.put("playerId", SessionManager.getUserId());
            map.put("modelId", gameModelId);
            map.put("roomId", gameRoomId);
            map.put("beginTime", DateTool.addDays(endTime, -1));
            map.put("endTime", endTime);
            //走势结果
            List<Map<String, Object>> result = null;
            GameModelCodeEnum modelCodeEnum = GameModelCodeEnum.enumOf(listVo.getResult().get(0).getGameModelCode());
            switch (modelCodeEnum) {
                case FIGHT:
                    //经典看牌
                    return new ResultData("哎呀，该玩法暂未开放！", 0);
                case BET:
                    //百人大战
                    // TODO: 2017/4/7 black 消除不需要列名的映射
                    result = matchService.selectBull100Trend(map);
                    break;
                case GRAB:
                    //押宝大战
                    result = matchService.selectGrabTrend(map);
                    break;
            }
            if(result == null || result.isEmpty()){
                return new ResultData("哎呀，该房间暂无走势记录！", 0);
            }
            return new ResultData(result);
        } else {
            return new ResultData("哎呀，该玩法或者房间暂未开放！", 0);
        }
    }

    /**
     * 百人大战 获取吃瓜群众列表
     * @return result
     */
    @RequestMapping("/bull100/playerList")
    public Object playerList() {

        Integer userId = SessionManager.getUserId();
        Player player = playerManager.get(userId);
        Integer deskId = player.getDeskId();
        if (deskId == null) {
            return new ResultData("您当前未进入桌子，无法获取玩家列表!", 0);
        }
        Set<String> result = deskManager.getDeskUsers(deskId);
        List<Integer> userIds = new ArrayList<>();
        if(result.isEmpty()){
            return new ResultData("哎呀，好冷清，连吃瓜群众都没有！", 0);
        }
        for (String userIdStr : result) {
            Integer tempUserId = Integer.valueOf(userIdStr);
            Integer dealerUserId = deskManager.getDealerId(deskId);
            if(dealerUserId != null  && dealerUserId.equals(tempUserId)){
                userIds.add(0,tempUserId);
            }else{
                userIds.add(tempUserId);
            }
        }
        List<UserPlayer> userPlayers = this.playerManager.getByIds(userIds);
        HashMap<String, Object>[] rs = new HashMap[userPlayers.size()];
        for (int i = 0; i < userPlayers.size(); i++) {
            UserPlayer r = userPlayers.get(i);
            HashMap<String, Object> map = new HashMap<>(4);
            map.put("id",r.getId());
            map.put("coin",r.getCoin());
            map.put("avatarUrl",r.getAvatarUrl());
            map.put("nickname",r.getNickname());

            rs[i] = map;
        }
        return new ResultData(rs);
    }

    /**
     * 获取登陆玩家的信息
     * @return result
     */
    @RequestMapping("/playerInfo")
    public Object playerIfo() {

        VSysUserListVo listVo = new VSysUserListVo();
        listVo.setProperties(VSysUser.PROP_WALLET_BALANCE, VSysUser.PROP_AVATAR_URL,
                VSysUser.PROP_ID, VSysUser.PROP_NICKNAME);
        listVo.getQuery().setCriterions(new Criterion[]{

            new Criterion(VSysUser.PROP_ID, Operator.EQ, SessionManager.getUserId())
        });
        List<Map<String, Object>> result = sysUserService.searchProperties(listVo);
        for (Map<String, Object> objectMap : result) {
            objectMap.put(UserPlayer.PROP_COIN,objectMap.get(VSysUser.PROP_WALLET_BALANCE));
            objectMap.remove(VSysUser.PROP_WALLET_BALANCE);
        }
        return new ResultData(result);
    }

    /**
     * 获取玩家声音设置，第一次为默认开
     * @param gameType 游戏类型
     * @return map
     */
    @RequestMapping("/playerSysSet")
    public Object playerSysSet(String gameType) {

        int code = 0;
        String userName = SessionManager.getUserName();
        Map map = new HashMap();
        SysUserSetListVo listVo = new SysUserSetListVo();
        listVo.setProperties(SysUserSet.PROP_MUSIC,SysUserSet.PROP_SOUND);
        listVo.getQuery().setCriterions(new Criterion[]{

                new Criterion(SysUserSet.PROP_PLAYER_ID, Operator.EQ, SessionManager.getUserId()),
                new Criterion(SysUserSet.PROP_GAMETYPE, Operator.EQ, gameType)
        });
        List<Map<String, Object>> result = sysUserSetService.searchProperties(listVo);
        if (result.isEmpty()) {
            map.put("code",code);
            map.put("userName",userName);
            return map;
        }else {
            map.put("userName",userName);
            map.put("result",result);
            return new ResultData(map);
        }
    }

    /**
     * 保存玩家声音设置
     * @param musicClass 音乐
     * @param soundClass 声音
     * @param gameType 游戏类型
     * @return map
     */
    @RequestMapping("/playerSysSetSave")
    public Object playerSysSetSave(String musicClass, String soundClass, String gameType) {

        String closeClass = "Set_close";
        boolean musicSwitch = false;
        boolean soundSwitch = false;
        if (closeClass.equals(musicClass)) {
            musicSwitch = false;
        }else {
            musicSwitch = true;
        }
        if (closeClass.equals(soundClass)) {
            soundSwitch = false;
        }else {
            soundSwitch = true;
        }
        SysUserSetVo sysUserSetVo = new SysUserSetVo();
        sysUserSetVo.getSearch().setPlayerId(SessionManager.getUserId());
        sysUserSetVo.getSearch().setGametype(gameType);
        //更新设置
        Map map = new HashMap();
        if (sysUserSetService.isExists(sysUserSetVo)) {
            SysUserSet sysUserSet = sysUserSetService.findByGameType(sysUserSetVo);
            sysUserSet.setMusic(musicSwitch);
            sysUserSet.setSound(soundSwitch);
            sysUserSetVo.setResult(sysUserSet);
            sysUserSetService.update(sysUserSetVo);
            map.put("success",true);
            return map;
        }
        //第一次设置保存
        SysUserSet sysUserSet = new SysUserSet();
        sysUserSet.setGametype(gameType);
        sysUserSet.setMusic(musicSwitch);
        sysUserSet.setSound(soundSwitch);
        sysUserSet.setPlayerId(SessionManager.getUserId());
        sysUserSetVo.setResult(sysUserSet);
        sysUserSetService.insert(sysUserSetVo);
        map.put("success",true);
        return map;
    }

}