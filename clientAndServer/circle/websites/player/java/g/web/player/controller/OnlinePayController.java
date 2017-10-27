package g.web.player.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import g.model.depositdesk.enums.RechargeStatusEnum;
import g.model.depositdesk.po.PlayerRecharge;
import g.model.depositdesk.vo.PlayerRechargeVo;
import g.model.enums.PayAccountStatusEnum;
import g.model.enums.PayAccountType;
import g.model.payaccount.po.PayAccount;
import g.model.payaccount.vo.PayAccountVo;
import g.service.depositdesk.IPlayerRechargeService;
import g.service.payaccount.IPayAccountService;
import g.web.player.session.SessionManager;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.collections.MapTool;
import org.soul.commons.data.json.JsonTool;
import org.soul.commons.lang.DateTool;
import org.soul.commons.lang.string.RandomStringTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.math.NumberTool;
import org.soul.commons.net.IpTool;
import org.soul.commons.net.ServletTool;
import org.soul.commons.security.CryptoTool;
import org.soul.iservice.pay.IOnlinePayService;
import org.soul.model.pay.enums.CommonFieldsConst;
import org.soul.model.pay.enums.PayApiTypeConst;
import org.soul.model.pay.enums.PayResultStatus;
import org.soul.model.pay.vo.OnlinePayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资金管理-玩家存款
 * Created by cheery on 15-10-20.
 */
@Controller
@RequestMapping("/onlinePay")
public class OnlinePayController {
    //记录日志
    private static final Log LOG = LogFactory.getLog(OnlinePayController.class);
    //手动存提链接地址
    private static final String ARTIFICIAL_URL = "/fund/artificial/addChoose.html";

    @Autowired
    private IPlayerRechargeService playerRechargeService;
    @Autowired
    private IPayAccountService payAccountService;
    @Autowired
    private IOnlinePayService onlinePayService;

