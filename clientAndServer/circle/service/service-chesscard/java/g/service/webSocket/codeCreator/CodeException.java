package g.service.webSocket.codeCreator;

/**
 * 解析NetBean网络组件包,代码生成时的异常
 */
public class CodeException extends RuntimeException {
    public CodeException(String error) {
        super(error);
    }
}
