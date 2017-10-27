define([],
    function () {

    return Class.extend({

        defered : null,

        /**
         * 构造方法
         */
        init : function () {
            this.defered = new $.Deferred();
        },

        doFilter : function () {
            return this.defered;
        }

    });
});