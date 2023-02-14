package main.java.model.jeu;

public class Partie {

	private int tour;
	private Joueur joueur1, joueur2;
	private final int TAILLE_PONT = 19;
	private int[] pont; // -1:dalle de pont effondree / 0:dalle de pont non effondree / 1:j1 / 2:j2 /
						// 3:mur de feu

	public Partie(Joueur j1, Joueur j2) {
		joueur1 = j1;
		joueur2 = j2;
		tour = 0;
		pont = new int[TAILLE_PONT];
		int indexPositionMur = (int) Math.ceil(TAILLE_PONT / 2);
		pont[indexPositionMur] = 3; // on place le mur au milieu du pont
	}
	
	/**
	 * Calcule l'index de la position du mur de feu sur le pont
	 * @return int
	 */
	public int getIndexPositionMur() {
		for(int i=0;i<pont.length;i++) {
			if(pont[i] == 3) return i;
		}
		return -1;
	}

	public int[] getPont() {
		return pont;
	}

	/**
	 * Déplace le mur de feu de dp sur le pont (dp pouvant être négatif)
	 * 
	 * @param dp int étant le déplacement
	 */
	public void deplacerMur(int dp) {
		int indiceMur = getIndexPositionMur();
		if (verifierDeplacement(indiceMur, dp)) {
			pont[indiceMur] = 0;
			pont[indiceMur + dp] = 3;
		}
			
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
