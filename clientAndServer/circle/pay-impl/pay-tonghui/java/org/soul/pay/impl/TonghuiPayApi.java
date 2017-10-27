package org.soul.pay.impl;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.soul.commons.lang.DateTool;
import org.soul.commons.lang.SystemTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.net.http.HttpClientParam;
import org.soul.commons.net.http.HttpClientTool;
import org.soul.model.pay.enums.CommonFieldsConst;
import org.soul.model.pay.enums.EncryptModeConst;
import org.soul.model.pay.enums.PayApiTypeConst;
import org.soul.model.pay.enums.PayResultStatus;
import org.soul.model.pay.po.PayApiProvider;
import org.soul.model.pay.vo.OnlinePayVo;
import org.soul.model.pay.vo.PayAccountParam;
import org.soul.service.pay.core.AbstractPayApi;

import java.util.*;

/**
 * Created by gamebox on 16-3-31.
 */
public class TonghuiPayApi extends AbstractPayApi {

    private static final Log LOG = LogFactory.getLog(TonghuiPayApi.class);

    private static String paySignXmlFormat = "bank_code=${bank_code}&customer_ip=${customer_ip}&input_charset=${input_charset}&merchant_code=${merchant_code}&notify_url=${notify_url}&order_amount=${order_amount}" +
            "&order_no=${order_no}&order_time=${order_time}&pay_type=${pay_type}&req_referer=${req_referer}&return_url=${return_url}&key=${" + CommonFieldsConst.KEY + "}";

    private static String callBackSignXmlFormat = "merchant_code=${merchant_code}&notify_type=${notify_type}&order_amount=${order_amount}&order_no=${order_no}&order_time=${order_time}&trade_no=${trade_no}&trade_status=${trade_status}" +
            "&trade_time=${trade_time}&key=${" + CommonFieldsConst.KEY + "}";

    private static String querySignXmlFormat = "input_charset=${input_charset}&merchant_code=${merchant_code}&order_no=${order_no}&key=${" + CommonFieldsConst.KEY + "}";

    private static String queryResultSignXmlFormat = "merchant_code=${merchant_code}&order_amount=${order_amount}&order_no=${order_no}&order_time=${order_time}&trade_no=${trade_no}&trade_status=${trade_status}" +
            "&trade_time=${trade_time}&key=${" + CommonFieldsConst.KEY + "}";

    public TonghuiPayApi() {
        bankCodeMap = new HashMap<>();
        currencyMap = new HashMap<>();
    }

    @Override
    public OnlinePayVo getPayParams(OnlinePayVo onlinePayVo, PayApiProvider payApiProvider) {
        Map<String, String> commonFieldMap = onlinePayVo.getCommonFieldMap();
        String merchant_code = commonFieldMap.get(CommonFieldsConst.MERCHANT_CODE);
        String bankCode = bankCodeMap.get(commonFieldMap.get(CommonFieldsConst.BANK_ID));
        String order_no = commonFieldMap.get(CommonFieldsConst.ORDER_ID);
        String payDomain = commonFieldMap.get(CommonFieldsConst.PAYDOMAIN);
        String order_amount = commonFieldMap.get(CommonFieldsConst.ORDER_MONEY);
        String customer_ip = commonFieldMap.get(CommonFieldsConst.IP);
        String order_time = DateTool.formatDate(getOrderDate(commonFieldMap), DateTool.FMT_HYPHEN_DAY_CLN_SECOND);
        String frontUrl = StringTool.fillTemplate(this.frontUrl, commonFieldMap);
        String serverUrl = StringTool.fillTemplate(this.serverUrl, commonFieldMap);
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("input_charset", "UTF-8");
        paramMap.put("notify_url", serverUrl);
        paramMap.put("return_url", frontUrl);
        paramMap.put("pay_type", "1");
        paramMap.put("bank_code", bankCode);
        paramMap.put("merchant_code", merchant_code);
        paramMap.put("order_no", order_no);
        paramMap.put("order_amount", order_amount);
        paramMap.put("order_time", order_time);
        paramMap.put("req_referer", payDomain);
        paramMap.put("customer_ip", customer_ip);

        Map<String, String> extMap = (Map<String, String>) payApiProvider.getExtMap().get(SystemTool.isDebug() ? EXT_TEST : EXT_PRO);
        String payUrl = extMap.get(PAY_PAYURL);
        onlinePayVo.setInterfaceUrl(payUrl);
        String sign = StringTool.fillTemplate(StringTool.fillTemplate(paySignXmlFormat, paramMap), commonFieldMap);
        String signValue = getSignValue(EncryptModeConst.MD5, sign, null);
        paramMap.put("sign", signValue);

        onlinePayVo.setParamValueMap(paramMap);
        return onlinePayVo;
    }