    /**
     * 线上支付回调
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping({"/fcb"})
    public String doPayFrontCallback(HttpServletRequest request, Model model) {
        OnlinePayVo onlinePayVo = this.doPayCallBack(request, "fcb");
        String orderId = onlinePayVo.getCommonFieldMap().get(CommonFieldsConst.ORDER_ID);
        String orderMoney = onlinePayVo.getCommonFieldMap().get(CommonFieldsConst.ORDER_MONEY);
        model.addAttribute("orderId", orderId);
        model.addAttribute("orderMoney", orderMoney);
        LOG.debug("支付回调前台状态：{0},交易号：{1}", onlinePayVo.getPayResultStatus(), orderId);
        if (PayResultStatus.SUCCESS == onlinePayVo.getPayResultStatus()) {
            return "pay/PaySuccess";
        } else {
            return payError(model, onlinePayVo, orderId, NumberTool.toDouble(orderMoney));
        }
    }

    /**
     * 线上支付后台调用
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping({"/bcb"})
    public String doPayBackCallback(HttpServletRequest request, HttpServletResponse response) {
        OnlinePayVo onlinePayVo = this.doPayCallBack(request, "bcb");
        String orderId = onlinePayVo.getCommonFieldMap().get(CommonFieldsConst.ORDER_ID);
        String orderMoney = onlinePayVo.getCommonFieldMap().get(CommonFieldsConst.ORDER_MONEY);
        LOG.debug("收到支付回调后台通知：订单号{0}，订单金额{1},渠道{2}，商户ID{3},状态{4}", orderId, orderMoney, onlinePayVo.getChannelCode(), request.getParameter("merchant_id"), onlinePayVo.getPayResultStatus());
        onlinePayResult(onlinePayVo);
        try {
            if (StringTool.isNotBlank(onlinePayVo.getResponseText())) {
                response.getOutputStream().write(onlinePayVo.getResponseText().getBytes());
            }
        } catch (IOException var7) {
            LOG.error(var7);
        }
        return null;
    }

    @RequestMapping("/abcefg")
    public String abcefg(PlayerRechargeVo vo, HttpServletRequest request, Model model) {
        if(StringTool.isBlank(vo.getSearch().getTransactionNo())) {
            return null;
        }
        vo = playerRechargeService.searchPlayerRecharge(vo);
        if (vo.getResult() == null || !(RechargeStatusEnum.PENDING_PAY.getCode().equals(vo.getResult().getRechargeStatus()) || RechargeStatusEnum.OVER_TIME.getCode().equals(vo.getResult().getRechargeStatus()))) {
            return null;
        }
        PayAccountVo payAccountVo = new PayAccountVo();
        payAccountVo.getSearch().setId(vo.getResult().getPayAccountId());
        payAccountVo = payAccountService.get(payAccountVo);
        //调用第三方接口充值
        return doPay(model, payAccountVo.getResult(), request, vo);
    }

    @RequestMapping("/qrcode")
    public void qrcode(HttpServletResponse response, String key) {
        byte[] qrcode = SessionManager.getQrcode(key);
        ByteArrayInputStream in = new ByteArrayInputStream(qrcode);
        response.setDateHeader("Expires", 0L);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/png");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(ImageIO.read(in), "png", outputStream);
            SessionManager.removeQrcode(key);
        } catch (Exception e) {
            LOG.error(e);
        }
    }

    @RequestMapping("/doPay")
    public String doPay(HttpServletRequest request, Model model, HttpServletResponse response) {
        String channelCode = request.getParameter("c");//支付渠道代码
        String merchantId = request.getParameter("m");//商户号
        Map<String, String> accountMap = getAccountMap(channelCode, merchantId, request);//开户数据
        OnlinePayVo onlinePayVo = new OnlinePayVo();
        onlinePayVo.setChannelCode(channelCode);
        onlinePayVo.setApiType(PayApiTypeConst.PAY);
        Map<String, String> rechargeMap = getRecharge(channelCode, request);//充值信息
        rechargeMap.putAll(accountMap);
        onlinePayVo.setCommonFieldMap(rechargeMap);
        onlinePayVo = onlinePayService.callPay(onlinePayVo);
        String payUrl = onlinePayVo.getInterfaceUrl();
        Map<String, String> payParamMap = onlinePayVo.getParamValueMap();
        model.addAttribute("payUrl", payUrl);
        model.addAttribute("payParamMap", payParamMap);
        LOG.debug("充值渠道：" + onlinePayVo.getChannelCode() + ", 提交给第三方参数：" + JsonTool.toJson(payParamMap));
        //如果是后台已经提交给第三方，则不提交第三方，直接展示结果页面
        //当前只有智付微信支付需在本站展示二维码图片
        if (MapTool.isNotEmpty(onlinePayVo.getRequestParamMap()) && StringTool.isNotBlank(onlinePayVo.getRequestParamMap().get("qrcode")[0])) {
            return payQrcode(model, onlinePayVo, rechargeMap.get("_orderId"), NumberTool.toDouble("0.01"));
        } else {
            return "pay/PayPost";
        }
    }

    @RequestMapping("/pay")
    public void callOnline(HttpServletResponse response, HttpServletRequest request) {
        String channel_code = request.getParameter("c");
        String orderMoney = request.getParameter("orderMoney");
        String bankId = request.getParameter("bankId");
        Map<String, String> accountMap = getAccountMap(channel_code, null, request);
        try {
            String payDomain = accountMap.get(CommonFieldsConst.PAYDOMAIN);
            String url = payDomain + "/doPay.html?c=" + channel_code + "&orderMoney=" + orderMoney + "&bankId=" + bankId;
            response.sendRedirect(url);
        } catch (Exception e) {
            LOG.error(e);
        }
    }

    /**
     * 模拟充值数据
     *
     * @param channelCode 支付渠道
     * @return 充值数据
     */
    private Map<String, String> getRecharge(String channelCode, HttpServletRequest request) {
        Map<String, String> commonFieldMap = new HashMap<>();
        //模拟充值信息
        String orderMoney = request.getParameter("orderMoney");
        String bankId = request.getParameter("bankId");

        commonFieldMap.put(CommonFieldsConst.CHANNEL_CODE, channelCode);
        commonFieldMap.put(CommonFieldsConst.BANK_ID, bankId);
        commonFieldMap.put(CommonFieldsConst.ORDER_MONEY, orderMoney);
        commonFieldMap.put(CommonFieldsConst.ORDER_DATE, DateTool.currentDate(DateTool.UNFMT_yyyyMMddHHmmss));
        commonFieldMap.put(CommonFieldsConst.ORDER_ID, RandomStringTool.randomAlphanumeric(18));
        commonFieldMap.put(CommonFieldsConst.CURRENCY, "CNY");
        commonFieldMap.put(CommonFieldsConst.IP, ServletTool.getIpAddr(request));

        return commonFieldMap;
    }

