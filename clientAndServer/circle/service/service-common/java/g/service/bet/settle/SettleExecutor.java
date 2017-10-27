package g.service.bet.settle;

import g.model.task.AbstractTask;
import g.service.bet.ISettleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by longer on 7/7/16.
 * 结算执行器
 */
public class SettleExecutor implements ISettleExecutor {

    @Autowired
    private ThreadPoolTaskExecutor settleThreadPool;

    @Autowired
    private ISettleService settleService;

    @Override
    public void submit(AbstractTask task) {
        ISettleServiceWrapper wrapper = (ISettleServiceWrapper)task;
        wrapper.setSettleService(settleService);
        settleThreadPool.execute(task);
    }


}
