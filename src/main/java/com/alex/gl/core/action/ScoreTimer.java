package com.alex.gl.core.action;

import com.alex.gl.entity.Score;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 27.10.13
 * Time: 11:52
 */
public class ScoreTimer implements Runnable {

    private Score score;
    private float secDelay;

    public ScoreTimer(Score score, float secDelay) {
        this.score = score;
        this.secDelay = secDelay;
    }

    @Override
    public void run() {
       try {
           Thread.sleep((long)(secDelay * 1000));
       } catch (InterruptedException ex) {
           return;
       }
    }
}
