<%--@elvariable id="domain" type="g.model.company.sys.po.VSysSiteDomain"--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <%@ include file="/include/include.inc.jsp" %>
    <%@ include file="/include/include.head.jsp" %>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>Under Maintenance | 網站維護中</title>
    <link rel="stylesheet" href="${resComRoot}/themes/default/bootstrap/bootstrap.min.css" type="text/css" />
    <link rel="stylesheet" href="${resComRoot}/themes/error.css" type="text/css" />
    <style>

    </style>
</head>

<body class="bluebg">
<div class="mainbg vertical" style="min-height: 600px;">
<%--    <input type="hidden" name="startTime" value="${domain.maintainStartTime}">
    <input type="hidden" name="endTime" value="${domain.maintainEndTime}">--%>
    <div class="container">
        <div class="row">
            <div class="col-sm-12 text-2x text-center text-white">
                <p class="m-t"><img src="${resComRoot}/images/errors/ico-607.png" alt=""></p>
                <h1 class="text-shadow no-margin p-v-lg"> <span class="text-2x font-bold m-t-lg">Under Maintenance</span><p>網站維護中</p> </h1>
                <h4 class="font-bold m-v-md">We're doing our best and we'll be back in:</h4>
                <h4 class="font-bold m-v-md">我們正在進行例行維護，並儘快恢復服務，預計於以下時間完成：</h4>
            </div>
        </div>
        <div class="row time-item">
            <div class="col-xs-4">
                <div class="hour">23</div>
                <p class="font-bold text-white">Hours</p>
            </div>
            <div class="col-xs-4">
                <div class="min">40</div>
                <p class="font-bold text-white">Minutes</p>
            </div>
            <div class="col-xs-4">
                <div class="sec">59</div>
                <p class="font-bold text-white">Seconds</p>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var language = '${language.replace('_','-')}';
    var message = <%=I18nTool.getScriptMessageObject(SessionManager.getLocale().toString())%>;
    //    mcenter专用
    var entrance = '${SESSION_KEY_ENTRANCE}';
</script>
<%@ include file="/include/include.js.jsp" %>
<script>
    curl(['gb/home/TopPage','common/errors/607'], function(TopPage,Page) {
        topPage = new TopPage();
        page = new Page();
    });
</script>
</body>


</html>

