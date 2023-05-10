package main.java.model.jeu.ia;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;
import main.java.vue.ILancementStrategy;

public class IAExperte extends IAEtatJeu implements IA,ILancementStrategy{

	public IAExperte(ECouleurJoueur couleur, String nom, String prenom, String avatar) {
		super(couleur, nom, prenom, avatar);
	}

	@Override
	public void jouerTour(Partie p) {
		
	}

	@Override
	public void lancerClone(Partie p, Tour tour, Joueur joueur) {
		// TODO Auto-generated method stub
		
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
