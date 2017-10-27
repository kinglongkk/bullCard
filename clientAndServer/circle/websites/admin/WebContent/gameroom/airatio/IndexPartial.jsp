<%--@elvariable id="command" type="g.model.gameroom.vo.PlayerAiRatioControlListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<div class="table-responsive table-min-h table_border">
    <table class="table table-striped table-hover dataTable m-b-none">
        <thead>
            <tr role="row" class="bg-gray">
                <th>#</th>
                <th>玩家最低占比%</th>
                <th>玩家最高占比%</th>
                <th>AI最低占比%</th>
                <th>AI最高占比%</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${command.result}" var="p" varStatus="status">
                <tr class="tab-detail">
                    <td class="text_r">${status.index+1}</td>
                    <td class="text_r">${p.playerProportionMin}</td>
                    <td class="text_r">${p.playerProportionMax}</td>
                    <td class="text_r">${p.aiProportionMin}</td>
                    <td class="text_r">${p.aiProportionMax}</td>
                    <td>
                        <div class="joy-list-row-operations text_c">
                            <soul:button target="${root}/playerAiRatioControl/delete.html?id=${p.id}" text="删除" opType="ajax" dataType="json" confirm="您确定要删除该条记录吗？" callback="query" />
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${fn:length(command.result)<1}">
                <tr>
                    <td colspan="6" class="no-content_wrap">
                        <div>
                            <i class="fa fa-exclamation-circle"></i> ${views.common['noResult']}
                        </div>
                    </td>
                </tr>
            </c:if>
        </tbody>
    </table>
</div>

<soul:pagination/>
<!--//endregion your codes 1-->
