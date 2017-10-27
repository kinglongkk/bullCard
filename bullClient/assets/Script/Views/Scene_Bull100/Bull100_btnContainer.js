//百人大战按钮管理类


cc.Class({
    extends: require('AutoDealing'),

    properties: {
        _lastBtn : null,

        node_btnGroup : cc.Node,
        // node_effectOnButton : cc.Node,
        node_effectUnderButton : cc.Node,
        node_exitHomeBtn : cc.Node,

        node_rightBtn1 : cc.Node,
        node_rightBtn2 : cc.Node,
        node_rightBtn3 : cc.Node,

        node_continueDealer : {
            default : null,
            type : cc.Node,
            displayName : '续庄按钮容器'
        }
    },

    // use this for initialization
    onLoad: function () {
        this._btnCompName = 'Obj_optionButton';
        this._isAdaptPos = false;
        this._registerAllBtn();
    },

    initChipBtn : function (valueDict) {
        this._setBtnInfo(valueDict);
    },

    setStartInfo : function () {
        this.setBtnGroupEnable(false);
    },

    _registerAllBtn : function () {
        var btns = this.node_btnGroup.children;
        for(var i = 0; i < btns.length; i ++){
            this.registerButton(btns[i], this.OnClick_chooseMoney, this, parseInt(i), true);
        }
        this.registerButton(this.node_exitHomeBtn, this.OnClick_exit, this);
        //this.node_exitHomeBtn.getComponent('Obj_leftTopMenu').bindFunc(this.OnClick_exit.bind(this));

        this.registerButton(this.node_rightBtn1, this.OnClick_right1, this);
        this.registerButton(this.node_rightBtn2, this.OnClick_right2, this);
        this.registerButton(this.node_rightBtn3, this.OnClick_right3, this);

        var continueDealer = this.node_continueDealer.children[0];
        var downDealer = this.node_continueDealer.children[1];
        this.registerButton(continueDealer, this.OnClick_continueDealer, this);
        this.registerButton(downDealer, this.OnClick_leaveDealer, this);
    },
    OnClick_chooseMoney : function (event, userData) {
        var chooseLevel = parseInt(userData);
        this._showChooseEffect(event.target);

        var playerChoose = chooseLevel + 1;
        GG.bull100Mgr.getMyselfComp().chooseChip(playerChoose);
    },
    OnClick_exit : function (event) {
        var isContinue = GG.bull100Mgr.testExitBtn();
        if(!isContinue) return;

        if(G_Config_common.isLocal){
            GG.tipsMgr.showConfirmTip_TWO(G_CHINESE.exitText1, function () {
                GG.exitHome();
            });
            return
        }

        if(GG.bull100Mgr.getIsBetting()){
            GG.tipsMgr.showConfirmTip_TWO(G_CHINESE.exitText1, function () {
                var netData = {
                    roomId : GG.bull100Mgr.getHomeID(),
                    isBreak : true
                }
                GG.socketMgr.SendMsg(NetType.s_grab_Exit, netData);
            });
        } else if (GG.bull100Mgr.getMyselfComp().getIsDealer() && GG.bull100Mgr.getDealerState()) {
            GG.tipsMgr.showConfirmTip_TWO(G_CHINESE.exitText3, function () {
                var netData = {
                    roomId: GG.bull100Mgr.getHomeID(),
                    isBreak: true
                }
                GG.socketMgr.SendMsg(NetType.s_grab_Exit, netData);
            });
        } else{
            var netData = {
                roomId : GG.bull100Mgr.getHomeID(),
                isBreak : true
            }
            GG.socketMgr.SendMsg(NetType.s_grab_Exit, netData);
        }
    },
    //顯示玩家列表
    OnClick_right1 : function (event) {
        GG.bull100Mgr.showUI_playerList();
    },
    //顯示游戏规则
    OnClick_right2 : function (event) {
        var data = G_TYPE.gameModule.bull100;
        GG.bull100Mgr.showUI_rules(data);
    },
    //顯示走勢圖
    OnClick_right3 : function (event) {
        GG.bull100Mgr.showUI_trendList(GG.bull100Mgr.getHomeID());
    },

    //点击续庄
    OnClick_continueDealer : function (event) {
        GG.bull100Mgr.send_getDealerList();
    },
    //点击下庄
    OnClick_leaveDealer : function (event) {
        GG.bull100Mgr.send_downDealer();
    },

    _setBtnInfo : function (valueDict) {
        var btns = this.node_btnGroup.children, index;
        for(var i = 0; i < btns.length; i ++){
            index = parseInt(i);
            btns[i].tag = valueDict[index];
            btns[i].getComponent(this._btnCompName).showMoney(valueDict[index], false);
        }
    },
    chooseMoney : function (index) {
        var btn = this._getBtnByIndex(index);
        //获取第一次进房是否可下注
        this.isBetting = GG.bull100Mgr.getFirstBetting();
        this._showChooseEffect(btn);
    },

    //move touch effect
    _showChooseEffect : function (targetBtn) {
        this._chooseBtn(targetBtn);

        //effect
        // this.node_effectOnButton.active = true;
        this.node_effectUnderButton.active = true;

        // this.node_effectOnButton.position = cc.p(targetBtn.x, targetBtn.y);;
        this.node_effectUnderButton.x = targetBtn.x;
        this.node_effectUnderButton.y = -targetBtn.height/2;
        //刚进入游戏如果可下注 强制自适应
        // if (this.isBetting && !this._isAdaptPos) {
        //     this.node_effectUnderButton.position = G_TOOL.adaptPos(this.node_effectUnderButton.position);
        //     var size = G_TOOL.adaptSize(targetBtn.width, targetBtn.height);
        //     this.node_effectUnderButton.width = size.width;
        //     this._isAdaptPos = true;
        // } else {
        //     this.node_effectUnderButton.width = targetBtn.width;
        // }
        ////ani
        // //this.node_effectOnButton.getComponent(cc.Animation).play();
    },
    _hideEffect : function () {
        // this.node_effectOnButton.active = false;
        this.node_effectUnderButton.active = false;
    },


    //button touch move up
    _chooseBtn : function (targetBtn) {
        this._cancelChoose();
        targetBtn.getComponent(this._btnCompName).showMoney(null, true);
        //targetBtn.y += targetBtn.height/2;
        this.setBtnEnable(targetBtn, false, true);
        this._lastBtn = targetBtn;
    },
    _cancelChoose : function () {
        if(this._lastBtn){
            this._hideEffect();
            this._lastBtn.getComponent(this._btnCompName).showMoney(null, false);
            this.setBtnEnable(this._lastBtn, true);
            this._lastBtn.y = 0;
            this._lastBtn = null;
        }
    },

    setBtnGroupEnable : function (isEnable) {
        this._cancelChoose();
        var btnGroup = this.node_btnGroup.children;
        for(var i = 0; i < btnGroup.length; i ++){
            this.setBtnEnable(btnGroup[i], isEnable);
        }
    },

    refreshCanChooseBtn : function (useGold) {
        if(!this._list_myGrabOption) this._list_myGrabOption = GG.bull100Mgr.getMyselfComp().getMoneyChooseDict();
        var btnGroup = this.node_btnGroup.children;
        if(!G_DATA.isNumber(useGold)) useGold = GG.bull100Mgr.getMyselfComp().getUsableBalance();
        var targetChoose = null;

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
            var lastChoose = GG.bull100Mgr.getMyselfComp().getChooseChip() - 1;
            if(lastChoose < targetChoose) targetChoose = lastChoose;

            this._showChooseEffect(btnGroup[targetChoose]);
            GG.bull100Mgr.getMyselfComp().chooseChip(targetChoose+1);
        }
    },

    //是否显示续庄和下庄的按钮
    _setIsShowDealerBtn : function (isShow) {
        this.node_continueDealer.active = isShow;
    },
    //隐藏底部的所有按钮
    setBottomIsShow : function (isShow) {
        this.setBtnGroupEnable(false);
        this.node_btnGroup.active = isShow;
        this._setIsShowDealerBtn(!isShow);
    },

    _getBtnByIndex : function (index) {
        return this.node_btnGroup.children[index-1]
    },

    //获取吃瓜群众投注起点
    getIdleBettingPos : function () {
        var pos = this.node_rightBtn1.position;
        var basePos = this.node_rightBtn1.parent.parent.position;
        var posX = pos.x + this.node_rightBtn1.parent.x+basePos.x;
        var posY = pos.y + this.node_rightBtn1.parent.y+basePos.y + this.node_rightBtn1.height/2;
        return cc.p(posX, posY)
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
