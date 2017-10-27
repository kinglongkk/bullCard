/**
 * Created by jin on 2016/11/1.
 * 玩家系统设置
 */

define(['jsrender',
        'view/viewBase',
        'app/controller/gameMain',
        "app/view/soundBase",
        'text!app/template/dialog/PlayerSysSet.html'], function (jsrender, vBase, gm, sBase, template) {

    var switchOpen = "Set_open";
    var switchClose = "Set_close";
    var musicBgOpen = "Set_music_open";
    var musicBgClose = "Set_music_close";
    var soundBgOpen = "Set_sound_open";
    var soundBgClose = "Set_sound_close";

    return Class.extend({

        /**
         * 构造方法
         */
        init : function () {
            this.renderTemplate();
        },

        /**
         * 渲染模板
         * jqXHR jqXHR对象
         * textStatus 返回的状态
         * errorThrown 服务器抛出返回的错误信息
         */
        renderTemplate : function () {

            $.ajax({
                type: "post",
                url: root + "/game/PlayerSysSet.html",
                dataType:"json",
                data:{
                    "gameType": "DOU_NIU",
                },
                success:function(data){

                    var html = $(template).render(data);
                    $(".dialogFront").html(html);
                    var dialog = new vBase.Dialog();
                    dialog.show();
                    dialog.onHide = function () {
                        console.log("PlayerSysSet dialog hide");
                    }
                    //声音设置开关
                    var loginOut = $(".Set_switch_btn");
                    var dialogClose = $(".Prompt_close");
                    var musicSet = $(".voiceSet");
                    var soundSet = musicSet.next();
                    var musicBg = $(musicSet.children()[0]);
                    var musicSwitch = $(musicSet.children()[1]);
                    var soundBg = $(soundSet.children()[0]);
                    var soundSwitch = $(soundSet.children()[1]);
                    musicSwitch.click(function () {
                        if (musicSwitch.hasClass(switchClose)) {
                            musicSwitch.removeClass(switchClose).addClass(switchOpen);
                            musicBg.removeClass(musicBgClose).addClass(musicBgOpen);
                            //播放音乐
                            sBase.unMuteBg();
                        }else {
                            musicSwitch.removeClass(switchOpen).addClass(switchClose);
                            musicBg.removeClass(musicBgOpen).addClass(musicBgClose);
                            //暂停音乐
                            sBase.muteBg();
                        }
                    });
                    soundSwitch.click(function () {
                        if (soundSwitch.hasClass(switchClose)) {
                            soundSwitch.removeClass(switchClose).addClass(switchOpen);
                            soundBg.removeClass(soundBgClose).addClass(soundBgOpen);
                            //播放音效
                            sBase.unMute();
                        }else {
                            soundSwitch.removeClass(switchOpen).addClass(switchClose);
                            soundBg.removeClass(soundBgOpen).addClass(soundBgClose);
                            //暂停音效
                            sBase.mute();
                        }
                    });
                    //退出账号
                    loginOut.click(function () {
                        vBase.msgDialog.show("你确定要退出游戏？", "确定退出", "我点错了", function () {
                                window.location.href = root + "/passport/logout.html";
                            }, function () {
                                console.log("msgDialog cancel");
                            }
                        );
                    });
                    //声音设置弹窗关闭
                    dialogClose.click(function () {
                        var musicClass = musicSwitch.attr("class");
                        var soundClass = soundSwitch.attr("class");
                        $.ajax({
                            type: "post",
                            url: root + "/game/PlayerSysSetSave.html",
                            dataType:"json",
                            data:{
                                "musicClass": musicClass,
                                "soundClass": soundClass,
                                "gameType": "DOU_NIU"
                            },
                            success: function () {

                            },
                            error: function () {

                            }
                        });
                    });
                },
                error:function (jqXHR, textStatus, errorThrown) {
                    alert("you have a error PlayerSysSet");
                }
            });
        },
    });
});
