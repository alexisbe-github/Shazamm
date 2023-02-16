package main.java.model.jeu;

import main.java.model.jeu.Joueur.ECouleurJoueur;

public class Partie {

	private int tour;
	private Joueur joueurVert, joueurRouge;
	private Pont pont;

	public Partie(Joueur j1, Joueur j2) {
		joueurRouge = j1;
		joueurVert = j2;
		tour = 0;
		pont = new Pont();
	}

	/**
	 * Déplace le mur de feu de dp sur le pont (dp pouvant être négatif)
	 * 
	 * @param dp int étant le déplacement
	 */
	public void deplacerMur(int dp) {
		pont.deplacerMurDeFeu(dp);
	}

	/**
	 * Faire reculer de dp le joueur vert du pont
	 * 
	 * @param dp
	 */
	public void reculerJoueurVert(int dp) {
		pont.reculerJoueur(dp, ECouleurJoueur.VERT);
	}

	/**
	 * Faire reculer de dp le joueur rouge du pont
	 * 
	 * @param dp
	 */
	public void reculerJoueurRouge(int dp) {
		pont.reculerJoueur(dp, ECouleurJoueur.ROUGE);
	}

}
