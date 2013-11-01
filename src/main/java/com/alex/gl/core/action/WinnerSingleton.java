package com.alex.gl.core.action;

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

    public void setBlue(Boolean blue) {
        this.blue = blue;
    }

    public void reset() {
        blue = null;
    }
}
