//在经典模式对战中使用到的常用配置

var classicConfig = {
    //num_firstGive : 4,                                          //首次发牌数量
    //num_maxPlayer : 5,                                          //最大玩家数量
    //num_maxChoose : 3,                                          //看牌的时候能选中的最大卡牌数量

    //len_betweenOtherPoker : 25,                                //参战玩家两张卡牌之间的间隔
    //len_betweenMyPoker : 135,                                    //玩家自己拥有的两张卡牌之间的间隔
    //time_givePoker : 0.1,                                       //发每张牌的间隔
    //time_pokerFly : 0.8,                                        //卡牌移动需要的时间
    //scale_noMyPoker : 0.5,                                      //盖住的卡牌的缩放程度
    //num_pokerUp : 30,                                           //点击卡牌后向上移动的高度
    //time_dealerMove : 0.2,                                      //抢庄特效移动间隔
    //time_confirmDealer : 1.5,                                   //确定庄家特效持续时间
    //time_goldFly : 0.3,                                            //金币飞行的时间
    //num_goldGroup : 10,                                           //一次飞行的金币量
    //
    //time_showGold : 0.08,                                          //显示单个金币间隔
    //list_centerProgressTime : [0.7,0.2],                        //中间圆形进度条倒计时:参数1:第一个开始变化颜色占总时间的比例；参数2:第二个变化
    //time_pageResult : [0.3,0.8,1.3],                             //结算面板,time1:胜利文字动作；time2:背景动作; time3图标动作
    //time_pokerResultShow : 0.5,                                 //卡牌上面的结果特效显示的时间
    //scale_pokerResultShow : 1.2,                                //卡牌上方结果特效最大倍数
    //list_backgroundEffect1 : [1.0, 1.3],                        //背景特效1 ：参数1：缩放时间，参数2：缩放倍数
    //time_pokerResultInterval : 0.5,                             //依次显示卡牌结果的间隔
    //list_goldAddFly : [0.8, 0.8, 0.5],                           //增加金币效果参数1：飞出去的时间，参数2：飞回label的时间，参数3：中间停顿的间隔
    //list_goldLostFly : [0.8, 1.0, 0.3],                          //减少金币效果参数1：飞出去的时间，参数2：中间停顿的时间，参数3：消失花费的时间
    //num_pokerResultOff : 45,                                      //卡牌上面结果显示矫正
    //num_dealerEffMoveStep : 2,                                 //该数值乘以当前玩家数==随机庄家效果必定移动步数

    //num_goldFirstOff : 15,                                        //飞行金币初始位置偏移量
}

window.G_Config_classic = classicConfig;