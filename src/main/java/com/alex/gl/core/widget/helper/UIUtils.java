package com.alex.gl.core.widget.helper;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * Date: 13/04/13
 * Time: 21:20
 */
public class UIUtils {

    public static JTextField createField(int limit) {
        JTextField field = new JTextField();
        field.setColumns(5);
        field.setDocument(new LimitField(limit));
        return field;
    }
}
