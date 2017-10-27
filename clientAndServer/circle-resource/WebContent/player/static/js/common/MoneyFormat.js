// 金币从number转化为保留4位数字和小数点的字符串, 单位无\万\亿
define([],function () {
    var MoneyFormat = {
        format: function (money) {
            var zeroCnt = 0, nega = "";//带多少个0，负数标志位
            if (money < 0) {
                money = -money;
                nega = "-";
            }
            var tmp = money;
            while ((tmp = tmp / 10) >= 1)zeroCnt++;
            if (zeroCnt <= 3)return nega + money;
            for (var i = 0, tmp = 1; i < zeroCnt - 3; i++) {
                money /= 10;
                tmp = tmp == 10000 ? 10 : tmp * 10;
            }
            return nega + (parseInt(money) / (10000 / tmp)) + (zeroCnt < 8 ? "万" : (zeroCnt < 12 ? "亿" : "兆"));
        },
        formatBy1000: function (money) {
            return (""+money).replace(/(\d{1,3})(?=(\d{3})+(?:$|\D))/g,"$1,");
        }
    };
   return MoneyFormat;
});
