<%--@elvariable id="command" type="g.model.activitymessage.vo.ActivityMessageVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<!--//region your codes 1-->

<!--//endregion your codes 1-->
<div class="row">
    <form:form>
        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
            <span>游戏活动</span>
            <span>/</span><span>活动信息</span>
            <soul:button tag="a" target="goToLastPage" text="" opType="function" cssClass="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn">
                <em class="fa fa-caret-left"></em>${views.common['return']}
            </soul:button>
        </div>

        <div id="validateRule" style="display: none">${command.validateRule}</div>

        <div class="col-lg-12">

            <%-- 活动信息 start --%>
            <div class="wrapper white-bg shadow">
                <div class="present_wrap"><b>
                    <%--编辑显示活动类型--%>
                    <c:forEach items="${activityTypeEnum}" var="activityTypeName">
                       <c:choose>
                           <c:when test="${command.result.activityClassifyKey eq activityTypeName.code}">
                               ${activityTypeName.trans}
                           </c:when>
                       </c:choose>
                   </c:forEach>
                    <%--新增显示活动类型--%>
                    <c:forEach items="${activityTypeEnum}" var="activityTypeName">
                        <c:choose>
                            <c:when test="${activityClassifyKey eq activityTypeName.code}">
                                ${activityTypeName.trans}
                            </c:when>
                        </c:choose>
                    </c:forEach>
                    活动信息</b>
                </div>

                <input type="hidden" name="result.id" value="${command.result.id}" />
                <c:choose>
                    <c:when test="${not empty command.result.id}">
                        <input type="hidden" name="result.activityClassifyKey" value="${command.result.activityClassifyKey}" />
                        <input type="hidden" name="result.activityTypeCode" value="${command.result.activityTypeCode}" />
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="result.activityClassifyKey" value="${activityClassifyKey}" />
                        <input type="hidden" name="result.activityTypeCode" value="${activityClassifyKey}" />
                    </c:otherwise>
                </c:choose>

                <div class="clearfix m-t-sm">

                    <div class="dataTnables_wrapper">

                        <div class="form-group clearfix line-hi34 m-b-xxs">
                            <label class="col-xs-3 al-right">
                                <span class="co-red3 m-r-xs">*</span>
                                开始时间：
                            </label>
                            <div class="col-xs-2 p-x">
                                <gb:dateRange value="${command.result.startTime}" format="${DateFormat.DAY}" name="result.startTime"/>
                            </div>
                        </div>

                        <div class="form-group clearfix line-hi34 m-b-xxs">
                            <label class="col-xs-3 al-right">
                                <span class="co-red3 m-r-xs">*</span>
                                结束时间：
                            </label>
                            <div class="col-xs-2 p-x">
                                <gb:dateRange value="${command.result.endTime}" format="${DateFormat.DAY}" name="result.endTime"/>
                            </div>
                        </div>

                        <div class="form-group clearfix line-hi34 m-b-xxs">
                            <label class="col-xs-3 al-right">
                                <span class="co-red3 m-r-xs">*</span>
                                是否展示：
                            </label>
                            <div class="col-xs-8 p-x">
                                <select name="result.isDisplay" style="width: 254px;height: 34px">
                                    <c:choose>
                                        <c:when test="${not empty command.result.isDisplay}">
                                            <option value="false" ${command.result.isDisplay eq false?'selected="selected"':''}>否</option>
                                            <option value="true" ${command.result.isDisplay eq true?'selected="selected"':''}>是</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="">请选择</option>
                                            <option value="true">是</option>
                                            <option value="false">否</option>
                                        </c:otherwise>
                                    </c:choose>
                                </select>
                            </div>
                        </div>

                        <div class="form-group clearfix line-hi34 m-b-xxs">
                            <label class="col-xs-3 al-right">
                                <span class="co-red3 m-r-xs">*</span>
                                排序：
                            </label>
                            <div class="col-xs-8 p-x">
                                <input style="width: 254px;height: 34px" type="text" name="result.orderNum" value="${command.result.orderNum}"/>
                            </div>
                        </div>

                    </div>

                </div>
            </div>
            <%-- 活动信息 end --%>

            <%--活动规则 start --%>
            <div class="wrapper white-bg shadow">

                <div class="present_wrap"><b>
                        <%--编辑显示活动类型--%>
                    <c:forEach items="${activityTypeEnum}" var="activityTypeName">
                        <c:choose>
                            <c:when test="${command.result.activityClassifyKey eq activityTypeName.code}">
                                ${activityTypeName.trans}
                            </c:when>
                        </c:choose>
                    </c:forEach>
                        <%--新增显示活动类型--%>
                    <c:forEach items="${activityTypeEnum}" var="activityTypeName">
                        <c:choose>
                            <c:when test="${activityClassifyKey eq activityTypeName.code}">
                                ${activityTypeName.trans}
                            </c:when>
                        </c:choose>
                    </c:forEach>
                    活动规则</b>
                </div>

                <div class="clearfix m-t-sm">
                        <input name="activityRule.id" type="hidden" value="${ruleListVo.result[0].id}" />

                        <div class="dataTables_wrapper">

                            <div class="form-group clearfix line-hi34 m-b-xxs">
                                <label class="col-xs-3 al-right">
                                    <span class="co-red3 m-r-xs">*</span>
                                    有效时间：
                                </label>
                                <div class="col-xs-8 p-x">
                                    <select name="activityRule.effectiveTime" style="width: 254px;height: 34px">
                                        <c:choose>
                                            <c:when test="${not empty ruleListVo.result[0].id}">
                                                <option value="1"  ${ruleListVo.result[0].effectiveTime eq "1" ? 'selected="selected" ' : ''}>1天内</option>
                                                <option value="3"  ${ruleListVo.result[0].effectiveTime eq "3" ? 'selected="selected" ' : ''}>3天内</option>
                                                <option value="7"  ${ruleListVo.result[0].effectiveTime eq "7" ? 'selected="selected" ' : ''}>7天内</option>
                                                <option value="30" ${ruleListVo.result[0].effectiveTime eq "30" ? 'selected="selected" ' : ''}>30天内</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="">请选择有效时间</option>
                                                <option value="1">1天内</option>
                                                <option value="3">3天内</option>
                                                <option value="7">7天内</option>
                                                <option value="30">30天内</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group clearfix line-hi34 m-b-xxs">
                                <label class="col-xs-3 al-right">
                                    <span class="co-red3 m-r-xs">*</span>
                                    限制次数：
                                </label>
                                <c:choose>
                                    <c:when test="${not empty command.result.id}">
                                        <div class="col-xs-8 p-x">
                                                <input style="width: 254px;height: 34px" type="text" name="activityRule.limitNumber" value="${ruleListVo.result[0].limitNumber}">
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="col-xs-8 p-x">
                                            <input style="width: 254px;height: 34px" type="text" name="activityRule.limitNumber" value="">
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <div class="form-group clearfix line-hi34 m-b-xxs">
                                <label class="col-xs-3 al-right">
                                    <span class="co-red3 m-r-xs">*</span>
                                    要求首存：
                                </label>
                                <div class="col-xs-8 p-x">
                                    <select name="activityRule.isDemandFirst" style="width: 254px;height: 34px">
                                        <c:choose>
                                            <c:when test="${not empty ruleListVo.result[0].id}">
                                                <option value="true" ${ruleListVo.result[0].isDemandFirst eq 'true'?'selected="selected"':''}>是</option>
                                                <option value="false" ${ruleListVo.result[0].isDemandFirst eq 'false'?'selected="selected"':''}>否</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="">请选择是否要求首存</option>
                                                <option value="true">是</option>
                                                <option value="false">否</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group clearfix line-hi34 m-b-xxs" id="designatedGame">
                                <label class="col-xs-3 al-right">
                                    <span class="co-red3 m-r-xs">*</span>
                                    指定游戏：
                                </label>
                                <c:choose>
                                    <c:when test="${not empty ruleListVo.result[0].id}">
                                <table>
                                    <tr>
                                        <td>
                                        <div class="col-xs-8 p-x">
                                            <select style="width: 254px;height: 34px" onchange=window.top.page.getGameType(value)>
                                                    <c:forEach items="${ballType}" var="ballTypeName">
                                                        <c:choose>
                                                            <c:when test="${ballTypeName.code eq ruleListVo.result[0].gameType}">
                                                                <option value="${firstName[0].code}">${firstName[0].trans}</option>
                                                                <option value="${firstName[1].code}">${firstName[1].trans}</option>
                                                                <option value="${firstName[2].code}">${firstName[2].trans}</option>
                                                            </c:when>
                                                        </c:choose>
                                                    </c:forEach>

                                                   <c:forEach items="${lotteryType}" var="lotteryTypeName">
                                                       <c:choose>
                                                           <c:when test="${lotteryTypeName.code eq ruleListVo.result[0].gameType}">
                                                               <option value="${firstName[1].code}">${firstName[1].trans}</option>
                                                               <option value="${firstName[0].code}">${firstName[0].trans}</option>
                                                               <option value="${firstName[2].code}">${firstName[2].trans}</option>
                                                           </c:when>
                                                       </c:choose>
                                                   </c:forEach>

                                                   <c:forEach items="${chessCard}" var="chessCardName">
                                                       <c:choose>
                                                           <c:when test="${chessCardName.code eq ruleListVo.result[0].gameType}">
                                                               <option value="${firstName[2].code}">${firstName[2].trans}</option>
                                                               <option value="${firstName[0].code}">${firstName[0].trans}</option>
                                                               <option value="${firstName[1].code}">${firstName[1].trans}</option>
                                                           </c:when>
                                                       </c:choose>
                                                   </c:forEach>
                                            </select>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="col-xs-8 p-x">
                                            <select name="activityRule.gameType" class="fillOption" style="width: 254px;height: 34px">
                                                <c:forEach items="${ballType}" var="ballTypeName">
                                                    <c:choose>
                                                        <c:when test="${ballTypeName.code eq ruleListVo.result[0].gameType}">
                                                            <option value="${ballTypeName.code}">${ballTypeName.trans}</option>
                                                        </c:when>
                                                    </c:choose>
                                                </c:forEach>

                                                <c:forEach items="${lotteryType}" var="lotteryTypeName">
                                                    <c:choose>
                                                        <c:when test="${lotteryTypeName.code eq ruleListVo.result[0].gameType}">
                                                            <option value="${lotteryTypeName.code}">${lotteryTypeName.trans}</option>
                                                        </c:when>
                                                    </c:choose>
                                                </c:forEach>

                                                <c:forEach items="${chessCard}" var="chessCardName">
                                                    <c:choose>
                                                        <c:when test="${chessCardName.code eq ruleListVo.result[0].gameType}">
                                                            <option value="${chessCardName.code}">${chessCardName.trans}</option>
                                                        </c:when>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        </td>
                                        </tr>
                                    </table>
                                    </c:when>
                                    <c:otherwise>
                                        <table>
                                            <tr>
                                                <td>
                                        <div class="col-xs-8 p-x" >
                                            <select  style="width: 254px;height: 34px" onchange=window.top.page.getGameType(value)>
                                                <c:forEach items="${firstName}" var="firstTypeName">
                                                    <option value="${firstTypeName.code}">${firstTypeName['trans']}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                                </td>
                                                <td>
                                        <div class="col-xs-8 p-x">
                                            <select id="typeOption" name="activityRule.gameType" style="width: 254px;height: 34px">
                                                <c:forEach items="${ballType}" var="ballTypeName">
                                                <option value="${ballTypeName.code}">${ballTypeName.trans}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                                </td>
                                            </tr>
                                        </table>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <div class="form-group clearfix line-hi34 m-b-xxs hideIsExclusive">
                                <label class="col-xs-3 al-right">
                                    <span class="co-red3 m-r-xs">*</span>
                                    设置不同享：
                                </label>
                                <div class="col-xs-8 p-x">
                                    <select name="activityRule.isExclusive" style="width: 254px;height: 34px" onchange=window.top.page.isExclusiveActivity(value) >
                                        <c:choose>
                                            <c:when test="${not empty command.result.id}">
                                                <option value="true" ${ruleListVo.result[0].isExclusive eq 'true'?'selected="selected"':''}>是</option>
                                                <option value="false" ${ruleListVo.result[0].isExclusive eq 'false'?'selected="selected"':''}>否</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="">请选择是否同享</option>
                                                <option value="true">是</option>
                                                <option value="false">否</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group clearfix line-hi34 m-b-xxs" id="hideExclusiveActivity">
                                <label class="col-xs-3 al-right">
                                    <span class="co-red3 m-r-xs">*</span>
                                    不同享活动：
                                </label>
                                <div class="col-xs-8 p-x">
                                    <select name="activityRule.exclusiveActivity" style="width: 254px;height: 34px">
                                        <c:choose>
                                            <c:when test="${not empty ruleListVo.result[0].id}">
                                                <c:forEach items="${activityTypeEnum}" var="activityTypeName">
                                                    <option value="${activityTypeName['code']}" ${ruleListVo.result[0].exclusiveActivity eq 'true'?'selected="selected"':''}>${activityTypeName['trans']}</option>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="">请选择不共享活动</option>
                                                <c:forEach items="${activityTypeEnum}" var="activityTypeName">
                                                    <option value="${activityTypeName['code']}">${activityTypeName['trans']}</option>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </div>
                            </div>

                        </div>
                </div>

            </div>
            <%-- 活动规则 end --%>

            <%-- 优惠方式 start --%>
            <div class="wrapper white-bg shadow">
            <div class="present_wrap"><b>
                    <%--编辑显示活动类型--%>
                <c:forEach items="${activityTypeEnum}" var="activityTypeName">
                    <c:choose>
                        <c:when test="${command.result.activityClassifyKey eq activityTypeName.code}">
                            ${activityTypeName.trans}
                        </c:when>
                    </c:choose>
                </c:forEach>
                    <%--新增显示活动类型--%>
                <c:forEach items="${activityTypeEnum}" var="activityTypeName">
                    <c:choose>
                        <c:when test="${activityClassifyKey eq activityTypeName.code}">
                            ${activityTypeName.trans}
                        </c:when>
                    </c:choose>
                </c:forEach>
                优惠方式</b></div>

            <div class="operate-btn">
                <soul:button cssClass="btn btn-outline btn-lg" target="createTr" text="新增" opType="function">添加优惠方式</soul:button>
            </div>
            <table class="table table-condensed table-hover table-striped table-bordered" id="tab">
                <thead class="btn-filter">
                <tr role="row" class="bg-gray">
                    <th>是否为实物</th>
                    <th>
                        <c:choose>
                            <c:when test="${command.result.activityClassifyKey eq 'LOTTERY'}">
                                奖励
                            </c:when>
                            <c:when test="${activityClassifyKey eq 'LOTTERY'}">
                                奖励
                            </c:when>
                            <c:otherwise>
                                实物
                            </c:otherwise>
                        </c:choose>
                    </th>
                    <th>满足优惠值</th>
                    <th>优惠稽核倍数</th>
                    <th>优惠比例</th>
                    <th>顺序</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="trFill">
                <c:if test="${not empty command.result.id}">
                    <c:forEach items="${relationListVo.result}" var="p" varStatus="status">
                    <tr id="${status.index}">
                        <td>
                            <select name="activityWayRelation[${status.index}].isArticle">
                                <option value="false" ${p.isArticle eq 'false' ? 'selected="selected"':''}>否</option>
                                <option value="true" ${p.isArticle eq 'true' ? 'selected="selected"':''}>是</option>
                            </select>
                        </td>
                        <td>
                            <input name="activityWayRelation[${status.index}].article"
                                <c:choose>
                                    <c:when test="${p.isArticle eq true}">
                                        value="${p.article}"
                                    </c:when>
                                </c:choose>/>
                        </td>
                        <td><input name="activityWayRelation[${status.index}].preferentialValue" value="${p.preferentialValue}"/></td>
                        <td><input name="activityWayRelation[${status.index}].preferentialAudit" value="${p.preferentialAudit}"/></td>
                        <td><input name="activityWayRelation[${status.index}].preferentialRatio" value="${p.preferentialRatio}"/></td>
                        <td><input name="activityWayRelation[${status.index}].orderColumn" value="${p.orderColumn}"/></td>
                        <td><input type="button" value="删除" onclick=window.top.page.deltr("${status.index}")></td>
                    </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
                <%--优惠方式 end--%>

                <%--按钮--%>
            <div class="operate-btn">

                <soul:button target="${root}/activityMessage/persist.html" text="${views.common['OK']}" cssClass="btn btn-filter btn-lg" opType="ajax" post="getCurrentFormData" precall="validateForm" callback="goToLastPage" refresh="true">${views.common['OK']}</soul:button>
                <soul:button target="goToLastPage" text="${views.common['cancel']}" cssClass="btn btn-outline btn-filter btn-lg" opType="function">${views.common['cancel']}</soul:button>

            </div>
        </div>
            <%-- 优惠方式 end --%>

        </div>
    </form:form>
</div>

<script id="trTmpl" type="text/x-jsrender" >
    <tr id="{{:len}}">
       <td>
           <select name="activityWayRelation[{{:len}}].isArticle">
                <option value="true">是</option>
                <option value="false">否</option>
           </select>
       </td>
       <td><input name='activityWayRelation[{{:len}}].article' value='' class='{{:len}}'/></td>
       <td><input name='activityWayRelation[{{:len}}].preferentialValue' value=''  /></td>
       <td><input name='activityWayRelation[{{:len}}].preferentialAudit' value='' /></td>
       <td><input name='activityWayRelation[{{:len}}].preferentialRatio' value='' /></td>
       <td><input name='activityWayRelation[{{:len}}].orderColumn' value=''/></td>
       <td><input type="button" value="删除" onclick=window.top.page.deltr('{{:len}}')></td>
    </tr>
</script>
<!--//region your codes 4-->
<soul:import res="site/activitymessage/ActivityEdit"/>
<!--//endregion your codes 4-->

