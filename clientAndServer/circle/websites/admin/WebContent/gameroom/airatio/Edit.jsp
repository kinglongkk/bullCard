<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<style>
    .trai{
        text-align: center;
    }
</style>
<form:form id="editForm" action="${root}/playerWarningControl/edit.html" method="post">
    <div class="row">
        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
            <span>监控</span><span>/</span>基础设置<span></span>
            <soul:button target="goToLastPage" refresh="true" cssClass="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn" text="" opType="function">
                <em class="fa fa-caret-left"></em>${views.common['return']}
            </soul:button>
        <%--<a href="/game/list.html" style="display: none" name="refresh" nav-target="mainFrame">${views.common['return']}</a>--%>
        </div>
        <div class="col-lg-12">
            <div class="wrapper white-bg clearfix shadow">
                <div class="panel blank-panel">
                    <div class="panel-body">
                        <div class="panel-body">
                            <a id="refresMain" nav-target="mainFrame" href="/playerAiRatioControl/editAdd.html?roomId=${param.roomId}" class="btn btn-outline btn-filter"><i class="fa fa-refresh"></i><span class="hd">&nbsp;&nbsp;${views.common['refresh']}</span></a>

                            <div id="editable_wrapper" class="dataTables_wrapper" role="grid">
                                <div class="table-responsive" id="tab-1">
                                    <input type="hidden" name="result.id" value="${aiControl.id}">
                                    <input type="hidden" name="result.roomId" value="${param.roomId}">
                                    <input type="hidden" name="result.status" value="${aiControl.status}">
                                    <input type="hidden" name="result.aiQty" value="${aiControl.aiQty}">
                                    <input type="hidden" name="result.controlMode" value="${aiControl.controlMode}">
                                    <input type="hidden" name="result.gameModel" value="${aiControl.gameModel}">
                                    <input type="hidden" name="result.roomName" value="${aiControl.roomName}">
                                    <input type="hidden" name="result.batchId" value="${aiControl.batchId}">
                                    <input type="hidden" name="result.createUser" value="${aiControl.createUser}">
                                    <input type="hidden" name="result.createTime" value="${soulFn:formatDateTz(aiControl.createTime, DateFormat.DAY_SECOND, timeZone)}">
                                    <input type="hidden" name="result.updateUser" value="${aiControl.updateUser}">
                                    <input type="hidden" name="result.updateTime" value="${soulFn:formatDateTz(aiControl.updateTime, DateFormat.DAY_SECOND, timeZone)}">
                                    <input type="hidden" name="result.beginControlTime" value="${soulFn:formatDateTz(aiControl.beginControlTime, DateFormat.DAY_SECOND, timeZone)}">
                                    <input type="hidden" name="result.controlCycle" value="${aiControl.controlCycle}">
                                    <div style="text-align: center;color: red;" id="message"></div>
                                    <div class="tab-content">
                                        <table class="table dataTable">
                                            <tbody>
                                            <tr class="tab-title">
                                                <th>*携带金币数:</th>
                                                <td>
                                                    <input type="text" id="bringGoldMin" name="result.bringGoldMin" value="${aiControl.bringGoldMin}">-
                                                    <input type="text" id="bringGoldMax" name="result.bringGoldMax" value="${aiControl.bringGoldMax}">
                                                    <label>* 即AI进入房间时身上的金币范围</label>
                                                </td>
                                            </tr>
                                            <tr class="tab-title">
                                                <th>*进入时间间隔:</th>
                                                <td>
                                                    <input type="text" id="intervalMinTime" name="result.intervalMinTime" value="${aiControl.intervalMinTime}">-
                                                    <input type="text" id="intervalMaxTime" name="result.intervalMaxTime" value="${aiControl.intervalMaxTime}">
                                                    <label>* 即AI进入房间时的频率</label>
                                                </td>
                                            </tr>
                                            <tr class="tab-title">
                                                <th>*离开时间间隔:</th>
                                                <td>
                                                    <input type="text"  id="leaveMinTime" name="result.leaveMinTime" value="${aiControl.leaveMinTime}">-
                                                    <input type="text" id="leaveMaxTime" name="result.leaveMaxTime" value="${aiControl.leaveMaxTime}">
                                                    <label>* 即AI离开房间时的频率</label>
                                                </td>
                                            </tr>
                                            <tr class="tab-title">
                                                <th>*游戏休息局数:</th>
                                                <td>
                                                    <input type="text" id="restMinGames" name="result.restMinGames" value="${aiControl.restMinGames}">-
                                                    <input type="text" id="restMaxGames" name="result.restMaxGames" value="${aiControl.restMaxGames}">
                                                    <label>* 即AI连续游戏的局数，0为不休息</label>
                                                </td>
                                            </tr>
                                            <tr class="tab-title">
                                                <th>*投注筹码比率:</th>
                                                <td>
                                                    <input type="text" id="chipRates" name="result.chipRates" value="${aiControl.chipRates}">-
                                                    <label>* 投注筹码比率，用‘，’进行隔开</label>
                                                </td>
                                            </tr>
                                            <tr class="tab-title">
                                                <th>*每场比赛平均押注次数:</th>
                                                <td>
                                                    <input type="text" id="betCount" name="result.betCount" value="${aiControl.betCount}">
                                                    <label>* 每场比赛平均押注次数</label>
                                                </td>
                                            </tr>
                                            <tr class="tab-title">
                                                <th>*人数上限:</th>
                                                <td>
                                                    <input type="text" id="roomMaxQty" name="result.roomMaxQty" value="${aiControl.roomMaxQty}">
                                                    <label>* 房间玩家+AI数到达该值时，AI不再自动进入高于该值时，AI有序退出</label>
                                                </td>
                                            </tr>
                                            <tr class="tab-title">
                                                <th>*玩家人数介于:</th>
                                                <td>
                                                    <input type="text" id="playerProportionMin" name="playerProportionMin" value="">%-
                                                    <input type="text" id="playerProportionMax" name="playerProportionMax" value="">%
                                                    <label>*AI数:</label>
                                                    <input type="text" id="aiProportionMin" name="aiProportionMin" value="">%-
                                                    <input type="text" id="aiProportionMax" name="aiProportionMax" value="">%
                                                    <a href="javascript:void(0);" onclick="addAi()"  class="btn btn-filter" >添加</a>
                                                    <label>* 即玩家人数介于两者之间时，AI数的配比占房间可容纳人数的百分比</label>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                        <br>
                                        <div class="table-responsive table-min-h table_border">
                                            <table class="table table-striped table-hover dataTable m-b-none" id="aiTable">
                                                <thead>
                                                <tr role="row">
                                                    <th>ID</th>
                                                    <th>玩家最低占比%</th>
                                                    <th>玩家最高占比%</th>
                                                    <th>AI最低占比%</th>
                                                    <th>AI最高占比%</th>
                                                    <th>操作</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach items="${aiRatioControl}" var="p" varStatus="status">
                                                    <tr class="tab-detail">
                                                        <td class="text_r id" style='text-align: center'>${p.id}</td>
                                                        <td class="text_r playerProportionMin" style='text-align: center'>${p.playerProportionMin}</td>
                                                        <td class="text_r playerProportionMax" style='text-align: center'>${p.playerProportionMax}</td>
                                                        <td class="text_r aiProportionMin" style='text-align: center'>${p.aiProportionMin}</td>
                                                        <td class="text_r aiProportionMax" style='text-align: center'>${p.aiProportionMax}</td>
                                                        <td style='text-align: center'><a href='javasscript:void(0)' onclick='deleteAi(this)'>删除</a></td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                            <input type="hidden" id="aiControlJson" name="aiControlJson" value="">
                                            <soul:button cssClass="btn btn-filter" text="保存" opType="ajax"  dataType="json" target="${root}/playerAiRatioControl/saveAi.html" precall="validateForm1" callback="returnDate" post="getCurrentFormData" />
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
<soul:import res="site/gameroom/roomAi"/>