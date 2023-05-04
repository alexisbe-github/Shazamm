package main.java.controleur.menu;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;
import main.java.vue.jeu.VueJeu;
import main.java.vue.menu.VueMenu;

public class ControleurMenu implements ActionListener {

	private VueMenu vm;
	private boolean partieLancee; //empêche de lancer deux parties

	public ControleurMenu(VueMenu vm) {
		this.vm = vm;
		this.partieLancee = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		switch (bouton.getText()) {
		case "Jouer":
			if (!this.partieLancee) {
				this.partieLancee = true;
				vm.dispose();
				List<ECouleurJoueur> couleursTirees = tirerCouleurs();
				ECouleurJoueur couleurJ1 = couleursTirees.get(0);
				ECouleurJoueur couleurJ2 = couleursTirees.get(1);
				Joueur joueur1 = new Joueur(couleurJ1, "Pop", "Simoké", "blabla");
				Joueur joueur2 = new Joueur(couleurJ2, "Sorcier", "ledeux", "blabla");
				Partie p = new Partie(joueur1, joueur2);
				VueJeu fenetreJ1 = new VueJeu(joueur1, p);
				VueJeu fenetreJ2 = new VueJeu(joueur2, p);
				int width = Toolkit.getDefaultToolkit().getScreenSize().width;
				fenetreJ1.setLocation(new Point(0, 0));
				fenetreJ2.setLocation(new Point(width / 2, 0));
			}
			break;
		}
	}

	private List<ECouleurJoueur> tirerCouleurs() {
		List<ECouleurJoueur> couleurs = new ArrayList<>();
		Random r = new Random();
		if (r.nextBoolean()) {
			couleurs.add(ECouleurJoueur.ROUGE);
			couleurs.add(ECouleurJoueur.VERT);
		} else {
			couleurs.add(ECouleurJoueur.VERT);
			couleurs.add(ECouleurJoueur.ROUGE);
		}
		return couleurs;
	}

}
