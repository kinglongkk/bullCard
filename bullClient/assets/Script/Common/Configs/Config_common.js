/**
 * Created by lenovo on 2016/12/29.
 */

//所有对战模式通用的配置
var common = {
    version : '1.0.1',                                            //游戏版本
    isLocal : false,                                              //是否走本地
    frameScale : 0.5,                                             //界面缩放标准
    bottomTipPos : cc.p(cc.visibleRect.width*0.5, cc.visibleRect.height*0.1),
    time_tipDefault : 3,                                         //没有设置时间，默认存在的时长
    num_maxNameLen : 10,                                         //最大的名字长度,超过的则用省略号显示,按字符宽度

    scale_moveGold : 0.5,                                        //移动中的金币的缩放比例

    showTrendItems : 5,                                           //显示走势图条目五个
    connectTimeOut : 3,                                           //3秒超时
    defaultVolume : 1,                                            //默认音量大小
    notice_speed : 240,                                           //跑马灯移动速度
    notice_showTime : 4,                                          //跑马灯单条存在时间
    
    //btnGrayValue : 0.3,                                          //按钮置灰的透明度
    //time_confirmTipShow : 0.4,                                  //确认框显示出现时间
    //scale_confirmTipMax : 1.2,                                  //确认框最大缩放
    //num_multipleValueOff : 12,                                  //倍数按钮，数值显示大于10后的偏移
    //dict_moneyUnit : {
    //    10000: '万',
    //    100000000 : '亿',
    //},                                                            //超过数额后显示金钱单位
    //time_eyeEffect : [1.5, 2.0],                                  //眼睛上的特效，time1:移动时间，time2：停顿时间
    //time_pokerOpen : 0.6,                                        //翻牌消耗的时间，分两段

    nativeHref : 'http://192.168.0.100/player/',            //本地请求的地址
}


window.G_Config_common = common;