//玩家记录界面
cc.Class({
    extends: require('BaseDialog'),

    properties: {
        _lastTouchBtn : null,                                                                                           //上次点击的按钮
        _dict_cacheData : null,                                                                                         //缓存数据
        _firstShowNum : null,                                                                                           //首次显示的条目数
        _curDateClass : null,                                                                                           //当前显示的数据时间,比如当天
        _dict_listFull : null,                                                                                          //某天数据是否已经全部请求

        label_null : {
            default : null,
            type : cc.Label,
            displayName : '无数据时显示'
        },
        comp_scroll : {
            default : null,
            type : cc.ScrollView,
            displayName : '滚动的容器'
        },
    },

    // use this for initialization
    onLoad: function () {
        this._super();
        this._dealerItemCompName = 'Obj_dialogRecordCell';
        this._comp_exchangPages = this.getComponent('Part_dialog_exchangePages');

        this._firstShowNum = 6;
        this._dict_cacheData = {};
        this._dict_listFull = {};

        var dataObj = G_OBJ.data_dialog_exchangePages();
        dataObj.leftDealScrollComp = this.comp_leftBtnContainer;
        dataObj.leftPrefab = this.prefab_leftButton;
        dataObj.leftContentList = G_CHINESE.exchangePageLeft;
        dataObj.leftClickFunc = this.onClick_leftBtn.bind(this);
        dataObj.rightDealScrollComp = this.comp_itemContainer;
        dataObj.isStopSetRightContent = true;
        //dataObj.rightPrefabList = [this.prefab_introduce, this.prefab_bull100, this.prefab_grab];
        this._comp_exchangPages.initData(dataObj);
        this._comp_exchangPages.diyClickShow();

        //右边滚动容器
        var dataObj = {
            itemPrefab : this.prefab_item,
            scrollType : G_TYPE.scrollType.vertical,
        }
        this.comp_itemContainer.setData(dataObj);

        this.comp_scroll.node.on('scroll-to-bottom', this._scrollToBottom, this);
    },

    setData : function () {
        this._comp_exchangPages.showDefault(true);
    },

    //点击左边的选项按钮
    onClick_leftBtn : function (type) {
        if(G_Config_common.isLocal) {
            this.showLayer();
            this._updateBottom(this._getLocalRecordData(), true);
            this._comp_exchangPages.doneClickShow(type);
            return;
        }

        this._getDataByDate(type, 1, function (infoList) {
            this.showLayer();
            this._curDateClass = type;
            this._comp_exchangPages.doneClickShow(type);
            this._updateBottom(infoList, true);
            // if(this.comp_itemContainer.getShowItemNum() && infoList && infoList.length < this._firstShowNum){
            //     //该天的数据已经全部显示
            //     this._dict_listFull[type] = true;
            // }
        }.bind(this));
    },

    //获取数据
    _getDataByDate : function (dateClass, pageNo, callFunc) {
        var needData = this._dict_cacheData[dateClass];
        if(needData){
            var infoList = [];
            if(this._dict_cacheData[dateClass].data){
                var allCacheList = this._dict_cacheData[dateClass].data.datas.concat([]);
                infoList = allCacheList.splice(this._firstShowNum * (pageNo-1), this._firstShowNum);
            }
            if(Object.prototype.toString.call(infoList) === '[object Array]'){
                if(callFunc) callFunc(infoList);
            }else{
                this._requestNetData(dateClass, pageNo, callFunc);
            }
        }else{
            this._requestNetData(dateClass, pageNo, callFunc);
        }
    },
    //发送记录信息的请求
    _requestNetData : function (dateClass, pageNo, callFunc) {
        if(this._dict_listFull[dateClass]) return;
        var sendObj = G_OBJ.data_requestRecord();
        sendObj.gameId = GG.getPlayer().getGameID();
        sendObj.dateClass = dateClass;
        sendObj.pageSize = this._firstShowNum;
        sendObj.pageNo = pageNo;

        //gameId ： 属于那款游戏； dateClass：日期（0是当天,1,2）；pageSize：当前页面条目总数；pageNow：第几页；
        var sendStr = "gameId="+sendObj.gameId+"&dateClass="+sendObj.dateClass+"&pageSize="+sendObj.pageSize+"&pageNo="+sendObj.pageNo;
        GG.httpMgr.sendHttpRequest(G_DIALOG_URL.recordUrl, sendStr, function (data) {
            if(data){
                var dataList = this._cacheNetData(dateClass, data);
                if(dataList && callFunc) callFunc(dataList);
            }
        }.bind(this));
    },

    //=============================条目滚动自动延伸

    //缓存列表
    _cacheNetData : function (dateClass, netData) {
        var dataList = [];
        if(netData.code != 1 || !netData.data.datas || netData.data.datas.length < 1){
            // this._noneTip = netData.msg;
        }else{
            dataList = netData.data.datas;
            if(this._dict_cacheData[dateClass]){
                this._dict_cacheData[dateClass].code = netData.code;
                if(this._dict_cacheData[dateClass].data){
                    this._dict_cacheData[dateClass].data.datas = this._dict_cacheData[dateClass].data.datas.concat(dataList);
                }else{
                    this._dict_cacheData[dateClass].data = netData.data;
                }
            }
        }
        if(!this._dict_cacheData[dateClass]) this._dict_cacheData[dateClass] = netData;
        return dataList
    },

    //当玩家滚动到底部
    _scrollToBottom : function (event) {
        var self = this;
        var showItemNum = this.comp_itemContainer.getShowItemNum();
        this._getDataByDate(this._curDateClass, this.comp_itemContainer.getPageNoByNum(showItemNum+1, this._firstShowNum), function (infoList) {
            self._updateBottom(infoList);
        });
    },

    //当scroll滚动到底边  isRestart : 是否从第一个开始
    _updateBottom : function (infoList, isRestart) {
        var item, comp, index = 0, playerInfo, itemNum = this.comp_itemContainer.getShowItemNum();
        if(isRestart) itemNum = 0;
        for (var i = itemNum; i < (itemNum + this._firstShowNum); i++) {
            playerInfo = infoList[index];
            if(!playerInfo){
                //如果没有玩家数据则终止，不要出现断层
                break;
            }
            item = this.comp_itemContainer.getItemByIndex(i);
            if(item){
                comp = item.getComponent(this._dealerItemCompName);
                comp.setData(playerInfo);
            }
            index += 1;
        }
        //如果是第一页，需要将多余的条目清理
        if(isRestart) {
            this.comp_itemContainer.clearItems(infoList.length - 1);
            this._scrollToUp();
            if(infoList.length < 1) {
                this._setNull(this._dict_cacheData[this._curDateClass].msg);
            } else {
                this._setNull('');
            }
        }
        //检测该页数据是否满值
        if(infoList.length < this._firstShowNum){
            this._dict_listFull[this._curDateClass] = true;
        }
    },

    //视图内容将在规定时间内滚动到视图顶部。
    _scrollToUp : function () {
        this.comp_scroll.scrollToTop(0.2);
    },

    //做空的显示
    _setNull : function (msg) {
        if(Object.prototype.toString.call(msg) !== '[object String]'){
            msg = 'request timeout!'
        }
        this.label_null.string = msg;
    },

    //获取本地数据
    _getLocalRecordData : function () {
        var curList = [];
        for(var i = 0; i < 7; i ++){
            var curObj = {
                'confirmtime':'2007/1/1',//充值时间
                'gamemodelname':'经典',//房间类型
                'gameroomname':'房间'+i,//房间名
                'effectiveamount': 1000,//押注金额
                'profitamount':123 * (cc.random0To1() > 0.5 ? 1 : -1),//结果
            }
            curList.push(curObj);
        }
        curList[0].profitamount = -5000;
        curList[1].profitamount = -10000;
        return curList
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});