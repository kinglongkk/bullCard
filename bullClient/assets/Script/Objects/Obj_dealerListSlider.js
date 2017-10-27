//上庄进度条处理


cc.Class({
    extends: cc.Component,

    properties: {
        _sliderComp : null,                                                                                             //进度条组件
        _ui_moveNum : null,                                                                                             //移动的数字
        _label_moveNum : null,                                                                                          //移动的数字
        _oneWordWidth : null,                                                                                           //一个数字的长度
        _offNum : null,                                                                                                  //间隔数额
        _curMoveNum : null,                                                                                             //当前的移动到的数额
        _handleNode : null,                                                                                             //滑动的点
        _idleMoney : null,                                                                                               //空闲的资金，如果有则可以增大上庄金额

        node_lightLine : {
            default : null,
            type : cc.Node,
            displayName : '进度条'
        },
        node_touchLimit : {
            default : null,
            type : cc.Node,
            displayName : '进度条触摸吞噬层'
        },
    },

    // use this for initialization
    onLoad: function () {
        this._sliderComp = this.node.getComponent(cc.Slider);

        this._baseWordNum = 4.5;
        this._time_numHide = 2;

        this._addSliderEvent();
        this._initMoveNum();
        this._resetLightLine();
    },
    //增加监听事件
    _addSliderEvent : function () {
        this._sliderComp.node.on('slide', this._sliderMove, this);
        this._sliderComp.node.on(cc.Node.EventType.TOUCH_END, this.sliderEnd, this);

        var handle = this.node.getChildByName('Handle');
        if(handle){
            var btn = handle.getComponent(cc.Button);
            handle.on(cc.Node.EventType.TOUCH_CANCEL, this.sliderEnd, this);
        }
    },
    //初始化可操作节点
    _initMoveNum : function () {
        var handle = this.node.getChildByName('Handle');
        if(handle.children.length > 0){
            this._ui_moveNum = handle.children[0];
            this._label_moveNum = this._ui_moveNum.children[0].getComponent(cc.Label);
            this._oneWordWidth = this._ui_moveNum.width / this._baseWordNum;
        }
    },

    //myGold: 自己拥有的金币; offGold：移动金币的差值，minLimitGold：最小金币限额
    setData : function (myGold, offGold, minLimitGold) {
        if(!myGold) {
            this._setTouchLimit(true);
            return;
        }
        this._playerGold = myGold;
        this._offNum = offGold;
        this._minNum = minLimitGold;
        this._idleMoney = this._playerGold - this._minNum;

        var firstNum;
        if(this._playerGold < this._minNum){
            //钱不足
            this._setTouchLimit(true);
            firstNum = this._minNum;
            this._setGoldProgress(0)
        }else if(this._playerGold < (this._minNum + this._offNum)){
            this._setTouchLimit(true);
            this._setGoldProgress(1)
            this._resetLightLine();
            firstNum = this._minNum;
        }else{
            this._setTouchLimit(false);
            firstNum = this._minNum;
            this._setGoldProgress(0)
        }
        this._setUIMoveNum(firstNum);
        this._setMoveNumShow(true);
        this._isShow = true;
    },

    //移动回调，如果进度条有移动，则这里会被调用
    _sliderMove : function (event) {
        //console.log(event)
        this._resetLightLine();
        //显示数值
        var num = this._idleMoney * this._sliderComp.progress;
        var changeNum = Math.floor(num/this._offNum)*this._offNum;
        this._setUIMoveNum(changeNum+this._minNum);

        this._setMoveNumShow(true);
    },
    sliderEnd : function () {
        this._addTimeHide();
    },

    //设置进度
    _setGoldProgress : function (value) {
        this._sliderComp.progress = value;
        this._resetLightLine();
    },

    //设置进度条滑动的表现
    _resetLightLine : function () {
        this.node_lightLine.width = this.node.width*this._sliderComp.progress;
    },

    //=================================滑动点上的数字显示

    //显示移动在进度条上的数值
    _setUIMoveNum : function (num) {
        this._curMoveNum = num;
        num += '';
        var len = num.length;
        this._ui_moveNum.width = len * this._oneWordWidth
        this._label_moveNum.string = num;

        //this._ui_moveNum.x = this._handleNode.x;
    },

    //获取选择数值
    getMoveNum : function () {
        return this._curMoveNum;
    },

    //控制进度条上面的数值的显隐
    _setMoveNumShow : function (isShow) {
        if(this._ui_moveNum) {
            this._ui_moveNum.stopAllActions();
            if(isShow){
                this._ui_moveNum.opacity = 255;
                this._ui_moveNum.active = true;
            }else this._ui_moveNum.active = false;
        }
    },

    _addTimeHide : function () {
        if(this._ui_moveNum) {
            this._ui_moveNum.stopAllActions();
            this._ui_moveNum.runAction(cc.sequence(cc.fadeOut(this._time_numHide), cc.callFunc(function () {
                this._setMoveNumShow(false);
            }, this)));
        }
    },

    //==========================================

    _setTouchLimit : function (isLimit) {
        if (isLimit) {
            this.node_touchLimit.active = true;
            this.node_touchLimit.on(cc.Node.EventType.TOUCH_START, this._onClick_Touch, this);
        } else {
            this.node_touchLimit.off(cc.Node.EventType.TOUCH_START, this._onClick_Touch, this);
            this.node_touchLimit.active = false;
        }
    },
    _onClick_Touch : function () {
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
