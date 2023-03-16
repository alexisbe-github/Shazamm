package main.java.model.jeu.carte;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.Partie;

public abstract class Carte {

	protected String nom,path,description;
	protected Partie partie;
	protected Joueur joueur;
	protected int numeroCarte;

	public abstract void jouer();
	
	public ECouleurJoueur getCouleur() {
		return joueur.getCouleur();
	}

}
