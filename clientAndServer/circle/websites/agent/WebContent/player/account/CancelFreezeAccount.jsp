<%--@elvariable id="command" type="g.model.player.vo.VSysUserVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<html>
    <head>
        <%@ include file="/include/include.head.jsp" %>
    </head>
    <body>
        <form:form>
            <div class="modal-body">
                <div class="form-group clearfix m-b-sm">
                    <label class="col-xs-3 al-right">${views.common['optionAccount.account']}</label>
                    <div class="col-xs-9">${command.result.username}
                        <c:choose>
                            <c:when test="${command.result.status==1}">
                                <span class="m-l-sm co-green">${views.common['optionAccount.online']}</span>
                            </c:when>
                            <c:otherwise>
                                <span class="m-l-sm co-grayc2">${views.common['optionAccount.offOnline']}</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="form-group clearfix m-b-sm">
                    <label class="col-xs-3 al-right">账号冻结：</label>
                    <div class="col-xs-9">
                        <input type="checkbox" name="my-checkbox" disabled data-size="mini" checked>
                    </div>
                </div>
                <div class="form-group clearfix m-b-sm">
                    <label class="col-xs-3 al-right line-hi34">冻结时间：</label>
                    <div class="col-xs-9">
                        <div class="input-group">
                            <span class="input-group-addon bdn">开始时间：${soulFn:formatDateTz(command.result.freezeStartTime, DateFormat.DAY_SECOND,timeZone)}</span>
                            <span class="input-group-addon bdn">截止时间：${soulFn:formatDateTz(command.result.freezeEndTime, DateFormat.DAY_SECOND,timeZone)}</span>
                        </div>
                    </div>
                </div>
                <div class="form-group clearfix m-b-sm line-hi34">
                    <label class="col-xs-3 al-right">冻结原因：</label>
                    <div class="col-xs-9">
                        ${command.result.freezeTitle}
                    </div>
                </div>
                <div class="form-group clearfix m-b-sm line-hi34">
                    <label class="col-xs-3 al-right">内容：</label>
                    <div class="col-xs-9">
                            ${command.result.freezeContent}
                    </div>
                </div>
                <div class="form-group clearfix m-b-sm">
                    <label class="col-xs-3 al-right line-hi34">备注：</label>
                    <div class="col-xs-9">
                        <div class="gray-chunk clearfix">${command.result.accountFreezeRemark}</div>
                    </div>
                </div>
                <div class="form-group clearfix">
                    <label class="col-xs-3 al-right">操作人：</label>
                    <div class="col-xs-9">${command.result.username}&nbsp;&nbsp;&nbsp;&nbsp;${soulFn:formatDateTz(command.result.freezeTime, DateFormat.DAY_SECOND,timeZone)}</div>
                </div>
            </div>
            <div class="modal-footer">
                <soul:button target="${root}/share/account/cancelAccountFreeze.html?search.id=${command.result.id}" tag="button" cssClass="btn btn-filter" callback="closePage" text="" opType="ajax">
                    ${views.common['commit']}
                </soul:button>
                <soul:button target="closePage" text="${views.common['cancel']}" cssClass="btn btn-outline btn-filter" opType="function" callback="saveCallbak"/>
            </div>
        </form:form>
    </body>
</html>
<%@ include file="/include/include.js.jsp" %>
<soul:import res="site/player/account/FreezeAccount"/>
