package g.data.datasource;

import org.soul.commons.datasource.IDubboDatasourceResolver;
import org.soul.commons.init.context.AbstractBaseVo;
import org.soul.data.datasource.DatasourceTool;

import javax.sql.DataSource;

/**
 * Created by kevice on 7/8/15.
 */
public class DubboDatasourceResolver implements IDubboDatasourceResolver {

    @Override
    public DataSource determineDatasource(String typeName, AbstractBaseVo vo) {
        return DatasourceTool.getBaseDatasource();
    }

    @Override
    public DataSource getDefaultDatasource() {
        return DatasourceTool.getBaseDatasource();
    }

}
