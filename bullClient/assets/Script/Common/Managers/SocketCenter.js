if(!Number.isInteger)Number.isInteger=function(v){return "number"==typeof v&&(0|v)===v};

//数据对象结构体，具体的属性将通过服务端来完善
//socket对象
//var WebSocket = WebSocket || window.WebSocket || window.MozWebSocket;
if(window.netBeanInited == undefined || !window.netBeanInited){//只能初始化一次
    window.NetBean = new Object();
}
/** 消息类,用来封装消息,解析消息 */
var Msg = function(){
    this.pos=0;
    this.data = "";
    this.len = 0; //消息长度
    this.deepCount = 0;//deepCount 用于object嵌套检测,>=10时不再解析
}
var initTime = 1400000000;
var NetServer = function (wsUrl,msgHandle,printMsg){
    this.wsUrl=wsUrl;this.msgHandle=msgHandle;this.printMsg=printMsg;
    this.timeDiff = initTime;//时间差=当前时间-服务器时间
    this.getServerTime=function(){
        return new Date().getTime()-this.timeDiff;
    }
    this.resetServerTime= function(serverTime){
        this.timeDiff = Math.min(this.timeDiff, new Date().getTime()-serverTime);
    }
}
//网络服务是否已连接
NetServer.prototype.isConnected = function(){
    return this.socket && this.socket.readyState == WebSocket.OPEN;
}
NetServer.prototype.connect=function(){
    if (!window.WebSocket) {
        window.WebSocket = window.MozWebSocket;
    }
    if (window.WebSocket) {
        this.socket = new WebSocket(this.wsUrl);
        var netSrv = this;
        this.socket.onmessage = function(event) {
            //读取数据
            var obj = Msg.toObject(event.data, netSrv);
            if(obj==-1){
                if(netSrv.onOpen){
                    netSrv.onOpen();
                }
            }else{
                // console.dir(obj);
                // if(obj.constructor.className == "NbTest"){
                //     netSrv.send("NbTest", obj);
                // }
                var byHandle = true;
                //夺宝大战通过下面代码注入接收消息,返回值表示是否由默认handle处理
                // netSrv.msgReceived = function (className, obj) {
                //     return false;
                // }
                if(netSrv.msgReceived){
                    byHandle = netSrv.msgReceived(obj.constructor.className, obj);
                }
                //if(byHandle && netSrv.msgHandle[obj.constructor.className]){
                //    netSrv.msgHandle[obj.constructor.className](obj);
                //}
                if(netSrv.printMsg){
                    // console.log('\n收到数据 event:'+obj.constructor.event +" ,eventName:"+ obj.constructor.className+',\n' + TestUtil.objToStr(obj));
                }
            }
        }
        this.socket.onopen = function(event) {
            this.send('0');//发送一个字节获取json
        }
        this.socket.onclose = function(event) {
            if(netSrv.onClose){
                netSrv.onClose();
            }
        }
        return this;
    } else {
        return undefined;
    }
}
/** 发送心跳消息 */
NetServer.prototype.sendHeart=function() {
    if (this.socket.readyState == WebSocket.OPEN) {
        this.socket.send(String.fromCharCode(9));
        return true;
    } else {
        throw new Error("Disconnect from Server,Please try again...");
    }
}

NetServer.prototype.send=function(type, obj) {
    if (this.socket.readyState == WebSocket.OPEN) {
        try{
            var msg = Msg.toMsg(type, obj);
            this.socket.send(msg);
        }catch(e){
            console.error(e);
        }
        return true;
    } else {
        throw new Error("Disconnect from Server,Please try again...");
    }
}
/** 数据类型,名称后面带S为列表,MAP为map或对象,必须为奇数,单数据则为偶数 */
Msg.TYPE_NUM=0,Msg.TYPE_NUMS=1,Msg.TYPE_NUM_MAP=2,
    Msg.TYPE_STRING=4,Msg.TYPE_STRINGS=5,Msg.TYPE_STRING_MAP=6,
    Msg.TYPE_BOOL=8,Msg.TYPE_BOOLS=9,Msg.TYPE_BOOL_MAP=10;
Msg.Type = function (collectionType, jsonClass) {
    this.collectionType = collectionType;
    this.jsonClass = jsonClass;
    return this;
}

Msg.getMsg = function (fromServerData){
    var m = new Msg();
    m.data=fromServerData;
    return m;
}

var TYPE_SINGLE = 0,TYPE_ARRAY = 1,TYPE_LIST = 2,TYPE_MAP = 3;
Msg.prototype.loadJsons = function (netSrv){
    var getJson = function(index){
        if(!NetBean[index])NetBean[index] = function(){ return this;}
        return NetBean[index];
    }
    var jsonCount = this.getLong();
    for(var i = 0; i<jsonCount; i++){
        var index = this.getLong();
        var t = getJson(index);
        t.event=index;
        t.className=this.getString();
        t.sendBy=this.getLong();
        NetBean[t.className] = t;
        var fieldCount = this.getLong();
        t.fields = new Array();
        t.types = new Array();
        t.requireds = new Array();
        for(var j = 0; j<fieldCount; j++){
            var fieldIndex = this.getLong();
            t.requireds[fieldIndex]=this.getLong()==1;
            t.fields[fieldIndex]=this.getString();
            var collectionType = this.getLong();
            var fieldType = this.getLong();
            if(fieldType<8){//基本数据类型
                fieldType = fieldType*4 + collectionType;
                t.types[fieldIndex] = fieldType;
            }else{
                t.types[fieldIndex] = new Msg.Type(collectionType,getJson(fieldType-8));
            }
        }
    }
    window.netBeanInited = true;
}

