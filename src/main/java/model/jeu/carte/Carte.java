package main.java.model.jeu.carte;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.Partie;

public abstract class Carte {

	protected String nom,path,description;
	protected Partie partie;
	protected int numeroCarte;
	protected Joueur joueur;
	
	public abstract void lancerEffet(Joueur caster,Joueur adversaire);
	
	public int getNumeroCarte() {
		return this.numeroCarte;
	}
	
	public Joueur getJoueur() {
		return this.joueur;
	}
	
	public Joueur getAdversaire() {
		if(this.joueur.getCouleur().equals(ECouleurJoueur.ROUGE)) return partie.getJoueurVert();
		else return partie.getJoueurRouge();
	}

}
