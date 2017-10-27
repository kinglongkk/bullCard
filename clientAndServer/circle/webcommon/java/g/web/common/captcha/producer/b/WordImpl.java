package g.web.common.captcha.producer.b;

import com.google.code.kaptcha.text.WordRenderer;
import com.google.code.kaptcha.util.Configurable;
import org.soul.commons.image.captcha.ImgFontByte;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by longer on 7/21/16.
 */
public class WordImpl extends Configurable implements WordRenderer {

    int lineCount = 20;

    @Override
    public BufferedImage renderWord(String str1, int width, int height) {
        int x = 0;
        int red = 0;int green = 0;int blue = 0;

        int fontHeight = getConfig().getHeight() - 5;
        int codeCount = getConfig().getTextProducerCharLength();
        int codeY = height - 8;
        x = width / (codeCount + 2);

        BufferedImage buffImg = new BufferedImage(width, height, 1);
        Graphics2D g = buffImg.createGraphics();

        Random random = new Random();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        ImgFontByte imgFont = new ImgFontByte();
        Font font = imgFont.getFont(fontHeight);
        g.setFont(font);
        for (int i = 0; i < lineCount; i++)
        {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width / 8);
            int ye = ys + random.nextInt(height / 8);
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawLine(xs, ys, xe, ye);
        }
        StringBuffer randomCode = new StringBuffer();
        for (int i = 0; i < codeCount; i++)
        {
            String strRand = str1.substring(i,i+1);

            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawString(strRand, (i + 1) * x, codeY);

            randomCode.append(strRand);
        }
        return buffImg;
    }
}
