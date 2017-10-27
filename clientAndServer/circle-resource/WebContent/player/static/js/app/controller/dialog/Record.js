/**
 * Created by jin on 2016/11/09.
 * 游戏记录
 */

define(['jsrender',
        'view/viewBase',
        'text!app/template/dialog/Record.html'], function (jsrender, vBase,template) {

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
            _this.dataClass = parameter;
            var html = $(template);
            $(".dialogFront").html(html);
            var $content = $(".Record_content");
            var $list = $("#dataUl");
            var $end = $("#pageEnd");
            var $tmpl = $("#dataTmpl");
            var pageData = {"gameId":1, dateClass:parseInt(_this.dataClass), pageSize: 20}
            _this.scrollPage = new vBase.ScrollPage($content, $list, $end, root + "game/statistics/user/records.html",
                pageData, function (data, pageNow) {
                    if(pageNow == 0){
                        $list.html($tmpl.render(data));
                        $('#dialogLoading').hide();
                    }else{
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
                pageData.dateClass = parseInt($(e.target).attr("data-value"));
                _this.scrollPage.reloadData();
            });
        }
    });
});
