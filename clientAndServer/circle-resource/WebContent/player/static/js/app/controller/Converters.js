/**
 * Created by black on 2016/10/25.
 * converters函数
 */
define(['jsrender',
        'site/common/MoneyFormat',
        'site/common/TimeFormat'], function (jsrender, MoneyFormat, TimeFormat) {

    /**
     * data数据判断
     */
    $.views.helpers({
        "dataIsEmpty": function (code) {
            if (code == 0){

                return false;
            }else {

                return true;
            }
        },
        //状态为10房间显示
        "showRoom": function (status) {
            if (status == 10) {

                return true;
            }else {

                return false;
            }
        },
        //判断数据是否变化
        "updateDealerCoin": function (useableBalance) {
            if (useableBalance != 0) {

                return true;
            }else {

                return false;
            }
        },
        //胜负显示
        "winLoseShow": function (coin) {
            if (coin >= 0) {

                return true;
            } else {

                return false;
            }
        }
    });

    /**
     * money自定义转换
     */
    $.views.converters({
        //普通显示
        "moneyFormat": function (balance) {

            return MoneyFormat.format(balance);
        },
        //流水记录
        "moneyConverter": function (transactionMoney) {
            if (transactionMoney > 0){

                return "+" + transactionMoney;
            }else {

                return transactionMoney;
            }
        },
        //金币千位分隔符-上庄列表显示
        "separatorMoney": function (bankerMoney) {
            return MoneyFormat.formatBy1000(bankerMoney);
        }
    });

    /**
     * 格式化时间
     */
    $.views.converters({
        //月-日/时：分 MM-dd/hh:mm
        "timeFormat": function (completionTime) {
            Date.prototype.format = TimeFormat.format;
            return new Date(completionTime).format("MM-dd/hh:mm");
        },
        //时：分 hh:mm
        "timeFormatHM": function (completionTime) {
            Date.prototype.format = TimeFormat.format;
            return new Date(completionTime).format("hh:mm");
        }
    });

    /**
     * 盈利榜
     */
    $.views.converters({
        //奖牌设置
        "medalChange": function (index) {
            if (index < 4){

                return "";
            }else {

                return index;
            }
        }
    });

    /**
     * 玩家
     */
    $.views.converters({
        //登陆玩家头像显示
        "infoHeadIcon": function (avatarUrl) {
            var imagePath = "static/" + avatarUrl;
            return "<img src="+imagePath+" />";
        },
        //排行榜头像显示
        "listHeadIcon": function (avatarUrl) {
            var imagePath = "static/" + avatarUrl;
            return "<img class='list_head' src="+imagePath+" />";
        },
        //玩家昵称加*显示
        "hidePartUserName":function (userName) {
            if (userName.length == 11) {
                var reg = /^(\d{3})\d{4}(\d{4})$/;
                return userName.replace(reg,"$1****$2");
            }else {

                return userName.substring(0,userName.length-2)+"**";
            }
        },
        //玩家昵称过长缩短显示
        "partUserName": function (userName) {
            if (userName.length >= 8) {
                return userName.substring(0,6)+"**";
            }else {
                return userName;
            }
        }
    });

    /**
     * 账目
     */
    $.views.converters({
        //交易类型
        "transactionType": function (transactionType) {
            if (transactionType == 'DEPOSIT'){

                return "充值";
            }else {

                return "提现";
            }
        },
        //颜色控制
        "contentColor": function (transactionMoney) {
            if (!isNaN(transactionMoney)){

                if (transactionMoney > 0){

                    return "green";
                }else {
                    return "red";
                }
            }else {
                if (transactionMoney == 'DEPOSIT'){

                    return "";
                }else {

                    return "Account_content_red";
                }
            }
        }
    });

    /**
     * 百人大战
     */
    $.views.converters({
        //房间列表背景调控
        "changeBgColor": function(minLimitPlayerBlance){
            if (minLimitPlayerBlance === 1000) return "yellow";
            if (minLimitPlayerBlance === 2000) return "orange";
            if (minLimitPlayerBlance === 5000) return "green";
            if (minLimitPlayerBlance === 10000) return "red";
            if (minLimitPlayerBlance === 20000) return "blue";
            if (minLimitPlayerBlance === 50000) return "purple";
        },
        //走势图大小结果显示
        "bigOrSmall": function (outCome) {
            if (outCome == '1'){

                return "win"
            }else if (outCome == '0') {

                return "lose";
            }
        },
        //记录胜负结构显示
        "winOrLose":function (resultMoney) {
            if (resultMoney > 0) {

                return "胜";
            }else {

                return "负";
            }
        },
        //记录玩法名显示
        "partModelName": function (gamemodelname) {
            return gamemodelname.substring(0,2);
        }

    });

    /**
     * 玩家游戏设置
     */
    $.views.converters({
        //声音开关控制
        "voiceSwitch": function(voice){
            if (voice == true) {

                return "open";
            }else if (voice == false) {

                return "close";
            //声音设置默认为开
            }else {

                return "open";
            }
        }
    });

});
