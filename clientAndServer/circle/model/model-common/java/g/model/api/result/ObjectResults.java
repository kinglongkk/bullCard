package g.model.api.result;

/**
 * Created by tony on 2016/11/25.
 */
public class ObjectResults extends Results {

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public Object getFinalResults() {
        return data;
    }
}
