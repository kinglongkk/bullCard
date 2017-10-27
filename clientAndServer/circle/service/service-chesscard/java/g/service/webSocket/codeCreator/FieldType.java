package g.service.webSocket.codeCreator;

/**
 * Created by LENOVO on 2016/8/29.
 */
public class FieldType {

    boolean array = false;
    Class cls;

    FieldType(boolean array, Class cls) {
        this.array = array;
        this.cls = cls;
    }
}