    /**
     * 模拟开户数据
     *
     * @param channelCode 支付渠道编号
     * @param merchantId  商户编号
     * @return 开户数据
     */
    private Map<String, String> getAccountMap(String channelCode, String merchantId, HttpServletRequest request) {
        Map<String, String> accountMap = new HashMap<>();//开户数据
//        boolean isPro = new Boolean(request.getParameter("isPro"));
        String payDomainPath = ServletTool.getRootPath(request);
        //模拟国付宝-开户数据
        if (channelCode.equals("gopay")) {
//            String merchanCode = StringTool.isNotBlank(merchantId) ? merchantId : "0000001502";
//            accountMap.put("virCardNoIn", "0000000002000000257");
//            accountMap.put(CommonFieldsConst.KEY, "11111aaaaa");
            String merchanCode = StringTool.isNotBlank(merchantId) ? merchantId : "0000009080";
            accountMap.put("virCardNoIn", "0000000002000003264");
            accountMap.put(CommonFieldsConst.KEY, "567nkAeUvG0jUDFN389");

            accountMap.put("merchantID", merchanCode);
            accountMap.put(CommonFieldsConst.MERCHANT_CODE, merchanCode);
            accountMap.put(CommonFieldsConst.PAYDOMAIN, "http://szuxfabsitymni.cn/boss");//支付专用域名
        }
        //模拟宝付-开户数据
        if (channelCode.equals("baofoo")) {
//            accountMap.put("TerminalID", "10000001");
//            String merchanCode = StringTool.isNotBlank(merchantId) ? merchantId : "100000178";
            String merchanCode = StringTool.isNotBlank(merchantId) ? merchantId : "679875";
            accountMap.put(CommonFieldsConst.MERCHANT_CODE, merchanCode);
            accountMap.put("MemberID", merchanCode);
            accountMap.put("TerminalID", "27806");
            accountMap.put(CommonFieldsConst.KEY, "ks3bfj3dwe5xg7ne");
            accountMap.put(CommonFieldsConst.PAYDOMAIN, "http://pay.szkerefj.cn");//支付专用域名
        }
        //模拟易宝-开户数据
        if (channelCode.equals("yeepay")) {
//            String merchanCode = StringTool.isNotBlank(merchantId) ? merchantId : "10001126856";
//            accountMap.put(CommonFieldsConst.KEY, "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
            String merchanCode = StringTool.isNotBlank(merchantId) ? merchantId : "10012409595";
            accountMap.put(CommonFieldsConst.KEY, "gciv6o5urgd138umhlbdn5pz7gqgwqzl28g5wx636grhxj2h0wvp23xk99xv");
            accountMap.put(CommonFieldsConst.MERCHANT_CODE, merchanCode);
            accountMap.put("p1_MerId", merchanCode);
            accountMap.put(CommonFieldsConst.PAYDOMAIN, "http://www.byschina.cn/boss");//支付专用域名
        }
        //模拟环迅-开户数据(3.0版本)
        if (channelCode.equals("ips_3")) {
//            accountMap.put(CommonFieldsConst.KEY, "GDgLwwdK270Qj1w4xho8lyTpRQZV9Jm5x4NwWOTThUa4fMhEBK9jOXFrKRT6xhlJuU2FEa89ov0ryyjfJuuPkcGzO5CeVx5ZIrkkt1aBlZV36ySvHOMcNv8rncRiy3DQ");
//            String merchanCode = StringTool.isNotBlank(merchantId) ? merchantId : "000015";
            String merchanCode = StringTool.isNotBlank(merchantId) ? merchantId : "041852";
            accountMap.put(CommonFieldsConst.MERCHANT_CODE, merchanCode);
            accountMap.put("Mer_code", merchanCode);
            accountMap.put(CommonFieldsConst.KEY, "AF08GbLK8aRlh9pxG7jTv1fVzl7UGo1agRxKNtDT21PRmlxufzvPA4R8ZV8VTq3KZr3GBVZOuEQubEjEoCpGtcPczpksljzCy4rvggh9QXVvdXXKmugG0mRxRRGNlhdA");
            accountMap.put(CommonFieldsConst.PAYDOMAIN, "http://lkjfdsert.com/boss");//支付专用域名
        }
        //模拟环迅-开户数据(7.0版本)
        if (channelCode.equals("ips_7")) {
//            accountMap.put(CommonFieldsConst.KEY, "GDgLwwdK270Qj1w4xho8lyTpRQZV9Jm5x4NwWOTThUa4fMhEBK9jOXFrKRT6xhlJuU2FEa89ov0ryyjfJuuPkcGzO5CeVx5ZIrkkt1aBlZV36ySvHOMcNv8rncRiy3DQ");
//            String merchanCode = StringTool.isNotBlank(merchantId) ? merchantId : "000015";
            String merchanCode = StringTool.isNotBlank(merchantId) ? merchantId : "176036";
            accountMap.put(CommonFieldsConst.MERCHANT_CODE, merchanCode);
            accountMap.put("MerCode", merchanCode);
            accountMap.put("Account", "1760360014");
            accountMap.put(CommonFieldsConst.KEY, "TPAwveyjMVNM6F6veHRMWMMFGgPYd1GElo6wuMv0vLwBbf4SBNUStw9KND9oVj5SZnjmOznRzMb6kr5xq02RUahDGwqEeTzSj9VeGvDm0xnTmpccZB0Rwtmsg4LM36do");
            accountMap.put(CommonFieldsConst.PAYDOMAIN, "http://qianq88.com/boss");//支付专用域名
        }
        //模拟融宝-开户数据
        if (channelCode.equals("reapal")) {
            String merchanCode = StringTool.isNotBlank(merchantId) ? merchantId : "100000000203731";
            accountMap.put(CommonFieldsConst.MERCHANT_CODE, merchanCode);
            accountMap.put("merchant_ID", merchanCode);
            accountMap.put(CommonFieldsConst.KEY, "c5230a7d94g6e17bg7c47097afagf120g3f7424c42121gc28f0bg7833fc6ef73");
            accountMap.put(CommonFieldsConst.PAYDOMAIN, payDomainPath);//支付专用域名
            accountMap.put("seller_email", "a324542@126.com");
        }
        //模拟币币-开户数据(新平台，BB开头的)
        if (channelCode.equals("bbpay_new")) {
            String merchanCode = StringTool.isNotBlank(merchantId) ? merchantId : "BB01000001470";
            accountMap.put(CommonFieldsConst.MERCHANT_CODE, merchanCode);
            accountMap.put("merchantaccount", merchanCode);
            accountMap.put(CommonFieldsConst.KEY, "6f236d69274e497f90a75919bd1aa2f0");
            accountMap.put(CommonFieldsConst.PAYDOMAIN, "http://88vl.cn/boss");//支付专用域名
        }
        //模拟币币-开户数据(老平台，非BB开头)
        if (channelCode.equals("bbpay_old")) {
            String merchanCode = StringTool.isNotBlank(merchantId) ? merchantId : "14080110355";
            accountMap.put(CommonFieldsConst.MERCHANT_CODE, merchanCode);
            accountMap.put("p3_bn", merchanCode);
            accountMap.put(CommonFieldsConst.KEY, "d77a714c9b494a46aeb8e37565b8d9ea");
            accountMap.put(CommonFieldsConst.PAYDOMAIN, "http://qianq99.com/boss");//支付专用域名
        }
        //模拟摩宝（网银支付、微信扫码、支付宝扫描）-开户数据
        if (channelCode.equals("mobao") || channelCode.equals("mobao_wx") || channelCode.equals("mobao_zfb")) {
            String merchNo = StringTool.isNotBlank(merchantId) ? merchantId : "210001510010949";
            accountMap.put("merchNo", merchNo);
            accountMap.put(CommonFieldsConst.MERCHANT_CODE, merchNo);
            accountMap.put(CommonFieldsConst.KEY, "b87ade8eb2321835daa0330d581c7123");
            accountMap.put(CommonFieldsConst.PAYDOMAIN, payDomainPath);//支付专用域名
            accountMap.put("platformID", merchNo);
        }
//        //模拟汇潮支付-开户数据
//        if (channelCode.equals("ecpss")) {
//            accountMap.put("merchant_ID", StringTool.isNotBlank(merchantId) ? merchantId : "100000000203731");
//            accountMap.put(CommonFieldsConst.KEY, "c5230a7d94g6e17bg7c47097afagf120g3f7424c42121gc28f0bg7833fc6ef73");
//            accountMap.put(CommonFieldsConst.PAYDOMAIN, payDomainPath);//支付专用域名
//            accountMap.put("seller_email", "a324542@126.com");
//        }
        //模拟汇通支付-开户数据
        if (channelCode.equals("tonghui") || channelCode.equals("tonghui_wx") || channelCode.equals("tonghui_zfb")) {
            accountMap.put(CommonFieldsConst.KEY, "b659c98f711711e6b8b244a842133fcb");
            accountMap.put(CommonFieldsConst.PAYDOMAIN, payDomainPath);
            accountMap.put(CommonFieldsConst.MERCHANT_CODE,  StringTool.isNotBlank(merchantId) ? merchantId : "10722134");
            accountMap.put(CommonFieldsConst.PAYDOMAIN, "http://158899rk66.iask.in");//支付专用域名
        }
        accountMap.put(CommonFieldsConst.PAYDOMAIN, accountMap.get(CommonFieldsConst.PAYDOMAIN) + "/onlinePay");
        return accountMap;
    }


