//玩家系统设置
var setType = {
    typeMusic : 1,
    typeSound : 2,
}

cc.Class({
    extends: require('BaseDialog'),

    properties: {
        _musicIsOpen : null,                                                                                                 //音乐是否开启
        _soundIsOpen : null,                                                                                                 //音效是否开启
        _isOpenFirst : null,                                                                                                 //是否第一次打开
        _dict_lastSet : null,                                                                                                //上次的设定，判定是否有改变配置

        node_changeBtn : {
            default : null,
            type : cc.Node,
            displayName : '切换账号'
        },
        node_musicSwitch : {
            default : null,
            type : cc.Node,
            displayName : '音乐开关'
        },
        node_soundSwitch : {
            default : null,
            type : cc.Node,
            displayName : '音效开关'
        },
        node_musicIcon: {
            default : null,
            type : cc.Node,
            displayName : '音乐左边图标'
        },
        node_soundIcon: {
            default : null,
            type : cc.Node,
            displayName : '音效左边图标'
        },
        label_name : {
            default : null,
            type : cc.Label,
            displayName : '玩家名字'
        },
    },

    // use this for initialization
    onLoad: function () {
        this._super();
        this._registerEvent();
        this._isOpenFirst = true;
    },
    _registerEvent : function () {
        this.registerButton(this.node_changeBtn, this._onClick_change, this);
        this.registerButton(this.node_musicSwitch, this.onClick_open, this, setType.typeMusic, true);
        this.registerButton(this.node_soundSwitch, this.onClick_open, this, setType.typeSound, true);
    },
    hideLayer : function(){
        this._super();
        this._dict_lastSet = null;
    },

    _onClick_close : function () {
        if(G_Config_common.isLocal) {
            this.hideLayer();
            return
        }

        if(this._dict_lastSet){
            if(this._dict_lastSet['music']!=this._musicIsOpen||this._dict_lastSet['sound']!=this._soundIsOpen) {
                //配置发生改变,保存设置
                var musiceStr = this._musicIsOpen ? 'Set_open' : 'Set_close';
                var soundStr = this._soundIsOpen ? 'Set_open' : 'Set_close';
                var sendStr = "musicClass="+musiceStr+'&soundClass='+soundStr+'&gameType=DOU_NIU';
                var self = this;
                GG.httpMgr.sendHttpRequest(G_DIALOG_URL.saveSystemSetUrl, sendStr, function (data) {
                    self.hideLayer();
                }.bind(this));
            }else this.hideLayer();
        }else this.hideLayer();
    },

    setData : function () {
        var self = this;
        if(G_Config_common.isLocal){
            var data = {'music':true,'sound':true};
            this._dict_lastSet = data;
            if(GG.audioMgr.getAudioConfig()) data = GG.audioMgr.getAudioConfig();
            else GG.audioMgr.setAudioConfig({'music':true,'sound':true});
            this.defaultSetSound(data.music, data.sound);
            this._setName();
            self.showLayer();
        }else{
            //获取玩家系统设置
            //if(this._isOpenFirst){
            //    GG.httpMgr.sendHttpLogin(G_DIALOG_URL.getSystemSetUrl, 'gameType=DOU_NIU', function (data) {
            //        var config;
            //        if(data.code == 0){
            //            config = {'music':true,'sound':true};
            //        } else {
            //            config = data.data.result[0];
            //        }
            //        //如果音乐已经播放，则不需要重复播放
            //        if(config.music && GG.audioMgr.getIsPlayMusic()) GG.audioMgr.saveSoundConfig(config.sound);
            //        else GG.audioMgr.setAudioConfig(config);
            //
            //        var data = GG.audioMgr.getAudioConfig();
            //        self.defaultSetSound(data.music, data.sound);
            //        self._setName();
            //        self._isOpenFirst = false;
            //        self.showLayer();
            //    }.bind(this));
            //}else {
            //    var data = GG.audioMgr.getAudioConfig();
            //    this.defaultSetSound(data.music, data.sound);
            //    this._setName();
            //    self.showLayer();
            //}
            var data = GG.audioMgr.getAudioConfig();
            if(data){
                this._dict_lastSet = {};
                for(var attr in data){
                    this._dict_lastSet[attr] = data[attr];
                }
                this.defaultSetSound(data.music, data.sound);
                this._setName();
                self.showLayer();
            }
        }
    },

    //玩家点击了开关
    onClick_open : function (event, type) {
        switch (type) {
            case setType.typeMusic :
                //音乐
                this._setMusicOpen();
                GG.audioMgr.saveMusicConfig(this._musicIsOpen);
                break;
            case setType.typeSound :
                //音效
                this._setSoundOpen();
                GG.audioMgr.saveSoundConfig(this._soundIsOpen);
                break;
        }
    },
    //设置音乐开关
    _setMusicOpen : function () {
        this._musicIsOpen = !this._musicIsOpen;
        //表现
        var pointImg, openImg, lightIcon;
        openImg = this.node_musicSwitch.children[0];
        pointImg = this.node_musicSwitch.children[1];
        lightIcon = this.node_musicIcon.children[0];

        openImg.active = this._musicIsOpen;
        if(this._musicIsOpen){
            pointImg.x = this.node_musicSwitch.width/2 - pointImg.width/2;
        }else{
            pointImg.x = -this.node_musicSwitch.width/2 + pointImg.width/2;
        }
        lightIcon.active = this._musicIsOpen;
    },
    //设置音效开关
    _setSoundOpen : function () {
        this._soundIsOpen = !this._soundIsOpen;
        //表现
        var pointImg, openImg, lightIcon;
        openImg = this.node_soundSwitch.children[0];
        pointImg = this.node_soundSwitch.children[1];
        lightIcon = this.node_soundIcon.children[0];

        openImg.active = this._soundIsOpen;
        if(this._soundIsOpen){
            pointImg.x = this.node_soundSwitch.width/2 - pointImg.width/2;
        }else{
            pointImg.x = -this.node_soundSwitch.width/2 + pointImg.width/2;
        }
        lightIcon.active = this._soundIsOpen;
    },
    
    //玩家默认系统设置(第一次进入游戏设置), isOpenMusic=true则开启音乐，音效等同
    defaultSetSound : function (isOpenMusic, isOpenSound) {
        this._musicIsOpen = !isOpenMusic;
        this._soundIsOpen = !isOpenSound;
        this._setMusicOpen();
        this._setSoundOpen();
    },

    //设置名字
    _setName : function (name) {
        if(G_Config_common.isLocal)  name = '123456';
        else name = GG.getPlayer().getPlayerName();
        if(!name) name = ''
        else name = G_TOOL.getNameLimit(name, 16);
        this.label_name.string = name;
    },

    _onClick_change : function (event) {
        GG.tipsMgr.showConfirmTip_TWO(G_CHINESE.exitText2, function () {
            //确认退出
            GG.exitGame();
        });

        //var sendData = "gameId=1&dateClass=0&pageSize=20&pageNow=0";
        //GG.httpMgr.sendHttpLogin("game/statistics/user/records.html", sendData, function (data) {
        //    console.log('get login======');
        //    console.log(data);
        //}.bind(this));
    }
    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
