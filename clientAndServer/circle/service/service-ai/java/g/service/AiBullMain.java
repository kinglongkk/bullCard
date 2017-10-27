package g.service;

import g.service.ai.bull.service.AiService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by LENOVO on 2016/12/20.
 */
public class AiBullMain {

    public static void main(String args[]) throws Exception {
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("classpath:/conf/app-appCtx.xml");
        AiService mng = (AiService) app.getBean(AiService.class);
        mng.start();
//        session.sendNetBean(login);
//        client.conectToServer();
    }
}
