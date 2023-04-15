package main.java.controleur.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.java.vue.VueJeu;
import main.java.vue.menu.VueMenu;

public class ControleurMenu implements ActionListener {

	public ControleurMenu(VueMenu vm) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		switch (bouton.getText()) {
		case "Jouer":
			new VueJeu();
			break;
		}
	}

}
