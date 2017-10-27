<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/include/include.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">

    <title>${siteName}</title>

    <!--http://www.html5rocks.com/en/mobile/mobifying/-->
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1, minimum-scale=1, maximum-scale=1, minimal-ui"/>

    <!--https://developer.apple.com/library/safari/documentation/AppleApplications/Reference/SafariHTMLRef/Articles/MetaTags.html-->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    <meta name="format-detection" content="telephone=no">

    <!-- force webkit on 360 -->
    <meta name="renderer" content="webkit"/>
    <meta name="force-rendering" content="webkit"/>
    <!-- force edge on IE -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="msapplication-tap-highlight" content="no">

    <!-- force full screen on some browser -->
    <meta name="full-screen" content="yes"/>
    <meta name="x5-fullscreen" content="true"/>
    <meta name="360-fullscreen" content="true"/>

    <!-- force screen orientation on some browser -->
    <meta name="screen-orientation" content="landscape"/>
    <meta name="x5-orientation" content="landscape">

    <!--fix fireball/issues/3568 -->
    <!--<meta name="browsermode" content="application">-->
    <meta name="x5-page-mode" content="app">

    <!--<link rel="apple-touch-icon" href=".png" />-->
    <!--<link rel="apple-touch-icon-precomposed" href=".png" />-->

    <link rel="stylesheet" type="text/css" href="${resRoot}/css/bull/style-mobile.css"/>
    <%@ include file="/include/include.js.jsp" %>
    <%--<script>--%>
        <%--(function (doc, win) {--%>
            <%--var docEl = doc.documentElement,--%>
                    <%--resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',--%>
                    <%--recalc = function () {--%>
                        <%--var clientWidth = docEl.clientWidth;--%>
                        <%--if (!clientWidth) return;--%>
                        <%--docEl.style.fontSize = 100 * (clientWidth  / 1280) + 'px';--%>
                    <%--};--%>

            <%--if (!doc.addEventListener) return;--%>
            <%--win.addEventListener(resizeEvt, recalc, false);--%>
            <%--doc.addEventListener('DOMContentLoaded', recalc, false);--%>
        <%--})(document, window);--%>
    <%--</script>--%>
</head>
<body>
<canvas id="GameCanvas" oncontextmenu="event.preventDefault()" tabindex="0"></canvas>
<div id="splash">
    <%--<div class="plumBG"></div>--%>
    <%--<div class="plumBG-full"></div>--%>
    <%--<div class="plumBG-fulling"></div>--%>
    <%--<div class="progress-bar stripes">--%>
        <%--<span style="width: 0%"></span>--%>
    <%--</div>--%>
    <%--<div class="G_loading"></div>--%>
    <%--<div class="Animation_loading_prompt">--%>
        <%--五张牌的三张加起来是十的倍数就有牛--%>
    <%--</div>--%>
</div>
<%--<script type="text/javascript">--%>
    <%--var lanscaped =function(isLanscape){--%>
        <%--var hw = window.innerWidth;--%>
        <%--var hh = window.innerHeight;--%>
        <%--contain.style.width = (isLanscape?hh:hw) + 'px';--%>
        <%--contain .style.height = (isLanscape?hw:hh)+'px';--%>
        <%--contain.style["margin-top"] = (isLanscape?(hh-hw)/2 : 0) + 'px';--%>
        <%--contain.style["margin-left"] = (isLanscape?-(hh-hw)/2 : 0)+'px';--%>
        <%--contain.style.webkitTransform = contain .style.transform = (isLanscape?'rotateZ(90deg)':'');--%>
    <%--}--%>
    <%--var checSplash = function(){--%>
        <%--contain = document.getElementById('splash');--%>
        <%--var isLanscape = window.orientation==180||window.orientation==0;--%>
        <%--window.addEventListener("onorientationchange" in window ? "orientationchange" : "resize", function(){--%>
            <%--lanscaped(window.orientation==180||window.orientation==0);--%>
        <%--}, false);--%>
        <%--if(isLanscape){--%>
            <%--lanscaped(true);--%>
        <%--}else{--%>
        <%--}--%>
    <%--}--%>
    <%--checSplash();--%>
<%--</script>--%>
<%--<script src="${resRoot}/js/bull/settings.js" charset="utf-8"></script>--%>
<%--<script src="${resRoot}/js/bull/main.js" charset="utf-8"></script>--%>

<script>
    var browser = {
        versions : function() {
            var u = navigator.userAgent, app = navigator.appVersion;
            return {//移动终端浏览器版本信息
                trident : u.indexOf('Trident') > -1, //IE内核
                presto : u.indexOf('Presto') > -1, //opera内核
                webKit : u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
                gecko : u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
                mobile : u.indexOf('Chrome') > -1 ? u.indexOf('Windows') <= -1 : !!u.match(/AppleWebKit.*Mobile.*/)
                    || !!u.match(/AppleWebKit/), //是否为移动终端
                ios : !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                android : u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
                iPhone : u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
                iPad: u.indexOf('iPad') > -1, //是否iPad
                webApp : u.indexOf('Safari') == -1,//是否web应该程序，没有头部与底部
                google:u.indexOf('Chrome')>-1
            };
        }(),
        language : (navigator.browserLanguage || navigator.language).toLowerCase()
    }
    var isPC = !browser.versions.mobile;
    window.G_urlEX = {};
    G_urlEX.url_root = 'static/';
    if(isPC){
        G_urlEX.url_js = 'js/bull_PC/';
        G_urlEX.url_image = 'images/bull_PC/'
    }else{
        G_urlEX.url_js = 'js/bull/';
        G_urlEX.url_image = 'images/bull/'
    }

    var myScript= document.createElement("script");
    myScript.src = '${resRoot}/'+G_urlEX.url_js+'settings.js';
    myScript.charset = "utf-8";
    document.body.appendChild(myScript);

    window.onload = function(){
        var myScript2= document.createElement("script");
        myScript2.src = '${resRoot}/'+G_urlEX.url_js+'main.js';
        myScript2.charset = "utf-8";
        document.body.appendChild(myScript2);
    }
</script>

</body>
</html>
