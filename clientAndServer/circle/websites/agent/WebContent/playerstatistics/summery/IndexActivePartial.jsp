<%--@elvariable id="command" type="g.model.playerstatistics.vo.PlayerSummeryListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<input id="dataStr" name="dataStr" value="${dataStr}" type="hidden">
<div class="table-responsive">
    <div id="lineLabels"></div>
</div>
<soul:import res="site/playerstatistics/indexActive"></soul:import>
