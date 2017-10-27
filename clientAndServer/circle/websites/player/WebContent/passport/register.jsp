<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html lang="zh-CN">
<body>
<%@ include file="/include/include.inc.jsp" %>
<%@ include file="/include/include.head.jsp" %>
<div  class="container">
    <form action="${root}/passport/register.html" method="post" onsubmit="return checkForm()">
        <div class="ui-green-bg">
            <div class="Registered_position">
                <div class="acount_cord">
                    <div class="acount_btn"></div>
                </div>
                <div class="Registered_bg1">
                    <div class="Registered_tc_bg1">
                        <div class="Registered_ribbon"></div>
                        <div class="Registered_ribbon1"></div>
                        <div class="Registered_ribbon2"></div>
                    </div>
                    <div class="Registered_Prompt_bg">
                        <div class="Registered_Prompt_tc">
                            <div class="Registered_tc_bg2">
                                <div class="Registered_bg">
                                    <div class="Registered-acount">
                                        手机号码
                                        <input type="text" class="userPhone" name="username" placeholder=""
                                               value="${userName}"  required oninvalid="setCustomValidity('请输入您的手机号码')"
                                               oninput="setCustomValidity('')" />
                                        <span class="Registered-color1"></span>
                                    </div>
                                    <div class="Registered-password">
                                        设置密码
                                        <input type="password" class="userPwd" name="password" id=" " placeholder=""
                                               required oninvalid="setCustomValidity('请设置您的密码')"
                                               oninput="setCustomValidity('')" />
                                        <div class="Registered_safe">
                                            <div class="Registered_safe1"></div>
                                            <div class="Registered_safe1"></div>
                                            <div class="Registered_safe1"></div>
                                        </div>
                                        <span class="Registered-color">7-20位，英文（区分大小写）数字或常用符号</span>
                                    </div>
                                    <div class="Registered-password">
                                        确认密码
                                        <input type="password" class="checkPwd" id="" placeholder=""
                                               required oninvalid="setCustomValidity('请确认您的密码')"
                                               oninput="setCustomValidity('')" />
                                        <span class="Registered-color">注意区分字母的大小写</span>
                                    </div>
                                    <div class="Registered-code">
                                        <div class="Registered-yzm">验证码</div>
                                        <input type="text" id="vcode" placeholder="" value=""
                                               required oninvalid="setCustomValidity('请输入手机验证码')"
                                               oninput="setCustomValidity('')" />
                                        <div class="Registered-code1">点击获取</div>
                                    </div>
                                    <div class="Registered-agree">
                                        <div class="agreement-border">
                                            <input type="checkbox" id="register-agreement" class="agreement" name="agreement"
                                                    required oninvalid="setCustomValidity('请同意《智玩服务协议》以完成注册')"
                                                   onclick="setCustomValidity('')" />
                                            <div></div>
                                        </div>
                                        </span> 同意<span>《智玩服务协议》</span>
                                    </div>
                                    <div class="Registered_error" id="errorMessage">${result}</div>
                                    <button class="Registered-btn" type="submit">立即注册</button>
                                    <div class="Registered_qh">你还可以<a href="/passport/login.html">前往游戏登录界面</a></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="Registered_tc_bg3"></div>
                </div>
            </div>
        </div>
    </form>
</div>
<%@ include file="/include/include.js.nologin.jsp" %>
<script type="text/javascript">
    function checkForm(){
        var tel = $(".userPhone");
        var pwd1 = $(".userPwd");
        var pwd2 = $(".checkPwd");
        var telReg = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
        var pwdReg = /^[A-Za-z0-9~!@#$%^&*()_+\{\}\[\]|\:;'"<>,./?]{7,20}$/;

        if(!(telReg.test(tel.val()))){

            $("#errorMessage").html("请输入正确的手机号码");
            return false;
        }

        if(!(pwdReg.test(pwd1.val()))){

            $("#errorMessage").html("登录密码为7到20位");
            return false;
        }

        if(pwd1.val()!=pwd2.val()){

            $("#errorMessage").html("两次输入的登录密码不一致.");
            return false;
        }
    }
</script>
</body>
</html>
