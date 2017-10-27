<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/include/include.inc.jsp" %>

<form>
<div class="row">
    <div class="position-wrap clearfix">
        <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
        <span>${views.sysResource['公告内容']}</span><span>/</span><span>${views.sysResource['公告详情']}</span>
        <a href="/vSysAnnouncement/sysNoticeHistory.html" nav-target="mainFrame" class="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn">
            <em class="fa fa-caret-left"></em>${views.common['return']}</a>
        <a href="javascript:void(0)" class="pull-right siteMap"><i class="fa fa-sitemap"></i></a>
    </div>
    <div class="col-lg-12 m-b">
        <%--<c:if test="${vSysAnnouncementListVo.result[0].publishStatus>0}">
            <a href="/vSysAnnouncement/editNotice.html?search.id=${vSysAnnouncementListVo.result[0].id}" nav-target="mainFrame" class="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn">
            ${views.common['edit']}</a>
        </c:if>--%>
        <div class="wrapper white-bg shadow">
            <ul class="artificial-tab clearfix bg-gray m-t">
                <c:forEach items="${vSysAnnouncementListVo.result}" var="v" varStatus="ide">
                    <c:if test="${vSysAnnouncementListVo.result.size()>1}">
                        <li class="m-l-lg">
                            <a id="index_${ide.index}" class="click_Detail ${ide.index==1?'current':''}">
                                    ${fn:substringBefore(dicts.common.language[v.local], '#')}&nbsp;
                                <span class="con">${views.column['sysNotice.edited']}</span>
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${vSysAnnouncementListVo.result.size()==1}">
                        <li class="m-l-lg">
                            <a id="index_${ide.index}" class="click_Detail">
                                    ${fn:substringBefore(dicts.common.language[v.local], '#')}&nbsp;
                                <span class="con">${views.column['sysNotice.edited']}</span>
                            </a>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>

            <div class="m-l m-r">
                <c:forEach items="${vSysAnnouncementListVo.result}" var="v" varStatus="ide">
                    <c:if test="${vSysAnnouncementListVo.result.size()>1}">
                        <div id="div_${ide.index}" class="${ide.index==1?'':'hide'}">
                            <h3 class="al-center">${v.title}</h3>
                            <div class="al-center co-grayc2 m-b">
                                <c:if test="${v.publishStatus>0}">${views.column['sysNotice.estimate']}${soulFn:formatDateTz(v.publishTime, DateFormat.DAY_SECOND,timeZone)}${views.column['sysNotice.publish']}</c:if>
                                <c:if test="${v.publishStatus<=0}">${soulFn:formatDateTz(v.publishTime, DateFormat.DAY_SECOND,timeZone)}</c:if>
                            </div>
                            <p class="con">${v.content}</p>
                        </div>
                    </c:if>
                    <c:if test="${vSysAnnouncementListVo.result.size()==1}">
                        <div id="div_${ide.index}">
                            <h3 class="al-center">${v.title}</h3>
                            <div class="al-center co-grayc2 m-b">
                                <c:if test="${v.publishStatus>0}">${views.column['announcement.estimate']}${soulFn:formatDateTz(v.publishTime, DateFormat.DAY_SECOND,timeZone)}${views.column['sysNotice.publish']}</c:if>
                                <c:if test="${v.publishStatus<=0}">${soulFn:formatDateTz(v.publishTime, DateFormat.DAY_SECOND,timeZone)}</c:if>
                            </div>
                            <p class="con">${v.content}</p>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</form>
<soul:import res="site/announcement/SystemNoticeDetail"/>
