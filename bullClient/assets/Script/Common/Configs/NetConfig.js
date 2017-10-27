//与服务端交互的事件类型，r_是只能接收的事件receive。s_客户端请求的事件send

var config = {
    s_login : 'NbLogin',                                                      //登陆
    s_enterHouse: 'NbIntoRoomIn',                                           //进入房间的请求
    r_tips : 'NbTip',                                                         //直接返回信息结果
    r_enterHouseReturn : 'NbIntoRoomOut',                                  //进入房间后返回的信息
    r_idleTimeOut : 'NbTimeoutOut',                                         //长时间没有做操作
    s_doGrab : 'NbBet',                                                      //玩家选中一个投注区域
    r_getAnnouncement : 'NbGameAnnouncement',                             //公告的接收
    r_passiveOut : 'NbKickOut',                                             //异地登陆
    r_nbSelf : 'NbSelf',                                                    //如果有消息传来,则刷新自己的信息
    //classic
    s_calculating : 'NbClassicCounting',                                   //算牌
    s_grabDealerOrSetScore : 'NbClassicMultipleIn',                       //抢庄或者押注
    r_setScoreEnd : 'NbClassicBetMultipleOverOut',                        //押倍数结束，单个通知每一个桌内玩家数据.
    r_grabDealerEnd : 'NbClassicDealerMultipleOverOut',                  //抢庄结束，单个通知每一个桌内玩家数据.
    r_getPlayersInfo : 'NbClassicInitializeOut',                          //进入房间后，入座初始化桌内玩家数据.
    r_oneGrab : 'NbClassicMultipleOut',                                    //抢庄，押倍数批量通知桌内玩家数据.
    r_startInfo : 'NbClassicNewMatchOut',                                  //游戏开始数据.
    r_oneInsert : 'NbClassicSeatOut',                                       //新玩家入座，批量通知桌内玩家数据.
    r_calculateEnd : 'NbClassicSettleOut',                                 //算牌结束结算数据，批量通知桌内玩家.
    //grab
    r_grab_reStartGame : 'NbBullBaoStartMatchOut',                        //在房间内重新开始游戏时候的数据
    r_doGrab_return : 'NbBetOut',                                           //玩家选中一个投注区域
    r_otherGrab : 'NbBetBatchOut',                                          //有某个玩家投注
    r_grabEnd : 'NbSettleMatchOut',                                         //投注结束
    r_mineHaveGrab : 'NbSettlePlayerOut',                                  //自己有投注则有这条消息显示收益
    r_oneInHome : 'NbSeatOut',                                               //有某个玩家入座
    s_grab_Exit : 'NbExitRoomIn',                                            //退出房间
    r_grab_exitReturn : 'NbExitRoomOut',                                    //退出房间返回
    //bull100
    r_bull100_reStartGame : 'NbStartMatchOut',                             //在房间内重新开始游戏时候的数据
    s_bull100_dealerUp : 'NbUpDealerIn',                                    //上庄请求
    s_bull100_dealerContinue : 'NbKeepDealerIn',                           //续庄请求
    s_bull100_dealerDown : 'NbDownDealerIn',                                //下庄请求
    s_bull100_getDealerList : 'NbUpDealerListIn',                          //获取上庄列表
    r_bull100_dealerDownWarning : 'NbWarningDealerOut',                   //下庄警告
    r_bull100_dealerListReturn : 'NbUpDealerOut',                          //上庄下发消息
    r_bull100_downDealerReturn : 'NbDownDealerOut',                        //下庄请求回调
}

window.NetType = config;