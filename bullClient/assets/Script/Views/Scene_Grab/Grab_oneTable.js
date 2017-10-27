//单个投注区域格子


cc.Class({
    extends: cc.Component,

    properties: {
        _ownerGold : 0,
        _areaGold : 0,
        _goldFrame : null,
        _bullType : null,
        _multiple : 1,
        _maxAdaptGold : null,
        _curGoldNum : null,
        _isWin : null,

        num_result : {
            default : 0,
            type : cc.Integer,
            displayName : '代表的结果值'
        },
        label_multiple : {
            default : null,
            type : cc.Label,
            displayName : '倍数'
        },
        node_goldContainer : {
            default : null,
            type : cc.Node,
            displayName : '金币容器'
        },
        label_areaValue : {
            default : null,
            type : cc.Label,
            displayName : '格子内总金币'
        },
        label_ownerValue : {
            default : null,
            type : cc.Label,
            displayName : '投注值'
        },
        label_resultMultiple : {
            default : null,
            type : cc.Label,
            displayName : '结果倍数'
        }
    },

    // use this for initialization
    onLoad: function () {
        this._init();
    },

    _init : function () {
        this._ownerFormatStr = '<b>%s</b>';
        //this._areaFormatStr = '<b>%s</b>';
        //this._multipleFormatStr = '<b>%s</b>';
        //this._areaFormatStr = '';
        //this._multipleFormatStr = '';
        //this.node_goldContainer.x += 200;
        this._goldOffY = -this.node.height * 0.15;
        var rate = G_Config_grab.num_goldImgArea;
        // this._minGoldX = -this.node.width*rate*1.3;
        this._maxGoldX = this.node.width*rate*1.3;
        // this._minGoldY = this.node.height*rate*0.35;
        this._maxGoldY = - this.node.height*rate*0.35
        this._maxAdaptGold = 50;
        this._curGoldNum = 0;
        this._loseColor = new cc.Color(253, 41,31); //失败的颜色
        this._winColor = this.label_ownerValue.node.color, //胜利的颜色


        this._goldScale = G_Config_grab.scale_moveGold;
        this.comp_scrollValue = this.getComponent('Obj_scrollValue');
    },

    setInfo : function (goldFrame, bullType, multiple) {
        this._goldFrame = goldFrame;
        this._bullType = bullType;
        this._multiple = parseInt(multiple);
    },

    clearData : function () {
        this._ownerGold = 0;
        this._areaGold = 0;
        this._isWin = false;

        this._setMultiple();
        this._clearGoldImg();
        this.label_areaValue.string = '';
        this.label_ownerValue.node.color = this._winColor;//胜利颜色
        this._setOwnerGold(0);
    },

    OnClick_table : function (event) {
        var dataObj = G_OBJ.data_grabBetting();
        dataObj.tableIndex = this.getResultType();
        dataObj.targetPos = this.getGoldCenterPos();
        dataObj.callFunc = this._goldAdd.bind(this);

        GG.grabMgr.playMyGoldMove(dataObj);
    },
    _goldAdd : function () {
        this._addOneGoldImg();
    },

    otherAddGold : function (goldInfo) {
        goldInfo.targetPos = this.getWorldPos();
        goldInfo.callFunc = this._goldAdd.bind(this);

        var addGoldValue = goldInfo.goldValue;
        if(goldInfo.isMine){
            this.addOwnerGold(addGoldValue);//自己押注
        }else this._addOthersGold(addGoldValue);//他人押注
        return goldInfo
    },

    //自己押注值
    addOwnerGold : function (goldValue) {
        if(!goldValue) return;
        this._ownerGold += goldValue;
        this._setOwnerGold(this._ownerGold);
        this._addOthersGold(goldValue);
    },
    //他人押注值
    _addOthersGold : function (goldValue) {
        if(!goldValue) goldValue = 0;
        this._areaGold += goldValue;
        this._setAreaGold(this._areaGold);
    },

    _setOwnerGold : function (goldNum) {
        if(!goldNum){
            this.label_ownerValue.node.parent.active = false;
        }else{
            this.label_ownerValue.node.parent.active = true;
            //this.label_ownerValue.string = G_TOOL.formatStr(this._ownerFormatStr, G_TOOL.changeMoney(goldNum));
            this.label_ownerValue.string = G_TOOL.changeMoney(goldNum);
            //this.label_ownerValue.string = G_TOOL.changeMoney(goldNum);
            this.label_resultMultiple.string = '';
            this._changeDownLabel(this.label_ownerValue.node, false);
        }
    },
    _setAreaGold : function (goldNum) {
        if(!goldNum) this.label_areaValue.string = '';
        else this.label_areaValue.string = G_TOOL.changeMoney(goldNum);
        //else this.label_areaValue.string = G_TOOL.formatStr(this._areaFormatStr, G_TOOL.changeMoney(goldNum));
    },

    showOwnerResult : function () {
        if(this._ownerGold < 1) return;
        var showStr = G_TOOL.changeMoney(this._ownerGold);
        this._changeDownLabel(this.label_ownerValue.node, this._isWin);
        if(!this._isWin) {
            showStr = '-'+showStr;
            this.label_resultMultiple.string = '';
            this.label_ownerValue.string = showStr;
            this.label_ownerValue.node.color = this._loseColor;//失败颜色
        } else{
            //胜利
            this.label_resultMultiple.string = 'X'+this._multiple;
            var winValue = this._ownerGold*this._multiple;
            var dataObj = {
                startNum : 0,
                targetNum : winValue,
                label : this.label_ownerValue,
                formatStr : '',
                callFunc : function () {
                    this.label_ownerValue.string = G_TOOL.changeMoney(winValue);
                    //this.label_ownerValue.node.color = this._winColor;//胜利颜色
                }.bind(this)
            }
            this.comp_scrollValue.scrollLabel(dataObj);
        }
        //this.label_ownerValue.string = G_TOOL.formatStr(this._ownerFormatStr, showStr);
    },
    //滚动金币结束
    _scrollCall : function () {

    },
    _setMultiple : function () {
        var str = this._multiple + '倍'
        this.label_multiple.string = str;
    },
    _addOneGoldImg : function () {
        if(!this._goldFrame) return;

        var rate = this._curGoldNum/this._maxAdaptGold;
        rate = Math.min(Math.max(rate, 0.55), 1);
        var isUp = G_TOOL.getRandomBool() ? 1 : -1;
        var goldX = G_TOOL.getRandomArea(0, this._maxGoldX*rate) * isUp;
        isUp = G_TOOL.getRandomBool() ? 1 : -1;
        var goldY = G_TOOL.getRandomArea(0, this._maxGoldY*rate) * isUp;
        var gold = this._getGold();
        gold.position = cc.p(0,this._goldOffY);
        gold.runAction(cc.moveTo(0.35,goldX,this._goldOffY+goldY));
        this._curGoldNum += 1;
    },

    removeOneGoldImg : function () {
        var gold = this.node_goldContainer.children[0];
        if(gold){
            var worldPos = this.getWorldPos();
            var curPos = cc.p(worldPos.x + gold.x, worldPos.y + gold.y);
            this._removeGold(gold);
            this._curGoldNum -= 1;
            return curPos
        }
        return null;
    },

    _getGold : function () {
        if(!this._pool) this._pool = new cc.NodePool('Grab_oneTable'+this.num_result);

        var goldNode = this._pool.get();
        if(!goldNode){
            goldNode = new cc.Node();
            var sp = goldNode.addComponent(cc.Sprite);
            sp.spriteFrame = this._goldFrame;
        }
        goldNode.parent = this.node_goldContainer;
        goldNode.scale = (1/this.node.parent.scale)*this._goldScale;
        goldNode.active = true;
        return goldNode;
    },
    _removeGold : function (goldeNode) {
        goldeNode.stopAllActions();
        goldeNode.active = false;
        this._pool.put(goldeNode);
    },

    _clearGoldImg : function () {
        var golds = this.node_goldContainer.children;
        for(var i = 0; i < golds.length; i ++){
            golds[i].destroy();
        }
        this._curGoldNum = 0;
    },

    setTouchEnable : function (isEnable) {
        if(isEnable){
            this.node.on(cc.Node.EventType.TOUCH_START, this.OnClick_table, this);
        }else this.node.off(cc.Node.EventType.TOUCH_START, this.OnClick_table, this);
    },
    //设置底部label随着倍数是否显示而变化位置
    _changeDownLabel : function (curLabel, isRight) {
        if(isRight){
            curLabel.anchorX = 1;
            curLabel.x = curLabel.parent.width*0.47;
        }else{
            curLabel.anchorX = 0.5;
            curLabel.x = 0;
        }
    },

    getWorldPos : function () {
        var parentPos = this.node.parent.position;
        var parentScale = this.node.parent.scale;
        return cc.p(parentPos.x + this.node.x*parentScale, parentPos.y+this.node.y*parentScale-this.node.height*0.4);//赢钱旗帜位置
    },
    getGoldCenterPos : function () {
        var parentPos = this.node.parent.position;
        var parentScale = this.node.parent.scale;
        return cc.p(parentPos.x + this.node.x*parentScale, this._goldOffY+parentPos.y+this.node.y*parentScale-this.node.height*0.2);
    },

    getResultType : function () {
        return this.num_result
    },
    setTableWin : function () {
        this._isWin = true;
    },

    getGrabGold : function () {
        return this._ownerGold * this._multiple
    },
    getLostGold : function () {
        return this._ownerGold
    },
    //获取剩余没有被移除的金币数量
    getLeaveGoldImg : function () {
        return this._curGoldNum
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
