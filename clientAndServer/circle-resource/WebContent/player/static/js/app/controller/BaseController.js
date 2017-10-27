define(['ClassTool'], function (Class) {

    return Class.extend({

        backUrl : "",

        /**
         * 构造方法
         */
        init : function (param) {
            this.bindEvent();
        },

        /**
         * 绑定事件
         */
        bindEvent : function () {
            var _this = this;
            //全局回退按钮事件
            $(".container").on("click",".GB_back",function () {
                window.location.href = "#" + _this.backUrl;
            })
        },

        /**
         * 回到上一个页面
         */
        setBackUrl : function (backUrl) {
            this.backUrl = backUrl;
        }
    });
});