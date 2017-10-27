
<%--@elvariable id="command" type="g.model.activityapply.vo.ActivityPlayerApplyListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<div class="table-responsive table-min-h table_border">
    <table class="table table-striped table-hover dataTable m-b-none">
        <thead>
            <tr role="row" class="bg-gray">
                <th>#</th>
                <th>活动</th>
                <th>开始时间</th>
                <th>结束时间</th>
                <th>参与人数</th>
                <th>参与人数比例</th>
                <th>总消费</th>
                <th>总返金</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${command.result}" var="p" varStatus="status">
                <tr class="tab-detail">
                    <td class="text_r">${status.index+1}</td>
                    <td>
                        <c:forEach items="${activityTypeEnum}" var="activityTypeEnumName">
                         <c:choose>
                            <c:when test="${activityTypeEnumName.code eq p.activityClassifyKey}">
                                ${activityTypeEnumName.trans}
                            </c:when>
                         </c:choose>
                        </c:forEach>
                    </td>
                    <td class="text_c">${soulFn:formatDateTz(p.starttime, DateFormat.DAY,timeZone)}</td>
                    <td class="text_c">${soulFn:formatDateTz(p.endtime, DateFormat.DAY,timeZone)}</td>
                    <td class="text_r">${p.joinNumber}</td>
                    <td class="text_r">${p.joinScale}</td>
                    <td class="text_r">${soulFn:formatCurrency(p.totalConsume)}</td>
                    <td class="text_r">${soulFn:formatCurrency(p.totalCashBack)}</td>
                    <td>
                        <div class="joy-list-row-operations text_c">
                            <a href="/activityPlayerApply/list.html?search.activityClassifyKey=${p.activityClassifyKey}" nav-target="mainFrame">查看</a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${fn:length(command.result)<1}">
                <tr>
                    <td colspan="${fn:length(command.fields)+9}" class="no-content_wrap">
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
