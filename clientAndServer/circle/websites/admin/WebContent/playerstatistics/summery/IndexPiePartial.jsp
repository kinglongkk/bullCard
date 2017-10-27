<%--@elvariable id="command" type="g.model.playerstatistics.vo.PlayerSummeryListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<div class="table-responsive">
    <div id="pieBasic" style="height:500px;border:1px solid #ccc;padding:10px;"></div>
</div>
<soul:import res="site/playerstatistics/indexPie"></soul:import>
