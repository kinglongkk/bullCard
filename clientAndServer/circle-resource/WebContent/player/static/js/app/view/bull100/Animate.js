define(['Velocity', 'view/viewBase'], function (Velocity, vBase) {
    var A = {coinZIndex: 1000};
    A.reset = function () {
        A.coinZIndex = 1000;
    };
    var mapVelo = {};
    var timers = [];
    var running = true;
    A.finish = function () {
        for(var name in mapVelo){
            Velocity(mapVelo[name], "finish");
        }
        mapVelo = {};
    }
    A.clear = function (stop) {
        for(var i in timers){
            vBase.clearTimeout(timers[i]);
        }
        timers = [];
        A.finish();
    }
    vBase.setAnimation(A);
    A.setTimeOut = function(delay, fun, div, css){
        var timer = vBase.setTimeout(function () {
            if(css)div.$ele.css(css);
            if(fun)fun();
            for (var i = 0; i < timers.length; i++) {
                if(timers[i] == timer){
                    timers.splice(i, 1);
                }
            }
        }, delay);
        timers.push(timer);
    }
    /** 如果styles有值并且不可见时则会不使用move的值, 如果move有+=的值或者其他css样式不支持的值则需要styles来设置*/
    A.animate = function (div, move, option, reverse, styles) {
        var ele = div.ele()[0];
        var fun = option.complete;
        if(fun) option.complete = null;
        var showing = vBase.showing;
        if(!showing){
            if(styles){
                move = styles;
            }else{
                if(move.rotateY!=undefined)delete move.rotateY;
                if(move.rotateX!=undefined)delete move.rotateX;
                if(move.scaleY!=undefined)delete move.scaleY;
                if(move.scaleX!=undefined)delete move.scaleX;
            }
            if(option.display){
                move["display"] = option.display;
            }
            try{
                div.ele().css(move);
            }catch (e){
                console.dir(move);
            }
        }else{
            Velocity(ele, move, option);
            if (reverse)Velocity(ele, "reverse");
            mapVelo[div.getAnimateId(true)] = ele;
        }
        var delayTime = (option.delay==undefined?0:option.delay)+(option.duration==undefined?0:option.duration);
        var timer = vBase.setTimeout(function () {
            if(showing && div.animateFinish()){
                delete mapVelo[div.getAnimateId()];
            }
            for (var i = 0; i < timers.length; i++) {
                if(timers[i] == timer){
                    timers.splice(i, 1);
                }
            }
            if(fun)fun();
        }, delayTime);
        timers.push(timer);
    }
    A.toDesk = function (sit, desk, coin, coinGap, delay, completeFun) {
        var coinEle = coin.ele();
        coinEle.css("z-index", A.coinZIndex++);
        if (coin._toCoin != null) {
            coin.flyToCoin();
        } else {
            var deskW = desk.getWidth() - coin.getWidth(), deskH = desk.getHeight() - coin.getHeight();
            var toX = desk.getX() + Math.random() * deskW, toY = desk.getY() + Math.random() * deskH;
            if (coinGap) {
                var coinCount = desk.coins.length;
                fori:for (var i = 0; i < 100; i++) {//最多100次
                    for (var j = 0; j < coinCount; j++) {
                        var c = desk.coins[j];
                        if (Math.abs(c._toX - toX) < coinGap.x && Math.abs(c._toY - toY) < coinGap.y) {
                            toX = desk.getX() + Math.random() * deskW, toY = desk.getY() + Math.random() * deskH;
                            continue fori;//有重叠
                        }
                    }
                    break;//都没有重叠
                }
            }
            coin.flyTo(toX, toY);
        }
        A.animate(coin, {left: (sit.getX() - coin.getWidth() / 2 + sit.getWidth() / 2) + "px",
            top: (sit.getY() - coin.getHeight() / 2 + coin.getHeight() / 2) + "px"}, {duration:0});
        A.animate(coin, {left: coin._toX, top: coin._toY}, {
            display: "block",
            duration: 500,
            complete: completeFun,
            delay : delay
        });
    }
    A.toSit = function (sit, coin, delay, completeFun) {
        var toX = sit.getX() - coin.getWidth() / 2 + sit.getWidth() / 2,
            toY = sit.getY() - coin.getHeight() / 2 + coin.getHeight() / 2;
        if (vBase.showing) {
            A.animate(coin, {left: toX, top: toY}, {display: "none", duration: 500,delay:delay, complete: completeFun});
        }else{
            coin.ele()[0].style.display = "none";
        }
    }
    A.moneyReflesh = function (moneyDiv) {
        if (vBase.showing) {
            A.animate(moneyDiv, {scaleX: 2, scaleY: 2}, {easing: "easeOutCubic", duration: 400}, true);
        }
    }
    return A;
})