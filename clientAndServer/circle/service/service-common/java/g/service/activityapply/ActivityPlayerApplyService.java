package g.service.activityapply;

import g.service.common.IUserPlayerService;
import org.soul.commons.query.Paging;
import org.soul.service.support.BaseService;
import g.data.activityapply.ActivityPlayerApplyMapper;
import g.model.activityapply.po.ActivityPlayerApply;
import g.model.activityapply.vo.ActivityPlayerApplyListVo;
import g.model.activityapply.vo.ActivityPlayerApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;


/**
 * 活动申请玩家表服务
 *
 * @author black
 * @time 2016-9-18 19:10:58
 */
//region your codes 1
public class ActivityPlayerApplyService extends BaseService<ActivityPlayerApplyMapper, ActivityPlayerApplyListVo, ActivityPlayerApplyVo, ActivityPlayerApply, Integer> implements IActivityPlayerApplyService {

    @Autowired
    private IUserPlayerService userPlayerService;

    public ActivityPlayerApplyListVo selectActivityPlayerApplyInfoByGroup(ActivityPlayerApplyListVo listVo) {

        Map<String, Object> queryParams = listVo.getQueryParams();
        List<ActivityPlayerApply> playerApplyList = this.mapper.selectActivityPlayerApplyInfoByGroup(queryParams);

        //分页操作
        Paging paging = listVo.getPaging();
        if (paging != null) {

            paging.setTotalCount(this.mapper.selectTotalRecordByGroup(queryParams));
            paging.cal();
        }
        listVo.setResult(playerApplyList);

        //本站玩家总数
        double totalNumber = userPlayerService.selectUserPlayerNumber();
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        for (ActivityPlayerApply apply : listVo.getResult()){

            apply.setJoinScale(percentFormat.format(apply.getJoinNumber()/totalNumber));
        }
        return listVo;
    }

    public ActivityPlayerApplyListVo selectActivityPlayerApplyInfoByDetail(ActivityPlayerApplyListVo listVo) {

        Map<String, Object> queryParams = listVo.getQueryParams();
        List<ActivityPlayerApply> playerApplyList = this.mapper.selectActivityPlayerApplyInfoByDetail(queryParams);

        //分页操作
        Paging paging = listVo.getPaging();
        if (paging != null) {

            paging.setTotalCount(this.mapper.selectTotalRecordByDetail(queryParams));
            paging.cal();
        }
        listVo.setResult(playerApplyList);
        return listVo;
    }


    public boolean updateIsRealize(Integer applyId){

        return this.mapper.updateIsRealize(applyId);
    }


    @Transactional
    public boolean insertNewRecord(Map<String, Object> map){

        return  this.mapper.insertNewRecord(map);
    }

}