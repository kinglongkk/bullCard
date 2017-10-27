"use strict";
cc._RF.push(module, '0512eKgI7FPO4yvK6/Ay6rT', 'Config_grab');
// Script/Common/Configs/Config_grab.js

"use strict";

/**
 * Created by lenovo on 2016/12/29.
 */

//夺宝模式的配置
var grab = {
    num_otherGrabGoldNum: 2, //其他玩家投注的时候飞行的金币数量
    time_grabWillEnd: 2, //距离投注结束剩余该秒数的时候不播放发牌动画
    num_winPokerOff: 15, //有牛时候，后面两张牌增加的间隔
    time_goldFlyOnHead: 1.5 };

window.G_Config_grab = grab;

cc._RF.pop();