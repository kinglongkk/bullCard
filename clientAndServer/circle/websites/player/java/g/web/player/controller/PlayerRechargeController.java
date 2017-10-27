package g.web.player.controller;

import g.model.api.result.ListResults;
import g.model.player.vo.PlayerTransactionListVo;
import g.service.common.PlayerTransactionService;
import g.web.player.model.ResultData;
import g.web.player.session.SessionManager;
import org.soul.commons.lang.DateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


/**
 * 玩家充值表-mark控制器
 *
 * @author tom
 * @time 2016-7-5 10:15:54
 */
@RestController
@RequestMapping("/playerRecharge")
public class PlayerRechargeController {

    @Autowired
    private PlayerTransactionService transactionService;

    /**
     * 玩家的账目记录
     * @return result
     */
    @RequestMapping("/playerAccountRecord")
    public Object selectPlayerAccountRecord(Integer dateClass, Integer pageNo, Integer pageSize){
        String[] messages = {"您今天没有账目记录！", "您昨天没有账目记录！", "您前天没有账目记录！"};
        String message = null;
        try{
            message = messages[dateClass];
        }catch (ArrayIndexOutOfBoundsException exception) {
            return  new  ResultData("非法查询", 0);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -dateClass);
        Date queryDay = calendar.getTime();
        String dateFormat = DateTool.formatDate(queryDay, DateTool.FMT_HYPHEN_DAY);
        Date date = DateTool.parseDate(dateFormat, DateTool.FMT_HYPHEN_DAY);
        Date newDate = DateTool.addDays(date, 1);

        ListResults result = new ListResults();
        PlayerTransactionListVo listVo = new PlayerTransactionListVo();
        listVo.getSearch().setPlayerId(SessionManager.getUserId());
        listVo.getSearch().setStartTime(date);
        listVo.getSearch().setEndTime(newDate);
        listVo.getPaging().setPageNumber(pageNo);
        listVo.getPaging().setPageSize(pageSize);
        List<Map<String, Object>> listResults = transactionService.selectPlayerAccountRecord(listVo);
        if (listResults.isEmpty()){
            return new  ResultData(message, 0);
        }
        for (int i = 0; i < listResults.size(); i++) {//按玩家当前时区格式化时间
            String completiontime = DateTool.formatDate((Date)listResults.get(i).get("completiontime"), SessionManager.getLocale(), SessionManager.getTimeZone(), "HH:mm");
            listResults.get(i).put("completiontime", completiontime);
        }
        result.setPageNo(listVo.getPaging().getPageNumber());
        result.setPageSize(listVo.getPaging().getPageSize());
        result.setPageTotalSize((int)listVo.getPaging().getTotalCount());
        result.setDatas(listResults);
        return new ResultData(result);
    }
}