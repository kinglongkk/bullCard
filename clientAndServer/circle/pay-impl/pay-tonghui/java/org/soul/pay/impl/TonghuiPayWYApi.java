package org.soul.pay.impl;

import java.util.HashMap;

public class TonghuiPayWYApi extends TonghuiPayApi {

    public TonghuiPayWYApi() {
        bankCodeMap = new HashMap<>();
        currencyMap = new HashMap<>();

        currencyMap.put("CNY", "CNY");//人民币

        bankCodeMap.put("icbc","ICBC");//中国工商银行
        bankCodeMap.put("cmb","CMB");//招商银行
        bankCodeMap.put("ccb","CCB");//中国建设银行
        bankCodeMap.put("abc","ABC");//中国农业银行
        bankCodeMap.put("boc","BOC");//中国银行
        bankCodeMap.put("cmbc","CMBCS");//中国民生银行
        bankCodeMap.put("ceb","CEBBANK");//中国光大银行
        bankCodeMap.put("comm","BOCOM");//交通银行
        bankCodeMap.put("spdb","SPDB");//浦发银行
        bankCodeMap.put("spabank","PINGAN");//平安银行
        bankCodeMap.put("cib","CIB");//兴业银行
        bankCodeMap.put("bjbank","BCCB");//北京银行
        bankCodeMap.put("citic","ECITIC");//中信银行
        bankCodeMap.put("gdb","CGB");//广发银行
        bankCodeMap.put("psbc","PSBC");//中国邮政储蓄银行
//        bankCodeMap.put("jsbank","江苏银行");//江苏银行
        bankCodeMap.put("hxbank","HXB");//华夏银行
        bankCodeMap.put("shbank","BOS");//上海银行
//        bankCodeMap.put("bohaib","渤海银行");//渤海银行
//        bankCodeMap.put("hkbea","东亚银行");//东亚银行
//        bankCodeMap.put("nbbank","宁波银行");//宁波银行
//        bankCodeMap.put("czbank","浙商银行");//浙商银行
//        bankCodeMap.put("hzcb","杭州银行");//杭州银行
//        bankCodeMap.put("gcb","广州银行");
//        bankCodeMap.put("fjnx","福建农商银行");
//        bankCodeMap.put("hangseng","恒生银行");
    }

}
