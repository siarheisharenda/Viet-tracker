package com.alex.gl.entity;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 29.09.13
 * Time: 9:10
 */
public class Score {

    private final float POSITIVE_DELTA = 0.3f;
    private final float NEGATIVE_DELTA = 0.8f;

    private int cRed;
    private int cBlue;
    private boolean donRed;
    private boolean donBlue;
    private int round = 1;
    private float redDelta;
    private float blueDelta;
    private Settings settings;
    private int pBlue;
    private int pRed;

    private int remBlue;
    private int remRed;
    private int warnBlue;
    private int warnRed;

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

    public void setpBlue(int step) {
        if (pBlue + step> -1) {
            this.pBlue += step;
            setcBlue(this.cBlue - step);
        }
    }

    public int getpRed() {
        return pRed;
    }

    public void setpRed(int step) {
        if (pRed + step > -1) {
            this.pRed += step;
            setcRed(this.cRed - step);
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
        remRed = 0;
        remBlue = 0;
        warnBlue = 0;
        warnRed = 0;
        donRed = false;
        donBlue = false;
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

    public boolean getDonRed() {
        return donRed;
    }

    public boolean getDonBlue() {
        return donBlue;
    }

    public void setDonRed(boolean donRed) {
        this.donRed = donRed;
    }

    public void setDonBlue(boolean donBlue) {
        this.donBlue = donBlue;
    }

    public int getRemBlue() {
        return remBlue;
    }

    public void setRemBlue(int remBlue) {
        if (remBlue >= 0) {
            this.remBlue = remBlue;
        }
    }

    public int getRemRed() {
        return remRed;
    }

    public void setRemRed(int remRed) {
        if (remRed >= 0) {
            this.remRed = remRed;
        }
    }

    public int getWarnBlue() {
        return warnBlue;
    }

    public void setWarnBlue(int warnBlue) {
        if (warnBlue >= 0) {
            this.warnBlue = warnBlue;
        }
    }

    public int getWarnRed() {
        return warnRed;
    }

    public void setWarnRed(int warnRed) {
        if (warnRed >= 0) {
            this.warnRed = warnRed;
        }
    }
}
