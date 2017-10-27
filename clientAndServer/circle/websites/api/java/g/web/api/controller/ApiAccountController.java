package g.web.api.controller;

import g.api.service.*;
import g.model.api.param.*;
import g.model.api.result.Results;
import g.model.api.result.WithdrawResults;
import g.web.api.model.ApiRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 玩家账户交易
 * Created by black on 2016/11/14.
 */
@RestController
@RequestMapping("/account")
public class ApiAccountController extends ApiController {

    @Autowired
    private IGameDepositService depositService;

    @Autowired
    private IGameWithdrawService withdrawService;

    @Autowired
    private IGameConfirmTransactionService confirmTransactionService;

    @Autowired
    private IGameCheckTransferService checkTransferService;

    @Autowired
    private IGameFetchBalanceService fetchBalanceService;

    /**
     * 预存款（转账到游戏平台）
     * RMB：GO 1:100
     * @param form
     * @param bindingResult
     * @return result
     */
    @RequestMapping("/prepare/deposit")
    public Object deposit(@Valid ApiRequest<DepositParams> form, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throwLegalException(getBindingResultErrorMsg(bindingResult));
        }
        DepositParams params = form.getParams(DepositParams.class);
        //交易订单生成
        Results result = depositService.deposit(params);
        return responseResults(params, result);
    }

    /**
     * 预取款
     * RMB：GO 1:100
     * @param form
     * @param bindingResult
     * @return result
     */
    @RequestMapping("/prepare/withdraw")
    public Object withdraw(@Valid ApiRequest<WithdrawParams> form, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            throwLegalException(getBindingResultErrorMsg(bindingResult));
        }
        WithdrawParams params = form.getParams(WithdrawParams.class);
        Results result = withdrawService.withdraw(params);
        //将信息发送给客户端
        WithdrawResults withdrawResults = (WithdrawResults)result.getFinalResults();
        withdrawService.sendWithdrawMessage(withdrawResults.getTransactionNo());
        return responseResults(params, result);
    }

    /**
     * 确认转账
     * @param form
     * @param bindingResult
     * @return result
     */
    @RequestMapping("/confirm/transaction")
    public Object confirm (@Valid ApiRequest<ConfirmTransactionParams> form, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            throwLegalException(getBindingResultErrorMsg(bindingResult));
        }
        ConfirmTransactionParams params = form.getParams(ConfirmTransactionParams.class);
        Results result = confirmTransactionService.confirm(params);
        return responseResults(params, result);
    }

    /**
     * 检查转账状态
     * @param form
     * @param bindingResult
     * @return result
     */
    @RequestMapping("/check/transfer")
    public Object checkTransfer(@Valid ApiRequest<CheckTransferParams> form, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            throwLegalException(getBindingResultErrorMsg(bindingResult));
        }
        CheckTransferParams params = form.getParams(CheckTransferParams.class);
        Results result = checkTransferService.checkTransfer(params);
        return responseResults(params, result);
    }

    /**
     * 获取游戏平台的账户余额
     * @param form
     * @param bindingResult
     * @return result
     */
    @RequestMapping("/balances")
    public Object fetchBalance(@Valid ApiRequest<FetchBalanceParams> form, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            throwLegalException(getBindingResultErrorMsg(bindingResult));
        }
        FetchBalanceParams params = form.getParams(FetchBalanceParams.class);
        Results result = fetchBalanceService.fetchBalance(params);
        return responseResults(params, result);
    }

}
