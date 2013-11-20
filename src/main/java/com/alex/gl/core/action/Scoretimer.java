package com.alex.gl.core.action;

import com.alex.gl.entity.DBoolean;
import com.alex.gl.entity.Score;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 07.11.13
 * Time: 16:32
 */
public class ScoreTimer implements Runnable {

    private static int buttonDelay = 750;

    private DBoolean[] score;
    private Score scoreManager;
    private boolean run = false;
    private boolean blue;

    public ScoreTimer(boolean isBlue) {
        this.blue = isBlue;
    }

    public void setItems(DBoolean[] score, Score scoreManager) {
        this.score = score;
        this.scoreManager = scoreManager;
    }

    public boolean isRun() {
        return run;
    }

    @Override
    public void run() {
        run = true;
        try {
            Thread.sleep(750);
            int point = checkPoint();
            if (blue) {
                scoreManager.setcBlue(scoreManager.getcBlue() + point);
            } else {
                scoreManager.setcRed(scoreManager.getcRed() + point);
            }
            clear();
        } catch (InterruptedException e) {
        } finally {
            run = false;
        }
    }

    private int checkPoint() {
        int hits = 0;
        int dHits = 0;
        for (int i = 0; i < DBoolean.getJudges(); i++) {
            if (score[i].isHit()) {
                if (score[i].isDoubleClick()) {
                    dHits++;
                } else {
                    hits++;
                }
            }
        }
        return (dHits > hits) ? 2 : 1;
    }

    private void clear() {
        for (int i = 0; i < DBoolean.getJudges(); i++) {
            score[i].reset();
        }
    }
}
