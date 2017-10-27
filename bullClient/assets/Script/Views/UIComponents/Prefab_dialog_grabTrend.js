//押宝走势图窗体
cc.Class({
    extends: require('BaseDialog'),

    properties: {
        _resultDict: null,
        _oneResultDict: null,
        _loseColor: null,
        _bullResult: null,
        _bullValue: null,
        node_data:{
            default:null,
            type:cc.Node,
            displayName:'走势图数据节点'
        },
        node_noData:{
            default:null,
            type:cc.Node,
            displayName:'无走势记录时显示节点'
        },
        node_trendBG: {
            default:null,
            type:cc.Node,
            displayName:'无走势记录时背景节点'
        },
    },

    // use this for initialization
    onLoad: function () {
        this._super();
        this._pokerCompName = 'Obj_poker';
        this._pokerResultCompName = 'Obj_onPokerResult';
        this._pokerContainerName = 'pokerContainer';
        this._pokerResultNodeName = 'Prefab_PokerResult';
        this._resultDict = {};
        this._oneResultDict = {};
        this._loseColor = new cc.Color(253, 41, 31);//失败的颜色
        this._winColor = new cc.Color(155, 176, 235);//胜利的颜色
        this._notMatchColor = this.node_data.color;
        this.node_noData.active = false;
        this._winPokerOff = G_Config_grab.num_winPokerOff;
        this._bullResult = 0;
        this._bullValue = 0;

    },

    setData: function (homeID) {
        var dataList;
        if (G_Config_common.isLocal) {
            dataList = {
                code: 1,
                data: [
                    {
                        client: '[29, 30, 11, 17, 2]',
                        profitamount: 1
                    },
                    {
                        client: '[27, 13, 45, 30, 26]',
                        profitamount:-1
                    },
                    {
                        client: '[48, 51, 40, 15, 10]',
                    },
                    {
                        client: '[48, 51, 40, 15, 10]',
                    },
                    {
                        client: '[48, 51, 40, 15, 10]',
                    },
                    {
                        client: '[48, 51, 40, 15, 10]',
                    },
                ],
            };
            this.showLayer();
            this._showList(dataList);
        } else {
            var sendObj = {
                gameModelId: G_TYPE.http_gameModule.grab,
                gameRoomId: homeID,
            };
            this._sendRequest(sendObj, function (dataObj) {
                if(dataObj){
                    this.showLayer();
                    this._showList(dataObj);
                }
            }.bind(this));
        }
    },

    //发送押宝走势图信息的请求
    _sendRequest : function (dataObj, callFunc) {
        var sendData = 'gameModelId=' + dataObj.gameModelId + '&gameRoomId=' + dataObj.gameRoomId;
        GG.httpMgr.sendHttpRequest(G_DIALOG_URL.trendUrl, sendData, function (data) {
            if(data){
                if (callFunc) callFunc(data);
            }
        }.bind(this));
    },


    _showList: function (data) {
        if (data.code == 0) {
            this.node_noData.getComponent(cc.Label).string = data.msg;
            this.node_noData.active = true;
            this.node_data.active = false;
        } else {
            var dataList = data.data;
            this.node_noData.active = false;
            this.node_data.active = true;

            //刷新牌型数据信息
            var pokerList = this.node_data.children;
            var pokerData;
            for(var i = 0; i < pokerList.length; i ++){
                pokerData = dataList[i];
                if(pokerData){
                    pokerList[i].active = true;
                    //牌型牛几显示
                    this._setPokerGroupInfo(pokerData.client, pokerList[i]);
                    //胜负显示
                    this._setWinOrLose(parseInt(pokerData.profitamount), pokerList[i]);
                }else{
                    pokerList[i].active = false;
                }
            }
        }
    },
    //显示一副新卡牌信息
    _setPokerGroupInfo : function (groupInfoList, pokerNodeList) {
        var pokerIndex, pokerInfo, pokerList = [];
        groupInfoList = JSON.parse(groupInfoList);
        for(var i = 0; i < groupInfoList.length; i ++){
            pokerIndex = groupInfoList[i];
            pokerInfo = G_DATA.getPokerInfo(pokerIndex);
            pokerList.push({flow: pokerInfo.pokerType, pokerValue: pokerInfo.pokerValue});
            if (i < 3) {
                if (pokerInfo.pokerValue > 10) pokerInfo.pokerValue = 10;
                this._bullResult += pokerInfo.pokerValue;
            }
            if (i == 3) {
                if (pokerInfo.pokerValue > 10) pokerInfo.pokerValue = 10;
                this._bullValue = pokerInfo.pokerValue;
            }
            if (i == 4) {
                if (pokerInfo.pokerValue > 10) pokerInfo.pokerValue = 10;
                this._bullValue += pokerInfo.pokerValue;
            }
        }
        this._refreshPokerGroundInfo(pokerList, pokerNodeList);
    },
    //刷新一副卡牌信息
    _refreshPokerGroundInfo: function (pokerList, pokerNodeList) {
        var pokerNode,
            bullValue = this._bullValue,
            bullResult = parseInt(this._bullResult),
            pokerContainer = pokerNodeList.getChildByName(this._pokerContainerName),
            resultNode = pokerNodeList.getChildByName(this._pokerResultNodeName);
        for(var i = 0; i < pokerList.length + 1; i ++){
            if (i < 5 && pokerContainer) {
                //刷新卡牌信息
                pokerNode = pokerContainer.children[i];
                pokerNode.getComponent(this._pokerCompName).setPokerInfo(pokerList[i].flow, pokerList[i].pokerValue, true);
            }else if (i == 5 && resultNode) {
                //显示牛几信息
                if (bullResult % 10 == 0) {
                    bullValue = this._bullValue % 10;
                    if (bullValue == 0) bullValue = 10;
                } else bullValue = -1;
                resultNode.getComponent(this._pokerResultCompName).showPokerResultValue(parseInt(bullValue), true);
            }
        }
        this._bullValue = 0;
        this._bullResult = 0;
    },
    //显示胜负信息
    _setWinOrLose: function (winOrLose, node) {
        var labelText, labelColor, resultNode = node.getChildByName('resultText');
        if(!resultNode) return;
        this._winOrLoseLabel = resultNode.getComponent(cc.Label);
        this._winOrLoseNode = resultNode;
        if (winOrLose) {//大于0 显示胜；小于0显示负
            if (winOrLose > 0) {
                labelText = G_CHINESE.winMatch;
                labelColor = this._winColor;
            } else {
                labelText = G_CHINESE.loseMatch;
                labelColor = this._loseColor;
            }
        } else {
            labelText = G_CHINESE.notInvolvedMatch;//显示未参与
            labelColor = this._notMatchColor;
        }
        this._winOrLoseLabel.string = labelText;
        this._winOrLoseNode.color = labelColor;
    },
    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
