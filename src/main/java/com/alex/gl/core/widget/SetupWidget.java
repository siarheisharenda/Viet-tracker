package com.alex.gl.core.widget;

import com.alex.gl.core.widget.helper.FileUtils;
import com.alex.gl.core.widget.helper.UIUtils;
import com.alex.gl.entity.Settings;
import org.apache.commons.lang.NumberUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 29.09.13
 * Time: 22:25
 * To change this template use File | Settings | File Templates.
 */
public class SetupWidget extends JDialog {

    private Settings settings;
    private JTextField roundsInMatchField;
    private JTextField secondsInRoundField;
    private JTextField secondsBetweenRounds;
    private JTextField medicalBreakTime;

    public SetupWidget(Frame owner, Settings settings) {
        super(owner, "Setup window");
        this.settings = settings;
        init();
    }

    private void init() {
        setLocation(getParent().getLocation());
        setSize(300, 400);
        setModal(true);
        initLayout();
        initButton();
        injectData();
        pack();
        setResizable(false);
        setVisible(true);
    }

    private void initLayout() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Rounds in match:"));
        roundsInMatchField = UIUtils.createField(2);
        panel.add(roundsInMatchField);
        panel.add(new JLabel("Seconds in round:"));
        secondsInRoundField = UIUtils.createField(4);
        panel.add(secondsInRoundField);
        panel.add(new JLabel("Seconds between rounds:"));
        secondsBetweenRounds = UIUtils.createField(4);
        panel.add(secondsBetweenRounds);
        panel.add(new JLabel("Seconds between rounds:"));
        medicalBreakTime = UIUtils.createField(3);
        panel.add(medicalBreakTime);
        add(panel, BorderLayout.CENTER);
    }

    private void initButton() {
        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apply();
                dispose();
                FileUtils.saveSettings(settings);
            }
        });
        add(okButton, BorderLayout.SOUTH);
    }

    private void apply() {
        settings.setRoundsInMatch(NumberUtils.createInteger(roundsInMatchField.getText()));
        settings.setMedicalBreakTime(NumberUtils.createInteger(medicalBreakTime.getText()));
        settings.setSecondBetweenRounds(NumberUtils.createInteger(secondsBetweenRounds.getText()));
        settings.setSecondsInRound(NumberUtils.createInteger(secondsInRoundField.getText()));
    }

    private void injectData() {
        roundsInMatchField.setText(String.valueOf(settings.getRoundsInMatch()));
        secondsInRoundField.setText(String.valueOf(settings.getSecondsInRound()));
        secondsBetweenRounds.setText(String.valueOf(settings.getSecondBetweenRounds()));
        medicalBreakTime.setText(String.valueOf(settings.getMedicalBreakTime()));
    }

}
