//在牌局中玩家金币的管理
cc.Class({
    extends: cc.Component,

    properties: {
        _balance : null,                                    //玩家的所有余额
        _usableBalance : null,                             //玩家的可用余额
        _bettingGoldValue : null,                          //玩家在本局中投注出去的金额
        _goldValueEX : null,                               //额外的金币，用于吃瓜群众扣除
        _block : null,                                      //挂载对象
    },

    // use this for initialization
    onLoad: function () {
        this._part_ui = this.getComponent('Part_playerBlock_UI');

        this._balance = 0;
        this._usableBalance = 0;
        this._bettingGoldValue = 0;
        this._goldValueEX = 0;
    },

    bindBlock : function (block) {
        this._block = block;
    },

    addBalance : function (value) {
        this._balance += value;
        this._block.showGoldValue(this._balance);
    },

    addUsableBalance : function (value) {
        this._usableBalance += value;
        this._block.showGoldValue(this._usableBalance);
    },

    payBalance : function (value) {
        this._balance -= value;
        this._block.showGoldValue(this._balance);
    },

    payUsableBalance : function (value) {
        this._usableBalance -= value;
        this._block.showGoldValue(this._usableBalance);
    },

    setBalance : function (value) {
        this._balance = value;
        this._block.showGoldValue(this._balance);
    },

    setUsableBalance : function (value) {
        this._usableBalance = value;
        //this._block.showGoldValue(this._usableBalance);
    },

    getBalance : function () {
        return this._balance;
    },

    getUsableBalance : function () {
        return this._usableBalance;
    },

    //已经投注的金额===================================

    setBettingGold : function (value) {
        this._bettingGoldValue = value;
    },
    addBettingGold : function (value) {
        this._bettingGoldValue += value;
    },
    getBettingGold : function () {
        return this._bettingGoldValue;
    },

    //额外计算的金币，用于吃瓜群众==============================

    setGoldValueEX : function (value) {
        this._goldValueEX = value;
    },

    addGoldValueEX : function (value) {
        this._goldValueEX += value;
    },

    payGoldValueEX : function (value) {
        this._goldValueEX -= value;
    },

    getGoldValueEX : function () {
        return this._goldValueEX;
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
