//MK. 游戏的所有控件都在这边定义.所有类带_代表私有变量或者私有方法
define(["site/common/MoneyFormat", "Velocity"], function (MoneyFormat, Velocity) {
    var vBase = new Object();
    var timerBlob = new Blob(["self.addEventListener('message',function(e){var d=e.data;if(d[1] && d[1]>1)setInterval(function(){self.postMessage({id:d[3],data:d[2]});},d[0]);else setTimeout(function(){self.postMessage({id:d[3],data:d[2]});},d[0]);}, false);"]);
    var timerUrl = window.URL.createObjectURL(timerBlob);
    var timerWorker = new Worker(timerUrl);
    var timerCount = 1;
    var timerIntervals ={};
    vBase.setTimeout = function (fun, delay, data) {
        var id = timerCount++;
        var listener = function(e) {
            if(e.data.id != id)return;
            fun(e.data.data);
            timerWorker.removeEventListener('message', listener);
            delete timerIntervals[id];
        }
        timerWorker.addEventListener('message', listener);
        timerIntervals[id]=listener;
        timerWorker.postMessage([delay, 1, data, id]);
        return id;
    }
    vBase.clearTimeout = function (id) {
        timerWorker.removeEventListener('message', timerIntervals[id]);
        delete timerIntervals[id];
    }
    vBase.setInterval = function (fun, delay, data) {
        var id = timerCount++;
        var listener = function(e) {
            if(e.data.id != id)return;
            fun(e.data.data);
        }
        timerWorker.addEventListener('message', listener);
        timerIntervals[id]=listener;
        timerWorker.postMessage([delay, 2, data, id]);
        return id;
    }
    vBase.clearInterval = function (id) {
        timerWorker.removeEventListener('message', timerIntervals[id]);
        delete timerIntervals[id];
    }

    var A = null;
    vBase.setAnimation = function(Animate){
        A = Animate;
    }
    vBase.Pos = function (x, y) {
        this.x = x;
        this.y = y;
    }//位置,坐标,也可以用来指宽高
    vBase.SET_WH = 1;//独立设值对象的WH值,SET_WH和SET_WH_ALL只能取其1,SET_WH_ALL优先
    vBase.SET_WH_ALL = 2;//设值所有该对象的WH值为同一个
    vBase.SET_XY = 4;//设值对象的相对XY值, SET_XY和SET_XY_OFFSET只能取其1,SET_XY优先
    vBase.SET_XY_OFFSET = 8;//设值对象的绝对XY值
    var setWH = function (o, ele) {//设置一个对象的wh属性为ele的宽高
        o._w = ele[0].offsetWidth;
        if (o._w == 0)o._w = ele.width();
        o._h = ele[0].offsetHeight;
        if (o._h == 0)o._h = ele.height();
    }
    var setXY = function (o, ele) {//设置一个对象的相对上层DIV xy属性为ele的XY
        o._x = parseInt(ele.position().left);//offset().left;
        o._y = parseInt(ele.position().top);
    }
    var setOffsetXY = function (o, ele) {//设置一个对象的相对body的xy属性为ele的XY
        o._x = parseInt(ele.offset().left);//;
        o._y = parseInt(ele.offset().top);
    }
    var getWidth = function () {
        return this._w;
    }
    var getHeight = function () {
        return this._h;
    }
    var idCreater = 0;
    vBase.Div = Class.extend({//dom元素控件
        $ele: null,_id: -1,_animateCnt:0,
        getAnimateId: function (add) {
          if(add)this._animateCnt++;
          return this._id==-1 ? (this._id=idCreater++) : this._id;
        },
        animateFinish : function(){
            return --this._animateCnt == 0;// 全部播放完毕
        },
        getWidth: function () {//元素控件对象如果构造器有w属性就取,否则取对象的w,再没有就取div的w,再没有只能取div里ele的offsetWidth
            var w = this._w;
            if (w == undefined)if ((w = this.constructor._w) == undefined)w = this.$ele.offsetWidth;
            return w;
        },
        getHeight: function () {//元素控件对象如果div有w属性就取,否则取对象的w,再没有就取构造器的w,再没有只能取div里ele的offsetWidth
            var h = this._h;
            if (h == undefined)if ((h = this.constructor._h) == undefined)h = this.$ele.offsetHeight;
            return h;
        },
        getX: function () {
            var x = this._x;
            if (x == undefined || isNaN(x)) {
                var xy = this.$ele.offset();
                this._y = xy.top;
                return this._x = xy.left;
            }
            return x;
        },
        getY: function () {
            var y = this._y;
            if (y == undefined || isNaN(y))if ((y = this.y) == undefined) {
                var xy = this.$ele.offset();
                this._x = xy.left;
                return this._y = xy.top;
            }
            return y;
        },
        ele: function () {
            return this.$ele;
        },
        show: function () {
            this.$ele.show();
            return this;
        },
        hide: function () {
            this.$ele.hide();
            return this;
        },
        setClass: function (cls) {
            this.$ele.attr("class", cls);
            return this;
        },
        init: function (ele, sets) {//sets值必须为V.SET_*或者V.SET_*|vBase.SET_*,ele有值的时候会设置一个只带ele的Div
            if (!(ele instanceof $))ele = $(ele);//自动封装为jquery对象
            this.$ele = ele;
            if (sets) {
                if ((sets & vBase.SET_WH_ALL) != 0) {
                    var cstt = this.constructor;
                    if (!cstt.getWidth) {
                        setWH(cstt, ele);
                        cstt.getWidth = getWidth;
                        cstt.getHeight = getHeight;
                    }
                } else if ((sets & vBase.SET_WH) != 0)setWH(this, ele);
                if ((sets & vBase.SET_XY) != 0)setXY(this, ele);
                else if ((sets & vBase.SET_XY_OFFSET) != 0)setOffsetXY(this, ele);
            }
        }
    });

    //金额显示对象,
    vBase.MoneyDiv = vBase.Div.extend({
        zeroHide: false,
        init: function (ele, zeroHide) {//ele为jquery封装的dom元素,zeroHide表示setMoney金额为0时是否隐藏
            this._super(ele);
        },
        setMoney: function (money) {
            if (money == 0 && this.zeroHide) {
                this.$ele.hide();
                return this;
            }
            this.$ele.text(MoneyFormat.format(money));
            this.$ele.show();
            return this;
        }
    });
    var $main = $(".dialogMain");
    var dialogNow = null;//当前窗口
    vBase.Dialog = Class.extend({
        $main: null,
        $small: null,
        $bg: null,
        $close: null,
        onHide: null,
        _showing:false,
        init: function () {
            var t = this;
            t.$main = $main;
            t.$small = $("#dialog");
            t.$bg = t.$main.find(".dialogBg");
            t.$close = t.$main.find(".Prompt_close");
            t.$close.click(t, this._hide);
        },
        isShowing : function () {
            return this._showing && this.$main.is(":visible");
        },
        show: function () {
            var t = this;
            t._showing = true;
            dialogNow = t;
            t.$small.hide();
            t.$bg.hide();
            t.$main.show();
            Velocity(t.$small[0], {scaleX: 0, scaleY: 0, opacity: 0}, 0);
            Velocity(t.$small[0], {scaleX: "1", scaleY: "1", opacity: 1}, {
                duration: 450, display: 'block', easing: [0.6, 0.96, 0.41, 1.25]
            });
            Velocity(t.$bg, {opacity: 0}, 0);
            Velocity(t.$bg, {opacity: 0.7}, {duration: 450, display: 'block'});
        },
        _hide: function (e) {
            e.data.hide();
        },
        hide: function () {
            var t = this;
            if(!t._showing)return;
            t._showing = false;
            var close = function () {
                Velocity(t.$bg[0], {opacity: 0}, 450);
                Velocity(t.$small[0], {scaleX: 0, scaleY: 0, opacity: 0}, {
                    duration: 450, easing: [0.46, -0.37, 0.32, 0.38], complete: function () {
                        t.$main.hide();
                        if (t.onHide)t.onHide();
                    }
                });
            }
            if(t.$close.length == 0){
                close();
            }else {
                Velocity(t.$close[0], {scaleX: 1.4, scaleY: 1.4}, 70);
                Velocity(t.$close[0], {scaleX: 0.8, scaleY: 0.8}, {
                    duration: 70, complete: close
                });
            }
        }
    });
    vBase.Dialog.hide = function () {//隐藏当前弹窗
        if(dialogNow)dialogNow.hide();
    }
    //弹出确认对话框,调用方式 vBase.msgDialog.show();,ok按钮绿色, cancel按钮红色,俩个按钮都会触发隐藏弹出对话框
    vBase.msgDialog = {
        $main: null,
        $small: null,
        $bg: null,
        $close: null,
        onCancel: null,
        onOk: null,
        _showing:false,
        _isOk: false,
        _init: function () {
            var t = this;
            t.$main = $(".msgDialogMain");
            t.$small = $("#msgDialog");
            t.$bg = t.$main.find(".dialogBg");
            t.$cancel = $(".Prompt_exit");
            t.$ok = $(".Prompt_wrong");
            t.$msgStr = t.$main.find(".Prompt_content");
            t.$okStr = t.$ok.find("p");
            t.$cancelStr = t.$cancel.find("p");
            t.$cancel.click(t, this._hide);
            t.$ok.click(t, this._ok);
        },
        isShowing : function () {
            return this._showing && this.$main.is(":visible");
        },
        _ok : function (e) {
            var t = e.data;
            t._isOk = true;
            t._hide(e);
        },
        //消息内容, ok按钮内容(没有不显示), cancel按钮内容(没有不显示),ok按钮触发事件, cancel按钮触发事件
        show : function(msg, okStr, cancelStr, onOk, onCancel){
            var t = this;
            if(!t.$main) t._init();
            t.$msgStr.html(msg);
            if(okStr){
                t.$okStr.text(okStr);
                t.$ok.show();
                t.onOk = onOk;
            }else{
                t.$ok.hide();
            }
            if(cancelStr){
                t.$cancelStr.text(cancelStr);
                t.$cancel.show();
                t.onCancel = onCancel;
            }else{
                t.$cancel.hide();
            }
            t._show();
        },
        _show: function () {
            this._showing = true;
            var t = this;
            t._isOk = false;
            t.$small.hide();
            t.$bg.hide();
            t.$main.show();
            Velocity(t.$small[0], {scaleX: 0, scaleY: 0, opacity: 0}, 0);
            Velocity(t.$small[0], {scaleX: "1", scaleY: "1", opacity: 1}, {
                duration: 450, display: 'block', easing: [0.6, 0.96, 0.41, 1.25]
            });
            Velocity(t.$bg, {opacity: 0}, 0);
            Velocity(t.$bg, {opacity: 0.7}, {duration: 450, display: 'block'});
        },
        _hide: function (e) {
            var t = e ? e.data : this;
            if(!t._showing)return;
            t._showing = false;
            if(e && t._isOk && t.onOk){
                t.onOk();
            }
            Velocity(t.$bg[0], {opacity: 0}, 450);
            Velocity(t.$small[0], {scaleX: 0, scaleY: 0, opacity: 0}, {
                duration: 450, easing: [0.46, -0.37, 0.32, 0.38], complete: function () {
                    t.$main.hide();
                    if(!t._isOk && t.onCancel){
                        t.onCancel();
                    }
                }
            });
        },
        hide: function () {//通过代码直接关闭
            this._hide();
        }

    };
    //系统提示 vBase.tip.show();
    vBase.tip = {
        $main: null,
        _showing:false,
        _showAlways:false,
        _init: function () {
            var t = this;
            t.$main = $("#systemTip");
            t.$content = $("#tipContent");
            t.$num = $("#tipNum");
            t.$mask = $("#maskDiv");
        },
        isShowing : function () {
            return this._showing && this.$main.is(":visible");
        },
        //msg消息内容, num消息对应的数字(可以为空),hideLater表示
        show : function(msg, num, showAlways){
            var t = this;
            if(t._showAlways && t._showing){//重连等一直显示的系统提示不会被其他消息打断
                return;
            }
            t.$content.html(msg);
            t._showAlways = showAlways;
            if(num != undefined && num != null)t.$num.text(num);
            Velocity(t.$main[0], {scaleY: 0, opacity: 0}, {duration: 0, display: 'none'});
            t._show();
        },
        text : function (newText) {//在显示过程中改变文字
            var t = this;
            t.$content.html(newText);
        },
        hide : function () {//通过定期隐藏关闭
            var t = this;
            if(t._showAlways){
                Velocity(t.$mask[0], {opacity: 0}, {duration: 300, easing: "liner", display: 'none'});
                t._showAlways = false;
            }
            Velocity(t.$main[0], {scaleY: 0, opacity: 0}, {duration: 300, display: 'none', complete:function () {
                t._showing = false;
            }});
        },
        _show: function () {
            this._showing = true;
            var t = this;
            Velocity(t.$main[0], {scaleY: 1, opacity: 1}, {duration: 300, easing: "liner", display: 'block'});
            if(t._showAlways){
                Velocity(t.$mask[0], {opacity: 0.2}, {duration: 300, easing: "liner", display: 'block'});
            }else{
                Velocity(t.$main[0], {scaleY: 0, opacity: 0}, {delay:2500, duration: 300, display: 'none', complete:function () {
                    t._showing = false;
                }});
            }
        }
    };
    vBase.tip._init();
    var w = window, m = Math, dId = document.getElementById;
    var events = "ontouchend" in document ? ["touchstart", "touchmove", "touchend"]
        : ["mousedown", "mousemove", "mouseup"];

    //进度条,onChanged为该进度条通过拖拽改变了的时候触发的方法function(百分比),defaultPercent默认百分比
    //minUnit 为最小单位,为10000时精确到10000, 为0.01时精确到2位小数
    vBase.Slider = Class.extend({
        _width: 0, _maxWidth: 0, _startX: 0, _min: 0, _max: 0, _value: 0, _enable: true,
        init: function (btnId, barId, stepId, btnTip, onChanged, NumFormat, minUnit) {
            var t = this;
            t.btn = document.getElementById(btnId);
            t.bar = document.getElementById(barId);
            t.step = document.getElementById(stepId);
            t.btnTip = document.getElementById(btnTip);
            t.btnTip.style.display = 'none';
            t.$val = $(t.btnTip).find("span");
            t.onChanged = onChanged;
            t._NumFormat = NumFormat;
            t._minUnit = minUnit ? minUnit : 1;
            var touchMove = function (e) {
                var thisX = getEventX(e);
                thisX = m.min(t._maxWidth, m.max(0, thisX - t._startX));
                if(thisX == t._width){
                    return;
                }
                t._width = thisX;
                t._value = Math.floor(t._width / t._maxWidth * ( t._max - t._min) / t._minUnit) * t._minUnit + t._min;
                t.$val.text(t._NumFormat?t._NumFormat(t._value):t._value);
                t._setLeft(t._width + 'px');
                if (t.onChanged)t.onChanged(t._value);
                w.getSelection ? w.getSelection().removeAllRanges() : w.selection.empty();//桌面需要
            };
            var getEventX = function (e) {
                if (e.touches && (e = e.touches[0]));
                return e.clientX;
            }
            t.btn.addEventListener(events[0], function (e) {
                if(!t._enable || t._max <= t._min){
                    return;
                }
                t._startX = getEventX(e) - t._width, t._maxWidth = t.bar.offsetWidth;
                t.btn.addEventListener(events[1], touchMove);
                Velocity(t.btnTip, "stop");
                Velocity(t.btnTip, {opacity: 1},{display:"block", duration:200, mobileHA:false});
            });
            t.btn.addEventListener(events[2], function () {
                t.btn.removeEventListener(events[1], touchMove);
                Velocity(t.btnTip, "stop");
                Velocity(t.btnTip, {opacity: 0},{display:"none",duration:2000, mobileHA:false, delay:1000});
            });
        },
        _setLeft: function (left) {
            var t = this;
            t.btnTip.style.left = t.btn.style.left = t.step.style.width = left;
        },
        setRange : function (value, min, max) {
            var t = this;
            t._value = value;
            t._max = max;
            t._min = min;
            t._width = (t._max == t._min ? 1 : m.floor((t._value - t._min) / (t._max - t._min))) * t.bar.offsetWidth;
            t.$val.text(t._NumFormat ? t._NumFormat(t._value) : t._value);
            t._setLeft(t._width + 'px');
        },
        getValue : function () {
            return this._value;
        },
        setEnable : function (enable) {
            this._enable = enable;
        }
    });

    //动画提示,参数div是主要div,showTime显示多少毫秒后隐藏div(不设置则不隐藏),onEnd会在隐藏之后触发
    vBase.AnimationTip = Class.extend({
        init:function (div, showTime, onEnd) {
            this._showTime = showTime;
            this._div = div;
            this._onEnd = onEnd;
        },
        showAnimation: function(){
            var t = this;
            t._div.show();
            if(t._showTime){
                A.setTimeOut(t._showTime, t._onEnd, t._div, {display:"none"});
            }
        },
        hide: function () {
            this._div.hide();
        },
        _div:null,
        _hideTime:null,
        _onEnd: null
    });
    /** 滚动分页组件,
     * $content 滚动区域, $list列表显示区域, $end 列表底部加载条, url 数据url,
     * data 请求参数(可包含pageNow当前页码默认0,pageSize分页大小默认10),
     * onDataLoaded(data, pageNow)加载到数据后调用, pageNow为当前页码
     * */
    vBase.ScrollPage = Class.extend({
        _loading : false,
        _loadedCount : 0,
        _maxCount : 900000000,
        init : function ($content, $list, $end, url, data, onDataLoaded) {
            var t = this;
            t.$content = $content;
            t.$list = $list;
            t.$end = $end;
            t._url = url;
            t._data = data;
            if(!data.pageSize)data.pageSize = 10;
            if(!data.pageNow)data.pageNow = 0;
            t.onDataLoaded = onDataLoaded;
            t.$end.hide();
            $content.scroll(function () {
                if(!t._loading && (t.$end.position().top <= t.$content.height())){
                    t._data.pageNow ++ ;
                    t._loadData();
                }
            });
            t._loadData();
        },
        reloadData : function () {// 重新加载数据
            var t = this;
            t._data.pageNow = 0;
            t._loadData();
        },
        _loadData : function () {//分页加载数据
            var t = this;
            var pageNow = t._data.pageNow;
            t._loading = true;
            $.ajax({
                type: "post",
                url: t._url,
                dataType:"json",
                data:t._data,
                success: function (data) {
                    t.onDataLoaded(data, pageNow);
                    if(!data.data){
                        t.$end.hide();
                        return;
                    }
                    t._loadedCount += data.data.datas.length;
                    if(t._loadedCount >= data.data.pageTotalSize){//最后一页或者无数据
                        t.$end.hide();
                    }else{
                        t.$end.show();
                        t._loading = false;
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.error("you have a error on loading:" + t.url + "状态"+textStatus);
                }
            });
        }
    });

    // 各种浏览器兼容,监听网页可见变化
    var showStateName, showChange;
    vBase.showing = true;
    if (typeof document.hidden !== "undefined") {
        showChange = "visibilitychange";
        showStateName = "visibilityState";
    } else if (typeof document.mozHidden !== "undefined") {
        showChange = "mozvisibilitychange";
        showStateName = "mozVisibilityState";
    } else if (typeof document.msHidden !== "undefined") {
        showChange = "msvisibilitychange";
        showStateName = "msVisibilityState";
    } else if (typeof document.webkitHidden !== "undefined") {
        showChange = "webkitvisibilitychange";
        showStateName = "webkitVisibilityState";
    }
    vBase.onShowChanged = null;
    document.addEventListener(showChange, function() {
        vBase.showing = (document[showStateName]=="visible");
        if(!vBase.showing){
            if(A)A.finish();
        }
    }, false);// 初始化
    return vBase;
})
