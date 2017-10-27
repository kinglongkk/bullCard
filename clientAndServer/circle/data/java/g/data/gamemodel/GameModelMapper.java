package g.data.gamemodel;

import g.model.gamemodel.po.GameModel;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.List;


/**
 * 游戏表数据访问对象
 *
 * @author lenovo
 * @time 2016-8-25 14:20:28
 */
//region your codes 1
public interface GameModelMapper extends IBaseMapper<GameModel, Integer> {
//endregion your codes 1

    //region your codes 2
    List<GameModel> getGameModelAllList();
    //endregion your codes 2

}