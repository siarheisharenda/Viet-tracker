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
        status = RoundStatus.READY;
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

    private void start() {
        thread = new Thread(this);
        thread.start();
        run = true;
    }

    public void startFight() {
        start();
        status = RoundStatus.FIGHT;
    }

    public void startBreak() {
        start();
        status = RoundStatus.BREAK;
    }

    public boolean isRun() {
        return run;
    }

    public void stop() {
        thread.interrupt();
    }

    public void run() {
        try {
            if (RoundStatus.BREAK.equals(status)) {
                count();
                status = RoundStatus.READY;
            } else {
                count();
                if (score.getRound() + 1 > score.getSettings().getRoundsInMatch()) {
                    status = RoundStatus.FINISH;
                } else {
                    status = RoundStatus.NEXT_ROUND;
                }
            }
        } catch (InterruptedException ex) {
        }
        run = false;
    }

    private void count() throws InterruptedException {
        while (seconds > 0) {
            Thread.sleep(delay);
            substractSeconds();
        }
    }

    public static enum RoundStatus {
        READY, FIGHT, NEXT_ROUND, FINISH, BREAK;
    }

    public RoundStatus getStatus() {
        return status;
    }

    public void breakRound() {
        status = RoundStatus.BREAK;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
