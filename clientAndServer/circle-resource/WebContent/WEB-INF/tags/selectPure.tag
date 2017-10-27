<%@ tag import="com.alibaba.dubbo.common.json.JSON" %>
<%@ tag import="com.google.common.collect.Maps" %>
<%@ tag import="org.soul.commons.data.json.JsonTool" %>
<%@ tag import="org.soul.commons.lang.string.I18nTool" %>
<%@ tag import="org.soul.model.sys.po.SysDict" %>
<%@ tag import="org.soul.web.session.SessionManagerBase" %>
<%@ tag import="java.util.List" %>
<%@ tag import="java.util.Map" %>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="name" type="java.lang.String" required="true" description="表单名字" %>
<%@attribute name="value" type="java.lang.Object" required="false" description="值" %>
<%@attribute name="prompt" type="java.lang.String" required="false" description="值为空提示" %>

<%@attribute name="list" type="java.lang.Object" required="false"
             description="列表,不能同时和ajaxListPath、relSelectPath参数同时存在" %>
<%@attribute name="ajaxListPath" type="java.lang.String" required="false"
             description="当下拉数据不从list获取，而使用异步加载路径,不能同时和list、relSelectPath参数同时存在" %>
<%@attribute name="listKey" type="java.lang.String" required="false" description="列表数据源中元素对象的属性, 用于获取选项的值" %>
<%@attribute name="listValue" type="java.lang.String" required="false" description="列表数据源中元素对象的属性, 用于获取选项的文本内容" %>

<%@attribute name="cssClass" type="java.lang.String" required="false" description="输出元素时的class属性" %>
<%@attribute name="cssStyle" type="java.lang.String" required="false"
             description="输出元素时的css样式定义(译者注:就是html元素的style属性)" %>

<%@attribute name="relSelect" type="java.lang.String" required="false" description="和某个select插件级联，被指向方代表是当前插件的子集" %>
<%@attribute name="relSelectPath" type="java.lang.String" required="false"
             description="插件级联子集指向地址,#value#代表是传递的当前参数value,不能同时和list、ajaxListPath参数同时存在" %>
<%@ attribute name="callback" type="java.lang.String" required="false" description="事件回调函数：取值为空或者js函数名，不可带参数和括号" %>

<%
    Map map = Maps.newHashMap();
    Map<String, SysDict> mapDict = Maps.newHashMap();
    if (list != null) {
        if (list instanceof String) {
            if (list != null) {
                list = list.toString().replace("'", "\"");
                list = JsonTool.fromJson(list.toString(), Map.class);
            }
        }
        if (list instanceof Map) {
            if (((Map) list).size() > 0 && ((Map) list).get(((Map) list).keySet().iterator().next()) instanceof SysDict) {
                mapDict = (Map) list;
            } else {
                map = (Map) list;
            }
        }
    }
%>
<select class="${cssClass}" name="${name}"
${empty cssStyle ? '' : cssStyle}
${empty ajaxListPath?'':' ajaxListPathPure='.concat(ajaxListPath)}
${empty listKey?'':'listKey='.concat(listKey)}
${empty listValue?'':'listValue='.concat(listValue)}
<%if(value!=null)out.print("initValue='"+value+"'");%>
${empty relSelect?'':'relSelectPure='.concat(relSelect)}
${empty relSelectPath?'':' relSelectPathPure='.concat(relSelectPath)}
    initPrompt="${prompt}"  callback="${callback}">
    <c:if test="${not empty prompt}">
        <option value=""><%=prompt%></option>
    </c:if>
    <c:set var="dicts" value="<%=I18nTool.getDictsMap(SessionManagerBase.getLocale().toString()) %>"/>
    <%
        if (mapDict.size() > 0) {%>
    <c:set var="entry" value="<%=mapDict %>"/>
    <c:forEach items="${entry}" var="p" varStatus="status">
        <option <c:if test="${value == p.key}">selected</c:if> value="${p.key}">${dicts[p.value.module][p.value.dictType][p.value.dictCode]}</option>
    </c:forEach><%
} else if (map.size() > 0) {%>
    <c:set var="entry" value="<%=map %>"/>
    <c:forEach items="${entry}" var="p" varStatus="status">
        <option <c:if test="${value == p.key}">selected</c:if> value="${p.key}">${p.value}</option>
    </c:forEach><%
} else if (list instanceof List) {%>
    <c:set var="entry" value="<%=list %>"/>
    <c:forEach items="${entry}" var="p" varStatus="status">
        <option <c:if test="${value == p[listKey]}">selected</c:if> value="${p[listKey]}">${p[listValue]}</option>
    </c:forEach> <%
    }
%>
</select>