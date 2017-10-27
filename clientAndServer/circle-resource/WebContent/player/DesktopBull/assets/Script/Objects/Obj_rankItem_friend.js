//好友排行榜条目处理


cc.Class({
    extends: cc.Component,

    properties: {

        richRankBG : {
            default : [],
            type : cc.SpriteFrame,
            displayName : '富豪榜排名背景'
        },
        gainRankBG : {
            default : [],
            type : cc.SpriteFrame,
            displayName : '盈利榜排名背景'
        },
        sprite_hat : {
            default : null,
            type : cc.Sprite,
            displayName : '皇冠'
        },
        labelRank: {
            default : null,
            type : cc.Label,
            displayName : '排名数字'
        },
        node_headImg : {
            default : null,
            type : cc.Node,
            displayName : '头像'
        },
        label_name : {
            default : null,
            type : cc.Label,
            displayName : '名字'
        },
        label_goldValue : {
            default : null,
            type : cc.Label,
            displayName : '金币数值'
        },
        node_goldBG : {
            default : null,
            type : cc.Node,
            displayName : '金币背景'
        },
    },

    // use this for initialization
    onLoad: function () {

    },

    init : function (isGain, rank, playerInfo) {
        this._setHat(isGain, parseInt(rank));
        this._setPlayerName(playerInfo.nickname);
        this._setGoldValue(playerInfo.walletbalance ? playerInfo.walletbalance : playerInfo.profitamount);
        G_TOOL.setHeadImg(this.node_headImg, playerInfo.avatarurl);
    },

    //设置前三名名次图标
    _setHat : function (isGain, rank) {
        if (rank < 3) {
            this.labelRank.node.active = false;
            if (isGain) {
                this.sprite_hat.spriteFrame = this.gainRankBG[rank];
            } else {
                this.sprite_hat.spriteFrame = this.richRankBG[rank];
            }
        } else {
            this.labelRank.node.active = true;
            this.labelRank.string = (rank + 1).toString();
        }
    },
    //玩家名字
    _setPlayerName : function (playerName) {
        if(!playerName) playerName = '';
        else playerName = G_TOOL.getNameLimit(playerName, 16, false);
        this.label_name.string = playerName;
    },
    //金额数值
    _setGoldValue : function (value) {
        var str;
        if(!value) {
            str = G_CHINESE.noRecord;
            this.node_goldBG.active = false;
        } else {
            str = value + '';
            if (str.length >= 17) {
                str = G_CHINESE.upperLimit;
            } else {
                str = G_TOOL.changeMoney(value);
            }
            this.node_goldBG.active = true;
            // this._resetBackground(str);   保留
        }
        this.label_goldValue.string = str;
    },

    //改变背景长度
    _resetBackground : function (str) {
        if(!this._widthRate) this._widthRate = this.node_goldBG.width/6;
        if(!this.node_goldBG._firstW) this.node_goldBG._firstW = this.node_goldBG.width;

        var wordLen=0, name = str, curStr, reg = /^[\u4E00-\u9FA5]+$/;;
        for(var i =0; i < name.length; i ++){
            curStr = name[i];
            if(reg.test(curStr)) {
                wordLen += 2;
            } else {
                wordLen += 1;
            }
        }

        var targetW = wordLen * this._widthRate;
        this.node_goldBG.width = Math.max(this.node_goldBG._firstW, targetW);
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
