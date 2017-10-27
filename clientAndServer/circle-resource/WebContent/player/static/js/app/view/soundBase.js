//MK. 游戏的所有声音都在这边定义
define([], function () {
    var soundCount = 20;//设定音效池,最多只能有这么多个音效可被播放
    var sBase = new Object();
    sBase.Sound = function (index, src) {
        this.index = index;
        this.src = src;
        return this;
    };
    var vol = 1, volBg = 1, muted = true, mutedBg = true, baseSrc, suffix = ".ogg";
    var isIosClient = !!navigator.userAgent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);
    var background = new Audio(), backgroundSrc = null;
    var soundAudios = new Array();
    var soundSrcs = new Array();
    if (!background.canPlayType("audio/ogg") && background.canPlayType("audio/mp3")) {//苹果设备不支持ogg格式
        suffix = ".mp3";
    }
    var inited = false;
    var toPlayBg = false;//加载完背景音乐或者初始化后是否即时播放
    var initSound = function (bgSrc) {
        if (inited)return;
        inited = true;//避免多次初始化
        var src = bgSrc;
        if (!src)src = "COMMON_CLICK";
        src = baseSrc + src + suffix;
        background.src = src;
        background.onloadeddata = function () {
            if (!toPlayBg)background.pause();
            background.onloadeddata = undefined;
            background.loop = true;
        }
        background.play();
        for (var i = 0; i < soundCount; i++) {//最多支持4个音效同时播放
            var audio = new Audio();
            // audio.onloadeddata = function () {
            //     audio.pause();
            //     audio.onloadeddata = undefined;
            // }
            if(soundSrcs[i]){
                audio.src = baseSrc + soundSrcs[i]+suffix;
            }else{
                audio.src = src;
            }
            audio.play();
            soundAudios.push(audio);
        }
        setTimeout(function () {
            for (var i = 0; i < soundAudios.length; i++) {
                soundAudios[i].pause();
            }
        }, 1);
    }
    //src为声音文件根目录路径,bgSrc表示背景音乐相对路径设置后在声音开启的时候会播放,playBg表示是否马上播放
    sBase.init = function (src, bgSrc, playBg) {
        baseSrc = src;
        backgroundSrc = bgSrc;
        toPlayBg = toPlayBg || playBg;
        initSound(bgSrc);
    }

    var playBg = function (src) {//播放背景音乐最好是通过init设定背景音乐路径
        if (!src)src = backgroundSrc;
        if (!background || !inited) {
            return;
        } else {
            // background.pause();
            background.src = baseSrc + src + suffix;
        }
        background.play();
    }
    //获取到设置后,设定音乐音效是否播放
    sBase.set = function (bgPlay, soundPlay) {
        if (muted == bgPlay) {
            if (muted) {
                toPlayBg = true;
                sBase.unMuteBg();
            } else {
                sBase.muteBg();
            }
        }
        muted = !soundPlay;
    }
    sBase.playBackgroup = function (src) {//播放背景音乐
        if (src == backgroundSrc) {
        } else {
            backgroundSrc = src;
            if (mutedBg) return;
            playBg();
        }
    };
    sBase.insertBackgroup = function (src, endFun) {//插入背景音乐
        if (mutedBg || !background) return;
        background.pause();
        background.loop = false;
        playBg(src);
        background.onended = function () {
            background.loop = true;
            playBg();
            if (endFun)endFun();
        }
    };
    sBase.pauseBackgroup = function () {//暂停背景音乐
        if (mutedBg) return;
        if (background)background.pause();
    };
    sBase.resumeBackgroup = function () {//播放背景音乐
        if (mutedBg) return;
        if (background)background.play();
    };
    sBase.muteBg = function () {
        mutedBg = true;
        if (background)background.pause();
    }
    sBase.unMuteBg = function () {
        mutedBg = false;
        if (!backgroundSrc || !inited) return;
        if (background)
            background.play();
    }
    sBase.mute = function () {
        muted = true;
    }
    sBase.unMute = function () {
        muted = false;
    }
    sBase.plays = function (sounds, endFun, delay, interval) {//播放多个音效
        if (!inited || muted)return;
        for (var i = 0; i < sounds.length; i++) {
            var sound = sounds[i];
            sBase.play(sound, endFun, delay + i * interval);
        }
    }
    sBase.initSound = function (sound) {//播放音效
        if (!inited || muted)return;
        try {
            var audio = soundAudios[sound.index];
            if (soundSrcs[sound.index] != sound.src) {
                var src = sound.src;
                soundSrcs[sound.index] = src;
                src = baseSrc + src + suffix;
                if (!audio) {
                    return;//池里没有音效可以播放，忽略该次播放
                } else {
                    audio.src = src;
                }
            }
            return audio;
        } catch (e) {
            console.debug("初始化音效异常:" + e);
        }
    }
    //每个场景在进入游戏时都要更新初始化音效资源
    sBase.initSounds = function (sounds) {
        try {
            for (var i in sounds) {
                var sound = sounds[i];
                if (sound instanceof sBase.Sound) {
                    var src = sound.src;
                    if (soundSrcs[sound.index] == src)continue;
                    soundSrcs[sound.index] = src;
                    if(baseSrc){
                        src = baseSrc + src + suffix;
                    }
                    var audio = soundAudios[sound.index];
                    if (audio) {
                        audio.src = src;
                    }
                } else if (sound instanceof Array) {
                    sBase.initSounds(sound);
                }
            }
        } catch (e) {
            console.debug("初始化音效异常:" + e);
        }
    }
    sBase.play = function (sound, endFun, delay) {//播放音效
        if (!inited || muted)return;
        try {
            var audio = sBase.initSound(sound);
            if (delay) {
                setTimeout(function () {
                    sBase.play(sound, endFun);
                }, delay);
                return;
            }
            if (endFun)audio.onended = function () {
                endFun();
            }
            // console.debug("sound.src:"+audio.currentTime);
            if (audio.readyState >= 1) {//音频可播放
                try {
                    audio.currentTime = 0;
                    audio.play();
                } catch (e) {
                    console.error("播放音效异常:" + e);
                }
            }
        } catch (e) {
            console.debug("播放音效异常:" + e);
        }
    }
    return sBase;
})
