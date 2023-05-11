package main.java.vue.jeu;

import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;

import main.java.model.jeu.Chrono;

public class VueChrono extends JLabel implements PropertyChangeListener{
	private Chrono chrono;

    public VueChrono(Chrono chrono) {
    	this.chrono = chrono;
    	this.setFont(new Font("Verdana", Font.PLAIN, 20));
    	this.updateChrono();
        this.setVisible(true);
        this.setForeground(Color.WHITE);
        this.setHorizontalAlignment(JLabel.CENTER);
        chrono.addObserver(this);
    }
    
    public void updateChrono() {
    	int sec = chrono.getTempsRestant()%60;
    	int min = chrono.getTempsRestant()/60;
    	this.setText(String.format("%02d:%02d", min,sec));
    }

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.updateChrono();
	}
}
