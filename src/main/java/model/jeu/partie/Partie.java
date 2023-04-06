package main.java.model.jeu.partie;

import java.util.ArrayList;
import java.util.List;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.Pont;
import main.java.model.jeu.carte.Carte;

public class Partie {

	private Joueur joueur1, joueur2;
	private Pont pont;
	private List<Manche> listeManche;

	public Partie(Joueur j1, Joueur j2) {
		joueur2 = j1;
		joueur1 = j2;
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

	private void lancerPartie() {
		joueur2.initialiserPaquet(this);
		joueur1.initialiserPaquet(this);
		joueur2.piocherCartes(5);
		joueur1.piocherCartes(5);
		joueur2.remplirReserveDeMana();
		joueur1.remplirReserveDeMana();
		this.listeManche.add(new Manche());
	}

	public void lancerNouvelleManche() {
		joueur2.melangerPaquet();
		joueur1.melangerPaquet();
		joueur2.piocherCartes(3);
		joueur1.piocherCartes(3);
		joueur2.remplirReserveDeMana();
		joueur1.remplirReserveDeMana();
		pont.effondrerMorceauDuPont();
		this.pont.setup();
		this.listeManche.add(new Manche());
	}

	public Manche getMancheCourante() {
		return this.listeManche.get(this.listeManche.size() - 1);
	}

	public void setupPont() {
		this.pont.setup();
	}

	public int getPosJoueur(ECouleurJoueur couleur) {
		if (couleur.equals(ECouleurJoueur.ROUGE))
			return this.pont.getPosJoueurRouge();
		else
			return this.pont.getPosJoueurVert();
	}

	public Pont getPont() {
		return this.pont;
	}

	public Joueur getJoueur1() {
		return this.joueur1;
	}

	public Joueur getJoueur2() {
		return this.joueur2;
	}

}
