//游戏场景中最上面的那个层


cc.Class({
    extends: cc.Component,

    properties: {
        _isConfirmTipOpen : null,                                                                                       //提示窗口是否已经打开
        _isHasOpen : null,                                                                                               //提示窗口是否已经打开

        prefab_loadingAni : {
            default : null,
            type : cc.Prefab,
            displayName : '网络请求动画'
        }
    },

    // use this for initialization
    onLoad: function () {
        // GG.topTouchLayer = this;

        this._isConfirmTipOpen = false;
        this._touchLimitLayer = this.getComponent('Obj_touchLimit');
    },

    start : function () {
        this._registerFunc(true);
    },

    //显示请求网络的动画
    showNetRequest : function (isShowTip) {
        if(!this.prefab_loadingAni) return;
        if(!this._netAni){
            this._netAni = cc.instantiate(this.prefab_loadingAni);
            this._netAni.parent = this.node;
            var center = cc.visibleRect.center;
            this._netAni.position = cc.p(center.x, center.y*1.35)
            //this._netAni.active = false;
        }
        if(this._netAni.active) return;
        if(this._isConfirmTipOpen){
            //弹窗已经存在
            this._isHasOpen = true;
            return
        }
        this._netAni.active = true;
        var center = cc.visibleRect.center;
        this._netAni.position = cc.p(center.x, center.y*1.35)
        this._showReconnectTip(isShowTip);
        this._netAni.getComponent(dragonBones.ArmatureDisplay).playAnimation('');
        if(this._touchLimitLayer) this._touchLimitLayer.addTouchLimit();
    },

    closeNetRequest : function () {
        if(this._netAni){
            this._netAni.active = false;
            if(this._touchLimitLayer) this._touchLimitLayer.cancelTouchLimit();
        }
    },

    //显示重连提示
    _showReconnectTip : function (isShow) {
        var tipNode = this._netAni.getChildByName('tip');
        if(tipNode){
            var content;
            if(isShow) {
                var tableData = G_DATA.getChinese(61);
                content =tableData.content;
            }else content = '';
            tipNode.getComponent(cc.Label).string = content;
        }
    },

    //注册确认窗口的监听
    _registerFunc : function (isRegister) {
        if(GG.tipsMgr){
            GG.tipsMgr.registerConfirm(this._whenConfirmTipChange.bind(this), isRegister);
        }
    },

    //当窗口打开或者关闭
    _whenConfirmTipChange : function (isOpen) {
        this._isConfirmTipOpen = isOpen;
        if(this._netAni.active){
            //当显示连接的时候
            if(isOpen) {
                this._isHasOpen = true;
                this.closeNetRequest();
            }
        }else{
            if(!isOpen){
                if(this._isHasOpen){
                    var tipNode = this._netAni.getChildByName('tip');
                    var isShow = false;
                    if(tipNode){
                        isShow = Boolean(tipNode.getComponent(cc.Label).string)
                    }
                    this.showNetRequest(isShow);
                }
            }
        }
    },

    onDestroy : function () {
        this._registerFunc(false);
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
