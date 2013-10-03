package com.alex.gl.core.widget.helper;

import org.apache.commons.lang.StringUtils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Created with IntelliJ IDEA.
 * Date: 4/9/13
 * Time: 1:38 PM
 */
public class LimitField extends PlainDocument {

    private int limit;

    public LimitField(int limit) {
        this.limit = limit;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (StringUtils.isEmpty(str)) {
            return;
        }
        if (getLength() + str.length() <= limit) {
            super.insertString(offs, str, a);
        }
    }
}
