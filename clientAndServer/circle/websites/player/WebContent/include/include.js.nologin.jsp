<%--common js base--%>
<%@ include file="include.base.inc.jsp" %>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    var root = '${root}';
    var resComRoot = '${resComRoot}';
    var resRoot = '${resRoot}';
    var fileRoot = '${fileRoot}';
    var imgRoot = '${imgRoot}';
    var language = '${language.replace('_','-')}';
</script>

<%--RequireJs begin--%>
<script src="${resComRoot}/js/jquery/jquery-1.11.1.min.js"></script>
<%--RequireJs end--%>
<script src="${resRoot}/js/common/common-rem.js"></script>

