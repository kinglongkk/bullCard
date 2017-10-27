<%--@elvariable id="command" type="g.model.agent.vo.VUserPlayerGroupVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<div class="row">
    <form:form>
    <div class="position-wrap clearfix">
        <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
        <span>${views.sysResource['会员']}</span>
        <span>/</span><span>管理分组</span>
        <soul:button tag="a" target="goToLastPage" text="" opType="function" cssClass="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn">
            <em class="fa fa-caret-left"></em>${views.common['return']}
        </soul:button>
    </div>
    <div class="col-lg-12">
        <div class="wrapper white-bg shadow">
            <div class="present_wrap"><b>${views.group['group.view']}</b></div>
            <%--分组信息 start--%>
            <div class="form-group over clearfix">
                <label class="al-right pull-left line-hi34">
                    ${views.group['groupName']}：
                </label>
                <div class="input-group m-b pull-left">
                    <input class="form-control" value="${command.result.groupName}" readonly/>
                </div>
            </div>
            <div class="form-group over clearfix">
                <label class="al-right pull-left line-hi34">
                    ${views.group['playernum']}：
                </label>
                <div class="input-group m-b pull-left">
                    <input class="form-control" value="${command.result.playernum}" readonly/>
                </div>
            </div>
            <%--分组信息 end--%>

            <%--玩家分组限额设置 start--%>
            <div class="">
                <div class="m-b">
                    <div class="wrapper white-bg">
                        <div class="present_wrap"><b>限额设置</b></div>
                        <!--表格内容-->
                        <div class="panel-body">
                            <div class="tab-content">
                                <div class="table-responsive">
                                    <table class="table table-striped table-hover dataTable m-b-none" aria-describedby="editable_info">
                                        <thead>
                                        <tr>
                                            <th style="width: 20%"></th>
                                            <th style="width: 40%">单注最高限额</th>
                                            <th style="width: 40%">单项最高限额</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:if test="${fn:length(command.vUserGroupBetLimitList)>0}">
                                            <c:set var="ubetLimitList0" value="${command.vUserGroupBetLimitList.get(0)}"/>
                                            <c:set var="ubetLimitList1" value="${command.vUserGroupBetLimitList.get(1)}"/>
                                            <c:set var="ubetLimitList2" value="${command.vUserGroupBetLimitList.get(2)}"/>
                                            <c:set var="ubetLimitList3" value="${command.vUserGroupBetLimitList.get(3)}"/>
                                            <c:set var="ubetLimitList4" value="${command.vUserGroupBetLimitList.get(4)}"/>
                                            <c:set var="ubetLimitList5" value="${command.vUserGroupBetLimitList.get(5)}"/>
                                        </c:if>
                                        <tr>
                                            <td>让球，大小，单双</td>
                                            <td>
                                                <div class="input-group">
                                                    <input type="text" class="form-control" readonly value="${ubetLimitList0.betMax}">
                                                </div>
                                            </td>
                                            <td>
                                                <div class="input-group">
                                                    <input type="text" class="form-control" readonly value="${ubetLimitList0.itemMax}">
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>滚球让球，滚球大小，滚球单双</td>
                                            <td>
                                                <div class="input-group">
                                                    <input type="text" class="form-control" readonly value="${ubetLimitList1.betMax}">
                                                </div>
                                            </td>
                                            <td>
                                                <div class="input-group">
                                                    <input type="text" class="form-control" readonly value="${ubetLimitList1.itemMax}">
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>独赢，滚球独赢</td>
                                            <td>
                                                <div class="input-group">
                                                    <input type="text" class="form-control" readonly value="${ubetLimitList2.betMax}">
                                                </div>
                                            </td>
                                            <td>
                                                <div class="input-group">
                                                    <input type="text" class="form-control" readonly value="${ubetLimitList2.itemMax}">
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>其他</td>
                                            <td>
                                                <div class="input-group">
                                                    <input type="text" class="form-control" readonly value="${ubetLimitList3.betMax}">
                                                </div>
                                            </td>
                                            <td>
                                                <div class="input-group">
                                                    <input type="text" class="form-control" readonly value="${ubetLimitList3.itemMax}">
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>滚球其他</td>
                                            <td>
                                                <div class="input-group">
                                                    <input type="text" class="form-control" readonly value="${ubetLimitList4.betMax}">
                                                </div>
                                            </td>
                                            <td>
                                                <div class="input-group">
                                                    <input type="text" class="form-control" readonly value="${ubetLimitList4.itemMax}">
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>冠军</td>
                                            <td>
                                                <div class="input-group">
                                                    <input type="text" class="form-control" readonly value="${ubetLimitList5.betMax}">
                                                </div>
                                            </td>
                                            <td>
                                                <div class="input-group">
                                                    <input type="text" class="form-control" readonly value="${ubetLimitList5.itemMax}">
                                                </div>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <%--玩家分组限额设置 end--%>

            <!--综合过关单注最高限额 start -->
            <div class="main-box">
                <div class=" m-b">
                    <div class="wrapper white-bg">
                        <div class="present_wrap">
                            <b>综合过关单注最高限额</b>
                            <span class="table-title-hint brown">单注最低限额固定<b class="text-red-mark">${empty command.sysParam.paramValue?command.sysParam.defaultValue:command.sysParam.paramValue}</b>元</span>
                        </div>
                        <!--表格内容-->
                        <div class="panel-body">
                            <div class="tab-content">
                                <div class="table-responsive">
                                    <table class="table table-striped table-hover dataTable m-b-none" aria-describedby="editable_info">
                                        <thead>
                                        <tr>
                                            <th style="width: 20%">方式</th>
                                            <th style="width: 40%">赔率的积</th>
                                            <th style="width: 40%">单注上限</th>
                                        </tr>
                                        </thead>
                                        <c:forEach items="${command.betNumKeyMap}" var="map" varStatus="outIndex">
                                            <tbody>
                                            <c:set var="rowNum" value="${fn:length(map.value)}"/>
                                            <c:forEach items="${map.value}" var="b" varStatus="inIndex">
                                                <tr>
                                                    <c:if test="${inIndex.index==0}">
                                                        <td rowspan="${rowNum}">${b.betNum}串1</td>
                                                    </c:if>
                                                    <td>${b.oddsMin}-${b.oddsMax}</td>
                                                    <td>
                                                        <c:forEach items="${command.vUserGroupBetLimitMultipleListVo.result}" var="c">
                                                            <c:if test="${b.betNum==c.betNum && b.oddsMin==c.oddsMin && b.oddsMax==c.oddsMax}">
                                                                <c:set var="betMaxObject" value="${c}"/>
                                                            </c:if>
                                                        </c:forEach>
                                                        <div class="input-group">
                                                            <input type="text" class="form-control" value="${betMaxObject.betMax}" readonly>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <!--综合过关单注最高限额 end -->
        </div>
    </div>
    </form:form>
</div>
<soul:import type="view"/>
