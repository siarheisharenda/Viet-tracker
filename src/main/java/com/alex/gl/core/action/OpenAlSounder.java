package com.alex.gl.core.action;

import static org.lwjgl.openal.AL10.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.util.WaveData;
import org.newdawn.slick.util.ResourceLoader;

import java.nio.IntBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 01.11.13
 * Time: 7:50
 */
public class OpenAlSounder {

    public static OpenAlSounder instance = new OpenAlSounder();
    /** Buffers hold sound data. */
    IntBuffer buffer = BufferUtils.createIntBuffer(1);

    /** Sources are points emitting sound. */
    IntBuffer source = BufferUtils.createIntBuffer(1);

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
            alGenBuffers(buffer);
            alBufferData(buffer.get(0), data.format, data.data, data.samplerate);
            data.dispose();
            alGenSources(source);
            alSourcei(source.get(0), AL_BUFFER, buffer.get(0));
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public void playGong() {
        alSourcePlay(source.get(0));
    }

    public void destroyAl() {
        alDeleteSources(source);
        alDeleteBuffers(buffer);
        AL.destroy();
    }
}
