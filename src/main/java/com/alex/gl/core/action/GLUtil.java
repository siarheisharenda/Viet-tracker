package com.alex.gl.core.action;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;

import org.apache.commons.lang.SystemUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 12.10.13
 * Time: 11:29
 */
public class GLUtil {

    public static void glIniter() {
        glDisable(GL_TEXTURE_2D);
        glShadeModel(GL_SMOOTH);
        glDisable(GL_LIGHTING);
        glDisable(GL_DEPTH_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glClearColor(0, 0, 0, 1);
        glClearDepth(1);

        glMatrixMode(GL_MODELVIEW);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glViewport(0, 0, Display.getWidth(), Display.getHeight());
        gluOrtho2D(0, Display.getWidth(), Display.getHeight(), 0);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glLineWidth(3f);
    }

    public static void attachFullScreenMode(DisplayMode current) {
        DisplayMode targetMode = null;
        try {
            DisplayMode[] modes = Display.getAvailableDisplayModes();
            for (DisplayMode mode : modes) {
                if ((mode.getWidth() == current.getWidth()) && (mode.getHeight() == current.getHeight())) {
                    int maxBPP = 24;
                    if (SystemUtils.OS_NAME.contains("Windows")) {
                        maxBPP = 32;
                    }
                    if (mode.getBitsPerPixel() == maxBPP) {
                        targetMode = mode;
                        break;
                    }
                }
            }
            if (targetMode != null) {
                Display.setDisplayMode(targetMode);
                Display.setFullscreen(true);
            }
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public static void setTimerColor(SecondsTimer.RoundStatus status) {
        switch (status) {
            case BREAK:
                glColor3ub((byte)0xAF,(byte)0xEE,(byte)0xEE);
                break;
            default:
                glColor3f(1, 1, 0);
                break;
        }
    }

}
