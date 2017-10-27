<%--@elvariable id="command" type="g.model.playerstatistics.vo.PlayerSummeryListVo"--%>
<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/include/include.inc.jsp" %>
<style>
    .agentDesk{
        border: 1px solid  #e5e6e7;
        width: 528px;
        height: 320px;
        float: left;
        margin:10px 20px 20px 10px;
        text-align: center;
        line-height: 100px;/*值等于元素高度的值*/
        position:relative;
    }
    .childdiv{
        height: 318px;
    }
    .big{
        position:absolute;
        width: 95%;
        height: 600px;
        z-index: 1000;
        border: 2px solid  #e5e6e7;
        background-color: white;
    }
    .childBig{
        height: 590px;
    }
    .spanBig{
        position:absolute;
        /*margin-top:-52px;*/
        right:0;
        z-index:999;
        /*cursor: pointer;*/

        margin-top: 30px;
        cursor: pointer;
        margin-left: 480px;
        float: left
    }
</style>
<!--//region your codes 1-->
<div class="table-responsive">
    <!--//endregion your codes 1-->
    <input id="dataStr" name="dataStr" value="${dataStr}" type="hidden">
    <input id="dateTime" name="dateTime" value="${dateTime}" type="hidden">
    <div class="sy_content">
        <div class="agentDesk">
            <span class="spanBig fa fa-search-plus" onclick="bigOrSmall(this,'echartIndexProfit');">放大</span>
            <div id="echartIndexProfit" class="childdiv">加载中.....</div>
        </div>
        <div class="agentDesk">
            <span class="spanBig fa fa-search-plus" onclick="bigOrSmall(this,'echartIndexLines');">放大</span>
            <div id="echartIndexLines" class="childdiv">加载中.....</div>
        </div>
        <div class="agentDesk">
            <span class="spanBig fa fa-search-plus" onclick="bigOrSmall(this,'echartIndexPie');">放大</span>
             <div id="echartIndexPie" class="childdiv">加载中.....</div>
        </div>
        <div class="agentDesk">
            <span class="spanBig fa fa-search-plus" onclick="bigOrSmall(this,'echartIndexAdd');">放大</span>
            <div id="echartIndexAdd" class="childdiv">加载中.....</div>
        </div>
        <div class="agentDesk">
            <span class="spanBig fa fa-search-plus" onclick="bigOrSmall(this,'echartIndexTotal');">放大</span>
            <div id="echartIndexTotal" class="childdiv">加载中.....</div>
        </div>
        <div class="agentDesk">
            <span class="spanBig fa fa-search-plus" onclick="bigOrSmall(this,'echartIndexActive');">放大</span>
            <div id="echartIndexActive" class="childdiv">加载中.....</div>
        </div>

    </div>
</div>
<soul:import res="site/playerstatistics/desktopMenu"></soul:import>
