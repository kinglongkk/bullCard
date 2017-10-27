//整个游戏开始前的处理

//对应G_TYPE.gameModule
var gameModule = cc.Enum({
    platform : 0,
    bull100 : 1,
    grab : 2,
    classic : 3
})

cc.Class({
    extends: cc.Component,

    properties: {
        _gameState : null,

        node_gameScene : {
            default : null,
            type : cc.Node,
            displayName : '整个游戏的父层'
        },
        gameType : {
            default : 0,
            type: gameModule,
            displayName : '游戏模式'
        }
    },

    // use this for initialization
    onLoad: function () {
        //this.node_gameScene.active = false;
        //临时的处理方案，切换不同的场景
        //if(window.G_InRoomType && window.G_InRoomType != this.gameType){
        //    switch (window.G_InRoomType){
        //        case G_TYPE.gameModule.bull100:
        //            cc.director.preloadScene('Poker_Bull100', function () {
        //                cc.director.loadScene('Poker_Bull100');
        //            });
        //            break
        //        case G_TYPE.gameModule.grab:
        //            cc.director.preloadScene('Poker_Grab', function () {
        //                cc.director.loadScene('Poker_Grab');
        //            });
        //            break
        //        case G_TYPE.gameModule.classic:
        //            cc.director.preloadScene('Poker_classic', function () {
        //                cc.director.loadScene('Poker_classic');
        //            });
        //            break
        //        case G_TYPE.gameModule.platform:
        //            cc.director.preloadScene('Platform', function () {
        //                cc.director.loadScene('Platform');
        //            });
        //            break
        //        default:
        //            break
        //    }
        //    return
        //}

        //一些动画效果在脏矩形下发生错误
        //if (cc._renderType === cc.game.RENDER_TYPE_CANVAS) {
        //    cc.renderer.enableDirtyRegion(false);
        //    cc.view.enableRetina(true);
        //}

        //this._reloadTable();
    },

    _reloadTable : function () {
        var tables = [];
        switch (this.gameType){
            case gameModule.platform:
                //tables.push(G_RES_URL.dict_tablesName.platformConfig);
                break;
            case gameModule.classic:
                //tables.push(G_RES_URL.dict_tablesName.classicConfig);
                break;
            case gameModule.grab:
                tables.push(G_RES_URL.dict_tablesName.grabConfig);
                break;
            case gameModule.bull100:
                //tables.push(G_RES_URL.dict_tablesName.bull100Config);
                break;
            default:
                break;
        }
        tables.push(G_RES_URL.dict_tablesName.commonConfig);
        tables.push(G_RES_URL.dict_tablesName.chinese);
        tables.push(G_RES_URL.dict_tablesName.audioName);
        tables.push(G_RES_URL.dict_tablesName.grabConfig);
        GG.tableMgr.reloadTables(tables, this._addConfigCallFunc.bind(this));
    },

    //加载配置文件结束
    _addConfigCallFunc : function () {
        this._gameState = G_TYPE.webGameState.running;

        //经典看牌的主要配置
        switch (this.gameType){
            case gameModule.platform:
                //var table = GG.tableMgr.getTable(G_RES_URL.dict_tablesName.platformConfig);
                //if(table) {
                //
                //}
                break;
            case gameModule.classic:
                var table = GG.tableMgr.getTable(G_RES_URL.dict_tablesName.classicConfig);
                if(table) {
                    var dataObj = table.getFirstData();
                    this._addConfig(G_Config_classic, dataObj);
                }
                break;
            case gameModule.grab:
                var table = GG.tableMgr.getTable(G_RES_URL.dict_tablesName.grabConfig);
                if(table) {
                    var dataObj = table.getFirstData();
                    this._addConfig(G_Config_grab, dataObj);
                }
                break;
            case gameModule.bull100:
                var table = GG.tableMgr.getTable(G_RES_URL.dict_tablesName.bull100Config);
                if(table) {
                    var dataObj = table.getFirstData();
                    this._addConfig(G_Config_grab, dataObj);
                }
                break;
            default:
                break;
        }

        var table = GG.tableMgr.getTable(G_RES_URL.dict_tablesName.commonConfig);
        if(table) {
            var dataObj = table.getFirstData();
            this._addConfig(G_Config_common, dataObj);
        }
        this.node_gameScene.active = true;
    },

    //增加配置信息
    _addConfig : function (targetObject, addObject) {
        if(addObject){
            for(var attrName in addObject){
                targetObject[attrName] = addObject[attrName];
            }
        }
    },

    getGameState : function () {
        return this._gameState
    },

    getGameType : function () {
        return this.gameType
    },

    onDestroy : function () {
        cc.game.off(cc.game.EVENT_HIDE, this._pauseWebGame, this);
        cc.game.off(cc.game.EVENT_SHOW, this._resumeWebGame, this);
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
