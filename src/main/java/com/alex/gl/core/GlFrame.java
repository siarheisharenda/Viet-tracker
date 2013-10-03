package com.alex.gl.core;

import com.alex.gl.core.action.ActionUtil;
import com.alex.gl.core.action.SecondsTimer;
import com.alex.gl.entity.Rect;
import com.alex.gl.entity.Score;
import com.alex.gl.entity.Settings;
import org.apache.commons.lang.SystemUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 27.09.13
 * Time: 0:17
 */
public class GlFrame {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 600;

    private TrueTypeFont trueFont1;
    private TrueTypeFont trueFont2;
    private int halfWidth;
    private int topBorderOffset;
    private Score score = new Score();
    private Rect timeRect;
    private Rect redRect;
    private Rect blueRect;
    private Point timePoint;
    private SecondsTimer timer;
    private Settings settings;

    public GlFrame(Settings settings) {
        this.settings = settings;
    }

    public void start() {
        hardInit();
        displayInit();
        initFont();
        initInterface();
        initTimer(settings.getSecondsInRound());
        startMainLoop();
    }

    private void initTimer(int startSeconds) {
        timer = new SecondsTimer(1000, startSeconds);
    }

    private void initInterface() {
        int topBorder = (int) (Display.getHeight() * 0.15);
        int bottomBorder = (int)(Display.getHeight() * 0.85);
        topBorderOffset = (int)(Display.getHeight() * 0.01);
        timeRect = new Rect(halfWidth - 200, 0, halfWidth + 200, topBorder);
        redRect = new Rect(0, topBorder, halfWidth, bottomBorder);
        blueRect = new Rect(halfWidth, topBorder, Display.getWidth(), bottomBorder);
        timePoint = new Point(timeRect.x + 90, timeRect.y + 15);
    }

    private void hardInit() {
        try {
            DisplayMode displayMode = new DisplayMode(WIDTH, HEIGHT);
//            attachFullScreenMode(displayMode);
            Display.setDisplayMode(displayMode);
            Display.setTitle("Vovinam Viet Vo Dao");
            Display.setVSyncEnabled(true);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    private void initFont() {
        try {
            InputStream inputStream	= ResourceLoader.getResourceAsStream("ADLER.TTF");
            Font font2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            font2 = font2.deriveFont(72f);
            trueFont2 = new TrueTypeFont(font2, true);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        Font font = new Font("Arial", Font.BOLD, 72);
        trueFont1 = new TrueTypeFont(font, true);
    }

    private void displayInit() {
        halfWidth = Display.getWidth() / 2;
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
    }

    private void startMainLoop() {
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            keyDetector();
            display();
            Display.update();
            Display.sync(100);
        }
        close();
    }

    private void close() {
        Display.destroy();
        score.reset();
    }

    private void display() {
        glClear(GL_COLOR_BUFFER_BIT);
        glColor3f(1, 0, 0);
        glRecti(redRect.x, redRect.y, redRect.x2, redRect.y2);
        glColor3f(0, 0, 1);
        glRecti(blueRect.x, blueRect.y, blueRect.x2, blueRect.y2);
        glColor3f(1, 1, 0);
        glRecti(timeRect.x, timeRect.y, timeRect.x2, timeRect.y2);
        glColor3d(0, 0, 0);
        stringShow();
        glFlush();
    }

    private void stringShow() {
        glEnable(GL_BLEND);
        timeDraw();
        glPushMatrix();
        glScaled(5, 6, 0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        scoreDraw();
        glPopMatrix();
        glDisable(GL_BLEND);
        glPopAttrib();
    }

    private void scoreDraw() {
        trueFont1.drawString(halfWidth * (0.05f - score.getRedDelta()), topBorderOffset,
                String.valueOf(score.getcRed()), Color.white);
        trueFont1.drawString(Display.getWidth() * (0.14f - score.getBlueDelta()), topBorderOffset,
                String.valueOf(score.getcBlue()), Color.white);
    }

    private void timeDraw() {
        trueFont2.drawString(timePoint.x, timePoint.y, ActionUtil.convertTime(timer.getSeconds()), Color.red);
    }

    private void keyDetector() {
        ActionUtil.controlDetect(score, timer);
    }

    private void attachFullScreenMode(DisplayMode current) {
        DisplayMode targetMode = null;
        try {
            DisplayMode[] modes = Display.getAvailableDisplayModes();
            for (DisplayMode mode : modes) {
                if ((mode.getWidth() == current.getWidth()) && (mode.getHeight() == current.getHeight())) {
                    int maxBPP = 24;
                    if (SystemUtils.OS_NAME.contains("Window")) {
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
}
