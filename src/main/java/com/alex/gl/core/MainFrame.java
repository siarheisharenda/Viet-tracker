package com.alex.gl.core;

import com.alex.gl.core.widget.GraphicsWidget;
import com.alex.gl.core.widget.SetupWidget;
import com.alex.gl.core.widget.helper.FileUtils;
import com.alex.gl.entity.SettingContainer;
import com.alex.gl.entity.Settings;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 08.09.13
 * Time: 18:50
 */
public class MainFrame extends JFrame {

    private static Logger log = Logger.getLogger(MainFrame.class.getName());

    private GlFrame glFrame;
    private JButton startButton;
    private Settings settings;
    private SettingContainer container = new SettingContainer();

    public MainFrame() {
        settings = loadSettings();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocation((int) ((screenSize.getWidth() - getWidth()) / 2),
                (int) ((screenSize.getHeight() - getHeight()) / 2));
        setTitle("VoViet");
        setResizable(false);
        initLayout();
        initActions();
        setVisible(true);
    }

    private Settings loadSettings() {
        Object object = FileUtils.loadObject();
        return (object != null) ? (Settings) object : new Settings();
    }

    private void initLayout() {
        initMenuBar();
        startButton = new JButton("Start");
        startButton.setFocusable(true);
        startButton.grabFocus();
        JPanel panel = new JPanel(new BorderLayout(), true);
        panel.add(initHelpPanel(), BorderLayout.CENTER);
        panel.add(startButton, BorderLayout.SOUTH);
        add(panel, BorderLayout.CENTER);
        pack();
    }

    private JPanel initHelpPanel() {
        JPanel panel = new JPanel(new GridLayout(14, 2));
        panel.add(new JLabel("'Q' - increase RED point"));
        panel.add(new JLabel("'Q' + 'Left Ctrl' - decrease RED point"));
        panel.add(new JLabel("'A' - increase RED penalty point"));
        panel.add(new JLabel("'A' + 'Left Ctrl' - decrease RED penalty point"));
        panel.add(new JLabel("'O' - increase BLUE point"));
        panel.add(new JLabel("'O' + 'Left Ctrl' - decrease BLUE point"));
        panel.add(new JLabel("'J' - increase BLUE point"));
        panel.add(new JLabel("'J' + 'Left Ctrl' - decrease BLUE penalty point"));
        panel.add(new JLabel("'W' - set DonChan for red"));
        panel.add(new JLabel("'W' + 'Left Ctrl' - unset DonChan for red"));
        panel.add(new JLabel("'P' - set DonChan for blue"));
        panel.add(new JLabel("'P' + 'Left Ctrl' - unset DonChan for blue"));
        panel.add(new JLabel("'S' - set Reminder Nhắc nhở for blue"));
        panel.add(new JLabel("'S' + 'Left Ctrl' - unset Reminder Nhắc nhở for blue"));
        panel.add(new JLabel("'K' - set Reminder Nhắc nhở for red"));
        panel.add(new JLabel("'K' + 'Left Ctrl' - unset Reminder Nhắc nhở for red"));
        panel.add(new JLabel("'D' - set Warning Canh cáo for red"));
        panel.add(new JLabel("'D' + 'Left Ctrl' - unset Warning Canh cáo for red"));
        panel.add(new JLabel("'L' - set Warning Canh cáo for blue"));
        panel.add(new JLabel("'L' + 'Left Ctrl' - unset Warning Canh cáo for blue"));
        panel.add(new JLabel(StringUtils.EMPTY));
        panel.add(new JLabel(StringUtils.EMPTY));
        panel.add(new JLabel("'R' - restart match"));
        panel.add(new JLabel("'Y' - resolve winner"));
        panel.add(new JLabel("'Y' + 'Left Ctrl' - reset winner"));
        panel.add(new JLabel("'M' - get medical"));
        panel.add(new JLabel("'SPACE' - start and stop time(medical)"));
        panel.add(new JLabel("'F' - make full screen(window) mode"));
        return panel;
    }

    private void initMenuBar() {
        final JFrame self = this;
        JMenuBar menuBar = new JMenuBar();
        JMenu jMenu = new JMenu("File");
        JMenu jMenuSettings = new JMenu("Settings");
        JMenuItem menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                self.dispose();
                System.exit(0);
            }
        });
        addComponent(jMenu, menuItem);
        menuItem = new JMenuItem("Setup");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                log.info("Opening settings menu ...");
                new SetupWidget(self, settings);
                log.info("Closed settings menu ...");
            }
        });
        addComponent(jMenuSettings, menuItem);
        menuItem = new JMenuItem("Graphics");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GraphicsWidget(self, container);
            }
        });
        addComponent(jMenuSettings, menuItem);
        addComponent(menuBar, jMenu);
        addComponent(menuBar, jMenuSettings);
        setJMenuBar(menuBar);
    }

    private void addComponent(JComponent parent, JComponent child) {
        parent.add(child);
    }

    /**
     * Init action with main graphic window.
     */
    private void initActions() {
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                glFrame = new GlFrame(settings, container);
                glFrame.start();
            }
        });
    }

}
