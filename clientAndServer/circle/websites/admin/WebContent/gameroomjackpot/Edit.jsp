<%--@elvariable id="command" type="g.model.admin.gameroomconfig.vo.GameRoomConfigBull100Vo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->
<form:form>
    <form:hidden path="result.id" />
    <form:hidden path="result.roomId" />
    <div id="validateRule" style="display: none">${command.validateRule}</div>
    <div class="row">
        <div class="position-wrap clearfix">
            <h2>
                <a class="navbar-minimalize" href="javascript:void(0)">
                    <i class="icon iconfont">&#xe610;</i>
                </a>
            </h2>
            <span>游戏</span>
            <span>/</span>
            <span>奖池设置</span>
            <soul:button tag="a" target="goToLastPage" text="" opType="function" cssClass="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn">
                <em class="fa fa-caret-left"></em>${views.common['return']}
            </soul:button>
        </div>
        <div class="col-lg-12">
            <div class="wrapper white-bg shadow">
                <div class="panel blank-panel">
                    <div class="panel-body">
                        <div class="panel-body">
                            <div id="editable_wrapper" class="dataTables_wrapper" role="grid">
                                <div class="table-responsive" id="tab-1">
                                    <div class="tab-content">
                                        <table class="table dataTable">
                                            <tbody>
                                                <tr class="tab-title">
                                                    <th class="bg-tbcolor">奖池总额</th>
                                                    <td>
                                                        <form:input path="result.jackpotSum" value="${command.result.jackpotSum}" />
                                                    </td>

                                                    <th class="bg-tbcolor">变量奖池金额</th>
                                                    <td>
                                                        截止时间：
                                                        ${soulFn:formatDateTz(command.result.confirmTime, DateFormat.DAY_SECOND, timeZone)}
                                                        &nbsp;&nbsp;&nbsp;&nbsp;　　
                                                        ${soulFn:formatCurrency(command.result.jackpot)}
                                                    </td>
                                                </tr>

                                                <tr class="tab-title">
                                                    <th class="bg-tbcolor"></th>
                                                    <td></td>
                                                </tr>

                                                <tr class="tab-title">
                                                    <th class="bg-tbcolor">奖池最高积累金额</th>
                                                    <td>
                                                        <form:input path="result.maxJackpotLimit" value="${command.result.maxJackpotLimit}" />
                                                    </td>
                                                </tr>

                                                <tr class="tab-title">
                                                    <th class="bg-tbcolor"></th>
                                                    <td></td>
                                                </tr>

                                                <tr class="tab-title">
                                                    <th class="bg-tbcolor">奖池最低下限金额</th>
                                                    <td>
                                                        <form:input path="result.minJackpotLimit" value="${command.result.minJackpotLimit}" />
                                                    </td>

                                                    <th class="bg-tbcolor">奖池溢出金额</th>
                                                    <td>
                                                        截止时间：
                                                        ${soulFn:formatDateTz(command.result.confirmTime, DateFormat.DAY_SECOND, timeZone)}　
                                                        &nbsp;&nbsp;&nbsp;&nbsp;　
                                                        ${soulFn:formatCurrency(command.result.jackpotOverflow)}
                                                    </td>
                                                </tr>

                                                <tr class="tab-title">
                                                    <th class="bg-tbcolor"></th>
                                                    <td></td>
                                                </tr>

                                                <tr class="tab-title">
                                                    <th class="bg-tbcolor">当局最高可输金额</th>
                                                    <td>
                                                        <form:input path="result.maxLimitGameLose" value="${command.result.maxLimitGameLose}" />
                                                    </td>
                                                </tr>

                                                <tr class="tab-title">
                                                    <th class="bg-tbcolor"></th>
                                                    <td></td>
                                                </tr>

                                                <tr class="tab-title">
                                                    <th class="bg-tbcolor">当局设定收缴值</th>
                                                    <td>
                                                        <form:input path="result.maxJackpotAmatch" value="${command.result.maxJackpotAmatch}" />
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                        <br>
                                        <div class="modal-footer">
                                            <soul:button target="${root}/gameRoomJackpot/persist.html" text="${views.common['OK']}" cssClass="btn btn-filter btn-lg" precall="validateForm" opType="ajax" post="getCurrentFormData" callback="goToLastPage" refresh="true">${views.common['OK']}</soul:button>
                                            <soul:button target="goToLastPage"  text="${views.common['cancel']}" cssClass="btn btn-outline btn-filter btn-lg" opType="function">${views.common['cancel']}</soul:button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form:form>
<!--//region your codes 3-->
<soul:import type="edit"/>
<!--//endregion your codes 3-->
