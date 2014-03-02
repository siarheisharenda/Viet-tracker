package com.alex.gl.entity;

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

    private int pointGap = 10;
    private int pointCap = 5;

    private int reminderPoint = 5;
    private int warningPoint = 24;
    private int totalPoint = 2;

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

    public int getPointGap() {
        return pointGap;
    }

    public void setPointGap(int pointGap) {
        this.pointGap = pointGap;
    }

    public int getPointCap() {
        return pointCap;
    }

    public void setPointCap(int pointCap) {
        this.pointCap = pointCap;
    }

    public int getReminderPoint() {
        return reminderPoint;
    }

    public void setReminderPoint(int reminderPoint) {
        this.reminderPoint = reminderPoint;
    }

    public int getWarningPoint() {
        return warningPoint;
    }

    public void setWarningPoint(int warningPoint) {
        this.warningPoint = warningPoint;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }
}
