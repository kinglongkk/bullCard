<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<%--@elvariable id="command" type="g.model.player.vo.VSysUserListVo"--%>
<div class="row">
    <form:form action="${root}/parameterConfig/indexs.html" method="post">
        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
            <span>游戏</span><span>/</span>
            <span>游戏参数</span>
            <a name="returnValue" style="display: none" href="/parameterConfig/indexs.html" nav-target="mainFrame"></a>
        </div>
        <div class="col-lg-12">
            <div class="wrapper white-bg shadow">
                <div class="filter-wraper clearfix border-b-1">
                    <div class="search-wrapper btn-group pull-right m-r-n-xs">
                        <div class="input-group">
                            <span class="input-group-btn">
                                <soul:button cssClass="btn btn-filter" confirm="您确认要恢复默认吗?" tag="button" opType="function" text="恢复默认" target="defaultParam" >恢复默认</soul:button>
                            </span>
                        </div>
                    </div>
                </div>
                <div id="editable_wrapper" class="search-list-container dataTables_wrapper" role="grid">
                    <%@ include file="IndexPartial.jsp" %>
                </div>
            </div>
        </div>
    </form:form>
</div>
<soul:import res="site/setting/PlatformParam/preferences/ParamConfig"/>

