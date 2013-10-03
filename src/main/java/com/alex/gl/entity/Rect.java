package com.alex.gl.entity;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 29.09.13
 * Time: 13:52
 * To change this template use File | Settings | File Templates.
 */
public class Rect {

    public int x;
    public int y;
    public int x2;
    public int y2;

    public Rect(int x, int y, int x2, int y2) {
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
    }

    public int getWidth() {
        return Math.abs(x - x2);
    }

    public int getHeight() {
        return Math.abs(y - y2);
    }
}
