package main.java.model.jeu.ia;

import java.util.List;

import main.java.model.bdd.Profil;
import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.carte.Carte;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;
import main.java.utils.Utils;
import main.java.vue.ILancementStrategy;

public class IAFacile extends IAEtatJeu implements IA, ILancementStrategy {


	public IAFacile(Joueur j) {
		super(j.getCouleur(), j.getProfil());
	}
	
	public IAFacile(ECouleurJoueur couleur, Profil profil) {
		super(couleur, profil);
	}

	/**
	 * IA de type facile qui joue aléatoirement chaque tour
	 * 
	 * @param p Partie
	 */
	@Override
	public void jouerTour(Partie p) {
		// on génère une mise aléatoirement entre 1 et le mana de l'ordinateur
		int mise = Utils.genererEntier(1, getManaActuel() + 1);

		// on joue entre 0 et le nombre de cartes dans la main
		int nbCartesAJouer = Utils.genererEntierAvecPoids(0, getMainDuJoueur().size());

		// si mutisme est activé, l'IA ne jouera plus de cartes pendant la manche
		if(p.getMancheCourante().getMutismeCourant()) nbCartesAJouer = 0;
		
		for (int i = 0; i < nbCartesAJouer; i++) {
			int index = Utils.genererEntier(0, getMainDuJoueur().size());
			Carte carte = getMainDuJoueur().get(index);
			p.jouerCarte(carte, this);
		}
		p.getMancheCourante().getTourCourant().setMiseJoueur(this, mise);
	}

	@Override
	public void lancerClone(Partie p, Tour tour, Joueur joueur) {
		List<Carte> cartesJoueesTourPrec = p.getCartesJoueesParAdversaireTourPrecedent(joueur);
		int i = Utils.genererEntier(0, cartesJoueesTourPrec.size());
		Carte carteAVoler = cartesJoueesTourPrec.get(i);
		tour.activerClone(carteAVoler);
	}

	@Override
	public void lancerRecyclage(Partie p, Tour tour, Joueur joueur) {
		int miseRecyclage = Utils.genererEntier(-5, 6);
		tour.recyclerMise(joueur, miseRecyclage);
	}

	@Override
	public void lancerLarcin(Partie p, Tour tour, Joueur joueur) {
		List<Carte> cartes;
		if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE)) {
			cartes = p.getListeCartesJoueesParJoueur(p.getJoueurVert());
		} else {
			cartes = p.getListeCartesJoueesParJoueur(p.getJoueurRouge());
		}
		
		// on joue entre 0 et le nombre de cartes jouees par l'adversaire
		int nbCartesAVoler = Utils.genererEntierAvecPoids(0, cartes.size());

		for (int i = 0; i < nbCartesAVoler; i++) {
			int index = Utils.genererEntier(0, cartes.size());
			Carte carte = cartes.get(index);
			carte.changerDetenteurCarte(joueur);
		}
	}
}
