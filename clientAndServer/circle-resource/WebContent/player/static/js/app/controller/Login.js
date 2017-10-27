define(['app/controller/gameMain', "app/view/soundBase",'view/viewBase'
        ], function (gm, sBase,vBase) {

    return Class.extend({

        /**
         * 构造方法
         */
        init : function () {
            this.loginToSet();
            this.keepLogin();
        },


        /**
         * 获取登录游戏的token
         */
        loginToken : function () {
            $.ajax({
                url : root + "/user/token.html",
                type: "post",
                dataType: "json",
                data : {t:new Date()},
                success: function (data) {
                    console.info("获取游戏登录token:"+data.token);
                    gm.sendToServer("NbLogin",{
                        token : data.token,
                        userId : data.uid
                    });
                    gm.setLogined(true);//TODO 应该是登录返回成功的消息后才调用登录成功
                    window.userId = data.uid;
                },
                error: function (e) {

                }
            });
        },

        /**
         * 获取登录游戏的声音设置
         */
        loginToSet : function () {
            $.ajax({
                url : root + "/user/voiceSet.html",
                type: "post",
                dataType: "json",
                data : {t:new Date()},
                success: function (data) {
                    //获取数据库储存的声音设置
                    if (data.length != 0) {
                        var music = data[0].music;
                        var sound = data[0].sound;
                        sBase.set(music, sound);
                    }else {//玩家第一次开启游戏声音设置默认为开true
                        sBase.set(true, true);
                    }
                },
                error: function (e) {

                }
            });
        },

        /**
         * 保持用户登录状态
         */
        keepLogin: function () {
            var _this = this;
            setInterval(function () {
                _this.timedRefresh();
            },60000);
        },

        /**
         * 定时刷新
         */
        timedRefresh:function () {
            $.ajax({
                url : root + "/user/alive.html",
                type: "post",
                dataType: "json",
                data : {t:new Date()},
                success: function () {
                    console.log("success");
                },
                error: function (e) {
                    console.log("error");
                }
            });
        }
    });
});