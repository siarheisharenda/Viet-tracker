package com.alex.gl.core.action;

import static org.lwjgl.openal.AL10.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.util.WaveData;
import org.newdawn.slick.util.ResourceLoader;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 01.11.13
 * Time: 7:50
 */
public class OpenAlSounder {

    public static OpenAlSounder instance = new OpenAlSounder();

    private int source;
    private int buffer;
    private WaveData data;

    private OpenAlSounder() {
        try {
            data = WaveData.create(ResourceLoader.getResourceAsStream("DingDing.wav"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {
        try {
            AL.create();
            buffer = alGenBuffers();
            alBufferData(buffer, data.format,data.data, data.samplerate);
            data.dispose();
            source = alGenSources();
            alSourcei(source, AL_BUFFER, buffer);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        alSourcePlay(source);
    }

    public void destroyAl() {
        alDeleteBuffers(buffer);
        AL.destroy();
    }
}
