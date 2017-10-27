package g.service.bet.settle;

import g.model.task.AbstractTask;

/**
 * Created by longer on 7/7/16.
 */
public interface ISettleExecutor {

    /**
     * 提交任务
     * @param task
     */
    void submit(AbstractTask task);

}
