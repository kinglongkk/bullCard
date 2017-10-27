//显示百人的赛事结果界面


cc.Class({
    extends: cc.Component,

    properties: {
        _list_rankHead : null,                                                              //排行的头像列表
        _winColor : null,                                                //胜利的颜色
        _loseColor : null,                                               //失败的颜色

        node_myWinGold : {
            default : null,
            type : cc.Node,
            displayName : '自己赢的钱'
        },
        prefab_head : {
            default : null,
            type : cc.Prefab,
            displayName : '头像'
        },
        node_container : {
            default : null,
            type : cc.Node,
            displayName : '头像容器'
        },
    },

    // use this for initialization
    onLoad: function () {
        this._headCompName = 'Obj_winRankHead';
        this._loseColor = new cc.Color(199,199,199);
    },

    //显示结果
    showResult : function (dataObj) {
        this.node.active = true;
        var myChangeGold = dataObj.myChangeGold;
        if(!myChangeGold) myChangeGold = 0;
        this._setGoldValue(myChangeGold);

        var dataList = [
            {
                name : '人1',
                addGold : 99
            }
        ];
        var rankDict = dataObj.othersInfo;
        if(rankDict) {
            dataList = [];
            var info;
            for(var seatIndex in rankDict){
                info = rankDict[seatIndex];
                var changeGold = info.changeGold;
                if(changeGold > 0){
                    dataList.push({
                        name : info.name,
                        addGold :changeGold,
                        icon : info.icon
                    })
                }
            }
        }
        this._showRank(dataList);
    },

    _showRank : function (dataList) {
        var heads = this.node_container.children;
        var len = heads.length, head, data;
        for(var i = 0; i < heads.length; i ++){
            head = heads[i].getComponent(this._headCompName);
            if(head){
                data = dataList[i];
                if(data){
                    head.setIsShow(true);
                    head.setData(data);
                }else{
                    head.setIsShow(false);
                }
            }
        }
    },

    _setGoldValue : function (newValue) {
        var isPlus = Boolean(newValue > 0);
        this._setMyGoldColor(isPlus);
        var showStr;
        if(isPlus) showStr = '+'+newValue;
        else showStr = newValue+'';
        this.node_myWinGold.getComponent(cc.Label).string = showStr;
    },

    _setIsShow : function (isShow) {
        this.node.active = isShow;
    },

    _setMyGoldColor : function (isWin) {
        if(isWin){
            if(this._winColor) {
                this.node_myWinGold.color = this._winColor;
                this._winColor = null;
            }
        }else{
            if(!this._winColor) this._winColor = this.node_myWinGold.color;
            this.node_myWinGold.color = this._loseColor;
        }
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
