/**
 * Created by black on 2016/10/28.
 * 玩家上庄
 */

define(['jsrender','view/viewBase','text!app/template/dialog/Banker.html', "site/common/MoneyFormat", 'bull100/ctrlBull',],
    function (jsrender, vBase, template, MoneyFormat, cBull) {

    return Class.extend({
        dialog : null,
        _myMoney : 0,
        _state : -1,//状态-1不在列表 0在庄 >=1在列表
        _minDealerMoney: 0,
        _playerId: null,

        /**
         * 构造方法
         */
        init : function (bankers, minDealerMoney, myMoney, playerId) {
            this._playerId = playerId;
            this.renderTemplate(bankers, minDealerMoney, myMoney);
        },

        /**
         * 渲染模板
         */
        renderTemplate : function (bankers, minDealerMoney, myMoney) {
            var t = this;
            t._minDealerMoney = minDealerMoney;
            t._myMoney = myMoney;
            var players = bankers.players;
            var dealer = bankers.deskDealer;
            var councilDealer = bankers.dealer;
            players.unshift(dealer);
            var html = $(template).render(bankers);
            $(".dialogFront").html(html);
            var $btnUp = $(".Banker_btn");
            var $bankerBtns = $(".Banker_btn1");
            var $btnContinue = $(".Banker_xz");
            var $btnDown = $(".Banker_xz1");
            t._state = -1;
            var minDealerMoney = t._minDealerMoney;
            for (var i = 0; i < players.length; i++) {
                var player = players[i];
                if(player.playerId == t._playerId){
                    t._state = i;
                    minDealerMoney = player.usableBalance;
                    break;
                }
            }

            if(t.dialog == null){
                t.dialog = new vBase.Dialog();
                t.dialog.onHide = function () {
                    console.log("Banker dialog hide");
                }
            }else{
                t.dialog.init();
            }
            if(!t.dialog.isShowing()){
                t.dialog.show();
            }
            var slider = new vBase.Slider('btn','bar', 'step', 'btnTip', function (percent) {
                // console.log(t._selectMoney);//根据已改变的滑动条的百分比设置金额
            },MoneyFormat.formatBy1000, 10000);
            var showTip = function (text, money) {
                $(".Banker_font1").html(text + "<span>"+MoneyFormat.format(money)+"！</span>");
            }
            if(t._state == -1){//不在上庄列表
                $btnUp.click(function(){
                    cBull.upDealer(parseInt(slider.getValue()));
                });
                $bankerBtns.hide();
                if(t._myMoney < t._minDealerMoney){
                    slider.setRange(t._myMoney, t._myMoney, t._minDealerMoney);
                    slider.setEnable(false);
                    $btnUp.css("opacity", 0.3).unbind("click");
                    showTip("金币不足,无法上庄，上庄至少要", minDealerMoney);
                }else{
                    slider.setRange(minDealerMoney, minDealerMoney, t._myMoney);
                    showTip("上庄最低金额为", minDealerMoney);
                }
            }else{//在上庄列表,或者在庄
                slider.setRange(10000, 10000, t._myMoney);
                if(t._state == 0){
                    showTip("您已在庄，庄家资金", minDealerMoney);
                }else{
                    showTip("上庄排序："+(t._state+1)+",上庄资金", minDealerMoney);
                }
                $btnContinue.click(function(){
                    cBull.keepDealer(parseInt(slider.getValue()));
                    cBull.tipMsg.keepDealer = false;
                });
                $btnDown.click(function(){
                    cBull.downDealer();
                });
                $btnUp.hide();
            }
            return t;
        }
    });
});