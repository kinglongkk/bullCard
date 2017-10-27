<%--
  Created by IntelliJ IDEA.
  User: jeff
  Date: 15-12-18
  Time: 上午9:27
--%>
<%--@elvariable id="command" type="g.model.master.player.vo.AccountVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<html>
    <head>
        <%@ include file="/include/include.head.jsp" %>
    </head>
    <body>
        <form:form>
            <div style="display: none" id="validateRule">${command.validateRule}</div>
            <input type="hidden" name="result.id" value="${command.result.id}">
            <input type="hidden" name="type"  value="${command.type}">
            <div class="modal-body">
                <div class="form-group clearfix m-b-sm">
                    <label class="col-xs-3 al-right">${views.common['optionAccount.account']}</label>
                    <div class="col-xs-9">${command.result.username}</div>
                </div>
                <div class="form-group clearfix m-b-sm">
                    <label class="col-xs-3 al-right">${views.common['optionAccount.onlineStatus']}</label>
                    <c:choose>
                        <c:when test="${command.isLogin}">
                            <div class="col-xs-9">${views.common['optionAccount.online']}</div>
                        </c:when>
                        <c:otherwise>
                            <div class="col-xs-9">${views.common['optionAccount.offOnline']}</div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <%--<div class="form-group clearfix m-b-sm">--%>
                    <%--<label class="col-xs-3 al-right line-hi34">${views.common['optionAccount.reason']}</label>--%>
                    <%--<div class="col-xs-9">--%>
                        <%--<div class="input-group date">--%>
                            <%--<select callback="setDisabledReason" data-placeholder="${views.common['optionAccount.choosenReason']}" name="result.freezeTitle" class="btn-group chosen-select-no-single reason-select" tabindex="9">--%>
                                <%--<option value="">${messages["playerTag"]["disabled_please_reason"]}</option>--%>
                                <%--<c:forEach items="${command.noticeLocaleTmpls}" var="i">--%>
                                    <%--<option value="${i.title}" holder="${i.content}" groupCode="${i.groupCode}">--%>
                                        <%--${fn:substring(i.title, 0, 20)}<c:if test="${fn:length(i.title)>20}">...</c:if>--%>
                                    <%--</option>--%>
                                <%--</c:forEach>--%>
                            <%--</select>--%>
                            <%--<input type="hidden" name="groupCode">--%>
                            <%--<span class="input-group-addon bdn">--%>
                                <%--<soul:button target="reasonPreviewMore.editTmpl" text="" opType="function" cssClass="m-l-sm _edit">--%>
                                    <%--${views.common['editTmpl']}--%>
                                <%--</soul:button>--%>
                            <%--</span>--%>
                        <%--</div>--%>
                        <%--<div class="clearfix m-t-sm">--%>
                            <%--<soul:button target="previewMore" cssClass="pull-right _edit dropdown-toggle" text="" opType="function" toggle="false">--%>
                                <%--${views.common['previewMore']}--%>
                            <%--</soul:button>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="panel blank-panel p-b-sm" id="previewMore" style="display:none">--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group clearfix m-b-sm previewMore_some">--%>
                    <%--<label class="col-xs-3 al-right line-hi34">${views.common['optionAccount.content']}</label>--%>
                    <%--<div class="col-xs-9">--%>
                        <%--<div class="gray-chunk clearfix reason-content"></div>--%>
                        <%--<input type="hidden" value="" name="result.freezeTitle">--%>
                        <%--<input type="hidden" value="${command.result.id}" name="result.id">--%>
                    <%--</div>--%>
                <%--</div>--%>
                <div class="form-group clearfix m-b-sm">
                    <label class="col-xs-3 al-right line-hi34">${views.common['optionAccount.remark']}</label>
                    <div class="col-xs-9">
                        <textarea class="form-control _edit" data-preview=".preview_remark" name="remark"></textarea>
                        <div class="gray-chunk clearfix _preview hide preview_remark"></div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <soul:button target="toPreview" cssClass="btn btn-filter _edit" precall="" text="" opType="function">${views.common['optionAccount.submitAndPreview']}</soul:button>
                <%--<button type="button" class="btn btn-filter" data-toggle="modal" data-target="#commentsyl"></button>--%>
                <soul:button target="closePage" cssClass="btn btn-outline btn-filter _edit" returnValue="true" text="" opType="function">${views.common['cancel']}</soul:button>

                <soul:button target="toPreview" edit="edit" opType="function" text="" cssClass="btn btn-outline btn-filter _preview hide">${views.common['optionAccount.backEdit']}</soul:button>
                <soul:button target="toConfirm"  text="" opType="function"  cssClass="btn btn-filter _preview hide">${views.common['commit']}</soul:button>
                <%--<soul:button target="${root}/share/account/setAccountDisabled.html" returnValue="true" callback="saveCallbak" text="" opType="ajax" post="getCurrentFormData" cssClass="btn btn-filter _preview hide">提交</soul:button>--%>
            </div>
        </form:form>
    </body>
    <%@ include file="/include/include.js.jsp"%>
    <script>
        curl(['site/player/account/DisabledAccount'], function(Page) {
            page = new Page();
            page.bindButtonEvents();
        });
    </script>
</html>
