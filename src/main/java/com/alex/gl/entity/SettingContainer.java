package com.alex.gl.entity;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 11.10.13
 * Time: 0:03
 */
public class SettingContainer {

    private DisplayMode displayMode;

    public DisplayMode getDisplayMode() {
        return displayMode;
    }

    public void setDisplayMode(DisplayMode displayMode) {
        this.displayMode = displayMode;
    }
}
