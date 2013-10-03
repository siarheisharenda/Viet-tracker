package com.alex.gl.core.action;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 29.09.13
 * Time: 14:35
 * To change this template use File | Settings | File Templates.
 */
public class SecondsTimer implements Runnable {

    private int seconds;
    private int startSeconds;
    private int delay;
    private Thread thread;
    private boolean run = false;

    public SecondsTimer(int delay, int seconds) {
        this.delay = delay;
        this.seconds = seconds;
        this.startSeconds = seconds;
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
        } catch (InterruptedException ex) {
        }
        run = false;
    }
}
