package com.alex.gl;

import java.awt.*;

import javax.swing.*;

/**
 * Class of viet-tracker.
 * <p/>
 * Copyright (C) 2013 copyright.com
 * <p/>
 * Date: 10/22/13
 *
 * @author Aliaksandr Shynkevich
 */
public class TestWindow extends Canvas {

    public TestWindow() throws HeadlessException {
        setSize(new Dimension(1000, 1000));
    }

    public void init() {
        JLabel label = new JLabel("String");
        Font font = new Font("Arial", Font.BOLD, 172);
        label.setFont(font);

        System.out.println(font.toString());
        setVisible(true);
    }
}
