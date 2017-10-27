<%--@elvariable id="command" type="g.model.match.ssc.vo.MatchCqsscTemplateListVo"--%>
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
            <th>截止时间</th>
            <th>采集时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${command.result}" var="s" varStatus="index">
            <tr class="gradeA">
                <td><input type="checkbox" value="${s.id}" class="i-checks"></td>
                <td>${index.index+1}</td>
                <td>${s.no}</td>
                <td>${soulFn:formatDateTz(s.beginTime, DateFormat.DAY_SECOND,timeZone)}</td>
                <td>${soulFn:formatDateTz(s.endTime, DateFormat.DAY_SECOND,timeZone)}</td>
                <td>${soulFn:formatDateTz(s.resultGatherTime, DateFormat.DAY_SECOND,timeZone)}</td>
                <td>
                    <soul:button target="${root}/matchCqsscTemplate/edit.html?id=${s.id}"
                                 text="编辑"
                                 opType="dialog" callback="query"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<soul:pagination/>


