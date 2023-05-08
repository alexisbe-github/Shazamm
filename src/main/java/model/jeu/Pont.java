package main.java.model.jeu;

import main.java.model.jeu.partie.Manche;

public class Pont implements Cloneable{

	private int positionMurFeu;
	private int indexLave, positionJoueurRouge, positionJoueurVert;
	private final int ECART_MUR_DE_FEU = 3;
	public static final int TAILLE_PONT = 19;

	public Pont() {
		positionMurFeu = (int) Math.ceil(TAILLE_PONT / 2);
		indexLave = 0;
		placerJoueurs();
	}

	/**
	 * Permet de savoir si la case est une case de lave à l'aide de son index passé
	 * en paramètre
	 * 
	 * @param indexCase int
	 * @return boolean si c'est une case de lave
	 */
	public boolean estUneCaseLave(int indexCase) {
		return (indexCase < indexLave && indexCase <= Math.ceil(TAILLE_PONT / 2))
				|| (indexCase >= TAILLE_PONT - this.indexLave && indexCase >= Math.ceil(TAILLE_PONT / 2));
	}

	public void placerJoueurs() {
		positionJoueurRouge = positionMurFeu - ECART_MUR_DE_FEU;
		positionJoueurVert = positionMurFeu + ECART_MUR_DE_FEU;
	}

	public boolean murDeFeuPousseUnSorcier() {
		return this.positionMurFeu == this.positionJoueurRouge || this.positionMurFeu == this.positionJoueurVert;
	}

	public boolean unSorcierEstTombe() {
		boolean res = false;
		if (positionJoueurRouge <= indexLave || positionJoueurVert >= TAILLE_PONT - this.indexLave)
			res = true;
		return res;
	}

	public String getVainqueur() {
		String res = "";
		if (positionJoueurRouge <= indexLave && positionJoueurVert >= TAILLE_PONT - this.indexLave) {
			res = "Egalité!";
		} else {
			if (positionJoueurRouge <= indexLave) {
				res = "Vert Grand Vainqueur!";
			}
			if (positionJoueurVert >= TAILLE_PONT - this.indexLave) {
				res = "Rouge Grand Vainqueur!";
			}
		}
		return res;
	}

	@Override
	public String toString() {
		String res = "\n";
		for (int i = 1; i <= this.positionJoueurRouge; i++) {
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
		for (int i = 0; i < TAILLE_PONT; i++) {
			if (i < indexLave || TAILLE_PONT - this.indexLave <= i) {
				res += "X";
			} else {
				res += "O";
			}
		}
		res += "\n R:" + this.positionJoueurRouge + " Mur:" + this.positionMurFeu + " V:" + this.positionJoueurVert;
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
		if (this.positionMurFeu < this.positionJoueurRouge)
			positionMurFeu++;
		if (this.positionMurFeu > this.positionJoueurVert)
			positionMurFeu--;
	}

	/**
	 * Place le mur de feu sur l'index en paramètre
	 * 
	 * @param index int
	 */
	public void placerMurDeFeu(int index) {
		this.positionMurFeu = index;
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
			if (this.positionJoueurVert + deplacement < TAILLE_PONT - indexLave)
				this.positionJoueurVert += deplacement;
		}
	}

	/**
	 * Incrémente le nombre de cases de lave pour chaque extrémité du pont
	 */
	public void effondrerMorceauxDuPont() {
		if (indexLave < (int) Math.ceil(TAILLE_PONT / 2))
			indexLave++;
	}

	public int getPosJoueurRouge() {
		return this.positionJoueurRouge;
	}

	public int getPosJoueurVert() {
		return this.positionJoueurVert;
	}

	public int getPosMurDeFeu() {
		return this.positionMurFeu;
	}

	public int getPosJoueur(Joueur joueur) {
		if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE))
			return this.positionJoueurRouge;
		return this.positionJoueurVert;
	}

	public String getPathMur() {
		return "src/main/resources/perso/mur.gif";
	}

	/**
	 * Méthode utilisée par l'IA pour évaluer la partie
	 * 
	 * @param joueur
	 * @return
	 */
	public int getDistanceEntreMurDeFeuEtJoueur(Joueur joueur) {
		if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE)) {
			return Math.abs(positionMurFeu - positionJoueurRouge) - 1;
		}
		return Math.abs(positionMurFeu - positionJoueurVert) - 1;
	}

	/**
	 * Méthode utilisée par l'IA pour évaluer la partie
	 *
	 * @return
	 */
	public int getDistanceEntreMurDeFeuEtMilieu() {
		return Math.abs((positionJoueurRouge + 3) - positionMurFeu);
	}

	/**
	 * Méthode utilisée par l'IA pour évaluer la partie
	 *
	 * @return
	 */
	public int getDistanceEntreJoueurEtLave(Joueur joueur) {
		if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE)) {
			return positionJoueurRouge+1;
		}
		return TAILLE_PONT - positionJoueurVert;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Pont pontClone = (Pont) super.clone();
		return pontClone;
	}
}
