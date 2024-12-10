package it.unibo.es3;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class GUI extends JFrame {
    private final int ITEM_TO_ENABLE_AT_APPLICATION_START = 3;
    private static final String BUTTON_DEFAULT_TEXT = " ";
    private static final String BUTTON_ENABLED_TEXT = "*";
    private static final String PLAY_BUTTON_TEXT = ">";

    private final Map<Pair<Integer, Integer>, JButton> cells = new HashMap<>();
    private final Logics logics;

    private void updateButtonText(JButton button, Pair<Integer, Integer> position) {
        button.setText(logics.getValue(position) ? BUTTON_ENABLED_TEXT : BUTTON_DEFAULT_TEXT);
    }

    public GUI(int width) {
        this.logics = new LogicsImpl(width);
        this.logics.initStage(ITEM_TO_ENABLE_AT_APPLICATION_START);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70 * width, 70 * width);
        // init main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        // gridPanel
        JPanel gridPanel = new JPanel(new GridLayout(width, width));
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                var position = new Pair<>(j, i);
                final JButton button = new JButton();
                this.cells.put(position, button);
                updateButtonText(button, position);
                gridPanel.add(button);
            }
        }
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        // button panel
        ActionListener playActionListener = e -> {
            logics.nextStage();
            cells.entrySet().forEach(x -> {
                final Pair<Integer, Integer> position = x.getKey();
                final JButton button = x.getValue();
                updateButtonText(button, position);
            });
            if (logics.quit()) {
                System.exit(1);
            }
        };
        JPanel playButtonPanel = new JPanel(new BorderLayout());
        JButton playButton = new JButton();
        playButton.setText(PLAY_BUTTON_TEXT);
        playButton.addActionListener(playActionListener);
        playButtonPanel.add(playButton);
        mainPanel.add(playButtonPanel, BorderLayout.SOUTH);
        // set items visible
        this.getContentPane().add(mainPanel);
        this.setVisible(true);
    }

}