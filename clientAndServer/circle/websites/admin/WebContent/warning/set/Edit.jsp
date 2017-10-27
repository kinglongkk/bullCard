<%--@elvariable id="command" type="g.model.warning.vo.PlayerWarningControlVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->
<form:form id="editForm" action="${root}/playerWarningControl/edit.html" method="post">
    <div class="row">
        <div class="position-wrap clearfix">
            <h2>
                <a class="navbar-minimalize" href="javascript:void(0)">
                    <i class="icon iconfont">&#xe610;</i>
                </a>
            </h2>
            <span>监控</span>
            <span>/</span>
            <span>异常玩家预警设置</span>
            <a href="playerWarningControl/edit.html" style="display: none" name="refresh" nav-target="mainFrame">${views.common['return']}</a>
        </div>
        <div class="col-lg-12">
            <div class="wrapper white-bg clearfix shadow">
                <div class="panel blank-panel">
                    <div class="panel-body">
                        <div class="panel-body">
                            <div id="editable_wrapper" class="dataTables_wrapper" role="grid">
                                <div class="table-responsive" id="tab-1">
                                    <div class="tab-content">
                                        <table class="table dataTable">
                                            <tbody>
                                            <tr class="tab-title">
                                                <td></td>
                                                <th>黄色预警</th>
                                                <th>红色预警</th>
                                                <td></td>
                                            </tr>
                                            <tr class="tab-title">
                                                <th>赢得金币与投注本金倍数比 >=:</th>
                                                <td><input type="text" name="result.winRate1" value="${command.winRate1}"></td>
                                                <td><input type="text" name="result.winRate2" value="${command.winRate2}"></td>
                                                <th>*黄色警报为声音提醒，红色警报为账号冻结并声音提醒</th>
                                            </tr>
                                            <tr class="tab-title">
                                                <th>取现总额 >=:</th>
                                                <td><input type="text" name="result.enchashmentSum1" value="${command.enchashmentSum1}"></td>
                                                <td><input type="text" name="result.enchashmentSum2" value="${command.enchashmentSum2}"></td>
                                                <th>*黄色警报为声音提醒，红色警报为账号冻结并声音提醒</th>
                                            </tr>
                                            <tr class="tab-title">
                                                <th>连续派彩局数 >=:</th>
                                                <td><input type="text" name="result.payoutTimes1" value="${command.payoutTimes1}"></td>
                                                <td><input type="text" name="result.payoutTimes2" value="${command.payoutTimes2}"></td>
                                                <th>*黄色警报为声音提醒，红色警报为账号冻结并声音提醒</th>
                                            </tr>
                                            <tr class="tab-title">
                                                <th>今日取现次数 >=:</th>
                                                <td><input type="text" name="result.enchashmentTimes1" value="${command.enchashmentTimes1}"></td>
                                                <td><input type="text" name="result.enchashmentTimes2" value="${command.enchashmentTimes2}"></td>
                                                <th>*黄色警报为声音提醒，红色警报为账号冻结并声音提醒</th>
                                            </tr>
                                            </tbody>
                                        </table>
                                        <br>
                                        <div class="modal-footer">
                                            <soul:button cssClass="btn btn-filter" text="保存" opType="ajax" dataType="json" target="${root}/playerWarningControl/saveAi.html" precall="validateForm" post="getCurrentFormData" />
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