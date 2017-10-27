//座位容器管理


cc.Class({
    extends: cc.Component,

    properties: {

    },

    // use this for initialization
    onLoad: function () {

    },

    //获取一个位置的坐标
    addOnePlayer : function (seatIndex) {
        var seat = this.node.children[seatIndex-1];
        if(seat) {
            seat.active = false;
            return seat.position
        }
        return null
    },

    //有一个玩家离开
    onePlayerLeave : function (seatIndex) {
        var seat = this.node.children[seatIndex-1];
        if(seat){
            seat.active = true;
        }
    },

    //获取座位数量
    getSeatsNum : function () {
        return this.node.children.length
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
