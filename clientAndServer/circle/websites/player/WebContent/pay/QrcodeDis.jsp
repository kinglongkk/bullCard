<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="keywords" content="keywords"/>
    <meta name="description" content="description"/>
    <meta name="author" content="author" />
    <meta name="Copyright" content="author" />
    <meta name="viewport" content="width=device-width"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="shortcut icon" href="${resRoot}/images/favicon.png"/>
    <link rel="apple-touch-icon" href="${resRoot}/images/touchicon.png"/>
    <title>充值接口-支付二维码</title>
</head>
<link rel="stylesheet" type="text/css" href="${resRoot}/themes/pay/qrcode.css?v=${rcVersion}"/>
<body>
<div class="top-wrap">
    <div class="top">
        <!--顶部左边-->
        <div class="top-left">
            <label class="line-after">扫码支付</label>
        </div>
    </div>
</div>
<!--banner-->
<div class="wrapper">
    <div class="scan-code clearfix">
        <div class="m-order-info">
            <span>存款金额：<strong class="f-c-orange">￥${soulFn:formatCurrency(money)}</strong></span>
            <ul>
                <li><label>订单号：</label>${key}</li>
            </ul>

        </div>
    </div>
    <!--right-->
    <div class="main-wrap r-password">
        <div class=" find-scan-code">
            <div class="m-pay-way">
                <div class="m-pay-wrap m-pay-wrap-fix sel">
                    <div class="m-pay-t wechatpay">
                        <input type="radio" name="payMethod" value="weChatPay" checked="">
                        <span class="m-pay-name">微信扫码支付</span>
                        <span class="m-pay-info">扫一扫二维码即可完成支付</span>
                    </div>
                    <div class="m-pay-mn clearfix" style="display: block;">
                        <div class="m-box">
                            <div id="qrCode"></div>
                            <div class="m-bl">
                                <div class="m-wechat-pay f-shadow-02" id="wechat_qrcode"><img src="${root}/onlinePay/qrcode.html?key=${key}"/></div>
                                <p class="u-wechat-p" id="wechatBtn">微信扫码支付</p>
                            </div>
                            <div class="m-br" style="float: left; margin-left: 100px;">
                                <div class="m-wechat-pay-desc"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="btn-scan-code control-group">
            <a title="付款完成" class="btn-blue btn large-big" href="javascript:window.close()">
                付款完成
            </a>
        </div>
    </div>
</div>
</body>
<%@ include file="/include/include.js.jsp" %>
</html>