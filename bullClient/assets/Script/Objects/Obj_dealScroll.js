//自动处理滚动层逻辑

var dataObj = {
    itemPrefab : null,
    lineNum : null,
    scrollType : null,
}


cc.Class({
    extends: cc.Component,

    properties: {
        _itemPrefab : null,                                                                                             //条目的复制体
        _showItemNum : null,                                                                                             //显示的条目数量
        _itemHeight : null,                                                                                              //条目的高度
        _itemWidth : null,                                                                                               //条目的宽度
        _itemOffX : null,                                                                                                //条目之间的间隔
        _itemOffY : null,                                                                                                //条目之间的间隔
        _pool : null,                                                                                                     //对象池
        _scrollType : null,                                                                                              //滚动容器的类型
        _viewW2 : null,                                                                                                   //可视区域宽度的一半
        //背包类型需要用到的属性
        _lineNum : null,                                                                                                  //每一行的单元数量
    },

    // use this for initialization
    onLoad: function () {
        this._itemOffY = 0;
        this._itemOffX = 0;
    },

    setData : function (dataObj) {
        this._initData();
        this._itemPrefab = dataObj.itemPrefab;
        this._lineNum = dataObj.lineNum;
        this._scrollType = dataObj.scrollType;
        //默认类型
        if(!this._scrollType) this._scrollType = G_TYPE.scrollType.vertical;
    },
    _initData : function () {
        this._showItemNum = 0;
        this._itemHeight = null;
        this._itemWidth = null;
        if(!this._viewW2) this._viewW2 = this.node.parent.width*0.5;
    },

    //添加一个条目
    addOneItem : function () {
        var item = this._getOnePoolNode();

        switch (this._scrollType){
            case G_TYPE.scrollType.vertical:
                item.y = -this._showItemNum * (this._itemHeight + this._itemOffY);
                break;
            case G_TYPE.scrollType.horizontal://水平滚动层，需要去掉水平方向的widget
                var startPosX = -this._viewW2;
                item.x = startPosX + this._showItemNum * (this._itemWidth + this._itemOffY);
                break;
            case G_TYPE.scrollType.bag:
                var startPosX = -this._viewW2;
                var lineNum;
                if(this._showItemNum == 0) lineNum = 0;
                else lineNum = this._showItemNum%this._lineNum;
                var rowNum = Math.floor(this._showItemNum/this._lineNum);
                item.x = startPosX + lineNum * (this._itemWidth + this._itemOffY) + this._itemWidth/2;
                item.y = -rowNum * (this._itemHeight + this._itemOffX);
                break;
            default:
                break;
        }

        this._showItemNum += 1;
        this._resetContainerSize();
        return item
    },

    //获取某个条目(从0开始)
    getItemByIndex : function (index) {
        var items = this.node.children;
        var item = items[index];
        if(item) return item;
        return this.addOneItem();
    },

    //获取所有的条目
    getAllItems : function () {
        return this.node.children;
    },

    //清理条目（从0开始，清理index之后的条目, 不包括index）
    clearItems : function (index) {
        var items = this.node.children;
        for(var i = items.length-1; i > index; i --){
            if(items[i]) this._removePoolNode(items[i]);
        }
    },

    //直接设置容器的大小
    setContentSize : function (width, height) {
        if(width) this.node.width = width;
        if(height) this.node.height = height;
    },

    //=====================================================

    //获取一个条目
    _getOnePoolNode : function () {
        if(!this._itemPrefab) return null;
        if(!this._pool) this._pool = new cc.NodePool('Obj_dealScroll'+this._itemPrefab.name);

        var cNode = this._pool.get();
        if(!cNode){
            cNode = cc.instantiate(this._itemPrefab);
            if(!this._itemHeight) this._itemHeight = cNode.height;
            if(!this._itemWidth) this._itemWidth = cNode.width;
        }
        cNode.parent = this.node;
        cNode.active = true;
        return cNode;
    },

    _removePoolNode : function (cNode) {
        // cNode.active = false;
        this._pool.put(cNode);

        this._showItemNum -= 1;
        this._resetContainerSize();
    },

    _resetContainerSize : function () {
        var contentH, contentW;
        switch (this._scrollType){
            case G_TYPE.scrollType.vertical:
                contentW = this._itemWidth + this._itemOffX;
                contentH = this._showItemNum * (this._itemHeight + this._itemOffY);
                break;
            case G_TYPE.scrollType.horizontal:
                contentW = this._showItemNum * (this._itemWidth + this._itemOffX);
                contentH = this._itemHeight + this._itemOffY;
                break;
            case G_TYPE.scrollType.bag:
                contentW = this._lineNum * (this._itemWidth + this._itemOffX);
                var rowNum = Math.ceil(this._showItemNum/this._lineNum);
                contentH = rowNum * (this._itemHeight + this._itemOffY);
                break;
            default:
                break;
        }
        this.node.height = contentH;
        this.node.width = contentW;
    },

    //获取当前已经显示的条目数量
    getShowItemNum : function () {
        return this._showItemNum
    },

    //获取scrollview组件
    getScrollView : function () {
        return this.node.parent.parent.getComponent(cc.ScrollView)
    },

    //注册滚动到底部的事件
    registerBottomEvent : function () {

    },

    //获取下一页的更新信息
    getNextPageList : function (dataList, intervalNum) {
        return dataList.splice(0,intervalNum);
    },

    //根据数量获取页数 从1开始
    getPageNoByNum : function (itemNum, firstNum) {
        var pageNo = Math.floor(itemNum / firstNum);
        if(itemNum > pageNo * firstNum){
            pageNo += 1;
        }
        return pageNo
    },

    scrollToUp : function () {
        this.getScrollView().scrollToTop(0.2);
    },

    onDestroy : function () {
        if(this._pool) this._pool.clear();
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
