//表格操作，读取与解析

//表格对象，存放着这个表格所有的信息
var tableObj = cc.Class({
    _nameList           : null,
    _typeList            :null,
    _firstDict           : null,
    _dataDict            : null,
    _keyList            : null,
    setData : function(_nameList, _typeList, dataDict, keyList){
        this._nameList = _nameList;
        this._typeList = _typeList;
        this._firstDict = dataDict;
        this._keyList = keyList;
        this._dataDict = {};
    },

    getDataByID : function(keyID){
        if(this._dataDict[keyID]) return this._dataDict[keyID];
        var data = this._parseObj(keyID);
        this._dataDict[keyID] = data;
        return data;
    },
    getKeyList : function(){
        return this._keyList;
    },
    //获取第一条信息的值
    getFirstData : function () {
        var data;
        for(var key in this._firstDict){
            data = this.getDataByID(key);
            if(data) break;
        }
        return data;
    },
    //获取所有的表格信息
    getAllData : function () {
        var index, data = {};
        for(var i = 0; i < this._keyList.length; i ++){
            index = this._keyList[i];
            data[index] = this.getDataByID(index);
        }
        return data;
    },

    _parseObj : function(keyID){
        if(!this._firstDict) return null;
        var curData = this._firstDict[keyID];
        if(!curData){
            console.log('table parse _____ no this key :' + keyID)
            return null;
        }
        var dataObj = {};
        //第一个ID的值忽略
        for(var i = 1; i < curData.length; i ++){
            var data = this._parseByType(curData[i], this._typeList[i], this._nameList[i]);
            dataObj[this._nameList[i]] = data;
        }
        return dataObj;
    },
    _parseByType : function(data, type, objName){
        if(data == undefined || data == null) return null;

        try {
            switch (type) {
                case 'key_int':
                    return parseInt(data);
                case 'number':
                    return parseFloat(data);
                case 'string':
                    return data.toString();
                case 'json':
                    data = data.replace(/\'/g,'\"');
                    return JSON.parse(data);
                case 'calculate':
                    return data.toString();

                default:
                    return null;
            }
        } catch (e) {
            console.log(e)
            // console.log('table parse _______ fileName =' + this._fileName + ';  objName =' + objName + ';  error obj =' + data)
            return null;
        }
    },

})

//表格管理器
var tableMgr = cc.Class({
    //extends : cc.Component,
    properties: {
        _tDict : null,
        _callFunc : null,
        _list_reloadNames : null,
    },
    ctor : function () {
        //GG.tableMgr = this;
    },
    getTable : function(tableName){
        return this._tDict[tableName];
    },

    //导入表格
    reloadTables : function (tableNames, callFunc) {
        var type = Object.prototype.toString.call(tableNames);
        switch (type){
            case '[object String]':
                this._list_reloadNames = [tableNames];
                break;
            case '[object Array]':
                this._list_reloadNames = tableNames;
                break;
            case '[object Object]':
                this._list_reloadNames = [];
                for(var key in tableNames){this._list_reloadNames.push(tableNames[key]);}
                break;
            default:
                return;
        }
        this._callFunc = callFunc;
        this._loadStep();
    },
    _loadStep : function () {
        if(this._list_reloadNames.length <= 0){
            if(this._callFunc) {
                this._callFunc();
                this._callFunc = null;
            }
            return
        }
        //var name = this._list_reloadNames[0];
        var name=this._list_reloadNames.splice(0,1);
        this._loadTable(name);
    },
    _loadTable : function (tableName) {
        if(this._tDict && this._tDict[tableName]) {
            this._loadStep();
            return;
        }
        cc.loader.loadRes("Tables/"+tableName, function (err, data) {
            if(err){
                console.log(err)
                if(this._callFunc) {
                    this._callFunc();
                    this._callFunc = null;
                }
            }else{
                if(!this._tDict) this._tDict = {};
                this._tDict[tableName] = this._sortData(data);
                this._loadStep();
            }
        }.bind(this));
    },

    _sortData : function(xdata){
        if(!xdata) {
            console.log('table is null')
            return null;
        }
        this._data = xdata;
        var _nameList, _typeList;
        var _xData = {};
        var _keyList = [];
        for(var i = 0; i < xdata.length; i ++){
            switch (i){
                case 0:
                    //tips
                    break;
                case 1:
                    //dataName
                    _nameList = xdata[i];
                    break;
                case 2:
                    //dataType
                    _typeList = xdata[i];
                    break;
                default:
                    //dataDetail
                    var keyID = xdata[i][0];
                    _keyList.push(keyID);
                    _xData[keyID] = xdata[i];
                    break;
            }
        }

        var table = new tableObj();
        table.setData(_nameList, _typeList, _xData, _keyList);
        return table;
    },

    //游戏一开始加载所有的配置
    reloadConfigOnStart : function (callFunc) {
        this.reloadTables(G_RES_URL.dict_tablesName, callFunc);
    },
    
    onDestroy : function () {
        //for(var tableName in this._tDict){
        //    cc.loader.release("Tables/"+tableName);
        //}
    }
})

module.exports = tableMgr;