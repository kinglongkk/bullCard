//提示弹窗类的管理器

cc.Class({
    extends: cc.Component,
    properties: {
        _curTxtTipNum : null,
        _confirmTag : null,                                            //确认框显示标记
        _list_hideTips : null,                                         //隐藏并可复用的tip

        prefab_confirmTip : cc.Prefab,                                //可点击确定的弹窗
        prefab_txtTip : cc.Prefab,                                     //文本提示

        node_countDownProgress : cc.Node,                             //图形文字倒计时提示
        node_systemTip : cc.Node,                                      //系统提示
        Node_topNotice : cc.Node,                                      //公告通知类
    },

    // use this for initialization
    onLoad: function () {
        GG.tipsMgr = this;

        this._progressCompName = 'Obj_countDownProgress';
        this._systemTipCompName = 'Obj_systemTip';
        this._txtTipCompName = 'Obj_txtTip';
        this._confirmTipCompName = 'Obj_confirmTip';
        this._topNoticeCompName = 'Obj_notice';
        this._dict_onlyOne = {};
        this._curTxtTipNum = 0;
        this._commonScale = G_Config_common.frameScale;
        this._confirmTag = 3;
        this._list_hideTips = [];
    },

    //显示纯文本弹窗, pos为空默认显示在中央, 可重复使用的tip
    showTxtTip : function (dataObj) {
        var tip = this._getTxtTip();;
        tip.showTip(dataObj);
        return tip
    },
    _getTxtTip : function () {
        var tip = this._list_hideTips.splice(0,1)[0];
        if(!tip){
            tip = this._getNewTip();
        }

        return tip.getComponent(this._txtTipCompName)
    },
    _getNewTip : function () {
        var tip = cc.instantiate(this.prefab_txtTip);
        //this.node.addChild(tip, 0, this._curTxtTipNum);
        tip.parent = this.node;
        //tip.scale = this._commonScale;
        tip.getComponent(this._txtTipCompName).bindMgr(this);
        this._curTxtTipNum += 1;
        return tip
    },
    oneTipHide : function (tipNode) {
        var tipComp = tipNode.getComponent(this._txtTipCompName);
        if(tipComp){
            if(!this._dict_onlyOne[tipComp.getTipIndex()]){
                this._list_hideTips.push(tipNode);
            }
        }
    },

    //=---------------------------只显示一次的tip

    setTextTip : function (tipNodeComp) {
        if(tipNodeComp) this._diyTipComp = tipNodeComp;
    },

    showOnlyOne : function (dataObj) {
        var tip;
        var tipIndex = dataObj.tipIndex;
        if(this._diyTipComp){
            tip = this._diyTipComp;
        }else {
            if(!this._dict_onlyOne) this._dict_onlyOne = {};
            tip = this._dict_onlyOne[tipIndex];
        }

        if(tip){
            if(tip.getIsShow()){
                tip.forceChangeCountDown(dataObj.retainTime);
            }else{
                tip.showTip(dataObj);
            }
        }else{
            tip = this._getNewTip().getComponent(this._txtTipCompName);
            this._dict_onlyOne[tipIndex] = tip;
            tip.showTip(dataObj);
        }
        return tip
    },

    //======================================

    //显示倒计时文字图形
    showProgressCountDown : function (time, callFunc) {
        this.node_countDownProgress.getComponent(this._progressCompName).showCountDown(time, callFunc);
    },
    getProgressLeaveTime : function () {
        return this.node_countDownProgress.getComponent(this._progressCompName).getLeaveTime();
    },
    forceProgressEnd : function () {
        this.node_countDownProgress.getComponent(this._progressCompName).forceEnd();
    },

    //======================================

    //显示可点击确定的弹窗   单选按钮
    showConfirmTip_ONE : function (showStr, callFunc) {
        var confirmPage = this.node.getChildByTag(this._confirmTag);
        if(!confirmPage){
            confirmPage = cc.instantiate(this.prefab_confirmTip);
            confirmPage.parent = this.node;
            confirmPage.tag = 3;
        }
        confirmPage.getComponent(this._confirmTipCompName).showContentEx(showStr, callFunc);
    },
    //多选按钮
    showConfirmTip_TWO : function (showStr, callFunc, callFunc2) {
        var confirmPage = this.node.getChildByTag(this._confirmTag);
        if(!confirmPage){
            confirmPage = cc.instantiate(this.prefab_confirmTip);
            confirmPage.parent = this.node;
            confirmPage.tag = 3;
        }
        confirmPage.getComponent(this._confirmTipCompName).showContent(showStr, callFunc, callFunc2);
    },

    //清理弹出的确认框
    clearConfirm : function () {
        var confirm = this.node.getChildByTag(this._confirmTag);
        if(confirm){
            var comp = confirm.getComponent(this._confirmTipCompName);
            if(comp && confirm.active) comp.closePage();
        }
    },

    //注册确认框的打开关闭事件监听   isRegister: 是否开启监听
    registerConfirm : function (func, isRegister) {
        if(!this.node) return;
        var confirm = this.node.getChildByTag(this._confirmTag);
        if(confirm){
            confirm.getComponent(this._confirmTipCompName).registerFunc(func, isRegister);
        }
    },

    //======================================

    //显示体统提示
    showSystem : function (showStr, pos, time, callBack) {
        this.node_systemTip.getComponent(this._systemTipCompName).showContent(showStr, pos, time, callBack);
    },

    //======================================

    //显示公告信息
    addNotice : function (msg) {
        this.Node_topNotice.getComponent(this._topNoticeCompName).addMsg(msg);
    },


    //添加禁止点击层，仅对touch事件吞噬，对mouse事件没有效果;
    setTouchLimit : function (isLimit) {
        var parent = this.node.parent;
        if(isLimit) parent.on(cc.Node.EventType.TOUCH_START, this._limitCall, this);
        else parent.off(cc.Node.EventType.TOUCH_START, this._limitCall, this);
    },
    _limitCall : function () {},


    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
