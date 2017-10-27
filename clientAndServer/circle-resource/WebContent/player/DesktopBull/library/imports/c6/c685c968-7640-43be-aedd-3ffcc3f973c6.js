"use strict";
cc._RF.push(module, 'c685clodkBDvq7dP/zD+XPG', 'LocalStorage');
// Script/Common/Managers/LocalStorage.js

"use strict";

//存储工具，对本地的数据进行操作

window.G_Storage = {
    setData: function setData(localType, data) {
        cc.sys.localStorage.setItem(localType, data);
    },

    delData: function delData(localType) {
        cc.sys.localStorage.removeItem(localType);
    },

    getData: function getData(localType) {
        return cc.sys.localStorage.getItem(localType);
    }
};

cc._RF.pop();