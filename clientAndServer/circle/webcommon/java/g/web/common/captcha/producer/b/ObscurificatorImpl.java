package g.web.common.captcha.producer.b;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.util.Configurable;

import java.awt.image.BufferedImage;

/**
 * Created by longer on 7/21/16.
 */
public class ObscurificatorImpl extends Configurable implements GimpyEngine {

    @Override
    public BufferedImage getDistortedImage(BufferedImage bufferedImage) {

        return bufferedImage;
    }
}
