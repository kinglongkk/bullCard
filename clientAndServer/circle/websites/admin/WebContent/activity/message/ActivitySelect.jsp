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
            <a href="/activityMessage/list.html" nav-target="mainFrame" class="m-l-sm btn btn-outline btn-default btn-xs co-gray6 return-btn">
                <em class="fa fa-caret-left"></em>${views.common['return']}</a>
        </div>

        <div class="col-lg-12">

            <%-- 选择活动类型 start --%>
            <div class="wrapper white-bg shadow">
                <div class="present_wrap"><b>选择活动类型</b></div>

                <div class="clearfix m-t-sm">

                    <table class="table table-condensed table-hover table-striped table-bordered">
                        <tr style="height: 300px" role="row" class="bg-gray">
                            <td><a href="/activityMessage/create.html?result.activityClassifyKey=${activityTypeEnum[0].code}" nav-target="mainFrame">${activityTypeEnum[0].trans}</a></td>
                            <td><a href="/activityMessage/create.html?result.activityClassifyKey=${activityTypeEnum[1].code}" nav-target="mainFrame">${activityTypeEnum[1].trans}</a></td>
                            <td><a href="/activityMessage/create.html?result.activityClassifyKey=${activityTypeEnum[2].code}" nav-target="mainFrame">${activityTypeEnum[2].trans}</a></td>
                            <td><a href="/activityMessage/create.html?result.activityClassifyKey=${activityTypeEnum[3].code}" nav-target="mainFrame">${activityTypeEnum[3].trans}</a></td>
                            <td><a href="/activityMessage/create.html?result.activityClassifyKey=${activityTypeEnum[4].code}" nav-target="mainFrame">${activityTypeEnum[4].trans}</a></td>
                        </tr>
                    </table>

                </div>
            </div>
            <%-- 选择活动类型 end --%>
        </div>
    </form:form>
</div>
