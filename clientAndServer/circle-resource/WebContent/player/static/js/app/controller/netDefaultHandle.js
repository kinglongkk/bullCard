define(['view/viewBase'],function (vBase) {
    var handle = new Object();
    handle.Nb2 = function(nb1){
      console.log(nb1.b1+","+nb1.b2);
    }

    /**
     * 返回消息
     * @constructor
     */
    handle.NbTip = function (nbTip) {
        console.info("返回消息:" + nbTip.code + "," + nbTip.tip)
        if (nbTip.code === '10001') {
            vBase.msgDialog.show(nbTip.tip, "重新登录", "取消", function () {
                window.login.loginToken();
            });
        }

    }

    return handle;
})