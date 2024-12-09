package it.unibo.es2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private static final String BUTTON_DEFAULT_TEXT = " ";
    private static final String BUTTON_CLICKED_TEXT = "*";

    private final Map<JButton, Pair<Integer, Integer>> buttons = new HashMap<>();
    private final Logics logics;

    public GUI(int size) {
        this.logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100 * size, 100 * size);

        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(BorderLayout.CENTER, panel);

        ActionListener al = (e) -> {
            final JButton buttonClicked = (JButton) e.getSource();
            final Pair<Integer, Integer> buttonPosition = buttons.get(buttonClicked);
            buttonClicked.setText(this.logics.hit(buttonPosition) ? BUTTON_CLICKED_TEXT : BUTTON_DEFAULT_TEXT);
            if (this.logics.toQuit(buttonPosition)) {
                System.exit(1);
            }
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton(BUTTON_DEFAULT_TEXT);
                jb.addActionListener(al);
                this.buttons.put(jb, new Pair<>(i, j));
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
}
