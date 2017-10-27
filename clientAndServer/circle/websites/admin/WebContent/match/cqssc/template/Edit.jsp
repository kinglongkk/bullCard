<%--@elvariable id="command" type="g.model.match.ssc.vo.MatchCqsscTemplateVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>${views.common['edit']}</title>
    <%@ include file="/include/include.head.jsp" %>
</head>

<body>
    <form:form method="post">
        <div id="validateRule" style="display: none">${command.validateRule}</div>

        <form:input type="hidden" path="result.id" value="${command.result.id}"/>
        <form:input type="hidden" path="result.no" value="${command.result.no}"/>
        <div class="modal-body">

            <div class="form-group over clearfix">
                <label class="col-xs-3 al-right line-hi34">期号：</label>
                <div class="input-group m-b col-xs-9">
                    ${command.result.no}
                </div>
            </div>


            <div class="form-group over clearfix">
                <label class="col-xs-3 al-right line-hi34">开始时间：</label>
                <div class="input-group m-b col-xs-9">
                    <gb:dateRange useTime="true" value="${command.result.beginTime}" format="${DateFormat.DAY_SECOND}" name="result.beginTime"/>
                </div>
            </div>
            <div class="form-group over clearfix">
                <label class="col-xs-3 al-right line-hi34">结束时间：</label>
                <div class="input-group m-b col-xs-9">
                    <gb:dateRange position="up" useTime="true"  value="${command.result.endTime}" format="${DateFormat.DAY_SECOND}" name="result.endTime"/>
                </div>
            </div>
            <div class="form-group over clearfix">
                <label class="col-xs-3 al-right line-hi34">结果采集时间：</label>
                <div class="input-group m-b col-xs-9">
                    <gb:dateRange position="up" useTime="true"  value="${command.result.resultGatherTime}" format="${DateFormat.DAY_SECOND}" name="result.resultGatherTime"/>
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <soul:button precall="validateForm" cssClass="btn btn-filter" callback="saveCallbak" text="${views.common['save']}" opType="ajax" dataType="json" target="${root}/matchCqsscTemplate/persist.html" post="getCurrentFormData"/>
            <soul:button target="closePage" text="${views.common['cancel']}" cssClass="btn btn-outline btn-filter" opType="function"/>
        </div>
    </form:form>
</body>
<%@ include file="/include/include.js.jsp" %>
<soul:import type="edit"/>
</html>