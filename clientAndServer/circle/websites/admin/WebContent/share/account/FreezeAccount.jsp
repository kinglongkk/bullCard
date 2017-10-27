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
                <div class="form-group clearfix m-b-sm col-xs-12">
                    <label class="col-xs-3 al-right">${views.common['optionAccount.account']}</label>
                    <div class="col-xs-9">${command.result.username}
                        <c:choose>
                            <c:when test="${command.isLogin}">
                                <span class="m-l-sm co-green">${views.common['optionAccount.online']}</span>
                            </c:when>
                            <c:otherwise>
                                <span class="m-l-sm co-grayc2">${views.common['optionAccount.offOnline']}</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="form-group clearfix m-b-sm col-xs-12">
                    <label class="col-xs-3 al-right line-hi34">${views.common['optionAccount.FreezeTimeEnum']}：</label>
                    <div class="col-xs-8 p-x">
                        <div>
                            <gb:select prompt="" name="chooseFreezeTime" value="${empty command.chooseFreezeTime ? command.freezeTimeForerve:command.chooseFreezeTime}"  list="${command.freezeTime}"></gb:select>
                        </div>
                    </div>
                </div>

                <div class="form-group clearfix m-b-sm col-xs-12">
                    <label class="col-xs-3 al-right">${views.common['optionAccount.FreezeeRason']}</label>
                    <div class="col-xs-8 p-x">
                        <textarea class="form-control _edit" name="remark" data-preview=".preview_remark"></textarea>
                        <div class="gray-chunk clearfix _preview hide preview_remark"></div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <soul:button target="previewOrEdit" tag="button" text="" precall="validateForm" cssClass="_edit btn btn-filter" opType="function">
                    ${views.common['optionAccount.submitAndPreview']}
                </soul:button>
                <soul:button target="closePage" text="" cssClass="btn btn-outline btn-filter _edit" opType="function" tag="button">
                    ${views.common['cancel']}
                </soul:button>
                <soul:button edit="edit" target="previewOrEdit" tag="button" cssClass="btn btn-outline btn-filter _preview hide" text="" opType="function">
                    ${views.common['optionAccount.backEdit']}
                </soul:button>
                <soul:button target="${root}/share/account/setFreezeAccount.html" returnValue="true" post="getCurrentFormData" tag="button" cssClass="btn btn-filter _preview hide" callback="saveCallbak" text="" opType="ajax">
                    ${views.common['commit']}
                </soul:button>
            </div>
        </form:form>
    </body>
    <%@ include file="/include/include.js.jsp" %>
    <script>
        curl(['site/share/account/FreezeAccount'], function(Page) {
            page = new Page();
            page.bindButtonEvents();
        });
    </script>
</html>
