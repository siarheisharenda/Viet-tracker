package com.alex.gl.entity;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 29.09.13
 * Time: 9:10
 */
public class Score {

    private final float POSITIVE_DELTA = 0.3f;
    private final float NEGATIVE_DELTA = 0.6f;

    private int cRed;
    private int cBlue;
    private float redDelta;
    private float blueDelta;
    private int round = 1;
    private Settings settings;
    private int pBlue;
    private int pRed;

    public Score(Settings settings) {
        this.settings = settings;
    }

    public int getcRed() {
        return cRed;
    }

    public synchronized void setcRed(int cRed) {
        if (isAllow(cRed)) {
            return;
        }
        this.cRed = cRed;
        redDelta = (cRed > 9) ? POSITIVE_DELTA : (cRed < 0) ? NEGATIVE_DELTA : 0f;
    }

    public int getcBlue() {
        return cBlue;
    }

    public synchronized void setcBlue(int cBlue) {
        if (isAllow(cBlue)) {
            return;
        }
        this.cBlue = cBlue;
        blueDelta = (cBlue > 9) ? POSITIVE_DELTA : (cBlue < -0) ? NEGATIVE_DELTA : 0f;
    }

    public int getpBlue() {
        return pBlue;
    }

    public void setpBlue(int pBlue) {
        if (pBlue > -1) {
            this.pBlue = pBlue;
        }
    }

    public int getpRed() {
        return pRed;
    }

    public void setpRed(int pRed) {
        if (pRed > -1) {
            this.pRed = pRed;
        }
    }

    public float getRedDelta() {
        return redDelta;
    }

    public float getBlueDelta() {
        return blueDelta;
    }

    public void reset() {
        cRed = 0;
        cBlue = 0;
        pBlue = 0;
        pRed = 0;
        redDelta = 0;
        blueDelta = 0;
        round = 1;
    }

    private boolean isAllow(int count) {
        return count > 99 || count < -99;
    }

    public void setRound(int round) {
        if (round > settings.getRoundsInMatch()) {
            return;
        }
        this.round = round;
    }

    public int getRound() {
        return round;
    }

    public Settings getSettings() {
        return settings;
    }
}
