package g;

import g.model.gameroom.po.PlayerAiControl;
import g.service.gameroom.PlayerAiControlService;
import org.soul.commons.spring.utils.SpringTool;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * Created by MK on 2017/3/8.
 */
public class TestAddControl {

    @SuppressWarnings("resource")
	public static void main(String args[]){
        /*ClassPathXmlApplicationContext app = */new ClassPathXmlApplicationContext("classpath:/conf/app-appCtx.xml");
        PlayerAiControlService service = (PlayerAiControlService) SpringTool.getBean("playerAiControlService");
        PlayerAiControl control = new PlayerAiControl();
        control.setId(8);
        control.setRoomId(7);
        control.setChipRates("1,1,1,1");
        control.setGameModel(3);
        control.setStatus("10");
        control.setAiQty(130);//进出人数
        control.setControlMode("1");
        control.setBringGoldMin(4000000);
        control.setBringGoldMax(5000000);
        control.setIntervalMinTime(100);
        control.setIntervalMaxTime(300);
        control.setLeaveMinTime(1000);
        control.setLeaveMaxTime(1000);
        control.setRestMinGames(5);
        control.setRestMaxGames(8);
        control.setRoomMaxQty(150);
        control.setBeginControlTime(new Date());
        control.setControlCycle(6000);
        control.setBetCount(10);
        service.publishAiPlayerMessage(control);
    }
}
