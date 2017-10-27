//存储工具，对本地的数据进行操作

window.G_Storage = {
    setData : function (localType, data) {
        cc.sys.localStorage.setItem(localType, data);
    },

    delData : function (localType) {
        cc.sys.localStorage.removeItem(localType);
    },

    getData : function (localType) {
        return cc.sys.localStorage.getItem(localType)
    },
}