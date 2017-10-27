package g.service.playerstatistics;


import g.common.tool.DateTimeTool;
import g.data.playerstatistics.PlayerDataStatisticsMapper;
import g.model.game.vo.GameListVo;
import g.model.gamemodel.po.GameModel;
import g.model.gamemodel.vo.GameModelListVo;
import g.model.player.vo.VSysUserListVo;
import g.model.playerstatistics.po.JackpotMatch;
import g.model.playerstatistics.po.JackpotPlayer;
import g.model.playerstatistics.po.PlayerDataStatistics;
import g.model.playerstatistics.po.PlayerDataStatisticsReport;
import g.model.playerstatistics.vo.JackpotMatchListVo;
import g.model.playerstatistics.vo.JackpotPlayerListVo;
import g.model.playerstatistics.vo.PlayerDataStatisticsListVo;
import g.model.playerstatistics.vo.PlayerDataStatisticsVo;
import g.model.warning.po.VWarningPlayerDetail;
import g.model.warning.vo.VWarningPlayerDetailListVo;
import g.service.common.IVSysUserService;
import g.service.game.IGameService;
import g.service.gamemodel.IGameModelService;
import org.apache.commons.collections.map.HashedMap;
import org.soul.commons.lang.DateTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.query.Paging;
import org.soul.service.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


/**
 * 玩家数据统计表服务
 *
 * @author lenovo
 * @time 2017-1-5 14:06:09
 */
//region your codes 1
public class PlayerDataStatisticsService extends BaseService<PlayerDataStatisticsMapper, PlayerDataStatisticsListVo, PlayerDataStatisticsVo, PlayerDataStatistics, Integer> implements IPlayerDataStatisticsService {
//endregion your codes 1

    @Autowired
    private IGameService gameService;

    @Autowired
    private IGameModelService gameModelService;

    @Autowired
    private IVSysUserService sysUserService;

    //region your codes 2
    public PlayerDataStatisticsListVo selectAllAgentList(PlayerDataStatisticsListVo listVo) {

        listVo.setResult(this.mapper.selectAllAgentList(getQueryParams(listVo)));
        //分页操作
        Paging paging = listVo.getPaging();
        if (paging != null){
            paging.setTotalCount(this.mapper.selectAllAgentListNumber(getQueryParams(listVo)));
            paging.cal();
        }
        return selectTotalAmount(listVo);
    }

    public PlayerDataStatisticsListVo selectOwnerPlayerList(PlayerDataStatisticsListVo listVo) {

        listVo.setResult(this.mapper.selectOwnerPlayerList(getQueryParams(listVo)));
        //分页操作
        Paging paging = listVo.getPaging();
        if (paging != null){
            paging.setTotalCount(this.mapper.selectOwnerPlayerListNumber(getQueryParams(listVo)));
            paging.cal();
        }
        return selectTotalAmount(listVo);
    }

    public PlayerDataStatisticsListVo selectAllGameList(PlayerDataStatisticsListVo listVo) {

        listVo.setResult(this.mapper.selectAllGameList(getQueryParams(listVo)));
        //分页操作
        Paging paging = listVo.getPaging();
        if (paging != null) {
            paging.setTotalCount(this.mapper.selectAllGameListNumber(getQueryParams(listVo)));
            paging.cal();
        }
        return selectTotalAmount(listVo);
    }


    public List<PlayerDataStatistics> selectAllAgentExportData(PlayerDataStatisticsListVo listVo) {

        return this.mapper.selectAllAgentExportData(getQueryParams(listVo));
    }

    public List<PlayerDataStatistics> selectOwnerPlayerExportData(PlayerDataStatisticsListVo listVo) {

        return this.mapper.selectOwnerPlayerExportData(getQueryParams(listVo));
    }

    public List<PlayerDataStatistics> selectAllGameExportData(PlayerDataStatisticsListVo listVo) {

        return this.mapper.selectAllGameExportData(getQueryParams(listVo));
    }

    public List<PlayerDataStatistics> selectPlayerOrGameDetailExportData(PlayerDataStatisticsListVo listVo) {

        return this.mapper.selectPlayerOrGameDetailExportData(getQueryParams(listVo));
    }

