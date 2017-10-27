//底部按钮


cc.Class({
    extends: require('BaseUI'),

    properties: {
        node_buttonContainer : {
            default : null,
            type : cc.Node,
            displayName : '按钮容器'
        },
        // node_propContainer : {
        //     default : null,
        //     type : cc.Node,
        //     displayName : '道具容器'
        // }
    },

    // use this for initialization
    onLoad: function () {
        this._registerEvent();

    },

    _registerEvent : function () {
        var btns = this.node_buttonContainer.children;
        for(var i = 0; i<btns.length; i++) {
            btns[i].tag = i;
            this.registerButton(btns[i], this._onClick_touchBtn, this);
        }
    },

    _onClick_touchBtn : function (event) {
        switch (event.currentTarget.tag){
            case 0:
                //点击打开公告弹窗
                //GG.platformMgr.showUI('UI_announcement', false);
                var uiName = G_RES_URL.uiName.announcement;
                var layer = GG.platformMgr.openUI(uiName, function (layer) {
                    layer.showLayer();
                });
                break;
            case 1:
                //点击打开规则弹窗
                //GG.platformMgr.showUI('UI_rules', false);
                var uiName = G_RES_URL.uiName.rules;
                GG.platformMgr.openUI(uiName, function (layer) {
                    layer.showLayer();
                });
                break;
            case 2:
                //点击打开记录弹窗
                //GG.platformMgr.showUI('UI_record', false);
                //GG.platformMgr.getRecordData();

                var uiName = G_RES_URL.uiName.record;
                var layer = GG.platformMgr.openUI(uiName, function (layer) {
                    layer.setData();
                });
                break;
            case 3:
                //点击打开帐目弹窗
                var uiName = G_RES_URL.uiName.account;
                var layer = GG.platformMgr.openUI(uiName, function (layer) {
                    layer.setData();
                });
                break;
            default:
                break;
        }
    },

    //移出屏幕外
    moveOutScene : function (isOut, isNoEffect) {
        if(!this.node_buttonContainer._firstBtnPosY) {
            this.node_buttonContainer._firstBtnPosY = -this.node_buttonContainer.y;
        }
        if(isNoEffect){
            this.node_buttonContainer.y = this.node_buttonContainer._firstBtnPosY - (isOut?this.node_buttonContainer.height*this.node_buttonContainer.scale:0);
        }else{
            var time = 0.5;
            this.node_buttonContainer.stopAllActions();
            var targetPos = cc.p(this.node_buttonContainer.x, this.node_buttonContainer._firstBtnPosY);
            if(isOut) targetPos.y = -(this.node_buttonContainer.height*this.node_buttonContainer.scale/2);
            this.node_buttonContainer.runAction(cc.moveTo(time, targetPos));
        }
        // this.node_propContainer.active = !isOut;
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
