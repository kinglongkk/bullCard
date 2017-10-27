<%--@elvariable id="command" type="g.model.game.vo.GameListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

<!--//region your codes 1-->
<div class="table-responsive table-min-h table_border">
    <table class="table table-striped table-hover dataTable m-b-none">
        <thead>
            <tr role="row" class="bg-gray">
                <th>#</th>
                <th>游戏名称</th>
                <th>游戏代码</th>
                <th>游戏图标</th>
                <th>${views.common['status']}</th>
                <th>一级类型</th>
                <th>二级类型</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${command.result}" var="p" varStatus="status">
                <tr class="tab-detail">
                    <td class="text_r">${status.index+1}</td>
                    <td>${p.name}</td>
                    <td>${p.code}</td>
                    <td class="text_c">
                        <div class="form-group clearfix line-hi34 m-b-xxs">
                            <div class="col-xs-8 p-x">
                                <!-- TODO cj set default img -->
                                <img src="${soulFn:getImagePathWithDefault(domain, p.icon,resRoot.concat('/images/myaccount.jpg'))}" class="logo-size-h100">
                                <soul:button target="${root}/game/toUploadIconPortrait.html?result.id=${p.id}" text="点击修改图标" title="${views.setting['myAccount.editHeadPortrait']}" opType="dialog" callback="query"></soul:button>
                            </div>
                        </div>
                    </td>
                    <td>
                        <span class="label label-success">${p.status eq '10' ? views.common['enable'] : ''}</span>
                        <span class="label label-danger">${p.status eq '20' ? views.common['forbidden'] : ''}</span>
                        <span class="label label-warning">${p.status eq '30' ? views.common['safeguard'] : ''}</span>
                    </td>
                    <td>
                        <c:forEach items="${firstName}" var="TypeName">
                            <c:choose>
                                <c:when test="${p.firstType eq TypeName.code}">
                                    ${TypeName.trans}
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </td>
                    <td>
                        <c:forEach items="${sportType}" var="sportTypeName">
                             <c:choose>
                                 <c:when test="${p.type eq sportTypeName.code}">
                                    ${sportTypeName.trans}
                                 </c:when>
                             </c:choose>
                        </c:forEach>
                        <c:forEach items="${lotteryType}" var="lotteryTypeName">
                            <c:choose>
                                <c:when test="${p.type eq lotteryTypeName.code}">
                                    ${lotteryTypeName.trans}
                                </c:when>
                            </c:choose>
                        </c:forEach>
                        <c:forEach items="${chessCardType}" var="chessCardTypeName">
                            <c:choose>
                                <c:when test="${p.type eq chessCardTypeName.code}">
                                    ${chessCardTypeName.trans}
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </td>
                    <td>
                        <div class="joy-list-row-operations text_c">
                            <soul:button target="${root}/game/view.html?id=${p.id}" text="查看" opType="dialog" />
                            <soul:button target="${root}/game/edit.html?id=${p.id}" text="编辑" opType="dialog" callback="query" />
                            <soul:button target="${root}/game/delete.html?id=${p.id}" text="删除" opType="ajax" dataType="json" confirm="您确定要删除该条记录吗？" callback="query" />
                        </div>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${fn:length(command.result)<1}">
                <tr>
                    <td colspan="${fn:length(command.fields)+8}" class="no-content_wrap">
                        <div>
                            <i class="fa fa-exclamation-circle"></i> ${views.common['noResult']}
                        </div>
                    </td>
                </tr>
            </c:if>
        </tbody>
    </table>
</div>

<soul:pagination/>
<!--//endregion your codes 1-->
