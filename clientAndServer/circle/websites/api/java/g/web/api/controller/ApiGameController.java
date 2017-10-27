package g.web.api.controller;

import g.api.service.IGameFetchRecordService;
import g.api.service.IGameNotifyService;
import g.api.service.IGamePlayerProfitAndLossService;
import g.api.service.IGameSelectService;
import g.model.api.param.BetRecordParams;
import g.model.api.param.GameNotifyParams;
import g.model.api.param.GameSelectParams;
import g.model.api.param.ProfitAndLossParams;
import g.model.api.result.ListResults;
import g.web.api.model.ApiRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by tony on 2016/11/17.
 * 游戏相关接口
 */

@RestController
@RequestMapping("/game")
public class ApiGameController extends ApiController {

    @Autowired
    private IGameSelectService selectService;

    @Autowired
    private IGameNotifyService notifyService;

    @Autowired
    private IGameFetchRecordService recordService;

    @Autowired
    private IGamePlayerProfitAndLossService lossService;

    /**
     * 查询游戏列表
     * 不传类型查全部
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping("/select")
    public Object gameSelect(@Valid ApiRequest<GameSelectParams> form, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            throwLegalException(getBindingResultErrorMsg(bindingResult));
        }
        GameSelectParams params = form.getParams(GameSelectParams.class);
        ListResults result = selectService.gameSelect(params);
        return responseListResults(params, result);
    }

    /**
     * 游戏通知
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping("/notify")
    public Object gameNotify(@Valid ApiRequest<GameNotifyParams> form, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            throwLegalException(getBindingResultErrorMsg(bindingResult));
        }
        GameNotifyParams params = form.getParams(GameNotifyParams.class);
        ListResults result = notifyService.gameNotify(params);
        return responseListResults(params, result);
    }

    /**
     * 单个玩家投注记录
     * 数据只能查看未超过一个月
     * @param form
     * @param bindingResult
     * @return 返回记录序列
     */
    @RequestMapping("/records")
    public Object gameRecords(@Valid ApiRequest<BetRecordParams> form, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            throwLegalException(getBindingResultErrorMsg(bindingResult));
        }
        BetRecordParams params = form.getParams(BetRecordParams.class);
        ListResults result = recordService.fetchRecord(params);
        return responseListResults(params, result);
    }

    /**
     * 查询玩家的盈亏
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping("/profitAndLoss")
    public Object profitAndLoss(@Valid ApiRequest<ProfitAndLossParams> form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throwLegalException(getBindingResultErrorMsg(bindingResult));
        }
        ProfitAndLossParams params = form.getParams(ProfitAndLossParams.class);
        ListResults result = lossService.profitAndLoss(params);
        return responseListResults(params, result);
    }

}
