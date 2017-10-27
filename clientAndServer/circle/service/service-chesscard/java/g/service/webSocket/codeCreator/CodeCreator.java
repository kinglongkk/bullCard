package g.service.webSocket.codeCreator;

import g.service.webSocket.Msg;
import g.service.webSocket.context.WsSession;
import javassist.*;
import javassist.bytecode.AccessFlag;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 往NetBeanTool代码注入实现读写网络对象.
 *
 * @author MK.
 * @date 2016-8-28
 */
public class CodeCreator {

    private static Log log = LogFactory.getLog(CodeCreator.class);
    HashMap<Class, String> numTypes = new HashMap<>();//基础数据类型映射
    HashMap<String, Class> numClasses = new HashMap<>();//基础数据类型映射
    ClassPool pool = ClassPool.getDefault();//pool.importPackage("import g.service.chesscard.netBeans.*");
    CtClass typeList;
    CtClass typeMap;
    //处理所有类
    ArrayList<String> classNames;
    HashMap<Class, Integer> events = new HashMap();
    HashMap<Integer, Class> classes = new HashMap();
    StringBuilder sWrite = new StringBuilder();
    StringBuilder sRead = new StringBuilder();
    StringBuilder sAssign = new StringBuilder();
    ArrayList<Info> infos = new ArrayList();
    int eventCnt = 0;
    HashMap<Class, HandleInfo> handles = new HashMap<>();
    String netBeanPackage;
    String netHandelPackage;
    /**
     * 是否是服务端,默认是
     */
    boolean isServer = true;
    private static final String javaUtilPackage = "java.util";
    private static final String javaLangPackage = "java.lang";
    /**
     * 单数据类型
     */
    private static final int TYPE_SINGLE = 0;
    private static final int TYPE_ARRAY = 1;
    private static final int TYPE_LIST = 2;
    private static final int TYPE_MAP = 3;

    public CodeCreator(String netBeanPackage, String netHandelPackage, boolean isServer) {
        this.netBeanPackage = netBeanPackage;
        this.netHandelPackage = netHandelPackage;
        this.isServer = isServer;
        numTypes.put(Long.class, "long");
        numTypes.put(Integer.class, "int");
        numTypes.put(Short.class, "short");
        numTypes.put(Byte.class, "byte");
        numTypes.put(Boolean.class, "boolean");
        numClasses.put("long", Long.class);
        numClasses.put("int", Integer.class);
        numClasses.put("short", Short.class);
        numClasses.put("byte", Byte.class);
        numClasses.put("boolean", Boolean.class);
        sWrite.append("int event = ((Integer)events.get($2.getClass())).intValue();if(writeEvent)$1.putLong(event);$1.markByte();" +
                "int fieldCount=0;switch(event){");//switch event
        sRead.append("int fieldCount = $1.getByte();int event = ((Integer) events.get($2)).intValue();switch (event){");//switch event
        create();
    }

    private void create() {
        try {
            typeList = pool.get("java.util.List");
            typeMap = pool.get("java.util.Map");
            //1.注册网络通讯组件
            classNames = KFile.getClassNames(netBeanPackage);
            checkSettedEvent();
            addNetBeans(classNames);
            //2.注册网络处理类
            classNames = KFile.getClassNames(netHandelPackage);
            addHandles(classNames);
            codeHandle(handles);
            //4.注入代码生成读取写入消息处理类
            codeNetBeanTool();
        } catch (Exception e) {
            log.error(e, "网络系统处理解析出错!");
            System.exit(1);
        }
    }

    private void addNetBeans(ArrayList<String> classNames) throws Exception {
        for (String className : classNames) {
            CtClass cc = pool.get(className);
            int event = setEvent(className);
            Info info = addBeforeFields(event, cc);
            ArrayList<CtField> fields = new ArrayList<>();
            getClassFields(fields, cc);
            int[] fieldIndexes = getFieldIndexes(fields);
            for (int i = 0; i < fields.size(); i++) {
                CtField cf = fields.get(i);
                if ((cf.getModifiers() & AccessFlag.STATIC) != 0 || (cf.getModifiers() & AccessFlag.PRIVATE) != 0) {
                    continue;//静态变量和私有变量不解析
                }
                FieldInfo fi = getFieldInfo(cf, fieldIndexes[i]);
                codeField(fi, cf);
                info.add(fi);
            }
            sWrite.append("break;}");// end case event
            sRead.append("default:break;}}return b;}");// end switch field, for field, case event
        }
    }

