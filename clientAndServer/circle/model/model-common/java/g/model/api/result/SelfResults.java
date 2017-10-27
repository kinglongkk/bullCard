package g.model.api.result;

/**
 * Created by tony on 2016/11/29.
 * 返回自身成员
 */
public class SelfResults extends Results {

    @Override
    public Object getFinalResults() {
        return this;
    }
}
