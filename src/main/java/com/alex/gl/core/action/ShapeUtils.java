package com.alex.gl.core.action;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 06.10.13
 * Time: 23:20
 */
public class ShapeUtils {

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
        int y = (int)(height * 0.3);
        int y2 = y + 50;
        int y3 = y2 + 50;
        int y4 = y3 + 50;

        drawEmptyQuad(halfWidth - 50, y, 50, 50, false);
        drawEmptyQuad(halfWidth - 50, y2, 50, 50, false);
        drawEmptyQuad(halfWidth - 50, y3, 50, 50, false);
        drawEmptyQuad(halfWidth - 50, y4, 50, 50, false);
        drawEmptyQuad(halfWidth, y, 50, 50, false);
        drawEmptyQuad(halfWidth, y2, 50, 50, false);
        drawEmptyQuad(halfWidth, y3, 50, 50, false);
        drawEmptyQuad(halfWidth, y4, 50, 50, false);
    }
}
