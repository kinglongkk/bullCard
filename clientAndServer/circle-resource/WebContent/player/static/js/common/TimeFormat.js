/**
 * Created by black on 2016/10/25.
 * 格式化时间
 */
define([],function () {

    var TimeFormat = {
        format: function(format) {
            var each = {
                "M+": this.getMonth() + 1, // 月份
                "d+": this.getDate(), // 日
                "h+": this.getHours(), // 小时
                "m+": this.getMinutes(), // 分
                "s+": this.getSeconds(), // 秒
                "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
                "S": this.getMilliseconds()// 毫秒
            };
            if (/(y+)/.test(format))
                format = format.replace(RegExp.$1, (this.getFullYear() + "")
                    .substr(4 - RegExp.$1.length));
            for (var index in each)
                if (new RegExp("(" + index + ")").test(format))
                    format = format.replace(RegExp.$1, (RegExp.$1.length == 1) ? (each[index])
                        : (("00" + each[index]).substr(("" + each[index]).length)));
            return format;
        }
    };
    return TimeFormat;
});