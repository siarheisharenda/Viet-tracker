package com.alex.gl.core.action;

import com.alex.gl.entity.Score;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 29.09.13
 * Time: 9:16
 * To change this template use File | Settings | File Templates.
 */
public class ActionUtil {

    private static Logger log = Logger.getLogger(ActionUtil.class.getName());

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
                if (Keyboard.getEventKey() == Keyboard.KEY_F) {
                    try {
                        Display.setFullscreen(!Display.isFullscreen());
                    } catch (LWJGLException e) {
                        e.printStackTrace();
                        System.exit(0);
                    }
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

    public static Controller initJoystick() {
        Controller joystick = null;
        for (Controller c : ControllerEnvironment.getDefaultEnvironment().getControllers()) {
            if (c.getType() == Controller.Type.STICK) {
                joystick = c;
                System.out.println(joystick.getName());
            }
        }

        if (joystick == null) {
            System.err.println("No joystick was found.");
        } else {
            for (Component component : joystick.getComponents()) {
                System.out.println(component.getName());
            }
        }

        return joystick;
    }

    public static void joyDetect(Controller controller) {
        controller.poll();
        boolean button1 = false;
        boolean button2 = false;
        boolean button3 = false;
        boolean button4 = false;
        boolean button5 = false;
        boolean button6 = false;
        boolean button7 = false;
        boolean button8 = false;
        boolean button9 = false;
        boolean button10 = false;
        for (Component c : controller.getComponents()) {
            if (c.getName().contains("0")) {
                button1 = c.getPollData() == 1f;
            }
            if (c.getName().contains("1")) {
                button2 = c.getPollData() == 1f;
            }
            if (c.getName().contains("2")) {
                button3 = c.getPollData() == 1f;
            }
            if (c.getName().contains("3")) {
                button4 = c.getPollData() == 1f;
            }
            if (c.getName().contains("4")) {
                button5 = c.getPollData() == 1f;
            }
            if (c.getName().contains("5")) {
                button6 = c.getPollData() == 1f;
            }
            if (c.getName().contains("6")) {
                button7 = c.getPollData() == 1f;
            }
            if (c.getName().contains("7")) {
                button8 = c.getPollData() == 1f;
            }
            if (c.getName().contains("8")) {
                button9 = c.getPollData() == 1f;
            }
            if (c.getName().contains("9")) {
                button10 = c.getPollData() == 1f;
            }
        }
        if (button1) {
            System.out.println("kick 1");
        }
        if (button2) {
            System.out.println("kick 2");
        }
        if (button3) {
            System.out.println("kick 3");
        }
        if (button4) {
            System.out.println("kick 4");
        }
        if (button5) {
            System.out.println("kick 5");
        }
        if (button6) {
            System.out.println("kick 6");
        }
        if (button7) {
            System.out.println("kick 7");
        }
        if (button8) {
            System.out.println("kick 8");
        }
        if (button9) {
            System.out.println("kick 9");
        }
        if (button10) {
            System.out.println("kick 10");
        }
    }

}