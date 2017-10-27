package g.service.gamemodel;

import g.data.gamemodel.GameModelMapper;
import g.model.gamemodel.po.GameModel;
import g.model.gamemodel.vo.GameModelListVo;
import g.model.gamemodel.vo.GameModelVo;
import org.soul.service.support.BaseService;

import java.util.List;


/**
 * 游戏表服务
 *
 * @author lenovo
 * @time 2016-8-25 14:20:28
 */
//region your codes 1
public class GameModelService extends BaseService<GameModelMapper, GameModelListVo, GameModelVo, GameModel, Integer> implements IGameModelService {
//endregion your codes 1

    //region your codes 2

    @Override
    public List<GameModel> getGameModelAllList() {
        return this.mapper.getGameModelAllList();
    }

    //endregion your codes 2

}