    /**
     * 支付接口回调
     *
     * @param request
     * @return
     */
    private OnlinePayVo doPayCallBack(HttpServletRequest request, String type) {
        String c = request.getParameter("c");
        String channelCode = c.substring(0, c.indexOf("-"));
        String merchantId = c.substring(c.indexOf("-") + 1, c.length());
        PayAccountVo payAccountVo = new PayAccountVo();
        payAccountVo.getSearch().setBankCode(channelCode);
        payAccountVo.getSearch().setAccount(merchantId);
        payAccountVo.getSearch().setType(PayAccountType.ONLINE_ACCOUNT.getCode());
        payAccountVo.getSearch().setStatus(PayAccountStatusEnum.DELETED.getCode());
        PayAccount payAccount = payAccountService.searchPayAccountByRecharge(payAccountVo);
        OnlinePayVo onlinePayVo = new OnlinePayVo();
        onlinePayVo.setOperatorIp(IpTool.ipv4StringToLong(ServletTool.getIpAddr(request)));
        onlinePayVo.setOperatorName(SessionManager.getUserName());
        onlinePayVo.setChannelCode(channelCode);
        onlinePayVo.setRequestParamMap(request.getParameterMap());
        onlinePayVo.setCommonFieldMap(this.joinAccountMap(request, payAccount));
        if ("fcb".equals(type)) {
            onlinePayVo.setApiType(PayApiTypeConst.PAY_PAGECALLBACK);
            onlinePayVo = onlinePayService.callPay(onlinePayVo);
        } else if ("bcb".equals(type)) {
            onlinePayVo.setApiType(PayApiTypeConst.PAY_SERVERCALLBACK);
            onlinePayVo = onlinePayService.callPay(onlinePayVo);

        }
        return onlinePayVo;
    }

