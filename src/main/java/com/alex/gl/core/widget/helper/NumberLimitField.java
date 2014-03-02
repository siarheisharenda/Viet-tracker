package com.alex.gl.core.widget.helper;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 22.02.14
 * Time: 22:54
 */
public class NumberLimitField extends LimitField {
    private Pattern pattern = Pattern.compile("[0-9]*");

    public NumberLimitField(int limit) {
        super(limit);
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (pattern.matcher(str).matches()) {
            super.insertString(offs, str, a);
        }
    }
}
