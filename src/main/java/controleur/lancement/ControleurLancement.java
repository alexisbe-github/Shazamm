package main.java.controleur.lancement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.java.vue.VueConsole;
import main.java.vue.VueLancement;

public class ControleurLancement implements ActionListener {
	
	private VueLancement vl;
	
	public ControleurLancement(VueLancement vl) {
		this.vl = vl;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		switch(bouton.getText()) {
		case "Console":
			
			break;
		case "Inerface Graphique":
			
			break;
		}
	}

}