    /**
     * 处理线上支付结果
     *
     * @param onlinePayVo
     */
    private void onlinePayResult(OnlinePayVo onlinePayVo) {
        String orderId = onlinePayVo.getCommonFieldMap().get(CommonFieldsConst.ORDER_ID);
        if (StringTool.isBlank(orderId)) {
            return;
        }
        PlayerRechargeVo playerRechargeVo = new PlayerRechargeVo();
        playerRechargeVo.getSearch().setTransactionNo(orderId);
        playerRechargeVo.setIp(SessionManager.getIpDb().getIp());
        //处理存款结果
        playerRechargeVo = playerRechargeService.handleOnlineRechargeResult(playerRechargeVo, onlinePayVo);
//        if (playerRechargeVo.isSuccess()) {
//            PlayerRecharge playerRecharge = playerRechargeVo.getResult();
//            Integer playerId = playerRecharge.getPlayerId();
//            SysUserVo sysUserVo = new SysUserVo();
//            sysUserVo.getSearch().setId(playerId);
//            sysUserVo = sysUserService.get(sysUserVo);
//            SysSiteVo sysSiteVo = new SysSiteVo();
//            sysSiteVo.getSearch().setId(sysUserVo.getResult().getSiteId());
//            sysSiteVo = ServiceTool.sysSiteService().get(sysSiteVo);
//            if (RechargeStatusEnum.ONLINE_SUCCESS.getCode().equals(playerRecharge.getRechargeStatus())) {
//                //判断是否需要回充
//                backwashNotice(playerRechargeVo.getById(), playerRecharge, sysUserVo, sysSiteVo);
//            }
            //推送消息给前端
//            sendMessage(sysUserVo, sysSiteVo, playerRecharge, onlinePayVo);
//        }
    }

