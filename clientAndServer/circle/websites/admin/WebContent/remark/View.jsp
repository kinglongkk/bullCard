<%--@elvariable id="command" type="g.model.master.player.vo.RemarkVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<html lang="zh-CN">
<head>
    <title>${views.common['view']}</title>
    <%@ include file="/include/include.head.jsp" %>
</head>
<body>
<div><br/></div>
<div class="form-group over clearfix">
    <label class="col-xs-3 al-right line-hi34">${views.playerAdmin['player.view.remark.remarkTitle']}：</label>
    <div class="input-group m-b col-xs-9">
        <input readonly="readonly" class="form-control" value="${command.result.remarkTitle}" />
    </div>
</div>
<div class="form-group over clearfix">
    <label class="col-xs-3 al-right line-hi34">${views.playerAdmin['player.view.remark.remarkContent']}：</label>
    <div class="input-group m-b col-xs-9">
        <textarea readonly="readonly" class="form-control">${command.result.remarkContent}</textarea>
    </div>
</div>
</body>
<%@ include file="/include/include.js.jsp" %>
<soul:import type="view"/>
</html>