<%--@elvariable id="command" type="g.model.playerstatistics.vo.PlayerDataStatisticsListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<input id="dataStr" name="dataStr" value="${dataStr}" type="hidden">
<div class="table-responsive">
        <div id="columnRotatedLabels"></div>
</div>
<soul:import res="site/playerstatistics/playerstatistics"></soul:import>
<!--//endregion your codes 1-->
