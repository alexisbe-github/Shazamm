package main.java.model.jeu.ia;

import java.util.List;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.carte.Carte;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;
import main.java.utils.Utils;
import main.java.vue.ILancementStrategy;

/**
 * 
 * Lors d'une simulation de partie nous avons besoin de simuler les coups lancés
 * par les cartes de lancement strategy
 *
 */
public class SimulationStrategyLancementSort implements ILancementStrategy {

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
