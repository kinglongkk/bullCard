<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/include/include.head.jsp" %>
</head>
<body>
<form:form>
    <!--修改头像-->
    <div id="validateRule" style="display: none">${validate}</div>
    <div class="modal-body" style="height: 390px;">
        <div class="form-group clearfix">
            <!-- TODO cj (上传后两种尺寸预览未实现，未找到插件) -->
            <b>${views.setting['myAccount.currentHeadImage']}</b>
            <br/>

            <div class="form-group m-b-sm">
                <c:if test="${not empty url}">
                    <div style="height: 300px;line-height: 300px;text-align: center">
                        <img src="${soulFn:getImagePathWithDefault(domain, url, resComRoot.concat('/images/def.png'))}"
                             class="logo-size-h100" style="margin: 10px 0;"/>
                    </div>
                </c:if>
                <input class="file" type="file" target="result.logoPath" accept="image/*">
                <input type="hidden" name="result.logoPath" value="">
            </div>
            <span>${views.setting['myAccount.avatar.image.size']}</span>
        </div>
    </div>
    <div class="modal-footer">
        <soul:button cssClass="btn btn-filter" text="${views.common.OK}" opType="ajax" precall="myValidateForm"
                     target="${root}/sys/site/uploadLogoPortrait.html" dataType="json" post="getCurrentFormData"
                     callback="saveCallbak"/>
        <soul:button cssClass="btn btn-outline btn-filter" target="closePage" opType="function"
                     text="${views.common.cancel}"/>
    </div>
</form:form>
</body>
<%@ include file="/include/include.js.jsp" %>
<soul:import res="site/sys/site/Index"/>
</html>