    public Map getQueryParams(PlayerDataStatisticsListVo listVo) {

        Map<String, Object> queryParams = listVo.getQueryParams();
        String where = (String)queryParams.get("where");
        if (StringTool.isBlank(where)) {
            where = "1=1";
            queryParams.put("where", where);
        }
        return queryParams;
    }

    public PlayerDataStatisticsListVo selectTotalAmount(PlayerDataStatisticsListVo listVo) {

        Double totalProfitAmount = 0d;
        Double totalEffectiveAmount = 0d;
        Double totalWinAmount = 0d;
        if (listVo.getSearch().getIsDetail() != "true") {
            for (PlayerDataStatistics statistics : listVo.getResult()) {
                totalProfitAmount += statistics.getProfitAmount() != null ? statistics.getProfitAmount() : 0;
                totalEffectiveAmount += statistics.getEffectiveAmount() != null ? statistics.getEffectiveAmount() : 0;
                totalWinAmount += statistics.getWinAmount() != null ? statistics.getWinAmount() : 0;
            }
        } else {
            Integer playerId = listVo.getResult().get(0).getSysUserId();
            VSysUserListVo vSysUserListVo = new VSysUserListVo();
            vSysUserListVo.getSearch().setId(playerId);
            vSysUserListVo = sysUserService.search(vSysUserListVo);
            String playerName = vSysUserListVo.getResult().get(0).getUsername();
            //插入玩家信息
            for (int i = 0, length = listVo.getResult().size(); i < length; i ++) {
                PlayerDataStatistics statistics = listVo.getResult().get(i);
                totalProfitAmount += statistics.getProfitAmount() != null ? statistics.getProfitAmount() : 0;
                totalEffectiveAmount += statistics.getEffectiveAmount() != null ? statistics.getEffectiveAmount() : 0;
                totalWinAmount += statistics.getWinAmount() != null ? statistics.getWinAmount() : 0;
                listVo.getResult().get(i).setUsername(playerName);
            }
        }
        listVo.setTotalProfitAmount(totalProfitAmount);
        listVo.setTotalEffectiveAmount(totalEffectiveAmount);
        listVo.setTotalWinAmount(totalWinAmount);
        return listVo;
    }

    public PlayerDataStatisticsListVo setSearchCondition(PlayerDataStatisticsListVo listVo){

        listVo.setStartTime(listVo.getSearch().getStartTime());
        listVo.setOwnerUsername(listVo.getSearch().getOwnerUsername());
        listVo.setPlayerId(listVo.getSearch().getSysUserId());
        listVo.setGameId(listVo.getSearch().getGameId());
        listVo.setGameModelId(listVo.getSearch().getGameModelId());
        listVo.setRoomId(listVo.getSearch().getRoomId());
        return listVo;
    }

    public PlayerDataStatisticsListVo addOneDay(PlayerDataStatisticsListVo listVo) {

        Date startTime = listVo.getSearch().getStartTime();
        Date endTime = listVo.getSearch().getEndTime();
        if (startTime != null && endTime != null) {
            endTime = DateTool.addDays(endTime, 1);
            listVo.getSearch().setEndTime(endTime);
        }
        return listVo;
    }

    public GameListVo selectAllGame() {

        GameListVo gameListVo = new GameListVo();
        gameListVo.setResult(gameService.getGameList());
        return gameListVo;
    }

    public GameListVo selectAgentGame(Integer agentId) {

        GameListVo gameListVo = new GameListVo();
        Map map = new HashMap();
        map.put("agentId", agentId);
        gameListVo.setResult(gameService.selectAgentGame(map));
        return gameListVo;
    }

    public Map selectOwnerGameModel(Integer gameId) {

        Map map = new HashMap();
        List<GameModel> result = new ArrayList<>();
        if (gameId == null) {
            map.put("ownerGameModel", result);
            return map;
        }
        GameModelListVo listVo = new GameModelListVo();
        listVo.getSearch().setGameId(gameId);
        listVo = gameModelService.search(listVo);
        result = listVo.getResult();
        map.put("ownerGameModel", result);
        return map;
    }
    //endregion your codes 2


