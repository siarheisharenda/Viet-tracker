package com.alex.gl.core.action;

import com.alex.gl.entity.Score;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 29.09.13
 * Time: 14:35
 */
public class SecondsTimer implements Runnable {

    private int seconds;
    private int startSeconds;
    private int delay;
    private Thread thread;
    private boolean run = false;
    private Score score;
    private RoundStatus status;

    public SecondsTimer(int delay, int seconds, Score score) {
        this.delay = delay;
        this.seconds = seconds;
        this.startSeconds = seconds;
        this.score = score;
        status = RoundStatus.START_OF_MATCH;
    }

    public int getSeconds() {
        return seconds;
    }

    public void substractSeconds() {
        seconds--;
    }

    public void reset() {
        seconds = startSeconds;
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
        status = RoundStatus.BEGIN;
        run = true;
    }

    public boolean isRun() {
        return run;
    }

    public void stop() {
        thread.interrupt();
    }

    public void run() {
        try {
            while (seconds > 0) {
                Thread.sleep(delay);
                substractSeconds();
            }
//            score.setRound(score.getRound() + 1);
//            reset();
            if (score.getRound() + 1 != score.getSettings().getRoundsInMatch()) {
                status = RoundStatus.NEXT_ROUND;
            } else {
                status = RoundStatus.FINISH;
            }
        } catch (InterruptedException ex) {
        }
        run = false;
    }

    public static enum RoundStatus {
        START_OF_MATCH, BEGIN, NEXT_ROUND, FINISH;
    }
}
