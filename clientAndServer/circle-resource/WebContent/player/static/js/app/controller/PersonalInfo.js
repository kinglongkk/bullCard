/**
 * Created by black on 2016/10/14.
 * 玩家信息
 */
define(['jsrender',
        'view/viewBase',
        'app/controller/dialog/PlayerSysSet',
        "site/common/MoneyFormat",
        'text!app/template/PersonalInfo.html'],
    function (jsrender, vBase, PlayerSysSet, MoneyFormat, template) {

    return Class.extend({

        /**
         * 渲染模板
         *
         */
        renderTemplate : function (isHome) {
            $.ajax({
                type: "post",
                url: root + "/game/playerInfo.html",
                dataType:"json",
                success:function(data){
                    var html = $(template).render(data);
                    $(".personal_info").html(html);
                    if (isHome){
                        $("a.GB_back").hide();
                    }else {
                        $("a.GL_exit").hide();
                    }

                    //玩家系统设置
                    $('a.setting').click(function () {
                        new PlayerSysSet;
                    });
                    //退出游戏大厅到登陆页面
                    $('a.GL_exit').click(function () {
                        vBase.msgDialog.show("你确定要退出游戏？", "确定退出", "我点错了", function () {
                                window.location.href = root + "/passport/logout.html";
                            }, function () {
                                console.log("msgDialog cancel");
                            }
                        );
                    })
                },
                error:function (jqXHR, textStatus, errorThrown) {
                    alert("you have a error playerInfo"+textStatus);
                }
            });
        },

        /**
         * 实时更新头像上的金币
         */
        updatePersonInfoMoney: function (money) {
            $("#infoNum").html(MoneyFormat.format(money));
        }
    });
});