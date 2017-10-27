//跑马灯，公告


cc.Class({
    extends: cc.Component,
    _moveSpeed : null,                                      //每秒移动的速度
    _isMove : null,                                         //是否移动
    _list_msg : null,                                       //显示消息的队列
    _firstPosX : null,                                       //通知出现的初始位置
    properties: {
        node_view : cc.Node,                                //显示节点
        node_content : cc.Node,                             //滚动节点
        // node_wifi1 : cc.Node,                               //wifi1
        // node_wifi2 : cc.Node,                               //wifi2
        // node_wifi3 : cc.Node,                               //wifi3
    },

    // use this for initialization
    onLoad: function () {
        this._initConfigData();
    },

    //设置公告运行属性
    _initConfigData : function () {
        this._curLabelNode = this.node_content.children[0];
        this._curLabel1 = this._curLabelNode.getComponent(cc.RichText);
        this._firstPosX = this._curLabelNode.x+this.node_view.width*0.75;
        this._endPosX = this._curLabelNode.x - this.node_view.width*2;

        this._isMove = false;
        this._moveSpeed = G_Config_common.notice_speed;
        // this._runTime = 0;
        // this._interval_next = G_Config_common.notice_showTime;
        this._list_msg = [];
    },

    ////开启wifi实时监听
    //openWifiAni : function () {
    //
    //},
    ////关闭wifi监听
    //closeWifiAni : function () {
    //
    //},
    //隐藏公告
    hideNotice : function () {
        this._isMove = false;
        this.node.active = false;
    },

    //增加准备显示的公告消息
    addMsg : function (msg) {
        this._resetShowContent(msg);
        //msg = "<color=#00ff00>Ric111111</c><color=#0fffff>Text</color>12132132132131313213213213211";
    },

    ////增加优先显示的消息(当前显示结束后显示)
    //addHeightPriorityMsg : function (msg) {
    //    if(!this._list_msg) this._resetData();
    //    this._list_msg.splice(0,0,msg);
    //    this._setNextMsg();
    //},
    //
    ////直接显示的消息(将会替换掉当前的消息)
    //addFirstMsg : function (msg) {
    //    if(!this._list_msg) this._resetData();
    //    this._curLabel1.string = msg;
    //    this._restartLabel();
    //},



    //===========================================

    //下一条公告
    //_setNextMsg : function () {
    //    if(this._isMove) return;
    //
    //    if(this._list_msg.length > 0){
    //        this.node.active = true;
    //        var msg = this._list_msg[0];
    //        this._list_msg.splice(0, 1);
    //
    //        this._curLabel1.string = msg;
    //        this._restartLabel();
    //    }else this.node.active = false;
    //},

    //重新设置内容
    _resetShowContent : function (msg) {
        this.node.active = true;
        this._isMove= true;
        this._curLabel1.string = msg;
        this._restartLabel();
    },

    //重新开始移动label
    _restartLabel : function () {
        this._curLabelNode.x = this._firstPosX;
        // this._runTime = 0;
        this._isMove = true;
    },

    // called every frame, uncomment this function to activate update callback
    update: function (dt) {
        if(this._isMove){
            this._curLabelNode.x -= dt * this._moveSpeed;
            if(this._curLabelNode.x <= this._endPosX){
                this._isMove = false;
                this.node.active = false;
            }
            // if(this._runTime >= this._interval_next){
            //
            // }else this._runTime += dt;
        }
    },
});
