package main.java.model.jeu;

public class Partie {

	private int tour, indexPositionMur;
	private Joueur joueur1, joueur2;
	private final int TAILLE_PONT = 19;
	private int[] pont; // -1:dalle de pont effondree / 0:dalle de pont non effondree / 1:j1 / 2:j2 /
						// 3:mur de feu

	public Partie(Joueur j1, Joueur j2) {
		joueur1 = j1;
		joueur2 = j2;
		tour = 0;
		pont = new int[TAILLE_PONT];
		indexPositionMur = (int) Math.ceil(TAILLE_PONT / 2);
		pont[indexPositionMur] = 3; // on place le mur au milieu du pont
	}

	/**
	 * Déplace le mur de feu de dp sur le pont (dp pouvant être négatif)
	 * 
	 * @param dp int étant le déplacement
	 */
	public void deplacerMur(int dp) {
		if (verifierDeplacement(indexPositionMur, dp))
			indexPositionMur += dp;
	}

	/**
	 * Vérifie si le déplacement sur le pont ne dépasse pas le pont
	 * 
	 * @param x  coordonné de départ
	 * @param dp déplacement à effectuer
	 * @return boolean
	 */
	private boolean verifierDeplacement(int x, int dp) {
		return x + dp > 0 && x + dp < TAILLE_PONT;
	}

}
