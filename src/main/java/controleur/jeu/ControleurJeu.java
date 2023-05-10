package main.java.controleur.jeu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.carte.Carte;
import main.java.model.jeu.ia.IA;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;
import main.java.vue.jeu.Chrono;
import main.java.vue.jeu.VueHistorique;
import main.java.vue.jeu.VueJeu;

public class ControleurJeu implements ActionListener {

	private VueJeu vj;
	private Partie partie;
	private Chrono timer;
	
	public ControleurJeu(VueJeu vj, Partie p, Chrono c) {
		this.vj = vj;
		this.partie = p;
		this.timer=c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		switch (bouton.getText()) {
		case "Jouer le tour":
			bouton.setEnabled(false);
			Joueur j = this.vj.getJoueur();

			//On vérifie si l'adversaire est un ordinateur, si oui on le fait jouer avant le joueur
			Joueur joueurAdverse;
			if (j.getCouleur().equals(ECouleurJoueur.ROUGE)) {
				joueurAdverse = this.partie.getJoueurVert();
			} else {
				joueurAdverse = this.partie.getJoueurRouge();
			}
			boolean adversaireEstUnOrdinateur = joueurAdverse instanceof IA;
			if (adversaireEstUnOrdinateur)
				((IA) joueurAdverse).jouerTour(partie);

			
			int mise = vj.getMise();
			Tour tourCourant = partie.getMancheCourante().getTourCourant();
			tourCourant.setMiseJoueur(j, mise);
			List<Carte> cartes = vj.getCartesJouees();
			for (Carte c : cartes) {
				partie.jouerCarte(c, j);
			}
			partie.jouerTour();
			break;
		case "Historique de la partie":
			VueHistorique vh = new VueHistorique();
			break;
		default:
			return;
		}
	}

}
