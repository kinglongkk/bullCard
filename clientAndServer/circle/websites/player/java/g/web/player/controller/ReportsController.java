package g.web.player.controller;

import g.model.bet.BetTypeEnum;
import g.model.enums.LotteryTypeEnum;
import g.model.bet.BetSettleStatus;
import g.model.bet.po.Bet;
import g.model.bet.po.VBetDetail;
import g.model.bet.BetItem;
import g.model.bet.vo.BetListVo;
import g.model.bet.vo.VBetDetailListVo;
import g.model.bet.vo.VBetDetailVo;
import g.model.tool.AppDateTool;
import g.service.bet.BetService;
import g.service.bet.IVBetDetailService;
import g.web.player.controller.form.VBetDetailForm;
import g.web.player.controller.form.VBetDetailSearchForm;
import g.web.player.session.SessionManager;
import org.soul.commons.lang.DateTool;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.locale.DateQuickPicker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.*;


/**
 * 报表控制器
 *
 * @author mark
 * @time 2016-7-12 14:22:28
 */
@Controller
//region your codes 1
@RequestMapping("/reports")
public class ReportsController extends BaseCrudController<IVBetDetailService, VBetDetailListVo, VBetDetailVo, VBetDetailSearchForm, VBetDetailForm, VBetDetail, Long> {



    @Autowired
    private BetService betService;
    @Autowired
    private IVBetDetailService vBetDetailService;
    //endregion your codes 1

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/app/reports/";
        //endregion your codes 2
    }

    //region your codes 3
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String game() {
        return getViewBasePath() + "Report";
    }


    @RequestMapping(value = "/index",method = RequestMethod.POST)
    @ResponseBody
    public Map gameResult(VBetDetailListVo listVo) throws ParseException {
        listVo.getSearch().setSysUserId(SessionManager.getUserId());
        Date startTime = DateQuickPicker.getInstance().getDay(SessionManager.getTimeZone());
        Date endTime = DateTool.addDays(startTime, 1);
        BetListVo betVo = new BetListVo();
        if(listVo.getSearch().getSettleStatus().equals(BetSettleStatus.SETTLED.getCode())){
            betVo.getSearch().setStartTime(startTime);
            betVo.getSearch().setEndTime(endTime);
            listVo.getSearch().setStartTime(startTime);
            listVo.getSearch().setEndTime(endTime);
        }
        listVo = getService().search(listVo);
        betVo.getSearch().setSettleStatus(listVo.getSearch().getSettleStatus());
        if(BetSettleStatus.UN_SETTLE.getCode().equals(listVo.getSearch().getSettleStatus())){
            betVo.setPropertyName(Bet.PROP_SINGLE_AMOUNT);
        }else{
            betVo.setPropertyName(Bet.PROP_PROFIT_AMOUNT);
        }

        betVo.getSearch().setSysUserId(SessionManager.getUserId());
        Number sum = betService.sum(betVo);
        //类型
        listVo.setProperties(VBetDetail.PROP_BALL_TYPE,
                //期数
                VBetDetail.PROP_CODE,
                //时间
                VBetDetail.PROP_RESULT_GATHER_TIME,
                //投注内容
                VBetDetail.PROP_IOR_FIELD,
                VBetDetail.PROP_SINGLE_AMOUNT,
                //游戏结果
                // TODO: 2016/11/11 black matchResult 添加 
//                VBetDetail.PROP_MATCH_RESULT,
                //输赢
                VBetDetail.PROP_PROFIT_AMOUNT,
                //状态
                VBetDetail.PROP_STATUS,
                VBetDetail.PROP_SETTLE_STATUS,
                VBetDetail.PROP_BET_RESULT
                );
        List<Map<String,Object>> properties = vBetDetailService.pagingReturnProperties(listVo);
        for (Map<String, Object> property : properties) {
            String ballType = (String) property.get(VBetDetail.PROP_BALL_TYPE);
            String matchCode = (String) property.get(VBetDetail.PROP_CODE);
            Date time = (Date) property.get(VBetDetail.PROP_RESULT_GATHER_TIME);
            String iorField = (String) property.get(VBetDetail.PROP_IOR_FIELD);
            String betType = (String) property.get(VBetDetail.PROP_BET_TYPE);
            property.put(VBetDetail.PROP_BALL_TYPE, LotteryTypeEnum.enumOf(ballType).getTrans());
            property.put(VBetDetail.PROP_IOR_FIELD, BetItem.fromString(BetTypeEnum.enumOf(betType),iorField).toDesc());
            property.put(VBetDetail.PROP_CODE,matchCode.substring(8));
            property.put(VBetDetail.PROP_RESULT_GATHER_TIME,DateTool.formatDate(time,SessionManager.getLocale(),SessionManager.getTimeZone(), AppDateTool.FMT_CLN_MINUTE));
        }
        Map<String,Object> map = new HashMap<>();
        map.put("paging",listVo.getPaging());
        map.put("results",properties);
        map.put("sum",sum);
        return map;
    }
    //endregion your codes 3

}