package g.web.api.exception;

import g.api.exception.BusinessException;

/**
 * Created by tony on 2016/11/29.
 */
public class ApiValidateException extends BusinessException {

    public ApiValidateException() {
    }

    public ApiValidateException(String coce, String desc, String error) {
        super(coce, desc, error);
    }
}
