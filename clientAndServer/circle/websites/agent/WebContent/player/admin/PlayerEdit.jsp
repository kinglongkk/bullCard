<!DOCTYPE html>
<%--@elvariable id="command" type="g.model.player.vo.VSysUserVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<%--<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<html>
<head>
    <title>${views.common['edit']}</title>
    <%@ include file="/include/include.head.jsp" %>
</head>
<body>
<!--编辑弹窗-->
<!-- 面包屑 开始 -->
<div class="position-wrap clearfix">
    <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont"></i> </a></h2>
    <span>${views.sysResource['角色']}</span>
    <span>/</span><span>${views.sysResource['玩家管理']}</span>
    <soul:button target="goToLastPage" refresh="true"
                 cssClass="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn" text="" opType="function">
        <em class="fa fa-caret-left"></em>${views.common['return']}
    </soul:button>
    <a href="/player/playerList.html" nav-target="mainFrame" name="returnView" style="display: none"></a>
</div>
<!-- 面包屑 结束 -->
<form:form method="post">
    <div id="validateRule" style="display: none">${validateRule}</div>
    <div class="wrapper white-bg shadow">
        <div class="present_wrap"><b>${views.common['edit']}</b></div>
        <!-- 工具栏 开始 -->
        <div class="clearfix m-t-sm">
            <!-- 表格 开始 -->
                <div class="form-group clearfix m-b-sm">
                    <label class="col-sm-3  al-right line-hi34 ft-bold">${views.column['myAccount.account']} : </label>
                    <div class="col-sm-5 line-hi34">${command.result.username}</div>
                    <input type="hidden" name="result.id" value="${command.result.id}"/>
                    <input type="hidden" name="result.username" value="${command.result.username}"/>
                </div>
                <div class="form-group clearfix m-b-sm">
                    <label class="col-sm-3  al-right line-hi34 ft-bold">${views.column['myAccount.password']}: </label>
                    <div class="col-sm-5 line-hi34">
                        <input type="password" value="${command.result.password}" class="form-control" name="newPassword">
                    </div>
                </div>
                <div class="form-group clearfix m-b-sm">
                    <label class="col-sm-3  al-right line-hi34 ft-bold">${views.column['myAccount.rePassword']}: </label>
                    <div class="col-sm-5 line-hi34">
                        <input type="password" value="${command.result.password}" class="form-control" name="newRePassword">
                    </div>
                </div>
                <div class="form-group clearfix m-b-sm">
                    <label class="col-sm-3  al-right line-hi34 ft-bold">${views.column['role.player.nickName']}: </label>
                    <div class="col-sm-5 line-hi34">
                        <input value="${command.result.nickname}" class="form-control" name="result.nickname">
                    </div>
                </div>
                <!--表格内容 结束-->
            <div class="operate-btn">
                <soul:button cssClass="btn btn-filter btn-lg" text="${views.common['OK']}" opType="ajax" dataType="json"
                             target="${root}/player/savePlayer.html" precall="validateForm"
                             post="getCurrentFormData"
                             callback="queryPlayer"/>
                <soul:button target="goToLastPage" refresh="true" cssClass="btn btn-outline btn-filter btn-lg m-r"
                             text="${views.common['cancel']}" opType="function"/>
            </div>
        </div>
    </div>
</form:form>

</body>
<soul:import res="site/sys/role/player/Edit"/>
</html>


