<%--@elvariable id="command" type="g.model.company.operator.vo.SystemAnnouncementListVo"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/include/include.inc.jsp" %>
<form:form>
    <div class="row">
        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
            <span>${views.sysResource['服务']}</span><span>/</span><span>${views.sysResource['发布公告']}</span>
            <a href="javascript:void(0)" class="pull-right siteMap"><i class="fa fa-sitemap"></i></a>
        </div>
        <div class="col-lg-12 m-b">
            <div class="wrapper white-bg shadow">

                <!--表格内容-->
                <div id="editable_wrapper" class="dataTables_wrapper" role="grid">
                    <ul class="artificial-tab clearfix bg-gray">
                        <li class="col-sm-3"></li>
                        <c:forEach items="${languageList}" var="item" varStatus="ide">
                            <li class="m-l-lg">
                                <a id="index_${ide.index}" class="click_view_index ${ide.index==1?'current':''}">
                                        ${fn:substringBefore(dicts.common.language[item], '#')}&nbsp;
                                    <span class="con">${views.column['sysNotice.edited']}</span>
                                </a>
                                <input type="hidden" name="language[${ide.index}]" value="${item}">
                            </li>
                        </c:forEach>
                    </ul>
                    <div class="clearfix m-l-lg line-hi34">
                        <label class="ft-bold col-sm-3 al-right">${views.column['sysNotice.type']}：</label>
                        <div class="col-sm-5">${views.column["sysNotice.".concat(sysAnnouncementVo.result.announcementType)]}</div>
                        <input type="hidden" name="search.id" value="${sysAnnouncementVo.result.id}">
                        <input type="hidden" name="agentIds" value="${sysAnnouncementVo.agentIds}">
                        <input type="hidden" name="result.groupCode" value="${sysAnnouncementVo.result.groupCode}">
                        <input type="hidden" name="result.announcementType" value="${sysAnnouncementVo.result.announcementType}">
                        <input type="hidden" name="agentNotice" value="${sysAnnouncementVo.agentNotice}">
                        <input type="hidden" name="result.timingSend" value="${sysAnnouncementVo.result.timingSend}">
                        <input type="hidden" name="result.publishTime" value="${soulFn:formatDateTz(sysAnnouncementVo.result.publishTime, DateFormat.DAY_SECOND,timeZone)}">
                    </div>
                    <c:if test="${sysAnnouncementVo.agentNotice eq 'agentNotice'}">
                        <div class="clearfix m-l-lg line-hi34">
                            <label class="ft-bold col-sm-3 al-right">${messages.common['alreadyChoose']}</label>
                            <div class="col-sm-5">${views.column["sysNotice.".concat(sysAnnouncementVo.result.groupCode)]}</div>
                        </div>
                    </c:if>
                    <div class="clearfix m-l-lg line-hi34">
                        <label class="ft-bold col-sm-3 al-right">${views.column['sysNotice.publishTime']}：</label>
                        <div class="col-sm-5">
                            ${sysAnnouncementVo.result.timingSend==false?views.column['sysNotice.immediate']:views.column['sysNotice.timing']}
                            <c:if test="${sysAnnouncementVo.result.timingSend=='true'}">
                                &nbsp;&nbsp;${soulFn:formatDateTz(sysAnnouncementVo.result.publishTime, DateFormat.DAY_SECOND,timeZone)}
                            </c:if>
                        </div>
                    </div>
                    <div class="clearfix m-l-lg line-hi34">
                        <label class="ft-bold col-sm-3 al-right">${views.column['sysNotice.title']}：</label>
                        <c:forEach items="${sysAnnouncementVo.language}" var="con" varStatus="t">
                            <div id="t_div_${t.index}" class="col-sm-5 <c:if test="${fn:length(sysAnnouncementVo.language)>1}">${t.index==1?"":"hide"}</c:if>" >
                                    ${sysAnnouncementVo.title[t.index]}
                                <input type="hidden" name="title[${t.index}]" value="${sysAnnouncementVo.title[t.index]}">
                            </div>
                        </c:forEach>
                    </div>
                    <div class="clearfix m-l-lg line-hi34">
                        <label class="ft-bold col-sm-3 al-right">${views.column['sysNotice.publishcontent']}：</label>
                        <c:forEach items="${sysAnnouncementVo.language}" var="con" varStatus="ide">
                            <div id="c_div_${ide.index}" class="col-sm-5 <c:if test="${fn:length(sysAnnouncementVo.language)>1}">${ide.index==1?"":"hide"}</c:if>" >
                                    ${sysAnnouncementVo.content[ide.index]}
                                <input type="hidden" name="content[${ide.index}]" value="${sysAnnouncementVo.content[ide.index]}">
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div class="operate-btn ">
                    <soul:button target="sysNoticePreviewLastStep" text="${views.common['previous']}" opType="function" cssClass="btn btn-outline btn-filter btn-lg"/>
                    <soul:button target="saveSysNotice" text="${views.column['sysNotice.publishcontent']}" opType="function" cssClass="btn btn-filter btn-lg"/>
                </div>
            </div>
        </div>
    </div>
</form:form>

<soul:import res="site/announcement/SysNoticePreview"/>
