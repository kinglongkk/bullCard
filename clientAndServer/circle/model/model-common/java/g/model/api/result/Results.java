package g.model.api.result;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by tony on 2016/11/25.
 *
 */
public abstract class Results implements Serializable {

    /**
     * 附加结果
     */
    @JsonIgnore
    private Map<String, ?> additionalResult;

    public Map<String, ?> getAdditionalResult() {
        return additionalResult;
    }

    public void setAdditionalResult(Map<String, ?> additionalResult) {
        this.additionalResult = additionalResult;
    }

    @JsonIgnore
    public abstract Object getFinalResults();

}
