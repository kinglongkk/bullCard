//全局的事件监听与派送

module.exports = cc.Class({
    properties: {
        _dict_listeners : null,                             //所有监听
        _listenerIndex : null,                              //监听索引
        _list_delIndex : null,                              //可用的索引
    },

    // use this for initialization
    ctor: function () {
        this._dict_listeners = {};
        this._list_delIndex = [];
        this._listenerIndex = 0;
    },

    //增加监听
    registerFunc : function (eventType, callFunc) {
        if(!this._dict_listeners[eventType]) this._dict_listeners[eventType] = {};
        var listenerName = callFunc.name+'_'+this._getListenerIndex();
        this._dict_listeners[eventType][listenerName] = callFunc;
        return listenerName
    },

    //去除监听
    delListen : function (eventType, listenerName) {
        if(!this._dict_listeners[eventType]) return;
        if(this._dict_listeners[eventType][listenerName]){
            this._whenCancelListener(listenerName);
            //有这个监听
            delete this._dict_listeners[eventType][listenerName];
            if(Object.keys(this._dict_listeners[eventType]).length < 1) this._dict_listeners[eventType] = null;
        }
    },

    //派送监听的事件
    dispatchEventEX : function (eventType, data) {
        var listenersList = this._dict_listeners[eventType];
        if(listenersList){
            //存在监听对象
            var callFunc;
            for(var name in listenersList){
                callFunc = listenersList[name];
                if(cc.isValid(callFunc)){
                    callFunc(name, data);
                }
            }
        }
    },

    _whenCancelListener : function (listenerName) {
        var indexList = listenerName.split('_');
        var listenerIndex = indexList[indexList.length - 1];
        this._list_delIndex.push(listenerIndex);
    },
    //获取可用的监听索引
    _getListenerIndex : function () {
        if(this._list_delIndex[0]){
            return this._list_delIndex.splice(0,1);
        }else{
            this._listenerIndex += 1;
            return this._listenerIndex
        }
    },

});
