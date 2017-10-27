<%--@elvariable id="command" type="g.model.company.operator.vo.SynnouncementListVo"--%>
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
                    <a href="/vGameAnnouncement/gameNoticeHistory.html" nav-target="mainFrame"
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
                            <input type="hidden" name="result.id" value="${gameAnnouncementVo.result.id}">
                            <input type="hidden" name="language[${ide.index}]" value="${item}">
                            <!-- 标题 -->
                            <div class="clearfix m-l-lg m-t-md line-hi34 ${ide.index==0?'':'hide'}">
                                <label class="ft-bold col-sm-3 al-right">${views.column['sysNotice.title']}：</label>
                                <div class="col-sm-5">
                                    <input id="titleText" type="text" name="result.title" class="form-control p-r-70" maxlength="100" value="${gameAnnouncementVo.result.title}"/>
                                    <p class="character msg${ide.index}">0/100</p>
                                </div>
                            </div>

                            <div class="clearfix m-l-lg m-t-md line-hi34 ${ide.index==0?'':'hide'}">
                                <label class="ft-bold col-sm-3 al-right">${views.column['sysNotice.publishcontent']}：</label>
                                <!-- 内容 -->
                                <div class="col-sm-6">
                                    <script id="container" name="result.content" type="text/plain" >${gameAnnouncementVo.result.content}</script>
                                    <div id="ContentMes111" style="display: none; left: 15px; top: 375px;width: 100px; background:black;">
                                        <div class="tip-inner tip-bg-image">
                                            <font color="white" size="2px" >内容不能为空</font>
                                        </div>
                                        <div class="tip-arrow tip-arrow-top" ></div>
                                    </div>
                                </div>
                                <!-- UEditor end -->
                            </div>

                            <!-- 有效期 -->
                            <div class="clearfix m-l-lg m-t-md">
                                <label class="ft-bold col-sm-3 al-right">有效期：</label>
                                <div class="col-sm-8 line-hi34">
                                    <div class="form-group clearfix pull-left content-width-limit-400">
                                        <div class="input-group date " >
                                            <gb:dateRange useRange="true" format="${DateFormat.DAY}" style="width: 400px" startName="result.validityStartTime"
                                                          endName="result.validityEndTime"
                                                          startDate="${gameAnnouncementVo.result.validityStartTime}" endDate="${gameAnnouncementVo.result.validityEndTime}" useTime="true"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- 选择游戏 -->
                            <div class="clearfix m-l-lg m-t-md">
                                <label class="ft-bold col-sm-3 al-right line-hi34">选择游戏：</label>
                                <div class="col-sm-9 variable_wrap bdn _editTags" data-ue-key="editContent${ide.index}">
                                    <c:forEach var="game" items="${game}">
                                        <input type="checkbox" name="result.gameId" value="${game.id}" ${fn:contains(gameAnnouncementVo.result.gameId,game.id)?'checked':''} />
                                        &nbsp;
                                        <input type="checkbox" name="result.gameName" value="${game.name}" style="display:none" ${fn:contains(gameAnnouncementVo.result.gameName,game.name)?'checked':''} />${game.name}
                                        &nbsp;
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    <!-- 通知方式 -->
                    <div class="clearfix m-l-lg m-t-md line-hi34">
                        <label class="ft-bold col-sm-3 al-right">通知方式：</label>
                        <input type="hidden" name="result.announcementType_" value="${sysAnnouncementVo.result.id}">
                        <div class="col-sm-8 line-hi34">
                            <label>
                                <input type="radio" class="i-checks announcement"
                                       value="banner" ${gameAnnouncementVo.result.announcementType eq 'banner'?"checked":''}
                                       name="result.announcementType">&nbsp;跑马灯&nbsp;&nbsp;
                            </label>
                            <label>
                                <input type="radio" class="i-checks announcement"
                                       value="popup" ${gameAnnouncementVo.result.announcementType eq 'popup'?"checked":''}
                                       name="result.announcementType">&nbsp;弹窗
                            </label>
                        </div>
                        <label class="ft-bold col-sm-3 al-right"></label>
                    </div>
                    <!-- 发布时间 -->
                    <div class="clearfix m-l-lg m-t-md line-hi34">
                        <label class="ft-bold col-sm-3 al-right">
                            发布时间：
                        </label>
                        <div class="col-sm-8 line-hi34">
                            <div class="form-group clearfix pull-left content-width-limit-400">
                                <div class="input-group date ">
                                    <gb:dateRange format="${DateFormat.DAY_SECOND}" name="result.publishTime" value="${gameAnnouncementVo.result.publishTime}"
                                                  minDate="${dateQPicker.now}"  useTime="true"></gb:dateRange>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 定时发布end -->

                    <!-- 重复发布 -->
                    <div class="clearfix m-l-lg m-t-md line-hi34 " >
                        <label class="ft-bold col-sm-3 al-right">
                            是否重复：
                        </label>
                        <div class="col-sm-8 line-hi34">
                            <input name="result.repeat" aria-selected="true" type="radio" value="true" class="i-checks agentNotice" ${gameAnnouncementVo.result.repeat eq 'true'?"checked":''} >&nbsp;&nbsp;是&nbsp;&nbsp;
                            <input name="result.repeat"  type="radio" value="false"  class="i-checks agentNotice" ${gameAnnouncementVo.result.repeat eq 'false'?"checked":''}>&nbsp;否
                        </div>

                    </div>

                    <c:if test="${gameAnnouncementVo.result.repeat || gameAnnouncementVo.result.repeat == null || gameAnnouncementVo.result.repeat == '' }">
                        <div id="divRepeat" class="clearfix m-l-lg m-t-md line-hi34 " style="" >
                            <label class="ft-bold col-sm-3 al-right">
                                重复间隔：
                            </label>
                            <input id="repeatTime" type="text" name="result.repeatTime" onkeyup="this.value=this.value.replace(/\D/g, '')" onblur=window.top.page.checkTimeValue()
                                   value="${gameAnnouncementVo.result.repeatTime}" style="height: 25px;width: 80px;margin-left: 15px;"/>
                            <%--只许输入数字事件：this.value=this.value.replace(/\D/g, '')--%>
                            <select style=" display:none;" ></select>
                            <select id="repeatUnit" name="result.repeatUnit">
                                <option value="10" ${gameAnnouncementVo.result.repeatUnit eq '10'?"selected":''}>天</option>
                                <option value="20" ${gameAnnouncementVo.result.repeatUnit eq '20'?"selected":''}>时</option>
                                <option value="30" ${gameAnnouncementVo.result.repeatUnit eq '30'?"selected":''}>分</option>
                                <option value="40" ${gameAnnouncementVo.result.repeatUnit eq '40'?"selected":''}>秒</option>
                            </select>
                            <div id="timeMes" style="display: none; margin-left: 434px;height: 30px;width: 110px; background:black;">
                                <div class="tip-inner tip-bg-image"><font color="white" size="2px" >请输入正确的时间</font></div><div class="tip-arrow tip-arrow-top" ></div>
                            </div>
                        </div>
                    </c:if>

                </div>
                <div class="operate-btn sysNoticePreview">
                    <soul:button target="gameNoticePreview" precall="validateForm" text="${views.column['sysNotice.preview']}" opType="function"
                                 cssClass="btn btn-filter btn-lg"/>
                </div>
            </div>
        </div>
    </div>
</form:form>

<soul:import res="site/announcement/GameNotice"/>