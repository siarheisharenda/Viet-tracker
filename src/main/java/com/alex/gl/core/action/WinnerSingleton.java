package com.alex.gl.core.action;

import com.alex.gl.entity.Score;
import com.alex.gl.entity.Settings;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 30.10.13
 * Time: 8:09
 */
public class WinnerSingleton {

    public static WinnerSingleton instance = new WinnerSingleton();

    private WinnerSingleton() {
    }

    private Boolean blue;

    public Boolean isBlue() {
        return blue;
    }

    /**
     * Sets true if won blue.
     * Sets null if winner has been unsigned.
     * @param blue - true if won blue.
     */
    public void setBlue(Boolean blue) {
        this.blue = blue;
    }

    public void reset() {
        blue = null;
    }

    public void checkWin(Settings settings, Score score, SecondsTimer timer) {
        if (WinnerSingleton.instance.isBlue() == null) {
            int gapPoint = settings.getPointGap();
            if (Math.abs(score.getcRed() - score.getcBlue()) >= gapPoint) {
                blue = score.getcBlue() > score.getcRed();
                timer.finishFight();
                OpenAlSounder.instance.playGong();
                return;
            }
            int capPoint = settings.getPointCap();
            if (score.getpBlue() >= capPoint || score.getpRed() >= capPoint) {
                blue = score.getpRed() > score.getpBlue();
                timer.finishFight();
                OpenAlSounder.instance.playGong();
                return;
            }
        }
    }
}
