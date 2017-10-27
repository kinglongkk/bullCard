<%@ tag import="com.alibaba.dubbo.common.json.JSON" %>
<%@ tag import="com.google.common.collect.Maps" %>
<%@ tag import="org.soul.commons.data.json.JsonTool" %>
<%@ tag import="org.soul.commons.lang.string.I18nTool" %>
<%@ tag import="org.soul.web.session.SessionManagerBase" %>
<%@ tag import="org.soul.model.sys.po.SysDict" %>
<%@ tag import="java.util.*" %>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="name" type="java.lang.String" required="true" description="表单名字" %>
<%@attribute name="value" type="java.lang.Object" required="false" description="值" %>
<%@attribute name="optionDirection" type="java.lang.String" required="false" description="选项方向:默认：向下,down:向下;up:向上" %>
<%@attribute name="isButton" type="java.lang.Boolean" required="false" description="是否是button形式展现的 默认:false" %>
<%@attribute name="prompt" type="java.lang.String" required="false" description="值为空提示" %>
<%@attribute name="notUseDefaultPrompt" type="java.lang.Boolean" required="false" description="值为空提示" %>

<%@attribute name="list" type="java.lang.Object" required="false" description="列表,不能同时和ajaxListPath、relSelectPath参数同时存在" %>
<%@attribute name="ajaxListPath" type="java.lang.String" required="false" description="当下拉数据不从list获取，而使用异步加载路径,不能同时和list、relSelectPath参数同时存在"  %>
<%@attribute name="listKey" type="java.lang.String" required="false" description="列表数据源中元素对象的属性, 用于获取选项的值" %>
<%@attribute name="listValue" type="java.lang.String" required="false" description="列表数据源中元素对象的属性, 用于获取选项的文本内容" %>

<%@attribute name="cssClass" type="java.lang.String" required="false" description="输出元素时的class属性" %>
<%@attribute name="cssStyle" type="java.lang.String" required="false" description="输出元素时的css样式定义(译者注:就是html元素的style属性)" %>

<%@attribute name="relSelect" type="java.lang.String" required="false" description="和某个select插件级联，被指向方代表是当前插件的子集"  %>
<%@attribute name="relSelectPath" type="java.lang.String" required="false" description="插件级联子集指向地址,#value#代表是传递的当前参数value,不能同时和list、ajaxListPath参数同时存在"  %>
<%@ attribute name="callback" type="java.lang.String" required="false" description="事件回调函数：取值为空或者js函数名，不可带参数和括号"%>

<%
    Map map = Maps.newHashMap();
    String selected="";
    Map<String,SysDict> mapDict = Maps.newHashMap();
    Map<String, Map<String,Map<String,String>>> dicts = I18nTool.getDictsMap(SessionManagerBase.getLocale().toString());
    if(notUseDefaultPrompt==null){
        notUseDefaultPrompt = false;
    }
    if (prompt == null && notUseDefaultPrompt!=true) {
        prompt = I18nTool.getI18nMap(SessionManagerBase.getLocale().toString()).get("views").get("common").get("pleaseSelect");
    }else
    {
        if(prompt==null){
            prompt="";
        }
    }
    if(list!=null){
        if (list instanceof String) {
            if (list != null) {
                list = list.toString().replace("'", "\"");
                list = JsonTool.fromJson(list.toString(), Map.class);
            }
        }
        if(list instanceof Map)
        {
            if(((Map)list).size()>0 && ((Map)list).get(((Map)list).keySet().iterator().next()) instanceof SysDict) {
                mapDict = (Map) list;
                if (value != null && !"".equals(value)) {
                    SysDict temp = mapDict.get(value);
                    if(temp!=null)
                    {
                        selected = dicts.get(temp.getModule()).get(temp.getDictType()).get(temp.getDictCode());
                    }
                }
            }
            else
            {
                map = (Map) list;
                if (value != null && !"".equals(value)) {
                    if (map.get(value.toString()) != null) {
                        selected = map.get(value.toString()).toString();
                    }
                }
            }
        }
        if (list instanceof List  || list instanceof Collection) {
            for (Object o : (Collection) list) {
                Map m = new HashMap();
                if(!(o instanceof Map)){
                    m = JsonTool.fromJson(JSON.json(o, new String[]{listKey.toString(), listValue.toString()}), Map.class);
                }else{
                    m = (Map)o;
                }
                if (m.get(listKey).equals(value)) {
                    if (m.get(listValue) != null) {
                        selected = m.get(listValue).toString();
                    }
                }
            }
        }
    }
    selected=selected.length()>0?selected:((prompt==null||prompt.length()==0)?"&nbsp;":prompt);