    /**
     * 线上支付结果传回玩家中心
     *
     * @param sysUserVo
     * @param sysSiteVo
     * @param playerRecharge
     * @param onlinePayVo
     */
//    private void sendMessage(SysUserVo sysUserVo, SysSiteVo sysSiteVo, PlayerRecharge playerRecharge, OnlinePayVo onlinePayVo) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("orderId", playerRecharge.getTransactionNo());
//        MessageVo message = new MessageVo();
//        message.setSubscribeType(CometSubscribeType.MSITE_ONLINERECHARGE.getCode());
//        message.setMsgBody(JsonTool.toJson(map));
//        message.setSendToUser(true);
//        message.setCcenterId(sysSiteVo.getResult().getParentId());
//        message.setSiteId(sysUserVo.getResult().getSiteId());
//        message.setMasterId(sysSiteVo.getResult().getSysUserId());
//        message.setUserIdList(ListTool.newArrayList(playerRecharge.getPlayerId().toString()));
//        ServiceTool.messageService().sendToMsiteMsg(message);
//    }

    /**
     * 调用第三方接口充值
     *
     * @param model
     * @param payAccount
     */
    private String doPay(Model model, PayAccount payAccount, HttpServletRequest request, PlayerRechargeVo playerRechargeVo) {
        //开户数据
        Map<String, String> accountMap = joinAccountMap(request, payAccount);

        OnlinePayVo onlinePayVo = new OnlinePayVo();
        onlinePayVo.setChannelCode(payAccount.getBankCode());
        onlinePayVo.setApiType(PayApiTypeConst.PAY);

        //充值信息
        Map<String, String> commonFieldMap = joinRecharge(playerRechargeVo.getResult(), request, payAccount);
        commonFieldMap.putAll(accountMap);

        onlinePayVo.setCommonFieldMap(commonFieldMap);
        onlinePayVo = onlinePayService.callPay(onlinePayVo);

        Map<String, String> payParamMap = onlinePayVo.getParamValueMap();
        model.addAttribute("payUrl", onlinePayVo.getInterfaceUrl());
        model.addAttribute("payParamMap", payParamMap);
        //如果是后台已经提交给第三方，则不提交第三方，直接展示结果页面
        //当前只有智付微信支付需在本站展示二维码图片
        if (StringTool.isNotBlank(onlinePayVo.getErrorlog()) || PayResultStatus.FAIL == onlinePayVo.getPayResultStatus()) {
            return payError(model, onlinePayVo, playerRechargeVo.getResult().getTransactionNo(), playerRechargeVo.getResult().getRechargeAmount());
        } else if (MapTool.isNotEmpty(onlinePayVo.getRequestParamMap()) && StringTool.isNotBlank(onlinePayVo.getRequestParamMap().get("qrcode")[0])) {
            return payQrcode(model, onlinePayVo, playerRechargeVo.getResult().getTransactionNo(), playerRechargeVo.getResult().getRechargeAmount());
        } else {
            return "pay/PayPost";
        }
    }

