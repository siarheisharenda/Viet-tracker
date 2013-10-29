package com.alex.gl.entity;

import org.lwjgl.opengl.DisplayMode;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 29.09.13
 * Time: 16:51
 */
public class Settings implements Serializable {

    private int roundsInMatch = 2;
    private int secondsInRound = 30;
    private int secondBetweenRounds = 20;
    private int medicalBreakTime = 60;
    private int judges = 4;
    private float hitDelay = 1.5f;

    public int getRoundsInMatch() {
        return roundsInMatch;
    }

    public void setRoundsInMatch(int roundsInMatch) {
        this.roundsInMatch = roundsInMatch;
    }

    public int getSecondsInRound() {
        return secondsInRound;
    }

    public void setSecondsInRound(int secondsInRound) {
        this.secondsInRound = secondsInRound;
    }

    public int getSecondBetweenRounds() {
        return secondBetweenRounds;
    }

    public void setSecondBetweenRounds(int secondBetweenRounds) {
        this.secondBetweenRounds = secondBetweenRounds;
    }

    public int getMedicalBreakTime() {
        return medicalBreakTime;
    }

    public void setMedicalBreakTime(int medicalBreakTime) {
        this.medicalBreakTime = medicalBreakTime;
    }

    public int getJudges() {
        return judges;
    }

    public void setJudges(int judges) {
        this.judges = judges;
    }

    public float getHitDelay() {
        return hitDelay;
    }

    public void setHitDelay(float hitDelay) {
        this.hitDelay = hitDelay;
    }
}
