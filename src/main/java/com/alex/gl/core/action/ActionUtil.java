package com.alex.gl.core.action;

import com.alex.gl.entity.Score;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import javax.swing.*;
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
                timer.startFight();
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

    public static void roundControl(SecondsTimer timer, Score score) {
        if (SecondsTimer.RoundStatus.NEXT_ROUND.equals(timer.getStatus())) {
            score.setRound(score.getRound() + 1);
            timer.setSeconds(score.getSettings().getSecondBetweenRounds());
            timer.breakRound();
            timer.startBreak();
        }
        if (SecondsTimer.RoundStatus.READY.equals(timer.getStatus())) {
            timer.reset();
        }
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
                System.out.println(component.getIdentifier());
            }
        }

        return joystick;
    }

    public static void joyDetect(Controller controller) {
        controller.poll();
        boolean b1 = false;
        boolean b2 = false;
        boolean b3 = false;
        boolean b4 = false;
        boolean b5 = false;
        boolean b6 = false;
        boolean b7 = false;
        boolean b8 = false;
        boolean b9 = false;
        boolean b10 = false;
        for (Component component : controller.getComponents()) {
            if (component.getIdentifier().toString().equals("0")) {
                b1 = component.getPollData() >= 1f;
            }
            if (component.getIdentifier().toString().equals("1")) {
                b2 = component.getPollData() >= 1f;
            }
            if (component.getIdentifier().toString().equals("2")) {
                b3 = component.getPollData() >= 1f;
            }
            if (component.getIdentifier().toString().equals("3")) {
                b4 = component.getPollData() >= 1f;
            }
            if (component.getIdentifier().toString().equals("4")) {
                b5 = component.getPollData() >= 1f;
            }
            if (component.getIdentifier().toString().equals("5")) {
                b6 = component.getPollData() >= 1f;
            }
            if (component.getIdentifier().toString().equals("6")) {
                b7 = component.getPollData() >= 1f;
            }
            if (component.getIdentifier().toString().equals("7")) {
                b8 = component.getPollData() >= 1f;
            }
            if (component.getIdentifier().toString().equals("8")) {
                b9 = component.getPollData() >= 1f;
            }
            if (component.getIdentifier().toString().equals("9")) {
                b10 = component.getPollData() >= 1f;
            }
        }
    }

}