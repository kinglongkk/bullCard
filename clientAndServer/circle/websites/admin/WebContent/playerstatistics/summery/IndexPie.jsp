<%--@elvariable id="command" type="g.model.playerstatistics.vo.PlayerSummeryListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->
<form:form action="${root}/playerSummery/indexPie.html" method="post">
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <div class="panel panel-default">
        <div class="panel-body">
            <!--//region your codes 2-->
            <div class="row border-b-1">
                <div style="padding:0 0 20px 20px;">
                    <div class="form-group clearfix line-hi34 m-b-xxs">
                        <div class="col-xs-2 p-x">
                            <div>
                                <gb:dateRange value="${dateTime}" format="${DateFormat.DAY}" name="dateTime"/>
                            </div>
                            <div style="float: left;margin-top: -36px;margin-left: 257px;">
                                <soul:button target="query" opType="function" text="查询" cssClass="btn btn-filter" >
                                    <i class=" fa fa-search"> </i>
                                    <span>查询</span>
                                        </soul:button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br/>
            <div class="search-list-container">
                <%@ include file="IndexPiePartial.jsp" %>
            </div>
            <!--//endregion your codes 2-->
        </div>
    </div>
</form:form>

<!--//region your codes 3-->
<soul:import type="list"/>
<!--//endregion your codes 3-->