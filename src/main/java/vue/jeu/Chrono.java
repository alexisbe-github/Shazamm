package main.java.vue.jeu;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

public class Chrono extends JLabel{
    private Timer timer;
    private int tempsRestant;

    public Chrono(int sec) {
    	Chrono.this.setText(String.format("%02d:%02d", tempsRestant/60, tempsRestant%60));
    	tempsRestant=sec;
        this.setVisible(true);
        this.init();
        this.setForeground(Color.WHITE);
    }

    private void init() {
        this.timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempsRestant--;
                if (tempsRestant==0) {
                    timer.stop();
                }else {
                	int minutes = tempsRestant/60;
                	int secondes = tempsRestant%60;
                	Chrono.this.setText(String.format("%02d:%02d", minutes, secondes));
                }
            }
        });
        
        this.timer.start();
    }
}
