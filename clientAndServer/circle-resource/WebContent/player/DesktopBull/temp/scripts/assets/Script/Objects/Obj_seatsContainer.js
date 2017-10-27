"use strict";
cc._RF.push(module, 'e779eMDuShEOqiJv281BvTL', 'Obj_seatsContainer');
// Script/Objects/Obj_seatsContainer.js

"use strict";

//座位容器管理


cc.Class({
    extends: cc.Component,

    properties: {},

    // use this for initialization
    onLoad: function onLoad() {},

    //获取一个位置的坐标
    addOnePlayer: function addOnePlayer(seatIndex) {
        var seat = this.node.children[seatIndex - 1];
        if (seat) {
            seat.active = false;
            return seat.position;
        }
        return null;
    },

    //有一个玩家离开
    onePlayerLeave: function onePlayerLeave(seatIndex) {
        var seat = this.node.children[seatIndex - 1];
        if (seat) {
            seat.active = true;
        }
    },

    //获取座位数量
    getSeatsNum: function getSeatsNum() {
        return this.node.children.length;
    }

});

cc._RF.pop();