package g.service.gamemodel;

import g.model.gamemodel.po.GameModel;
import g.model.gamemodel.vo.GameModelListVo;
import g.model.gamemodel.vo.GameModelVo;
import org.soul.iservice.support.IBaseService;

import java.util.List;


/**
 * 游戏表服务接口
 *
 * @author lenovo
 * @time 2016-8-25 14:20:28
 */
//region your codes 1
public interface IGameModelService extends IBaseService<GameModelListVo, GameModelVo, GameModel, Integer> {
//endregion your codes 1

    //region your codes 2
    List<GameModel> getGameModelAllList();
    //endregion your codes 2

}