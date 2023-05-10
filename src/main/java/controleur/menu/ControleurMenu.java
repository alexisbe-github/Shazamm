package main.java.controleur.menu;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.ia.IAEtatJeu;
import main.java.model.jeu.ia.IAExperte;
import main.java.model.jeu.ia.IAFacile;
import main.java.model.jeu.ia.IAIntermediaire;
import main.java.model.jeu.partie.Partie;
import main.java.vue.jeu.VueJeu;
import main.java.vue.menu.VueMenu;

public class ControleurMenu implements ActionListener {

	private VueMenu vm;

	public ControleurMenu(VueMenu vm) {
		this.vm = vm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		switch (bouton.getText()) {
		case "Jouer":
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
			if (joueur1.getCouleur().equals(ECouleurJoueur.VERT)) {
				p.setStrategy(fenetreJ1, fenetreJ2);
			} else {
				p.setStrategy(fenetreJ2, fenetreJ1);
			}
			p.addObserver(fenetreJ1);
			p.addObserver(fenetreJ2);
			break;
		case "Jouer contre l'ordinateur":
			vm.dispose();
			List<ECouleurJoueur> couleurs = tirerCouleurs();
			ECouleurJoueur couleur = couleurs.get(0);
			ECouleurJoueur couleurIA = couleurs.get(1);
			Joueur joueur = new Joueur(couleur, "Pop", "Simoké", "blabla");
			Object[] options = { "Facile", "Intermédiaire", "Experte" };
			int input = JOptionPane.showOptionDialog(vm, "Choisissez la difficulté de l'ordinateur", "Choix difficulté",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			Partie partie;
			VueJeu fenetreJoueur;
			switch (input) {
			case 0:
				IAFacile ia = new IAFacile(couleurIA, "Sorcier", "ledeux", "blabla");
				partie = new Partie(joueur, ia);
			 fenetreJoueur = new VueJeu(joueur, partie);
				if (joueur.getCouleur().equals(ECouleurJoueur.VERT)) {
					partie.setStrategy(fenetreJoueur, ia);
				} else {
					partie.setStrategy(ia, fenetreJoueur);
				}
				partie.addObserver(fenetreJoueur);
				break;
			case 1:
				IAIntermediaire iaI = new IAIntermediaire(couleurIA, "Sorcier", "ledeux", "blabla");
				partie = new Partie(joueur, iaI);
				fenetreJoueur = new VueJeu(joueur, partie);
				if (joueur.getCouleur().equals(ECouleurJoueur.VERT)) {
					partie.setStrategy(fenetreJoueur, iaI);
				} else {
					partie.setStrategy(iaI, fenetreJoueur);
				}
				partie.addObserver(fenetreJoueur);
				break;
			case 2:
				IAExperte iaE = new IAExperte(couleurIA, "Sorcier", "ledeux", "blabla");
				partie = new Partie(joueur, iaE);
				fenetreJoueur = new VueJeu(joueur, partie);
				if (joueur.getCouleur().equals(ECouleurJoueur.VERT)) {
					partie.setStrategy(fenetreJoueur, iaE);
				} else {
					partie.setStrategy(iaE, fenetreJoueur);
				}
				partie.addObserver(fenetreJoueur);
				break;
			case JOptionPane.CLOSED_OPTION:
				vm.setVisible(true);
				break;
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
