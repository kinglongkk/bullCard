"use strict";
cc._RF.push(module, 'b384bgA7DZM6IuGbjtJZqgz', 'Part_playerBlock_Gold');
// Script/Comp_parts/Battle/Part_playerBlock_Gold.js

'use strict';

//在牌局中玩家金币的管理
cc.Class({
    extends: cc.Component,

    properties: {
        _balance: null, //玩家的所有余额
        _usableBalance: null, //玩家的可用余额
        _bettingGoldValue: null, //玩家在本局中投注出去的金额
        _goldValueEX: null, //额外的金币，用于吃瓜群众扣除
        _block: null },

    // use this for initialization
    onLoad: function onLoad() {
        this._part_ui = this.getComponent('Part_playerBlock_UI');

        this._balance = 0;
        this._usableBalance = 0;
        this._bettingGoldValue = 0;
        this._goldValueEX = 0;
    },

    bindBlock: function bindBlock(block) {
        this._block = block;
    },

    addBalance: function addBalance(value) {
        this._balance += value;
        this._block.showGoldValue(this._balance);
    },

    addUsableBalance: function addUsableBalance(value) {
        this._usableBalance += value;
        this._block.showGoldValue(this._usableBalance);
    },

    payBalance: function payBalance(value) {
        this._balance -= value;
        this._block.showGoldValue(this._balance);
    },

    payUsableBalance: function payUsableBalance(value) {
        this._usableBalance -= value;
        this._block.showGoldValue(this._usableBalance);
    },

    setBalance: function setBalance(value) {
        this._balance = value;
        this._block.showGoldValue(this._balance);
    },

    setUsableBalance: function setUsableBalance(value) {
        this._usableBalance = value;
        //this._block.showGoldValue(this._usableBalance);
    },

    getBalance: function getBalance() {
        return this._balance;
    },

    getUsableBalance: function getUsableBalance() {
        return this._usableBalance;
    },

    //已经投注的金额===================================

    setBettingGold: function setBettingGold(value) {
        this._bettingGoldValue = value;
    },
    addBettingGold: function addBettingGold(value) {
        this._bettingGoldValue += value;
    },
    getBettingGold: function getBettingGold() {
        return this._bettingGoldValue;
    },

    //额外计算的金币，用于吃瓜群众==============================

    setGoldValueEX: function setGoldValueEX(value) {
        this._goldValueEX = value;
    },

    addGoldValueEX: function addGoldValueEX(value) {
        this._goldValueEX += value;
    },

    payGoldValueEX: function payGoldValueEX(value) {
        this._goldValueEX -= value;
    },

    getGoldValueEX: function getGoldValueEX() {
        return this._goldValueEX;
    }

});

cc._RF.pop();