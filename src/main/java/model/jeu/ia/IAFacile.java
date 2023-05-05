package main.java.model.jeu.ia;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.carte.Carte;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;
import main.java.utils.Utils;
import main.java.vue.ILancementStrategy;

public class IAFacile extends Joueur implements IA, ILancementStrategy{

	public IAFacile(ECouleurJoueur couleur, String nom, String prenom, String avatar) {
		super(couleur, nom, prenom, avatar);
	}

	/**
	 * IA de type facile qui joue aléatoirement chaque tour
	 * @param p Partie
	 */
	@Override
	public void jouerTour(Partie p) {
		//on génère une mise aléatoirement entre 1 et le mana de l'ordinateur
		int mise = Utils.genererEntier(1, getManaActuel()+1);
		
		//on joue entre 0 et le nombre de cartes dans la main
		int nbCartesAJouer = Utils.genererEntierAvecPoids(0, getMainDuJoueur().size()+1);
		
		for(int i=0;i<nbCartesAJouer;i++) {
			int index = Utils.genererEntier(0, getMainDuJoueur().size());
			Carte carte = getMainDuJoueur().get(index);
			p.jouerCarte(carte, this);
		}
		
		p.getMancheCourante().getTourCourant().setMiseJoueur(this, mise);
	}

	@Override
	public void lancerClone(Partie p, Tour tour, Joueur joueur) {
		
	}

	@Override
	public void lancerRecyclage(Partie p, Tour tour, Joueur joueur) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lancerLarcin(Partie p, Tour tour, Joueur joueur) {
		// TODO Auto-generated method stub
		
	}
}
