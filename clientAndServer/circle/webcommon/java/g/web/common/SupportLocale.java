package g.web.common;

import org.soul.web.init.BaseConfigManager;
import org.soul.web.locale.ISupportLocale;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tony on 16-1-19.
 */
public class SupportLocale implements ISupportLocale {

    @Override
    public Set<String> getSupportLocale() {
        Set<String> locales = new HashSet<>();
        locales.add(BaseConfigManager.getConfigration().getDefaultLocal().toString());
        return locales;
    }
}
