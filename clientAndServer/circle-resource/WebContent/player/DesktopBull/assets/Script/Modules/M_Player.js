//玩家对象，存在于整个游戏流程中

var player = cc.Class({
    _playerID : null,
    _playerToken : null,
    _playerName : null,                                                                                                     //姓名
    _playerUID : null,                                                                                                      //
    _playerGold : null,                                                                                                     //玩家的金币

    _dict_systemSet : null,                                                                                                 //系统配置信息
    _dict_baseInfo : null,                                                                                                  //基础信息列表

    _inHomeData : null,                                                                                                     //临时记录当前的进房间信息
    _outHomeData : null,                                                                                                    //临时记录当前的出房间信息
    _reloadSceneInfo : null,                                                                                                //预加载场景的时候传递的参数
    ctor : function () {},

    setPlayerToken : function (tokenData) {
        this._playerToken = tokenData.token;
        this._playerUID = tokenData.uid;
    },
    setPlayerID : function (id) {
        this._playerID = id;
    },
    //名字
    setPlayerName : function (name) {
        this._playerName = name;
    },
    //系统设置
    setSystemSet : function (curDict) {
        this._dict_systemSet = curDict;
    },
    //玩家基础信息: nickname,avatarUrl,id
    setBaseInfo : function (rectData) {
        if(!rectData) return;
        if(!this._dict_baseInfo) this._dict_baseInfo = {};
        var data = rectData.data[0];
        if(data){
            for(var attrName in data){
                //头像avatarUrl；金币coin；nickname昵称
                this._dict_baseInfo[attrName] = data[attrName];
                //if(attrName == 'id') this.setPlayerID(data[attrName]);
                switch (attrName){
                    case 'id':
                        this.setPlayerID(data[attrName]);
                        break
                    case 'coin':
                        var obj = G_OBJ.data_nbSelf();
                        obj.balance = data[attrName];
                        this.setPlayerGold(obj);
                        break
                    default:
                        break
                }
            }
        }
    },
    setInHomeData : function (dataObj) {
        this._inHomeData = dataObj;
    },
    setOutHomeData : function (dataObj) {
        this._outHomeData = dataObj;
    },
    setReloadSceneInfo : function (info) {
        this._reloadSceneInfo = info;
    },
    setPlayerGold : function (dataObj) {
        this._playerGold = parseInt(dataObj.balance);
        //分发金币变化的信息
        GG.Listener.dispatchEventEX(G_TYPE.globalListener.playerGold, dataObj);
    },

    //=============================================

    getPlayerGold : function () {
        return this._playerGold
    },
    getPlayerID : function () {
        return this._playerID
    },
    getPlayerToken : function () {
        return this._playerToken;
    },
    //玩家姓名
    getPlayerName : function () {
        return this._dict_baseInfo['nickname'];
    },
    //获取系统配置信息
    getSystemSet : function () {
        return this._dict_systemSet
    },
    //获取基础信息
    getBaseInfo : function () {
        return this._dict_baseInfo
    },
    //获取玩家头像
    getHeadImgUrl : function () {
        return this._dict_baseInfo['avatarUrl']
    },
    //获取进房间信息
    getInHomeData : function () {
        var homeData = this._inHomeData;
        this._inHomeData = null;
        return homeData
    },
    //获取出房间信息
    getOutHomeData : function () {
        var homeData = this._outHomeData;
        this._outHomeData = null;
        return homeData
    },
    //获取UID
    getUID : function () {
        return this._playerUID
    },
    //获取游戏ID，第几款游戏
    getGameID : function () {
        return 1
    },
    //获取预加载场景传递的信息
    getReloadSceneInfo : function () {
        var info = this._reloadSceneInfo;
        this._reloadSceneInfo = null;
        return info
    },

    //====================================

    //退出游戏时候的处理（比如记录当前的音乐音效设置信息）
    whenExit : function () {

    },
})


module.exports = player;