<%--@elvariable id="command" type="g.model.sys.vo.syssitevo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<div class="table-responsive table-min-h">

    <c:forEach items="${command.result}" var="p">

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                站点名称
            </label>
            <div class="col-xs-8 p-x">
                    ${p.name}
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                站点状态
            </label>
            <div class="col-xs-8 p-x">
                <c:choose>
                    <c:when test="${p.status eq '1'}">正常</c:when>
                    <c:when test="${p.status eq '2'}"></c:when>
                    <c:when test="${p.status eq '3'}"></c:when>
                    <c:otherwise>未建库</c:otherwise>
                </c:choose>
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                站点简称
            </label>
            <div class="col-xs-8 p-x">
                    ${p.shortName}
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                站点网站
            </label>
            <div class="col-xs-8 p-x">
                    ${p.webSite}
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                Logo
            </label>
            <div class="col-xs-8 p-x">
                <img src="${soulFn:getImagePathWithDefault(domain, p.logoPath,resRoot.concat('/images/myaccount.jpg'))}" class="logo-size-h100">
                <soul:button target="${root}/sys/site/toUploadLogoPortrait.html?result.id=${p.id}" text="点击修改Logo" title="${views.setting['myAccount.editHeadPortrait']}" opType="dialog" callback="query"></soul:button>
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                维护开始时间
            </label>
            <div class="col-xs-8 p-x">
                    ${p.maintainStartTime}
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                维护结束时间
            </label>
            <div class="col-xs-8 p-x">
                    ${p.maintainEndTime}
            </div>
        </div>

        <div class="form-group clearfix line-hi34 m-b-xxs">
            <label class="col-xs-3 al-right">
                维护原因
            </label>
            <div class="col-xs-8 p-x">
                    ${p.maintainReason}
            </div>
        </div>

    </c:forEach>

</div>

<div class="modal-footer">
    <a href="/sys/site/edit.html?id=${p.id}" nav-target="mainFrame" style="background: #2095f2; padding: 15px 20px; border-radius: 4px; border: 1px solid #2095f2; border-image: none; color: #FFFFFF;" class="">${views.common['edit']}</a>
</div>