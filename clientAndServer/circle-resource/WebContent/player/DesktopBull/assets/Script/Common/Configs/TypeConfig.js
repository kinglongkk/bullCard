//游戏中各种类型信息的定义

var typeConfig = {

    webGameState : cc.Enum({
        pause : 1,
        running : 2,
    }),

    gameModule : cc.Enum({
        platform : 0,
        bull100 : 1,
        grab : 2,
        classic : 3,
    }),

    //场景名称，用于切换场景
    sceneName : cc.Enum({
        platform : 'Platform',
        bull100 : 'Poker_Bull100',
        grab : 'Poker_Grab',
        classic : 'Poker_classic',
        loadingScene : 'InHomeLoading',
    }),

    //http请求
    http_gameModule : cc.Enum({
        platform : 0,
        bull100 : 1,
        classic : 2,
        grab : 3,
    }),

    grabState : cc.Enum({
        wait : 0,                           //等待
        readyStart : 1,                    //
        givePoker : 2,                     //
        touchTables : 3,                   //
        showFlag : 4,                      //
        flyGold : 5,                       //
        readyTouchTable : 6,              //
        isBetting : 7,                    //是否已下注
    }),

    //游戏流程的状态机制
    classicState : cc.Enum({
        wait : 0,                           //游戏等待开始
        readyStart : 1,                    //游戏准备开始（播放开始动画时候）
        givePoker : 2,                     //正在发牌
        grabDealer : 3,                    //正在抢庄
        setScore : 4,                      //正在下注
        calculate : 5,                     //算牌时间
        showResult : 6,                    //显示结果
    }),

    //百人大战进程
    bull100State : cc.Enum({
        wait : 0,                           //游戏等待时间
        startReady : 1,                    //游戏投注前的表现
        betting: 2,                          //游戏开始投注
        endEff : 3,                         //游戏结束后的结算表现
    }),

    //牌型的值
    pokerResult : cc.Enum({
        bull_0 : 0,                         //没牛
        bull_1 : 1,
        bull_2 : 2,
        bull_3 : 3,
        bull_4 : 4,
        bull_5 : 5,
        bull_6 : 6,
        bull_7 : 7,
        bull_8 : 8,
        bull_9 : 9,
        bull_10 : 10,                       //牛牛
        bull_11 : 11,
        bull_12 : 12,
    }),

    //卡牌的类型  大小排列为黑桃 红桃 草花 方块
    pokerType:cc.Enum({
        blackHeart : 1,                     //黑色桃心
        redHeart : 2,                       //红色桃心
        flower : 3,                         //梅花
        block : 4,                          //方块
    }),

    playerType : cc.Enum({
        system : 0,
        owner : 1,
        others : 2
    }),

    net_gameModule : cc.Enum({
        bull100:'BULL_100',
        grab : 'BULL_BAO',
        classic : 'BULL_CLASSIC'
    }),
    net_gameModule2 : cc.Enum({
        bull100:'bull100',
        grab : 'bullBao',
        classic : 'bullclassic'
    }),

    //桌子大小类型
    tableType : cc.Enum({
        big : 1,
        min : 2
    }),

    //tip显示的类型
    tipType : cc.Enum({
        
    }),

    //接受服务端消息后的code类型
    serverCodeType : cc.Enum({
        success : '0',
        serverErr1 : '00001',                                                                                              //服务器忙
        remoteLanding : '00005',                                                                                           //异地登陆
        goldNotEnough : '200002',                                                                                          //您剩余金币不足
        upDealerErr1 : '200003',                                                                                           //您剩余金币不足,无法上庄
        matchNonExistent : '500001',                                                                                       //赛事不存在
        upDealerErr2 : '600002',                                                                                           //未达到最低上庄金额
        upDealerErr3 : '600013',                                                                                           //输入的上庄金额格式错误
        noBetting : '00003',                                                                                               //没有投注
        downDealer1 : '600016',                                                                                            //您已在庄，下庄系统将帮您打完本局！
        downDealer2 : '600017',                                                                                            //您已从上庄列表退出竞庄！
        downDealer3 : '600012',                                                                                            //您已下庄!
        downDealer4 : '600018',                                                                                            //您剩余金币不足,已下庄!
        downDealer5 : '600019',                                                                                            //您当期已下庄!
        continueFail1 : '200004',                                                                                          //庄家金币不足续庄
        continueFail2 : '600008',                                                                                          //庄家被动续庄
        warnDealer1 : '600007',                                                                                             //友情提示: 您的上庄资金不足80%，请及时续庄！
        warnDealer2 : '600009',
        upDealerSuccess : '600010',                                                                                        //成为本局庄家
        continueFail3 : '600014',                                                                                          //输入的续庄金额格式错误
        continueSuccess : '600011',                                                                                        //输入的续庄金额格式错误
        needLogin : '10001',                                                                                                //未登陆，请登陆
        betNotPermitCoin: '400004',                                                                                         //下分金额为非许可值
        betNotPermitBet: '400005',                                                                                          //非法下注类型
        matchNoEnd: '300003',                                                                                          //已经 开始的对局未结束
        chooseErrorMoney: '400001',                                                           //选择了错误的筹码数值
    }),

    //
    dealerNetType : cc.Enum({
        upDealer : 1,                                                                                                        //上庄
        continueDealer : 2,                                                                                                 //续庄
        getList : 3,                                                                                                        //获取上庄列表
    }),

    //滚动层的类型
    scrollType : cc.Enum({
        horizontal : 1,                                                                                                       //横向放置单元
        vertical : 2,                                                                                                         //竖向放置单元
        bag : 3,                                                                                                               //背包格式放置单元
    }),

    //公告的通知方式枚举
    //announcementType : cc.Enum({
    //    general : 'general',                                                                                                  //一般公告
    //    importance : 'importance',                                                                                           //重要公告
    //    personal : 'personal',                                                                                                //个人公告
    //    allAgent : 'allAgent',                                                                                                //全局代理商
    //    appointAgent : 'appointAgent',                                                                                       //指定代理商
    //    enable : 'enable',                                                                                                     //启用
    //    disable : 'disable',                                                                                                  //取消
    //}),

    //走势图，判定是什么牌型，如梅花
    pokerTypeStr : cc.Enum({
        spade : 'SPADE',                                                                                                      //黑桃
        diamond : 'DIAMOND',                                                                                                 //方片
        club : 'CLUB',                                                                                                        //梅花
        heart : 'HEART',                                                                                                      //红桃
    }),

    //座位的定义
    seatsIndex : cc.Enum({
        seat_1:1,
        seat_2:2,
        seat_3:3,
        seat_4:4,
        seat_5:5,
        seat_6:6,
        seat_7:7,
        seat_8:8,
    }),

    //本地存储的数据类型
    localDataType : cc.Enum({
        inHome : 1,                                                                                                         //进房信息
        testType : 2,                                                                                                       //测试信息
    }),

    //全局的监听
    globalListener : cc.Enum({
        playerGold : 'playerGold',                           //玩家自己的金币变化
    }),

    webMobile_design : cc.Enum({
        toolbar_show : 1,
        toolbar_hide : 2,
    })
}

window.G_TYPE = typeConfig;