/**
 * Created by black on 2016/10/25.
 * 玩家账目
 */
define(['jsrender',
        'view/viewBase',
        'text!app/template/dialog/Accounts.html'], function (jsrender, vBase, template) {

    return Class.extend({


        /**
         * 构造方法
         */
        init : function (parameter) {
            this.renderTemplate(parameter);
        },

        /**
         * 渲染模板
         */
        renderTemplate : function (parameter) {
            var _this = this;
            _this.dateClass = parameter;
            var html = $(template);
            $(".dialogFront").html(html);
            var $content = $(".Account_content");
            var $list = $("#dataUl");
            var $end = $("#pageEnd");
            var $tmpl = $("#dataTmpl");
            var pageData = {"gameId":1, dateClass:parseInt(_this.dateClass), pageSize: 20};
            _this.scrollPage = new vBase.ScrollPage($content, $list, $end, root + "/playerRecharge/playerAccountRecord.html",
                pageData, function (data, pageNow) {
                if (pageNow == 0) {
                    $list.html($tmpl.render(data));
                    $("#dialogLoading").hide();
                } else {
                    $list.html($list.html()+$tmpl.render(data));
                }
            });
            var dialog = new vBase.Dialog();
            dialog.show();

            $("#dataTab").children().click(function (e) {
                $('#dialogLoading').show();
                $("[tab]").addClass('bg3_op');
                $("[tab]").eq($(this).index()).removeClass('bg3_op');
                $(this).parents(".dia_big").find("#dataUl").empty();
                $(this).parents(".dia_big").find( "." + $(this).attr("tab")).show();
                pageData.dateClass = $(e.target).attr("data-value");
                _this.scrollPage.reloadData();
            });
        }
    });
});