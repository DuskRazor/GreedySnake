package com.kali;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageUtil {
    private static final ImageUtil INSTANCE = new ImageUtil();
    public BufferedImage bg,body,hU,hD,hL,hR;

    private ImageUtil(){
        try {
            bg = ImageIO.read(Objects.requireNonNull(ImageUtil.class.getClassLoader().getResourceAsStream("images/bg.png")));
            hU = ImageIO.read(Objects.requireNonNull(ImageUtil.class.getClassLoader().getResourceAsStream("images/hU.png")));
            hD = ImageIO.read(Objects.requireNonNull(ImageUtil.class.getClassLoader().getResourceAsStream("images/hD.png")));
            hL = ImageIO.read(Objects.requireNonNull(ImageUtil.class.getClassLoader().getResourceAsStream("images/hL.png")));
            hR = ImageIO.read(Objects.requireNonNull(ImageUtil.class.getClassLoader().getResourceAsStream("images/hR.png")));
            body = ImageIO.read(Objects.requireNonNull(ImageUtil.class.getClassLoader().getResourceAsStream("images/body.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ImageUtil getInstance(){
        return INSTANCE;
    }
}
