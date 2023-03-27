package main.java.model.jeu.partie;

import java.util.ArrayList;
import java.util.List;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.Pont;
import main.java.model.jeu.carte.Carte;

public class Partie {

	private Joueur joueurVert, joueurRouge;
	private Pont pont;
	private List<Manche> listeManche;

	public Partie(Joueur j1, Joueur j2) {
		joueurRouge = j1;
		joueurVert = j2;
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
	 * @param dp int
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
		joueurRouge.initialiserPaquet(this);
		joueurVert.initialiserPaquet(this);
		joueurRouge.piocherCartes(5);
		joueurVert.piocherCartes(5);
		this.listeManche.add(new Manche());
	}

	public void lancerNouvelleManche() {
		joueurRouge.melangerPaquet();
		joueurVert.melangerPaquet();
		joueurRouge.piocherCartes(3);
		joueurVert.piocherCartes(3);
		joueurRouge.remplirReserveDeMana();
		joueurVert.remplirReserveDeMana();
		pont.effondrerMorceauDuPont();
		this.listeManche.add(new Manche());
	}

	public Manche getMancheCourante() {
		return this.listeManche.get(this.listeManche.size() - 1);
	}

	public Pont getPont() {
		return this.pont;
	}

	public Joueur getJoueurVert() {
		return this.joueurVert;
	}

	public Joueur getJoueurRouge() {
		return this.joueurRouge;
	}

}
