package com.alex.gl.core.action;

import static com.alex.gl.core.widget.ButtonContainer.instance;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex2d;
import static org.lwjgl.opengl.GL11.glVertex2i;

import com.alex.gl.entity.DBoolean;
import com.alex.gl.entity.Score;
import com.alex.gl.entity.Settings;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 06.10.13
 * Time: 23:20
 */
public class ShapeUtils {

    public static final int OFFSET_REMINDER = 135;
    public static final int OFFSET_WARNING = 135;
    private static int blueHits;
    private static int redHits;
    private static final int SIZE_IMAGE = 50;

    private static ScoreTimer blueScore = new ScoreTimer(true);
    private static ScoreTimer redScore = new ScoreTimer(false);

    public static void drawJoystickQuads(Score score) {
        instance.drawButtons();

        blueHits = getHitsCount(instance.getBlueButtons());
        redHits = getHitsCount(instance.getRedButtons());

        if (blueHits + redHits >= DBoolean.getJudges() - 1) {
            if (blueHits > redHits) {
                if (!blueScore.isRun()) {
                    blueScore.setItems(instance.getBlueButtons(), score);
                    new Thread(blueScore).start();
                }
            } else {
                if (!(blueHits == redHits) && !redScore.isRun()) {
                    redScore.setItems(instance.getRedButtons(), score);
                    new Thread(redScore).start();
                }
            }
        }
    }

    public static int getHitsCount(DBoolean[] buttons) {
        int count = 0;
        for (int i = 0; i < DBoolean.getJudges(); i++) {
            if (buttons[i].isHit()) {
                count++;
            }
        }
        return count;
    }

    public static void drawImage(int x, int y, int width, int height, Texture texture) {
        Color.white.bind();
        texture.bind();
        glBegin(GL_QUADS);
        glTexCoord2f(0f, 0f);
        glVertex2i(x, y);
        glTexCoord2f(1f, 0f);
        glVertex2i(x + width, y);
        glTexCoord2f(1f, 1f);
        glVertex2i(x + width, y + height);
        glTexCoord2f(0f, 1f);
        glVertex2i(x, y + height);
        glEnd();
        glDisable(GL_TEXTURE_2D);
    }

    public static void drawCircle(int x, int y, int radius, int dotNumber) {
        glPushMatrix();
        glTranslated(x + radius, y + radius, 0);
        glBegin(GL_POLYGON);
        double angle = 2 * Math.PI / dotNumber;
        for (int i = 1; i <= dotNumber; i++) {
            double xP = radius * Math.cos(angle * i);
            double yP = radius * Math.sin(angle * i);
            glVertex2d(xP, yP);
        }
        glEnd();
        glPopMatrix();
        glColor3d(0, 0, 0);
    }

    public static void showDonChan(boolean redScore, boolean blueScore) {
        Texture redDonTex = ImageUtil.loadImage(ImageUtil.DONCHAN_RED);
        Texture blueDonTex = ImageUtil.loadImage(ImageUtil.DONCHAN_BLUE);
        int y = Display.getHeight() - SIZE_IMAGE;
        int half_x = Display.getWidth() / 2;
        if (redScore) {
            drawImage(half_x - SIZE_IMAGE, y, SIZE_IMAGE, SIZE_IMAGE, redDonTex);
        }
        if (blueScore) {
            drawImage(Display.getWidth() - SIZE_IMAGE, y, SIZE_IMAGE, SIZE_IMAGE, blueDonTex);
        }
    }

    public static void drawReminders(int y, int remRed, int remBlue) {
        for (int i = 0; i < remRed; i++) {
            glColor3d(1, 1, 0);
            drawCircle(OFFSET_REMINDER + 30*i, y, 10, 16);
        }
        int blue_x = Display.getWidth() / 2 + OFFSET_REMINDER;
        for (int i = 0; i < remBlue; i++) {
            glColor3d(1, 1, 0);
            drawCircle(blue_x + 30*i, y, 10, 16);
        }
    }
    public static void drawWarnings(int y, int warRed, int warBlue) {
        for (int i = 0; i < warRed; i++) {
            glColor3d(1, 0, 1);
            drawCircle(OFFSET_WARNING + 30*i, y, 10, 16);
        }
        int blue_x = Display.getWidth() / 2 + OFFSET_WARNING;
        for (int i = 0; i < warBlue; i++) {
            glColor3d(1, 0, 1);
            drawCircle(blue_x + 30*i, y, 10, 16);
        }
    }
    public static void checkWarningsReminders(Score score, Settings settings) {
        if (score.getRemBlue() >= settings.getReminderPoint()) {
            score.setRemBlue(0);
            score.setWarnBlue(score.getWarnBlue() + 1);
        }
        if (score.getRemRed() >= settings.getReminderPoint()) {
            score.setRemRed(0);
            score.setWarnRed(score.getWarnRed() + 1);
        }
        if (score.getWarnBlue() >= settings.getWarningPoint()) {
            score.setWarnBlue(0);
            score.setcBlue(score.getcBlue() - settings.getTotalPoint());
        }
        if (score.getWarnRed() >= settings.getWarningPoint()) {
            score.setWarnRed(0);
            score.setcRed(score.getcRed() - settings.getTotalPoint());
        }
    }
}
