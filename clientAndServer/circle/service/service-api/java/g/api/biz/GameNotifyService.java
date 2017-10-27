package g.api.biz;

import g.api.base.BaseService;
import g.api.service.IGameNotifyService;
import g.model.admin.agent.game.po.UserAgentGame;
import g.model.admin.agent.game.vo.UserAgentGameListVo;
import g.model.admin.agent.message.po.UserAgent;
import g.model.announcement.vo.GameAnnouncementListVo;
import g.model.api.enums.ResultStatusEnums;
import g.model.api.param.GameNotifyParams;
import g.model.api.param.Params;
import g.model.api.result.ListResults;
import g.service.admin.agent.game.IUserAgentGameService;
import g.service.announcement.IGameAnnouncementService;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by black on 2016/12/20.
 */
public class GameNotifyService extends BaseService implements IGameNotifyService {

    @Autowired
    private IUserAgentGameService gameService;

    @Autowired
    private IGameAnnouncementService announcementService;

    public ListResults gameNotify(GameNotifyParams params) {

        ListResults result = new ListResults();
        if (!validatePreConditions(params)) {
            throwBusinessException(ResultStatusEnums.VALIDATE_PARAMS_FAILED);
        }
        UserAgent userAgent = getUserAgent(params.getMerchantNo());
        if (userAgent == null) {
            throwBusinessException(ResultStatusEnums.AGENT_NOT_EXISTS);
        }
        UserAgentGameListVo gameListVo = new UserAgentGameListVo();
        gameListVo.getQuery().setCriterions(new Criterion[]{

            new Criterion(UserAgentGame.PROP_AGENT_ID, Operator.EQ, userAgent.getAgentId()),
            new Criterion(UserAgentGame.PROP_GAME_ID, Operator.EQ, Integer.parseInt(params.getMerchantGameNo()))
        });
        gameListVo = gameService.search(gameListVo);
        if (gameListVo.getResult() == null || gameListVo.getResult().isEmpty() || gameListVo.getResult().size() < 1) {
            throwBusinessException(ResultStatusEnums.NO_GAME_ID);
        }
        List<Map<String, Object>> resultList = new ArrayList<>();
        GameAnnouncementListVo listVo = new GameAnnouncementListVo();
        List<Map<String, Object>> initList = announcementService.selectGameNotify(listVo);
        if (initList != null && !initList.isEmpty()) {
            for (int i = 0, length = initList.size(); i < length; i++) {
                String gameId = (String) initList.get(i).get("gameid");
                if (gameId != null && !StringTool.isBlank(gameId)) {
                    if (gameId.contains(",")) {
                        String[] ids = gameId.split(",");
                        List<String> idsList = Arrays.asList(ids);
                        if (idsList.contains(params.getMerchantGameNo())) {
                            initList.get(i).remove("gameid");
                            resultList.add(initList.get(i));
                        }
                    } else {
                        if (gameId.contains(params.getMerchantGameNo())) {
                            initList.get(i).remove("gameid");
                            resultList.add(initList.get(i));
                        }
                    }

                }
            }
        }
        result.setDatas(resultList);
        return result;
    }

    @Override
    public boolean validatePreConditions(Params params) {

        GameNotifyParams notifyParams = (GameNotifyParams) params;
        if (notifyParams.getMerchantGameNo() != null && !StringTool.isBlank(notifyParams.getMerchantGameNo())) {
            return true;
        }else {
            return false;
        }
    }

}
