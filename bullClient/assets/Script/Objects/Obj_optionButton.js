//显示倍数的按钮

cc.Class({
    extends: cc.Component,
    _valueOffX : null,                             //倍数大于10，显示容器的偏移量
    properties: {
        _firstFrame : null,
        _lastValue : null,

        frame_disable : cc.SpriteFrame,             //不可用的时候使用的图片
        node_centerLabel : {
            default : null,
            type : cc.Node,
            displayName : '显示内容的label'
        },//不可用的时候使用的图片
    },

    // use this for initialization
    onLoad: function () {
        this._normalColor = new cc.Color(199,140,39);
        this._chooseColor = new cc.Color(137,98,207);
    },

    //显示倍数，调整label的对齐
    showValue : function (value, isRed) {
        this._showMultiple(value, isRed);
    },
    //显示金钱
    showMoney : function (value, isRed) {
        this._showMoneyEX(value, isRed);
    },
    _initContainer : function () {
        if(!this._container) {
            this._container = this.node.getChildByName('container');
            if(!this._container) return false;
            this._firstFrame = this.getComponent(cc.Sprite).spriteFrame;
            this._valueOffX = G_Config_common.num_multipleValueOff;
            this.node_leftLabel = this._container.getChildByName('left');
            this._rightLabel = this._container.getChildByName('right').getComponent(cc.Label);
        }
        return true;
    },
    _isValidValue : function (value) {
        if(!value) return false;
        if(value == this._lastValue) return false;
        this._lastValue = value;
        return true;
    },

    //替换红色资源
    _showRed : function () {
        this.node_leftLabel.getComponent(cc.Label).font = this.font_red;
        this._rightLabel.font = this.font_red;

        this._showDisable(true);
    },

    //替换绿色资源
    _showGreen : function () {
        this.node_leftLabel.getComponent(cc.Label).font = this.font_green;
        this._rightLabel.font = this.font_green;

        this._showDisable(false);
    },

    _showDisable : function (isDisable) {
        if(!this._firstFrame) this._firstFrame = this.getComponent(cc.Sprite).spriteFrame;
        if(isDisable){
            this.getComponent(cc.Sprite).spriteFrame = this.frame_disable;
        }else{
            this.getComponent(cc.Sprite).spriteFrame = this._firstFrame;
        }
    },


    //显示倍数
    _showMultiple : function (value, isRed) {
        this.node_centerLabel.getComponent(cc.Label).string = value+'倍';
        this.node_centerLabel.getComponent(cc.LabelOutline).color = this.getOutLineColor(isRed);
        this._showDisable(isRed);
    },
    //显示金钱
    _showMoneyEX : function (value, isRed) {
        if(value) this.node_centerLabel.getComponent(cc.Label).string = G_TOOL.changeMoney(value);
        this.node_centerLabel.getComponent(cc.LabelOutline).color = this.getOutLineColor(isRed);
        this._showDisable(isRed);
    },

    //获取对应颜色
    getOutLineColor : function (isRed) {
        return isRed ? this._chooseColor : this._normalColor;
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