    @Override
    public List<PlayerDataStatisticsReport> searchPlayerList(String startTime, String endTime, String agentUserName) {
        Map<String,Object> map = new HashedMap();
        map.put("beginTime", DateTimeTool.getDateByFull(startTime));
        map.put("endTime", DateTimeTool.getDateByFull(endTime));
        map.put("ownerName",agentUserName.split(","));
        List<PlayerDataStatisticsReport> list = this.mapper.playerDataStatisticsReport(map);
        return list;
    }

    @Override
    public List<PlayerDataStatisticsReport> searchMultiplePlayerList(String startTime, String endTime, String[] username) {
        Map<String,Object> map = new HashedMap();
        map.put("beginTime", DateTimeTool.getDateByFull(startTime));
        map.put("endTime", DateTimeTool.getDateByFull(endTime));
        map.put("ownerNames",username);
       // List<PlayerDataStatistics> list = this.mapper.reportSearchAll(map);
        List<PlayerDataStatisticsReport> list = this.mapper.playerDataStatisticsReport(map);
        return list;
    }

    @Override
    public List<PlayerDataStatisticsReport> searchAllPlayerList(String startTime, String endTime) {
        Map<String,Object> map = new HashedMap();
        map.put("beginTime", DateTimeTool.getDateByFull(startTime));
        map.put("endTime", DateTimeTool.getDateByFull(endTime));
        List<PlayerDataStatisticsReport> list = this.mapper.playerDataStatisticsReport(map);
        return list;
    }

    @Override
    public Boolean quartzPlayerDataStatistics() {

        return this.mapper.playerDataStatisticsCall();
    }

    @Override
    public JackpotMatchListVo getJackpotMatch(JackpotMatchListVo listVo,Date startTime,Date endTime,String sysuserid,String gameid,String gamemodelid) {

        Map<String, Object> queryParams = listVo.getQueryParams();
        queryParams.put("startTime",startTime);
        queryParams.put("outTime",endTime);
        queryParams.put("sysuserid",Integer.parseInt(sysuserid));
        queryParams.put("gameid",Integer.parseInt(gameid));
        queryParams.put("gamemodelid",Integer.parseInt(gamemodelid));
        List<JackpotMatch>  matchPlayer = this.mapper.getJackpotMatch(queryParams);
        //分页操作
        Paging paging = listVo.getPaging();
        if (paging != null){
            paging.setTotalCount(this.mapper.jackpotTotalRecords(queryParams));
            paging.cal();
        }
        listVo.setResult(matchPlayer);
        return listVo;
    }

    @Override
    public JackpotPlayerListVo getJackpotPlayer(JackpotPlayerListVo listVo,Date startTime,Date endTime) {
        Map<String, Object> queryParams = listVo.getQueryParams();
        queryParams.put("startTime",startTime);
        queryParams.put("outTime",endTime);
        List<JackpotPlayer>  matchPlayer = this.mapper.getJackpotPlayer(queryParams);
        //分页操作
        Paging paging = listVo.getPaging();
        if (paging != null){
            paging.setTotalCount(this.mapper.jackpotPlayerTotalRecords(queryParams));
            paging.cal();
        }
        listVo.setResult(matchPlayer);
        return listVo;
    }

    @Override
    public VWarningPlayerDetailListVo jackpotBet(VWarningPlayerDetailListVo listVo) {
        Map<String, Object> queryParams = listVo.getQueryParams();
        List<VWarningPlayerDetail>  matchPlayer = this.mapper.jackpotBet(queryParams);
        //分页操作
        Paging paging = listVo.getPaging();
        if (paging != null){
            paging.setTotalCount(this.mapper.jackpotTotalBet(queryParams));
            paging.cal();
        }
        listVo.setResult(matchPlayer);
        return listVo;
    }

    public boolean dataStatisticsReport(Map map) {

        return this.mapper.dataStatisticsReport(map);
    }
}