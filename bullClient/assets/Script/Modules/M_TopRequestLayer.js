//最上面的网络请求动画

module.exports = cc.Class({
    properties: {
        _ani : null,                                     //加载动画
        _isLoading : null,                              //正在加载
    },

    // use this for initialization
    ctor: function () {
        this._isLoading = false;
    },

    //显示网络请求动画
    showNetRequest : function (isShowTip) {
        var self = this;
        this._getRequestAni(function (layer_netAni) {
            if(!layer_netAni) return;
            if(!layer_netAni.active){
                layer_netAni.active = true;
                self._showReconnectTip(layer_netAni, isShowTip);

                var aniNode = layer_netAni.getChildByTag(1);
                if(aniNode){
                    var ani = aniNode.getComponent(dragonBones.ArmatureDisplay);
                    if(ani) ani.playAnimation('');
                }
            }
        })
    },
    //隐藏动画
    closeNetRequest : function () {
        var self = this;
        this._getRequestAni(function (layer_netAni) {
            if(!layer_netAni) return;
            layer_netAni.active = false;
        })
    },

    //获取播放的动画
    _getRequestAni : function (callFunc) {
        var limitLayer = cc.find('Canvas/layer_loadingRequest');
        if(limitLayer && callFunc){
            callFunc(limitLayer);
            callFunc = null;
        }else{
            if(this._isLoading) return;
            var self = this;
            this._isLoading = true;
            cc.loader.loadRes('ani_prefab/Prefab_simpleLoading', cc.Prefab, function (err, prefab) {
                if(prefab){
                    var canvas = cc.find('Canvas');
                    limitLayer = new cc.Node();
                    limitLayer.setContentSize(cc.visibleRect.width, cc.visibleRect.height);
                    canvas.addChild(limitLayer, 3);
                    limitLayer.setName('layer_loadingRequest');
                    self.addTouchLimit(limitLayer);

                    var ani = cc.instantiate(prefab);
                    ani.y = cc.visibleRect.height * 0.2;
                    limitLayer.addChild(ani, 1, 1);
                }
                self._isLoading = false;
                if(callFunc){
                    callFunc(limitLayer);
                    callFunc = null;
                }
            })
        }
    },

    getIsShow : function () {
        var layer_netAni = cc.find('Canvas/layer_loadingRequest');
        return Boolean(layer_netAni && layer_netAni.active)
    },

    //显示重连提示
    _showReconnectTip : function (layer_netAni, isShow) {
        var tipNode = layer_netAni.getChildByTag(2);
        if(!tipNode){
            tipNode = new cc.Node();
            tipNode.addComponent(cc.Label);
            tipNode.y = -cc.visibleRect.height * 0.1;
            layer_netAni.addChild(tipNode, 0, 2);
        }
        var content;
        if(isShow) {
            var tableData = G_DATA.getChinese(61);
            content =tableData.content;
        }else content = '';
        tipNode.getComponent(cc.Label).string = content;
    },

    addTouchLimit : function (target) {
        if(!target) target = cc.find('Canvas/layer_loadingRequest');
        if(target) target.on(cc.Node.EventType.TOUCH_START, this._touchThisLayer, this);
    },
    _touchThisLayer : function () {

    },
    cancelTouchLimit : function (target) {
        if(!target) target = cc.find('Canvas/layer_loadingRequest');
        if(target) target.off(cc.Node.EventType.TOUCH_START, this._touchThisLayer, this);
    },
});
