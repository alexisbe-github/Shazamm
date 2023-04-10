package main.java.model.jeu;

public class Pont {

	private int positionMurFeu;
	private int indexLave, positionJoueurRouge, positionJoueurVert;
	private final int ECART_MUR_DE_FEU = 3;
	private final int TAILLE_PONT = 19;

	public Pont() {
		positionMurFeu = (int) Math.ceil(TAILLE_PONT / 2);
		indexLave = 0;
		placerJoueurs();
	}

	public void placerJoueurs() {
		positionJoueurRouge = positionMurFeu - ECART_MUR_DE_FEU;
		positionJoueurVert = positionMurFeu + ECART_MUR_DE_FEU;
	}

	public boolean unSorcierEstTombe() {
		boolean res = false;
		if (positionJoueurRouge <= indexLave || positionJoueurVert >= indexLave)
			res = true;
		return res;
	}

	@Override
	public String toString() {
		String res = "\n";
		for (int i = 0; i < this.positionJoueurRouge; i++) {
			res += " ";
		}
		res += "R";
		for (int i = this.positionJoueurRouge + 1; i < this.positionMurFeu; i++) {
			res += " ";
		}
		res += "|";
		for (int i = this.positionMurFeu; i < this.positionJoueurVert - 1; i++) {
			res += " ";
		}
		res += "V\n";
		for (int i = 0; i < this.TAILLE_PONT; i++) {
			if (i < indexLave || this.TAILLE_PONT - this.indexLave < i) {
				res += "X";
			} else {
				res += "O";
			}
		}
		return res;
	}

	/**
	 * Deplace le mur de feu sur le pont
	 * 
	 * @param dp int
	 */
	public void deplacerMurDeFeu(int dp) {
		if (positionMurFeu > 1 && positionMurFeu < TAILLE_PONT)
			positionMurFeu += dp;
	}

	/**
	 * Fait reculer selon le joueur (rouge ou vert) sur le pont
	 * 
	 * @param deplacement, int
	 * @param joueur,      EJoueur
	 */
	public void reculerJoueur(int deplacement, ECouleurJoueur couleurJoueur) {
		if (couleurJoueur == ECouleurJoueur.ROUGE) {
			if (this.positionJoueurRouge + deplacement > indexLave)
				this.positionJoueurRouge -= deplacement;
		} else {
			if (this.positionJoueurVert + deplacement < this.TAILLE_PONT - indexLave)
				this.positionJoueurVert += deplacement;
		}
	}

	/**
	 * Incrémente le nombre de cases de lave pour chaque extrémité du pont
	 */
	public void effondrerMorceauDuPont() {
		if (indexLave < (int) Math.ceil(TAILLE_PONT / 2))
			indexLave++;
	}

	public int getPosJoueurRouge() {
		return this.positionJoueurRouge;
	}

	public int getPosJoueurVert() {
		return this.positionJoueurVert;
	}

}
