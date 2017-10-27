//多页面切换的插件脚本
//定义左边选项按钮的时候，一定需要一个命名为content的label
//这里使用的滚动层都是有绑定dealscroll脚本的
//默认内容的显示，需要在start里面做
cc.Class({
    extends: cc.Component,

    properties: {
        _clickFunc : null,                              //点击选项后触发的事件
        _leftDealComp : null,                           //点击选项后触发的事件
        _dict_rightContent : null,                      //右边的内容
        _list_rightPrefab : null,                       //右边的预制体集合
        _rightScroll : null,                            //右边的滚动层
        _rightScrollViewComp : null,                   //右边的滚动层
        _lastRightContent : null,                       //右边的上次显示的内容
        _lastIndex : null,                               //上次点击的选项
        _isRightAutoLimit : null,                      //是否禁止右边内容自动刷新
        _clickShowLimit : null,                         //是否主动控制点击事件的表现效果
    },

    // use this for initialization
    onLoad: function () {
        this._leftNormalName = 'normal';
        this._leftChooseName = 'choose';
        this._dict_rightContent = {};
    },

    initData : function (dataObj) {
        this._clickShowLimit = false;
        //dataObj = G_OBJ.data_dialog_exchangePages();
        this._initLeftScroll(dataObj);
        //右边的预制体
        this._list_rightPrefab = dataObj.rightPrefabList;
        this._rightScroll = dataObj.rightDealScrollComp.node;
        this._rightScrollViewComp = dataObj.rightDealScrollComp.getScrollView();

        this._isRightAutoLimit = dataObj.isStopSetRightContent;
    },

    showDefault : function (isReset) {
        if(isReset) this._lastIndex = null;
        this._onClick(null, 0);
    },

    //=======================左边

    _initLeftScroll : function (dataObj) {
        if(!dataObj.leftDealScrollComp) return;
        this._leftDealComp = dataObj.leftDealScrollComp;

        var curData = {
            itemPrefab : dataObj.leftPrefab,
            scrollType : G_TYPE.scrollType.vertical,
        }
        this._leftDealComp.setData(curData);
        //初始化左边内容
        this._clickFunc = dataObj.leftClickFunc;
        var item, contentNode, contentList = dataObj.leftContentList;
        for(var i = 0; i < contentList.length; i ++){
            item = this._leftDealComp.getItemByIndex(i);
            if(item){
                contentNode = item.getChildByName('content');
                if(!contentNode) continue;
                contentNode.getComponent(cc.Label).string = contentList[i];

                this._registerClickEvent(item, this._onClick, this, i);
            }
        }
    },

    _onClick : function (event, index) {
        if(this._lastIndex == index) return;

        if(this._clickFunc){
            this._clickFunc(index)
        }

        if(!this._clickShowLimit) this.doneClickShow(index);
    },

    diyClickShow : function () {
        this._clickShowLimit = true;
    },

    doneClickShow : function (index) {
        this._lastIndex = index;
        this._showLeft(index);
        this._showRightContent(index);
    },

    _showLeft : function (index) {
        var item = this._leftDealComp.getItemByIndex(index);
        if(item){
            if(this._lastTouchBtn) {
                this._lastTouchBtn.getChildByName(this._leftChooseName).active = false;
                this._lastTouchBtn = null;
            }
            this._lastTouchBtn = item;
            item.getChildByName(this._leftChooseName).active = true;
        }
    },

    //右边========================================

    //显示选中的右边内容
    _showRightContent : function (index) {
        this._rightScrollViewComp.scrollToTop(0.5);

        if(this._isRightAutoLimit) return;
        if(this._lastRightContent){
            this._lastRightContent.active = false;
        }

        var rightContent = this._dict_rightContent[index];
        if(!rightContent){
            rightContent = this._createRightContent(index);
        }
        this._rightScroll.height = rightContent.height;
        rightContent.active = true;
        this._lastRightContent = rightContent;
    },

    //新建一个规则
    _createRightContent : function (index) {
        if(!this._list_rightPrefab[index]) return null;
        var rightContent = cc.instantiate(this._list_rightPrefab[index])
        rightContent.parent = this._rightScroll;
        this._dict_rightContent[index] = rightContent;
        return rightContent
    },

    _registerClickEvent : function (node, callBack, target, userData) {
        node.on(cc.Node.EventType.TOUCH_START, function (event) {
            GG.audioMgr.playSound(17);
        }, target);
        node.on(cc.Node.EventType.TOUCH_END, function (event) {
            callBack.call(target, event, userData);
        }, target);
        node.on(cc.Node.EventType.TOUCH_CANCEL, function (event) {

        }, target);
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