    @Override
    public OnlinePayVo queryOrder(OnlinePayVo onlinePayVo, PayApiProvider payApiProvider) {
        Map<String, String> commonFieldMap = onlinePayVo.getCommonFieldMap();
        String merchant_code = commonFieldMap.get(CommonFieldsConst.MERCHANT_CODE);
        String order_no = commonFieldMap.get(CommonFieldsConst.ORDER_ID);
        String orgtranAmt = commonFieldMap.get(CommonFieldsConst.ORDER_MONEY);

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("input_charset","UTF-8");
        paramMap.put("merchant_code",merchant_code);
        paramMap.put("order_no",order_no);

        String sign = StringTool.fillTemplate(StringTool.fillTemplate(querySignXmlFormat, paramMap), commonFieldMap);
        String signValue = getSignValue(EncryptModeConst.MD5, sign, null);
        paramMap.put("sign",signValue);

        Map<String, String> extMap = (Map<String, String>) payApiProvider.getExtMap().get(SystemTool.isDebug() ? EXT_TEST : EXT_PRO);
        HttpClientParam param = new HttpClientParam(extMap.get(PAY_QUERYORDERURL), paramMap);
        param.setConnectTimeout(5000);
        param.setSocketTimeout(5000);
        String orderQueryResult = HttpClientTool.sync(param);
        onlinePayVo.setQueryOrderResult(orderQueryResult);

//        <?xml version="1.0" encoding="UTF-8" ?>
//        <pay>
//        <response>
    //        <is_success>TRUE</is_success>
    //        <sign>56ae9c3286886f76e57e0993625c71fe</sign>
    //        <trade>
        //        <merchant_code>11987909</merchant_code>
        //        <order_no>5600235669</order_no>
        //        <order_time>2015-01-10 13:18:00</order_time>
        //        <order_amount>100.00</order_amount>
        //        <trade_no>5784736483031236</trade_no>
        //        <trade_time>2015-01-10 13:20:01</trade_time>
        //        <trade_status>success</trade_status>
    //        </trade>
//        </response>
//        </pay>

        if (StringTool.isEmpty(orderQueryResult)) {
            onlinePayVo.setErrorlog(PAY_QUERY_ERR);
            LOG.error(PAY_QUERY_ERR);
            return onlinePayVo;
        }
        if (orderQueryResult.startsWith("<?xml")) {
            try {
                Document document = DocumentHelper.parseText(orderQueryResult);
                Element root = document.getRootElement();
                Element response = root.element("response");
                String is_success = response.elementText("is_success");
                signValue = response.elementText("sign");
                if ("TRUE".equals(is_success)) {
                    Element trade = response.element("trade");
                    Map<String, String> map = new HashMap<>();
                    map.put("merchant_code", trade.elementText("merchant_code"));
                    map.put("order_no", trade.elementText("order_no"));
                    map.put("order_time", trade.elementText("order_time"));
                    map.put("order_amount", trade.elementText("order_amount"));
                    map.put("trade_no", trade.elementText("trade_no"));
                    map.put("trade_time", trade.elementText("trade_time"));
                    map.put("trade_status", trade.elementText("trade_status"));
                    sign = getSignValue(EncryptModeConst.MD5, StringTool.fillTemplate(StringTool.fillTemplate(queryResultSignXmlFormat, map), commonFieldMap), null);
                    if (sign.equals(signValue)) {
                        String trade_status = map.get("trade_status");
                        String order_amount = String.valueOf(map.get("order_amount"));
                        if (map.get("merchant_code").equals(merchant_code) && orgtranAmt.equals(order_amount)) {
                            onlinePayVo.setStatusCodeStr("is_success=" + is_success + ",trade_status=" + trade_status);
                            if ("success".equals(trade_status)) {
                                onlinePayVo.setPayResultStatus(PayResultStatus.SUCCESS);
                            }else if("failed".equals(trade_status)){
                                onlinePayVo.setPayResultStatus(PayResultStatus.FAIL);
                            } else if ("paying".equals(trade_status)) {
                                onlinePayVo.setPayResultStatus(PayResultStatus.PROCCESSING);
                            }
                        } else {
                            onlinePayVo.setErrorlog(PAY_MER_ORDERID_ERR);
                        }
                    } else {
                        onlinePayVo.setErrorlog(PAY_SIGN_ERR);
                    }
                } else if("FALSE".equals(is_success)){
                    String errMessage = root.elementText("errror_msg");
                    onlinePayVo.setErrorlog("is_success=" + is_success + ",errror_msg=" + errMessage);
                }
            } catch (DocumentException e) {
                LOG.error(e);
            }
        }else{
            onlinePayVo.setErrorlog("订单查询时返回未知的结果，返回值为："+orderQueryResult);
        }
        return onlinePayVo;
    }

