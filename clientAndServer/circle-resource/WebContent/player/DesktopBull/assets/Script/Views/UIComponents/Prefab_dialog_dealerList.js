//上庄列表的界面


cc.Class({
    extends: require('BaseDialog'),

    properties: {
        _minDealerGold : null,                                                                                            //最小上庄金额

        node_bottomBtns : {
            default : null,
            type : cc.Node,
            displayName : '按钮容器'
        },
        node_goldTip : {
            default : null,
            type : cc.Node,
            displayName : '金币限制提示'
        },
        comp_goldSlider : {
            default : null,
            type : require('Obj_dealerListSlider'),
            displayName : '进度条'
        },
    },

    // use this for initialization
    onLoad: function () {
        this._super('UI_dealerList');
        this._dealerItemCompName = 'Obj_dealerListCell';
        this._moneyFormat = '<color=#FFFD61>%s</c>';
        this._limitText = [G_CHINESE.dealerGoldText1, G_CHINESE.dealerGoldText2, G_CHINESE.dealerGoldText3, G_CHINESE.dealerGoldText4];
        this._changeInterval = G_Config_bull100.dealerSliderInterval;

        this._registerEvent();
        this._initDealerList();
    },

    start : function () {

    },

    setData : function (dataObj) {
        this._minDealerGold = GG.bull100Mgr.getMinDealerGold();
        var myGold = dataObj.myGold;
        var dealerInfo = dataObj.dealerInfo;
        //var myMaxDealerGold = dealerInfo.maxDealerCoin;
        var myselfID = GG.getPlayerID();
        var isInDealerList = false;                             //玩家自己在上庄列表
        var rankList = dataObj.rankList;
        if(!rankList) rankList = [];
        var dataList = [], playerData, isDealer, myRankNum='';
        for(var i = 0; i < rankList.length; i ++){
            playerData = rankList[i];
            isDealer = false;

            if(playerData.playerId == dealerInfo.playerId) {
                dealerInfo = null;
                isDealer = true;
                dataList.splice(0,0,{
                    headImg : playerData.icon,
                    name : playerData.nickname,
                    gold : playerData.usableBalance,
                    isDealer : isDealer
                });
            }else{
                dataList.push({
                    headImg : playerData.icon,
                    name : playerData.nickname,
                    gold : playerData.usableBalance,
                    isDealer : isDealer
                })
            }
            if(playerData.playerId == myselfID) {
                isInDealerList = true;
                myRankNum = i+1;
            }
        }
        if(dealerInfo){
            dataList.splice(0,0,{
                headImg : dealerInfo.icon,
                name : dealerInfo.nickname,
                gold : dealerInfo.usableBalance,
                isDealer : true
            });
            if(dealerInfo.playerId == myselfID) {
                isDealer = true;
                isInDealerList = true;
            }
        }

        this.setBtnShow(isInDealerList, isDealer);
        this._setDealerList(dataList);

        var offGold = this._changeInterval;
        if (isInDealerList) {
            //在上庄列表
            this.comp_goldSlider.setData(myGold, offGold, offGold);
            if(isDealer){
                this._showDownTip(this._limitText[2]);
            }else{
                //在排队中
                this._showDownTip(G_TOOL.formatStr(this._limitText[3], myRankNum));
            }
        } else {
            //不在上庄列表
            this.comp_goldSlider.setData(myGold, offGold, this._minDealerGold);

            if(myGold < this._minDealerGold){
                //金币不足，无法上庄
                this._showDownTip(this._limitText[1], this._minDealerGold);
                this.setBtnEnable(this.node_bottomBtns.children[1], false);

            }else{
                //金币足够
                this._showDownTip(this._limitText[0], this._minDealerGold);
                this.setBtnEnable(this.node_bottomBtns.children[1], true);
            }
        }
    },

    _registerEvent : function () {
        var btns = this.node_bottomBtns.children;
        for(var i = 0; i < btns.length; i ++){
            btns[i].tag = i;
            this.registerButton(btns[i], this._onClick_dealerBtnsr, this);
        }
    },
    hideLayer : function (event) {
        this._super();
    },
    _onClick_dealerBtnsr : function (event) {
        switch (event.currentTarget.tag){
            case 0:
                //续庄
                GG.bull100Mgr.commitContinueDealer(this.comp_goldSlider.getMoveNum());
                break
            case 1:
                //上庄
                GG.bull100Mgr.send_upDealer(this.comp_goldSlider.getMoveNum());
                break
            case 2:
                //下庄
                GG.bull100Mgr.send_downDealer();
                break
            default:
                break
        }
        //cc.eventManager.pauseTarget(this.comp_goldSlider.node, true);
    },


    //初始化庄家列表
    _initDealerList : function () {
        var dataObj = {
            itemPrefab : this.prefab_item,
        }
        this.comp_itemContainer.setData(dataObj);
    },
    //设置列表内容
    _setDealerList : function (dataList) {
        var item, comp, i = 0;
        for(i = 0; i < dataList.length; i ++){
            item = this.comp_itemContainer.getItemByIndex(i);
            if(item){
                comp = item.getComponent(this._dealerItemCompName);
                comp.setData(dataList[i]);
            }
        }
        this.comp_itemContainer.clearItems(i - 1);
    },

    //===================================

    //设置上庄金额限制提示
    setGoldLimitTip : function (myGold, minDealerGold) {
        var str;
        if(myGold > minDealerGold) {
            str = this._limitText[0];
        } else {
            str = this._limitText[1];
        }
        this.node_goldTip.getComponent(cc.Label).string = str;
    },

    //根据是否已经在庄家列表来判定按钮的显隐
    setBtnShow : function (isMyDealer, isDealer) {
        var btns = this.node_bottomBtns.children;
        btns[0].active = isMyDealer || isDealer;//我要续庄
        btns[1].active = !(isMyDealer || isDealer);//我要上庄
        btns[2].active = isMyDealer || isDealer;//我要下庄
    },

    //上庄成功
    upDealerSuccess : function (dataObj) {
        this.showLayer();
        this.setData(dataObj);
        if(dataObj.tipStr){
            var tipObj = {
                tipIndex : 19,
                showStr : dataObj.tipStr,
                isCountDown : false,
                showPos : G_DATA.getCenterTipPos(),
                //callBack : this.on_beforeStart.bind(this)
            }
            // GG.tipsMgr.showTxtTip(tipObj);
        }
    },

    //从上庄列表下来
    downDealerSuccess : function (dataObj) {
        this.showLayer();
        this.setData(dataObj);
        if(dataObj.tipStr){
            var tipObj = {
                tipIndex : 19,
                showStr : dataObj.tipStr,
                isCountDown :false,
                showPos : G_DATA.getCenterTipPos(),
            }
            // GG.tipsMgr.showTxtTip(tipObj);
        }
    },

    //==============================

    //设置底部的tip限制提示
    _showDownTip : function (leftStr, gold) {
        if(gold) gold = G_TOOL.formatStr(this._moneyFormat, G_TOOL.changeMoney(gold));
        else gold = '';
        this.node_goldTip.getComponent(cc.RichText).string = leftStr + gold;
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
