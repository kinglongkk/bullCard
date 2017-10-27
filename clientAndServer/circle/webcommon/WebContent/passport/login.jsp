<%@ page import="org.soul.commons.lang.string.I18nTool" %>
<%@ page import="g.web.common.SessionManagerCommon" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <%@ include file="/include/include.inc.jsp" %>
  <%@ include file="/include/include.head.jsp" %>
    <title>${siteName}</title>

</head>
<body class="login">
<form action="${root}${requestScope.loginUrl}" method="post">
<div id="preview">
    <div id="preview-container">
    <div class="login-logo">Game<span class="fs24 m-l-sm">${requestScope.entranceDesc}</span></div>
    <div class="login-bl">

            <input type="hidden" name="url" value="${root}/"/>
            <div class="clearfix">
                <span class="fs24 m-b-sm pull-left">登录</span>
                <span class="pull-right line-hi34">
                <a href="<%--login-retrieve-password.html--%>javascript:void(0)">忘记密码</a>
                </span>
            </div>
            <div class="form-group">
                <div class="ico-left"><em class="fa fa-user"></em></div>
                <input type="text" name="username" value="${sessionScope.SK_Passport_Rs.username}" class="form-control" placeholder="用户名" autocomplete="off" >
            </div>
            <div class="form-group">
                ${SK_Passport_Rs.propMessages["username"]}
            </div>
            <div class="form-group">
                <div class="ico-left"><em class="fa fa-unlock-alt"></em></div>
                <input name="password" type="password" class="form-control" placeholder="密码" value="${sessionScope.SK_Passport_Rs.password}" autocomplete="off">
            </div>
            <div class="form-group">
                ${sessionScope.SK_Passport_Rs.propMessages["password"]}
            </div>
                <c:if test="${sessionScope.SK_Passport_Rs.isOpenCaptcha}">
                    <div class="form-group clearfix ">
                        <div class="input-group date">
                          <span class="input-group-addon abroder-no"><b>验证：</b></span>
                          <input type="text" name="captcha" class="form-control" placeholder="请输入验证码" value="">
                          <span class="verify-img"><img class="captcha-code" src="${root}/captcha/code.html"></span>
                          <span class="input-group-addon abroder-no"><soul:button target="changeCode" tag="a" text="" opType="function">换一张</soul:button></span>
                        </div>
                    </div>
                </c:if>
            <div class="form-group">
                ${sessionScope.SK_Passport_Rs.propMessages["captcha"]}${sessionScope.SK_Passport_Rs.message}
            </div>
            <div class="login-btn-1"><button type="submit" class="btn btn-filter full-width btn-lg btn-block">立即登录</button></div>

    </div>
        <div class="login-footer"><span class="m-l-sm">${views.platform['copyright']}</span>
        </div>

    </div>

</div>
    <input type="hidden" name="subsysCode" value="${subsysCode}">
<div class="hint-box" style="display: none" >
    <span class="hint-content">
        <i class="fa fa-exclamation-circle"></i>
        ${views.privilege[subsysCode]}
        <soul:button target="closeHint" cssClass="close" tag="button" text="" opType="function">
            <span aria-hidden="true">×</span>
            <span class="sr-only">关闭</span>
        </soul:button>
        <%--<button type="button" class="close" data-dismiss="modal">--%>
        <%--</button>--%>
    </span>
</div>
</form>
<script type="text/javascript">
    var language = '${language.replace('_','-')}';
    var message = <%=I18nTool.getScriptMessageObject(SessionManagerCommon.getLocale().toString())%>;
</script>
<%@ include file="/include/include.js.jsp" %>
<script>
    curl(['gb/home/TopPage','common/Login'], function(TopPage,Page) {
        topPage = new TopPage();
        page = new Page();
        page.bindButtonEvents();
    });
</script>

</body>
</html>