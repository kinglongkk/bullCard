<%@ tag import="org.soul.web.session.SessionManagerBase" %>
<%@ tag import="org.soul.web.locale.DateQuickPicker" %>
<%--@elvariable id="DateFormat" type="org.soul.web.locale.DateFormat"--%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://soul/fnTag" prefix="soulFn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ attribute name="id" type="java.lang.String" required="false" description="单选ID"%>
<%@ attribute name="name" type="java.lang.String" required="false" description="单选日期名称"%>
<%@ attribute name="value" type="java.util.Date" required="false" description="单选日期值"%>
<%@ attribute name="useTime" type="java.lang.Boolean" required="false" description="是否使用时间"%>
<%@ attribute name="style" type="java.lang.String" required="false" description="控件样式"%>
<%@ attribute name="format" type="java.lang.String" required="false" description="日期格式 如：yyyy-MM-dd HH:mm:ss"%>
<%@ attribute name="minDate" type="java.util.Date" required="false" description="最小日期"%>
<%@ attribute name="maxDate" type="java.util.Date" required="false" description="最大日期"%>
<%@ attribute name="inputStyle" type="java.lang.String" required="false" description="input的样式"%>
<%@ attribute name="callback" type="java.lang.String" required="false" description="日期选择发生改变时的回调，脚本函数名称，不带参数，函数接收，Start，End,Label三个参数"%>
<%@ attribute name="btnClass" type="java.lang.String" required="false" description="按钮的样式"%>
<%@ attribute name="position" type="java.lang.String" required="false" description="选项方向:默认：向下,down:向下;up:向上" %>
<%@ attribute name="opens" type="java.lang.String" required="false" description="选项方向:默认：left; left,center,right" %>
<%@ attribute name="showDropdowns" type="java.lang.Boolean" required="false" description="是否显示年份月份下拉选择" %>

<%@ attribute name="useRange" type="java.lang.Boolean" required="false" description="是否区间选择"%>
<%@ attribute name="useToday" type="java.lang.Boolean" required="false" description="是否选择包含今天"%>
<%@ attribute name="startName" type="java.lang.String" required="false" description="开始日期名称"%>
<%@ attribute name="endName" type="java.lang.String" required="false" description="结束日期名称"%>
<%@ attribute name="startDate" type="java.util.Date" required="false" description="开始时间"%>
<%@ attribute name="endDate" type="java.util.Date" required="false" description="结束时间"%>

<%@ attribute name="required" type="java.lang.String" required="false" description="是否必填"%>
<%@ attribute name="title" type="java.lang.String" required="false" description="必填项是显示标题"%>
<c:if test="${value!=null}">
       <c:set var="startDate" value="${value}"/>
</c:if>
<c:if test="${endDate==null}">
       <%--<c:set var="endDate" value="<%= new DateQuickPicker().getToday() %>"/>--%>
</c:if>

<jsp:doBody var="bodyRes" />

<c:set var="timeZone" value="<%= SessionManagerBase.getTimeZone() %>"/>
<c:set var="DateFormat" value="<%= new org.soul.web.locale.DateFormat() %>"/>
<c:set var="format" value="${format == null ?  DateFormat.DAY : format}"/>
<div class="input-group bootstrap" style="${style}">
       ${bodyRes}
       <i class="input-group-addon glyphicon glyphicon-calendar fa fa-calendar" style="display: table-cell;top:0px;"></i>

              <input class="form-control" type="text" id="${id}" name="${name}" style="${inputStyle}"

       <c:if test="${useRange==true}">
              value="${soulFn:formatDateTz(startDate, format,timeZone).concat(" - ").concat(soulFn:formatDateTz(endDate, format,timeZone))}"
       </c:if>
       <c:if test="${useRange!=true}">
              value="${soulFn:formatDateTz(value, format,timeZone)}"
              <c:if test="${required == 'required'}">
                     required
              </c:if>
              <c:if test="${title != '' && title != null}">
                     title="${title}"
              </c:if>
       </c:if>
              data-rel="{showDropdowns:${showDropdowns==null?false:showDropdowns},callback:'${callback}',useRange:${useRange==null?false:useRange},useToday:${useToday==null?false:useToday},format:'${format}',useTime:${useTime==null?false:useTime},minDate:'${soulFn:formatDateTz(minDate, format,timeZone)}',maxDate:'${soulFn:formatDateTz(maxDate, format,timeZone)}',startDate:'${soulFn:formatDateTz(startDate, format,timeZone)}',endDate:'${soulFn:formatDateTz(endDate, format,timeZone)}',btnClass:'${btnClass}',drops:'${position==null?'down':position}',opens:'${opens==null?'right':opens}',startName:'${startName}',endName:'${endName}'}" />
       <c:if test="${useRange}">
              <div>
                     <input type="hidden" name="${startName}" value="${soulFn:formatDateTz(startDate, format, timeZone)}"
                     <c:if test="${required == 'required'}">
                            required
                     </c:if>
                     <c:if test="${title != '' && title != null}">
                            title="${title}"
                     </c:if>
                     />
                     <input type="hidden" name="${endName}" value="${soulFn:formatDateTz(endDate, format, timeZone)}"
                     <c:if test="${required == 'required'}">
                            required
                     </c:if>
                     <c:if test="${title != '' && title != null}">
                            title="${title}"
                     </c:if>
                     />
              </div>
       </c:if>

</div>


