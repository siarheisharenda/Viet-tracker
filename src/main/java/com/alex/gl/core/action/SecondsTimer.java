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
    private int snapShot;
    private int delay = 1000;
    private Thread thread;
    private boolean run = false;
    private Score score;
    private RoundStatus status;
    private int medical;

    public SecondsTimer(int seconds, int medical ,Score score) {
        this.seconds = seconds;
        this.startSeconds = seconds;
        this.score = score;
        this.medical = medical;
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
        status = RoundStatus.READY;
    }

    private void start() {
        thread = new Thread(this);
        thread.start();
        run = true;
    }

    public void startFight() {
        status = RoundStatus.FIGHT;
        start();
    }

    public void startBreak() {
        status = RoundStatus.BREAK;
        start();
    }

    public boolean isRun() {
        return run;
    }

    public void stop() {
        if (isRun()) {
            thread.interrupt();
        }
        if (snapShot != 0) {
            seconds = snapShot;
            snapShot = 0;
        }
    }

    public void run() {
        try {
            switch (status) {
                case BREAK:
                    count();
                    status = RoundStatus.READY;
                    break;
                case MEDICAL:
                    count();
                    status = RoundStatus.PAUSE;
                    seconds = snapShot;
                    snapShot = 0;
                    break;
                default:
                    count();
                    if (score.getRound() + 1 > score.getSettings().getRoundsInMatch()) {
                        status = RoundStatus.FINISH;
                        OpenAlSounder.instance.playGong();
                    } else {
                        status = RoundStatus.NEXT_ROUND;
                    }
                    break;
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

    public RoundStatus getStatus() {
        return status;
    }

    public void setContinue() {
        status = RoundStatus.PAUSE;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void startMedical() {
        stop();
        snapShot = seconds;
        seconds = medical;
        status = RoundStatus.MEDICAL;
        start();
    }

    public void finishFight() {
        stop();
        status = RoundStatus.FINISH;
    }

    public static enum RoundStatus {
        READY, FIGHT, NEXT_ROUND, FINISH, BREAK, MEDICAL, PAUSE;
    }
}
