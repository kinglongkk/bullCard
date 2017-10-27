<%--@elvariable id="command" type="g.model.playerstatistics.vo.PlayerDataStatisticsListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->
<form:form action="${root}/playerDataStatistics/list.html" method="post">
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <div class="panel panel-default">
        <div class="panel-body">
            <!--//region your codes 2-->
            <div class="row border-b-1">
                <div style="padding:0 0 20px 20px;">
                    年:
                    <select name="year" style="height: 34px;border-radius: 3px;">
                        <c:forEach begin="2016" end="2100" var="i">
                            <option value="${i}" <c:if test="${i == year}">selected</c:if> >${i}</option>
                        </c:forEach>
                    </select>
                    月:
                    <select name="month" style="height: 34px; border-radius: 3px;">
                        <c:forEach begin="1" end="12" var="i">
                            <option value="${i}" <c:if test="${i == month}">selected</c:if>  >${i}</option>
                        </c:forEach>
                    </select>
                    <soul:button target="query" opType="function" text="查询" cssClass="btn btn-filter" />
                </div>

            </div>

            <br/>
            <div class="search-list-container">
                <%@ include file="IndexPartial.jsp" %>
            </div>
            <!--//endregion your codes 2-->
        </div>
    </div>
</form:form>

<!--//region your codes 3-->
<soul:import type="list"/>
<!--//endregion your codes 3-->