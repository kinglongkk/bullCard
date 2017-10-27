/**
 *
 */
define(['director','app/route/RouteConfig'], function (director,routeConfig) {

    function initController(item,params, filterArgs) {
        var url = item['url'];
        var controller = item['controller'];
        requirejs([controller], function (Controller) {
            if (Controller == undefined) {
                console.error("无效的控制器地址:" + url + "," + controller);
                return;
            }
            //TODO:
            var c = new Controller(params, filterArgs);
        });
    }

    var StringRouter = {
        init : function () {
            var routes = {};
            //用于把字符串转化为一个函数，而这个也是[路由]的处理核心

            var routeHandler = function (item) {
                return function () {
                    var url = item['url'];
                    var filter = item['filter'];
                    var params = arguments;
                    console.info("[路由]:进入" + url);

                    if (filter != '') {
                        console.info("[路由]:进入" + url + ",加载过滤器:" + filter);
                        requirejs([filter], function (Filter){
                            var f = new Filter(params);
                            f.doFilter()
                            .done(function (filterArg) {
                                console.info("[路由]:进入" + url + ",过滤器:" + filter + ",返回成功!");
                                initController(item,params,arguments)
                            })
                            .fail(function () {
                                console.info("[路由]:进入" + url + ",过滤器:" + filter + ",返回失败!");
                                window.history.back();
                            });
                        });
                    } else {
                        initController(item,params);
                    }
                }
            };

            for (var i = 0; i < routeConfig.length; i++) {
                var item = routeConfig[i];
                routes[item['url']] = routeHandler(item);
            }

            var router = director(routes);
            router.init();
        }
    }
    return StringRouter;
});