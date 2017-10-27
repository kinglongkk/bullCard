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
    <div id="validateRule" style="display: none">${command.validateRule}</div>
        <div class="col-lg-12">
            <div class="wrapper white-bg shadow">
                <div class="present_wrap"><b>${empty command.result.id ? views.group['group.createTitle']:views.group['group.editTitle']}</b></div>
                <%--分组信息 start--%>
                <div class="form-group over clearfix">
                    <label class="al-right pull-left line-hi34">
                        ${views.group['groupName']}：
                    </label>
                    <div class="input-group m-b pull-left">
                        <input class="form-control" name="result.groupName" value="${command.result.groupName}" maxlength="100"/>
                        <input type="hidden" name="result.id" value="${command.result.id}">
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
                                            <c:if test="${fn:length(command.userBetLimit)>0}">
                                                <c:set var="betLimitList0" value="${command.userBetLimit.get(0)}"/>
                                                <c:set var="betLimitList1" value="${command.userBetLimit.get(1)}"/>
                                                <c:set var="betLimitList2" value="${command.userBetLimit.get(2)}"/>
                                                <c:set var="betLimitList3" value="${command.userBetLimit.get(3)}"/>
                                                <c:set var="betLimitList4" value="${command.userBetLimit.get(4)}"/>
                                                <c:set var="betLimitList5" value="${command.userBetLimit.get(5)}"/>
                                            </c:if>

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
                                                        <span class="input-group-addon abroder-no">${betLimitList0.betMin}&lt;</span>
                                                        <input type="hidden" name="betLimitList[0].betMin" value="${betLimitList0.betMin}">
                                                        <input type="text" class="form-control" name="betLimitList[0].betMax" value="${ubetLimitList0.betMax}">
                                                        <input type="hidden" value="${betLimitList0.betMax}">
                                                        <span class="input-group-addon abroder-no">&lt;${betLimitList0.betMax}</span>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" name="betLimitList[0].itemMax" value="${ubetLimitList0.itemMax}">
                                                        <input type="hidden" value="${betLimitList0.itemMax}">
                                                        <span class="input-group-addon abroder-no">&lt;${betLimitList0.itemMax}</span>
                                                    </div>
                                                </td>
                                                <input type="hidden" name="betLimitList[0].columnGroupCode" value="${ubetLimitList0.columnGroupCode}"/>
                                            </tr>
                                            <tr>
                                                <td>滚球让球，滚球大小，滚球单双</td>
                                                <td>
                                                    <div class="input-group">
                                                        <span class="input-group-addon abroder-no">${betLimitList1.betMin}&lt;</span>
                                                        <input type="hidden" name="betLimitList[1].betMin" value="${betLimitList1.betMin}">
                                                        <input type="text" class="form-control" name="betLimitList[1].betMax" value="${ubetLimitList1.betMax}">
                                                        <input type="hidden" value="${betLimitList1.betMax}">
                                                        <span class="input-group-addon abroder-no">&lt;${betLimitList1.betMax}</span>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" name="betLimitList[1].itemMax" value="${ubetLimitList1.itemMax}">
                                                        <input type="hidden" value="${betLimitList1.itemMax}">
                                                        <span class="input-group-addon abroder-no">&lt;${betLimitList1.itemMax}</span>
                                                    </div>
                                                </td>
                                                <input type="hidden" name="betLimitList[1].columnGroupCode" value="${ubetLimitList1.columnGroupCode}"/>
                                            </tr>
                                            <tr>
                                                <td>独赢，滚球独赢</td>
                                                <td>
                                                    <div class="input-group">
                                                        <span class="input-group-addon abroder-no">${betLimitList2.betMin}&lt;</span>
                                                        <input type="hidden" name="betLimitList[2].betMin" value="${betLimitList2.betMin}">
                                                        <input type="text" class="form-control" name="betLimitList[2].betMax" value="${ubetLimitList2.betMax}">
                                                        <input type="hidden" value="${betLimitList2.betMax}">
                                                        <span class="input-group-addon abroder-no">&lt;${betLimitList2.betMax}</span>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" name="betLimitList[2].itemMax" value="${ubetLimitList2.itemMax}">
                                                        <input type="hidden" value="${betLimitList2.itemMax}">
                                                        <span class="input-group-addon abroder-no">&lt;${betLimitList2.itemMax}</span>
                                                    </div>
                                                </td>
                                                <input type="hidden" name="betLimitList[2].columnGroupCode" value="${ubetLimitList2.columnGroupCode}"/>
                                            </tr>
                                            <tr>
                                                <td>其他</td>
                                                <td>
                                                    <div class="input-group">
                                                        <span class="input-group-addon abroder-no">${betLimitList3.betMin}&lt;</span>
                                                        <input type="hidden" name="betLimitList[3].betMin" value="${betLimitList3.betMin}">
                                                        <input type="text" class="form-control" name="betLimitList[3].betMax" value="${ubetLimitList3.betMax}">
                                                        <input type="hidden" value="${betLimitList3.betMax}">
                                                        <span class="input-group-addon abroder-no">&lt;${betLimitList3.betMax}</span>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" name="betLimitList[3].itemMax" value="${ubetLimitList3.itemMax}">
                                                        <input type="hidden" value="${betLimitList3.itemMax}">
                                                        <span class="input-group-addon abroder-no">&lt;${betLimitList3.itemMax}</span>
                                                    </div>
                                                </td>
                                                <input type="hidden" name="betLimitList[3].columnGroupCode" value="${ubetLimitList3.columnGroupCode}"/>
                                            </tr>
                                            <tr>
                                                <td>滚球其他</td>
                                                <td>
                                                    <div class="input-group">
                                                        <span class="input-group-addon abroder-no">${betLimitList4.betMin}&lt;</span>
                                                        <input type="hidden" name="betLimitList[4].betMin" value="${betLimitList4.betMin}">
                                                        <input type="text" class="form-control" name="betLimitList[4].betMax" value="${ubetLimitList4.betMax}">
                                                        <input type="hidden" value="${betLimitList4.betMax}">
                                                        <span class="input-group-addon abroder-no">&lt;${betLimitList4.betMax}</span>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" name="betLimitList[4].itemMax" value="${ubetLimitList4.itemMax}">
                                                        <input type="hidden" value="${betLimitList4.itemMax}">
                                                        <span class="input-group-addon abroder-no">&lt;${betLimitList4.itemMax}</span>
                                                    </div>
                                                </td>
                                                <input type="hidden" name="betLimitList[4].columnGroupCode" value="${ubetLimitList4.columnGroupCode}"/>
                                            </tr>
                                            <tr>
                                                <td>冠军</td>
                                                <td>
                                                    <div class="input-group">
                                                        <span class="input-group-addon abroder-no">${betLimitList5.betMin}&lt;</span>
                                                        <input type="hidden" name="betLimitList[5].betMin" value="${betLimitList5.betMin}">
                                                        <input type="text" class="form-control" name="betLimitList[5].betMax" value="${ubetLimitList5.betMax}">
                                                        <input type="hidden" value="${betLimitList5.betMax}">
                                                        <span class="input-group-addon abroder-no">&lt;${betLimitList5.betMax}</span>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" name="betLimitList[5].itemMax" value="${ubetLimitList5.itemMax}">
                                                        <input type="hidden" value="${betLimitList5.itemMax}">
                                                        <span class="input-group-addon abroder-no">&lt;${betLimitList5.itemMax}</span>
                                                    </div>
                                                </td>
                                                <input type="hidden" name="betLimitList[5].columnGroupCode" value="${ubetLimitList5.columnGroupCode}"/>
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
                                            <c:set var="allIndex" value="0"/>
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
                                                                <input type="text" class="form-control" value="${betMaxObject.betMax}" name="betLimitMultipleList[${allIndex}].betMax">
                                                                <input type="hidden" value="${b.betMax}"/>
                                                                <span class="input-group-addon abroder-no">&lt;${b.betMax}</span>
                                                                <input type="hidden" name="betLimitMultipleList[${allIndex}].betNum" value="${b.betNum}"/>
                                                                <input type="hidden" name="betLimitMultipleList[${allIndex}].oddsMin" value="${b.oddsMin}"/>
                                                                <input type="hidden" name="betLimitMultipleList[${allIndex}].oddsMax" value="${b.oddsMax}"/>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <c:set var="allIndex" value="${allIndex+1}"/>
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
        <div class="modal-footer">
            <div class="clearfix remark-wrap m-t-sm">
                <soul:button target="closePage" text="${views.common['cancel']}" cssClass="btn btn-outline btn-filter" opType="function"/>
                <soul:button precall="myValidateForm" cssClass="btn btn-filter" callback="goQuery" text="${views.common['OK']}" opType="ajax" dataType="json" target="${root}/vUserPlayerGroup/editUserGroup.html" post="getCurrentFormData"/>
            </div>
        </div>
    </form:form>
</div>
<soul:import res="site/player/Edit"/>
