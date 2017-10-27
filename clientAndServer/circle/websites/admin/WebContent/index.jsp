<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>首页 - 管理员</title>
    <%@ include file="/include/include.head.jsp" %>
    <!-- Toastr style -->
    <link href="${resComRoot}/themes/${curTheme}/toastr/toastr.min.css" rel="stylesheet">
    <!-- Gritter -->
    <link href="${resComRoot}/themes/${curTheme}/gritter/jquery.gritter.css" rel="stylesheet">
    <link href="${resComRoot}/themes/${curTheme}/iconfont.css" rel="stylesheet">
    <link href="${resComRoot}/themes/${curTheme}/iCheck/custom.css" rel="stylesheet">
    <link href="${resComRoot}/themes/${curTheme}/chosen/chosen.css" rel="stylesheet">
    <link href="${resComRoot}/themes/${curTheme}/nouslider/jquery.nouislider.css" rel="stylesheet">
    <link href="${resComRoot}/themes/${curTheme}/banner/elastislide.css" rel="stylesheet"/>
    <link href="${resComRoot}/themes/${curTheme}/switchery/switchery.css" rel="stylesheet">
    <link href="${resComRoot}/themes/${curTheme}/cropper/cropper.min.css" rel="stylesheet">
    <link href="${resComRoot}/themes/${curTheme}/chosen/chosen.css" rel="stylesheet">

    <style type="text/css">
        .carousel-fill{
            background-position:center;
            text-align:center;
        }
        .carousel-fill img{
            /*设置图片垂直居中*/
            vertical-align:middle;
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
        curl([ 'gb/home/TopPage', 'gb/home/TopNav', 'gb/home/LeftNav', 'common/Passport','site/index/Index','jqmetisMenu', 'jqnouislider'],
                function (TopPage, TopNav, LeftNav, Passport,Index) {
            topPage = new TopPage();
            topNav = new TopNav();
            leftNav = new LeftNav();
            passport = new Passport();
            index = new Index();


        });
    </script>
    <script id="topMenuTmpl" type="text/x-jsrender">
        {{for m}}
            <li id="menuItem{{:object.id}}">
                <a nav-top='page-content' {{if object.resourceUrl == '' || object.resourceUrl == null}} href='/index/content.html?parentId={{:object.id}}' {{else}} href='/{{: object.resourceUrl}}' {{/if}} ><i class="fa {{:object.resourceIcon}}"></i>{{:object.resourceName}}</a>
            </li>
        {{/for}}
    </script>
</head>
<body oncontextmenu="return true" class="background-gray">
<input id="domainTemp" type="hidden" value="${domainTemp}">
<div id="newMessageDIV" style="display:none"></div>
    <div class="top row top-navigation border-bottom white-bg clearfix header">
        <nav class="navbar navbar-static-top" role="navigation">
        <div class="pull-left logo-m-l line-hi60">
            <a href="javascript:void(0)"><span class="m-l-sm m-r-sm fs18">${views.platform['code']}</span>
                <span class="line">|</span>
                <span href="javascript:void(0)" class="m-l-sm fs16">${views.app['name']}</span></a>
        </div>
        <div class="navbar-header">
                <button aria-controls="navbar" aria-expanded="false" data-target="#navbar" data-toggle="collapse" class="navbar-toggle collapsed" type="button">
                    <span class="sr-only">导航栏</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
        </div>
            <div class="navbar-collapse collapse" id="navbar" aria-expanded="false">
        <ul class="nav navbar-nav navbar-left">

        </ul>
        <div class="navbar-nav-up">
           <a class="fa fa-angle-up" data-target="#navbar" data-toggle="collapse" type="button" aria-expanded="true"></a>
        </div>
         </div>
        <div class="pull-right top-account-menu">
            <ul class="tips">




                <li class="clock top-time open"><a href="../../icon/clock-o" data-toggle="dropdown" class="clock-icon-show top-time-ico" aria-expanded="true"><i class="icon iconfont"></i></a>
                    <span id="userTime">${views.home['home.userTimeZone']}：<%= SessionManager.getTimeZone().getID() %></span>
                    <span id="index-clock" class="clock-show"> </span>
                </li>

                <li class="infos show-info">
                    <a data-toggle="dropdown" style="min-width: 100px">
                        <img alt="image" class="img-circle" src="${soulFn:getThumbPath(domain, sysUser.avatarUrl,50,25)}"><%=SessionManager.getUserName()%><i
                            class="fa fa-angle-down"></i></a>
                    <ul class="information nav-shadow">
                        <li><a href="/sys/account/index.html"  nav-target="mainFrame"><i class="fa fa-user m-r-sm"></i>个人资料</a></li>
                        <li><a href="/sys/account/index.html"  nav-target="mainFrame"><i class="fa fa-key m-r-sm"></i>更改密码</a></li>
                        <li class="off"><a href="javascript:void(0)">退出 <i class="fa fa-power-off"></i></a></li>
                    </ul>
                </li>

                <li class="informations">
                    <a href="javascript:void(0)" class="locate" data-toggle="dropdown"
                       data-href="${root}/index/message.html">
                        <i class="icon iconfont"></i><span class="label label-green" id="unReadCount">${empty unReadCount?0:unReadCount}</span>
                    </a>
                    <dl class="infos_list nav-shadow">
                        <dt>系统消息</dt>
                        <dd class="infos-load">
                            <img src="${resRoot}/images/022b.gif">
                        </dd>
                        <dd class="infos-none"><i class="fa fa-exclamation-circle"></i>暂无消息</dd>
                    </dl>
                </li>
                <li class="tasks">
                    <a href="javascript:void(0)" class="locate" data-href="${root}/index/task.html" data-toggle="dropdown" aria-expanded="false"><i class="icon iconfont"></i><span class="label label-orange" id="unReadTaskCount">${empty unReadTaskCount?0:unReadTaskCount}</span></a>
                    <dl class="infos_list tasks_list nav-shadow">
                        <dt>任务提醒</dt>
                        <dd class="infos-load">
                            <img src="${resRoot}/images/022b.gif">
                        </dd>
                    </dl>
                </li>
            </ul>
        </div>
        </nav>
    </div>
    <div id="page-content" style="min-height: 800px;">

    </div>
    <div class="footer">
        <div class="pull-right"> <div id="divLanguage" class="btn-group dropup">
            <button type="button" class="btn btn-outline btn-filter dropdown-toggle language-btn m-sm" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"></button>

            <ul class="dropdown-menu dropdown-menu-right m-sm m-b-none"></ul>
        </div></div>
        <div class="a-copy clearfix">
            <div class="pull-left"><a href="javascript:void(0)">${views.home['home.contact.Customer']}</a><span class="dividing-line m-r-xs m-l-xs">|</span>
                <a href="javascript:void(0)" id="feedback">${views.home['home.feedBack']}</a>
        </div>
            <span class="m-l-sm pull-left">${views.platform['copyright']}</span> <span class="htm-5-logo" type="button" data-toggle="tooltip" data-placement="top" title="" data-original-title="我们项目使用html5规范开发，可提供您在所有设备上更加好的体验。"></span></div>
    </div>
    <div class="preloader"/>
</body>

</html>

