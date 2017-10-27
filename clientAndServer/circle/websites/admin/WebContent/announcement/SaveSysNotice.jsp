<%--@elvariable id="command" type="g.model.company.operator.vo.SystemAnnouncementListVo"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/include/include.inc.jsp" %>

<div class="row">
    <div class="position-wrap clearfix">
        <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
        <span>${views.sysResource['服务']}</span><span>/</span><span>${views.sysResource['发布公告']}</span>
        <a href="javascript:void(0)" class="pull-right siteMap"><i class="fa fa-sitemap"></i></a>
    </div>
    <div class="col-lg-12 m-b">
        <div class="wrapper white-bg shadow">
            <!--表格内容-->
            <div id="editable_wrapper" class="dataTables_wrapper" role="grid">
                <div class="fundsContext step-finish p-b-lg clearfix">
                    <div class="col-xs-5 al-right">
                        <i class="success fa fa-smile-o"></i>
                    </div>
                    <div class="col-xs-7">
                        <div class="success p-t-sm">${messages.common['operation.success']}！</div>
                    </div>
                </div>
            </div>
            <div class="operate-btn ">
                <a href="/vSysAnnouncement/sysNotice.html" nav-target="mainFrame" class="btn btn-outline btn-filter btn-lg ">${messages.common['operation.continueOperation']}</a>
                <a href="/vSysAnnouncement/sysNoticeHistory.html" nav-target="mainFrame" class="btn btn-outline btn-filter btn-lg ">${messages.common['operation.showNotes']}</a>
            </div>
        </div>
    </div>
</div>