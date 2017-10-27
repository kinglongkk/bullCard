<!DOCTYPE HTML>
<%-- @elvariable id="command" type="org.soul.model.sys.po.SysParam" --%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<html lang="zh-CN">
<html>
<head>
  <%@ include file="/include/include.head.jsp" %>
</head>
<body>
<form:form>
    <div class="modal-body">
        <div class="col-xs-12">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>${views.setting['preference.editTone.soundsName']}</th>
                        <th>${views.setting['preference.editTone.listeningTest']}</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${sysTones}" var="t">
                    <tr>
                        <td>
                            <label>
                                <div class="pull-left"><input type="radio" class="i-checks" name="result.paramValue" ${tone.paramValue==t.paramValue?'checked':''} value="${t.paramValue}">${dicts[t.module][t.paramType][t.paramCode]}
                            </label>
                        </td>
                        <td><audio src="${root}/static/${t.paramValue}" preload="auto"></audio></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="modal-footer">
        <input type="hidden" name="result.id" value="${tone.id}"/>
        <soul:button precall="checkOne" tag="button" opType="ajax" dataType="json" target="${root}/param/uploadTone.html" post="getCurrentFormData" callback="saveCallbak" cssClass="btn btn-filter" text="${views.common['OK']}">${views.common['OK']}</soul:button>
        <soul:button target="closePage" tag="button" opType="function" text="${views.common['cancel']}" cssClass="btn btn-outline btn-filter">${views.common['cancel']}</soul:button>
    </div>
</form:form>
</body>
</html>
<%@ include file="/include/include.js.jsp" %>

<script src="${resComRoot}/js/audiojs/audio.min.js"></script>
<script>
    audiojs.events.ready(function() {
        audiojs.createAll();
    });
</script>
<soul:import res="site/setting/PlatformParam/preferences/PreferenceEdit"/>