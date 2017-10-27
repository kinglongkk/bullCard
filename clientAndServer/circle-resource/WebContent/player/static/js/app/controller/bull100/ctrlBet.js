define(["view/bull100/viewBull", "app/controller/gameMain"], function (vBull, gm) {
    var prefix = ["SPADE","HEART","CLUB","DIAMOND"];
    var suffix = ["W", "L"];
    var cBull = null;
    var cBet = {};
    cBet.getBetItem = function (deskArea) {
        return prefix[deskArea.index]+"_"+suffix[deskArea.isBig?0:1];
    };
    cBet.getDeskArea = function (betItem) {
        var items = betItem.split("_");
        return vBull.deskAreas[prefix.indexOf(items[0])+4*suffix.indexOf(items[1])];
    };
    cBet.init = function (_cBull) {
        cBull = _cBull;
        vBull.onclickDeskArea =function(e, deskArea) {
            if(cBull.match.mySitIndex == vBull.SIT_BANKER){
                return;//庄家不能投注
            }
            var money = vBull.getStakeMoney();
            if(money == -1) return;
            if(money>cBull.match.usableBalance)return;
            gm.sendToServer("NbBet",{
                matchId : cBull.match.matchId,
                gold : money,
                item : cBet.getBetItem(deskArea),
                type : "BULL_100"
            });
        };
    }
    return cBet;
});