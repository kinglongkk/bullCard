requirejs.config({
    baseUrl : resComRoot+'/js',
    packages : [
        { name     : "site",
          location : resRoot+"/js"
        },
        { name     : "app",
          location : resRoot+"/js/app"
        },
        { name     : "bull100",
          location : resRoot+"/js/app/controller/bull100"
        },
        { name     : "view",
          location : resRoot+"/js/app/view"
        },
        { name     : "test",
          location : resRoot+"/js/test"
        },
    ],
    paths : {
        gm:resRoot+'/js/app/controller/gameMain',
        ClassTool: 'circle/common/ClassTool',
        text: 'RequireJS-2.3.2/text',
        css: 'RequireJS-2.3.2/css',
        jquery : 'jquery/jquery-1.11.1.min',
        jsrender : 'jsrender/jsrender',
        director : 'director/director-1.2.6',
        Velocity : 'velocity/velocity'
    },
    shim : {
        jsrender : {
            //tobe jquery plugin
            deps : ['jquery'],
            exports: '$.fn.render'
        },
        director : {
            exports: 'Router'
        }
    }
});

$(document).ready(function () {
    $(".msgDialogMain").css({display: "none", top: 0});
});

function initIndex () {
    //过滤console.dir输出
    var cdir = console.dir;
    console.dir = function (obj) {
        if(obj.constructor.className == "NbStartMatchOut"){
            cdir(obj);
        }
    }

    window.onerror = function(errorMessage, scriptURI, lineNumber,columnNumber,error) {
        if(scriptURI.length == 0) return;//屏蔽小米5下搜狗浏览器报错:SeMobFillFormTool.fillFrom is not a function
        console.debug(scriptURI+" :"+lineNumber+", "+columnNumber +", "+ errorMessage+": "+error);
    }

    requirejs(['ClassTool'
            ,'app/controller/gameMain'
            ,'app/controller/Main'
            ,'app/controller/Login'
            ,'app/controller/Index'
            ,'app/controller/Converters'
            ,'app/controller/Helpers'
            ,'bull100/handleBull'
        ],
        function(Class,gm,Main,Login,Index) {
            var main = new Main();
            login = new Login();    //全局变量，可重复登录请求
            var index = new Index();
            //TODO:Double for test
            window.gm = gm;
            gm.reConnect();
        }
    );
}
initIndex();


//初始化声音
requirejs(["app/view/soundBase",'Velocity'], function(sBase,velocity){
    //由于出现小米5系统内核浏览器也不能自动播放声音,所以统一改成第一次触摸开始加载声音
    // if(/iphone|ipad|ipod/.test(navigator.userAgent.toLowerCase())){//苹果移动设备
        var eventName = "ontouchend" in document ? "touchstart" : "mousedown";
        var event = function (e) {
            document.body.removeEventListener(eventName, event);
            sBase.init(resRoot+"/sounds/", "ingameBGMMono");
        };
        document.body.addEventListener(eventName, event);
    // }else{//其他设备
    //     sBase.init(resRoot+"/sounds/", "ingameBGMMono");
    // }
});
//记录所有加载的files
var all=[];
requirejs.onResourceLoad = function (context, map, depArray) {
    all.push(map.name);
};

//清理依赖
function end(){
    console.log("--------------------------- END requirejs:");
    all.map(function(item){
        requirejs.undef(item);
    });
    all = [];
};


$('#init_button').on('click', function(){
    end();
});

