package com.alex.gl.core.action;

import com.alex.gl.core.widget.ButtonContainer;
import com.alex.gl.entity.DBoolean;
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
 */
public class ActionUtil {

    private static Logger log = Logger.getLogger(ActionUtil.class.getName());

    public static void controlDetect(Score score, SecondsTimer timer) {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_Q) {
                    if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)
                            || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
                        score.setcRed(score.getcRed() - 1);
                    } else {
                        score.setcRed(score.getcRed() + 1);
                    }
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_A) {
                    if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)
                            || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
                        score.setpRed(score.getpRed() - 1);
                    } else {
                        score.setpRed(score.getpRed() + 1);
                    }
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_O) {
                    if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)
                            || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
                        score.setcBlue(score.getcBlue() - 1);
                    } else {
                        score.setcBlue(score.getcBlue() + 1);
                    }
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_J) {
                    if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)
                            || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
                        score.setpBlue(score.getpBlue() - 1);
                    } else {
                        score.setpBlue(score.getpBlue() + 1);
                    }
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_R) {
                    score.reset();
                    WinnerSingleton.instance.reset();
                    timer.reset();
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_M) {
                    if (!SecondsTimer.RoundStatus.MEDICAL.equals(timer.getStatus())) {
                        timer.startMedical();
                    }
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_Y) {
                    if (SecondsTimer.RoundStatus.FINISH.equals(timer.getStatus())) {
                        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)
                                || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
                            WinnerSingleton.instance.reset();
                        } else if (score.getcBlue() != score.getcRed()) {
                            WinnerSingleton.instance.setBlue(score.getcBlue() > score.getcRed());
                            OpenAlSounder.instance.playGong();
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
                if (Keyboard.getEventKey() == Keyboard.KEY_W) {
                    if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)
                            || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
                        score.setDonRed(false);
                    } else {
                        score.setDonRed(true);
                    }
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_P) {
                    if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)
                            || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
                        score.setDonBlue(false);
                    } else {
                        score.setDonBlue(true);
                    }
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_S) {
                    if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)
                            || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
                        score.setRemRed(score.getRemRed() - 1);
                    } else {
                        score.setRemRed(score.getRemRed() + 1);
                    }
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_K) {
                    if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)
                            || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
                        score.setRemBlue(score.getRemBlue() - 1);
                    } else {
                        score.setRemBlue(score.getRemBlue() + 1);
                    }
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_D) {
                    if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)
                            || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
                        score.setWarnRed(score.getWarnRed() - 1);
                    } else {
                        score.setWarnRed(score.getWarnRed() + 1);
                    }
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_L) {
                    if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)
                            || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
                        score.setWarnBlue(score.getWarnBlue() - 1);
                    } else {
                        score.setWarnBlue(score.getWarnBlue() + 1);
                    }
                }
                timeSwitcher(timer);
            }
        }
    }

    public static void timeSwitcher(SecondsTimer timer) {
        if (!SecondsTimer.RoundStatus.BREAK.equals(timer.getStatus()) && Keyboard.getEventKey() == Keyboard.KEY_SPACE
                && !SecondsTimer.RoundStatus.FINISH.equals(timer.getStatus())) {
            if (timer.isRun()) {
                timer.stop();
                timer.setContinue();
            } else {
                if (!SecondsTimer.RoundStatus.PAUSE.equals(timer.getStatus())) {
                    OpenAlSounder.instance.playGong();
                }
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
            OpenAlSounder.instance.playGong();
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

    public static void joyDetect(Controller controller) {
        controller.poll();
        Component[] components = controller.getComponents();
        for (int i = buttonOffset; i < buttonsEnd; i++) {
            if (components[i].getPollData() >= 1f) {
                startButton(i - buttonOffset);
            } else {
                ButtonContainer.instance.getButtons()[i - buttonOffset].release();
            }
        }
    }

    private static void startButton(int indexButton) {
        DBoolean button = ButtonContainer.instance.getButtons()[indexButton];
        DBoolean appositeButton = ButtonContainer
                .instance.getButtons()[isEven(indexButton) ? indexButton + 1 : indexButton - 1];
        if (!appositeButton.isHit() && button.isReleased()) {
            if (!button.isRun() && !button.isDoubleClick()) {
                button.hit();
                new Thread(button).start();
            } else {
                button.setDoubleClick(true);
            }
        }
    }

    private static boolean isEven(int value) {
        return value % 2 == 0;
    }
}