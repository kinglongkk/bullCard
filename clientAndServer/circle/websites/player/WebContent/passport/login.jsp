<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
    <%@ include file="/include/include.inc.jsp" %>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="Edge"/>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta content="telephone=no" name="format-detection">
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>${sessionScope.S_SYS_SITE_PO.name}</title>
    <link rel="stylesheet" type="text/css" href="${resRoot}/css/login.css"/>
    <title>${siteName}</title
</head>
<body onkeydown="enterLogin();">
<form id="loginForm" method="post" action="${root}/passport/login.html">
    <div class="container">
        <div class="login_bg">
            <div class="ui-contianer-login">
                <div class="login_list">
                    <ul>
                        <li>
                            <div class="login-account"></div>
                            <div class="ui-login-acount">
                                <input type="text" name="username" placeholder="请输入用户名"
                                       value="${sessionScope.SK_Passport_Rs.username}"/>
                            </div>
                        </li>
                        <li>
                            <div class="login-password"></div>
                            <div class="ui-login-acount">
                                <input type="password" name="password" placeholder="请输入密码"/>
                            </div>
                        </li>
                        <c:if test="${sessionScope.SK_Passport_Rs.isOpenCaptcha}">
                            <li>
                                <div class="login-group"></div>
                                <div class="input-group">
                                    <input type="text" name="captcha" class="form-control" placeholder="验证码" value="">
                                    <span class="verify-img" onclick="changeCode();">
                                    <img id="captcha-code" class="captcha-code" src="${root}/captcha/code.html">
                                </span>
                                </div>
                            </li>
                            <div class="form-group clearfix ">
                            </div>
                            <div class="core-group">
                                    ${sessionScope.SK_Passport_Rs.propMessages["captcha"]}${sessionScope.SK_Passport_Rs.message}
                            </div>
                        </c:if>
                        <li>
                            <div class="login_button clear">
                                <a class="ui-login-button"
                                   href="javascript:void(0);document.getElementById('loginForm').submit();">
                                </a>
                                <a class="ui-clear-button" href="#">
                                </a>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</form>
<%@ include file="/include/include.js.nologin.jsp" %>
<script>
    $(document).ready(function () {
        $(".captcha-code").on("click", function () {
            var timestamp = (new Date()).valueOf();
            var src = root + "/captcha/code.html?t=" + timestamp;
            $(this).attr("src", src);
        });
        //登陆输入框清空
        $(".ui-clear-button").on("click", function () {
            $("input[name='username']").val("");
            $("input[name='password']").val("");
        });
    });

    function enterLogin() {
        //回车键的键值为13
        if (event.keyCode == 13) {
            window.location.href = "javascript:void(0);document.getElementById('loginForm').submit();";
        }
    }
</script>
</body>
</html>
