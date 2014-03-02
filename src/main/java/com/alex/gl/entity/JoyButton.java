package com.alex.gl.entity;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 07.11.13
 * Time: 16:57
 */
public abstract class JoyButton {

    private int x;
    private int y;
    private int width;
    private int height;

    public void setPosition(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw() {
        boolean fill = isHit();
        int mode = (fill) ? GL_QUADS : GL_LINE_LOOP;
        glBegin(mode);
        glColor3f(1, 1, (isDoubleClick()) ? 1 : 0);
        glVertex2d(x, y);
        glVertex2d(x + width, y);
        glVertex2d(x + width, y + height);
        glVertex2d(x, y + height);
        glEnd();
        glFlush();
    }

    protected abstract boolean isDoubleClick();
    public abstract boolean isHit();
}
