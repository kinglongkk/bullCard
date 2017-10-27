//最上面的触摸禁用层

cc.Class({
    extends: cc.Component,

    properties: {
        _netAni : null,                                             //预存的网络请求动画
    },

    // use this for initialization
    onLoad: function () {

    },

    addTouchLimit : function (isGray) {
        this.node.on(cc.Node.EventType.TOUCH_START, this._touchThisLayer, this);
    },
    _touchThisLayer : function () {

    },
    cancelTouchLimit : function () {
        this.node.off(cc.Node.EventType.TOUCH_START, this._touchThisLayer, this);
    },

    //设置是否有置灰层
    setIsShowGray : function (isGray) {
        if(!this._grayLayer){

        }
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
