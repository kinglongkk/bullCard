package g.service.announcement;

import g.model.announcement.po.GameAnnouncement;
import g.model.announcement.vo.GameAnnouncementListVo;
import g.model.announcement.vo.GameAnnouncementVo;
import g.model.enums.SendMsgOrderEnum;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.data.json.JsonTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.spring.utils.SpringTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springside.modules.nosql.redis.JedisTemplate;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by solo on 2017/3/29.
 *
 * 发送消息
 */
public class SendAnnouncementJob extends QuartzJobBean {
    private String CHANNEL = "gameAnnouncement";
    private Log log = LogFactory.getLog(StartGameAnnouncementJob.class);

    @Autowired
    private IGameAnnouncementService announcementService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        final GameAnnouncement gameAnnouncement = getNowSendMsg();
        if(gameAnnouncement != null) {
            JedisTemplate template = (JedisTemplate) SpringTool.getBean("jedisTemplateGame");
            final String ids = gameAnnouncement.getGameId();
            List idList = Arrays.asList(ids.split(","));
            for(int i=0; i<idList.size(); i++) {
                final int gameId = Integer.parseInt(idList.get(i).toString());
                template.execute(new JedisTemplate.JedisAction<Object>() {
                    @Override
                    public Object action(Jedis jedis) {
                        String channel = CacheKey.getCacheKey(CHANNEL, gameId);
                        jedis.publish(channel, JsonTool.toJson(gameAnnouncement));
                        log.info("发布消息===" + JsonTool.toJson(gameAnnouncement) + "...");
                        gameAnnouncement.setIsSend(1);
                        upSendState(gameAnnouncement);
                        return null;
                    }
                });
            }
        }

    }

    //保存发送状态
    public void upSendState(GameAnnouncement gameAnnouncement) {
        GameAnnouncementVo gameAnnouncementVo = new GameAnnouncementVo();
        gameAnnouncementVo.setResult(gameAnnouncement);
        announcementService.update(gameAnnouncementVo);
    }

    /**
     *
     * 获取当前时间点需要发送的消息
     *   先查找被动生成的消息
     *   如果消息为空再查找后台的消息
     */
    public GameAnnouncement getNowSendMsg() {
        //获取当前时间需要发送的被动产生的消息
        Date nowDt = new Date();
        int isSend = 0;
        String msgType = "BACKSTAGE";
        GameAnnouncementListVo listVo = new GameAnnouncementListVo();
        listVo = announcementService.selectPassiveMsg(nowDt,isSend,msgType);

        if(null != listVo.getResult() && listVo.getResult().size() > 0) {
            return filterList(listVo.getResult());
        }
        return null;
    }



    /**
     * 排列当前时间节点的信息
     * @param list     查询到的消息集合
     * @return    当前需要发送的消息
     */
    public GameAnnouncement filterList (List<GameAnnouncement> list) {
        GameAnnouncement gameAnnouncement = new GameAnnouncement();
        List<GameAnnouncement> sendList = null;
        if(list.size() == 1) {
            return list.get(0);
        }else {
            String[] orders = getMsgTypeOrder();
            if (orders.length > 0) {
                String typeName = "";       //消息类型名称
                //按类别先后顺序筛选
                for(int i = 0; i<orders.length;i++) {
                    typeName = orders[i];
                    for (GameAnnouncement ga : list) {
                        if(sendList == null){
                            sendList = new ArrayList<GameAnnouncement>();
                        }
                        if(typeName.equals(ga.getMsgType())) {
                            sendList.add(ga);
                        }
                    }
                    if(sendList.size() > 0) {
                        break;
                    }
                }

                //按类别下的排序条件筛选
                if(sendList != null && sendList.size() == 1) {
                    return sendList.get(0);
                }else {
                    switch (typeName){
                        case "WINGAME":   //赢的比赛的公告
                            gameAnnouncement = orderByWinGame(sendList);
                            break;
                    }
                    return gameAnnouncement;
                }
            }
        }
        return null;
    }

    //排序赢得比赛的消息
    private GameAnnouncement orderByWinGame (List<GameAnnouncement> list){
        GameAnnouncement gameAnnouncement = null;
        if(list != null && list.size() > 0){
            for(GameAnnouncement ga : list) {
                if(null == gameAnnouncement) {
                    gameAnnouncement = ga;
                }
                if(ga.getGainGoldNum() > gameAnnouncement.getGainGoldNum()) {
                    gameAnnouncement = ga;
                }
            }
        }
        return gameAnnouncement;
    }


    /**
     * 获取消息类型的排序
     * @return
     */
    private String[] getMsgTypeOrder() {
        int sendTypeNum = SendMsgOrderEnum.values().length;        //获取发送消息类型数量
        String[] orders = new String[sendTypeNum];
        for(int i = 1; i <= sendTypeNum; i++) {
            for(SendMsgOrderEnum smoe : SendMsgOrderEnum.values()) {
                if(i == smoe.getOrder()) {
                    orders[i] = smoe.getTypeName();
                }
            }
        }
        return orders;
    }




}
