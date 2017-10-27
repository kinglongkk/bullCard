package g.service.exception;

/**
 * Created by longer on 7/16/16.
 * 结果为空异常
 */
public class NullResultException extends Exception {

    private static final long serialVersionUID = -3226636430527460091L;

    public NullResultException() {
        super();
    }

    public NullResultException(String message) {
        super(message);
    }

    public NullResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullResultException(Throwable cause) {
        super(cause);
    }

    protected NullResultException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
