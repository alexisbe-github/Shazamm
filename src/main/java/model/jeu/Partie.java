package main.java.model.jeu;

import java.util.ArrayList;
import java.util.List;

import main.java.model.jeu.manche.Manche;

public class Partie {

	private int manche;
	private Joueur joueurVert, joueurRouge;
	private Pont pont;
	private List<Manche> listeManche;

	public Partie(Joueur j1, Joueur j2) {
		joueurRouge = j1;
		joueurVert = j2;
		manche = 0;
		pont = new Pont();
		listeManche = new ArrayList<>();
		lancerPartie();
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
	
	public void lancerPartie() {
		joueurRouge.piocherCartes(5);
		joueurVert.piocherCartes(5);
	}

}
