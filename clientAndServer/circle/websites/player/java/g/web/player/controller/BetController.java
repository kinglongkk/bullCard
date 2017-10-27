package g.web.player.controller;

import g.model.bet.po.VBetDetail;
import g.model.bet.vo.VBetDetailListVo;
import g.service.bet.VBetDetailService;
import g.web.player.model.ResultData;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bet")
public class BetController {

    @Autowired
    private VBetDetailService vBetDetailService;

    @RequestMapping("/user/records")
    public Object userRecords(VBetDetailListVo vo){
        VBetDetailListVo betDetailListVo = new VBetDetailListVo();
        betDetailListVo.setProperties(VBetDetail.PROP_BET_TIME,VBetDetail.PROP_EFFECTIVE_AMOUNT,VBetDetail.PROP_BET_RESULT,VBetDetail.PROP_PROFIT_AMOUNT);
        betDetailListVo.getQuery().setCriterions(new Criterion[]{new Criterion(VBetDetail.PROP_SYS_USER_ID, Operator.EQ,vo.getSearch().getSysUserId())});
        List result = vBetDetailService.searchProperties(betDetailListVo);
        return new ResultData(result);
    }


}