Msg.toObject = function (fromServerData, netSrv){
    var msg = Msg.getMsg(fromServerData);
    var serverTime = msg.getLong();
    netSrv.resetServerTime(serverTime);
    var classIndex = msg.getLong();
    if(classIndex==-1){
        msg.loadJsons(netSrv);
        return -1;
    }
    return msg.toObject(NetBean[classIndex]);
}

Msg.toMsg = function (nbType, obj){
    var msg = new Msg();
    var nb = NetBean[nbType];
    msg.putLong(nb.event);
    msg.toMsg(nb, obj);
    return msg.data;
}

Msg.prototype.toMsg = function (nb, o){
    var fieldCount=0;
    this.mark();//fieldCount
    var types = nb.types, fields = nb.fields,requireds = nb.requireds;
    for (var i = 0; i < fields.length; i ++) {
        var val = o[fields[i]];
        if(val == undefined || val == null){
            if(requireds[i]){
                throw new Error("required的值不能为空,NetBean."+nb.className+"."+nb.fields[i]);
            }
            continue;
        }
        fieldCount ++;
        this.putLong(i);
        var error = this.toFieldMsg(types[i], val);
        if(error != null){
            throw new Error("变量赋值错误,NetBean."+nb.className+"."+nb.fields[i]
                +"="+val+",数据类型应该为:"+error);
        }
    }
    this.putByteOnMark(fieldCount);
}

Msg.prototype.toFieldMsg = function (type, val) {
    switch (type) {
        case Msg.TYPE_NUM:
            if(!Number.isInteger(val))return "数字整型";
            this.putLong(val);
            break;
        case Msg.TYPE_STRING:
            if(typeof val != "string")return "字符串类型";
            this.putString(val);
            break;
        case Msg.TYPE_BOOL:
            if(typeof val != "boolean")return "布尔类型";
            this.putBoolean(val);
            break;
        default: {
            var collectionType = TYPE_SINGLE;
            var dataType = Msg.TYPE_NUM;
            if(typeof type == "number"){
                collectionType = type&3;
                dataType = type-collectionType;
            }else{
                collectionType = type.collectionType;
                dataType = -1;
            }
            switch(collectionType){
                case TYPE_SINGLE:{
                    this.toMsg(type, val);
                }break;
                case TYPE_MAP: {
                    this.mark();
                    var len = 0;
                    for (var j in val) {
                        this.toFieldMsg(Msg.TYPE_STRING, j+"");
                        if(dataType!=-1){
                            this.toFieldMsg(dataType, val[j]);
                        }else{
                            this.toMsg(type.jsonClass, val[j]);
                        }
                        len ++;
                    }
                    this.putLongOnMark(val.length);
                }break;
                default: {//Array
                    this.putLong(val.length);
                    for (var j in val) {
                        if(dataType!=-1){
                            this.toFieldMsg(dataType, val[j]);
                        }else{
                            this.toMsg(type.jsonClass, val[j]);
                        }
                    }
                };
            }
            break;
        }
    }
    return null;
}

/**
 * @param jsonClass Json对应的类
 * @returns 消息转换的对象
 */
Msg.prototype.toObject = function (jsonClass){
    var obj;
    try{
        obj = new jsonClass();
    }catch (e){
        console.log(e);
    }
    var fieldLen = this.getByte();
    for(var i = 0; i < fieldLen; i++){//获取fieldIndex,再获取变量值的模式
        var fieldIndex = this.getLong();
        var jsonType = jsonClass.types[fieldIndex];
        var isObject = typeof jsonType != 'number';
        if((!isObject && (jsonType&3!=0)) || (isObject && jsonType.collectionType != TYPE_SINGLE)) {//奇数,列表,先读取数量
            var isMap = false;
            if(!isObject){
                if((jsonType & 3) == 3) isMap = true;
                jsonType = jsonType - (jsonType&3);
            }else{
                isMap = jsonType.collectionType == TYPE_MAP;
            }
            var listLen = this.getFiled(Msg.TYPE_NUM);
            if(!isMap){
                obj[jsonClass.fields[fieldIndex]] = new Array();
            }else{
                obj[jsonClass.fields[fieldIndex]] = {};
            }
            for(var j = 0; j < listLen; j++){
                var key = isMap ? this.getFiled(Msg.TYPE_STRING) : j;
                obj[jsonClass.fields[fieldIndex]][key]=this.getFiled(jsonType);
            }
        }else{
            obj[jsonClass.fields[fieldIndex]]=this.getFiled(jsonType);
        }
    }
    return obj;
}

