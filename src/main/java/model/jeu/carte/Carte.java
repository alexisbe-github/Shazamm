package main.java.model.jeu.carte;

import java.util.Objects;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;

public abstract class Carte {

	protected String nom, path, description;
	protected Partie partie;
	protected int numeroCarte;
	protected Joueur joueur;

	public abstract void lancerEffet(Tour tour);

	public int getNumeroCarte() {
		return this.numeroCarte;
	}

	public Joueur getJoueur() {
		return this.joueur;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void changerDetenteurCarte(Joueur nouveauDetenteur) {
		joueur = nouveauDetenteur;
	}
	
	public void defausser() {
		this.joueur.defausser(this);
	}

	/**
	 * Deux cartes sont equals si elles ont le même numéro de carte et la même
	 * couleur
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carte other = (Carte) obj;
		return Objects.equals(numeroCarte, other.numeroCarte)
				&& Objects.equals(joueur.getCouleur(), other.joueur.getCouleur());
	}

	@Override
	public String toString() {
		return "Carte "+ numeroCarte + ": nom : " + nom + ", desc : " + description;
	}

	public String getPath() {
		String res = "src/main/resources/cartes/";
		if(this.numeroCarte < 10) res += "0";
		res+=this.numeroCarte;
		if(this.joueur.getCouleur().equals(ECouleurJoueur.ROUGE)) res+= "r"; 
		else res += "v";
		return res+".jpg";
	}
}
