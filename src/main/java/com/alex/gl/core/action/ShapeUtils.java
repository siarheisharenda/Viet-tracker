package com.alex.gl.core.action;

import static com.alex.gl.core.widget.ButtonContainer.*;

import com.alex.gl.core.widget.ButtonContainer;
import com.alex.gl.entity.DBoolean;
import com.alex.gl.entity.Score;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 06.10.13
 * Time: 23:20
 */
public class ShapeUtils {

    private static int blueHits;
    private static int redHits;

    private static ScoreTimer blueScore = new ScoreTimer(true);
    private static ScoreTimer redScore = new ScoreTimer(false);

    public static void drawJoystickQuads(Score score) {
        instance.drawButtons();

        blueHits = getHitsCount(instance.getBlueButtons());
        redHits = getHitsCount(instance.getRedButtons());

        if (blueHits + redHits >= DBoolean.getJudges() - 1) {
            if (blueHits > redHits) {
                if (!blueScore.isRun()) {
                    blueScore.setItems(instance.getBlueButtons(), score);
                    new Thread(blueScore).start();
                }
            } else {
                if (!(blueHits == redHits) && !redScore.isRun()) {
                    redScore.setItems(instance.getRedButtons(), score);
                    new Thread(redScore).start();
                }
            }
        }
    }

    public static int getHitsCount(DBoolean[] buttons) {
        int count = 0;
        for (int i = 0; i < DBoolean.getJudges(); i++) {
            if (buttons[i].isHit()) {
                count++;
            }
        }
        return count;
    }
}
