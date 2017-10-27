//资源预加载管理

var mgr = cc.Class({
    _dict_urlRes : null,                            //路径资源，不包括头像
    _dict_headImg : null,                           //头像资源
    _list_headImg : null,
    ctor : function () {
    },
    //加载单张头像图片资源
    getHeadImg : function (url, callFunc) {
        if(!this._dict_headImg) {
            this._dict_headImg = {};
            this._list_headImg = [];
        }
        if(this._dict_headImg[url]) {
            if(callFunc) callFunc(this._dict_headImg[url][0]);
            var index = this._list_headImg.indexOf(url);
            if(index > -1){
                this._list_headImg.splice(index,1);
                this._list_headImg.splice(0,0,url);
            }
        } else{
            cc.loader.load(url, function (err, texture) {
                var frame;
                if(err){
                    console.log('request headImg error== '+err)
                }else{
                    if(texture){
                        frame=new cc.SpriteFrame(texture);
                        if(frame){
                            this._dict_headImg[url] = [frame, texture];
                            this._list_headImg.splice(0,0,url);
                            if(this._list_headImg.length > 30){
                                var lastUrl = this._list_headImg.pop();
                                this.removeOneHeadImg(lastUrl);
                            }
                        }
                    }
                }
                if(callFunc) callFunc(frame);
            }.bind(this));
        }
    },

    //释放单个资源
    removeOneHeadImg : function (url) {
        if(this._dict_headImg[url]) {
            cc.loader.releaseAsset(this._dict_headImg[url][0]);
            cc.loader.releaseAsset(this._dict_headImg[url][1]);
            delete this._dict_headImg[url];
        }
    },

    //释放所有资源
    removeAllImages : function () {
        for(var url in this._dict_headImg){
            cc.loader.releaseAsset(this._dict_headImg[url][0]);
            cc.loader.releaseAsset(this._dict_headImg[url][1]);
        }
    },
})


module.exports = mgr;