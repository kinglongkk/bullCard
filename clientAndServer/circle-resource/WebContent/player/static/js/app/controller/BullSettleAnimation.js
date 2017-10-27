/**
 * Created by jin on 2016/12/24.
 * 结算动画
 */

define(['jsrender',
        'view/viewBase',
        'bull100/ctrlBull',
        'text!app/template/BullSettleAnimation.html'],
    function (jsrender, vBase, cBull, template) {

    return Class.extend({

        /**
         * 构造方法
         */
        init: function (settleResult) {
            this.renderTemplate(settleResult);
        },

        /**
         * 渲染模板
         */
        renderTemplate: function (settleResult) {
            var t = this;
            var html = $(template).render(settleResult);
            t.resultHtml = $("#BullAnimation").html(html);
            var winLoseDiv = new vBase.Div(t.resultHtml.children());
            cBull.winLoseAnimation = new vBase.AnimationTip(winLoseDiv, 4000);
            $(".Bull_victory_btn").click(function () {
                cBull.winLoseAnimation.hide();
            });
            return t;
        }
    });
});