    @Override
    public OnlinePayVo doPayPageCallBack(OnlinePayVo onlinePayVo, PayApiProvider payApiProvider) {
        return doPayCallBack(onlinePayVo, payApiProvider);
    }

    @Override
    public OnlinePayVo doPayServerCallBack(OnlinePayVo onlinePayVo, PayApiProvider payApiProvider) {
        return doPayCallBack(onlinePayVo, payApiProvider);
    }

    private OnlinePayVo doPayCallBack(OnlinePayVo onlinePayVo, PayApiProvider payApiProvider) {
        Map<String, String> requestParamMap = transformRequestMap(onlinePayVo.getRequestParamMap());
        Map<String, String> commonFieldMap = onlinePayVo.getCommonFieldMap();
        if (requestParamMap != null && requestParamMap.size() > 0) {
            String signValue = getSignValue(EncryptModeConst.MD5, StringTool.fillTemplate(StringTool.fillTemplate(callBackSignXmlFormat, requestParamMap), commonFieldMap), null);
            String sign = requestParamMap.get("sign");
            if (sign.equals(signValue)) {
                String trade_status = requestParamMap.get("trade_status");
                String merchant_code = requestParamMap.get("merchant_code");
                String order_no = requestParamMap.get("order_no");
                String order_amount = requestParamMap.get("order_amount");
                String order_time = requestParamMap.get("order_time");
                String notify_type = requestParamMap.get("notify_type");
                onlinePayVo.setStatusCodeStr("trade_status=" + trade_status + ",notify_type=" + notify_type);
                if (merchant_code.equals(commonFieldMap.get(CommonFieldsConst.MERCHANT_CODE))) {
                    onlinePayVo.getCommonFieldMap().put(CommonFieldsConst.ORDER_ID, order_no);
                    onlinePayVo.getCommonFieldMap().put(CommonFieldsConst.ORDER_MONEY, order_amount);
                    Date date = DateTool.changeTimeZone(DateTool.parseDate(order_time, DateTool.UNFMT_yyyyMMddHHmmss), TimeZone.getTimeZone("GMT+8"), TimeZone.getTimeZone("GMT"));
                    onlinePayVo.getCommonFieldMap().put(CommonFieldsConst.ORDER_DATE, DateTool.formatDate(date, DateTool.UNFMT_yyyyMMddHHmmss));
                    if ("success".equals(trade_status)) {
                        onlinePayVo.setPayResultStatus(PayResultStatus.SUCCESS);
                        if (PayApiTypeConst.PAY_SERVERCALLBACK.equals(onlinePayVo.getApiType()) && "back_notify".equals(notify_type)) {
                            onlinePayVo.setResponseText("success");
                        }
                    } else if ("paying".equals(trade_status)) {
                        onlinePayVo.setPayResultStatus(PayResultStatus.PROCCESSING);
                    } else if ("failed".equals(trade_status)) {
                        onlinePayVo.setPayResultStatus(PayResultStatus.FAIL);
                    }
                } else {
                    onlinePayVo.setErrorlog(PAY_MER_ORDERID_ERR);
                }
            } else {
                onlinePayVo.setErrorlog(PAY_SIGN_ERR);
            }
        }
        return onlinePayVo;
    }

