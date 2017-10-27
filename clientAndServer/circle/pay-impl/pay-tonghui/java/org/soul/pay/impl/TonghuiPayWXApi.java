package org.soul.pay.impl;

import org.soul.model.pay.enums.CommonFieldsConst;
import org.soul.model.pay.po.PayApiProvider;
import org.soul.model.pay.vo.OnlinePayVo;

import java.util.HashMap;

public class TonghuiPayWXApi extends TonghuiPayApi {

    public TonghuiPayWXApi() {
        bankCodeMap = new HashMap<>();
        currencyMap = new HashMap<>();

        currencyMap.put("CNY", "CNY");//人民币

        bankCodeMap.put("tonghui_wx", "WEIXIN");
    }

    @Override
    public OnlinePayVo getPayParams(OnlinePayVo onlinePayVo, PayApiProvider payApiProvider) {
        onlinePayVo.getCommonFieldMap().put(CommonFieldsConst.BANK_ID, "WEIXIN");
        return super.getPayParams(onlinePayVo, payApiProvider);
    }

}
