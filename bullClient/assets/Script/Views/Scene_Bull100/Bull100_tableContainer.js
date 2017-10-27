//存放所有的桌子


cc.Class({
    extends: cc.Component,

    properties: {
        prefab_table : cc.Prefab,                               //桌子预制体
    },

    // use this for initialization
    onLoad: function () {
        this._tableCompName = 'Bull100_table';

        //this._addTables();
    },

    _addTables : function () {
        var tableNum = 4;
        for(var i = 0; i < tableNum; i ++){
            var table = cc.instantiate(this.prefab_table);
            if(!table) continue;
            table.parent = this.node;
        }
    },

    //初始化所有的桌子
    initAllTables : function () {
        var tables = this.node.children;
        var table,comp;
        for(var i = 0; i < tables.length; i ++){
            table = tables[i];
            comp = table.getComponent(this._tableCompName);
            if(comp) comp.initTable(i);
        }
    },

    //获取每张桌子对应的卡牌的位置
    getTablePokerPos : function () {
        var tables = this.node.children;
        var table,comp, posList = [];
        for(var i = tables.length-1; i >= 0; i --){
            table = tables[i];
            comp = table.getComponent(this._tableCompName);
            if(comp) {
                posList.push(comp.getPokerPos());
            }
        }
        return posList
    },

    //开启所有桌子的点击功能
    setTableTouchEnable : function (isEnable) {
        var tables = this.node.children;
        var table,comp;
        for(var i = 0; i < tables.length; i ++){
            table = tables[i];
            comp = table.getComponent(this._tableCompName);
            if(comp) comp.setTouchEnable(isEnable);
        }
    },

    //显示某张桌子的胜利失败情况
    showTableWin : function (tableIndex, showWin) {
        var tables = this.node.children;
        var containerIndex = GG.bull100Mgr.getTableContainerIndex(tableIndex);
        var table = tables[containerIndex];
        if(table){
            var comp = table.getComponent(this._tableCompName);
            if(comp) {
                comp.showWin(tableIndex, showWin);
            }
        }
    },

    //获取对应桌子索引的，多个随机金币位置
    getGoldPosList : function (tableIndex, goldNum) {
        var tables = this.node.children;
        var containerIndex = GG.bull100Mgr.getTableContainerIndex(tableIndex);
        var table = tables[containerIndex];
        if(table) {
            return table.getComponent(this._tableCompName).getGoldPosList(tableIndex, goldNum);
        }
        return null
    },

    //设置桌子的成功投注信息
    setBettingSuccess : function (tableIndex, goldValue, isMine) {
        var tables = this.node.children;
        var containerIndex = GG.bull100Mgr.getTableContainerIndex(tableIndex);
        var table = tables[containerIndex];
        if(table) {
            //设置自己桌子的成功投注信息
            if (isMine) table.getComponent(this._tableCompName).setMyselfBettingSuccess(tableIndex, goldValue);
            else table.getComponent(this._tableCompName).setBettingSuccess(tableIndex, goldValue);
        }
    },
    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
