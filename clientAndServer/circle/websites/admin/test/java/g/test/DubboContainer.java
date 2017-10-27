package g.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by longer on 3/26/15.
 */

public class DubboContainer {

    AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext(
            "classpath*:conf/soul-commons*.xml"
            , "classpath*:conf/soul-dubbo-*.xml"
            , "classpath*:conf/test-soul-temp.xml"
    );

    public void start() {
        applicationContext.start();
    }

    public void stop() {
        applicationContext.stop();
    }

    public static void main(String[] args) {
        DubboContainer startup = new DubboContainer();
        startup.start();
    }
}
