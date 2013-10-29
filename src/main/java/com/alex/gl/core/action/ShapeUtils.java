package com.alex.gl.core.action;

import com.alex.gl.entity.DBoolean;
import com.alex.gl.entity.Score;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.alex.gl.core.action.ActionUtil.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 06.10.13
 * Time: 23:20
 */
public class ShapeUtils {

    public static final int QUAD_WIDTH = 30;
    private static final List<DBoolean> blueScore = new ArrayList<>();
    private static final List<DBoolean> redScore = new ArrayList<>();

    public static DBoolean drawEmptyQuad(int x, int y, int width, int height, DBoolean button) {
        boolean fill = button.isHit();         
        int mode = (fill) ? GL_QUADS : GL_LINE_LOOP;
        glBegin(mode);
        glColor3f(1, 1, 0);
        glVertex2d(x, y);
        glVertex2d(x + width, y);
        glVertex2d(x + width, y + height);
        glVertex2d(x, y + height);
        glEnd();
        return fill ? button : null;        
    }

    public static void drawJoystickQuads(int width, int height, Score score) {
        int y = (int) (height * 0.3);
        int y2 = y + QUAD_WIDTH;
        int y3 = y2 + QUAD_WIDTH;
        int y4 = y3 + QUAD_WIDTH;

        addScore(drawEmptyQuad(width - QUAD_WIDTH, y, QUAD_WIDTH, QUAD_WIDTH, b2));
        addScore(drawEmptyQuad(width - QUAD_WIDTH, y2, QUAD_WIDTH, QUAD_WIDTH, b4));
        addScore(drawEmptyQuad(width - QUAD_WIDTH, y3, QUAD_WIDTH, QUAD_WIDTH, b6));
        addScore(drawEmptyQuad(width - QUAD_WIDTH, y4, QUAD_WIDTH, QUAD_WIDTH, b8));
        addScore(drawEmptyQuad(0, y, QUAD_WIDTH, QUAD_WIDTH, b1));
        addScore(drawEmptyQuad(0, y2, QUAD_WIDTH, QUAD_WIDTH, b3));
        addScore(drawEmptyQuad(0, y3, QUAD_WIDTH, QUAD_WIDTH, b5));
        addScore(drawEmptyQuad(0, y4, QUAD_WIDTH, QUAD_WIDTH, b7));
        
        if (blueScore.size() + redScore.size() >= DBoolean.getJudges()) {
            if (blueScore.size() > redScore.size()) {
                score.setcBlue(score.getcBlue() + 1);
            } else {
                score.setcRed(score.getcRed() + 1);
            }
            clearScore(blueScore);
            clearScore(redScore);
        } else {
            blueScore.clear();
            redScore.clear();
        }
    }

    private static void clearScore(List<DBoolean> buttons) {
        for (DBoolean button: buttons) {
            button.reset();
        }
        buttons.clear();
    }

    private static void addScore(DBoolean dBoolean) {
        if (dBoolean != null) {
            if (dBoolean.isBlue()) {
                blueScore.add(dBoolean);
            } else {
                redScore.add(dBoolean);
            }
        }
    }
}
