<%-- 欢乐牛牛 老主页 --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
    <%--开发阶段禁止缓存--%>
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />

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
    <%--   <meta name="screen-orientation" content="landscape"/>
       <meta name="x5-orientation" content="landscape">
        <meta name="x5-page-mode" content="app">--%>

    <!--fix fireball/issues/3568 -->
    <!--<meta name="browsermode" content="application">-->
    <%@ include file="/include/include.inc.jsp" %>
    <%@ include file="/include/include.head.jsp" %>
    <link rel="stylesheet" type="text/css" href="static/css/bull/style-mobile.css"/>
    <title>${siteName}</title>
</head>
<body>

<div class="container"></div>
<div id="maskDiv"></div>
<!--竟庄提示区域-->
<div id="systemTip">
    <span id="tipContent">有玩家竞庄，您目前的竟排位：</span><span id="tipNum">5</span>
</div>
<!--加载数据动画-->
<div id="dialogLoading" class="dialogLoading">
    <div class="sk-circle">
        <div class="sk-circle1 sk-child"></div>
        <div class="sk-circle2 sk-child"></div>
        <div class="sk-circle3 sk-child"></div>
        <div class="sk-circle4 sk-child"></div>
        <div class="sk-circle5 sk-child"></div>
        <div class="sk-circle6 sk-child"></div>
        <div class="sk-circle7 sk-child"></div>
        <div class="sk-circle8 sk-child"></div>
        <div class="sk-circle9 sk-child"></div>
        <div class="sk-circle10 sk-child"></div>
        <div class="sk-circle11 sk-child"></div>
        <div class="sk-circle12 sk-child"></div>
    </div>
</div>
<div class="dialogMain">
    <div class="dialogBg"></div>
    <div class="dialogFront"></div>
</div>
<div class="msgDialogMain">
    <div class="dialogBg"></div>
    <div class="msgDialogFront">
        <div id="msgDialog" class="dia_small">
            <div class="Prompt_bg1">
                <div class="Prompt_bt_bg">
                    <div class="cow_head"></div>
                </div>
                <div class="Dialog_bg1 Prompt_tc_bg1"></div>
                <div class="Dialog_bg2">
                    <p class="Prompt_content">你确定要退出游戏？</p>
                </div>
                <div class="Dialog_bg3 Prompt_tc_bg1">
                    <div class="Prompt_tc_btn">
                        <button class="Prompt_exit">
                            <p> 确定退出</p>
                        </button>
                        <button class="Prompt_wrong">
                            <p>我点错了</p>
                        </button>
                    </div>
                </div>
                <!--<button class="Prompt_close"></button>-->
            </div>
        </div>
    </div>
</div>
<div id="coinMain"><!--总共初始化游戏里最多256个金币-->
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
    <div class="coin"></div>
    <div class="coin"></div>
    <div class="coin"></div>
    <div class="coin"></div>
    <div class="coin"></div>
    <div class="coin"></div>
    <div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div><div class="coin"></div>
</div>
<div id="debug" onclick="this.style.display='none'" style="display:none;position:fixed;top:0;left:0;width:100%;height:100%; z-index: 9999;opacity: 0.65;">
    <textarea disabled style="font-size: 14px;position: absolute;bottom: 0;right: 0;width: 50%;height: 30%;background-color: black;color: wheat;"></textarea></div>
<script>
    if(!!navigator.userAgent.match(/AppleWebKit.*Mobile.*/)){
        console.debug=function(xx){
            $("#debug").show();
            var dbg = $("#debug textarea");
            dbg.val(dbg.val()+"\n"+xx);
            dbg.scrollTop(dbg[0].scrollHeight);
        }
    }
</script>
<%--<input id="init_button" type="button" value="清理">--%>
<%--<a href="#/login">游戏引擎登录</a>--%>
<%@ include file="/include/include.js.jsp" %>
</body>
</html>
