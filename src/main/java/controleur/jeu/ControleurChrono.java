package main.java.controleur.jeu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.java.model.jeu.Chrono;
import main.java.vue.jeu.VueChrono;

public class ControleurChrono implements ActionListener {

	private Chrono chrono;
	
	public ControleurChrono(Chrono chrono) {
		this.chrono=chrono;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
        chrono.secondePasse();
    }

}
