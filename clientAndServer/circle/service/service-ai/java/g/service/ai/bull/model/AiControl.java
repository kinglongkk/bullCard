package g.service.ai.bull.model;

import g.model.gameroom.po.PlayerAiControl;
import g.service.chesscard.util.Probability;

import java.util.Random;

/**
 * Created by MK on 2017/3/6.
 */
public class AiControl {

    private PlayerAiControl config;
    private Probability betRate;
    private Random random = new Random();

    public AiControl(PlayerAiControl control) {
        this.config = control;
        String[] rateStrs = control.getChipRates().split(",");
        int[] rates = new int[rateStrs.length];
        for (int i = 0; i < rateStrs.length; i++) {
            rates[i] = Integer.parseInt(rateStrs[i].trim());
        }
        betRate = new Probability(rates);
    }

    public long getCoinRandom(){
        return random.nextInt((int)(config.getBringGoldMax()- config.getBringGoldMin()+1))+ config.getBringGoldMin();
    }

    public int getLoginDelay(){
        return new Random().nextInt(config.getIntervalMaxTime()- config.getIntervalMinTime()+1) + config.getIntervalMinTime();
    }

    /** 按一定比率随机获取一个砝码值 */
    public int getBetChipIndex(){
        return betRate.nextIndex();
    }

    /** 获取一个玩家的这次登录的比赛场数 */
    public int getRandomMatchCount() {
        if(config.getRestMaxGames()==0){
            return Integer.MAX_VALUE;
        }
        return random.nextInt(config.getRestMaxGames()- config.getRestMinGames()+1)+ config.getRestMinGames();
    }

    public int getExitDelay(){
        return new Random().nextInt(config.getLeaveMaxTime()- config.getLeaveMinTime()+1)+ config.getLeaveMinTime();
    }

    public Integer getRoomId() {
        return config.getRoomId();
    }

    public PlayerAiControl getConfig() {
        return config;
    }
}
