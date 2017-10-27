//音效管理器
// music 是音乐； sound是音效
var audioMgr = cc.Class({
    ctor : function () {
        this._musicVolume = 1;                                                    //音乐音量大小
        this._soundVolume = 1;                                                    //音效音量大小
        //this._dict_audioConfig = {'music':true,'sound':true};                  //音乐音效的配置
        this._dict_audioConfig = null;                                             //音乐音效的配置
        this._bgMusicID = -1;                                                      //背景音乐
        this._bgMusicName = null;                                                 //背景音乐
        this._isReloadPlatform = false;                                            //是否已经加载大厅音效
        this._isReloadGame = false;                                                //是否已经加载游戏房间音效
        this._reloadPlatformURL = '';                                              //使用音频格式的加载路劲
        this._reloadGameRoomURL = '';                                              //使用音频格式的加载路劲
        this._dict_audioUrl = {};                                                   //存放所有音效的名字对应路径
        //环境检测
        this._environmentConfirm();
    },

    //播放音乐
    playMusic : function (audioID) {
        if(!audioID) audioID = 19;
        var musicName = this.getAudioName(audioID);
        if(this._musicVolume <= 0){
            //暂停音乐
            if(this._bgMusicName) {
                cc.audioEngine.stop(this._bgMusicID);
                this._bgMusicName = null;
            }
        }else{
            //开启音乐
            if(musicName){
                if(this._bgMusicName !== musicName) {
                    //换音乐
                    this._bgMusicName = musicName;
                    var url = this.getAudio(this._bgMusicName);
                    //console.log('播放背景音乐'+url)
                    this._bgMusicID = cc.audioEngine.play(url, true);
                    //this._bgMusicID = cc.audioEngine.playMusic(url, true);
                }
            }else{
                //if(this._bgMusicName) {
                //    //播放上次的音乐
                //    cc.audioEngine.resume(this._bgMusicID);
                //}
            }
        }
    },

    //播放音效
    playSound : function (audioID) {
        if(this._soundVolume <= 0) return;
        var url = this.getAudio(this.getAudioName(audioID));
        //console.log('播放音效'+url);
        return cc.audioEngine.play(url, false);
        //return cc.audioEngine.playEffect(url, false);
    },

    //调整音乐大小
    setMusicVolume : function (num) {
        this._musicVolume = num;
        this.playMusic();
    },

    //调整音效大小
    setSoundVolume : function (num) {
        this._soundVolume = num;
    },

    //停止某个音效
    stopAudio : function (audioID) {
        cc.audioEngine.stop(audioID);
    },

    //停止音乐
    stopMusic : function () {
        if(this._bgMusicName) {
            cc.audioEngine.stop(this._bgMusicID);
            this._bgMusicName = null;
        }
    },

    //获取音频文件
    getAudio : function (audioName) {
        if(!this._dict_audioUrl[audioName]) return cc.url.raw(G_TOOL.formatStr(G_RES_URL.audioUrl, audioName));
        return this._dict_audioUrl[audioName]
    },

    //获取音乐是否正在播放
    getIsPlayMusic : function () {
        if(this._bgMusicName && this._musicVolume > 0) return true
        return false
    },

    //=====================================
    //获取音效配置
    getAudioConfig : function () {
        return this._dict_audioConfig
    },
    //设置音效配置
    setAudioConfig : function (dict) {
        this.setMusicVolume(dict.music ? 1 : 0);
        this.setSoundVolume(dict.sound ? 1 : 0);
        this._dict_audioConfig = dict;
    },
    //保存音乐配置
    saveMusicConfig : function (isPlay) {
        this.setMusicVolume(isPlay ? 1 : 0);
        this._dict_audioConfig.music = isPlay;
    },
    //保存音效配置
    saveSoundConfig : function (isPlay) {
        this.setSoundVolume(isPlay ? 1 : 0);
        this._dict_audioConfig.sound = isPlay;
    },
    //=====================================
    //获取某个音频的名字
    getAudioName : function (audioID) {
        var tableObj = GG.tableMgr.getTable(G_RES_URL.dict_tablesName.audioName);
        var data = tableObj.getDataByID(audioID);
        if(data) return data.audioName;
        return null
    },

    //预加载音频资源
    //reloadAudio : function (callBack) {
    //    var self = this;
    //    cc.loader.loadResDir('Sounds', function (err, audioList) {
    //        var isOk = false;
    //        if(!err){
    //            var content = audioList[0];
    //            var index = content.lastIndexOf('/');
    //            self._basePlatformUrl = content.substring(0, index+1)+self._audioFormatStr;
    //            isOk = true;
    //        }else {
    //            //加载音效发生问题
    //        }
    //        if(callBack){
    //            callBack(isOk);
    //            callBack = null;
    //        }
    //    })
    //},

    //加载大厅音效
    reloadPlatformAudio : function (callBack) {
        var self = this;
        cc.loader.loadResDir(this._reloadPlatformURL, function (err, audioList) {
            var isOk = false;
            if(!err){
                var audioUrl, urlEnd,audioName;
                for(var i = 0; i < audioList.length; i ++){
                    audioUrl = audioList[i]
                    urlEnd = audioUrl.substring(audioUrl.lastIndexOf('/')+1, audioUrl.length);
                    audioName = urlEnd.split('.')[0];
                    self._dict_audioUrl[audioName] = audioUrl;
                }
                self._isReloadPlatform = true;
            }else {
                //加载音效发生问题
            }
            if(callBack){
                callBack(isOk);
                callBack = null;
            }
        })
    },

    //加载游戏房间内的音效
    reloadGameAudio : function (callBack) {
        var self = this;
        cc.loader.loadResDir(this._reloadGameRoomURL, function (err, audioList) {
            if(!err){
                //没有加载错误
                var audioUrl, urlEnd,audioName;
                for(var i = 0; i < audioList.length; i ++){
                    audioUrl = audioList[i]
                    urlEnd = audioUrl.substring(audioUrl.lastIndexOf('/')+1, audioUrl.length);
                    audioName = urlEnd.split('.')[0];
                    self._dict_audioUrl[audioName] = audioUrl;
                }
                self._isReloadGame = true;
            }
            if(callBack){
                callBack();
                callBack = null;
            }
        })
    },
    getIsReloadPlatform : function () {
        return this._isReloadPlatform
    },
    getIsReloadGame : function () {
        return this._isReloadGame
    },

    //平台鉴定
    _environmentConfirm : function () {
        //switch (cc.sys.os){
        //    case cc.sys.OS_ANDROID:
        //        //android手机用ogg
        //        //this._reloadPlatformURL = 'Sounds/ogg_platform';
        //        //this._reloadGameRoomURL = 'Sounds/ogg_gameRoom';
        //        this._reloadPlatformURL = 'Sounds/mp3_platform';
        //        this._reloadGameRoomURL = 'Sounds/mp3_gameRoom';
        //        break
        //    case cc.sys.OS_IOS:
        //        //ios手机用mp3
        //        this._reloadPlatformURL = 'Sounds/mp3_platform';
        //        this._reloadGameRoomURL = 'Sounds/mp3_gameRoom';
        //        break
        //    default:
        //        break
        //}
        if(cc.sys.isNative){
            this._reloadPlatformURL = 'Sounds/mp3_platform';
            this._reloadGameRoomURL = 'Sounds/mp3_gameRoom';
        }else{
            var audio = new Audio();
            if(audio.canPlayType("audio/mp3")){
                this._reloadPlatformURL = 'Sounds/mp3_platform';
                this._reloadGameRoomURL = 'Sounds/mp3_gameRoom';
            }else if(audio.canPlayType("audio/ogg")){
                this._reloadPlatformURL = 'Sounds/ogg_platform';
                this._reloadGameRoomURL = 'Sounds/ogg_gameRoom';
            }
        }
    },

    // releaseAudio : function () {
    //     cc.loader.release(cc.url.raw("resources/sounds/" + url));
    // }
})

module.exports = audioMgr;