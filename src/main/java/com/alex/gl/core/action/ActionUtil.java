package com.alex.gl.core.action;

import com.alex.gl.entity.Score;
import org.lwjgl.input.Keyboard;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 29.09.13
 * Time: 9:16
 * To change this template use File | Settings | File Templates.
 */
public class ActionUtil {

    public static void controlDetect(Score score, SecondsTimer timer) {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_A) {
                    score.setcRed(score.getcRed() + 1);
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_Z) {
                    score.setcRed(score.getcRed() - 1);
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_K) {
                    score.setcBlue(score.getcBlue() + 1);
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_M) {
                    score.setcBlue(score.getcBlue() - 1);
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_R) {
                    score.reset();
                }
                timeSwitcher(timer);
            }
        }
    }

    public static void timeSwitcher(SecondsTimer timer) {
        if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
            if (timer.isRun()) {
                timer.stop();
            } else {
                timer.start();
            }
        }
        if (Keyboard.getEventKey() == Keyboard.KEY_T) {
            timer.stop();
            timer.reset();
        }
    }

    public static String convertTime(int seconds) {
        int minutes = seconds / 60;
        seconds = seconds - 60 * minutes;
        String stringSecond = (seconds > 9) ? String.valueOf(seconds) : "0" + seconds;
        return String.format("%d : %s", minutes, stringSecond);
    }

}
