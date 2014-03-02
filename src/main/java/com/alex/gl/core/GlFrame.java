package com.alex.gl.core;

import static org.lwjgl.opengl.GL11.*;

import com.alex.gl.core.action.*;
import com.alex.gl.core.widget.ButtonContainer;
import com.alex.gl.entity.*;
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

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 27.09.13
 * Time: 0:17
 */
public class GlFrame {

    private static final int WIDTH = 1024;
    private static final int HEIGHT = 600;
    private static final String WINDOW_TITLE = "Vovinam Viet Vo Dao";
    public static final String MINUS_POINTS_BLUE_FORMAT = "Minus points blue: %d";
    public static final String MINUS_POINTS_RED_FORMAT = "Minus points red: %d";
    public static final String ROUND_FORMAT = "Round: %d";
    public static final String REMINDER_LABEL = "Reminders:";
    public static final String WARNING_LABEL = "Warnings:";
    private UnicodeFont trueFont1;
    private TrueTypeFont trueFont2;
    private TrueTypeFont trueFont3;
    private TrueTypeFont trueFont4;
    private TrueTypeFont trueFont5;
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
    private Controller joystick;
    private int timeXPoint;
    private int halfHeight;

    public GlFrame(Settings settings, SettingContainer container) {
        this.settings = settings;
        this.container = container;
        score = new Score(settings);
    }

    public void start() {
        hardInit();
        initControl();
        displayInit();
        initFont();
        initScoreFont();
        initInterface();
        initTimer();
        startMainLoop();
    }

    private void initControl() {
        DBoolean.setDelay(settings.getHitDelay());
        DBoolean.setJudges(settings.getJudges());
        joystick = ActionUtil.initJoystick();
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
            Display.setTitle(WINDOW_TITLE);
            Display.setVSyncEnabled(true);
            Display.setFullscreen(true);
            Display.create();
        } catch (LWJGLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
        OpenAlSounder.instance.init();
    }

    private void displayInit() {
        halfWidth = Display.getWidth() / 2;
        halfHeight = Display.getHeight() / 2;
        GLUtil.glIniter();
        if (joystick != null) {
            ButtonContainer.instance.reInitPositions();
        }
    }

    private void initFont() {
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream("ADLER.TTF");
            Font font2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            trueFont2 = new TrueTypeFont(font2.deriveFont(72f), true);
            trueFont3 = new TrueTypeFont(font2.deriveFont(45f), true);
            trueFont4 = new TrueTypeFont(font2.deriveFont(18f), true);
            trueFont5 = new TrueTypeFont(font2.deriveFont(55f), true);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void initScoreFont() {
        int fontInc = (Display.getWidth() - 1024) / 10;
        int size = 300 + fontInc;
        Font font = new Font("Arial", Font.BOLD, (size > 350) ? 350 : size);
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
        timeXPoint = timeRect.x + (int) (timeRect.getWidth() * 0.25);
    }

    private void initTimer() {
        timer = new SecondsTimer(settings.getSecondsInRound(), settings.getMedicalBreakTime(), score);
    }

    private void startMainLoop() {
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            if (Display.wasResized()) {
                displayInit();
                initScoreFont();
                initInterface();
            }
            ActionUtil.roundControl(timer, score);
            keyDetector();
            display();
            Display.update();
            Display.sync(100);
        }
        close();
    }

    private void close() {
        OpenAlSounder.instance.destroyAl();
        Display.destroy();
        score.reset();
        ImageUtil.resetCache();
    }

    private void display() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        drawRect(false);
        drawRect(true);
        glColor3d(0, 0, 0);
        GLUtil.setTimerColor(timer.getStatus());
        glRecti(timeRect.x, timeRect.y, timeRect.x2, timeRect.y2);
        ShapeUtils.drawJoystickQuads(score);
        WinnerSingleton.instance.checkWin(settings, score, timer);
        if (WinnerSingleton.instance.isBlue() != null) {
            glColor3ub((byte) 0x4F, (byte) 0xFF, (byte) 0);
            drawRect(WinnerSingleton.instance.isBlue());
        }
        if (SecondsTimer.RoundStatus.MEDICAL.equals(timer.getStatus())) {
            glColor3f(1, 1, 1);
            glRecti(redRect.x, redRect.y + 100, blueRect.x2, blueRect.y2 - 100);
            glEnable(GL_BLEND);
            trueFont5.drawString(timePoint.x - 20, halfHeight - 10, "MEDICAL", Color.blue);
            glDisable(GL_BLEND);
        }
        ShapeUtils.showDonChan(score.getDonRed(), score.getDonBlue());
        ShapeUtils.drawReminders(redRect.y2 + 30, score.getRemRed(), score.getRemBlue());
        ShapeUtils.drawWarnings(redRect.y2 + 55, score.getWarnRed(), score.getWarnBlue());
        ShapeUtils.checkWarningsReminders(score, settings);
        stringShow();
        glFlush();
    }

    private void drawRect(boolean isBlue) {
        if (isBlue) {
            glColor3f(0, 0, 1);
            glRecti(blueRect.x, blueRect.y, blueRect.x2, blueRect.y2);
        } else {
            glColor3f(1, 0, 0);
            glRecti(redRect.x, redRect.y, redRect.x2, redRect.y2);
        }
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
        trueFont3.drawString(20, 20, String.format(ROUND_FORMAT, score.getRound()), Color.yellow);
        trueFont3.drawString(timeRect.x2 * 1.1f, 20, String.valueOf(timer.getStatus()), Color.cyan);
        trueFont4.drawString(10, redRect.y2 + 5, String.format(MINUS_POINTS_RED_FORMAT, score.getpRed()), Color.red);
        trueFont4.drawString(10, redRect.y2 + 30, REMINDER_LABEL, Color.red);
        trueFont4.drawString(10, redRect.y2 + 55, WARNING_LABEL, Color.red);
        trueFont4.drawString(blueRect.x + 10, redRect.y2 + 5, String.format(MINUS_POINTS_BLUE_FORMAT, score.getpBlue()),
                Color.cyan);
        trueFont4.drawString(blueRect.x + 10, redRect.y2 + 30, REMINDER_LABEL, Color.cyan);
        trueFont4.drawString(blueRect.x + 10, redRect.y2 + 55, WARNING_LABEL, Color.cyan);
    }

    private void keyDetector() {
        ActionUtil.controlDetect(score, timer);
        if (joystick != null) {
            ActionUtil.joyDetect(joystick);
        }
    }
}
