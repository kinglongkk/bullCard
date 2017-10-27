package g.web.common.init;

import org.soul.commons.init.context.CommonContext;
import org.soul.web.init.BaseCtxLoaderListener;

import javax.servlet.ServletContextEvent;
import java.util.TimeZone;

/**
 * Created by Kevice on 2015/3/23 0023.`
 */
public class CommonCtxLoaderListener extends BaseCtxLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        CommonContext.setLocal(true);
    }
}
