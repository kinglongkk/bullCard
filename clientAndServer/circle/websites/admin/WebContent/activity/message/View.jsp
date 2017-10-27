<%--@elvariable id="command" type="g.model.activitymessage.vo.ActivityMessageVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>

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

				<%-- 活动信息 start--%>
				<div class="wrapper white-bg shadow">

					<div class="present_wrap"><b>
                            <%--显示活动类型--%>
                        <c:forEach items="${activityTypeEnum}" var="activityTypeName">
                            <c:choose>
                                <c:when test="${command.result.activityClassifyKey eq activityTypeName.code}">
                                    ${activityTypeName.trans}
                                </c:when>
                            </c:choose>
                        </c:forEach>
                        活动信息</b></div>

					<div class="clearfix m-t-sm">

						<div class="dataTables_wrapper">

							<div class="form-group clearfix line-hi34 m-b-xxs">
								<label class="col-xs-3 al-right">
									开始时间：
								</label>
                                <div class="col-xs-8 p-x">${soulFn:formatDateTz(command.result.startTime, DateFormat.DAY,timeZone)}</div>
							</div>

							<div class="form-group clearfix line-hi34 m-b-xxs">
								<label class="col-xs-3 al-right">
									结束时间：
								</label>
                                <div class="col-xs-8 p-x">${soulFn:formatDateTz(command.result.endTime, DateFormat.DAY,timeZone)}</div>
							</div>

							<div class="form-group clearfix line-hi34 m-b-xxs">
								<label class="col-xs-3 al-right">
									是否展示：
								</label>
                                <div class="col-xs-8 p-x">
                                	${command.result.isDisplay eq false ? '否':'是'}
								</div>
							</div>

							<div class="form-group clearfix line-hi34 m-b-xxs">
								<label class="col-xs-3 al-right">
									排序：
								</label>
                                <div class="col-xs-8 p-x">${command.result.orderNum}</div>
							</div>

						</div>

					</div>

				</div>
				<%-- 活动信息 end --%>

				<%-- 活动规则 start --%>
				<div class="wrapper white-bg shadow">

					<div class="present_wrap"><b>
                            <%--显示活动规则--%>
                        <c:forEach items="${activityTypeEnum}" var="activityTypeName">
                            <c:choose>
                                <c:when test="${command.result.activityClassifyKey eq activityTypeName.code}">
                                    ${activityTypeName.trans}
                                </c:when>
                            </c:choose>
                        </c:forEach>
                        活动规则</b></div>

					<div class="clearfix m-t-sm">

						<div class="dataTables_wrapper">

							<c:forEach items="${ruleListVo.result}" var="p" varStatus="status">

								<div class="form-group clearfix line-hi34 m-b-xxs">
									<label class="col-xs-3 al-right">
										有效时间：
									</label>
									<div class="col-xs-8 p-x">${p.effectiveTime}</div>
								</div>

								<div class="form-group clearfix line-hi34 m-b-xxs">
									<label class="col-xs-3 al-right">
										限制次数：
									</label>
									<div class="col-xs-8 p-x">${p.limitNumber}</div>
								</div>

								<div class="form-group clearfix line-hi34 m-b-xxs">
									<label class="col-xs-3 al-right">
										要求首存：
									</label>
									<div class="col-xs-8 p-x">
										${p.isDemandFirst eq false ? '否' : '是'}
									</div>
								</div>

								<c:choose>
								<c:when test="${p.isDesignatedGame eq true}">
								<div class="form-group clearfix line-hi34 m-b-xxs" style="display: block;">
									<label class="col-xs-3 al-right">
										指定游戏：
									</label>
									<div class="col-xs-8 p-x">
										<c:forEach items="${ruleListVo.result}" var="p">
											<c:forEach items="${ballType}" var="ballTypeName">
												<c:choose>
													<c:when test="${ballTypeName.code eq p.gameType}">
														${ballTypeName.trans}
													</c:when>
												</c:choose>
											</c:forEach>

											<c:forEach items="${lotteryType}" var="lotteryTypeName">
												<c:choose>
													<c:when test="${lotteryTypeName.code eq p.gameType}">
														${lotteryTypeName.trans}
													</c:when>
												</c:choose>
											</c:forEach>

											<c:forEach items="${chessCard}" var="chessCardName">
												<c:choose>
													<c:when test="${chessCardName.code eq p.gameType}">
														${chessCardName.trans}
													</c:when>
												</c:choose>
											</c:forEach>
										</c:forEach>
									</div>
								</div>
								</c:when>
									<c:otherwise>
								<div style="display: none;"/>
									</c:otherwise>
								</c:choose>

								<div class="form-group clearfix line-hi34 m-b-xxs">
									<label class="col-xs-3 al-right">
										设置不同享：
									</label>
									<div class="col-xs-8 p-x">
											${ruleListVo.result[0].isExclusive eq false ? '否' : '是'}
									</div>
								</div>

									<c:choose>
										<c:when test="${ruleListVo.result[0].isExclusive eq false}">
											<div style="display: none;"></div>
										</c:when>
										<c:otherwise>
											<div class="form-group clearfix line-hi34 m-b-xxs">
											<label class="col-xs-3 al-right">
												不同享活动：
											</label>
											<div class="col-xs-8 p-x">
													<c:forEach items="${activityTypeEnum}" var="activityTypeName">
														${ruleListVo.result[0].exclusiveActivity eq activityTypeName.code ? activityTypeName.trans : ''}
													</c:forEach>
											</div>
											</div>
										</c:otherwise>
									</c:choose>

							</c:forEach>

						</div>

					</div>

				</div>
				<%-- 活动规则 end--%>

				<%-- 活动优惠 start --%>
				<div class="wrapper white-bg shadow">

					<div class="present_wrap"><b>
                            <%--显示优惠方式--%>
                        <c:forEach items="${activityTypeEnum}" var="activityTypeName">
                            <c:choose>
                                <c:when test="${command.result.activityClassifyKey eq activityTypeName.code}">
                                    ${activityTypeName.trans}
                                </c:when>
                            </c:choose>
                        </c:forEach>
                        优惠方式</b></div>

					<table class="table table-condensed table-hover table-striped table-bordered">
						<thead class="btn-filter">
						<tr  role="row" class="bg-gray">
							<th>是否为实物</th>
							<th>实物</th>
							<th>满足优惠值</th>
							<th>优惠稽核倍数</th>
							<th>优惠比例</th>
							<th>顺序</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${relationListVo.result}" var="p" varStatus="status">
							<tr>
								<td>${p.isArticle eq true ? '是' : '否'}</td>
								<td>
									<c:choose>
										<c:when test="${p.isArticle eq true}">
											${p.article}
										</c:when>
									</c:choose>
								</td>
								<td>${p.preferentialValue}</td>
								<td>${p.preferentialAudit}</td>
								<td>${p.preferentialRatio}</td>
								<td>${p.orderColumn}</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>

				</div>
				<%-- 活动优惠 end --%>

			</div>

		</form:form>
	</div>
<!--//region your codes 4-->
<soul:import type="view"/>
<!--//endregion your codes 4-->