    private String payError(Model model, OnlinePayVo onlinePayVo, String orderId, Double orderMoney) {
        model.addAttribute("responseText", onlinePayVo.getResponseText());
        model.addAttribute("statusCode", onlinePayVo.getStatusCodeStr());
        model.addAttribute("orderId", orderId);
        model.addAttribute("orderMoney", orderMoney);
        return "pay/PayError";
    }

    private String payQrcode(Model model, OnlinePayVo onlinePayVo, String orderId, Double orderMoney) {
        SessionManager.setQrcode(orderId, onlinePayVo.getQrcode());
        model.addAttribute("key", orderId);
        model.addAttribute("money", orderMoney);
        return "pay/QrcodeDis";
    }

    /**
     * 线上支付-组装账户信息
     *
     * @param request
     * @param payAccount
     * @return
     */
    private Map<String, String> joinAccountMap(HttpServletRequest request, PayAccount payAccount) {
        Map<String, String> accountMap = new HashMap<>();//开户数据
        List<Map<String, String>> accountJson = JsonTool.fromJson(payAccount.getChannelJson(), new TypeReference<ArrayList<Map<String, String>>>() {
        });
        //账户数据（包含商户id，密钥等）
        if (CollectionTool.isNotEmpty(accountJson)) {
            for (Map<String, String> map : accountJson) {
                if ("key".equals(MapTool.getString(map, "column"))) {
                    accountMap.put(MapTool.getString(map, "column"), CryptoTool.aesDecrypt(MapTool.getString(map, "value")));
                } else {
                    accountMap.put(MapTool.getString(map, "column"), MapTool.getString(map, "value"));
                }
                if (CommonFieldsConst.MERCHANT_CODE.equals(MapTool.getString(map, "view"))) {
                    accountMap.put(CommonFieldsConst.MERCHANT_CODE, MapTool.getString(map, "value"));
                }
            }
        }
        //TODO::By cherry 回调跳转链接
        if (accountMap.get(CommonFieldsConst.PAYDOMAIN).contains("http://")) {
            accountMap.put(CommonFieldsConst.PAYDOMAIN, accountMap.get(CommonFieldsConst.PAYDOMAIN) + "/onlinePay");//支付专用域名
        } else if (request.getServerPort() > 0) {
            accountMap.put(CommonFieldsConst.PAYDOMAIN, "http://" + accountMap.get(CommonFieldsConst.PAYDOMAIN) + ":" + request.getServerPort() + "/onlinePay");//支付专用域名
        } else {
            accountMap.put(CommonFieldsConst.PAYDOMAIN, "http://" + accountMap.get(CommonFieldsConst.PAYDOMAIN) + "/onlinePay");//支付专用域名
        }
        return accountMap;
    }

