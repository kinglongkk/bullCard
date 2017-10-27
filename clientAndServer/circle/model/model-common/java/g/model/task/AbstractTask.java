package g.model.task;

/**
 * Created by longer on 7/5/16.
 */
public abstract class AbstractTask implements Runnable{

    protected TaskContext context;

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContext(TaskContext context) {
        this.context = context;
    }

}
