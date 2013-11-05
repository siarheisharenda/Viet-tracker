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
    private JTextField pointGapField;
    private JTextField pointCapField;

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
        JPanel panel = new JPanel(new GridLayout(6, 2));
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
        panel.add(new JLabel("Point gap to win:"));
        pointGapField = UIUtils.createField(2);
        panel.add(pointGapField);
        panel.add(new JLabel("Point cap to win:"));
        pointCapField = UIUtils.createField(2);
        panel.add(pointCapField);
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
        int value = NumberUtils.createInteger(roundsInMatchField.getText());
        settings.setRoundsInMatch((value > 0) ? value : 5);
        value = NumberUtils.createInteger(medicalBreakTime.getText());
        settings.setMedicalBreakTime((value > 0) ? value : 5);
        value = NumberUtils.createInteger(secondsBetweenRounds.getText());
        settings.setSecondBetweenRounds((value > 0) ? value : 5);
        value = NumberUtils.createInteger(secondsInRoundField.getText());
        settings.setSecondsInRound((value > 0) ? value : 5);
        value = NumberUtils.createInteger(judgesBox.getSelectedItem().toString());
        settings.setJudges((value > 0) ? value : 5);
        float fvalue = NumberUtils.createFloat(hitDelayField.getText());
        settings.setHitDelay((fvalue > 0) ? value : 5);
        value = NumberUtils.createInteger(pointGapField.getText());
        settings.setPointGap((value > 0) ? value : 5);
        value = NumberUtils.createInteger(pointCapField.getText());
        settings.setPointCap((value > 0) ? value : 5);
    }

    private void injectData() {
        roundsInMatchField.setText(String.valueOf(settings.getRoundsInMatch()));
        secondsInRoundField.setText(String.valueOf(settings.getSecondsInRound()));
        secondsBetweenRounds.setText(String.valueOf(settings.getSecondBetweenRounds()));
        medicalBreakTime.setText(String.valueOf(settings.getMedicalBreakTime()));
        judgesBox.setSelectedItem(settings.getJudges());
        hitDelayField.setText(String.valueOf(settings.getHitDelay()));
        pointGapField.setText(String.valueOf(settings.getPointGap()));
        pointCapField.setText(String.valueOf(settings.getPointCap()));
    }

}
