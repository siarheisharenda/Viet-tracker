package com.alex.gl.core;

import static org.lwjgl.opengl.GL11.GL_ALL_ATTRIB_BITS;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFlush;
import static org.lwjgl.opengl.GL11.glPopAttrib;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushAttrib;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRecti;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import com.alex.gl.core.action.ActionUtil;
import com.alex.gl.core.action.GLUtil;
import com.alex.gl.core.action.SecondsTimer;
import com.alex.gl.core.action.ShapeUtils;
import com.alex.gl.entity.Rect;
import com.alex.gl.entity.Score;
import com.alex.gl.entity.SettingContainer;
import com.alex.gl.entity.Settings;

import net.java.games.input.Controller;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.io.InputStream;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 27.09.13
 * Time: 0:17
 */
public class GlFrame {

    private static final int WIDTH = 1024;
    private static final int HEIGHT = 600;
    private UnicodeFont trueFont1;
    private TrueTypeFont trueFont2;
    private TrueTypeFont trueFont3;
    private int halfWidth;
    private int rightBorderOffset;
    private int topBorderOffset;
    private Score score;
    private Rect timeRect;
    private Rect redRect;
    private Rect blueRect;
    private Point timePoint;
    private SecondsTimer timer;
    private Settings settings;
    private SettingContainer container;
    private Controller joystick = ActionUtil.initJoystick();
    private int timeXPoint;

    public GlFrame(Settings settings, SettingContainer container) {
        this.settings = settings;
        this.container = container;
        score = new Score(settings);
    }

    public void start() {
        hardInit();
        displayInit();
        initFont();
        initInterface();
        initTimer(settings.getSecondsInRound());
        startMainLoop();
    }

    private void hardInit() {
        try {
            DisplayMode displayMode = null;
            if (container.getDisplayMode() != null) {
                displayMode = container.getDisplayMode();
            } else {
                displayMode = new DisplayMode(WIDTH, HEIGHT);
            }
            Display.setDisplayMode(displayMode);
            Display.setResizable(true);
            Display.setTitle("Vovinam Viet Vo Dao");
            Display.setVSyncEnabled(true);
            Display.setFullscreen(true);
            Display.create();
        } catch (LWJGLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void displayInit() {
        halfWidth = Display.getWidth() / 2;
        GLUtil.glIniter();
    }

    private void initFont() {
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream("ADLER.TTF");
            Font font2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            trueFont2 = new TrueTypeFont(font2.deriveFont(72f), true);
            trueFont3 = new TrueTypeFont(font2.deriveFont(40f), true);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        Font font = new Font("Arial", Font.BOLD, 250);
        trueFont1 = new UnicodeFont(font);
        try {
            trueFont1.addAsciiGlyphs();
            trueFont1.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
            trueFont1.addNeheGlyphs();
            trueFont1.loadGlyphs();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    private void initInterface() {
        int topBorder = (int) (Display.getHeight() * 0.15);
        int bottomBorder = (int) (Display.getHeight() * 0.85);
        timeRect = new Rect(halfWidth - 200, 0, halfWidth + 200, topBorder);
        redRect = new Rect(0, topBorder, halfWidth, bottomBorder);
        blueRect = new Rect(halfWidth, topBorder, Display.getWidth(), bottomBorder);
        timePoint = new Point(timeRect.x + 90, timeRect.y + 15);
        rightBorderOffset = (int) (redRect.getWidth() * 0.35);
        topBorderOffset = redRect.y + (int) (redRect.getHeight() * 0.2);
        timeXPoint = timeRect.x + (int)(timeRect.getWidth() * 0.25);
    }

    private void initTimer(int startSeconds) {
        timer = new SecondsTimer(1000, startSeconds, score);
    }

    private void startMainLoop() {
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            if (Display.wasResized()) {
                displayInit();
                initInterface();
            }
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
        ShapeUtils.drawJoystickQuads(Display.getWidth(), Display.getHeight(), halfWidth);
        stringShow();
        glFlush();
    }

    private void stringShow() {
        glEnable(GL_BLEND);
        timeDraw();
        glPushMatrix();
        glPushAttrib(GL_ALL_ATTRIB_BITS);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        scoreDraw();
        glPopAttrib();
        glPopMatrix();
        glDisable(GL_BLEND);
    }

    private void scoreDraw() {
        trueFont1.drawString(rightBorderOffset - (rightBorderOffset * score.getRedDelta()), topBorderOffset,
                String.valueOf(score.getcRed()));
        trueFont1.drawString(blueRect.x + rightBorderOffset - (rightBorderOffset * score.getBlueDelta()),
                topBorderOffset, String.valueOf(score.getcBlue()));
    }

    private void timeDraw() {
        trueFont2.drawString(timeXPoint, timePoint.y, ActionUtil.convertTime(timer.getSeconds()), Color.red);
        trueFont3.drawString(20, 20, "Round: ", Color.yellow);
        trueFont3.drawString(timeRect.x * 0.7f, 20, String.valueOf(score.getRound()), Color.yellow);
    }

    private void keyDetector() {
        ActionUtil.controlDetect(score, timer);
        if (joystick != null) {
            ActionUtil.joyDetect(joystick);
        }
    }
}
