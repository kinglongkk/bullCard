//可以点击投注的所有格子所在的层


cc.Class({
    extends: cc.Component,

    properties: {
        _dict_tables : null,

        frame_gold : {
            default : null,
            type : cc.SpriteFrame,
            displayName : '金币图片'
        },
    },

    // use this for initialization
    onLoad: function () {
        this._init();
    },

    _init : function () {
        this._talbeCompName = 'Grab_oneTable';
        if(!this._dict_tables) {
            this._dict_tables = {};
            var tables = this.node.children;
            for(var i = 0; i < tables.length; i ++){
                var table = tables[i].getComponent(this._talbeCompName);
                this._dict_tables[table.getResultType()] = table;
            }
        }
    },

    setStartInfo : function (areaInfo) {
        var multipleInfo = {};
        //for(var i = 0; i < areaInfo.length; i ++){
        //    var infoDict = areaInfo[i];
        //    multipleInfo[infoDict['area']] = infoDict['bei'];
        //}
        //multipleInfo = {0:2,1:13,2:13,3:13,4:13,5:13,6:13,7:13,8:13,9:13,10:12}
        multipleInfo = areaInfo;
        var table;
        for(var key in this._dict_tables){
            table = this._dict_tables[key];
            table.setInfo(this.frame_gold, key, multipleInfo[key]);
        }
    },

    clearData : function () {
        for(var key in this._dict_tables){
            this._dict_tables[key].clearData();
        }
    },

    //0--10
    touchOneTable : function (tableIndex, goldInfo) {
        return this._dict_tables[tableIndex].otherAddGold(goldInfo);
    },

    setTouchEnable : function (isEnable) {
        for(var key in this._dict_tables){
            this._dict_tables[key].setTouchEnable(isEnable);
        }
    },

    setTableWin : function (pokerResult) {
        var table = this._dict_tables[pokerResult];
        table.setTableWin(true);
        return table.getWorldPos();
    },
    showOwnerGold : function (dict) {
        for(var tableIndex in dict){
            this._dict_tables[tableIndex].showOwnerResult();
        }
    },
    getTablePos : function (pokerResult) {
        return this._dict_tables[pokerResult].getWorldPos();
    },

    getTable : function (pokerResult) {
        return this._dict_tables[pokerResult]
    },

    getWinGold : function (tableList) {
        var isWin = function (index) {
            for(var i = 0; i < tableList.length; i ++){
                if(index == tableList[i]) return true;
            }
            return false
        }

        var lostGold=0, winGold=0;
        for(var index in this._dict_tables){
            var isWinIndex = isWin(index);
            if(isWinIndex){
                winGold += this._dict_tables[index].getGrabGold()
            } else lostGold += this._dict_tables[index].getLostGold()
        }
        var goldNum = winGold - lostGold;
        return goldNum
    },
    //获取剩余没有被移除的金币信息
    getLeaveGoldImages : function () {
        var table, leaveGoldNum, recordDict={};
        for(var index in this._dict_tables){
            table = this._dict_tables[index];
            leaveGoldNum = table.getLeaveGoldImg();
            if(table && leaveGoldNum>0){
                //该区域还有剩余金币
                recordDict[index] = leaveGoldNum;
            }
        }
        return recordDict
    },


    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
