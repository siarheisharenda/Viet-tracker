package com.alex.gl.core.action;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 06.10.13
 * Time: 23:20
 */
public class ShapeUtils {

    public static final int QUAD_WIDTH = 30;

    public static void drawEmptyQuad(int x, int y, int width, int height, boolean fill) {
        int mode = (fill) ? GL_QUADS : GL_LINE_LOOP;
        glBegin(mode);
        glColor3f(1, 1, 0);
        glVertex2d(x, y);
        glVertex2d(x + width, y);
        glVertex2d(x + width, y + height);
        glVertex2d(x, y + height);
        glEnd();
    }

    public static void drawJoystickQuads(int width, int height, int halfWidth) {
        int y = (int) (height * 0.3);
        int y2 = y + QUAD_WIDTH;
        int y3 = y2 + QUAD_WIDTH;
        int y4 = y3 + QUAD_WIDTH;

        drawEmptyQuad(width - QUAD_WIDTH, y, QUAD_WIDTH, QUAD_WIDTH, false);
        drawEmptyQuad(width - QUAD_WIDTH, y2, QUAD_WIDTH, QUAD_WIDTH, false);
        drawEmptyQuad(width - QUAD_WIDTH, y3, QUAD_WIDTH, QUAD_WIDTH, false);
        drawEmptyQuad(width - QUAD_WIDTH, y4, QUAD_WIDTH, QUAD_WIDTH, false);
        drawEmptyQuad(0, y, QUAD_WIDTH, QUAD_WIDTH, false);
        drawEmptyQuad(0, y2, QUAD_WIDTH, QUAD_WIDTH, false);
        drawEmptyQuad(0, y3, QUAD_WIDTH, QUAD_WIDTH, false);
        drawEmptyQuad(0, y4, QUAD_WIDTH, QUAD_WIDTH, false);
    }
}