Msg.prototype.getFiled = function (jsonType){
    switch(jsonType){
        case Msg.TYPE_NUM:
            return this.getLong();
            break;
        case Msg.TYPE_STRING:
            return this.getString();
            break;
        case Msg.TYPE_BOOL:
            return this.getBoolean();
            break;
        default:
            return this.toObject(jsonType.jsonClass);
            break;
    }
}

Msg.prototype.getLen = function(){
    var len = this.data.charCodeAt(this.pos++);
    var byteCount = (len>>4)&7;
    len=len&15;
    for(var i=0;i<byteCount;i++){
        len = len | ( this.data.charCodeAt(this.pos++)<<(i*7+4))
    }
    return len+this.pos;
}

Msg.prototype.putLong = function(val){
    var positive = val>0;
    if(!positive)val=val*-1;
    var bit = 0; var tmp = val;
    while(tmp>0){
        tmp=parseInt(tmp/2);
        bit++;
    }
    bit = parseInt((bit+3)/7);
    this.data += String.fromCharCode((bit<<4)|((positive?0:1)<<3) | (val&7));//高1-3多余byte数，低4位正0(负1)，低1-3位数据最低3位
    val=parseInt(val/(1<<3));
    for (var i = 0; i < bit; i++) {
        this.data+=String.fromCharCode(val&127);
        val = parseInt(val/(1<<7));
    }
}

Msg.prototype.getLong = function(){
    var d = this.data.charCodeAt(this.pos++);
    var rs = d&7;
    var byteCount = (d>>4)&7;
    var rate = 1<<3;//用乘法代替位移避免溢出
    for (var i = 0; i < byteCount; i++) {
        rs = rs+((this.data.charCodeAt(this.pos++)&255)*rate);
        rate *= (1<<7);
    }
    rs = rs*((d&8)==0?1:-1);
    return rs;
}

Msg.prototype.mark = function(){
    if(!this.marks)this.marks = new Array();
    this.marks.push(this.data);
    this.data ="";
}

Msg.prototype.putByteOnMark = function(val){
    this.data = this.marks.pop()+String.fromCharCode(val&127)+this.data;
}

Msg.prototype.putLongOnMark = function(val){
    var rs = this.marks.pop();
    var positive = val>0;
    if(!positive)val=val*-1;
    var bit = 0; var tmp = val;
    while(tmp>0){
        tmp=parseInt(tmp/2);
        bit++;
    }
    bit = parseInt((bit+3)/7);
    rs += String.fromCharCode((bit<<4)|((positive?0:1)<<3) | (val&7));//高1-3多余byte数，低4位正0(负1)，低1-3位数据最低3位
    val=parseInt(val/(1<<3));
    for (var i = 0; i < bit; i++) {
        rs+=String.fromCharCode(val&127);
        val = parseInt(val/(1<<7));
    }
    this.data = rs + this.data;
}

Msg.prototype.putByte = function(val){
    return this.data+=String.fromCharCode(val&127);
}

Msg.prototype.getByte = function(){
    return this.data.charCodeAt(this.pos++)&127;
}
Msg.prototype.putBoolean = function(val){
    return this.data+=String.fromCharCode(val?1:0);
}

Msg.prototype.getBoolean = function(){
    return this.data.charCodeAt(this.pos++)==1;
}

Msg.prototype.putString=function(val){
    var count = 0;
    for(var i=0;i<val.length;i++){
        var char = val.charCodeAt(i);
        if(char<0x7f){
            count+=1;
        }else if(char<=0x7ff){
            count+=2;
        }else if(char<=0xFFFF){
            count+=3;
        }else {
            count+=4;
        }
    }
    var bit = 0,tmp = count;
    while(tmp>0){
        tmp=tmp>>1;
        bit++;
    }
    bit = parseInt((bit+2)/7);
    tmp = count;
    this.data+=String.fromCharCode((bit<<5) | (tmp&31));
    tmp = tmp>>5;
    for (var i = 0; i < bit; i++) {
        this.data+=String.fromCharCode(tmp&127);
        tmp = tmp>>7;
    }
    this.len+=count-val.length;
    this.data+=val;
}

Msg.prototype.getString=function(){
    var d =  this.data.charCodeAt(this.pos++);
    var lenCount = (d>>5)&3;//高2位字节数
    var byteCount = d&31;
    for (var i = 0; i < lenCount; i++) {
        byteCount = byteCount|((this.data.charCodeAt(this.pos++)&255)<<(i*7+5));
    }
    if(byteCount==0)return "";
    var count = 0,start=this.pos;
    for(var i = this.pos; i< this.data.length;i++){
        var char = this.data.charCodeAt(this.pos++);
        if(char<0x7f){
            count+=1;
        }else if(char<=0x7ff){
            count+=2;
        }else if(char<=0xFFFF){
            count+=3;
        }else {
            count+=4;
        }
        if(count>=byteCount){
            break;
        }
    }
    return this.data.substring(start,this.pos);
}
module.exports = NetServer;