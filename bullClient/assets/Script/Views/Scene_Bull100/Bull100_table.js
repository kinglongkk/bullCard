//一张赌桌

cc.Class({
    extends: cc.Component,

    properties: {
        _typeValue : null,                                          //该桌赌牌的类型值
        _pool : null,                                                //放置金币的容器
        _bigTableIndex : null,                                      //当前桌子的索引,同时也是取大的桌子的索引
        _minTableIndex : null,                                      //是取小的桌子的索引
        _bigCenterPos : null,                                       //左边的金币坐标中心
        _minCenterPos : null,                                       //右边的金币坐标中心
        _winTableType : null,                                       //胜利的桌子类型

        _bigGoldNum : null,                                        //左边金币
        _minGoldNum : null,                                       //右边金币
        _bigMyGoldNum : null,                                      //左边金币
        _minMyGoldNum : null,                                    //右边金币

        node_big : cc.Node,
        node_min : cc.Node,
        frame_bigLight : cc.SpriteFrame,
        frame_minLight : cc.SpriteFrame,
        //显示金币的上下label
        node_bigUpLabel : {
            default : null,
            type : cc.Node,
            displayName : '押大总额节点'
        },
        node_bigDownLabel : {
            default : null,
            type : cc.Node,
            displayName : '自己押大节点'
        },
        node_minUpLabel : {
            default : null,
            type : cc.Node,
            displayName : '押小总额节点'
        },
        node_minDownLabel : {
            default : null,
            type : cc.Node,
            displayName : '自己押小节点'
        },
        node_winFrame : {
            default : null,
            type : cc.Node,
            displayName : '胜利特效框'
        },
        label_leftUp : {
            default : null,
            type : cc.Label,
            displayName : '所有玩家押大金币总额'
        },
        label_rightUp : {
            default : null,
            type : cc.Label,
            displayName : '所有玩家押小金币总额'
        },
    },

    // use this for initialization
    onLoad: function () {
        this._scrollCompName = 'Obj_scrollValue';

        //卡牌X位置偏移
        this._pokerOffX = 0.4;
        this._winColor = this.node_bigDownLabel.children[1].color;
        this._lostColor = new cc.Color(189,188,188);
    },

    //初始化赌桌
    initTable : function (_index) {
        this._setTableIndex(_index);
        this._setBigMultiple(0);
        this._setMinMultiple(0);

        this._setBigDownGold(0);
        this._setBigUpGold(0);
        this._setMinDownGold(0);
        this._setMinUpGold(0);
        this.resetWinShow();
    },

    onClick_big : function () {
        var mgr = GG.bull100Mgr;
        var goldNum = mgr.getMyselfFlyGoldNum();
        mgr.touchTable(this._bigTableIndex, this._getBigGoldPos(goldNum));
    },
    onClick_min : function () {
        var mgr = GG.bull100Mgr;
        var goldNum = GG.bull100Mgr.getMyselfFlyGoldNum();
        GG.bull100Mgr.touchTable(this._minTableIndex, this._getMinGoldPos(goldNum));
    },

    //投注成功后显示该桌子的投注信息
    setBettingSuccess : function (tableIndex, goldValue) {
        if(tableIndex%2 == 0){
            //所有玩家押大总额显示
            this._bigGoldNum += goldValue;
            this._setBigUpGold(this._bigGoldNum);
        }else{
            //所有玩家押小总额显示
            this._minGoldNum += goldValue;
            this._setMinUpGold(this._minGoldNum);
        }
    },

    //自己投注成功后显示该桌子的投注信息
    setMyselfBettingSuccess : function (tableIndex, goldValue) {
        if(tableIndex%2 == 0){
            //自己押大
            this._bigMyGoldNum += goldValue;
            this._setBigDownGold(this._bigMyGoldNum);
        }else{
            //自己押小
            this._minMyGoldNum += goldValue;
            this._setMinDownGold(this._minMyGoldNum);
        }
        this.setBettingSuccess(tableIndex, goldValue);
    },

    //显示该桌子上金币的胜利
    showWin : function (tableIndex, multiple) {
        //区域倍数
        var bettingLabel=null, curGoldValue = null;
        if(!multiple) multiple = 1;
        if(!this._firstBigImg) this._firstBigImg = this.node_big.getComponent(cc.Sprite).spriteFrame;
        if(!this._firstMinImg) this._firstMinImg = this.node_min.getComponent(cc.Sprite).spriteFrame;
        this.node_winFrame.active = true;
        //this.node_winFrame.width = this.node_big.parent.width*1.15;

        if(tableIndex%2 == 0){
            //大
            this._winTableType = G_TYPE.tableType.big;
            this.node_big.getComponent(cc.Sprite).spriteFrame = this.frame_bigLight;
            this.node_winFrame.x = this.node_big.parent.x;
            if(this._bigMyGoldNum > 0) {
                this._setBigMultiple(multiple);
                bettingLabel = this.node_bigDownLabel.children[1].getComponent(cc.Label);
                curGoldValue = this._bigMyGoldNum;
            }
            //lost
            if(this._minMyGoldNum > 0){
                this._setMinMultiple(multiple);
                this._setMinDownGold(this._minMyGoldNum*(multiple), true)
                this.node_minDownLabel.children[0].color = this._lostColor;
                this.node_minDownLabel.children[1].color = this._lostColor;
            }
        }else{
            //小
            this._winTableType = G_TYPE.tableType.min;
            this.node_min.getComponent(cc.Sprite).spriteFrame = this.frame_minLight;
            this.node_winFrame.x = this.node_min.parent.x;
            if(this._minMyGoldNum > 0) {
                this._setMinMultiple(multiple);
                bettingLabel = this.node_minDownLabel.children[1].getComponent(cc.Label);
                curGoldValue = this._minMyGoldNum;
            }

            //lost
            if(this._bigMyGoldNum > 0){
                this._setBigMultiple(multiple);
                this._setBigDownGold(this._bigMyGoldNum*(multiple), true)
                this.node_bigDownLabel.children[0].color = this._lostColor;
                this.node_bigDownLabel.children[1].color = this._lostColor;
            }
        }

        if(bettingLabel){
            //胜利区域有投注
            var dataObj = {
                startNum : 0,
                targetNum : curGoldValue*(multiple),
                label : bettingLabel,
                formatStr : '',
                callFunc : this._scrollCall.bind(this)
            }
            this.node.getComponent(this._scrollCompName).scrollLabel(dataObj);
        }
    },
    //胜利滚动结束
    _scrollCall : function () {

    },
    //清理胜利的显示
    resetWinShow : function () {
        this.node_winFrame.active = false;
        switch (this._winTableType){
            case G_TYPE.tableType.big:
                this.node_big.getComponent(cc.Sprite).spriteFrame = this._firstBigImg;
                this.node_minDownLabel.children[0].color = this._winColor;
                this.node_minDownLabel.children[1].color = this._winColor;
                break;
            case G_TYPE.tableType.min:
                this.node_min.getComponent(cc.Sprite).spriteFrame = this._firstMinImg;
                this.node_bigDownLabel.children[0].color = this._winColor;
                this.node_bigDownLabel.children[1].color = this._winColor;
                break;
            default:
                break;
        }
    },

    //====================================================

    setTouchEnable : function (isEnable) {
        var bigParent = this.node_big.parent;
        var minParent = this.node_min.parent;
        if(isEnable){
            bigParent.on(cc.Node.EventType.TOUCH_START, this.onClick_big, this);
            minParent.on(cc.Node.EventType.TOUCH_START, this.onClick_min, this);
        }else {
            bigParent.off(cc.Node.EventType.TOUCH_START, this.onClick_big, this);
            minParent.off(cc.Node.EventType.TOUCH_START, this.onClick_min, this);
        }
    },

    //获取随机的金币位置
    getGoldPosList : function (tableIndex, goldNum) {
        var posList;
        if(tableIndex%2 == 0){
            //大
            posList = this._getBigGoldPos(goldNum);
        }else posList = this._getMinGoldPos(goldNum);
        return posList
    },
    _getBigGoldPos : function (num) {
        if(!num) num = 1;
        var goldPos, posList = [];
        for(var i = 0; i < num; i ++){
            goldPos = this._initGoldPos();
            goldPos.x += this._bigCenterPos.x;
            goldPos.y += this._bigCenterPos.y;
            posList.push(goldPos);
        }
        return posList
    },
    _getMinGoldPos : function (num) {
        if(!num) num = 1;
        var goldPos, posList = [];
        for(var i = 0; i < num; i ++){
            goldPos = this._initGoldPos();
            goldPos.x += this._minCenterPos.x;
            goldPos.y += this._minCenterPos.y;
            posList.push(goldPos);
        }
        return posList
    },
    _initGoldPos : function () {
        var parentScale = this.node.parent.scale;
        var bigBG = this.node_big.parent;
        if(!this._bigCenterPos){
            var parentPos = this.node.parent.position;
            var minBG = this.node_min.parent;
            this._bigCenterPos = cc.p(parentPos.x + (this.node.x + bigBG.x)*parentScale, parentPos.y + (this.node.y + bigBG.y)*parentScale);
            this._minCenterPos = cc.p(parentPos.x + (this.node.x + minBG.x)*parentScale, parentPos.y + (this.node.y + minBG.y)*parentScale);
            this._goldHeight = (bigBG.height*0.5-this.node_bigDownLabel.height)*parentScale;
        }

        //假设金币的宽度为该值
        var goldWidth2 = this._getGoldSize().width*0.5*0.5;
        var dir;

        var maxX = bigBG.width*0.5*parentScale-goldWidth2;
        var goldX = G_TOOL.getRandomArea(0, maxX);
        dir = G_TOOL.getRandomBool() ? 1 : -1;
        goldX*=dir;

        var maxY = this._goldHeight-goldWidth2;
        var goldY = G_TOOL.getRandomArea(0, maxY);
        dir = G_TOOL.getRandomBool() ? 1 : -1;
        goldY*=dir;
        return cc.p(goldX, goldY)
    },

    //----------------------------

    //所有玩家押大总额
    _setBigUpGold : function (goldNum) {
        if(!goldNum){
            this._bigGoldNum = 0;
            this.node_bigUpLabel.active = false;
            this.label_leftUp.string = '';
        }else{
            this.node_bigUpLabel.active = true;
            var labelNode = this.label_leftUp;
            if(labelNode) labelNode.string = G_TOOL.changeMoney(goldNum);
        }
    },
    //所有玩家押小总额
    _setMinUpGold : function (goldNum) {
        if(!goldNum){
            this._minGoldNum = 0;
            this.node_minUpLabel.active = false;
            this.label_rightUp.string = '';
        }else{
            this.node_minUpLabel.active = true;
            var labelNode = this.label_rightUp;
            if(labelNode) labelNode.string = G_TOOL.changeMoney(goldNum);
        }
    },

    //自己押注大显示
    _setBigDownGold : function (goldNum, isLoseShow) {
        if(!goldNum){
            this._bigMyGoldNum = 0;
            this.node_bigDownLabel.active = false;
        }else{
            this.node_bigDownLabel.active = true;
            var labelNode = this.node_bigDownLabel.children[1];
            var gold;
            if(labelNode) gold = G_TOOL.changeMoney(goldNum);
            if (isLoseShow) gold = '-' + gold;//输钱显示为-
            labelNode.getComponent(cc.Label).string = gold;
        }
    },
    //设置押大的时候倍数的位置
    _setBigMultiple : function (multiple) {
        var labelNode = this.node_bigDownLabel.children[0];
        if(labelNode) {
            var str = '', rightNode = this.node_bigDownLabel.children[1];
            if(multiple) {
                str = "X"+multiple;
                this._changeDownLabel(rightNode, true);
            }else{
                this._changeDownLabel(rightNode, false);
            }
            labelNode.getComponent(cc.Label).string = str;
        }
    },

    //----------------------------

    //自己押注小显示
    _setMinDownGold : function (goldNum, isLoseShow) {
        if(!goldNum){
            this._minMyGoldNum = 0;
            this.node_minDownLabel.active = false;
        }else{
            this.node_minDownLabel.active = true;
            var labelNode = this.node_minDownLabel.children[1];
            var gold;
            if(labelNode) gold = G_TOOL.changeMoney(goldNum);
            if (isLoseShow) gold = '-' + gold;//输钱显示为-
            labelNode.getComponent(cc.Label).string = gold;
        }
    },
    //设置押小的时候倍数的位置
    _setMinMultiple : function (multiple) {
        var labelNode = this.node_minDownLabel.children[0];
        if(labelNode) {
            var str = '', rightNode = this.node_minDownLabel.children[1];
            if(multiple) {
                str = "X"+multiple;
                this._changeDownLabel(rightNode, true);
            }else{
                this._changeDownLabel(rightNode, false);
            }
            labelNode.getComponent(cc.Label).string = str;
        }
    },

    //设置底部label随着倍数是否显示而变化位置
    _changeDownLabel : function (curLabel, isRight) {
        if(isRight){
            curLabel.anchorX = 1;
            curLabel.x = curLabel.parent.width*0.43;
        }else{
            curLabel.anchorX = 0.5;
            curLabel.x = 0;
        }
    },
    //设置索引信息
    _setTableIndex : function (index) {
        this._bigTableIndex = GG.bull100Mgr.getBigTableIndex(index);
        this._minTableIndex = GG.bull100Mgr.getMinTableIndex(index);
    },


    //获取桌子底下卡牌的位置
    getPokerPos : function () {
        var parentPos = this.node.parent.position;
        var scale = this.node.parent.scale;
        var posX = parentPos.x + this.node.x*scale - this.node.width*0.5*scale*this._pokerOffX;
        var posY = parentPos.y + this.node.y*scale - this.node.height*0.5*scale;
        return cc.p(posX, posY)
    },
    //获取父节点的坐标
    _getParentPos : function () {
        var parentPos = this.node.parent.position;
        var scale = this.node.parent.scale
        return cc.p(parentPos.x*scale, parentPos.y*scale)
    },
    //获取金币的宽高
    _getGoldSize : function () {
        if(!this._goldSize){
            this._goldSize = GG.bull100Mgr.getGoldImgSize()
        }
        return this._goldSize
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
