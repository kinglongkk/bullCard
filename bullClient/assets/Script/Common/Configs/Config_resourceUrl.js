//配置资源的路径或者资源的名字

var resURL = {
    atlas_multipleImgName : { //按钮上的倍数图片
        0:'comm_text_dont',
        imgName : 'comm_text_',
    },
    atlas_pokerResult : {       //卡牌上面的牌面结果
        resultImgName : 'comm_text_list_according_',
        noResult : 'comm_text_list_according_no',
        dont : 'comm_text_list_according_ok',
    },
    atlas_chooseMultipleName : {        //头像旁边的抢几和下分倍数图片
        dealer_0:'Scene_Classic_buqaing02',
        dealer_1:'Scene_Classic_qz_1',
        dealer_2:'Scene_Classic_qz_2',
        dealer_3:'Scene_Classic_qz_3',
        dealer_4:'Scene_Classic_qz_4',
        score_5:'Scene_Classic_x5',
        score_10:'Scene_Classic_x10',
        score_15:'Scene_Classic_x15',
        score_25:'Scene_Classic_x25',
    },
    //确定庄家后的特效
    ani_getDealerName : {
        horizontal : 'GetDealer01',
        vertical : 'GetDealer02',
    },
    //获取金币时候的头像特效
    ani_getGoldOnHeadName : {
        horizontal : 'GoldOnHead_horizontal',
        vertical : 'GoldOnHead_vertical',
    },
    //开始动画
    boneAni : {
        loading : 'BoneAni_loading',                //加载中
        matching : 'BoneAni_loading_dating',        //速配中
    },
    //卡牌基础的显示
    pokerType : {
        blockMin : 'common_card_f_02',                 //方块小
        blockMax : 'common_card_f_01',                 //方块大
        blackHeartMin : 'common_card_h_02',           //黑色桃心小
        blackHeartMax : 'common_card_h_01',           //黑色桃心大
        redHeartMin : 'common_card_hong_02',          //红色桃心小
        redHeartMax : 'common_card_hong_01',          //红色桃心大
        flowerMin : 'common_card_mei_02',             //梅花小
        flowerMax : 'common_card_mei_01',             //梅花大
        value_J : 'common_card_J',                     //卡牌j的牌型显示
        value_Q : 'common_card_Q',                     //卡牌Q的牌型显示
        value_K : 'common_card_k',                     //卡牌K的牌型显示
    },
    //表名枚举
    dict_tablesName : {
        platformConfig : '',
        commonConfig : 'CommonConfig',
        commonLoading : 'Common_loadingTips',
        //classicConfig : 'Classic_mainConfig',
        chinese : 'Chinese',
        grabConfig : 'Grab_mainConfig',
        //bull100Config : 'Bull100_mainConfig',
        audioName : 'Enum_audioName',
    },

    //ui界面
    uiName : cc.Enum({
        account : 'Prefab_dialog_account',                                                                                              //账目界面
        announcement : 'Prefab_dialog_announcement',                                                                                   //公告界面
        dealerList : 'Prefab_dialog_dealerList',                                                                                        //上庄列表界面
        playerList : 'Prefab_dialog_playerList',                                                                                        //玩家列表界面
        record : 'Prefab_dialog_record',                                                                                                 //记录界面
        rules : 'Prefab_dialog_rules',                                                                                                   //规则界面
        rulesInHome : 'Prefab_dialog_rulesInHome',                                                                                      //房间内的规则界面
        systemSet : 'Prefab_dialog_set',                                                                                                 //系统设置界面
        bull100Trend : 'Prefab_dialog_bull100Trend',                                                                                     //百人走势图界面
        grabTrend : 'Prefab_dialog_grabTrend',                                                                                            //押宝走势图界面

    }),

    //音效路径
    audioUrl : 'resources/Sounds/%s.mp3',
}
window.G_RES_URL = resURL;