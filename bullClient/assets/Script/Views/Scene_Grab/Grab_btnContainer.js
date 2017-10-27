//按钮管理类


cc.Class({
    extends: require('AutoDealing'),

    properties: {
        _lastBtn : null,

        node_btnGroup : cc.Node,
        // node_effectOnButton : cc.Node,
        node_effectUnderButton : cc.Node,
        node_exitHomeBtn : cc.Node,                 //退出房间的按钮
        //右下角的按钮
        node_playersList : cc.Node,
        node_rules : cc.Node,
        node_trend : cc.Node,
    },

    // use this for initialization
    onLoad: function () {
        this._init();

        this._registerAllBtn();
    },

    setStartInfo : function () {
        var valueDict = GG.grabMgr.getMyselfComp().getMoneyChooseDict();
        this._setBtnInfo(valueDict);

        this.setBtnGroupEnable(false);
    },

    _init : function () {
        this._btnCompName = 'Obj_optionButton';
        this._isAdaptPos = false;
    },

    _registerAllBtn : function () {
        var btns = this.node_btnGroup.children;
        for(var i = 0; i < btns.length; i ++){
            this.registerButton(btns[i], this.OnClick_chooseMoney, this, parseInt(i), true);
        }
        //this.node_exitHomeBtn.parent.getComponent('Obj_leftTopMenu').bindFunc(this.OnClick_exit.bind(this), this.OnClick_changeDesk.bind(this));
        this.registerButton(this.node_exitHomeBtn, this.OnClick_exit, this);
        //右下角按钮
        this.registerButton(this.node_playersList, this.OnClick_playerList, this);
        this.registerButton(this.node_rules, this.OnClick_rules, this);
        this.registerButton(this.node_trend, this.OnClick_trend, this);
    },
    //点击选中筹码  userData从0开始
    OnClick_chooseMoney : function (event, userData) {
        this.chooseOption(userData);
    },
    //点击换桌
    OnClick_changeDesk : function () {},
    //点击退出房间
    OnClick_exit : function (event) {
        if(G_Config_common.isLocal){
            GG.tipsMgr.showConfirmTip_TWO(G_CHINESE.exitText1, function () {
                GG.exitHome();
            });
            return
        }

        if(GG.grabMgr.getIsGrab()){
            GG.tipsMgr.showConfirmTip_TWO(G_CHINESE.exitText1, function () {
                var netData = {
                    roomId : GG.grabMgr.getHomeID(),
                    isBreak : true
                }
                GG.socketMgr.SendMsg(NetType.s_grab_Exit, netData);
            });
        }else{
            var netData = {
                roomId : GG.grabMgr.getHomeID(),
                isBreak : true
            }
            GG.socketMgr.SendMsg(NetType.s_grab_Exit, netData);
        }

    },
    //点击玩家列表
    OnClick_playerList : function () {
        var data = 1;
        var layer = GG.grabMgr.showDialogUI(G_RES_URL.uiName.playerList, function (layer) {
            layer.setData(data);
        });
    },
    //点击游戏规则
    OnClick_rules : function () {
        var data = G_TYPE.gameModule.grab;
        var layer = GG.grabMgr.showDialogUI(G_RES_URL.uiName.rulesInHome, function (layer) {
            layer.showLayer();
            layer.setData(data);
        });
    },
    //点击走势图列表
    OnClick_trend : function () {
        var data = GG.grabMgr.getHomeID();
        var layer = GG.grabMgr.showDialogUI(G_RES_URL.uiName.grabTrend, function (layer) {
            layer.setData(data);
        });
    },

    //=========================================

    //选中一个筹码  index 从0开始
    chooseOption : function (chooseLevel) {
        chooseLevel = parseInt(chooseLevel);
        GG.grabMgr.getMyselfComp().setChooseLevel(chooseLevel);
        this._showChooseEffect(this._getBtnByIndex(chooseLevel));
    },

    _setBtnInfo : function (valueDict) {
        var btns = this.node_btnGroup.children, index;
        for(var i = 0; i < btns.length; i ++){
            index = parseInt(i);
            btns[i].tag = valueDict[index];
            btns[i].getComponent(this._btnCompName).showMoney(valueDict[index], false);
        }
    },
    _getBtnByIndex : function (index) {
        return this.node_btnGroup.children[index]
    },
    chooseMoney : function (index) {
        var btn = this._getBtnByIndex(index);
        //获取第一次进房是否可下注
        this.isBetting = GG.grabMgr.getFirstBetting();
        this._showChooseEffect(btn);
    },

    //刷新押宝可用筹码显示
    refreshGrabOption : function (useGold) {
        if(!this._list_myGrabOption) this._list_myGrabOption = GG.grabMgr.getMyselfComp().getMoneyChooseDict();
        if(!G_DATA.isNumber(useGold)) useGold = GG.grabMgr.getMyselfComp().getGoldValue();
        var targetChoose = null;
        var lastChoose = GG.grabMgr.getMyselfComp().getChooseLevel()
        var btnGroup = this.node_btnGroup.children
        for(var option = 0; option < this._list_myGrabOption.length; option ++){
            if(this._list_myGrabOption[option] <= useGold){
                this.setBtnEnable(btnGroup[option], true);
                targetChoose = parseInt(option);
            }else{
                if(this._lastBtn && this._lastBtn.tag == this._list_myGrabOption[option]){
                    this._cancelChoose();
                }
                this.setBtnEnable(btnGroup[option], false);
            }
        }
        //如果有取消选中筹码，需要重新选择筹码
        if(!this._lastBtn && G_DATA.isNumber(targetChoose)){
            //保持上次的选择
            if(GG.grabMgr.getMyselfComp().getChooseLevel() < targetChoose) targetChoose = GG.grabMgr.getMyselfComp().getChooseLevel();

            this._showChooseEffect(btnGroup[targetChoose]);
            GG.grabMgr.getMyselfComp().setChooseLevel(targetChoose);
        }
    },

    //===========================表现

    //设置按钮置灰
    setBtnGroupEnable : function (isEnable) {
        this._cancelChoose();
        var btnGroup = this.node_btnGroup.children;
        for(var i = 0; i < btnGroup.length; i ++){
            //设置某个按钮置灰
            this.setBtnEnable(btnGroup[i], isEnable);
        }
    },


    //按钮选中效果
    _showChooseEffect : function (targetBtn) {
        if(targetBtn){
            //取消上次的选中效果
            this._cancelChoose();

            //按钮颜色
            targetBtn.getComponent(this._btnCompName).showMoney(null, true);
            //按钮底部效果
            this._showUnderBtnEffect(targetBtn);
            //禁用选中按钮，但是不置灰
            this.setBtnEnable(targetBtn, false, true);
            this._lastBtn = targetBtn;

            //刚进入游戏如果可下注 强制自适应
            // if (this.isBetting && !this._isAdaptPos) {
            //     this.node_effectUnderButton.position = G_TOOL.adaptPos(this.node_effectUnderButton.position);
            //     var size = G_TOOL.adaptSize(targetBtn.width, targetBtn.height);
            //     this.node_effectUnderButton.width = size.width;
            //     this._isAdaptPos = true;
            // } else {
            //     this.node_effectUnderButton.width = targetBtn.width;
            // }
        }

        //this._changeBtnColor(targetBtn);
        //
        ////effect
        //// //this.node_effectOnButton.active = true;
        //this.node_effectUnderButton.active = true;
        ////
        //// //this.node_effectOnButton.position = cc.p(targetBtn.x, targetBtn.y);;
        //this.node_effectUnderButton.x = targetBtn.x;
        //this.node_effectUnderButton.y = -targetBtn.height/2;
        ////刚进入游戏如果可下注 强制自适应
        //if (this.isBetting && !this._isAdaptPos) {
        //    this.node_effectUnderButton.position = G_TOOL.adaptPos(this.node_effectUnderButton.position);
        //    var size = G_TOOL.adaptSize(targetBtn.width, targetBtn.height);
        //    this.node_effectUnderButton.width = size.width;
        //    this._isAdaptPos = true;
        //} else {
        //    this.node_effectUnderButton.width = targetBtn.width;
        //}
    },

    //取消上一次的选中效果
    _cancelChoose : function () {
        if(this._lastBtn){
            this._hideUnderBtnEffect();
            this._lastBtn.getComponent(this._btnCompName).showMoney(null, false);
            this.setBtnEnable(this._lastBtn, true);
            this._lastBtn = null;
        }
    },

    //显示按钮底部效果
    _showUnderBtnEffect : function (targetBtn) {
        this.node_effectUnderButton.active = true;
        this.node_effectUnderButton.x = targetBtn.x;
        this.node_effectUnderButton.y = -targetBtn.height/2;
    },
    //隐藏按钮特效
    _hideUnderBtnEffect : function () {
        this.node_effectUnderButton.active = false;
    },

    //=============================获取

    //获取轮子装饰物的位置
    getWheelPos : function () {
        var designSize = cc.view.getDesignResolutionSize();
        return cc.p(designSize.width*0.8, designSize.height*0.2)
    },
    //获取吃瓜群众投注起点
    getIdleBettingPos : function () {
        var pos = this.node_playersList.position;
        var basePos = this.node_playersList.parent.parent.position;
        var posX = pos.x + this.node_playersList.parent.x+basePos.x;
        var posY = pos.y + this.node_playersList.parent.y+basePos.y + this.node_playersList.height/2;
        return cc.p(posX, posY)
    },
    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
