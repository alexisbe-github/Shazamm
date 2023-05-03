package main.java.vue;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;

public interface ILancementStrategy {

	public void lancerClone(Partie p, Tour tour, Joueur joueur);
	
	public void lancerRecyclage(Partie p, Tour tour, Joueur joueur);
	
	public void lancerLarcin(Partie p, Tour tour, Joueur joueur);

}
