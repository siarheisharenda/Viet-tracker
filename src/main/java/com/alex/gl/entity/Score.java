package com.alex.gl.entity;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 29.09.13
 * Time: 9:10
 * To change this template use File | Settings | File Templates.
 */
public class Score {

    private final float CONST_DELTA = 0.02f;

    private int cRed;
    private int cBlue;
    private float redDelta;
    private float blueDelta;

    public int getcRed() {
        return cRed;
    }

    public void setcRed(int cRed) {
        if (cRed > 99 || cRed < 0) {
            return;
        }
        this.cRed = cRed;
        redDelta = (cRed > 9) ? CONST_DELTA : 0f;
    }

    public int getcBlue() {
        return cBlue;
    }

    public void setcBlue(int cBlue) {
        if (cBlue > 99 || cBlue < 0) {
            return;
        }
        this.cBlue = cBlue;
        blueDelta = (cBlue > 9) ? CONST_DELTA : 0f;
    }

    public float getRedDelta() {
        return redDelta;
    }

    public void setRedDelta(float redDelta) {
        this.redDelta = redDelta;
    }

    public float getBlueDelta() {
        return blueDelta;
    }

    public void setBlueDelta(float blueDelta) {
        this.blueDelta = blueDelta;
    }

    public void reset() {
        cRed = 0;
        cBlue = 0;
        redDelta = 0;
        blueDelta = 0;
    }
}
