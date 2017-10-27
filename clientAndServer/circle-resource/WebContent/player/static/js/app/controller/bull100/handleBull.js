define(["app/controller/netDefaultHandle", "view/bull100/viewBull", "bull100/ctrlBet"],
    function (handle, vBull, cBet) {
    var cBull = null;
    var hBull = {
        init:function (_cBull) {
            cBull = _cBull;
        }
    }
    handle.NbBetOut=function(nbBetOut){
        if (nbBetOut.tip.code != "0") {
            cBull.bettingTipMsg(nbBetOut.tip);
        }else {
            var nbBet = nbBetOut.bet;
            var deskArea = cBet.getDeskArea(nbBet.item);
            if (nbBet.gold > 0) {
                deskArea.addMoneySelf(nbBet.gold);
                cBull.bet(vBull.SIT_MYSELF,deskArea,2,nbBet.gold);
                cBull.setSelfMoney(nbBetOut.nbSelf);
            }
        }
    }
    handle.NbBetBatchOut=function(nbBetBatchOut){
        var bets = nbBetBatchOut.bets;
        // console.log("批量投注:"+new Date());
        for (var i = 0; i < bets.length; i++) {
            var nbBet = bets[i];
            var index = cBull.getSitIndex(nbBet.seatIndex);
            if(index != cBull.match.mySitIndex){
                cBull.bet(index,cBet.getDeskArea(nbBet.item),2,nbBet.gold, i);
            }
        }
    }
    handle.NbStartMatchOut=function(nbStartMatchOut){
        console.dir(nbStartMatchOut);
        cBull.initMatch(nbStartMatchOut.match, true);
        cBull.setSitPlayers(nbStartMatchOut.seats, nbStartMatchOut.match.dealer, true);
        if (nbStartMatchOut.seats.length != 0) {//庄家下庄后自己位置上金币更新
            var seats = nbStartMatchOut.seats;
            for (var player in seats) {
                if (seats[player].player.playerId === cBull.match.playerId) {
                    cBull.setSelfMoney({balance:seats[player].player.coin, usableBalance:seats[player].player.usableBalance});
                }
            }
        }
    }
    var prefix = ["SPADE","HEART","CLUB","DIAMOND"];
    handle.NbSettleMatchOut=function(nbSettleMatchOut){
        console.dir(nbSettleMatchOut);
        var pokers = nbSettleMatchOut.pokers;
        for (var i = 0; i < pokers.length; i++) {
            var poker = pokers[i];//第一个是庄家
            var index =prefix.indexOf(poker.group)+1;
            var bigger = (poker.result=="WIN");
            vBull.pokerGroups[i].setVal(poker.code.split(","),poker.type, bigger, poker.odds);//poker.rate
            if(i!=0)vBull.setDeskAreaWinLose(i-1, bigger);
        }
        var seats = nbSettleMatchOut.players;
        if(seats)for (var i = 0; i < seats.length; i++) {
            var seat = seats[i];
            var sit = vBull.sits[cBull.getSitIndex(seat.seatIndex)];
            sit.setResult(seat.coin, seat.balance);
        }
        vBull.turnPoker(cBull.bankerWin);
    }
    handle.NbSettlePlayerOut=function(nbSettlePlayer){
        console.dir(nbSettlePlayer);
        requirejs(["app/controller/PersonalInfo"], function (PersonalInfo) {
            new PersonalInfo().updatePersonInfoMoney(nbSettlePlayer.player.balance);
        });
        if (cBull){
            if(cBull.isBanker())return;
            vBull.sits[vBull.SIT_MYSELF].setResult(nbSettlePlayer.player.coin, nbSettlePlayer.player.balance);
            cBull.setSelfMoney({balance:nbSettlePlayer.player.balance,usableBalance:nbSettlePlayer.usableBalance}, true);
            if (nbSettlePlayer) {
                cBull.match.resultCouncil = true;
                cBull.windMoney(nbSettlePlayer);
            }
        }
    }
    handle.NbSeatOut=function(nbSeatOut){
        console.dir(nbSeatOut);
        if (cBull) {
            cBull.setSitPlayers(nbSeatOut.seats);
        }
    }
    handle.NbUpDealerOut=function(upDealerList){
        console.dir(upDealerList);
        if (cBull.tipMsg.keepDealer) {
            cBull.DealerMsg(upDealerList);
            cBull.setSelfMoney({balance:upDealerList.dealer.coin, usableBalance:upDealerList.dealer.usableBalance});
        } else {
            var code = upDealerList.tip.code;
            if (code === "00001" || code === "200002" || code === "200003" || code === "200004" || code === "600002" || code === "600013" || code === "600014") {
                cBull.DealerMsg(upDealerList);
            } else {
                cBull.openBankerDialog(upDealerList);
            }
        }
    }
    handle.NbDownDealerOut=function (downDealerList) {
        console.dir(downDealerList);
        cBull.DealerMsg(downDealerList);
        cBull.openBankerDialog(downDealerList);
    }
    handle.NbInsufficientBalanceOut=function (notBalance) {
        console.dir(notBalance);
        cBull.balanceDown(notBalance);
    }
    handle.NbWarningDealerOut = function (nbWarningDealerOut) {
        console.dir(nbWarningDealerOut);
        cBull.DealerMsg(nbWarningDealerOut);
    }
    return hBull;
})