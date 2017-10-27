define(["view/bull100/viewBull","bull100/handleBull", "app/controller/gameMain", "bull100/ctrlBet",
        "app/view/soundBase", 'view/viewBase', "view/bull100/Animate"],
    function (vBull, hBull, gm, cBet, sBase, vBase, A) {try{
    var cBull = {
        maxCoinCount:256,
        onlyBig : false,//比大小模式,true只比大,false大小都比
        state:0,//1金币返回中
    };
    cBull.getSitIndex = function (seatIndex) {
        if(seatIndex<=0)return seatIndex*=-1;
        else return seatIndex+=vBull.SIT_START-1;
    };
    var pathHead = resRoot+"/";//images/head/";
    cBull.match = {matchId: 0, betChip: undefined, balance: 0, usableBalance: 0, mySitIndex: -2, minDealerMoney: 0,dealerDowned: false, resultCouncil: false};
    cBull.tipMsg = {notBalance: 1,keepDealer: false};
    cBull.isBanker = function () {
        return cBull.match.mySitIndex == vBull.SIT_BANKER;
    }
    cBull.initMatch = function(match1, newGame){ //match1比赛信息, newGame是否是新的游戏开始
        if(newGame){
            cBull.match.mySitIndex = -2;
        }else{
            cBull.match.balance = 0;
        }
        if(cBull.match.dealerDowned){
            vBull.setBankerEnable(true);
            cBull.match.dealerDowned = false;
        }
        cBull.match.matchId = match1.matchId;
        if(!newGame){//刚进入房间,只需把扑克牌背面都显示出来即可
            if(vBull.countDown.isBetting) {
                vBull.showPokerGroup();
                vBull.countDown._playerBetting = true;
            }
        }else{
            vBull.initGame();
        }
        vBull.countDown.setVal(match1.beginTimeNext, match1.settleTime);
    }
    cBull.upDealer = function (money) {
        gm.sendToServer("NbUpDealerIn",{coin : money});
        console.log("上庄成功")
    }
    cBull.keepDealer = function (money) {
        gm.sendToServer("NbKeepDealerIn",{coin : money});
        console.log("续庄成功" + money);
    }
    var _downDealer =function () {
        gm.sendToServer("NbDownDealerIn");
    }
    cBull.downDealer = function () {
        if(cBull.match.dealerDowned)return;
        if(cBull.match.mySitIndex ==  vBull.SIT_BANKER){
            vBase.msgDialog.show("您已做庄,强制下庄系统则继续帮您打完本局!", "确定", "取消", function(){
                cBull.match.dealerDowned = true;
                _downDealer();
            });
        }else{
            _downDealer();
        }
    }
    cBull.init=function (nbIntoRoomOut) {
        cBull.match.mySitIndex = -2;
        cBull.match.betChip = nbIntoRoomOut.betChip;
        cBull.match.minDealerMoney = nbIntoRoomOut.minDealerCoin;
        console.log(cBull.match.minDealerMoney)
        vBull.init(cBull);
        var bankerClick = function(){
            if(cBull.match.dealerDowned)return;
            gm.sendToServer("NbUpDealerListIn");
            cBull.tipMsg.keepDealer = false;
        }
        vBull.$dialogBanker.click(bankerClick);
        vBull.$bankerAddMoney.click(bankerClick);
        vBull.$bankerDown.click(cBull.downDealer);
        hBull.init(cBull);
        cBet.init(cBull);
        // $('.cards').click(function(){cBull.test()});//TODO 游戏功能测试模块按钮,测试时打开，无需测试请注释掉。
        //$('.onlookers_jb').click(function () {vBull.buttonGroup.updateStakeMoney(parseInt(Math.random()*152458));})// TODO 押注按钮测试
        cBull.initMatch(nbIntoRoomOut.match);
        cBull.setSitPlayers(nbIntoRoomOut.seats, nbIntoRoomOut.match.dealer, false);
        cBull.setSelfMoney(nbIntoRoomOut.nbSelf);
    };
    cBull.clear=function (){
      vBull.clear();
    };
    cBull.setSelfMoney = function (nbSelf, showMoneyDelay) {//设置自己的余额
        if(cBull.match.balance == nbSelf.balance && cBull.match.usableBalance == nbSelf.usableBalance){
            return;
        }
        cBull.match.balance = nbSelf.balance;
        cBull.match.usableBalance = nbSelf.usableBalance;//设置投注选择区可用余额
        if(!showMoneyDelay){
            vBull.sits[vBull.SIT_MYSELF].setMoney(nbSelf.balance);
            if(cBull.match.mySitIndex>=vBull.SIT_START)vBull.sits[cBull.match.mySitIndex].setMoney(nbSelf.balance);
        }
        vBull.buttonGroup.updateStakeMoney(cBull.match.usableBalance);
    }
    cBull.setSitPlayers = function(seats, dealer, newGame){//初始化座位, newGame是否新开局
        //每次比赛开始全部覆盖,暂时先这样做
        var players = new Array(9);
        var oldSitIndex = cBull.match.mySitIndex;
        if(dealer || cBull.match.mySitIndex != vBull.SIT_BANKER){
            cBull.match.mySitIndex = -1;
        }
        players[vBull.SIT_BANKER] = dealer;
        for (var i = 0; i < seats.length; i++) {
            var seat = seats[i], index = cBull.getSitIndex(seat.seatIndex), player = seat.player;
            if(index == vBull.SIT_BANKER)continue;//庄家数据过滤掉
            players[index] = player;
        }
        for (var i = 0; i < 9; i++) {
            if(i == vBull.SIT_OTHERS)continue;
            if(i == vBull.SIT_BANKER && !dealer)continue;
            var player = players[i];
            var sit = vBull.sits[i];
            if(!player){
                sit.hide();
            }else {
                var coin = player.coin;
                if(i==vBull.SIT_BANKER) coin = player.usableBalance;
                // if(i==vBull.SIT_MYSELF && cBull.match.mySitIndex == vBull.SIT_BANKER) coin = player.dealerCoin
                sit.setVal(pathHead + player.icon, coin, player.playerId, player.nickname);
                if (i == vBull.SIT_MYSELF) cBull.match.playerId = player.playerId;
                else if (player.playerId == cBull.match.playerId && cBull.match.mySitIndex == -1) {
                    cBull.match.mySitIndex = i;
                }
            }
        }
        if(oldSitIndex==-2 || (oldSitIndex != cBull.match.mySitIndex &&
            (oldSitIndex == vBull.SIT_BANKER || cBull.match.mySitIndex == vBull.SIT_BANKER))){//自己是庄家
            vBull.showBankerBar(cBull.match.mySitIndex == vBull.SIT_BANKER);
        }
        if(dealer){//有庄家信息表示进入房间或者新开局初始化
            vBull.gameStart(cBull.match.mySitIndex == vBull.SIT_BANKER, newGame);
        }
    }
    cBull.updatePlayerMoney = function (playerMoney) {
        gm.setBalance = playerMoney;
    };
    var dialogBanker = null;
    cBull.openBankerDialog = function(datas){
        if(cBull.match.dealerDowned){
            vBull.setBankerEnable(false);
            if(dialogBanker != null && dialogBanker.dialog.isShowing()){
                dialogBanker.dialog.hide();
            }
        }else{
            if(dialogBanker == null) {
                requirejs(["app/controller/dialog/Banker"],
                    function (Banker) {
                        dialogBanker = new Banker(datas, cBull.match.minDealerMoney, datas.dealer.maxDealerCoin, cBull.match.playerId);
                    }
                );
            }else{
                if (datas.tip.code === "600011" && !vBase.tip.isShowing()) {//庄家主动续庄
                    vBase.tip.show(datas.tip.tip, "");
                }
                dialogBanker.renderTemplate(datas, cBull.match.minDealerMoney, datas.dealer.maxDealerCoin);
                //服务器数据定义不统一,暂时先即时封装对象
                cBull.setSelfMoney({balance:datas.dealer.coin, usableBalance:datas.dealer.usableBalance});
            }
        }
    }
    //庄家上下庄续庄消息
    cBull.DealerMsg = function (warningDealerOut) {
        if (warningDealerOut.tip.code === "200004") {//庄家金币不足续庄
            vBase.msgDialog.show(warningDealerOut.tip.tip, "确定");
            return;
        }
        if (warningDealerOut.tip.code === "600008") {//庄家被动续庄
            console.log(warningDealerOut);
            vBase.msgDialog.show(warningDealerOut.tip.tip, "确定", "取消",
                function () {
                    cBull.keepDealer(warningDealerOut.coin);
                    console.info("续庄成功");
                    cBull.tipMsg.keepDealer = true;
                },
                function () {
                    cBull.match.dealerDowned = true;
                    _downDealer();
                }
            );
            return;
        }
        !vBase.tip.isShowing() ? vBase.tip.show(warningDealerOut.tip.tip, "") : "";
        console.log(warningDealerOut.tip.code, warningDealerOut.tip.tip);
    };
    var isBigger = function(deskAreaIndex) {
        return vBull.pokerGroups[deskAreaIndex + 1].isBigger();
    }
    //百人大战胜负结算动画
    cBull.windMoney = function(datas) {
        requirejs(["app/controller/BullSettleAnimation"], function (BullSettleAnimation) {
            new BullSettleAnimation(datas);
        });
    };
    cBull.bankerWin=function(){
        var sit=vBull.sits[vBull.SIT_BANKER];
        var maxCnt = 0;
        for (var i=0;i < 4;i++) {
            var bigger = isBigger(i);
            if(!bigger){//庄家赢,收回该坑的所有金币
                maxCnt = Math.max(maxCnt, vBull.coinToSit(vBull.deskAreas[i], sit));
            }
            if(!cBull.onlyBig && bigger){
                maxCnt = Math.max(maxCnt, vBull.coinToSit(vBull.deskAreas[i+4], sit));
            }
        }
        sit.refleshMoney();
        if(maxCnt == 0) cBull.bankerLose();//无动画可播，进入下一场动画
        else{
            A.setTimeOut(500+maxCnt*5, cBull.bankerLose);
            sBase.plays(vBull.SOUND.BET_ON_DESK, undefined, 500, 270);
        }
    }
    cBull.bankerLose=function(){
        var maxCnt = 0;
        var sit=vBull.sits[vBull.SIT_BANKER];
        for (var i=0;i<4;i++) {
            var bigger = isBigger(i);
            if(bigger){//庄家输,把金币放入该坑
                maxCnt = Math.max(maxCnt, vBull.bankerToDesk(sit,vBull.deskAreas[i]));
            }
            if(!cBull.onlyBig && !bigger){
                maxCnt = Math.max(maxCnt, vBull.bankerToDesk(sit,vBull.deskAreas[i+4]));
            }
        }
        if(maxCnt == 0) cBull.othersFinish();//无动画可播，进入下一场动画
        else{
            A.setTimeOut(500+maxCnt*5, cBull.othersWin);
            sBase.plays(vBull.SOUND.BET_ON_DESK, undefined, 500, 270);
        }
    }
    cBull.othersWin=function(){
        var maxCnt = 0;
        var sit=vBull.sits[vBull.SIT_BANKER];
        for (var i=0;i<4;i++) {
            var bigger = isBigger(i);
            if(bigger){//闲家赢,收回该坑的所有金币
                maxCnt = Math.max(maxCnt, vBull.coinToSit(vBull.deskAreas[i], undefined));
            }
            if(!cBull.onlyBig && !bigger){
                maxCnt = Math.max(maxCnt, vBull.coinToSit(vBull.deskAreas[i+4], undefined));
            }
        }
        if(maxCnt == 0) cBull.othersFinish();//无动画可播，进入下一场动画
        else{
            A.setTimeOut(500+maxCnt*5, cBull.othersFinish);
            sBase.plays(vBull.SOUND.BET_ON_DESK, undefined, 500, 270);
        }
    }
    cBull.othersFinish=function(){
        for (var i=0;i<vBull.sits.length;i++) {
            if(i==vBull.SIT_BANKER || i==vBull.SIT_OTHERS)continue;
            vBull.sits[i].refleshMoney();
        }
        cBull.calcFinish();
    }
    cBull.calcFinish=function(){//结算动画全部完毕
        //玩家游戏中金币不足
        if (cBull.tipMsg.notBalance === 0) {
            vBull.notBalanceMsg(cBull.tipMsg.notBalance);
            cBull.tipMsg.notBalance = 1;
        }
        if (cBull.match.resultCouncil) {
            cBull.winLoseAnimation.showAnimation();
            cBull.match.resultCouncil = false;
        }
        // v.initGameStart();
    }
    cBull.bet = function(sitIdx, desk, count, money, otherIdx){
        if(cBull.state==1)return;
        if(!count)count=2;
        if(sitIdx == vBull.SIT_OTHERS && cBull.match.mySitIndex == -1){//其他人投注额要扣掉自己在前1秒中所投额度
            var tmpMoney = desk.clearMyTempMoney();
            money -= tmpMoney;
            if(money == 0)return;
        }
        var sit = vBull.sits[sitIdx];
        vBull.coinToDesk(sit, desk, sitIdx, count, money);
        if(sitIdx == vBull.SIT_MYSELF){
            sBase.play(vBull.SOUND.BET_ON_DESK_SELF,undefined, 500);
            if(cBull.match.mySitIndex==-1){//自己没有座位,投注后.广播消息会有包含自己的要扣除
                desk.addMyTempMoney(money);
            }
        }else{
            sit.addMoney(-money);
            if(otherIdx == 0){
                sBase.plays(vBull.SOUND.BET_ON_DESK,undefined, 500, 270);
            }
        }
    };
    //投注消息提示
    cBull.bettingTipMsg = function (tip) {
        vBull.warnTipMsg(tip.code,tip.tip);
    }
    //玩家余额不足时消息提示
    cBull.balanceDown = function (notBalance) {
        cBull.tipMsg.notBalance = notBalance.type;
    }
    var ran = function (start,end){return start+parseInt(Math.random()*(end+1-start));}
    cBull.testBet = function(){
        for (var i = 0; i < 15; i++) {
            setTimeout(function () {
                var time = new Date().getTime();
                for (var j = 0; j < 8; j++) {
                    cBull.bet(vBull.SIT_OTHERS,vBull.deskAreas[j], 2, ran(1,10)*100, j);
                }
                for (var i = vBull.SIT_START; i < 9; i++) {
                    // if(Math.random()<0.5)continue;
                    cBull.bet(i,vBull.deskAreas[parseInt(Math.random()*8)],2,ran(1,10)*100);
                }
                if((time = new Date().getTime()-time)>20)alert("金币投放时间过多(ms)："+time+" ,i:"+i);
            }, 1000*i);
        }
    }

    cBull.test=function(){
        switch(cBull.state){
            case 0:{
                vBull.dealt();
                break};
            case 1:{
                cBull.testBet();
                break};
            case 2:{
                vBull.turnPoker();
                break};
            case 3:{
                vBull.initGame();
                vBull.start();
                break};
        }
        cBull.state=(cBull.state+1)%4;
        // var testStr = ['开始游戏','其他人押注','结算'];
        // document.querySelector(".testBtn").innerText=testStr[cBull.state];
    }

    return cBull;
}catch(e){
    console.error(e);
}
});
/* 测试
requirejs(['bull100/bullMain'], function (bm) {
    bm.test();
});
 */
