package g.web.agent.controller.agent.controller;

import g.model.enums.GameTypeEnum;

import g.model.DictEnum;
import g.model.Module;
import g.model.TransactionTypeEnum;
import g.model.admin.po.Remark;
import g.model.admin.vo.RemarkVo;
import g.model.bet.BetStatus;
import g.model.bet.vo.BetVo;
import g.model.bet.po.VBetList;
import g.model.match.vo.VBetListListVo;
import g.model.bet.vo.VBetListVo;
import g.service.bet.IBetService;
import g.service.bet.ISettleService;
import g.service.common.IRemarkService;
import g.service.common.IVBetListService;
import g.web.agent.controller.agent.form.VBetListForm;
import g.web.agent.controller.agent.form.VBetListSearchForm;
import g.web.agent.session.SessionManager;
import org.soul.commons.collections.ListTool;
import org.soul.commons.dict.DictTool;
import org.soul.commons.lang.ArrayTool;
import org.soul.commons.locale.LocaleTool;
import org.soul.commons.log.LogFactory;
import org.soul.commons.net.ServletTool;
import org.soul.commons.support._Module;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.validation.form.annotation.FormModel;
import org.soul.web.validation.form.js.JsRuleCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.*;


/**
 * 注单控制器
 *
 * @author tom
 * @time 2016-6-7 19:50:30
 */
@Controller
//region your codes 1
@RequestMapping("/vBetList")
public class VBetListAgentController extends BaseCrudController<IVBetListService, VBetListListVo, VBetListVo, VBetListSearchForm, VBetListForm, VBetList, Long> {
//endregion your codes 1

    @Autowired
    private ISettleService settleService;

    @Autowired
    private IRemarkService remarkService;

