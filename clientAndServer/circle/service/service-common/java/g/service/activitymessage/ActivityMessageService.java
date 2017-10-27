package g.service.activitymessage;

import org.soul.service.support.BaseService;
import g.data.activitymessage.ActivityMessageMapper;
import g.model.activitymessage.po.ActivityMessage;
import g.model.activitymessage.vo.ActivityMessageListVo;
import g.model.activitymessage.vo.ActivityMessageVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 游戏信息表服务
 *
 * @author black
 * @time 2016-9-5 14:46:12
 */
//region your codes 1
public class ActivityMessageService extends BaseService<ActivityMessageMapper, ActivityMessageListVo, ActivityMessageVo, ActivityMessage, Integer> implements IActivityMessageService {
//endregion your codes 1


    @Transactional
    public boolean insertActivityMessageInfo(Map<String, Object> map){

        return this.mapper.insertActivityMessageInfo(map);
    }


    @Transactional
    public boolean updateActivityMessageInfo(Map<String, Object> map){

        return this.mapper.updateActivityMessageInfo(map);
    }


    public boolean updateMessageIsDeleted(Map<String, Object> map){

        return this.mapper.updateMessageIsDeleted(map);
    }


    public List<Map<String, Object>> selectCanJoinActivity(String gameType){

        return this.mapper.selectCanJoinActivity(gameType);
    }

}