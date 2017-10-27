<%--@elvariable id="command" type="g.model.master.player.vo.RemarkListVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--备注-->
<form:form action="${root}/playerRemark/remark.html?search.entityUserId=${command.search.entityUserId}&search.operatorId=${command.search.operatorId}" method="post">
    <div class="search-list-container">
        <%@include file="RemarkPartial.jsp"%>
    </div>
</form:form>
<script>
    curl(["site/player/remark/Remark"], function (Remark) {
        page.remark = new Remark();
    });
</script>

