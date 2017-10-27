package org.soul.pay.impl;

import org.soul.model.pay.enums.CommonFieldsConst;
import org.soul.model.pay.po.PayApiProvider;
import org.soul.model.pay.vo.OnlinePayVo;

import java.util.HashMap;

public class TonghuiPayZFBApi extends TonghuiPayApi {

    public TonghuiPayZFBApi() {
        bankCodeMap = new HashMap<>();
        currencyMap = new HashMap<>();

        currencyMap.put("CNY", "CNY");//人民币

        bankCodeMap.put("tonghui_zfb", "ZHIFUBAO");
    }

    @Override
    public OnlinePayVo getPayParams(OnlinePayVo onlinePayVo, PayApiProvider payApiProvider) {
        onlinePayVo.getCommonFieldMap().put(CommonFieldsConst.BANK_ID, "ZHIFUBAO");
        return super.getPayParams(onlinePayVo, payApiProvider);
    }

}
