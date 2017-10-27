/**
 * Created by lenovo on 2016/12/29.
 */

//夺宝模式的配置
var grab = {
    num_otherGrabGoldNum : 2,                                               //其他玩家投注的时候飞行的金币数量
    time_grabWillEnd : 2,                                                    //距离投注结束剩余该秒数的时候不播放发牌动画
    num_winPokerOff : 15,                                                    //有牛时候，后面两张牌增加的间隔
    time_goldFlyOnHead : 1.5,                                                //头像上金币增减飞行需要空出来的时间

    //time_goldFly_Add : 0.3,                                                  //押注时候，金币的移动时间
    //time_goldFly_Result : 0.5,                                               //计算收益的时候，金币的移动时间
    //time_goldFly_delay : 0.1,                                                //金币飞行，金币间隔
    //num_goldImgArea : 0.3,                                                   //金币在投注区显示的范围=最大范围的百分比
    //dict_moveGoldNum : {1:1,2:1,3:2,4:3,5:4},                               //玩家投注时候增加的金币图片数量
    //num_pokerOffX : 0.35,                                                     //卡牌之间的间隔=卡牌宽的百分比
    //time_pokerMove : 0.5,                                                    //卡牌移动花费的时间
    //delayTime_pokerMove : 0.05,                                             //飞出卡牌的间隔
    //time_openMovePoker : 0.2,                                               //庄家开牌时候，牌移动耗时
    //time_showFlag : 3.5,                                                     //赢钱旗帜保持显示的时长
    //time_goldOnHead : 0.5,                                                   //头像上面金币值移动速度
    //time_openDelay : 0.5,                                                    //开牌间隔
}


window.G_Config_grab = grab;