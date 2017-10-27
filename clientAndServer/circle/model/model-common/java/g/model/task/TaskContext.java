package g.model.task;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by longer on 7/5/16.
 */
public class TaskContext {

    private ConcurrentHashMap resultGatherTasks = new ConcurrentHashMap();

    public void put(AbstractTask task) {
        resultGatherTasks.putIfAbsent(task.getId(),task);
    }

    public boolean isContain(AbstractTask task) {
        return resultGatherTasks.containsKey(task.getId());
    }

    public boolean isContain(Long taskId) {
        return resultGatherTasks.containsKey(taskId);
    }

    public void remove(AbstractTask task){
        resultGatherTasks.remove(task.getId());
    }

}
