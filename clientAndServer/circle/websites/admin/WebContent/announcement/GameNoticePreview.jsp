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
                    <div>
                        <input type="hidden" name="result.id" value="${gameAnnouncementVo.result.id}" />
                        <input type="hidden" name="search.id" value="${gameAnnouncementVo.result.id}" />
                    </div>
                    <div class="clearfix m-l-lg line-hi34">
                        <label class="ft-bold col-sm-3 al-right">标题：</label>
                        <input type="hidden" name="result.title" value="${gameAnnouncementVo.result.title}"/>
                        <div class="col-sm-5">
                                ${gameAnnouncementVo.result.title}
                        </div>
                    </div>
                    <div class="clearfix m-l-lg line-hi34">
                        <label class="ft-bold col-sm-3 al-right">内容：</label>
                        <input type="hidden" name="result.content" value="${gameAnnouncementVo.result.content}"/>
                        <div class="col-sm-5">
                                ${gameAnnouncementVo.result.content}
                        </div>
                    </div>
                    <div class="clearfix m-l-lg line-hi34">
                        <label class="ft-bold col-sm-3 al-right">有效期：</label>
                        <input type="hidden" name="result.validityStartTime" value="${soulFn:formatDateTz(gameAnnouncementVo.result.validityStartTime, DateFormat.DAY_SECOND,timeZone)}"/>
                        <input type="hidden" name="result.validityEndTime" value="${soulFn:formatDateTz(gameAnnouncementVo.result.validityEndTime, DateFormat.DAY_SECOND,timeZone)}"/>
                        <div class="col-sm-5">
                                ${soulFn:formatDateTz(gameAnnouncementVo.result.validityStartTime, DateFormat.DAY_SECOND,timeZone)}&nbsp;-&nbsp;
                                ${soulFn:formatDateTz(gameAnnouncementVo.result.validityEndTime, DateFormat.DAY_SECOND,timeZone)}
                        </div>
                    </div>
                    <div class="clearfix m-l-lg line-hi34">
                        <label class="ft-bold col-sm-3 al-right">选择游戏：</label>
                        <%--<input type="hidden" name="result.gameId" value="${games.id}">--%>
                        <div class="col-sm-5">
                                <c:forEach var="games" items="${games}">
                                    <c:if test="${fn:contains(gameAnnouncementVo.result.gameId,games.id)}">
                                        <input type="hidden"   name="result.gameName" value="${games.name}" />${games.name}&nbsp;
                                    </c:if>
                                </c:forEach>
                            <input type="hidden" name="result.gameId" value="${gameAnnouncementVo.result.gameId}" />
                            <input type="hidden" name="result.gameName" value="${gameAnnouncementVo.result.gameName}" />
                        </div>
                    </div>
                    <div class="clearfix m-l-lg line-hi34">
                        <label class="ft-bold col-sm-3 al-right">通知方式：</label>
                        <input type="hidden" name="result.announcementType" value="${gameAnnouncementVo.result.announcementType}">
                        <div class="col-sm-5">
                            <c:if test="${gameAnnouncementVo.result.announcementType eq 'popup'}">弹窗</c:if>
                            <c:if test="${gameAnnouncementVo.result.announcementType eq 'banner'}">跑马灯</c:if>
                        </div>
                    </div>
                    <div class="clearfix m-l-lg line-hi34">
                        <label class="ft-bold col-sm-3 al-right">发布时间：</label>
                        <input type="hidden" name="result.publishTime" value="${soulFn:formatDateTz(gameAnnouncementVo.result.publishTime, DateFormat.DAY_SECOND,timeZone)}">
                        <div class="col-sm-5">
                                ${soulFn:formatDateTz(gameAnnouncementVo.result.publishTime, DateFormat.DAY_SECOND,timeZone)}
                        </div>
                    </div>
                    <div class="clearfix m-l-lg line-hi34">
                        <label class="ft-bold col-sm-3 al-right">是否重复：</label>
                        <input type="hidden" name="result.repeat" value="${gameAnnouncementVo.result.repeat}">
                        <div class="col-sm-5">
                            <c:if test="${gameAnnouncementVo.result.repeat}">是</c:if>
                            <c:if test="${!gameAnnouncementVo.result.repeat}">否</c:if>
                        </div>
                    </div>
                    <div class="clearfix m-l-lg line-hi34">
                        <c:if test="${gameAnnouncementVo.result.repeat}">
                            <label class="ft-bold col-sm-3 al-right">重复间隔：</label>
                            <input type="hidden" name="result.repeatTime" value="${gameAnnouncementVo.result.repeatTime}" >
                            <input type="hidden" name="result.repeatUnit" value="${gameAnnouncementVo.result.repeatUnit}">
                            <div class="col-sm-5">
                                    ${gameAnnouncementVo.result.repeatTime}&nbsp;${gameAnnouncementVo.result.repeatUnit}
                            </div>
                        </c:if>
                    </div>

                </div>
                <div class="operate-btn ">
                    <soul:button target="gameNoticePreviewLastStep" text="${views.common['previous']}" opType="function" cssClass="btn btn-outline btn-filter btn-lg"/>
                    <soul:button target="saveGameNotice" text="${views.column['sysNotice.publishcontent']}" opType="function" cssClass="btn btn-filter btn-lg"/>
                </div>
            </div>
        </div>
    </div>
</form:form>

<soul:import res="site/announcement/GameNoticePreview"/>