    @Autowired
    private IBetService betService;

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/bet/";
        //endregion your codes 2
    }

    //region your codes 3
    /**
     * 彩票
     */
    @RequestMapping(value = "/lottery")
    public String lottery(VBetListListVo listVo, @FormModel("search") @Valid VBetListSearchForm form, BindingResult result,
                          Model model,HttpServletRequest request){
        listVo.getSearch().setGameType(GameTypeEnum.LOTTERY.getCode());
        VBetListListVo cmd = doList(listVo, form, result, model);
        model.addAttribute("command", cmd);
        if (ServletTool.isAjaxSoulRequest(request)) {
            return getViewBasePath() + "/IndexPartial";
        } else {
            return getViewBasePath() + "/Index";
        }
    }

    /**
     * 彩票
     */
    @RequestMapping(value = "/allBet")
    public String allBet(VBetListListVo listVo, @FormModel("search") @Valid VBetListSearchForm form, BindingResult result,
                         Model model,HttpServletRequest request){
        listVo.getSearch().setGameType(GameTypeEnum.LOTTERY.getCode());
        VBetListListVo cmd = doList(listVo, form, result, model);
        model.addAttribute("command", cmd);
        if (ServletTool.isAjaxSoulRequest(request)) {
            return "/withdrawdesk/AllBetPartial";
        } else {
            return "/withdrawdesk/AllBet";
        }
    }


    @Override
    protected VBetListListVo doList(VBetListListVo listVo, VBetListSearchForm form, BindingResult result, Model model) {
        Map<String, Serializable> ballType = DictTool.get(DictEnum.GAME_BALL_TYPE);
        Map<String, Serializable> betSettleStatus = DictTool.get(DictEnum.BET_SETTLE_STATUS);
        Map<String, Serializable> betType = DictTool.get(DictEnum.GAME_BET_TYPE);

        model.addAttribute("ballType", ballType);
        model.addAttribute("betSettleStatus", betSettleStatus);
        model.addAttribute("betType", betType);
        listVo = super.doList(listVo, form, result, model);

        Double totalSingleAmount = 0d;
        Double totalEffectiveAmount = 0d;
        Double totalSendcach = 0d;
        int i = 0;
        for (VBetList vBetList : listVo.getResult()) {
            totalSingleAmount += vBetList.getSingleAmount()!=null?vBetList.getSingleAmount():0;
            totalEffectiveAmount += vBetList.getEffectiveAmount()!=null?vBetList.getEffectiveAmount():0;
            totalSendcach += vBetList.getProfitAmount()!=null?vBetList.getProfitAmount():0;
            i++;
        }
        listVo.setTotalSingleAmount(totalSingleAmount);
        listVo.setTotalEffectiveAmount(totalEffectiveAmount);
        listVo.setTotalSendcach(totalSendcach);

        return listVo;
    }

    @RequestMapping(value ="/cancelOne")
    public String cancelBet(VBetListVo vo,Model model) {
        VBetListVo vBetListVo = getService().get(vo);
        vBetListVo.setValidateRule(JsRuleCreator.create(VBetListForm.class));
        model.addAttribute("command",vBetListVo);
        return getViewBasePath()+"/CancelOne";
    }


    @RequestMapping("/cancelSome")
    @ResponseBody
    public Map cancelBet(Integer[] ids,Model model) {
        Map<String, Object> map = new HashMap<>(2);
        List<Long> _ids = new ArrayList<>();
        RemarkVo remarkVo = new RemarkVo();
        List<Remark> remarks = new ArrayList<>();
        if (ArrayTool.isNotEmpty(ids)) {
            for (Integer id : ids) {
                BetVo betVo = getBetVo(Long.valueOf(id));
                if (!BetStatus.CANCLED.getCode().equals(betVo.getResult().getStatus())) {
                    _ids.add(Long.valueOf(id));
                    Remark remark = new Remark();
                    setRemark(id, betVo.getResult().getSysUserId(), remark);
                    remark.setRemarkContent(TransactionTypeEnum.CANCEL_BET.getDesc());
                    remarks.add(remark);
                }
            }
            try {
                if (_ids.size()!=0) {
                    this.settleService.betCancel(_ids);
                    this.remarkService.batchInsert(remarkVo);
                }
                map.put("okMsg", LocaleTool.tranMessage(_Module.COMMON, "cancel.bets.success"));
                map.put("state", true);
            } catch (Exception e) {
                LogFactory.getLog(VBetListAgentController.class).error(e);
                map.put("errMsg", LocaleTool.tranMessage(_Module.COMMON, "cancel.bets.error"));
                map.put("state", false);
            }
        }
        return map;
    }

    @RequestMapping("/doCancel")
    @ResponseBody
    public Map sureCancelBet(VBetListVo vo,@FormModel() @Valid VBetListForm form, BindingResult result) {
        if (!result.hasErrors()) {
            Map<String, Object> map = new HashMap<>(2);
            List<Long> betIds = ListTool.newArrayList(vo.getResult().getId());
            try {

                this.settleService.betCancel(betIds);
                Remark remark = vo.getRemark();
                BetVo betVo = getBetVo(vo.getResult().getId());
                setRemark(vo.getResult().getId().intValue(), betVo.getResult().getSysUserId(), remark);
                RemarkVo remarkVo = new RemarkVo();
                remarkVo.setResult(remark);

                this.remarkService.insert(remarkVo);
                map.put("okMsg", LocaleTool.tranMessage(_Module.COMMON, "cancel.bets.success"));
                map.put("state", true);
            } catch (Exception e) {
                LogFactory.getLog(VBetListAgentController.class).error(e);
                map.put("errMsg", LocaleTool.tranMessage(_Module.COMMON, "cancel.bets.error"));
                map.put("state", false);
            }

            return map;
        }
        return null;
    }

    private void setRemark(Integer id, Integer sysUserId, Remark remark) {
        remark.setEntityId(id);
        remark.setModel(Module.BET.getCode());
        remark.setOperator(SessionManager.getUserName());
        remark.setOperatorId(SessionManager.getUserId());
        remark.setRemarkTime(new Date());
        remark.setRemarkTitle(TransactionTypeEnum.CANCEL_BET.getDesc());
        remark.setRemarkType(TransactionTypeEnum.CANCEL_BET.getCode());
        remark.setEntityUserId(sysUserId);
    }

    private BetVo getBetVo(Long betId) {
        BetVo betVo = new BetVo();
        betVo.getSearch().setId(betId);
        betVo = betService.get(betVo);
        return betVo;
    }

    //endregion your codes 3

}