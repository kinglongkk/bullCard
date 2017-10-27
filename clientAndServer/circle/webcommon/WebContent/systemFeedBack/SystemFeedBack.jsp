<%--@elvariable id="command" type="g.model.master.player.vo.PlayerRankVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->

<html lang="zh-CN">
<head>
    <title>意见反馈</title>
    <%@ include file="/include/include.head.jsp" %>
    <!--//region your codes 2-->

    <!--//endregion your codes 2-->
</head>

<body>

<form:form id="editForm" action="" method="post">
    <input type="hidden" name="flag" value="${isCaptcha}">
    <div id="validateRule" style="display: none">${validateRule}</div>
    <div class="modal-body">
        <div class="form-group clearfix m-b-sm col-xs-12">
            <label class="col-xs-3 al-right line-hi34"><span class="co-red m-r-sm">*</span>反馈内容：</label>
            <div class="col-xs-8 p-x">
                <div class="line-hi34 m-b-xs">
                    <span class="co-yellow"><i class="fa fa-exclamation-circle"></i></span>
                    业务方面的问题请直接联系客服处理
                </div>
                <textarea class="form-control" placeholder="如果您在使用平台的过程中遇到问题，或者有任何的意见或建议，欢迎随时向我们反馈。" name="result.feedbackContent"></textarea>
            </div>
        </div>
        <c:if test="${isCaptcha}">
            <div class="form-group clearfix m-b-sm col-xs-12">
                <label class="col-xs-3 al-right line-hi34">验证码：</label>
                <div class="col-xs-8 p-x">
                    <div class="input-group date">
                        <input type="text" placeholder="请输入验证码" class="form-control" name="code"/>
                    <span class="input-group-addon abroder-no p-x">
                        <%--<gb:captcha url="${root}/share/components/captcha.html?sessionCode=code" clickToReload="true"/>--%>
                        <img src="${root}/captcha/feedback.html" height="32" reloadable/>
                    </span>
                    </div>
                </div>
            </div>
        </c:if>

    </div>
    <div class="modal-footer">
        <soul:button cssClass="btn btn-default" text="${views.common['commit']}" opType="ajax" dataType="json" target="${root}/feedBack/saveFeedBack.html" precall="validateForm" post="getCurrentFormData" callback="saveCallbak" />
    </div>
    <!--//endregion your codes 3-->

</form:form>

</body>
<%@ include file="/include/include.js.jsp" %>
<!--//region your codes 4-->
<soul:import res="gb/systemFeedBack/SystemFeedBack"/>
<!--//endregion your codes 4-->
</html>