<%--
  Created by IntelliJ IDEA.
  User: jeff
  Date: 15-10-26
  Time: 上午10:09
--%>

<%--@elvariable id="sysRoleListVo" type="g.model.setting.vo.SysRoleListVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<%@ include file="/include/include.head.jsp" %>
<form:form>
    <div id="validateRule" style="display: none">${sysRoleListVo.validateRule}</div>
    <div class="row">
        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
            <span>${views.sysResource[resourceKey]}</span><span>/</span><span>${views.sysResource['子账号']}</span>
            <soul:button tag="a" target="goToLastPage" refresh="true" text="" opType="function" cssClass="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn">
                <em class="fa fa-caret-left"></em>${views.common['return']}
            </soul:button>
            <a href="javascript:void(0)" class="pull-right siteMap"><i class="fa fa-sitemap"></i></a>
        </div>
        <div class="col-lg-12 search-list-container">
            <%@ include file="IndexPartial.jsp" %>
        </div>
    </div>
</form:form>
<soul:import res="subaccount/role/Index"></soul:import>