%>
<c:set var="prompt" value="<%=prompt%>"/>
<%--这里请避免使用 <select> 元素，因为 WebKit 浏览器不能完全绘制它的样式。--%>
<c:if test="${empty optionDirection || (!empty optionDirection && optionDirection == 'down')}">
    <div selectDiv="${name}" class="btn-group"
</c:if>
<c:if test="${!empty optionDirection && optionDirection == 'up'}">
    <div selectDiv="${name}" class="btn-group dropup"
</c:if>
${empty ajaxListPath?'':'ajaxListPath='.concat(ajaxListPath)}
${empty listKey?'':'listKey='.concat(listKey)}
${empty listValue?'':'listValue='.concat(listValue)}
<%if(value!=null)out.print("value="+value);%>
${empty relSelect?'':'relSelect='.concat(relSelect)}
${empty relSelectPath?'':'relSelectPath='.concat(relSelectPath)}
initPrompt="${prompt}"  callback="${callback}">
<input type="hidden" name="${name}" value="${value}">
<c:if test="${!empty isButton && isButton == true }">
    <button type="button" class="btn btn-group btn-default dropdown-toggle" prompt="prompt"><%=selected%>
    </button>
</c:if>

<button type="button" class="btn btn-group btn-default dropdown-toggle" data-toggle="dropdown"
        aria-expanded="false">
    <c:if test="${!(!empty isButton && isButton == true) }">
        <span prompt="prompt"><%=selected%></span>
    </c:if>
    <span class="caret"></span>
</button>

<c:set var="dicts" value="<%=I18nTool.getDictsMap(SessionManagerBase.getLocale().toString()) %>"/>
<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1" style="${cssStyle}">
    <c:if test="${prompt!=null && !prompt.equals('')}">
        <li role='presentation'>
            <a role='menuitem' tabindex='-1' href='javascript:void(0)' key="">${prompt}</a>
        </li>
    </c:if>
    <%
if(mapDict.size()>0){%>
    <c:set var="entry" value="<%=mapDict %>"/>
    <c:forEach items="${entry}" var="p" varStatus="status">
        <li role='presentation'>
            <a role='menuitem' tabindex='-1' href='javascript:void(0)' key="${p.key}">${dicts[p.value.module][p.value.dictType][p.value.dictCode]}</a>
        </li>
    </c:forEach><%
}
else if (map.size()>0) {%>
    <c:set var="entry" value="<%=map %>"/>
    <c:forEach items="${entry}" var="p" varStatus="status">
        <li role='presentation'>
            <a role='menuitem' tabindex='-1' href='javascript:void(0)' key="${p.key}">${p.value}</a>
        </li>
    </c:forEach><%
} else if (list instanceof List || list instanceof Collection && ((Collection) list).size()>0) {%>
    <c:set var="entry" value="<%=list %>"/>
    <c:forEach items="${entry}" var="p" varStatus="status">
        <li role='presentation'>
            <a role='menuitem' tabindex='-1' href='javascript:void(0)' key="${p[listKey]}">${p[listValue]}</a>
        </li>
    </c:forEach> <%
    }
%>
</ul>
</div>
