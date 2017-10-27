//项目中可以通用的工具

var tools = {
    //返回数值包括最大最小值
    getRandomArea : function(downNum, upNum) {
        return parseInt(Math.random()*(upNum - downNum + 1) + downNum);
    },
    //返回随机的bool值是或否
    getRandomBool : function(){
        return Boolean(Math.random() > 0.5);
    },
    //使用%s，%d重新配置字符串，如 this.formatStr('这是:%s',9099)  ==> 这是:9099
    formatStr:function(){
        var t=arguments,e=t.length;
        if(e<1)return"";
        var i=/(%d)|(%s)/,n=1,r=t[0],s="string"==typeof r&&i.test(r);
        if(s)for(var o=/%s/;n<e;++n){
            var a=t[n],c="number"==typeof a?i:o;
            c.test(r)?r=r.replace(c,a):r+=" "+a
        }else if(e>1)for(;n<e;++n)r+=" "+t[n]; else r=""+r;
        return r
    },
    //改变金钱的单位
    changeMoney : function (money) {
        //var rate=null,name = '',dict = G_Config_common.dict_moneyUnit;
        //for(var rateLimit in dict){
        //    if(num >= rateLimit){
        //        rate = rateLimit;
        //        name = dict[rateLimit];
        //    }else break;
        //}
        //if(!rate) return num;
        //var limitLen = 4;       //将金额的数值控制在4位数
        //var value = parseFloat((num / rate).toFixed(3)).toString();
        //var valueLen = value.length;
        //if(valueLen >limitLen){
        //    var pointIndex = value.indexOf('.');
        //    if(pointIndex < 0){
        //        //整数
        //        value = value.substring(0,limitLen+1);
        //    }else{
        //        //有小数
        //        if(pointIndex >= limitLen) value = value.substring(0,limitLen);
        //        else value = value.substring(0,limitLen+1);
        //    }
        //}
        //return value+name
        var zeroCnt = 0, nega = "";//带多少个0，负数标志位
        if (money < 0) {
            money = -money;
            nega = "-";
        }
        var tmp = money;
        while ((tmp = tmp / 10) >= 1)zeroCnt++;
        if (zeroCnt <= 3)return nega + money;
        for (var i = 0, tmp = 1; i < zeroCnt - 3; i++) {
            money /= 10;
            tmp = tmp == 10000 ? 10 : tmp * 10;
        }
        return nega + (parseInt(money) / (10000 / tmp)) + (zeroCnt < 8 ? "万" : (zeroCnt < 12 ? "亿" : "兆"));
    },
    //千分符，千位加逗号
    formatBy1000: function (money) {
        return (""+money).replace(/(\d{1,3})(?=(\d{3})+(?:$|\D))/g,"$1,");
    },
    //获取当前的时间戳, 这种获取方式可以精确到毫秒
    getTimeStamp : function () {
        //var timestamp = Date.parse(new Date());  //精确到秒
        return (new Date()).valueOf();
    },

    quickSort : function(array){
        function sort(prev, numsize){
            var nonius = prev;
            var j = numsize -1;
            var flag = array[prev];
            if ((numsize - prev) > 1) {
                while(nonius < j){
                    for(; nonius < j; j--){
                        if (array[j] < flag) {
                            array[nonius++] = array[j];　//a[i] = a[j]; i += 1;
                            break;
                        };
                    }
                    for( ; nonius < j; nonius++){
                        if (array[nonius] > flag){
                            array[j--] = array[nonius];
                            break;
                        }
                    }
                }
                array[nonius] = flag;
                sort(0, nonius);
                sort(nonius + 1, numsize);
            }
        }
        sort(0, array.length);
        return array;
    },
    
    //名字规范  name为传入的名字， len为需要留住的字符长度， isNot多出的字符用...表示（true表示要）
    getNameLimit : function (name, len, isNot) {
        var str,
            maxNameLength;
        len ? maxNameLength = len : maxNameLength = G_Config_common.num_maxNameLen;
        var reg = /^[\u4E00-\u9FA5]+$/;
        var wordsNum = 0;
        var newStr = '';
        for(var i =0; i < name.length; i ++){
            if(wordsNum >= maxNameLength){
                if (isNot) newStr = newStr;
                else newStr += '...'
                break;
            }
            str = name[i];
            newStr += str;
            if(reg.test(str)) {
                wordsNum += 2;
            } else {
                wordsNum += 1;
            }
        }
        return newStr
    },
    objectLog : function (object) {
        var str = '{';
        for(var attrName in object){
            str += attrName+':'+object[attrName]+','
        }
        str += '}';
    },
    //适配初始节点大小
    adaptSize : function (curW, curH) {
        var designSize = cc.view.getDesignResolutionSize();
        var rateW = cc.visibleRect.width/designSize.width;
        var rateH = cc.visibleRect.height/designSize.height;
        return {width:curW*rateW, height:curH*rateH}
    },
    //适配初始节点位置
    adaptPos : function (pos) {
        var designSize = cc.view.getDesignResolutionSize();
        var rateW = cc.visibleRect.width/designSize.width;
        var rateH = cc.visibleRect.height/designSize.height;
        return cc.p(pos.x*rateW, pos.y*rateH)
    },

    //更新头像的显示,所有头像的更新都用这个接口
    resetUrlImg : function (urlNode, newFrame) {
        if(urlNode._firstSize === undefined) urlNode._firstSize = {width:urlNode.width,height:urlNode.height};
        urlNode.getComponent(cc.Sprite).spriteFrame = newFrame;
        urlNode.width = urlNode._firstSize.width;
        urlNode.height = urlNode._firstSize.height;
    },
    //根据图片名字设置图片
    setHeadImg : function (headNode, imgName) {
        if(G_Config_common.isLocal){
            this._headUrl = "Heads/"+'head01';
            cc.loader.loadRes(this._headUrl, cc.SpriteFrame, function (err, spriteFrame) {
                G_TOOL.resetUrlImg(headNode, spriteFrame);
            });
        }else{
            if(headNode._firstFrame === undefined) headNode._firstFrame = headNode.getComponent(cc.Sprite).spriteFrame;
            if(imgName) {
                this._headUrl = G_DATA.getResNetRoot(imgName);
                //this._headUrl = 'http://192.168.0.109:8080/classic/res/raw-assets/Texture/Common/head/head00.jpg';
                
                GG.resMgr.getHeadImg(this._headUrl, function (frame) {
                    if(frame){
                        G_TOOL.resetUrlImg(headNode, frame);
                    }else {
                        headNode.getComponent(cc.Sprite).spriteFrame = headNode._firstFrame;
                    }
                })
                
                // cc.loader.load(this._headUrl, function (err, texture) {
                //     if(err){
                //         console.log(err)
                //         headNode.getComponent(cc.Sprite).spriteFrame = headNode._firstFrame;
                //     }else{
                //         if(texture){
                //             var frame=new cc.SpriteFrame(texture);
                //             G_TOOL.resetUrlImg(headNode, frame);
                //         }else {
                //             headNode.getComponent(cc.Sprite).spriteFrame = headNode._firstFrame;
                //         }
                //     }
                // });
            }
        }
    },

    //获取匀速的时间
    getUniformTime : function (startPos, targetPos, maxTime) {
        var len = cc.pDistance(startPos, targetPos);
        return maxTime * (len / cc.visibleRect.width)
    },


    // showTopTip : function (content) {
    //     var canvas = cc.find('Canvas');
    //     var tipNode = canvas.getChildByTag(99);
    //     if(!tipNode){
    //         tipNode = new cc.Node();
    //         tipNode.addComponent(cc.Label);
    //         canvas.addChild(tipNode, 1, 99);
    //         tipNode.color = cc.Color.RED;
    //     }
    //     if(!tipNode._tip) tipNode._tip = '';
    //     tipNode._tip += (content+'; ');
    //     tipNode.getComponent(cc.Label).string = tipNode._tip;
    // },
}

window.G_TOOL = tools;