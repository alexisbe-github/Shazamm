package main.java.vue.jeu;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

public class Chrono extends JLabel{
    private Timer timer;
    private String content = "00:00:00";
    private int heures, minutes, secondes;

    public Chrono() {
        this.setVisible(true);
        this.init();
        this.setText(content);
        this.setForeground(Color.WHITE);
    }

    private void init() {
        this.timer = new Timer(1000, new ActionListener() {
        	
            @Override
            public void actionPerformed(ActionEvent e) {
                secondes++;
                if (secondes >= 60) {
                    secondes = 0;
                    minutes++;
                }
                if (minutes >= 60) {
                    minutes = 0;
                    heures++;
                }
                Chrono.this.setText(String.format("%02d:%02d:%02d", heures, minutes, secondes));
            }
        });
        
        this.timer.start();
    }
}
