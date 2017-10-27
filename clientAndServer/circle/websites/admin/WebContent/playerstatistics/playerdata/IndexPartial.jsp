<%--@elvariable id="command" type="g.model.playerstatistics.vo.PlayerDataStatisticsListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<input id="dataStr" name="dataStr" value="${dataStr}" type="hidden">
<div class="table-responsive">
        <div id="indexplaydata" style="height:500px;border:1px solid #ccc;padding:10px;" ></div>
        <%--<div style="text-align: center">--%>
                <%--<input type="button" value="曲线图" onclick="indexplaydata();" style="width: 100px; height: 40px;">--%>
                <%--<input type="button" value="柱形图" onclick="indexplaydatazhu();" style="width: 100px; height: 40px;">--%>
        <%--</div>--%>
</div>
<soul:import res="site/playerstatistics/indexplaydata"></soul:import>
<!--//endregion your codes 1-->
