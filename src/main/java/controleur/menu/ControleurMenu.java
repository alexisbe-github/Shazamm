package main.java.controleur.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import main.java.model.bdd.Profil;
import main.java.model.bdd.dao.DAOJoueur;
import main.java.model.jeu.Chrono;
import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.ia.IAExperte;
import main.java.model.jeu.ia.IAFacile;
import main.java.model.jeu.ia.IAIntermediaire;
import main.java.model.jeu.partie.Partie;
import main.java.vue.classement.VueClassement;
import main.java.vue.jeu.VueJeu;
import main.java.vue.menu.VueHistoriqueGeneral;
import main.java.vue.menu.VueMenu;
import main.java.vue.profil.VueLancementPartie;

public class ControleurMenu implements ActionListener {

	private VueMenu vm;

	public ControleurMenu(VueMenu vm) {
		this.vm = vm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		Chrono timer = new Chrono();
		switch (bouton.getText()) {
		case "Jouer":
			vm.dispose();
			List<ECouleurJoueur> couleursTirees = tirerCouleurs();
			
			ECouleurJoueur couleurJ1 = couleursTirees.get(0);
			ECouleurJoueur couleurJ2 = couleursTirees.get(1);
			
			VueLancementPartie vlp = new VueLancementPartie(couleurJ1,couleurJ2, timer);
			
			break;
		case "Jouer contre l'ordinateur":
			vm.dispose();
			List<ECouleurJoueur> couleurs = tirerCouleurs();
			ECouleurJoueur couleur = couleurs.get(0);
			ECouleurJoueur couleurIA = couleurs.get(1);
			Joueur joueur = new Joueur(couleur, "Joueur", "Invité");
			Object[] options = { "Facile", "Intermédiaire", "Experte" };
			int input = JOptionPane.showOptionDialog(vm, "Choisissez la difficulté de l'ordinateur", "Choix difficulté",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			Partie partie;
			VueJeu fenetreJoueur;
			switch (input) {
			case 0:
				IAFacile ia = new IAFacile(couleurIA, new Profil(new DAOJoueur().trouver(1L)));
				partie = new Partie(joueur, ia);
				fenetreJoueur = new VueJeu(joueur, partie, timer);
				if (joueur.getCouleur().equals(ECouleurJoueur.VERT)) {
					partie.setStrategy(fenetreJoueur, ia);
				} else {
					partie.setStrategy(ia, fenetreJoueur);
				}
				partie.addObserver(fenetreJoueur);
				break;
			case 1:
				IAIntermediaire iaI = new IAIntermediaire(couleurIA,
						new Profil(new DAOJoueur().trouver(1L)));
				partie = new Partie(joueur, iaI);
				fenetreJoueur = new VueJeu(joueur, partie, timer);
				if (joueur.getCouleur().equals(ECouleurJoueur.VERT)) {
					partie.setStrategy(fenetreJoueur, iaI);
				} else {
					partie.setStrategy(iaI, fenetreJoueur);
				}
				partie.addObserver(fenetreJoueur);
				break;
			case 2:
				IAExperte iaE = new IAExperte(couleurIA,
						new Profil(new DAOJoueur().trouver(1L)));
				partie = new Partie(joueur, iaE);
				fenetreJoueur = new VueJeu(joueur, partie, timer);
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
		case "Classement":
			vm.dispose();
			new VueClassement();
			break;
		case "Historique":
			new VueHistoriqueGeneral();
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
