<%--
  Created by IntelliJ IDEA.
  User: jeff
  Date: 15-10-26
  Time: 下午2:54
--%>

<%--@elvariable id="sysRoleListVo" type="g.model.setting.vo.SysRoleListVo"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<%--<%@ include file="/include/include.head.jsp" %>--%>
<input id="firstRoleId" value="${sysRoleListVo.search.id}" type="hidden">
<div class="wrapper white-bg shadow">
    <div id="editable_wrapper" class="dataTables_wrapper" role="grid">
        <ul class="clearfix sys_tab_wrap">
            <li><b class="m-l-sm">${views.subAccount['role.rightManage']}</b></li>
        </ul>
        <div class="clearfix">
            <div class="side-left _roles">
                <ul>
                    <c:forEach items="${sysRoleListVo.vSysRoles}" var="sysRole">
                        <li class="air${not sysRole.builtIn ? ' not_built_in':''}" data-id="${sysRole.id}">
                            <soul:button target="getResource" post="${sysRole.id}" dataType="#role_${sysRole.id}" cssClass="co-gray6" tag="a" text="" opType="function">
                                <b>
                                    <c:choose>
                                        <c:when test="${sysRole.builtIn}">
                                            <c:set var="_key" value="${'role.'.concat(sysRole.name)}"></c:set>
                                            ${views.subAccount[_key]}(${views.subAccount['role.default']})
                                        </c:when>
                                        <c:otherwise>
                                            ${sysRole.name}
                                        </c:otherwise>
                                    </c:choose>
                                </b>
                            </soul:button>
                            <c:choose>
                                <c:when test="${sysRole.userCount > 0}">
                                    <span class="label label-orange">${sysRole.userCount}${views.subAccount['role.indexPartial.people']}</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="label label-gray">${sysRole.userCount}${views.subAccount['role.indexPartial.people']}</span>
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${not sysRole.builtIn}">
                                <a href="javascript:void(0)" class="img">
                                    <img src="${resRoot}/images//file-list.png">
                                </a>
                                <c:if test="${!sysRoleListVo.readOnly}">
                                    <div class="edit">
                                        <soul:button target="${root}/subAccount/roleRename.html?search.id=${sysRole.id}" callback="reload" post="${sysRole.id}" text="${views.subAccount['role.rename']}" opType="dialog">
                                            ${views.subAccount['role.rename']}
                                        </soul:button>
                                        <c:if test="${sysRole.userCount == 0}">
                                            <soul:button target="deleteRole" callback="deleteCurrentli" tag="a" text="" post="${sysRole.id}" opType="function">
                                                ${views.common['delete']}
                                            </soul:button>
                                        </c:if>
                                    </div>
                                </c:if>
                            </c:if>
                        </li>
                    </c:forEach>
                    <c:if test="${!sysRoleListVo.readOnly}">
                        <li>
                            <soul:button target="getResource" post="0" cssClass="add" tag="a" text="" opType="function">
                                ${views.subAccount['role.createRole']}
                            </soul:button>
                        </li>
                    </c:if>
                </ul>
            </div>
            <c:forEach items="${sysRoleListVo.vSysRoles}" var="sysRole" varStatus="status">
                <div id="role_${sysRole.id}" class="side-right ${not status.first ? 'hide':''} role_tab_ban">
                    <c:set var="_key" value=""></c:set>
                    <c:choose>
                        <c:when test="${sysRole.builtIn}">
                            <c:set var="_key" value="${'role.'.concat(sysRole.name)}"></c:set>
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" value="rename" name="type">
                        </c:otherwise>
                    </c:choose>
                    <input type="hidden" value="${sysRole.id}" name="roleId">
                    <div class="clearfix jurisdiction-btn">
                        <span class="${sysRole.builtIn ?'':'show_role_content'}">
                            <h3 class="pull-left _roleName">${empty _key? sysRole.name:views.subAccount[_key]}</h3>
                        </span>
                        <c:if test="${!sysRoleListVo.readOnly}">
                            <soul:button target="editRole" tag="a" text="" post="${sysRole.id}" opType="function" cssClass="m-l-sm show_role_content">${views.subAccount['role.changeResource']}</soul:button>
                        </c:if>
                        <c:choose>
                            <c:when test="${sysRole.builtIn}">
                                <soul:button target="${root}/subAccount/resetDefault.html?search.roleId=${sysRole.id}" text="" opType="ajax" callback="reload" cssClass="m-l-sm hide role_edit_content">${views.subAccount['role.resetDefault']}</soul:button>
                            </c:when>
                            <c:otherwise>
                               <span class="content-width-limit-10 pull-left hide role_edit_content">
                                   <input type="text" value="${empty _key? sysRole.name:views.subAccount[_key]}" class="form-control" data-name="result.name">
                               </span>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${!sysRoleListVo.readOnly}">
                            <soul:button target="cancelEdit" tag="button" cssClass="btn btn-outline btn-filter pull-right hide role_edit_content" text="" post="${sysRole.id}" opType="function">
                                ${views.common['cancel']}
                            </soul:button>
                            <soul:button target="saveRole" precall="validateForm" tag="button" cssClass="btn btn-filter m-r-xs pull-right hide role_edit_content" text="" opType="function">
                                ${views.common['save']}
                            </soul:button>
                        </c:if>
                    </div>

                    <div class="clearfix">
                        <ul class="anchor role_edit_content hide role_navigation">

                        </ul>
                        <div class="demo role_tree ${sysRole.builtIn ? 'builtIn':''}">

                        </div>
                    </div>
                    <div class="operate-btn clearfix role_edit_content hide">
                        <soul:button target="cancelEdit" tag="button" cssClass="btn btn-outline  btn-filter pull-right m-r-xs" text="" post="${sysRole.id}" opType="function">
                            ${views.common['cancel']}
                        </soul:button>
                        <soul:button target="saveRole" precall="validateForm" tag="button" cssClass="btn btn-filter pull-right m-r-xs" text="" opType="function">
                            ${views.common['save']}
                        </soul:button>
                    </div>
                </div>
            </c:forEach>
            <div id="role_0" class="side-right hide role_tab_ban">
                <input type="hidden" value="${sysRole.id}" name="roleId">
                <div class="clearfix jurisdiction-btn">
                   <span class="content-width-limit-10 pull-left">
                       <input type="text" placeholder="" class="form-control" data-name="result.name" name="">
                   </span>
                    <soul:button target="cancelEdit" tag="button" cssClass="btn btn-outline btn-filter pull-right" text="" post="${sysRoleListVo.vSysRoles.get(0).id}" opType="function">
                        ${views.common['cancel']}
                    </soul:button>
                    <soul:button target="saveRole" precall="validateForm" tag="button" cssClass="btn btn-filter m-r-xs pull-right" text="" opType="function">
                        ${views.common['save']}
                    </soul:button>
                </div>

                <div class="clearfix">
                    <ul class="anchor role_edit_content hide role_navigation">
                    </ul>
                    <div class="demo role_tree">

                    </div>
                </div>
                <div class="operate-btn clearfix">
                    <soul:button target="cancelEdit" tag="button" cssClass="btn btn-outline  btn-filter pull-right m-r-xs" text="" post="${sysRoleListVo.vSysRoles.get(0).id}" opType="function">
                        ${views.common['cancel']}
                    </soul:button>
                    <soul:button target="saveRole" precall="validateForm" tag="button" cssClass="btn btn-filter pull-right m-r-xs" text="" opType="function">
                        ${views.common['save']}
                    </soul:button>
                </div>

            </div>
        </div>
    </div>
</div>