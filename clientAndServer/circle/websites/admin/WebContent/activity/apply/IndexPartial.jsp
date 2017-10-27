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
                <th>优惠形式</th>
                <th>玩家</th>
                <th>注册时间</th>
                <th>申请时间</th>
                <th>充值</th>
                <th>优惠</th>
                <th>实物</th>
                <th>是否兑现</th>
                <th>是否有效</th>
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
                    <td>${p.preferentialForm}</td>
                    <td>${p.userName}</td>
                    <td class="text_c">${soulFn:formatDateTz(p.registerTime, DateFormat.DAY,timeZone)}</td>
                    <td class="text_c">${soulFn:formatDateTz(p.applyTime, DateFormat.DAY,timeZone)}</td>
                    <td class="text_r">${soulFn:formatCurrency(p.rechargeAmount)}</td>
                    <td class="text_r">${soulFn:formatCurrency(p.preferentialValue)}</td>
                    <td>${p.article}</td>
                    <td>${p.isRealize eq false ? '否': '是'}</td>
                    <td>${p.isEffective eq false ? '否': '是'}</td>
                </tr>
            </c:forEach>
            <c:if test="${fn:length(command.result)<1}">
                <tr>
                    <td colspan="${fn:length(command.fields)+11}" class="no-content_wrap">
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
