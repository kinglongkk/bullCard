define(['ws/NetServer','app/controller/netDefaultHandle','view/viewBase'], function (NetServer, handle,vBase) {

    var isConnecting = true;
    var isConnected = false;
    var logined = false;

    //初始化NetServer
    var host = window.location.host;
    if (host.indexOf(":") >= 0)host = host.substring(0, host.indexOf(":"));
    if(host == "")host="localhost";
    var server = new NetServer("ws://"+host+":" +window.wsPort+ "/ws", handle, false);
    window.netServer = server;
    var urlOnConnected = null;
    var loadingTimer = null;
    var heartTimer = null;//心跳连接每4秒发一次
    var heartLastTime = 0;

    server.onOpen = function () {
        console.log("连接开启");
        isConnected = true;
        isConnecting = false;
        if(loadingTimer){
            vBase.clearInterval(loadingTimer);
            vBase.tip.hide();
            if(urlOnConnected){
                window.location.href = urlOnConnected;
                urlOnConnected = null;
            }
            loadingTimer = null;
        }
        heartLastTime = new Date().getTime();
        heartTimer = vBase.setInterval(function () {
            if(!logined) return;//登录后才需要心跳
            var now = new Date().getTime();
            if(now - heartLastTime >= 3500){//心跳连接至少每3.5秒一次
                server.sendHeart();
                heartLastTime = new Date().getTime();
            }
        }, 500, null);
        window.login.loginToken();//连接开启之后再去获取token
    }

    server.onClose = function () {
        isConnected = false;
        logined = false;
        console.log("连接关闭"+isConnecting);
        if(!isConnecting){//连接中断开
            if(heartTimer){//心跳定时器关闭
                vBase.clearInterval(heartTimer);
                heartTimer = 0;
            }
            var url = window.location.href;
            var index = url.indexOf("/room/");
            if(index == -1){
                isConnecting = false;
                return;
            }
            //如果在房间中,则回退到房间列表并显示重连按钮,由于刷新进房间没办法用backUrl,暂时用这种方法回退
            url = url.substring(0, index);
            urlOnConnected = url;
            if(gm.clearGame)gm.clearGame();
            var loadingMsg = "网络连接中断，正在尝试重新连接";
            var loadingText = [".&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;","..&nbsp;&nbsp;&nbsp;&nbsp;","...&nbsp;&nbsp;&nbsp;",
                "....&nbsp;&nbsp;",".....&nbsp;","......"];
            var loadingCount = 0;
            vBase.tip.show(loadingMsg+loadingText[loadingCount++], "", true);
            loadingTimer = vBase.setInterval(function () {
                if(loadingCount==-1)return;//弹出确认提示框,暂停重连
                vBase.tip.text(loadingMsg+loadingText[loadingCount++ % 6]);
                if(loadingCount % 120 == 0){
                    vBase.msgDialog.show("网络异常，确认重连！", "确定", null, function () {
                        loadingCount = 0;
                    });
                }else if(loadingCount%6 == 0){//每3秒进行一次重连
                    gm.reConnect();
                }
            }, 500);
        }else if(!loadingTimer){
            isConnecting = false;
        }
    }
    var CacheBean = function (type,data) {
        this.type = type;
        this.data = data;
        return this;
    }
    var cacheBeans = new Array();//未登录时只能发送登录消息,其余消息会被缓存起来等登录成功了再发送
    var gm = {

        /**
         * 重新连接
         */
        reConnect : function () {
            if (!isConnected) {
                console.info("通信连接中...");
                isConnecting = true;
                server.connect();
            }
        },

        /**
         * 获取服务器时间
         */
        getServerTime : function(){
            //获取当前服务器时间
            return server.getServerTime();
        },

        /**
         * 发送消息到服务器
         * @param type 类型
         * @param data 参数
         */
        sendToServer : function(type,data){
            if(!isConnected && !isConnecting){
                this._reConnectDialog();
                return false;
            }
            if(type != "NbLogin" && !logined){//未登录时只能发送登录消息,其余消息会被缓存起来等登录成功了再发送
                cacheBeans.push(new CacheBean(type, data));
                return false;
            }
            if (NetBean[type] == undefined) {
                this._reConnectDialog();
                return false;
            }
            try {
                server.send(type, data);
                heartLastTime = new Date().getTime();
                return true;
            } catch (e){
                console.log(e);
                this._reConnectDialog();
                return false;
            }
        },

        sendSoundSetting : function (music,sound) {
            //游戏登陆获取游戏声音设置开关true false

        },
        isLogined : function () {

        },
        setLogined : function (val) {
            logined = val;
            if(val && cacheBeans.length>0){
                for (var i = 0; i < cacheBeans.length; i++) {
                    var bean = cacheBeans[i];
                    this.sendToServer(bean.type, bean.data);
                }
                cacheBeans = [];
            }
        },
        //重连接弹窗
        _reConnectDialog : function () {
            if (!isConnected && isConnecting) {
                return;
            }
            isConnecting = true;
            var _this = this;
            window.setTimeout(function () {
                vBase.msgDialog.show("连接游戏服务器失败。", "重试", "关闭", function () {
                    _this.reConnect();
                });
            },100);
        },
        clearGame : null //为房间游戏注入的, 当页面后退或者断线回退需要清理游戏
    };


    return gm;
});