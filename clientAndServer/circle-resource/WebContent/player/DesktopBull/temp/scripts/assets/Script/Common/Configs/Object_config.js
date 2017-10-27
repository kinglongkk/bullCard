"use strict";
cc._RF.push(module, 'b6eb4DffzpAE7fb1TCIU67g', 'Object_config');
// Script/Common/Configs/Object_config.js

"use strict";

//对象内容枚举

window.G_OBJ = window.G_OBJ || {};

//百人投注信息
G_OBJ.data_bullBetting = function () {
    return {
        //来源于桌子的信息
        tableIndex: null, //桌子索引
        posList: null, //金币的位置列表
        //来源与玩家的信息
        startPos: null, //金币开始移动的位置
        goldValue: null, //投注的金币实际数值
        goldNum: null, //飞行在场景中的金币数量
        //是否是操作位投注
        isMine: null
    };
};
//押宝投注信息
G_OBJ.data_grabBetting = function () {
    return {
        //来源于桌子的信息
        tableIndex: null, //桌子索引
        targetPos: null, //投注区域的位置
        callFunc: null, //确定移动金币到投注区域后的回调
        //来源与玩家的信息
        startPos: null, //金币开始移动的位置
        goldValue: null, //投注的金币实际数值
        goldNum: null, //飞行在场景中的金币数量
        //是否是操作位投注
        isMine: null
    };
};
//押宝显示卡牌信息
G_OBJ.data_grabPokerInfo = function () {
    return {
        pokerInfoList: null, //卡牌信息列表
        pokerResult: null, //这副牌的结果值
        isFlyEffect: null, //是否播放飞行效果
        startPokerIndex: null, //显示的卡牌索引，从第几个开始显示
        callFunc: null };
};
//从大厅进房需要传输的信息
G_OBJ.data_inHomeData = function () {
    return {
        net_inHomeData: null, //进房请求信息
        net_resetHomeData: null };
};
//飞行金币需要的数据
G_OBJ.data_flyGold_playerToTable = function () {
    return {
        tableIndex: null, //投注区域索引
        goldNum: null, //投注飞行的金币数量
        startPos: null, //金币飞行的启示位置
        callFunc: null };
};
//飞行金币回收到玩家需要的数据
G_OBJ.data_flyGold_tableToPlayer = function () {
    return {
        tableIndex: null, //投注区域索引
        goldNum: null, //投注飞行的金币数量
        targetPos: null };
};
//监听玩家自己金币变化的时候传递的信息
G_OBJ.data_nbSelf = function () {
    return {
        balance: null, //余额
        usableBalance: null };
};
//切换页面的插件脚本初始化需要的数据    (contentlist和prefablist需要一一对应)
G_OBJ.data_dialog_exchangePages = function () {
    return {
        leftDealScrollComp: null, //左边滚动层
        leftPrefab: null, //左边选项预制体
        leftContentList: null, //左边选项内容列表
        leftClickFunc: null, //左边的回调，会传入对应的index
        rightDealScrollComp: null, //右边滚动层
        rightPrefabList: null, //右边内容的预制体列表
        isStopSetRightContent: null };
};
//赛事结算信息
G_OBJ.data_matchEnd = function () {
    return {
        pokersInfoList: null, //所有卡牌牌面显示信息
        pokersResultList: null, //所有卡牌结果信息
        othersInfo: null, //其他座位上的玩家信息
        dealerInfo: null, //庄家信息
        myselfInfo: null };
};
//结算的时候自己的信息
G_OBJ.data_selfMatchEndData = function () {
    return {
        changeGold: null, //结算时候改变的金币  (传入的值必须是整数)
        leaveGold: null };
};
//http请求的数据
G_OBJ.data_httpSend = function () {
    return {
        url: null, //路径
        sendData: null, //发送的数据
        callFunc: null, //接收到数据的回调
        xhrType: null };
};

//记录请求时发送的对象
G_OBJ.data_requestRecord = function () {
    return {
        gameId: null, //游戏id
        dateClass: null, //请求哪个时间段的数据  0是当前，1是昨天
        pageSize: null, //请求一页的条目数量
        pageNo: null };
};

cc._RF.pop();