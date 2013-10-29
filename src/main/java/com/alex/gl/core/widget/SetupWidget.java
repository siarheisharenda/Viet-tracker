package com.alex.gl.core.widget;

import com.alex.gl.core.widget.helper.FileUtils;
import com.alex.gl.core.widget.helper.UIUtils;
import com.alex.gl.entity.Settings;
import org.apache.commons.lang.NumberUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 29.09.13
 * Time: 22:25
 */
public class SetupWidget extends JDialog {

    private static Logger log = Logger.getLogger(SetupWidget.class.getName());

    private Settings settings;
    private JTextField roundsInMatchField;
    private JTextField secondsInRoundField;
    private JTextField secondsBetweenRounds;
    private JTextField medicalBreakTime;
    private JComboBox<Integer> judgesBox;
    private JTextField hitDelayField;

    public SetupWidget(Frame owner, Settings settings) {
        super(owner, "Setup window");
        this.settings = settings;
        init();
    }

    private void init() {
        setLocation(getParent().getLocation());
        setSize(300, 400);
        setModal(true);
        JTabbedPane pane = new JTabbedPane();
        pane.add("Rounds setup", initSetupLayout());
        pane.add("Control setup", initControlLayout());
        add(pane, BorderLayout.CENTER);
        initButton();
        injectData();
        pack();
        setResizable(false);
        setVisible(true);
    }

    private JPanel initSetupLayout() {
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
        panel.add(new JLabel("Medical break time:"));
        medicalBreakTime = UIUtils.createField(3);
        panel.add(medicalBreakTime);
        return panel;
    }

    private JPanel initControlLayout() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Judges:"));
        judgesBox = new JComboBox<>(new Integer[]{2, 3, 4});
        panel.add(judgesBox);
        panel.add(new JLabel("Hit button delay (sec):"));
        hitDelayField = UIUtils.createField(3);
        panel.add(hitDelayField);
        return panel;
    }

    private void initButton() {
        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    apply();
                    dispose();
                    FileUtils.saveSettings(settings);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(getContentPane(), "Incorrect number format: " + ex.getMessage(),
                            "Error window", JOptionPane.ERROR_MESSAGE);
                    log.log(Level.WARNING, ex.getMessage());
                }
            }
        });
        add(okButton, BorderLayout.SOUTH);
    }

    private void apply() {
        settings.setRoundsInMatch(NumberUtils.createInteger(roundsInMatchField.getText()));
        settings.setMedicalBreakTime(NumberUtils.createInteger(medicalBreakTime.getText()));
        settings.setSecondBetweenRounds(NumberUtils.createInteger(secondsBetweenRounds.getText()));
        settings.setSecondsInRound(NumberUtils.createInteger(secondsInRoundField.getText()));
        settings.setJudges(NumberUtils.createInteger(judgesBox.getSelectedItem().toString()));
        settings.setHitDelay(NumberUtils.createFloat(hitDelayField.getText()));
    }

    private void injectData() {
        roundsInMatchField.setText(String.valueOf(settings.getRoundsInMatch()));
        secondsInRoundField.setText(String.valueOf(settings.getSecondsInRound()));
        secondsBetweenRounds.setText(String.valueOf(settings.getSecondBetweenRounds()));
        medicalBreakTime.setText(String.valueOf(settings.getMedicalBreakTime()));
        judgesBox.setSelectedItem(settings.getJudges());
        hitDelayField.setText(String.valueOf(settings.getHitDelay()));
    }

}
