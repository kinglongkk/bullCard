"use strict";
cc._RF.push(module, 'fedd6zXtfZHDLTlXFNJ1otd', 'WebMobile_handler');
// Script/Common/Managers/WebMobile_handler.js

"use strict";

module.exports = cc.Class({
    properties: {
        _isGameStart: null,
        _type_toolbar: null,
        _isVertical: null
    },

    // use this for initialization
    ctor: function ctor() {
        this._isGameStart = false;
        this._isVertical = false;
        if (document) {
            this._initMobileWeb();
        }
    },

    gameStart: function gameStart() {
        this._isGameStart = true;
    },

    _initMobileWeb: function _initMobileWeb() {
        this._addHideListener();
        if (cc.sys.isMobile) this._addResizeListener();
    },

    _addResizeListener: function _addResizeListener() {
        // cc.view.resizeWithBrowserSize(true);
        // cc.view.adjustViewPort(true);
        // cc.view.setOrientation(cc.macro.ORIENTATION_LANDSCAPE);
        window.addEventListener("resize", function () {
            if (this._isGameStart) {
                var isLandscape = document.body.clientWidth > document.body.clientHeight;
                if (isLandscape) {
                    this._isVertical = false;
                    if (this._lastHeight != window.innerHeight) {
                        if (this._lastHeight && this._lastHeight > window.innerHeight) {
                            //show toolbar
                            this._doSafariHandler();
                            return;
                        }
                        this._lastHeight = window.innerHeight;
                    }
                }
                this._isVertical = !isLandscape;
                this._type_toolbar = G_TYPE.webMobile_design.toolbar_hide;
                this.adaptSceneSize();
            }
        }.bind(this));
    },

    _addHideListener: function _addHideListener() {
        var showStateName, showChange;
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
        var self = this;
        document.addEventListener(showChange, function () {
            if (self._isGameStart) {
                //var isShow = Boolean(document[showStateName]=="visible");
                if (Boolean(document[showStateName] == "visible")) {
                    //回到游戏
                    GG._resumeWebGame();
                } else {
                    //暂停游戏
                    GG._pauseWebGame();
                }
            }
        }, false);
    },

    _doSafariHandler: function _doSafariHandler() {
        var userAgent = navigator.userAgent;
        if (userAgent.indexOf('Safari') > -1) {
            this._type_toolbar = G_TYPE.webMobile_design.toolbar_show;
            var offH = this._firstDesignSize.height * 0.13;
            this._changeDesign(this._firstDesignSize.width, this._firstDesignSize.height + offH);
            this.adaptSceneSize();
        }
    },

    _scrollGame: function _scrollGame(offH) {
        setTimeout(function () {
            window.scrollTo(0, offH * 0.22);
        }, 100);
    },

    _changeDesign: function _changeDesign(width, height) {
        this._designWidth = width;
        this._designHeight = height;
    },

    _initData: function _initData() {
        if (this._firstDesignSize) return;
        var designSize = cc.view.getDesignResolutionSize();
        if (designSize.width > designSize.height) {
            this._firstDesignSize = { width: designSize.width, height: designSize.height };
        } else {
            this._firstDesignSize = { width: designSize.height, height: designSize.width };
        }
        this._lastHeight = window.innerHeight < window.innerWidth ? window.innerHeight : window.innerWidth;

        this._changeDesign(this._firstDesignSize.width, this._firstDesignSize.height);
        this._type_toolbar = G_TYPE.webMobile_design.toolbar_hide;
    },
    adaptSceneSize: function adaptSceneSize() {
        this._initData();

        if (cc.sys.isMobile) {
            if (document) {
                if (this._isVertical) {
                    cc.view.setOrientation(cc.macro.ORIENTATION_PORTRAIT);
                    cc.view.setDesignResolutionSize(this._firstDesignSize.width, this._firstDesignSize.height, cc.ResolutionPolicy.SHOW_ALL);
                } else {
                    cc.view.setOrientation(cc.macro.ORIENTATION_LANDSCAPE);
                    switch (this._type_toolbar) {
                        case G_TYPE.webMobile_design.toolbar_hide:
                            cc.view.setDesignResolutionSize(this._firstDesignSize.width, this._firstDesignSize.height, cc.ResolutionPolicy.EXACT_FIT);
                            break;
                        case G_TYPE.webMobile_design.toolbar_show:
                            setTimeout(function () {
                                cc.view.setDesignResolutionSize(this._designWidth, this._designHeight, cc.ResolutionPolicy.FIXED_HEIGHT);
                                this._scrollGame(this._designHeight - this._firstDesignSize.height);
                            }.bind(this), 200);
                            break;
                        default:
                            cc.view.setDesignResolutionSize(this._firstDesignSize.width, this._firstDesignSize.height, cc.ResolutionPolicy.EXACT_FIT);
                            break;
                    }
                }
            } else {
                cc.view.setDesignResolutionSize(this._firstDesignSize.width, this._firstDesignSize.height, cc.ResolutionPolicy.EXACT_FIT);
            }
        } else {
            cc.view.setDesignResolutionSize(this._firstDesignSize.width, this._firstDesignSize.height, cc.ResolutionPolicy.SHOW_ALL);
        }
    },

    getLocalHost: function getLocalHost() {
        return window.location.host;
    }

});

cc._RF.pop();