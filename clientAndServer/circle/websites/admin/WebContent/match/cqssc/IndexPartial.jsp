<%--@elvariable id="command" type="g.model.match.vo.VMatchResultListVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<div class="table-responsive">
    <table class="table table-striped table-hover dataTable m-b-sm" aria-describedby="editable_info">
        <thead class="bg-gray">
        <tr role="row">
            <th><input type="checkbox" class="i-checks"></th>
            <th>序号</th>
            <th>期号</th>
            <th>开始时间</th>
            <th>结束时间</th>
            <th>采集时间</th>
            <th>彩期状态</th>
            <th>结算状态</th>
            <th>开奖结果</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${command.result}" var="s" varStatus="index">
            <tr class="gradeA">
                <td><input type="checkbox" value="${s.id}" class="i-checks"></td>
                <td>${index.index+1}</td>
                <td>${s.code}</td>
                <td>${soulFn:formatDateTz(s.beginTime, DateFormat.DAY_SECOND,timeZone)}</td>
                <td>${soulFn:formatDateTz(s.endTime, DateFormat.DAY_SECOND,timeZone)}</td>
                <td>${soulFn:formatDateTz(s.resultGatherTime, DateFormat.DAY_SECOND,timeZone)}</td>
                <td>${s.isCancel ? "已取消" : "有效"}</td>
                <td>${dicts.bet.settle_status[s.settleStatus]}</td>
                <td>${s.result}</td>
                <td>
                    <c:if test="${not s.isRunning}">
                        <c:if test="${empty s.result}">
                            <soul:button target="${root}/match/cqssc/gather.html?search.id=${s.id}" text="采集" opType="ajax" callback="query"/>
                        </c:if>
                        <c:if test="${empty s.result}">
                            <soul:button target="${root}/match/cqssc/settleResult.html?search.id=${s.id}"
                                         text="手工"
                                         opType="dialog" callback="query"/>
                        </c:if>
                        <c:if test="${not empty s.result && s.settleStatus == '10' && not s.isCancel}">
                            <soul:button target="${root}/match/cqssc/settle.html?search.id=${s.id}"
                                         text="开奖"
                                         opType="dialog" callback="query"/>
                        </c:if>
                    </c:if>
                    <c:if test="${not empty s.result && s.settleStatus == '20' && not s.isCancel}">
                        <soul:button target="${root}/match/cqssc/reSettle.html?search.id=${s.id}"
                                     text="重新开奖"
                                     opType="dialog" callback="query"/>
                    </c:if>
                    <c:if test="${s.isRunning}">
                        <b style="color:red">进行中</b>
                    </c:if>
                    <c:if test="${not s.isCancel}">
                        <soul:button target="${root}/match/cqssc/cancel.html?search.id=${s.id}"
                                     text="取消"
                                     opType="dialog" callback="query" cssClass="disabled" />
                    </c:if>

                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<soul:pagination/>


