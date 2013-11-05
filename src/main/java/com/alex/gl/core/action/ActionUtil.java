package com.alex.gl.core.action;

import com.alex.gl.entity.DBoolean;
import com.alex.gl.entity.Score;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 29.09.13
 * Time: 9:16
 */
public class ActionUtil {

    private static Logger log = Logger.getLogger(ActionUtil.class.getName());

    public static void controlDetect(Score score, SecondsTimer timer) {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_A) {
                    score.setcRed(score.getcRed() + 1);
                    if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                        score.setpRed(score.getpRed() + 1);
                    }
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_Z) {
                    score.setcRed(score.getcRed() - 1);
                    if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                        score.setpRed(score.getpRed() - 1);
                    }
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_K) {
                    score.setcBlue(score.getcBlue() + 1);
                    if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                        score.setpBlue(score.getpBlue() + 1);
                    }
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_M) {
                    score.setcBlue(score.getcBlue() - 1);
                    if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                        score.setpBlue(score.getpBlue() + 1);
                    }
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_R) {
                    score.reset();
                    WinnerSingleton.instance.reset();
                    timer.reset();
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_W) {
                    if (SecondsTimer.RoundStatus.FINISH.equals(timer.getStatus())) {
                        if (score.getcBlue() != score.getcRed()) {
                            WinnerSingleton.instance.setBlue(score.getcBlue() > score.getcRed());
                        }
                    }
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
        if (!SecondsTimer.RoundStatus.BREAK.equals(timer.getStatus()) && Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
            if (timer.isRun()) {
                timer.stop();
            } else {
                timer.startFight();
                OpenAlSounder.instance.play();
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
            int i = 0;
            for (Component component : joystick.getComponents()) {
                if (component.getIdentifier().toString().equals("0")) {
                    buttonOffset = i;
                    break;
                }
                i++;
            }
            buttonsEnd = buttonOffset + DBoolean.getJudges() * 2;
        }

        return joystick;
    }

    static int buttonOffset;
    static int buttonsEnd;
    static DBoolean b1 = new DBoolean();
    static DBoolean b2 = new DBoolean(true);
    static DBoolean b3 = new DBoolean();
    static DBoolean b4 = new DBoolean(true);
    static DBoolean b5 = new DBoolean();
    static DBoolean b6 = new DBoolean(true);
    static DBoolean b7 = new DBoolean();
    static DBoolean b8 = new DBoolean(true);
    static DBoolean[] buttons = new DBoolean[]{b1, b2, b3, b4, b5, b6, b7, b8};

    public static void joyDetect(Controller controller) {
        controller.poll();
        Component[] components = controller.getComponents();
        for (int i = buttonOffset; i < buttonsEnd; i++) {
            if (components[i].getPollData() >= 1f) {
                startButton(i - buttonOffset);
            } else {
                buttons[i - buttonOffset].release();
            }
        }
    }

    private static void startButton(int indexButton) {
        DBoolean button = buttons[indexButton];
        DBoolean appositeButton = buttons[isEven(indexButton) ? indexButton + 1 : indexButton - 1];
        if (!appositeButton.isHit() && !button.isRun() && button.isReleased()) {
            button.hit();
            new Thread(button).start();
        }
    }

    private static boolean isEven(int value) {
        return value % 2 == 0;
    }
}