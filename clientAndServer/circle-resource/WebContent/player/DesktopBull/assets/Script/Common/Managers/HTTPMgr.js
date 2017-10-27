//http请求管理

var http = cc.Class({
    _list_request : null,                                                                                               //请求列表
    _isRequesting : null,                                                                                               //正在请求中
    _isAddHeart : null,                                                                                                 //是否有心跳
    _failTime : null,                                                                                                   //多长时间请求失败

    _time_timeOut : null,                                                                                               //超时时间
    _timeOutID : null,                                                                                                  //超时预计的ID
    _dict_longWait : null,                                                                                              //长时间等待的请求
    _sessionID : null,
    properties: {

    },

    // use this for initialization
    ctor: function () {
        this._list_request = [];
        this._isRequesting = false;
        this._isAddHeart = false;
        this._nativeHref = G_Config_common.nativeHref;
    },

    //發送請求
    sendHttpRequest : function (url, sendData, callFunc, xhrType) {
        this.addHeart();
        this._recordSend(url, sendData, callFunc, xhrType)
    },

    //记录发送的信息
    _recordSend : function (url, sendData, callFunc, xhrType) {
        if(!this._dict_sendRequest) this._dict_sendRequest = {};
        var sendObj = G_OBJ.data_httpSend();
        sendObj.url = url;
        sendObj.sendData = sendData;
        sendObj.callFunc = callFunc;
        sendObj.xhrType = xhrType;
        this._dict_sendRequest[url] = sendObj;

        if(!this._isRequesting){
            this._doNext();
        }
    },

    //下一个请求
    _doNext : function () {
        var sendObj;
        for(var url in this._dict_sendRequest){
            sendObj = this._dict_sendRequest[url];
            if(sendObj) {
                this._sendRequest(sendObj.url, sendObj.sendData, sendObj.callFunc, sendObj.xhrType);
                break;
            }
        }
    },
    //发送请求
    _sendRequest : function (url, sendData, callFunc, xhrType) {
        var xhr = new XMLHttpRequest();
        var self = this;
        xhr.onreadystatechange = function () {
            if(xhr.status == 404){
                //无效的请求地址
                self._endRequest(url, null, callFunc);
            }else {
                // console.log('request url= '+url+'; return data= '+xhr.responseText)
                self._receiveData(xhr, url, sendData, callFunc);
            }
        };
        //是否本地
        var needUrl = url;
        if(!xhrType) xhrType = 'POST';
        if(cc.sys.isNative){
            needUrl = this._nativeHref + url;
            xhr.setRequestHeader("User-Agent", "Chrome");
        }
        xhr.open(xhrType, needUrl, true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        if(this._sessionID) {
            // console.log('附加cookie信息'+this._sessionID)
            // var session = 'session={'+this._sessionID+'}'
            // var session = this._sessionID +'';
            // xhr.setRequestHeader("cookie", session);
            // xhr.setRequestProperty("cookie", this._sessionID);
        }
        // xhr.setRequestHeader("Content-Type", "Access-Control-Allow-Headers:x-requested-with");
        // xhr.setRequestHeader("Content-Type", "Access-Control-Allow-Methods:POST");
        // xhr.setRequestHeader("Content-Type", "Access-Control-Allow-Origin:*");
        xhr.send(sendData);
        this._isRequesting = true;
        if(!GG.getIsLoading())GG.topTouchLayer.showNetRequest();
        //增加超时处理
        var self = this;
        this._addTimeOut(function () {
            self._endRequest(url, null, callFunc);
        })
    },

    //接收到网络请求
    _receiveData : function (xhr, url, sendData, callFunc) {
        //if (xhr.readyState == 4 && (xhr.status >= 200 && xhr.status < 400)) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var response = xhr.responseText;

            if(url.indexOf('logout') > -1 || url.indexOf('login') > -1){
                //xhr.responseText 是整个页面的HTML
                // var cookieValue = xhr.getResponseHeader("Set-Cookie");
                // this._sessionID = cookieValue.substring(0, cookieValue.indexOf(';'));
                // this._sessionID = xhr.getResponseHeader("Set-Cookie").replace('SID=', '').split(';')[0];
                // this._sessionID = xhr.getResponseHeader("Set-Cookie");
                // console.log('获取到sessionID= '+ this._sessionID)
                response = true;
            }else{
                try {
                    response = JSON.parse(xhr.responseText);
                    // if(!response) response = true;

                    this._checkLongWait(url, response);
                }catch (e){
                    response = null;
                }
            }
            this._endRequest(url, response, callFunc);
        }
    },
    //请求结束
    _endRequest : function (url, response, callFunc) {
        var sendObj = this._dict_sendRequest[url];
        if(sendObj){
            if(sendObj.callFunc){
                sendObj.callFunc(response);
                sendObj.callFunc = null;
            }
            delete this._dict_sendRequest[url];
            if(!this.getIsRequesting()) GG.topTouchLayer.closeNetRequest();
            this._cancelTimeOut();
            this._isRequesting = false;
            this._doNext();
        }
    },
    //增加超时处理
    _addTimeOut : function (callFunc) {
        this._time_timeOut = 6000;
        this._timeOutID = setTimeout(function () {
            if(callFunc){
                callFunc();
                callFunc = null;
            }
        }, this._time_timeOut)
    },
    _cancelTimeOut : function () {
        if(G_DATA.isNumber(this._timeOutID)){
            clearTimeout(this._timeOutID);
            this._timeOutID = null;
        }
    },
    getIsRequesting : function () {
        var isRequesting = false;
        if(this._dict_sendRequest){
            for(var url in this._dict_sendRequest){
                isRequesting = true;
                break
            }
        }
        return isRequesting
    },

    //===========================


    //增加http心跳
    addHeart : function () {
        if(this._isAddHeart) return;
        this._isAddHeart = true;
        var _this = this;
        setInterval(function () {
            _this.timedRefresh();
        },900000);
    },

    timedRefresh : function () {
        this.sendHttpRequest(G_DIALOG_URL.timedRefreshUrl, {t:new Date()}, function (data) {

        }.bind(this));
    },

    //增加长监听的请求
    addLongRequestWait : function (url, sendData, callFunc) {
        if(!this._dict_longWait) this._dict_longWait = {};
        var sendObj = G_OBJ.data_httpSend();
        sendObj.url = url;
        sendObj.sendData = sendData;
        sendObj.callFunc = callFunc;
        this._dict_longWait[url] = sendObj;
    },
    _checkLongWait : function (url, response) {
        if(!this._dict_longWait || !this._dict_longWait[url]) return;
        if(response && !this._dict_sendRequest[url]) {
            var sendObj = this._dict_longWait[url];
            if(sendObj && sendObj.callFunc){
                sendObj.callFunc(response);
                sendObj.callFunc = null;
            }
            delete this._dict_sendRequest[url]
        }

    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});

module.exports = http;