//配置弱网资源请求路径及参数

var weekNetworkUrl = {
    loginTokenUrl : 'user/token.html',//token获取
    loginToSetUrl : 'user/voiceSet.html',//声音设置获取
    getSystemSetUrl : 'game/playerSysSet.html',//设置获取
    saveSystemSetUrl : 'game/playerSysSetSave.html',//声音设置保存(musicClass:true, soundClass:true, gameType:'DOU_NIU')
    timedRefreshUrl : 'user/alive.html',//定时刷新
    logout : 'passport/logout.html',//确定退出游戏
    login : 'passport/login.html',//登录游戏

    personalInfoUrl : 'game/playerInfo.html',//获取玩家信息
    playerTreasuresUrl : 'game/statistics/treasures.html',//获取富豪榜数据
    playerProfitUrl : 'game/statistics/profits.html',//获取盈利榜数据  需要gameId 目前为1
    gameRoomListUrl : 'game/rooms.html',//获取房间列表 需要游戏玩法 gameModelName

    announcementUrl : '',//公告内容获取
    rulesUrl : '',//规则内容获取
    recordUrl : 'game/statistics/user/records.html',//记录内容获取  需要"gameId":1, dateClass:0,1,2, pageSize: 20
    accountUrl : 'playerRecharge/playerAccountRecord.html',//账目内容获取  需要"gameId":1, dateClass:0,1,2, pageSize: 20
    trendUrl : 'game/chart/inning.html', //走势图数据获取     需要 "gameModelId": ModelId,  "gameRoomId": roomId
    playerListUrl : 'game/bull100/playerList.html',//玩家列表  需要游戏玩法
}

window.G_DIALOG_URL = weekNetworkUrl;


// var weekNetworkUrl = {
//     loginTokenUrl : '/user/token.html',//token获取
//     loginToSetUrl : '/user/voiceSet.html',//声音设置获取
//     getSystemSetUrl : '/game/playerSysSet.html',//设置获取
//     saveSystemSetUrl : '/game/playerSysSetSave.html',//声音设置保存(musicClass:true, soundClass:true, gameType:'DOU_NIU')
//     timedRefreshUrl : '/user/alive.html',//定时刷新
//     logout : '/passport/logout.html',//确定退出游戏
//
//     personalInfoUrl : '/game/playerInfo.html',//获取玩家信息
//     playerTreasuresUrl : 'game/statistics/treasures.html',//获取富豪榜数据
//     playerProfitUrl : '/game/statistics/profits.html',//获取盈利榜数据  需要gameId 目前为1
//     gameRoomListUrl : '/game/rooms.html',//获取房间列表 需要游戏玩法 gameModelName
//
//     announcementUrl : '',//公告内容获取
//     rulesUrl : '',//规则内容获取
//     recordUrl : '/game/statistics/user/records.html',//记录内容获取  需要"gameId":1, dateClass:0,1,2, pageSize: 20
//     accountUrl : '/playerRecharge/playerAccountRecord.html',//账目内容获取  需要"gameId":1, dateClass:0,1,2, pageSize: 20
//     trendUrl : 'game/chart/inning.html', //走势图数据获取     需要 "gameModelId": ModelId,  "gameRoomId": roomId
//     playerListUrl : 'game/bull100/playerList.html',//玩家列表  需要游戏玩法
// }