require.config({
    baseUrl: '../static/js',
    packages : [
        {   name     : "view",
            location : "../js/app/view"
        },
        { name     : "site",
            location : "../js"
        },
    ],
    paths: {
        text: '../../../common/js/RequireJS-2.3.2/text',
        css: '../../../common/js/RequireJS-2.3.2/css',
    Velocity : '../../../common/js/velocity/velocity'
    }
});
require(['../../../common/js/circle/common/ClassTool'], function () {
    require(['view/viewBase', 'text!app/template/Bull100BigSmall.html','text!app/template/BullAnimation.html',
           'css!../css/Bull100BigSmall.css','css!../css/Animation.css',],
        function (vBase, temlate, animation) {
            document.getElementsByClassName("container")[0].innerHTML = temlate + animation;
            $('#animationBet').show();
            // $('.rule_left').click(function () {
            //     $('.rule_right').toggleClass('rule_right1');
            //     $('.rule_left').toggleClass('rule_left1');
            // })
            $('.ui-destop-stake').click(function () {
                $('.Betting').toggleClass('show');
            })
            $('.ui-destop-onlookers').click(function () {
                require(['text!../../html/Dialog/DialogPlayerList.html', 'css!../css/Dialog.css'],
                    function (tmp) {
                        document.querySelector(".dialogFront").outerHTML = tmp;
                        var dialog = new vBase.Dialog();
                        dialog.show();
                        dialog.onHide = function () {
                            console.log("players dialog hide");
                        }
                    }
                );
            });
            $('.ui_btn_boss').click(function () {
                require(['text!../../html/Dialog/Banker.html', 'css!../css/Dialog.css'],
                    function (tmp) {
                        document.querySelector(".dialogFront").outerHTML = tmp;
                        var dialog = new vBase.Dialog();
                        dialog.show();
                        var slider = new vBase.Slider('btn','bar', 'step', 'btnTip', function (percent) {
                            console.log(Math.round(8888888*percent));//根据已改变的滑动条的百分比设置金额
                        },33);//默认33%,没有则为0
                        dialog.onHide = function () {
                            console.log("players dialog hide");
                        }
                    }
                );
            });
            //把所有隐藏的DIV显示出来
            // var ds=document.getElementsByTagName("div");for(var i in ds)ds[i].style.display='block';
        }
    );
});
// require(['text!../../html/Bull100BigSmall/Bull100BigSmall.html','css!../css/Bull100BigSmall.css'],

/**
 *
 */
// define(['director'], function (director) {
//     var StringRouter = {
//         init : function () {
//             var routes = {
//                 "/bull100":
//             };
//             var router = director(routes);
//             router.init();
//         }
//     }
//     return StringRouter;
// });