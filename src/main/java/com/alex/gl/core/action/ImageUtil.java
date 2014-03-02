package com.alex.gl.core.action;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 16.02.14
 * Time: 12:23
 */
public class ImageUtil {

    private static Map<String, Texture> cache = new HashMap<>();

    public static final String DONCHAN_RED = "donRed.png";
    public static final String DONCHAN_BLUE = "donBlue.png";

    public static Texture loadImage(String path) {
        try {
            Texture texture = cache.get(path);
            if (null == texture) {
                InputStream resource = ResourceLoader.getResourceAsStream(path);
                texture = TextureLoader.getTexture("PNG", resource);
                cache.put(path, texture);
            }
            return texture;
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    public static void resetCache() {
        cache.clear();
    }
}