    /**
     * 线上支付-组长充值信息
     *
     * @param recharge
     * @param request
     * @return
     */
    private Map<String, String> joinRecharge(PlayerRecharge recharge, HttpServletRequest request, PayAccount payAccount) {
//        SysUserVo sysUserVo = new SysUserVo();
//        sysUserVo.getSearch().setId(recharge.getPlayerId());
//        sysUserVo = ServiceTool.sysUserService().get(sysUserVo);
        Map<String, String> commonFieldMap = new HashMap<>(7);
        commonFieldMap.put(CommonFieldsConst.CHANNEL_CODE, payAccount.getBankCode());//选择收款渠道
        commonFieldMap.put(CommonFieldsConst.MERCHANT_CODE, payAccount.getAccount());
        commonFieldMap.put(CommonFieldsConst.BANK_ID, recharge.getPayerBank());//玩家支付银行
        commonFieldMap.put(CommonFieldsConst.ORDER_MONEY, new DecimalFormat("0.00").format(recharge.getRechargeAmount()));
        commonFieldMap.put(CommonFieldsConst.ORDER_DATE, DateTool.formatDate(recharge.getCreateTime(), DateTool.UNFMT_yyyyMMddHHmmss));
        commonFieldMap.put(CommonFieldsConst.ORDER_ID, recharge.getTransactionNo());
        commonFieldMap.put(CommonFieldsConst.CURRENCY, "CNY");//玩家主货币
        commonFieldMap.put(CommonFieldsConst.IP, ServletTool.getIpAddr(request));
        return commonFieldMap;
    }

    /**
     * 存款后判断是否玩家是否需回充金额发送消息
     *
     * @param userPlayer
     * @param recharge
     */
//    private void backwashNotice(UserPlayer userPlayer, PlayerRecharge recharge, SysUserVo sysUserVo, SysSiteVo sysSiteVo) {
//        if (userPlayer != null && userPlayer.getBackwashBalanceAmount() != null && userPlayer.getBackwashBalanceAmount() > 0) {
//            //自动回充后，给玩家发送消息
//            Map<String, Pair<String, String>> localeTmplMap = new HashMap<>();
//            localeTmplMap.put(sysUserVo.getResult().getDefaultLocale(), new Pair(userPlayer.getBackwashTitle(), userPlayer.getBackwashContent()));
//            NoticeVo noticeVo = new NoticeVo();
//            noticeVo.addUserIds(userPlayer.getId());
//            noticeVo.setLocaleTmplMap(localeTmplMap);
//            try {
//                ServiceTool.noticeService().publish(noticeVo);
//            } catch (Exception ex) {
//                LogFactory.getLog(this.getClass()).error(ex, "发布消息不成功");
//            }
//        }
//        if (userPlayer != null && ((userPlayer.getBackwashBalanceAmount() != null && userPlayer.getBackwashBalanceAmount() > 0 && recharge.getRechargeTotalAmount() > userPlayer.getBackwashBalanceAmount()) || (userPlayer.getBackwashBalanceAmount() == null || userPlayer.getBackwashBalanceAmount() == 0d)) && userPlayer.getManualBackwashBalanceAmount() != null && userPlayer.getManualBackwashBalanceAmount() > 0) {
//            //如果自动回充后还有余额，且手动回充需回充金额，需要提醒站长手动负数回充;无自动回充，但有手动回充，需提醒站长手动负数回充
//            MessageVo message = new MessageVo();
//            message.setSubscribeType(CometSubscribeType.MCENTER_NEGATIVE_WIHTHDRAW_REMINDER.getCode());
//            Map<String, Object> map = new HashMap<>(3);
//            map.put("date", recharge.getCreateTime());
//            map.put("currency", StringTool.isBlank(sysUserVo.getResult().getDefaultCurrency()) ? "" : sysUserVo.getResult().getDefaultCurrency());
//            map.put("amount", CurrencyTool.formatCurrency(recharge.getRechargeAmount()));
//            map.put("username", sysUserVo.getResult().getUsername());
//            message.setMsgBody(JsonTool.toJson(map));
//            message.setSendToUser(true);
//            message.setCcenterId(sysSiteVo.getResult().getParentId());
//            message.setSiteId(sysUserVo.getResult().getSiteId());
//            message.setMasterId(sysSiteVo.getResult().getSysUserId());
//
//            SysResourceListVo sysResourceListVo = new SysResourceListVo();
//            sysResourceListVo.getSearch().setUrl(ARTIFICIAL_URL);
//            message.addUserIds(ServiceTool.playerRechargeService().findUserIdByUrl(sysResourceListVo));
//            ServiceTool.messageService().sendToMcenterMsg(message);
//        }
//    }

}
