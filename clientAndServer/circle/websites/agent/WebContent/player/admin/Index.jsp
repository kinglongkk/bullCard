<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<%--@elvariable id="command" type="g.model.player.vo.VSysUserListVo"--%>
<div class="row">
    <form:form action="${root}/player/playerList.html" method="post">
        <div class="position-wrap clearfix">
            <h2><a class="navbar-minimalize" href="javascript:void(0)"><i class="icon iconfont">&#xe610;</i> </a></h2>
            <span>${views.sysResource['账号管理']}</span><span>/</span>
            <span>${views.sysResource['玩家管理']}</span>
            <a href="javascript:void(0)" class="pull-right siteMap"><i class="fa fa-sitemap"></i></a>
        </div>
        <div class="col-lg-12">
            <div class="wrapper white-bg shadow">
                <!--筛选条件-->
                <div class="filter-wraper clearfix border-b-1">
                    <a nav-target="mainFrame" href="/player/playerList.html" class="btn btn-outline btn-filter" ><i class="fa fa-refresh"></i><span class="hd">&nbsp;&nbsp;${views.common['refresh']}</span></a>
                    <div class="function-menu-show hide">
                        <soul:button permission="role:player_export" tag="button" cssClass="btn btn-outline btn-filter" callback="toExportHistory" text="${views.common['export']}"
                                     title="${views.role['player.dataExport']}" target="${root}/player/exportRecords.html" opType="dialog">
                            <i class="fa fa-sign-out"></i><span class="hd">&nbsp;&nbsp;${views.common['export']}</span>
                        </soul:button>
                        <div class="btn-group" id="player_tag">
                            <input type="hidden" value="true" id="hasLoadTag" >
                            <button data-toggle="dropdown" type="button" id="player_tag_btn" data-has-load="true" class="btn btn-filter btn-outline dropdown-toggle player_tag_dropdown_btn">
                                <i class="fa fa-tags"></i>
                                <span class="hd">&nbsp;&nbsp;${views.role['Player.list.playerTag.tag']}</span>
                                &nbsp;&nbsp;
                                <span class="caret"></span>
                            </button>

                            <ul class="dropdown-menu player_tag_dropdown_ul">
                                <div class="input-group label-search tag_stop_propagation">
                                    <input type="text" class="form-control tag_search_input" >
                                    <span class="input-group-addon cancel_search hide">×</span>
                                    <span class="input-group-addon go_search">
                                        <a href="javascript:void(0)">${views.common['search']}</a>
                                    </span>
                                </div>
                                <div class="label-menu-o tag_stop_propagation" id="player_tag_div">
                                </div>
                                <li class="divider m-t-none"></li>
                                <li class="m-b-xs bt m-t-n-xs" id="player_tag_btn_li">
                                    <soul:button target="${root}/playerTag/saveTag.html" opType="ajax" post="playerTag.playerTagGetData" tag="button" precall="playerTag.checkPlayerTagLen" cssClass="btn btn-filter btn-sm m-r-sm" text="${views.common['confirm']}" callback="playerTag.playerTagSaveCallBack"></soul:button>
                                    <soul:button target="${root}/vPlayerTag/list.html" callback="playerTag.playerTagSaveCallBack" cssClass="fil" tag="a" opType="dialog" text="${views.role['Player.list.tagManager']}">${views.common['manage']}</soul:button>
                                </li>
                            </ul>
                        </div>

                        <div class="btn-group">
                            <button data-toggle="dropdown" class="btn btn-filter btn-outline dropdown-toggle"><span class="hd">${views.common['more']}&nbsp;&nbsp;</span>
                                <span class="hd-show"><i class="fa fa-ellipsis-h"></i></span>
                                <span class="caret hd"></span>
                            </button>
                            <ul class="dropdown-menu">
                                <li>
                                    <soul:button precall="getSelectPlayerIds" target="${root}/player/groupSend/chooseSendType.html?playerIds={playerIds}" callback="thisQuery" text="${views.role['player.list.button.message']}" opType="dialog"><i class="fa fa-comments-o"></i>${views.role['player.list.button.message']}</soul:button>
                                </li>
                                <li><soul:button permission="role:player_cleanup" precall="" target="${root}/userPlayer/export.html" text="${views.role['Player.clearcontact.Export.clearcontact']}" callback="toExportHistory" opType="dialog"><i class="fa fa-eraser"></i>${views.role['Player.clearcontact.ClearContactInfo.index']}</soul:button></li>
                            </ul>
                        </div>
                    </div>
                    <div class="search-wrapper btn-group pull-right m-r-n-xs">
                        <div class="input-group">
                            <div class="input-group-btn">
                                账号:
                            </div>
                            <input type="text" name="search.username" class="form-control">
                            <span class="input-group-btn">
                                <soul:button cssClass="btn btn-filter" tag="button" opType="function" text="${views.common['search']}" target="query">
                                    <i class="fa fa-search"></i><span class="hd">&nbsp;${views.common['search']}</span>
                                </soul:button>
                            </span>
                        </div>
                    </div>
                </div>
                <div id="editable_wrapper" class="search-list-container dataTables_wrapper" role="grid">
                    <%@ include file="IndexPartial.jsp" %>
                </div>
            </div>
        </div>
        <a href="/share/exports/exportHistoryList.html" nav-target="mainFrame" class="hide" id="toExportHistory"></a>
    </form:form>
</div>
<soul:import res="site/player/admin/Index"/>

