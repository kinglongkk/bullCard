package g.service.webSocket.codeCreator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 网络传输实体对象变量
 *
 * @author MK.
 * @date 2016-8-28
 */
@Target(ElementType.FIELD)
public @interface NetBeanField {

    /**
     * 索引号
     */
    public int index() default -1;

    /**
     * 是否必须
     */
    public boolean required() default true;

    /**
     * 链表或Map泛型代表的数据类,必须是netBeans包下的类,或者String\Long\Int\Short\Byte
     */
    public Class collectionClass() default Object.class;
}