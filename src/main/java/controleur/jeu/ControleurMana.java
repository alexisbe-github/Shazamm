package main.java.controleur.jeu;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JTextField;

import main.java.utils.Utils;
import main.java.vue.jeu.VueJeu;

public class ControleurMana extends KeyAdapter{
	
	private JTextField saisieMana;
	private JButton boutonJouer;
	private VueJeu vue;
	
	public ControleurMana(JTextField saisieMana, JButton boutonJouer, VueJeu vj) {
		this.saisieMana=saisieMana;
		this.boutonJouer = boutonJouer;
		this.vue=vj;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		try {
			// On vérifie que la saisie est comprise entre 1 et le mana du joueur
			boolean saisieCorrecte = ((Character.isDigit(e.getKeyChar())
					|| e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE
					|| e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT
					|| e.getKeyCode() == KeyEvent.VK_SHIFT || e.getKeyCode() == KeyEvent.VK_CONTROL)
					&& ((Integer.parseInt(saisieMana.getText()
							+ (e.getKeyChar() == KeyEvent.VK_BACK_SPACE ? "" : e.getKeyChar())) <= vue.getJoueur()
									.getManaActuel())
							&& (Integer.parseInt(saisieMana.getText()
									+ (e.getKeyChar() == KeyEvent.VK_BACK_SPACE ? "" : e.getKeyChar())) >= 1)));

			if (saisieCorrecte) {
				saisieMana.setEditable(true);
			} else {
				saisieMana.setEditable(false);
				// Fondu vers le rouge pour prévenir l'utilisateur d'une saisie incorrecte
				Utils.fonduArrierePlan(saisieMana, new Color(255, 43, 28), 8, 15);
			}

			if (saisieMana.getText().length() == 1
					&& (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE)) {
				boutonJouer.setEnabled(false);
			} else
				boutonJouer.setEnabled(true);

		} catch (NumberFormatException ex) {

		}
	}
}
