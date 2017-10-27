package g.web.common.captcha.producer.b;

import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.util.Configurable;

import java.awt.image.BufferedImage;

/**
 * Created by longer on 7/21/16.
 */
public class NoiseImpl  extends Configurable implements NoiseProducer {
    public NoiseImpl() {
    }

    public void makeNoise(BufferedImage bufferedImage, float factorOne, float factorTwo, float factorThree, float factorFour) {

    }
}