package g.service.announcement;

import g.model.announcement.po.GameAnnouncement;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.data.json.JsonTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.spring.utils.SpringTool;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springside.modules.nosql.redis.JedisTemplate;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.List;

/**
 * 游戏公告任务调度 执行任务
 * Created by black on 2016/12/27.
 */
public class StartGameAnnouncementJob extends QuartzJobBean {

    private Log log = LogFactory.getLog(StartGameAnnouncementJob.class);

    private String CHANNEL = "gameAnnouncement";

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        final GameAnnouncement gameAnnouncement = (GameAnnouncement) context.getMergedJobDataMap().get("gameAnnouncement");
        log.info("连接服务器，发送公告中……" + gameAnnouncement.getId());
        JedisTemplate template = (JedisTemplate) SpringTool.getBean("jedisTemplateGame");
        //依据不同的游戏在不同的频道发送消息
        final String gameIds = gameAnnouncement.getGameId();
        if (gameIds.contains(",")) {
            List gameArray = Arrays.asList(gameIds.split(","));
            for (int i = 0, length = gameArray.size(); i < length; i ++) {
                final Integer gameId = Integer.parseInt(gameArray.get(i).toString());
                template.execute(new JedisTemplate.JedisAction<Object>() {
                    @Override
                    public Object action(Jedis jedis) {
                        String channel = CacheKey.getCacheKey(CHANNEL, gameId);
                        jedis.publish(channel, JsonTool.toJson(gameAnnouncement));
                        log.info("发布消息" + JsonTool.toJson(gameAnnouncement) + "...");
                        return null;
                    }
                });
            }
        } else {
            //发布消息
            template.execute(new JedisTemplate.JedisAction<Object>() {
                @Override
                public Object action(Jedis jedis) {
                    String channel = CacheKey.getCacheKey(CHANNEL, Integer.parseInt(gameIds));
                    jedis.publish(channel, JsonTool.toJson(gameAnnouncement));
                    log.info("发布消息" + JsonTool.toJson(gameAnnouncement) + "...");
                    return null;
                }
            });
        }
    }

}