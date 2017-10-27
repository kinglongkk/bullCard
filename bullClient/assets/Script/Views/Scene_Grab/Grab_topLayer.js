//游戏最上面的层容器

cc.Class({
    extends: require('AutoDealing'),

    properties: {
        node_startImg : cc.Node,
    },

    // use this for initialization
    onLoad: function () {
        this._registerStart();
    },
    _registerStart : function () {
        if(this.node_startImg) {
            this.node_startImg.active = true;
            this.registerButton(this.node_startImg, this.OnClick_startImg, this);
        }
        if(G_Config_common.isLocal){

        }else{
            this.node_startImg.active = false;
        }
    },
    OnClick_startImg : function (event) {
        if(G_Config_common.isLocal){
            this.node_startImg.active = false;
            GG.grabMgr.forceStart();
        }else{
            GG.socketMgr.connectSocket(this._connectEnd.bind(this));
        }
    },

    _connectEnd : function (isSuccess) {
        if(isSuccess){
            this.node_startImg.active = false;
            this._login();
            //GG.grabMgr.firstStart();
        }
    },

    _login : function () {
        var netData = {
            userId : GG.getPlayer().getPlayerID()
        }
        GG.socketMgr.SendMsg(NetType.s_login, netData);
    },

    //_enterHome : function (data) {
    //    console.log('_enterHome success------')
    //    console.log(data)
    //    GG.grabMgr.requestEnterHome();
    //},

    getTopEffect : function () {
        return this.getComponent('Grab_topEffect');
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
