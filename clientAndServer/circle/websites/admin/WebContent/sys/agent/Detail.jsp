<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<form:form action="${root}/" method="post">
<div id="agentDetail" class="row">
    <div class="position-wrap clearfix">
        <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
        <span>${views.sysResource['角色']}</span><span>/</span><span>${views.sysResource['代理管理']}</span>
        <%--<a href="/vAgentManage/list.html" nav-target="mainFrame" class="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn">
            <em class="fa fa-caret-left"></em>${views.common['return']}
        </a>--%>
        <soul:button target="goToLastPage" refresh="true" cssClass="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn" text="" opType="function">
            <em class="fa fa-caret-left"></em>${views.common['return']}
        </soul:button>
    </div>
    <div class="col-lg-12">
        <div class="wrapper white-bg clearfix shadow">
            <%@include file="detail.include/ButtonMenu.jsp"%>

            <div class="panel blank-panel">
                <div class="">
                    <div class="panel-options">
                        <ul class="nav nav-tabs p-l-sm p-r-sm">
                            <li class="active">
                                <a data-toggle="tab" index="1" aria-expanded="true" data-href="/vAgentManage/detail/agentDetail.html?search.id=${command.result.id}" data-load="1">${views.role['Agent.detail.agentInfo']}</a>
                            </li>
                            <li>
                                <a data-toggle="tab" index="2" aria-expanded="false" data-href="/userAgent/agentMessage.html?search.agentId=${command.result.id}" >商户信息</a>
                            </li>
                            <li>
                                <a data-toggle="tab" index="3" aria-expanded="false" data-href="/agentQuotaTransaction/list.html?search.agentId=${command.result.id}" >额度日志</a>
                            </li>
                            <li>
                                <a data-toggle="tab" index="4" aria-expanded="false" data-href="/userAgentGame/list.html?search.agentId=${command.result.id}" >代理游戏</a>
                            </li>
                            <li>
                                <a data-toggle="tab" index="5" aria-expanded="false" data-href="/playerRemark/remark.html?search.entityUserId=${command.result.id}&search.operatorId=${command.result.id}" >${views.role['agent.detail.remark']}</a>
                            </li>
                            <li>
                                <a data-toggle="tab" index="6" aria-expanded="false" data-href="/vAgentManage/detail/log.html?search.operatorId=${command.result.id}" >${views.role['agent.detail.log']}</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="panel-body">
                      <div class="panel-body">
                          <div class="tab-content" id="tab-content1">
                              <%@include file="detail.include/AgentDetail.jsp" %>
                          </div>
                          <div class="tab-content hide" id="tab-content2"></div>
                          <div class="tab-content hide" id="tab-content3"></div>
                          <div class="tab-content hide" id="tab-content4"></div>
                          <div class="tab-content hide" id="tab-content5"></div>
                          <div class="tab-content hide" id="tab-content6"></div>
                      </div>
                </div>
            </div>
        </div>
    </div>
</div>
</form:form>
<soul:import res="site/agent/View"/>