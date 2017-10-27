<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/include/include.inc.jsp" %>
<%@ include file="/include/include.head.jsp" %>
<%@ include file="/include/include.js.jsp" %>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
    <title>Bet Statistics</title>
    <link rel="stylesheet" href="${resRoot}/themes/default/gui-base.css">
    <link rel="stylesheet" href="${resRoot}/themes/default/gui-skin-default.css">
    <link rel="stylesheet" href="${resRoot}/themes/default/common_player.css">
    <link rel="stylesheet" href="${resRoot}/themes/default/page.css">
    <link rel="stylesheet" href="${resComRoot}/themes/default/jquery/plugins/jquery.poshytip/poshytip.css">
    <script type="text/javascript">
        curl(['site/home/TopPage','site/match/cqssc/BetStatistics'], function(TopPage,BetStatistics) {
            topPage = new TopPage();
            comet = new BetStatistics();
        });
    </script>
</head>
<body style="overflow-y: auto;overflow-x: auto;">
<%--<%@ include file="/include/header.jsp"%>--%>

<div class="content" >
    <input id="matchId" style="display: none;"/>
    <div class="lottery-group bg-navy al-center clearfix">
        第<span id="lastNo"></span>期<span class="label">官方开奖</span>
        <ul class="list-unstyled al-center p-t-lg">

        </ul>
    </div>
    <div class="countDown al-center bg-gray3">
        第<span id="no"></span>期投注截止 : <span id="countDown"></span>
    </div>

    <table class="table table-bordered">
        <thead><tr><th colspan="12"  style="text-align: center">总和</th></tr></thead>
        <tbody>
        <tr>
            <td>总和大</td><td><span name="odds" id="B_A"></span></td><td><span name="money" id="B_A_M"></span></td>
            <td>总和小</td><td><span name="odds" id="S_A"></span></td><td><span name="money" id="S_A_M"></span></td>
            <td>总和单</td><td><span name="odds" id="O_A"></span></td><td><span name="money" id="O_A_M"></span></td>
            <td>总和双</td><td><span name="odds" id="E_A"></span></td><td><span name="money" id="E_A_M"></span></td>
        </tr>
        </tbody>
    </table>

    <table class="table table-bordered table-bet pull-left w20 ng-scope" style="width: 20%;">
        <thead><tr><th colspan="3">万位</th></tr></thead>
        <tbody>
            <tr><td>大</td><td><span name="odds" id="B_5"></span></td><td><span name="money" id="B_5_M"></span></td></tr>
            <tr><td>小</td><td><span name="odds" id="S_5"></span></td><td><span name="money" id="S_5_M"></span></td></tr>
            <tr><td>单</td><td><span name="odds" id="O_5"></span></td><td><span name="money" id="O_5_M"></span></td></tr>
            <tr><td>双</td><td><span name="odds" id="E_5"></span></td><td><span name="money" id="E_5_M"></span></td></tr>
            <tr><td>0</td><td><span name="odds" id="0_5"></span></td><td><span name="money" id="0_5_M"></span></td></tr>
            <tr><td>1</td><td><span name="odds" id="1_5"></span></td><td><span name="money" id="1_5_M"></span></td></tr>
            <tr><td>2</td><td><span name="odds" id="2_5"></span></td><td><span name="money" id="2_5_M"></span></td></tr>
            <tr><td>3</td><td><span name="odds" id="3_5"></span></td><td><span name="money" id="3_5_M"></span></td></tr>
            <tr><td>4</td><td><span name="odds" id="4_5"></span></td><td><span name="money" id="4_5_M"></span></td></tr>
            <tr><td>5</td><td><span name="odds" id="5_5"></span></td><td><span name="money" id="5_5_M"></span></td></tr>
            <tr><td>6</td><td><span name="odds" id="6_5"></span></td><td><span name="money" id="6_5_M"></span></td></tr>
            <tr><td>7</td><td><span name="odds" id="7_5"></span></td><td><span name="money" id="7_5_M"></span></td></tr>
            <tr><td>8</td><td><span name="odds" id="8_5"></span></td><td><span name="money" id="8_5_M"></span></td></tr>
            <tr><td>9</td><td><span name="odds" id="9_5"></span></td><td><span name="money" id="9_5_M"></span></td></tr>
        </tbody>
    </table>
    <table class="table table-bordered table-bet pull-left w20 ng-scope" style="width: 20%;">
        <thead><tr><th colspan="3">千位</th></tr></thead>
        <tbody>
        <tr><td>大</td><td><span name="odds" id="B_4"></span></td><td><span name="money" id="B_4_M"></span></td></tr>
        <tr><td>小</td><td><span name="odds" id="S_4"></span></td><td><span name="money" id="S_4_M"></span></td></tr>
        <tr><td>单</td><td><span name="odds" id="O_4"></span></td><td><span name="money" id="O_4_M"></span></td></tr>
        <tr><td>双</td><td><span name="odds" id="E_4"></span></td><td><span name="money" id="E_4_M"></span></td></tr>
        <tr><td>0</td><td><span name="odds" id="0_4"></span></td><td><span name="money" id="0_4_M"></span></td></tr>
        <tr><td>1</td><td><span name="odds" id="1_4"></span></td><td><span name="money" id="1_4_M"></span></td></tr>
        <tr><td>2</td><td><span name="odds" id="2_4"></span></td><td><span name="money" id="2_4_M"></span></td></tr>
        <tr><td>3</td><td><span name="odds" id="3_4"></span></td><td><span name="money" id="3_4_M"></span></td></tr>
        <tr><td>4</td><td><span name="odds" id="4_4"></span></td><td><span name="money" id="4_4_M"></span></td></tr>
        <tr><td>5</td><td><span name="odds" id="5_4"></span></td><td><span name="money" id="5_4_M"></span></td></tr>
        <tr><td>6</td><td><span name="odds" id="6_4"></span></td><td><span name="money" id="6_4_M"></span></td></tr>
        <tr><td>7</td><td><span name="odds" id="7_4"></span></td><td><span name="money" id="7_4_M"></span></td></tr>
        <tr><td>8</td><td><span name="odds" id="8_4"></span></td><td><span name="money" id="8_4_M"></span></td></tr>
        <tr><td>9</td><td><span name="odds" id="9_4"></span></td><td><span name="money" id="9_4_M"></span></td></tr>
        </tbody>
    </table>
    <table class="table table-bordered table-bet pull-left w20 ng-scope" style="width: 20%;">
        <thead><tr><th colspan="3">百位</th></tr></thead>
        <tbody>
        <tr><td>大</td><td><span name="odds" id="B_3"></span></td><td><span name="money" id="B_3_M"></span></td></tr>
        <tr><td>小</td><td><span name="odds" id="S_3"></span></td><td><span name="money" id="S_3_M"></span></td></tr>
        <tr><td>单</td><td><span name="odds" id="O_3"></span></td><td><span name="money" id="O_3_M"></span></td></tr>
        <tr><td>双</td><td><span name="odds" id="E_3"></span></td><td><span name="money" id="E_3_M"></span></td></tr>
        <tr><td>0</td><td><span name="odds" id="0_3"></span></td><td><span name="money" id="0_3_M"></span></td></tr>
        <tr><td>1</td><td><span name="odds" id="1_3"></span></td><td><span name="money" id="1_3_M"></span></td></tr>
        <tr><td>2</td><td><span name="odds" id="2_3"></span></td><td><span name="money" id="2_3_M"></span></td></tr>
        <tr><td>3</td><td><span name="odds" id="3_3"></span></td><td><span name="money" id="3_3_M"></span></td></tr>
        <tr><td>4</td><td><span name="odds" id="4_3"></span></td><td><span name="money" id="4_3_M"></span></td></tr>
        <tr><td>5</td><td><span name="odds" id="5_3"></span></td><td><span name="money" id="5_3_M"></span></td></tr>
        <tr><td>6</td><td><span name="odds" id="6_3"></span></td><td><span name="money" id="6_3_M"></span></td></tr>
        <tr><td>7</td><td><span name="odds" id="7_3"></span></td><td><span name="money" id="7_3_M"></span></td></tr>
        <tr><td>8</td><td><span name="odds" id="8_3"></span></td><td><span name="money" id="8_3_M"></span></td></tr>
        <tr><td>9</td><td><span name="odds" id="9_3"></span></td><td><span name="money" id="9_3_M"></span></td></tr>
        </tbody>
    </table>
    <table class="table table-bordered table-bet pull-left w20 ng-scope" style="width: 20%;">
        <thead><tr><th colspan="3">十位</th></tr></thead>
        <tbody>
        <tr><td>大</td><td><span name="odds" id="B_2"></span></td><td><span name="money" id="B_2_M"></span></td></tr>
        <tr><td>小</td><td><span name="odds" id="S_2"></span></td><td><span name="money" id="S_2_M"></span></td></tr>
        <tr><td>单</td><td><span name="odds" id="O_2"></span></td><td><span name="money" id="O_2_M"></span></td></tr>
        <tr><td>双</td><td><span name="odds" id="E_2"></span></td><td><span name="money" id="E_2_M"></span></td></tr>
        <tr><td>0</td><td><span name="odds" id="0_2"></span></td><td><span name="money" id="0_2_M"></span></td></tr>
        <tr><td>1</td><td><span name="odds" id="1_2"></span></td><td><span name="money" id="1_2_M"></span></td></tr>
        <tr><td>2</td><td><span name="odds" id="2_2"></span></td><td><span name="money" id="2_2_M"></span></td></tr>
        <tr><td>3</td><td><span name="odds" id="3_2"></span></td><td><span name="money" id="3_2_M"></span></td></tr>
        <tr><td>4</td><td><span name="odds" id="4_2"></span></td><td><span name="money" id="4_2_M"></span></td></tr>
        <tr><td>5</td><td><span name="odds" id="5_2"></span></td><td><span name="money" id="5_2_M"></span></td></tr>
        <tr><td>6</td><td><span name="odds" id="6_2"></span></td><td><span name="money" id="6_2_M"></span></td></tr>
        <tr><td>7</td><td><span name="odds" id="7_2"></span></td><td><span name="money" id="7_2_M"></span></td></tr>
        <tr><td>8</td><td><span name="odds" id="8_2"></span></td><td><span name="money" id="8_2_M"></span></td></tr>
        <tr><td>9</td><td><span name="odds" id="9_2"></span></td><td><span name="money" id="9_2_M"></span></td></tr>
        </tbody>
    </table>
    <table class="table table-bordered table-bet pull-left w20 ng-scope" style="width: 20%;">
        <thead><tr><th colspan="3">个位</th></tr></thead>
        <tbody>
        <tr><td>大</td><td><span name="odds" id="B_1"></span></td><td><span name="money" id="B_1_M"></span></td></tr>
        <tr><td>小</td><td><span name="odds" id="S_1"></span></td><td><span name="money" id="S_1_M"></span></td></tr>
        <tr><td>单</td><td><span name="odds" id="O_1"></span></td><td><span name="money" id="O_1_M"></span></td></tr>
        <tr><td>双</td><td><span name="odds" id="E_1"></span></td><td><span name="money" id="E_1_M"></span></td></tr>
        <tr><td>0</td><td><span name="odds" id="0_1"></span></td><td><span name="money" id="0_1_M"></span></td></tr>
        <tr><td>1</td><td><span name="odds" id="1_1"></span></td><td><span name="money" id="1_1_M"></span></td></tr>
        <tr><td>2</td><td><span name="odds" id="2_1"></span></td><td><span name="money" id="2_1_M"></span></td></tr>
        <tr><td>3</td><td><span name="odds" id="3_1"></span></td><td><span name="money" id="3_1_M"></span></td></tr>
        <tr><td>4</td><td><span name="odds" id="4_1"></span></td><td><span name="money" id="4_1_M"></span></td></tr>
        <tr><td>5</td><td><span name="odds" id="5_1"></span></td><td><span name="money" id="5_1_M"></span></td></tr>
        <tr><td>6</td><td><span name="odds" id="6_1"></span></td><td><span name="money" id="6_1_M"></span></td></tr>
        <tr><td>7</td><td><span name="odds" id="7_1"></span></td><td><span name="money" id="7_1_M"></span></td></tr>
        <tr><td>8</td><td><span name="odds" id="8_1"></span></td><td><span name="money" id="8_1_M"></span></td></tr>
        <tr><td>9</td><td><span name="odds" id="9_1"></span></td><td><span name="money" id="9_1_M"></span></td></tr>
        </tbody>
    </table>
</div>
</body>
</html>

