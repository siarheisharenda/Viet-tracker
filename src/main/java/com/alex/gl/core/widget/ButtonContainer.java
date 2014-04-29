package com.alex.gl.core.widget;

import com.alex.gl.entity.DBoolean;
import org.lwjgl.opengl.Display;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 07.11.13
 * Time: 17:07
 */
public class ButtonContainer {

    public static final int QUAD_WIDTH = 30;
    public static ButtonContainer instance = new ButtonContainer();

    private DBoolean b1 = new DBoolean();
    private DBoolean b2 = new DBoolean(true);
    private DBoolean b3 = new DBoolean();
    private DBoolean b4 = new DBoolean(true);
    private DBoolean b5 = new DBoolean();
    private DBoolean b6 = new DBoolean(true);
    private DBoolean b7 = new DBoolean();
    private DBoolean b8 = new DBoolean(true);
    private DBoolean[] buttons = new DBoolean[]{b1, b2, b3, b4, b5, b6, b7, b8};
    private DBoolean[] blueButtons = new DBoolean[]{b2, b4, b6, b8};
    private DBoolean[] redButtons = new DBoolean[]{b1, b3, b5, b7};

    private ButtonContainer() {
    }

    public void reInitPositions() {
        int height = Display.getHeight();
        int width = Display.getWidth();
        int y = (int) (height * 0.3);
        int y2 = y + QUAD_WIDTH;
        int y3 = y2 + QUAD_WIDTH;
        int y4 = y3 + QUAD_WIDTH;

        b1.setPosition(0, y, QUAD_WIDTH, QUAD_WIDTH);
        b2.setPosition(width - QUAD_WIDTH, y, QUAD_WIDTH, QUAD_WIDTH);
        b3.setPosition(0, y2, QUAD_WIDTH, QUAD_WIDTH);
        b4.setPosition(width - QUAD_WIDTH, y2, QUAD_WIDTH, QUAD_WIDTH);
        b5.setPosition(0, y3, QUAD_WIDTH, QUAD_WIDTH);
        b6.setPosition(width - QUAD_WIDTH, y3, QUAD_WIDTH, QUAD_WIDTH);
        b7.setPosition(0, y4, QUAD_WIDTH, QUAD_WIDTH);
        b8.setPosition(width - QUAD_WIDTH, y4, QUAD_WIDTH, QUAD_WIDTH);
    }

    public DBoolean[] getButtons() {
        return buttons;
    }

    public void drawButtons() {
        for (int i = 0; i < DBoolean.getJudges() * 2; i++) {
            buttons[i].draw();
        }
    }

    public DBoolean[] getBlueButtons() {
        return blueButtons;
    }

    public DBoolean[] getRedButtons() {
        return redButtons;
    }
}
