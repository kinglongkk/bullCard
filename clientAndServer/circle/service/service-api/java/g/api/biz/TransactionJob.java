package g.api.biz;

import g.api.service.IGamePlayerTransactionService;
import g.model.TransactionStatusEnum;
import g.model.TransactionTypeEnum;
import g.model.player.po.PlayerTransaction;
import g.model.player.vo.PlayerTransactionListVo;
import g.service.common.IPlayerTransactionService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 检查玩家交易调度器
 * Created by black on 2017/3/29.
 */
public class TransactionJob extends QuartzJobBean {

    @Autowired
    private IPlayerTransactionService transactionService;

    @Autowired
    private IGamePlayerTransactionService playerTransactionService;

    private Log log = LogFactory.getLog(TransactionJob.class);

    public void run() throws Exception{
        executeInternal(null);
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        PlayerTransactionListVo listVo = new PlayerTransactionListVo();
        listVo.getQuery().setCriterions(new Criterion[]{
            new Criterion(PlayerTransaction.PROP_STATUS, Operator.EQ, TransactionStatusEnum.WAITING.getCode())
        });
        listVo = transactionService.search(listVo);
        if (listVo.getResult() != null && !listVo.getResult().isEmpty()) {
            for (int i = 0, length = listVo.getResult().size(); i < length; i++) {
                PlayerTransaction transaction = listVo.getResult().get(i);
                playerTransactionService.sendTransactionMessage(transaction);
            }
        }
        log.info("检查玩家交易调度器……");
    }
}
