package com.alex.gl.entity;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 27.10.13
 * Time: 20:13
 */
public class DBoolean extends JoyButton implements Runnable {

    private static float delay;
    private static int judges;

    private boolean run = false;
    private boolean hit = false;
    private boolean blue = false;
    private boolean released = true;
    private boolean doubleClick = false;

    public DBoolean(boolean blue) {
        this.blue = blue;
    }

    public static int getJudges() {
        return judges;
    }

    public static void setJudges(int judges) {
        DBoolean.judges = judges;
    }

    public DBoolean() {
    }

    public static void setDelay(float delay) {
        DBoolean.delay = delay;
    }

    public synchronized void hit() {
        this.hit = true;
        this.released = false;
    }

    public synchronized void reset() {
        this.hit = false;
    }

    public synchronized boolean isHit() {
        return hit;
    }

    public synchronized boolean isRun() {
        return run;
    }

    public boolean isReleased() {
        return released;
    }

    public void release() {
        this.released = true;
    }

    public boolean isBlue() {
        return blue;
    }

    public synchronized boolean isDoubleClick() {
        return doubleClick;
    }

    public synchronized void setDoubleClick(boolean doubleClick) {
        this.doubleClick = doubleClick;
        released = false;
    }

    @Override
    public void run() {
        run = true;
        try {
            Thread.sleep((long)(delay * 1000));
        } catch (InterruptedException ex) {
        } finally {
            run = false;
            hit = false;
            setDoubleClick(false);
        }
    }
}
