define(['view/viewBase', "view/bull100/Animate", "app/controller/gameMain", "app/view/soundBase"], function (vBase, A, gm, sBase) {//"vBull100"
    var vBull = {
        onclickDeskArea: null,
        onclickStake: null,
        onclickDialogBanker: null,
    };
    var groupIndex = 0, baseDuration = 350, afterFun, onlyBig, maxCoinCnt, deskMaxCoinCnt, isBanker = false;
    var SOUND = {
        BUULS: new Array(),
        START_BET: new sBase.Sound(11, "Start_Bet"),
        DEALT: new sBase.Sound(12, "Deal_Card"),
        BET_ON_DESK: [new sBase.Sound(13, "Bet_On_Desk"), new sBase.Sound(14, "Bet_On_Desk")],
        BET_ON_DESK_SELF: new sBase.Sound(15, "Bet_On_Desk"),
    };
    vBull.SOUND = SOUND;
    for (var i = 0; i < 11; i++) {
        SOUND.BUULS.push(new sBase.Sound(i, "bull" + i));
    }
    //金币
    var Coin = vBase.Div.extend({
        sitIndex: -1,
        _toX: -1,
        _toY: -1,
        _toCoin: null,
        init: function (ele, sitIndex) {//每个金币都会记录由谁投注到池里面的
            this._super(ele, vBase.SET_WH_ALL);
            this.sitIndex = sitIndex;
        },
        setSitIndex: function (sitIndex) {
            this.sitIndex = sitIndex;
            return this;
        },
        flyTo: function (x, y) {
            this._toX = x;
            this._toY = y;
        },
        flyToCoin: function () {
            this._toX = this._toCoin._toX;
            this._toY = this._toCoin._toY;
            this._toCoin = null;
        }
    });
    //投注大小区域
    var DeskArea = vBase.Div.extend({
        _coinCnts: null,
        _replaceIndex: 0,
        _myTempmoney: 0,//自己没有座位后投注的金额, 每秒批量投注消息下发后清0
        addCoin: function (coin, money, zIndex) {
            var t = this, idx = coin.sitIndex, toCoin = null;
            if (t.coins.length >= deskMaxCoinCnt) {//超出投注区最大金币个数
                if (t._replaceIndex++ == t.coins.length)t._replaceIndex = 1;
                var diff = t._coinCnts[idx] - deskMaxCoinCnt / (idx == vBull.SIT_OTHERS ? 4 : 8);
                if (diff >= 0) {//自己已经超过平均值
                    var toCoin = t.coins[t._replaceIndex - 1];
                    if (toCoin.sitIndex == coin.sitIndex || diff >= t._coinCnts[toCoin.sitIndex] - deskMaxCoinCnt / (toCoin.sitIndex == vBull.SIT_OTHERS ? 4 : 8)) {
                        //自己的金币比要替代的那个多
                    } else {
                        t._coinCnts[toCoin.sitIndex]--;
                        t._coinCnts[idx]++;
                        toCoin.sitIndex = idx;
                    }
                } else {
                    var toIdx = -1, max = 0;
                    for (var i = 0; i < t._coinCnts.length; i++) {//找出那堆最多的金币
                        var added = t._coinCnts[i] - deskMaxCoinCnt / (i == vBull.SIT_OTHERS ? 4 : 8);
                        if (added > 0 && added > max) {
                            toIdx = i;
                            max = added;
                        }
                    }
                    if (toIdx == -1) {//全部刚好都满了,则从最底下的金币开始变更
                        toCoin = t[t._replaceIndex - 1];
                    } else for (var i = 0; i < t.coins.length; i++) {//取出最多的那一组
                        var toCoin = t.coins[t._replaceIndex - 1];
                        if (toCoin.sitIndex == toIdx) {
                            toCoin.sitIndex = idx;
                            t._coinCnts[toIdx]--;
                            t._coinCnts[idx]++;
                            break;
                        }
                    }
                }
                coin.sitIndex = -1;//表示在飞行结束时要被隐藏并移除到池里
            } else {//投注区域未满才能加进来
                t._coinCnts[idx]++;
                t.coins.push(coin);
            }
            coin._toCoin = toCoin;
            return function () {
                if (money != undefined) {
                    t.addMoney(money);
                }
                if (toCoin) toCoin.$ele.css("z-index", zIndex);
                if (coin.sitIndex == -1) {
                    vBull.coins.push(coin);
                    coin.hide();
                }
            }
        },
        init: function (ele, isBig, index) {//投注区域宽高都一样
            var t = this;
            t._super($(ele.find(".Gamble_jb"), 0), vBase.SET_XY_OFFSET | vBase.SET_WH_ALL);
            t.$backgroup = ele;
            t.moneyDiv = new vBase.MoneyDiv($(ele.find(".Gamble_zje")[0]), true);//该区总投注金额
            var $pers = ele.find(".Gamble_per");
            t.moneySelfDiv = new vBase.MoneyDiv($($pers[0]), true);//自己投注的金额
            t.$winLost = $($pers[1]);
            var children = t.$winLost.children();
            t.$rateDiv = $(children[0]);
            t.moneyWinDiv = new vBase.MoneyDiv($(children[1]), true);
            t.coins = new Array();
            t.isBig = isBig;
            t.isWin = false;
            t.index = index;
            t.$backgroup.click(t, function (e) {
                if (vBull.countDown.isBetting() && vBull.countDown.isPlayerBetting() && vBull.onclickDeskArea != undefined)
                    vBull.onclickDeskArea(e, e.data);
            });
            t._coinCnts = new Array(9);
            t.reset();
        },
        reset: function () {
            var t = this;
            t.money = 0;
            t.moneySelf = 0;
            for (var i = 0; i < t._coinCnts.length; i++) {
                t._coinCnts[i] = 0;
            }
        },
        addMoney: function (money) {
            this.money += money;
            this.moneyDiv.setMoney(this.money);
        },
        addMoneySelf: function (money) {
            this.moneySelf += money;
            this.moneySelfDiv.setMoney(this.moneySelf);
        },
        addMyTempMoney: function (money) {
            this._myTempmoney += money;
        },
        clearMyTempMoney : function () {
            var tmp = this._myTempmoney;
            this._myTempmoney = 0;
            return tmp;
        },
        showResult: function (rate) {
            this.moneySelfDiv.hide();
            var money = this.moneySelf;
            this.moneySelf = 0;
            if (this.isWin)this.$ele.attr("class", this.isBig ? "Gamble_jb Bull_flow_big_yellow" : "Gamble_jb Bull_flow_samll_yellow");
            if (money == 0)return;
            this.$rateDiv.text("x" + rate);
            money = (this.isWin ? 1 : -1) * rate * money;
            this.moneyWinDiv.setMoney(money);
            this.$winLost.attr("class", "Gamble_per" + (money ? "" : " Gamble_loser"));
            this.$winLost.show();
        }
    });
    DeskArea.hide = function () {
        $(".Gamble_bg .Gamble_zje,.Gamble_bg .Gamble_per").hide();
        $('.Gamble_jb.Bull_flow_big_yellow').attr("class", "Gamble_jb Bull_flow_big");
        $('.Gamble_jb.Bull_flow_samll_yellow').attr("class", "Gamble_jb Bull_flow_small");
        for (var i = 0; i < vBull.deskAreas.length; i++) {
            vBull.deskAreas[i].reset();
        }
    }
    var getDeskArea = function (index, isSmall) {
        return vBull.deskAreas[onlyBig ? index : (isSmall ? 4 + index : index)];
    }
    DeskArea.showResult = function (index, rate) { //显示背景输赢，赢的话大小变色
        getDeskArea(index).showResult(rate);
        if (!onlyBig)getDeskArea(index, true).showResult(rate);
    }
    vBull.setDeskAreaWinLose = function (index, bigger) {//index为0-3 投注区的索引值，该区域是否比庄家大
        getDeskArea(index).isWin = bigger;
        if (!onlyBig)getDeskArea(index, true).isWin = !bigger;
    }
    //座位
    var Sit = vBase.Div.extend({
        _index: 0,
        init: function (ele, index) {//座位宽高都一样,除了其他人
            var t = this;
            t._index = index;
            var isOthers = (index == vBull.SIT_OTHERS);
            t._super($(ele.find(isOthers ? ".onlookers_jb" : "img")[0]), vBase.SET_XY_OFFSET | (isOthers ? vBase.SET_WH : vBase.SET_WH_ALL));
            if (!isOthers) {
                var moneyClassName = ".ui-destop-user-gold-num";
                if(index == vBull.SIT_MYSELF){
                    moneyClassName  = ".bull_user_nub1";
                    t.$nickname = ele.find(".bull_user_name2");
                }else if(index == vBull.SIT_BANKER){
                    moneyClassName  = ".bull_user_nub";
                    t.$nickname = ele.find(".bull_user_name1");
                }else{
                    t.$nickname = ele.find(".bull_user_name");
                }
                t.moneyDiv = new vBase.MoneyDiv($(ele.find(moneyClassName)[0]));
                t.moneyDiffDiv = new vBase.Div(ele.find(".bull_money_diff"));
                var $noUser = ele.find(".no_user");
                if ($noUser.length > 0) {
                    t.$noUser = $noUser;
                    t.$haveUser = ele.find(".ui-destop-user-b");
                }
                t._alignClass = (index<vBull.SIT_START ? (index==0) : (index%2==1)) ? "bull_nub_left" : "bull_nub_right";
            }
        },
        setVal: function (icon, money, playerId, nickname) {//设置头像及金额
            this.setMoney(money);
            this._playerId = playerId;
            if (this.$noUser) {
                if (icon)this.$noUser.hide(), this.$haveUser.show();
                else this.$noUser.show(), this.$haveUser.hide();
            }
            this.$ele.attr('src', icon);
            if (!(nickname)) {
                this.$nickname.text("");
                return;
            }
            if (nickname.length > 8) {
                this.nickname = nickname.substring(0,6)+"**";
            }else {
                this.nickname = nickname;
            }
            this.$nickname.text(this.nickname);
        },
        setResult: function (diffMoney, balance) {
            this._rsDiff = diffMoney;
            this._balance = balance;
        },
        setMoney: function (money) {//设置金额
            if (this._money == money)return false;
            this._money = money;
            this.moneyDiv.setMoney(money);
            return true;
        },
        addMoney: function (money) {//加减金额
            if(this._index == vBull.SIT_OTHERS)return;
            if (money == 0)return false;
            this._money += money;
            this.moneyDiv.setMoney(this._money);
            return true;
        },
        refleshMoney: function () {
            var t = this;
            if (t._balance == undefined || t._balance == null || t._rsDiff==0) {
                return;
            }
            var complete = function () {
                if(t._balance==null) return;
                t.setMoney(t._balance);
                t._balance = null;
            }
            if(t._rsDiff>0){
                t.moneyDiffDiv.$ele.text("+"+t._rsDiff);
                t.moneyDiffDiv.setClass("bull_money_diff bull_add_money "+t._alignClass);
                A.animate(t.moneyDiffDiv, {"margin-bottom":"1rem", opacity : 0}, {duration: 0, display: 'none'});
                A.animate(t.moneyDiffDiv, {"margin-bottom":"1.3rem", opacity : 1, scaleX:1.3,scaleY:1.3}, {duration: 600, display: 'block'});
                A.animate(t.moneyDiffDiv, {"margin-bottom":"0rem", opacity : 0, scaleX:0.8,scaleY:0.8},
                    {delay: 1600, duration: 600, display: 'none', complete:complete});
            }else{
                t.moneyDiffDiv.$ele.text(t._rsDiff);
                t.moneyDiffDiv.setClass("bull_money_diff "+t._alignClass);
                A.animate(t.moneyDiffDiv, {"margin-bottom":"0rem",scaleX:0.8,scaleY:0.8, opacity : 0}, {duration: 0, display: 'none'});
                A.animate(t.moneyDiffDiv, {"margin-bottom":"1.3rem", opacity : 1, scaleX:1.3,scaleY:1.3}, {duration: 600, display: 'block'});
                A.animate(t.moneyDiffDiv, {opacity : 0, scaleX:0.8,scaleY:0.8}, {delay: 1600, duration: 600, display: 'none', complete:complete});
            }
        },
        hide: function () {
            if (this.$noUser) {
                this.$noUser.show(), this.$haveUser.hide();
            }
        }
    });
    //扑克牌
    var Poker = vBase.Div.extend({
        init: function (ele) {
            var t = this;
            t._super(ele, vBase.SET_WH_ALL);
            t.x = this.$ele.css("margin-left");
            t.y = 0;
            t.$front = $(ele.find(".cmm_card2")[0]);//牌面
            t.$num = $(t.$front.find(".card_left_nub")[0]);
            t.$flowerLeft = $(t.$front.find(".card_flow")[0]);
            t.$flowerMain = $(t.$front.find(".card_auto")[0]);
        },
        showBack: function () {
            this.$front.hide();
        },
        showFront: function () {
            this.$front.show();
        },
        setVal: function (num, color, show) {//设置扑克的值0-12,花色0-3,是否马上显示
            this.num = num;
            color = 3 - color;
            this.color = 3 - color;
            this.$num.attr("class", "card_left_nub " + ((color % 2 == 0) ? "" : "r") + "nub" + num);
            this.$flowerLeft.attr("class", "card_flow flow" + color);
            this.$flowerMain.attr("class", "card_auto auto" + (num > 10 ? 4 + (num % 10) : color));
            if (show)this.showFront();
        }
    });
    Poker.hide = function () {
        $(".cmm_card").hide();
    };
    Poker.show = function () {
        $(".cmm_card").show();
    };
    //牛几显示
    var PokerBull = vBase.Div.extend({
        _bullVal: 0,
        init: function (ele) {
            var t = this;
            t._super(ele, vBase.SET_XY);
            t.$bull = $(ele.children()[0]);
            t.bull = new vBase.Div(t.$bull);
        },
        setVal: function (num) {//设置牛几 0-10//没牛到牛牛
            this._bullVal = num;
            this.$ele.attr("class", "cow cow" + num);
        }
    });
    //扑克牌5张为1组
    var PokerGroup = Class.extend({
        init: function (pokers, pokerBull) { //扑克组,pokers5张扑克数组,牛几对象
            this.pokers = pokers, this.pokerBull = pokerBull;
        },
        setVal: function (pokerVals, bullVal, isBigger, rate) { //扑克组,pokers5张扑克值数组,牛几值
            for (var i in this.pokers) {
                var val = parseInt(pokerVals[i]), num = val >> 2, color = val & 3;
                this.pokers[i].setVal(num, color);
            }
            this.pokerBull.setVal(parseInt(bullVal));
            this._bigger = isBigger;
            this.rate = rate;
        },
        getBullVal: function () {
            return this.pokerBull._bullVal;
        },
        isBigger: function () {
            return this._bigger;
        }
    });
    PokerGroup.hide = function () {
        Poker.hide();
        $(".cow").hide();
    };
    vBull.showPokerGroup = function () {
        Poker.show();
    }
    var ButtonGroup = function (onChange, $selectedAnimation) {
        var t = this;
        t.selectedIndex = 0;
        t.onChange = onChange;
        t.$selectedAnimation = $selectedAnimation;
        t.setButtons = function (buttons) {
            t.buttons = buttons;
            for (var i = 0; i < buttons.length; i++) {
                var btn = buttons[i];
                btn._index = i;
                btn.$ele.click(btn, btnClick);
                if(i==0)btn._selected = true;
            }
        }
        t.updateStakeMoney = function (usableBalance) {
            var seted = false;//是否已经激活一个按钮了
            if (this.selectedIndex == -1) {//未选中任意按钮
                for (var i = 0; i < vBull.betChip.length; i++) {
                    var btn = this.buttons[i];
                    if (usableBalance >= vBull.betChip[i]) {
                        btn._enable = true;
                        if (!seted) {
                            seted = true;
                            btn._selected = true;
                            this.selectedIndex = i;
                        }
                    } else {
                        break;
                    }
                    btn.updateClass();
                }
            } else {
                for (var i = vBull.betChip.length - 1; i >= 0; i--) {
                    if (usableBalance >= vBull.betChip[i]) {
                        if (this.selectedIndex == i) {
                            break;
                        }
                        var btn = this.buttons[i];
                        if (!seted && (this.selectedIndex == -1 || i <= this.selectedIndex)) {
                            seted = true;
                            this.selectedIndex = i;
                            btn.setAll(true, true);
                        } else {
                            btn.setAll(true, false);
                        }
                    } else {
                        if (this.selectedIndex == i)this.selectedIndex = -1;//之前选中的钱比余额多
                        this.buttons[i].setAll(false, false);
                        if(i == 0){
                            //已经是最后一个按钮了
                            this.selectedIndex = -1;
                        }
                    }
                }
            }
            if(this.selectedIndex == -1){
                this.$selectedAnimation.hide();
            }else{
                this.$selectedAnimation.show();
            }
            this._usableBalance = usableBalance;
        }
        var btnClick = function (e) {
            var btn = e.data;
            if (!btn._enable || t.selectedIndex == btn._index)return;
            else {
                t.buttons[t.selectedIndex].setSelected(false);
                t.buttons[btn._index].setSelected(true);
                t.selectedIndex = btn._index;
                if (t.onChange)t.onChange(t.selectedIndex);
            }
        };
        return t;
    };
    //下注额度
    var StakeButton = vBase.Div.extend({
        _index: 0,
        _enable: true,
        _selected: false,
        _buttonGroup: null,
        init: function (ele, buttonGroup, stakeNum) {
            var t = this;
            t._super(ele);
            t._buttonGroup = buttonGroup;
            t.moneyDiv = new vBase.MoneyDiv(ele.children());
            t.moneyDiv.setMoney(stakeNum);
        },
        updateClass: function () {
            if(this._selected)this._buttonGroup.$selectedAnimation.css("left", this.$ele.position().left);
            this.$ele.attr("class", this._selected ? "Be_bg" : (this._enable ? "Be_bg1" : "Be_bg1 tz_tmd"));
        },
        setAll: function (enable, selected) {
            if (this._enable == enable && this._selected == selected)return;
            this._enable = enable;
            this._selected = selected;
            this.updateClass();
        },
        setEnable: function (enable) {
            this._enable = enable;
            this.updateClass();
        },
        setSelected: function (selected) {
            this._selected = selected;
            this.updateClass();
        }
    });
    //获取当前选中的的投注额
    vBull.getStakeMoney = function () {
        if(vBull.buttonGroup.selectedIndex == -1) return -1;
        return vBull.betChip[vBull.buttonGroup.selectedIndex];
    }
    //倒计时
    var CountDown = vBase.Div.extend({
        init: function (ele) {
            var t = this;
            t._super(ele);

        },
        _betting: false,//是否正在投注中
        _playerBetting: false,
        _timeoutId: null,
        _beginTimeNext: 0,
        _settleTime:0,
        _count: 0,
        _setText: function () {//设置倒计时文字
            this.$ele.text((this._betting ? (isBanker?"请等待玩家投注" : "请选择幸运区域") : "辛苦啦,请休息一下吧") + "...(" + this._count + ")");
        },
        _showWait: function () {//显示倒计时
            var t = this;
            t._count--;
            if (t._count > 0) {
                t._setText();
                t._timeoutId = vBase.setTimeout(function () {
                    t._showWait();
                }, 1000);
            } else {
                t.$ele.hide();
                if (t._betting) {
                    t._betting = false;
                    t.showWaitBegin();//TODO 结算开始做好要去掉这行
                }
                t._timeout = null;
            }
        },
        _calcWaitShow: function (time, now) {
            if (!now)now = gm.getServerTime();
            var nextTime = (time - now) % 1000;
            var t = this;
            t._count = parseInt((time - now) / 1000) + 1;
            // console.log((this._betting ? "投注中：" : "休息中：") + time + "," + now + "," + this._beginTimeNext);
            t._setText();
            this._timeoutId = vBase.setTimeout(function () {
                t._showWait();
            }, nextTime);
        },
        showWaitBegin: function () {
            if (this._timeoutId)vBase.clearTimeout(this._timeoutId);
            this.$ele.show();
            this._calcWaitShow(this._beginTimeNext);
        },
        start: function () {
            var t = this;
            if (t._timeoutId)vBase.clearTimeout(t._timeoutId);
            t.$ele.show();
            var now = gm.getServerTime();
            t._beginTimeNext = t._beginTimeNext;
            t._betting = t._settleTime - now > 1000;//超过一秒才倒计时投注
            if (t._betting) {
                t._calcWaitShow(t._settleTime, now);
            } else t._calcWaitShow(t._beginTimeNext, now);
        },
        setVal: function (beginTimeNext, settleTime) {
            this._beginTimeNext = beginTimeNext;
            this._settleTime = settleTime;
        },
        isBetting: function () {
            return this._betting;
        },
        isPlayerBetting: function () {
            return this._playerBetting;
        },
        clear: function () {
            if (this._timeoutId)vBase.clearTimeout(this._timeoutId);
        },
        hide: function () {
            this.$ele.hide();
            if (this._timeoutId)vBase.clearTimeout(this._timeoutId);
        }

    });
    //投注消息提示
    vBull.warnTipMsg=function (bettingMark,msg) {
        !vBase.tip.isShowing() ? vBase.tip.show(msg, "") : "";
        console.log(bettingMark + msg);
    }
    vBull.notBalanceMsg = function (bettingMark) {
        if (bettingMark == "0") {
            vBase.msgDialog.show("您金币不足，无法继续游戏！", "确定", null, function () {
                gm.sendToServer("NbExitRoomIn", {
                    gameModel: $('#room_model').text(),
                    roomId: $("#room_nub").text(),
                    isBreak: true
                });
            });
        }
    }

    vBull.init = function (cfg) {
        //定义押注的金额
        vBull.betChip = cfg.match.betChip;
        sBase.initSounds(SOUND);
        onlyBig = cfg.onlyBig;
        maxCoinCnt = cfg.maxCoinCount;
        deskMaxCoinCnt = parseInt(maxCoinCnt / (onlyBig ? 4 : 8) / 2);
        $('.rule_left').click(function () {
            $('.rule_right').toggleClass('rule_right1');
            $('.rule_left').toggleClass('rule_left1');
        })
        // $('.ui-destop-stake').click(function () {
        $('.Betting').toggleClass('show');
        // })
        document.querySelector(".ui-destop-user-photo").onclick = function (e) {
            sBase.init(resRoot + "/sounds/", "ingameBGMMono");
        };
        //初始化视图的所有控件
        vBull.pokerGroups = function () {//扑克牌非固定位置
            var pokerGroup = null, poker = null, pokers, groups = new Array();
            var bulls = $(".cow");
            var $pokers = $(".cmm_card");
            $pokers.each(function (i, ele) {
                if (i % 5 == 0) {
                    pokers = new Array();
                    pokerGroup = new PokerGroup(pokers, new PokerBull($(bulls[parseInt(i / 5)])));
                    groups.push(pokerGroup);
                }
                var poker = new Poker($(ele));
                pokers.push(poker);
            });
            groups.hide = function () {
                $(".cow").hide();
                $(".cmm_card").hide();
            }
            return groups;
        }();
        vBull.coins = function () {//金币列表
            var coins = new Array();
            var $coins = $(".coin");
            for (var i = 0; i < $coins.length; i++) {
                coins.push(new Coin($coins[i]));
            }
            return coins;
        }();
        vBull.deskAreas = function () {
            var areas = new Array();
            var $areas = $(".Gamble_bg");
            var $area = null;
            for (var i = 0; i < $areas.length; i++) {
                $area = $($areas[i]);
                var isBig = cfg.onlyBig || i % 2 == 0;
                var index = cfg.onlyBig ? i : parseInt(i / 2)
                var area = new DeskArea($area, isBig, index);
                areas[index + (isBig ? 0 : 4)] = area;
            }
            return areas;
        }();
        vBull.buttonGroup = new ButtonGroup(null, $(".Betting_btn_animation"));
        var stakeButtons = function () {
            var stakeButtons = new Array();
            var $stakeButtons = $("ul.Betting_ul").children();
            var $selectedAnimation = $(".Betting_btn_animation");
            for (var i = 0; i < $stakeButtons.length; i++) {
                stakeButtons.push(new StakeButton($($stakeButtons[i]),vBull.buttonGroup, vBull.betChip[i]));
            }
            return stakeButtons;
        }();
        vBull.buttonGroup.setButtons(stakeButtons);
        DeskArea.coinGap = (function () {//计算每个金币的最小间隙
            var w = parseInt((DeskArea.getWidth() - Coin.getWidth()) / 8);
            var h = parseInt((DeskArea.getHeight() - Coin.getHeight()) / 8);
            return new vBase.Pos(w, h);
        })();
        vBull.SIT_MYSELF = 0, vBull.SIT_BANKER = 1, vBull.SIT_OTHERS = 2, vBull.SIT_START = 3;
        vBull.sits = function () {
            var sits = new Array();
            sits.push(new Sit($($(".user-border-f")[0]), vBull.SIT_MYSELF));//自己
            sits.push(new Sit($($(".user-border-a")[0]), vBull.SIT_BANKER));//庄家
            sits.push(new Sit($($(".ui-destop-onlookers")[0]), vBull.SIT_OTHERS));//其他
            var tmp = $(".user-border-lr");
            for (var i = 0; i < tmp.length; i++) {
                sits.push(new Sit($(tmp[i], vBull.SIT_START+i)));
            }
            return sits;
        }();
        //动画提示相关
        var betDiv = new vBase.Div($("#animationBet"));
        vBull.animationBet = new vBase.AnimationTip(betDiv, 3000, function () {
            vBull.dealt();
        });
        //玩家上庄
        vBull.$dialogBanker = $('.ui_btn_boss');
        vBull.$bankerIcon = $('.board_jb');
        vBull.$bankerBar = $('.bull_xz');
        vBull.$bankerAddMoney = vBull.$bankerBar.find('.bull_xz_button');
        vBull.$bankerDown = vBull.$bankerBar.find('.bull_xzz_button');
        vBull.$bankerInfo = $('.bull_user_info');
        vBull.$playerBar = $('.Betting');
        //设置庄家续庄和下庄按钮是否可用
        vBull.setBankerEnable = function (enable) {
            var opacity = enable ? 1 : 0.3;//通过透明度
            vBull.$bankerDown.css("opacity", opacity);
            vBull.$bankerAddMoney.css("opacity", opacity);
        }
        vBull.showBankerBar = function(isBanker){
            if(isBanker){
                vBull.$dialogBanker.hide();
                vBull.$bankerIcon.show();
                vBull.$bankerBar.show();
                vBull.$bankerInfo.attr("class", "bull_user_info bull_info_1");
                vBull.$playerBar.hide();
            }else{
                vBull.$dialogBanker.show();
                vBull.$bankerIcon.hide();
                vBull.$bankerBar.hide();
                vBull.$bankerInfo.attr("class", "bull_user_info");
                vBull.$playerBar.show();
            }
        }
        Sit.hide = function () {
            for (var i = vBull.SIT_START; i < vBull.sits.length; i++) {
                vBull.sits[i].hide();
            }
        }
        vBull.countDown = new CountDown($(".Prompt"));
        Sit.hide();
        vBull.initGame();
        // sBase.playBackgroup("ingameBGMMono");
    }
    vBull.initGame = function () {//isNewGame是否新开局
        //隐藏扑克
        vBull.clear();
        Poker.hide();
        PokerGroup.hide();
        DeskArea.hide();
    }
    vBull.gameStart = function (banker, newGame) {//是否是庄家
        isBanker = banker;
        if(newGame){
            if(isBanker){
                vBull.dealt();//庄家直接开牌
            }else{
                vBull.countDown._playerBetting = false;
                sBase.play(SOUND.START_BET);
                vBull.animationBet.showAnimation();
            }
        }
        vBull.countDown.start();
    }
    vBull.dealt = function (afterFun1) {
        groupIndex = 0;
        zDealt1();
        afterFun = afterFun1;
        vBull.countDown._playerBetting = true;
    };
    var zDealt1 = function () {//具体发牌,每调用一次发出一组5张牌
        if (groupIndex == 5) {
            console.error("zDealt1 groupIndex==5");
            return;
        }
        sBase.play(SOUND.DEALT);
        var group = vBull.pokerGroups[groupIndex], ah = Poker.getHeight(),
            poker = group.pokers[0],
            left = poker.x, top = (2.5 * ah) + "px";
        for (i = 0; i < 5; i++) {
            var p = group.pokers[i];
            A.animate(p, {"margin-left": left, "margin-top": top}, {duration:0});
            p.showBack();
        }
        groupIndex++;
        for (var i = 0; i < 5; i++) {
            var poker = group.pokers[i];
            var completeFun = (i == 4 ? (groupIndex >= 5 ? afterFun : zDealt1) : undefined);
            A.animate(poker, {"margin-top": 0, "margin-left": [poker.x, "easeInCubic"]}, {
                display: "block", duration: 300, easing: "linner",
                complete: completeFun
            }, false, {"margin-top": 0, "margin-left": poker.x});
        }
    }
    vBull.turnPoker = function (afterFun1) {
        groupIndex = 0;
        zTurnPoker1();
        afterFun = afterFun1;
        vBull.countDown._playerBetting = false;
    }
    var zTurnPoker1 = function () {
        if (groupIndex == 5) {
            console.error("zTurnPoker1 groupIndex==5");
            return;
        }
        var group = vBull.pokerGroups[groupIndex];
        for (var i = 0; i < 5; i++) {
            A.animate(group.pokers[i], {rotateY: 90}, {
                duration: baseDuration, easing: "linner",
                complete: i == 4 ? zTurnPoker2 : undefined
            });
        }
    };
    var zTurnPoker2 = function () {
        if (groupIndex == 5) {
            console.error("zTurnPoker1 groupIndex==5");
            return;
        }
        var group = vBull.pokerGroups[groupIndex];
        sBase.play(SOUND.BUULS[group.getBullVal()]);
        for (var i = 0; i < 5; i++) {
            var p = group.pokers[i];
            p.showFront();
            var completeFun = i == 4 ? zShowPokerBull : undefined;
            A.animate(p, {rotateY: 0}, {duration: baseDuration, easing: "linner", complete: completeFun});
        }
    };
    var zShowPokerBull = function () {
        if (groupIndex == 5) {
            console.error("zShowPokerBull groupIndex==5");
            return;
        }
        var group = vBull.pokerGroups[groupIndex];
        var b = group.pokerBull;
        b.$bull.hide();
        if (groupIndex != 0)DeskArea.showResult(groupIndex - 1, Math.max(group.rate, vBull.pokerGroups[0].rate));
        b.ele().show();
        A.animate(b.bull, {scaleX: 0, scaleY: 0}, {duration: 0});
        var completeFun = groupIndex == 4 ? afterFun : zTurnPoker1;
        A.animate(b.bull, {scaleX: 1, scaleY: 1}, {
            display: 'block',
            duration: baseDuration,
            easing: "easeOutBack",
            complete: completeFun
        });
        groupIndex++;
    };
    vBull.coinToDesk = function (sit, deskArea, sitIdx, count, money) {
        var toSound = false;
        var soundFun = null;
        for (var i = 0; i < count; i++) {
            var c1 = vBull.coins.pop().setSitIndex(sitIdx);
            if (c1 == undefined) {
                return;
            }
            var completeFun = deskArea.addCoin(c1, money != undefined && i == 0 ? money : undefined, A.coinZIndex + 1);
            A.toDesk(sit, deskArea, c1, DeskArea.coinGap, i * 50, completeFun);
        }
    }
    vBull.bankerToDesk = function (sit, deskArea, nextFun) {
        var coinCnt = deskArea.coins.length;
        for (var i = 0; i < coinCnt; i++) {
            var coin = vBull.coins.pop().setSitIndex(deskArea.coins[i].sitIndex);
            A.toDesk(sit, deskArea, coin, undefined, (i % 10) * 50, nextFun);
            deskArea.coins.push(coin);
        }
        return Math.min(coinCnt, 10);
    }
    vBull.coinToSit = function (deskArea, sit, nextFun) {
        var coins = deskArea.coins;
        var coinCnt = coins.length;
        for (var k = coinCnt - 1; k >= 0; k--) {
            var coin = coins.pop();
            var toSit = sit ? sit : vBull.sits[coin.sitIndex];
            vBull.coins.push(coin);
            A.toSit(toSit, coin, (k % 10) * 50, nextFun);
        }
        return Math.min(coinCnt, 10);
    }
    vBull.stakeMoneys = function () {
        return vBull.betChip;
    }
    vBull.clear = function () {
        vBull.countDown.clear();
        A.clear();
        //金币隐藏
        for (var i = 0; i < vBull.deskAreas.length; i++) {
            var deskArea = vBull.deskAreas[i];
            for (var j = 0; j < deskArea.coins.length; j++) {
                deskArea.coins[j].hide();
            }
        }
        vBase.Dialog.hide();
    }
    gm.clearGame = vBull.clear;
    return vBull;
});
/* 测试发牌,翻牌,显示牛几
 requirejs(["view/bull100/initView"], function (v) {v.pokerGroups.hide();
 v.dealt(v.turnPoker);
 });
 */
