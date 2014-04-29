package com.alex.gl.core.widget;

import com.alex.gl.entity.SettingContainer;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 10.10.13
 * Time: 23:40
 */
public class GraphicsWidget extends JDialog {

    private SettingContainer container;
    private JComboBox<DisplayMode> jList;

    public GraphicsWidget(Frame owner, SettingContainer container) {
        super(owner);
        this.container = container;
        init();
    }

    public void showWindow() {
        setVisible(true);
    }

    private void init() {
        setLocation(getParent().getLocation());
        setSize(300, 400);
        setModal(true);
        initLayout();
        initButton();
        pack();
        setResizable(false);
    }

    private void initButton() {
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyWindowMode();
                dispose();
            }
        });
        add(okButton, BorderLayout.SOUTH);
    }

    private void initLayout() {
        setLayout(new BorderLayout());
        try {
            DisplayMode[] modes = Display.getAvailableDisplayModes();
            jList = new JComboBox<>(modes);
            clearModes(modes);
            if (container.getDisplayMode() != null) {
                jList.setSelectedItem(container.getDisplayMode());
            }
            applyWindowMode();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        add(jList, BorderLayout.CENTER);
    }

    private void clearModes(DisplayMode[] modes) {
        for (DisplayMode mode : modes) {
            float k = (float) mode.getHeight() / (float) mode.getWidth();
            if (k > 0.6) {
                jList.removeItem(mode);
                continue;
            }
            if (mode.getWidth() < 1000) {
                jList.removeItem(mode);
                continue;
            }
        }
    }

    private void applyWindowMode() {
        container.setDisplayMode((DisplayMode) jList.getSelectedItem());
    }
}
