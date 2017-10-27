package g.web.common.captcha.producer.b;

import com.google.code.kaptcha.BackgroundProducer;

import java.awt.image.BufferedImage;

/**
 * Created by longer on 7/21/16.
 * 背景
 */
public class BackgroundImpl implements BackgroundProducer {

    @Override
    public BufferedImage addBackground(BufferedImage bufferedImage) {
        return bufferedImage;
    }


}
