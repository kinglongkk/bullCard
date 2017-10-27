define(['jsrender',
        'app/controller/PersonalInfo',
        'app/controller/Notice',
        'app/controller/PlayerTreasures',
        'text!app/template/GameLobby.html'],
    function (jsrender, PersonalInfo, Notice, PlayerTreasures, template) {

    return Class.extend({

        /**
         * 构造方法
         */
        init : function () {
            this.renderTemplate();
            this.bindEvent();
        },

        /**
         * 渲染模板
         */
        renderTemplate : function () {
            var html = $(template).render();
            $(".container").html(html);
            new PersonalInfo().renderTemplate(true);
            new Notice;
            new PlayerTreasures;
            $(".game_play a").bind("click", function () {
                $('#dialogLoading').show();
            });
        },

        /**
         * 绑定事件
         */
        bindEvent : function () {

            $(document).ajaxComplete(function( event, xhr, settings ) {
                if (xhr.status==600) { //Session过期
                    window.top.location.href=window.top.root;
                } else if (xhr.status==606){//踢出
                    var msg = JSON.parse(xhr.responseText);
                    window.alert(msg.message);
                    window.top.location.href = window.top.root + "/errors/" + xhr.status + ".html";
                } else if (xhr.status != 200) {
                    console.error("error code:" + xhr.status);
                }
            });

            //富豪榜
            $('.rich2').click(function(){
                $("#commonLoading").show();
                $('.earnings').toggleClass('show');
                $('.list_content').toggleClass('hidden');
                $('.button2').toggleClass('show');
                $('.button').toggleClass('hidden');

                $('.earnings').empty();
                new PlayerTreasures;
            });
            //盈利榜
            $('.gain').click(function(){
                $("#commonLoading").show();
                $('.earnings').toggleClass('show');
                $('.list_content').toggleClass('hidden');
                $('.button2').toggleClass('show');
                $('.button').toggleClass('hidden');

                $('.list_content').empty();
                requirejs(['app/controller/PlayerProfit'],function (PlayerProfit) {
                    new PlayerProfit;
                });
            });
            //账目弹窗
            $('.accounts').click(function () {
                $('#dialogLoading').show();
                requirejs(['app/controller/dialog/Accounts'],function (Accounts) {
                    new Accounts(0);
                });
            });
            //记录弹窗
            $('.notes').click(function () {
                $('#dialogLoading').show();
                requirejs(['app/controller/dialog/Record'],function (Record) {
                    new Record(0);
                });
            });
            //规则弹窗 游戏大厅
            $('.GameL-rules').click(function () {
                requirejs(['app/controller/dialog/Rules'],function (Rules) {
                    new Rules;
                });
            });
            //公告弹窗
            $('.GameL-announcement').click(function () {
                requirejs(['app/controller/dialog/Announcement'],function (Announcement) {
                    new Announcement;
                });
            });
        }
    });
});