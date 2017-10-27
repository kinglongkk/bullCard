package g.service.webSocket.codeCreator;

import g.service.webSocket.Msg;
import g.service.webSocket.context.WsSession;

import java.util.*;

/**
 * 网络传输实体对象工具类,通过代码注入实现读写网络对象.
 *
 * @author MK.
 * @date 2016-8-28
 */
public class NetBeanTool {
    public static HashMap<Class, Integer> events;// = new HashMap<>();
    public static HashMap<Integer, Class> classes;
    public static HashMap<Class, HandleInfo> handles;
    public static HashMap<Class, String> numTypes = new HashMap<>();//基础数据类型映射

    static {
        numTypes.put(Long.class, "long");
        numTypes.put(Integer.class, "int");
        numTypes.put(Short.class, "short");
        numTypes.put(Byte.class, "byte");
    }

    public static void eventAssign(int $1, Object $2, WsSession $3) {
    }

    public static Object readObject(Msg $1, Class $2) throws Exception {//event = (int)$1.getLong();
        return null;
    }

    public static void writeObject(Msg $1, Object $2, boolean writeEvent) {

    }
}
