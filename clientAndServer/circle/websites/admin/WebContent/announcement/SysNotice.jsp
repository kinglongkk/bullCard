<%--@elvariable id="command" type="g.model.company.operator.vo.SysAnnouncementListVo"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/include/include.inc.jsp" %>
<form:form>
    <div class="row">
        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
            <span>${views.sysResource['发布公告']}</span>
            <a href="javascript:void(0)" class="pull-right siteMap"><i class="fa fa-sitemap"></i></a>
        </div>
        <div class="col-lg-12 m-b">
            <div id="validateRule" style="display: none">${validate}</div>
            <div class="wrapper white-bg shadow">
                <!--筛选条件-->
                <div class="filter-wraper clearfix p-xs border-b-1">
                    <a href="/vSysAnnouncement/sysNoticeHistory.html" nav-target="mainFrame"
                       class="btn btn-outline btn-filter pull-right m-r-sm">${views.column['sysNotice.history']}</a>
                </div>
                <!--表格内容-->
                <div id="editable_wrapper" class="dataTables_wrapper" role="grid">

                    <ul class="artificial-tab clearfix bg-gray">
                        <li class="col-sm-3"></li>
                        <c:forEach items="${languageList}" var="item" varStatus="ide">
                            <li class="m-l-lg">
                                <a id="index_${ide.index}" data-language="${item}"
                                   class="click_index ${ide.index==0?'current':''}">
                                        ${fn:substringBefore(dicts.common.language[item], '#')}&nbsp;
                                    <span class="con">${views.column['sysNotice.unedit']}</span>
                                </a>
                            </li>
                        </c:forEach>

                        <li class="pull-right m-t-md">
                            <div class="btn-group">
                                <button data-toggle="dropdown" class="btn btn-link dropdown-toggle fzyx">
                                        ${views.service['announcement.copy']}&nbsp;&nbsp;<span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu pull-right">
                                    <c:forEach items="${languageList}" var="item" varStatus="status">
                                        <li class="temp hide">
                                            <a href="javascript:void(0)" copy-language="${item}"
                                               class="co-gray">${dicts.common.local[item]}</a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </li>
                    </ul>

                    <c:forEach items="${languageList}" var="item" varStatus="ide">
                        <div class="div_length" id="div_${ide.index}" language="${item}">
                            <input type="hidden" name="language[${ide.index}]" value="${item}">
                            <div class="clearfix m-l-lg m-t-md line-hi34 ${ide.index==0?'':'hide'}">
                                <label class="ft-bold col-sm-3 al-right">${views.column['sysNotice.title']}：</label>
                                <div class="col-sm-5">
                                    <input type="text" name="title[${ide.index}]" class="form-control p-r-70" maxlength="100"
                                           placeholder="${views.column['sysNotice.title']}" value="${sysAnnouncementVo.title[ide.index]}">
                                    <p class="character msg${ide.index}">0/100</p>
                                </div>
                            </div>

                            <div class="clearfix m-l-lg m-t-md line-hi34 ${ide.index==0?'':'hide'}">
                                <label class="ft-bold col-sm-3 al-right">${views.column['sysNotice.publishcontent']}：</label>
                                <div class="col-sm-5">
                                    <textarea class="form-control p-b-30" name="content[${ide.index}]" maxlength="2000"
                                              placeholder="${views.column['sysNotice.publishcontent']}">${sysAnnouncementVo.content[ide.index]}</textarea>
                                    <p class="character textareaMsg${ide.index}">0/2000</p>
                                </div>
                            </div>

                            <div class="clearfix m-l-lg m-t-md">
                                <label class="ft-bold col-sm-3 al-right line-hi34">${views.column['sysNotice.variableLabel']}：</label>
                                <div class="col-sm-9 variable_wrap bdn _editTags" id="content[${ide.index}]" data-ue-key="editContent${ide.index}">
                                    <a href="javascript:void(0)" class="variable">${views.column['sysNotice.userName']} &lt;<span>{user}</span>&gt;</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    <div class="clearfix m-l-lg m-t-md line-hi34">
                        <label class="ft-bold col-sm-3 al-right">${views.column['sysNotice.type']}：</label>
                        <input type="hidden" name="result.id" value="${sysAnnouncementVo.result.id}">
                        <div class="col-sm-8 line-hi34">
                            <label>
                                <input type="radio" class="i-checks announcement" checked
                                       value="general" ${sysAnnouncementVo.result.announcementType eq 'general'?"checked":''}
                                       name="result.announcementType">${views.column["sysNotice.general"]}
                            </label>
                            <label>
                                <input type="radio" class="i-checks announcement"
                                       value="importance" ${sysAnnouncementVo.result.announcementType eq 'importance'?"checked":''}
                                       name="result.announcementType">${views.column['sysNotice.importance']}
                            </label>
                        </div>
                        <label class="ft-bold col-sm-3 al-right"></label>
                        <div class="col-sm-8 line-hi34">
                            <label>
                                <input type="checkbox" class="i-checks agentNotice"
                                       value="agentNotice" ${sysAnnouncementVo.agentNotice eq 'agentNotice'?"checked":''}
                                       name="agentNotice">${views.column["sysNotice.personal"]}
                            </label>
                        </div>
                    </div>
                    <div class="personal  <c:if test="${sysAnnouncementVo.agentNotice != 'agentNotice'}">hide</c:if> clearfix m-l-lg line-hi34">
                        <label class="ft-bold col-sm-3 al-right"></label>
                        <div class="col-sm-8 line-hi34">
                            <div class="form-group clearfix pull-left content-width-limit-250 ">
                                <select name="result.groupCode" id="chooseAgent">
                                    <option value="allAgent" <c:if test="${sysAnnouncementVo.result.groupCode=='allAgent'}">selected</c:if>>${views.column["sysNotice.allAgent"]}</option>
                                    <option value="appointAgent" <c:if test="${sysAnnouncementVo.result.groupCode=='appointAgent'}">selected</c:if>>${views.column["sysNotice.appointAgent"]}</option>
                                </select>
                                <span class="co-grayc2 m-l-sm _agent_added_all hide">${views.column["sysNotice.allAgentSendAnnouncement"]}</span>
                                <span class="co-grayc2 m-l-sm _agent_added_length hide">${views.column["sysNotice.alreadyChooseAgent"]}</span>
                            </div>
                        </div>
                    </div>
                    <%--指定代理商页面--%>
                    <div id="appointAgent" class="clearfix m-t-sm line-hi34 bg-gray level <c:if test="${sysAnnouncementVo.result.groupCode!='appointAgent'}">hide</c:if>">
                            <input id="_agent" type="hidden" name="agentIds" value="${sysAnnouncementVo.agentIds}">
                            <div class="col-sm-9">
                                <div class="clearfix addAgents">
                                    <table style="margin-left: 445px;">
                                        <tr>
                                            <td>${views.column['myAccount.account']}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                    ${views.column['myAccount.mobile']}</td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <select multiple class="_addAgent" style="width:300px;height:300px;">
                                                    <c:forEach var="i" items="${agentList}">
                                                        <option data-search="${i.username}" value="${i.id}">
                                                            ${i.username}&nbsp;&nbsp;&nbsp;&nbsp;
                                                            ${soulFn:overlayTel(i.mobilePhone)}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <div class="pcenter">
                                                    <div>
                                                        <soul:button target="addAgent" text="" opType="function"><i class="fa fa-arrow-right"></i></soul:button>
                                                        <soul:button target="removeAgent" text="" opType="function"><i class="fa fa-arrow-left"></i></soul:button>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>
                                                <%--被选择的代理列表--%>
                                                <select multiple class="_agent_added" style="width:300px;height: 300px;">
                                                    <c:forEach var="i" items="${selectAgentList}">
                                                        <option data-search="${i.username}" value="${i.id}">
                                                            ${i.username}&nbsp;&nbsp;&nbsp;&nbsp;
                                                            ${soulFn:overlayTel(i.mobilePhone)}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>

                    <div class="clearfix m-l-lg m-t-md line-hi34">
                        <label class="ft-bold col-sm-3 al-right">
                            <input type="checkbox" name="result.timingSend" <c:if test="${sysAnnouncementVo.result.timingSend}">checked</c:if> class="i-checks send">定时发布：
                        </label>

                        <div class="col-sm-8 line-hi34">
                            <div class="form-group clearfix pull-left content-width-limit-400">
                                <div class="input-group date <c:if test="${!sysAnnouncementVo.result.timingSend}">hide</c:if> ">
                                    <gb:dateRange format="${DateFormat.DAY_SECOND}" name="result.publishTime" value="${dateQPicker.now}"
                                                  minDate="${dateQPicker.now}"  useTime="true"></gb:dateRange>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="operate-btn sysNoticePreview">
                    <soul:button target="sysNoticePreview" precall="validateForm" text="${views.column['sysNotice.preview']}" opType="function"
                                 cssClass="btn btn-filter btn-lg"/>
                </div>
            </div>
        </div>
    </div>
</form:form>

<soul:import res="site/announcement/SysNotice"/>

