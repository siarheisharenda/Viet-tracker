package com.alex.gl.core;

import com.alex.gl.core.widget.SetupWidget;
import com.alex.gl.core.widget.helper.FileUtils;
import com.alex.gl.entity.Settings;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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

    public MainFrame() {
        settings = loadSettings();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocation((int) ((screenSize.getWidth() - getWidth()) / 2), (int) ((screenSize.getHeight() - getHeight()) / 2));
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
        panel.add(startButton, BorderLayout.SOUTH);
        add(panel, BorderLayout.CENTER);
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

        addComponent(menuBar, jMenu);
        addComponent(menuBar, jMenuSettings);
        setJMenuBar(menuBar);
    }

    private void addComponent(JComponent parent, JComponent child) {
        parent.add(child);
    }

    private void initActions() {
//        final JFrame self = this;
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                glFrame = new GlFrame(settings);
                glFrame.start();
            }
        });
    }

}