    public String getCodeInfo(String code){
        String retInfo;
        switch(code){
            case "0000": retInfo = "交易成功"; break;
            case "GT01": retInfo = "系统内部错误"; break;
            case "GT03": retInfo = "通讯失败"; break;
            case "GT11": retInfo = "虚拟账户不存在"; break;
            case "GT12": retInfo = "用户没有注册国付宝账户"; break;
            case "GT14": retInfo = "该笔记录不存在"; break;
            case "GT16": retInfo = "缺少字段"; break;
            case "MR01": retInfo = "验证签名失败"; break;
            case "MR02": retInfo = "报文某字段格式错误"; break;
            case "MR03": retInfo = "报文某字段超出允许范围"; break;
            case "MR04": retInfo = "报文某必填字段为空"; break;
            case "MR05": retInfo = "交易类型不存在"; break;
            case "MR06": retInfo = "证书验证失败或商户校验信息验证失败"; break;
            case "MN01": retInfo = "超过今日消费上限"; break;
            case "MN02": retInfo = "超过单笔消费上限"; break;
            case "MN03": retInfo = "超过今日提现上限"; break;
            case "MN04": retInfo = "超过单笔提现上限"; break;
            case "MN07": retInfo = "余额不足"; break;
            case "MN11": retInfo = "商户上送的单笔订单交易金额超限"; break;
            case "SM01": retInfo = "商户不存在"; break;
            case "SM02": retInfo = "商户状态非正常"; break;
            case "SM03": retInfo = "商户不允许支付"; break;
            case "SM04": retInfo = "商户不具有该权限"; break;
            case "SC10": retInfo = "支付密码错误"; break;
            case "SC11": retInfo = "卡状态非正常"; break;
            case "SC13": retInfo = "国付宝号不正确"; break;
            case "ST07": retInfo = "支付类交易代码匹配失败"; break;
            case "ST10": retInfo = "交易重复,订单号已存在"; break;
            case "ST12": retInfo = "不允许(商户||企业)给个人转账"; break;
            case "ST13": retInfo = "交易重复,订单号已存在"; break;
            case "SU05": retInfo = "不存在该用户"; break;
            case "SU10": retInfo = "用户状态非正常"; break;
            case "SM07": retInfo = "商铺不能做为交易平台提供服务"; break;
            case "AN01": retInfo = "商户域名校验失败"; break;
            case "MP18": retInfo = "传递数据格式存在错误"; break;
            case "9999": retInfo = "订单处理中"; break;
            case "其他": retInfo = "订单失败"; break;
            default: retInfo = "支付失败"; break;
        }
        return retInfo;
    }

    @Override
    public OnlinePayVo getAccountParams(OnlinePayVo onlinePayVo, PayApiProvider payApiProvider) {
        Set<PayAccountParam> payAccountParams = new LinkedHashSet<>();
        PayAccountParam payAccountParam = new PayAccountParam();
        payAccountParam.setParamName("merchant_code");
        payAccountParam.setParamMean(CommonFieldsConst.MERCHANT_CODE);
        payAccountParams.add(payAccountParam);

        payAccountParam = new PayAccountParam();
        payAccountParam.setParamName(CommonFieldsConst.PAYDOMAIN);
        payAccountParam.setParamMean(CommonFieldsConst.PAYDOMAIN);
        payAccountParams.add(payAccountParam);

        payAccountParam = new PayAccountParam();
        payAccountParam.setParamName(CommonFieldsConst.KEY);
        payAccountParam.setParamMean(CommonFieldsConst.KEY);
        payAccountParams.add(payAccountParam);

        onlinePayVo.setAccountParams(payAccountParams);
        return onlinePayVo;
    }
}
