<%--@elvariable id="command" type="List<TreeNode<VSysUserResource>>"--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/include/include.inc.jsp" %>
<nav class="navbar-default navbar-static-side shadow side" role="navigation">
  <div class="sidebar-collapse">
    <ul class="nav" id="side-menu">
      <c:forEach items="${command}" var="obj" varStatus="status">
        <li>
          <a <c:if test="${obj.children.size()>0}">href="javascript:void(0);"</c:if>
                  <c:if test="${obj.children.size()==0}">nav-second='mainFrame' href="/${obj.object.resourceUrl}"</c:if>>
            <em class="iconfont ${obj.object.resourceIcon}"></em> <span class="nav-label">${obj.object.resourceRName}</span>
            <c:if test="${obj.children.size()>0}"><span class="fa arrow"></span></c:if>
          </a>
          <ul class="nav nav-second-level collapse" aria-expanded="false" style="height: 0px;">
            <c:forEach items="${obj.children}" var="cobj" varStatus="status">
              <li><a nav-second='mainFrame' href="/${cobj.object.resourceUrl}">${cobj.object.resourceRName}</a></li>
            </c:forEach>
          </ul>
        </li>
      </c:forEach>
    </ul>
  </div>
</nav>
<div id="page-wrapper" class="gray-bg dashbard-1 main">
  <div id="mainFrame">
  </div>
</div>
<script type="text/javascript" language="JavaScript">
  $('#side-menu').metisMenu();
//  $("a[nav-top]:first").click();
  //判断点击首页时触发菜单下第一个节点
//    $("a[nav-second]:first").click();
  $("#page-content #side-menu").find("li:first a:first").click();
$("#page-content #side-menu").find("li:first").find("ul li:first a[nav-second]:first").click();
</script>