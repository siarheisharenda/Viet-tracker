package com.alex.gl.core;

import java.awt.*;

/**
 * Class of viet-tracker.
 * <p/>
 * Copyright (C) 2013 copyright.com
 * <p/>
 * Date: 10/21/13
 *
 * @author Aliaksandr Shynkevich
 */
public class GlCanvas extends Canvas {

    public GlCanvas() {
        isDisplayable();
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        System.out.println("I'm here!!!");
    }
}