    private void getClassFields(ArrayList<CtField> fields, CtClass cc) throws Exception {
        CtField[] fs = cc.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            CtField field = fs[i];
            int modifier = field.getModifiers();
            if ((modifier & AccessFlag.STATIC) != 0 || (modifier & AccessFlag.PRIVATE) != 0) {
                continue;//静态变量和私有变量不解析
            }
            if (!isPrimitiveOrNetBean(field.getType())) {
                continue;//对象不是基础类型或者netBean
            }
            fields.add(fs[i]);
        }
        cc = cc.getSuperclass();
        if (cc.getPackageName().startsWith(netBeanPackage)) {//继承自netBean
            getClassFields(fields, cc);
        }
    }

    private boolean isPrimitiveOrNetBean(CtClass cls) {
        if (cls.isPrimitive()) return true;
        String packageName = cls.getPackageName();
        return packageName == null || packageName.startsWith(javaUtilPackage)
                || packageName.startsWith(javaLangPackage) || packageName.startsWith(netBeanPackage);
    }

    private void addHandles(ArrayList<String> classNames) throws Exception {
        Object handle;
        for (String className : classNames) {
//                CtClass cc = pool.get(className);
            Class cls = Class.forName(className);
            Method[] methods = cls.getDeclaredMethods();
            forMethod:
            for (Method m : methods) {
                Class[] parameterTypes = m.getParameterTypes();
                if ((m.getModifiers() & AccessFlag.PUBLIC) == 0) {//方法必须为public类型
                    continue;
                }
                if (parameterTypes.length != 2 || parameterTypes[0] != WsSession.class || events.get(parameterTypes[1]) == null) {
                    continue;
                }
                if (handles.get(parameterTypes[1]) != null) {
                    throw new CodeException("处理请求事件方法重复:" + m.toString() + "，" + handles.get(parameterTypes[1]).method);
                }
                handles.put(parameterTypes[1], new HandleInfo(cls, m));
            }
        }
        //检查每个从客户端发送的消息网络组件有被handle处理
        for (Map.Entry<Class, Integer> e : events.entrySet()) {
            int event = e.getValue();
            Class cls = e.getKey();
            for (Info i : infos) {
                if (i.event == event) {
                    if ((isServer && (i.sendBy & NetBean.SENDBY_CLIENT) != 0) && handles.get(cls) == null) {
                        throw new CodeException("在netHandles包下必须有方法处理会接收到的网络组件类:public void xx(WsSession xx,"
                                + cls.getName() + " obj)");
                    }
                    break;
                }
            }
        }
    }

    private void codeHandle(HashMap<Class, HandleInfo> infos) {
        sAssign.append("switch ($1){");
        for (Map.Entry<Class, HandleInfo> e : infos.entrySet()) {
            HandleInfo hi = e.getValue();
            sAssign.append("case ").append(events.get(e.getKey())).append(":{").append("((")
                    .append(hi.handle.getName()).append(")(org.soul.commons.spring.utils.SpringTool.getBean(((g.service.webSocket.codeCreator.HandleInfo)handles.get($2.getClass())).handle))).")
                    .append(hi.method.getName()).append("($3,(").append(e.getKey().getName()).append(") $2);").append("break;}");
        }
        sAssign.append("default:break;}");
    }

    private void codeNetBeanTool() throws Exception {
        CtClass netBeanTool = pool.get("g.service.webSocket.codeCreator.NetBeanTool");
        CtMethod writeObjectMethod = netBeanTool.getDeclaredMethods("writeObject")[0];
        CtMethod readObjectMethod = netBeanTool.getDeclaredMethods("readObject")[0];
        CtMethod assignMethod = netBeanTool.getDeclaredMethods("eventAssign")[0];
        sWrite.append("default:break;}$1.putByteOnMark(fieldCount);");//end switch
        sRead.append("default:break;}");//end field switch
        log.info("write: " + sWrite.toString());
        log.info("read: " + sRead.toString());
        log.info("assign: " + sAssign.toString());
        writeObjectMethod.insertBefore(sWrite.toString());
        readObjectMethod.insertBefore(sRead.toString());
        assignMethod.insertBefore(sAssign.toString());
        netBeanTool.toClass();
        NetBeanTool.events = events;
        NetBeanTool.classes = classes;
        NetBeanTool.handles = handles;
    }

    private FieldInfo getFieldInfo(CtField cf, int fieldIndex) throws Exception {
        CtClass ftype = cf.getType();
        NetBeanField nbf = (NetBeanField) cf.getAnnotation(NetBeanField.class);
        boolean primitive = ftype instanceof CtPrimitiveType || (ftype.isArray() && ftype.getName().indexOf(".") == -1);
        int collectionType = TYPE_SINGLE;
        if (ftype.subtypeOf(typeList)) collectionType = TYPE_LIST;
        else if (ftype.subtypeOf(typeMap)) collectionType = TYPE_MAP;
        boolean required = !(nbf != null && !nbf.required());
        String clsName = ftype.getName();
        if (ftype.isArray()) {
            collectionType = TYPE_ARRAY;
            clsName = clsName.substring(0, clsName.indexOf("["));
        }
        Class cls = getFieldClass(cf, nbf, collectionType, ftype, primitive, fieldIndex, required, clsName);
        int dataType = getDataType(cls);//0数字 1字符串 2对象
        FieldInfo fi = new FieldInfo(fieldIndex, cf.getName(), cls, dataType, required, primitive,
                collectionType, clsName);
        return fi;
    }

    private int getDataType(Class cls) {
        int dataType = 0;
        if (cls.getName().startsWith("java.lang.")) {
            if (cls.getName().endsWith(".String")) {
                dataType = 1;
            } else if (cls.getName().endsWith(".Boolean")) {
                dataType = 2;
            }
        } else {
            dataType = 8;
        }
        return dataType;
    }

    private int[] getFieldIndexes(ArrayList<CtField> fs) throws Exception {
        int[] fieldIndexes = new int[fs.size()];
        int index = 0;
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < fs.size(); i++) {
            ids.add(fs.size() - i - 1);
            fieldIndexes[i] = -1;
        }
        for (int i = 0; i < fs.size(); i++) {
            NetBeanField nbf = (NetBeanField) fs.get(i).getAnnotation(NetBeanField.class);
            if (nbf != null && nbf.index() >= 0) {
                fieldIndexes[i] = nbf.index();
                ids.remove(new Integer(nbf.index()));
            }
        }
        for (int i = 0; i < fs.size(); i++) {
            if (fieldIndexes[i] == -1) {
                fieldIndexes[i] = ids.remove(ids.size() - 1);
            }
        }
        return fieldIndexes;
    }

    private void codeField(FieldInfo fi, CtField cf) {
        if (fi.required) {
            if (fi.collectionType != TYPE_SINGLE || !fi.primitive) {
                sWrite.append("if(b.").append(fi.name).append("==null)throw new g.service.webSocket.codeCreator.CodeException(\"该变量定义是必须传输,不可为null,").append(cf.toString()).append("\");");
            }
            sWrite.append("if(true");
        } else {
            sWrite.append("if(b.").append(fi.name).append("!=null");
        }
        sWrite.append("){fieldCount++;$1.putLong(").append(fi.index).append(");");//fieldIndex
        sRead.append("case ").append(fi.index).append(":{");//case field

        if (fi.collectionType != TYPE_SINGLE) {//arrayLen
            sWrite.append("int len=b.").append(cf.getName());
            sRead.append("int len=(int) $1.getLong();").append("if(b.").append(cf.getName()).append("==null");
            if(fi.collectionType == TYPE_ARRAY)sRead.append("||b.").append(cf.getName()).append(".length!=len");
            sRead.append(")b.").append(cf.getName()).append("= new ");
            switch (fi.collectionType) {
                case TYPE_ARRAY: {
                    sWrite.append(".length;$1.putLong(len);");
                    String dataType = fi.primitive ? numTypes.get(fi.cls) : fi.cls.getName();
                    sWrite.append("for(int j=0;j<len;j++){").append(dataType)
                            .append(" i=b.").append(cf.getName()).append("[j];");
                    sRead.append(dataType).append("[len];");
                    sRead.append("for (int j = 0; j < len; j++) {b.").append(cf.getName()).append("[j]=(");
                }
                break;
                case TYPE_LIST: {
                    sWrite.append(".size();$1.putLong(len);");
                    String dataType = fi.cls.getName();
                    sWrite.append("for(int j=0;j<len;j++){").append(dataType).append(" i=(")
                            .append(dataType).append(")b.").append(cf.getName()).append(".get(j);");
                    sRead.append("java.util.ArrayList(len);");
                    sRead.append("for (int j = 0; j < len; j++) {b.").append(cf.getName()).append(".add(");
                }
                break;
                case TYPE_MAP: {
                    sWrite.append(".size();$1.putLong(len);");
                    String dataType = fi.cls.getName();
                    sWrite.append("java.util.Iterator it=b.").append(cf.getName())
                            .append(".entrySet().iterator();while (it.hasNext()) {java.util.Map.Entry e = (java.util.Map.Entry)it.next();$1.putString(e.getKey().toString());")
                            .append(dataType).append(" i=(").append(dataType).append(")e.getValue();");
                    sRead.append("java.util.HashMap(len);");
                    sRead.append("for (int j = 0; j < len; j++) {b.").append(cf.getName()).append(".put($1.getString(),");
                }
                break;
            }
            switch (fi.dataType) {
                case 0: {//数字
                    sWrite.append("$1.putLong(i);");
                    if (!fi.primitive) {
                        sRead.append("new ").append(fi.cls.getSimpleName());
                    }
                    sRead.append("((").append(numTypes.get(fi.cls)).append(")$1.getLong())");
                    break;
                }
                case 8: {//对象
                    sWrite.append("writeObject($1,i,false);");
                    sRead.append("(").append(fi.cls.getName()).append(")readObject($1,").append(fi.cls.getName()).append(".class)");
                    break;
                }
                default: {//字符串,Boolean 等
                    sWrite.append("$1.put").append(fi.cls.getSimpleName()).append("(i);");
                    sRead.append("$1.get").append(fi.cls.getSimpleName()).append("()");
                    break;
                }
            }
            sWrite.append("}");//end for array
            sRead.append(");}");//end for array
        } else {
            switch (fi.dataType) {
                case 0: {//数字 CtPrimitiveType ptype = (CtPrimitiveType)ftype;ptype.getName();
                    sWrite.append("$1.putLong(b.").append(cf.getName()).append(");");
                    sRead.append("b.").append(cf.getName()).append("=");
                    if (!fi.primitive) {
                        sRead.append("new ").append(fi.cls.getSimpleName());
                    }
                    sRead.append("((").append(numTypes.get(fi.cls)).append(")$1.getLong());");
                    break;
                }
                case 8: {//对象
                    sWrite.append("writeObject($1,b.").append(cf.getName()).append(",false);");
                    sRead.append("b.").append(cf.getName()).append("=(").append(fi.cls.getName()).append(")readObject($1,")
                            .append(fi.cls.getName()).append(".class);");
                    break;
                }
                default: {//字符串,Boolean 等
                    sWrite.append("$1.put").append(fi.clsName).append("(b.").append(cf.getName()).append(");");
                    sRead.append("b.").append(cf.getName()).append("=$1.get").append(fi.clsName).append("();");
                    break;
                }
            }
        }
        sWrite.append("}");//if(xx!=null) end
        sRead.append("break;}");//end case field
    }

    private Class getFieldClass(CtField cf, NetBeanField nbf, int collectionType, CtClass ftype, boolean primitive, int fieldIndex, boolean required, String clsName) throws Exception {
        if (collectionType > TYPE_ARRAY && (nbf == null || nbf.collectionClass() == Object.class)) {
            throw new CodeException("集合List或Map变量必须指定NetBeanField的cls属性class," + cf.toString());
        }
        if (ftype instanceof CtPrimitiveType) {//原始数字数据类型,必须有
            if (!required) {//原始数字类型是必须的
                throw new CodeException("原始数字类型变量的注解不能是非必须的," + cf.toString());
            }
        }
        if (collectionType > TYPE_ARRAY) {
            return nbf.collectionClass();
        }
        Class cls = null;
        if (primitive) {
            return numClasses.get(clsName);
        } else {
            return Class.forName(clsName);
        }
    }

    private Info addBeforeFields(int event, CtClass cc) throws ClassNotFoundException {
        NetBean nb = (NetBean) cc.getAnnotation(NetBean.class);
        int sendBy = nb == null ? NetBean.SENDBY_NONE : nb.sendBy();
        Info info = new Info(event, cc.getSimpleName(), sendBy);
        infos.add(info);
        sWrite.append("case ").append(event).append(":{");//case event
        sRead.append("case ").append(event).append(":{");//case event
        sWrite.append(cc.getName()).append(" b=(").append(cc.getName()).append(")$2;");
        sRead.append(cc.getName()).append(" b=(").append(cc.getName()).append(")((Class)$2).newInstance();");
        sRead.append("for (int i = 0; i <fieldCount; i++) {");//for field
        sRead.append("int fieldIndex = (int) $1.getLong();switch (fieldIndex){");//switch field
        return info;
    }

    private int setEvent(String className) throws Exception {
        Integer event = null;
        Class cls = Class.forName(className);
        if ((event = events.get(cls)) != null) {
            if (eventCnt == event) eventCnt++;
            return event;
        }
        while (classes.get(eventCnt) != null) {
            eventCnt++;
        }
        events.put(cls, eventCnt);
        classes.put(eventCnt, cls);
        return eventCnt++;
    }

    /**
     * 检查是否有设置注解event
     */
    private void checkSettedEvent() throws Exception {
        for (String className : classNames) {
            CtClass cc = pool.get(className);
            NetBean nb = (NetBean) cc.getAnnotation(NetBean.class);
            if (nb == null || nb.event() < 0) {
                continue;
            }
            events.put(Class.forName(className), nb.event());
            classes.put(nb.event(), Class.forName(className));
        }
    }

    public Msg getJson() {
        Msg jsonMsg = new Msg(4096);
        jsonMsg.putLong(-1);
        jsonMsg.putLong(infos.size());
        for (Info info : infos) {
            jsonMsg.putLong(info.event);
            jsonMsg.putString(info.name);
            jsonMsg.putLong(info.sendBy);
            jsonMsg.putLong(info.fields.size());
            for (int i = 0; i < info.fields.size(); i++) {
                FieldInfo fi = info.fields.get(i);
                if (fi.dataType == 8) fi.dataType = (events.get(fi.cls) + 8);
                jsonMsg.putLong(fi.index);
                jsonMsg.putLong(fi.required ? 1 : 0);
                jsonMsg.putString(fi.name);
                jsonMsg.putLong(fi.collectionType);
                jsonMsg.putLong(fi.dataType);
            }
        }
        return jsonMsg;
    }

    class FieldInfo {
        int index;
        String name;
        Class cls;
        int dataType;
        boolean required;
        boolean primitive;
        int collectionType;
        String clsName;

        FieldInfo(int index, String name, Class cls, int dataType, boolean required, boolean primitive, int collectionType,
                  String clsName) {
            this.index = index;
            this.name = name;
            this.cls = cls;
            this.dataType = dataType;
            this.required = required;
            this.primitive = primitive;
            this.collectionType = collectionType;
            int idx = clsName.lastIndexOf(".");
            if (idx == -1) this.clsName = clsName;
            else this.clsName = clsName.substring(idx + 1);
        }


    }

    class Info {
        int event;
        String name;
        int sendBy;
        ArrayList<FieldInfo> fields = new ArrayList<>();

        Info(int event, String name, int sendBy) {
            this.event = event;
            this.name = name;
            this.sendBy = sendBy;
        }

        void add(FieldInfo fi) {
            fields.add(fi);
        }
    }

}
