package g.web.admin.init;

import g.web.common.init.CommonCtxLoaderListener;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import javax.servlet.ServletContext;

/**
 * Created by Kevice on 2015/3/23 0023.
 */
public class CtxLoaderListener extends CommonCtxLoaderListener {

    @Override
    protected void customizeContext(ServletContext sc, ConfigurableWebApplicationContext wac) {
        super.customizeContext(sc, wac);
    }

}
