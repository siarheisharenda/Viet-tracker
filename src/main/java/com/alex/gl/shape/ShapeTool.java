package com.alex.gl.shape;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ARBMultisample;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Point;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: elenaliux
 * Date: 8/18/13
 * Time: 7:20 PM
 */
public class ShapeTool {

    static int spin;

    /**
     * Serpinsky triangle.
     */
    public static void serpinsky() {
        Point[] points = new Point[]{new Point(10, 10), new Point(700, 20), new Point(400, 600)};
        for (int i = 0; i < 3; i++) {
            drawDot(points[i].getX(), points[i].getY());
        }
        drawTriangle(points[0], points[1], points[2], 10);
        glFlush();
    }

    private static void drawTriangle(Point p1, Point p2, Point p3, int level) {
        if (level > 0) {
            Point p12 = createPoint(p1, p2);
            Point p13 = createPoint(p1, p3);
            Point p23 = createPoint(p2, p3);
            drawDot(p12.getX(), p12.getY());
            drawDot(p23.getX(), p23.getY());
            drawDot(p13.getX(), p13.getY());
            drawTriangle(p1, p12, p13, level - 1);
            drawTriangle(p2, p12, p23, level - 1);
            drawTriangle(p3, p23, p13, level - 1);
        } else {
            return;
        }
    }

    /**
     * Draws dot.
     * @param x - coord X
     * @param y - coord Y
     */
    public static void drawDot(int x, int y) {
        glBegin(GL_POINTS);
        glVertex2i(x, y);
        glEnd();
    }

    /**
     * Draws cube.
     */
    public static void drawCube() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glPolygonMode(GL_BACK, GL_LINE);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_FRONT);
        glPushMatrix();
        glTranslated(100, 100, 0);
        glRotated(spin, 1, 1, 1);
        glBegin(GL_QUAD_STRIP);
        draw();
        glEnd();
        glPopMatrix();
        glFlush();
    }

    private static void draw() {
        glVertex3i(-50, -50, 50);
        glVertex3i(-50, 50, 50);
        glVertex3i(50, -50, 50);
        glVertex3i(50, 50, 50);
        glVertex3i(50, -50, -50);
        glVertex3i(50, 50, -50);
        glVertex3i(-50, -50, -50);
        glVertex3i(-50, 50, -50);
        glVertex3i(-50, -50, 50);
        glVertex3i(-50, 50, 50);
    }

    /**
     * Draw polygon.
     *
     * @param points - list of points.
     */
    public static void drawPoly(List<Point> points) {
        glBegin(GL_LINE_STRIP);
        for (Point point : points) {
            glVertex2i(point.getX(), point.getY());
        }
        glEnd();
    }

    /**
     * Draws chess board.
     * @param width - width
     * @param height - height
     */
    public static void drawChessBoard(int width, int height) {
        int rwidth = width / 8;
        int rheight = height / 8;
        int draw = -1;
        glColor3f(0, 0, 0);
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                int x = i * rwidth;
                int y = j * rheight;
                if (draw > 0) {
                    glRecti(x, y, x + rwidth, y + rheight);
                }
                draw *= -1;
            }
            draw *= -1;
        }
    }

    private static Point createPoint(Point p, Point p2) {
        return new Point(((p.getX() + p2.getX()) / 2), (p.getY() + p2.getY()) / 2);
    }

    /**
     * Animator inner class.
     */
    public static class Animator extends Thread {

        public void run() {
            try {
                while (!Thread.interrupted()) {
                    Thread.sleep(50);

                    spin += 1;
                    if (spin > 360) spin -= 360;
                }
            } catch (InterruptedException e) {
            }
        }
    }
}
