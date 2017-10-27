<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>首页 - 代理中心 - ${siteName}
    </title>
    <%@ include file="/include/include.head.jsp" %>
    <!-- Toastr style -->
    <link href="${resComRoot}/themes/${curTheme}/toastr/toastr.min.css" rel="stylesheet">
    <!-- Gritter -->
    <link href="${resComRoot}/themes/${curTheme}/gritter/jquery.gritter.css" rel="stylesheet">
    <link href="${resComRoot}/themes/${curTheme}/iconfont.css" rel="stylesheet">
    <%--<link href="${resComRoot}/themes/${curTheme}/iCheck/custom.css" rel="stylesheet">--%>
    <link href="${resComRoot}/themes/${curTheme}/chosen/chosen.css" rel="stylesheet">
    <link href="${resComRoot}/themes/${curTheme}/nouslider/jquery.nouislider.css" rel="stylesheet">
    <link href="${resComRoot}/themes/${curTheme}/banner/elastislide.css" rel="stylesheet"/>
    <link href="${resComRoot}/themes/${curTheme}/switchery/switchery.css" rel="stylesheet">
    <link href="${resComRoot}/themes/${curTheme}/cropper/cropper.min.css" rel="stylesheet">
    <%--<link href="${resComRoot}/themes/${curTheme}/bootstrap-checkbox/bootstrap-checkbox.css" rel="stylesheet">--%>
    <link href="${resComRoot}/themes/${curTheme}/chosen/chosen.css" rel="stylesheet">
    <style type="text/css">
        .carousel-fill {
            background-position: center;
            text-align: center;
        }

        .carousel-fill img {
            /*设置图片垂直居中*/
            vertical-align: middle;
            max-width: 100%;
            max-height: 100%;
        }

        /* faster sliding speed */
        .carousel-inner > .item {
            -webkit-transition: 0.3s ease-in-out left;
            -moz-transition: 0.3s ease-in-out left;
            -o-transition: 0.3s ease-in-out left;
            transition: 0.3s ease-in-out left;
        }
    </style>
    <script type="text/javascript">
        var language = '${language.replace('_','-')}';
        var message = <%=I18nTool.getScriptMessageObject(SessionManager.getLocale().toString())%>;
    </script>
    <%@ include file="/include/include.js.jsp" %>
    <script type="text/javascript">
        curl(['gb/home/TopPage','site/home/TopNav', 'site/home/LeftNav',
                   'common/Passport', 'jqmetisMenu', 'jqnouislider'],
                function (TopPage, TopNav, LeftNav, Passport) {
                    topPage = new TopPage();
                    topNav = new TopNav();
                    leftNav = new LeftNav();
                    passport = new Passport();
                });
    </script>
    <script id="topMenuTmpl" type="text/x-jsrender">
        {{for m}}
            <li id="menuItem{{:object.id}}"　class="dropdown">
                <a aria-expanded="false" role="button" nav-top='page-content' {{if object.resourceUrl == ''}} href='/index/content.html?parentId={{:object.id}}' {{else}} href='{{: object.resourceUrl}}' {{/if}} ><div class="icon"><i class="icon iconfont">{{:object.resourceIcon}}</i></div><span>{{:object.resourceName}}</span></a>
            </li>
        {{/for}}
    </script>
</head>
<body oncontextmenu="return true" onselectstart="return false" ondragstart="return false" onbeforecopy="return false" class="background-gray">
<div class="top header top-navigation border-bottom white-bg">
    <nav class="navbar navbar-static-top" role="navigation">
        <div class="pull-left logo-m-l line-hi60">
        <a href="javascript:void(0)"
           class="m-l-sm m-r-sm fs18">${views.platform['code']}
        </a>

        <a href="javascript:void(0)" class="m-l-sm fs16">${views.app['name']}</a>


    </div>
        <div class="pull-left m-l-sm clock top-time line-hi60">
        <a href="javascript:void(0)" class="clock-icon"><i class="icon iconfont"></i></a>
        <span id="userTime">当前时区：<%= SessionManager.getTimeZone().getID() %></span>
        <span id="index-clock" class="clock-show"> </span>
        </div>
    <div class="pull-right top-account-menu">
        <ul class="tips">
            <li class="infos show-info">
                <a data-toggle="dropdown">
                    <img alt="image" class="img-circle" src="${resRoot}/images/profile_small.jpg"><%=SessionManager.getUserName()%><i
                        class="fa fa-angle-down"></i></a>
                <ul class="information nav-shadow">
                    <li><a href="/agentAccount/myAccount.html" nav-target="mainFrame"><i class="fa fa-user"></i> 个人资料</a></li>
                    <%--<li><a href="/agentAccount/myAccount.html" nav-target="mainFrame"><i class="fa fa-file-excel-o"></i> 登录日志</a></li>--%>
                    <li><a href="/agentAccount/myAccount.html" nav-target="mainFrame"><i class="fa fa-key"></i>更改密码</a></li>
                    <li class="off"><a href="javascript:void(0);">退出 <i class="fa fa-power-off"></i></a></li>
                </ul>
            </li>
            <li class="informations">
                <div id="msg_music"></div>
                <input id="isReminderMsg" type="hidden" value="${isReminderMsg}"/>
                <a href="javascript:void(0)" class="locate" data-toggle="dropdown"
                   data-href="${root}/index/message.html">
                    <i class="icon iconfont"></i><span class="label label-green">${unReadMsgCount}</span>
                </a>
                <dl class="infos_list nav-shadow">
                    <dt>系统消息</dt>
                    <dd class="infos-load">
                        <img src="${resRoot}/images/022b.gif">
                    </dd>
                </dl>
            </li>
            <li class="infos show-info">
                <a data-toggle="dropdown" class="language"></a>
                <ul class="language nav-shadow"></ul>
            </li>
        </ul>
    </div>
    </nav>
</div>
<div id="page-content"  style="min-height: 800px;">

</div>
<div class="footer">
    <div class="pull-right">
        <div class="btn-group dropup" id="divLanguage">
            <button type="button" class="btn btn-outline btn-filter dropdown-toggle language-btn m-sm"
                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></button>
            <ul class="dropdown-menu dropdown-menu-right m-sm m-b-none"></ul>
        </div>
    </div>
    <div class="a-copy clearfix">
        <div class="pull-left"><a href="javascript:void(0)">联系客服</a><span class="dividing-line m-r-xs m-l-xs">|</span>
            <a href="javascript:void(0)" id="feedback">意见反馈</a>
        </div>
        <span class="m-l-sm pull-left">${views.home['app.copyright']}</span> <span class="htm-5-logo" type="button"
                                                                                   data-toggle="tooltip"
                                                                                   data-placement="top" title=""
                                                                                   data-original-title="我们项目使用html5规范开发，可提供您在所有设备上更加好的体验。"></span>
    </div>
</div>
<div class="preloader"></div>
</body>

</html>

