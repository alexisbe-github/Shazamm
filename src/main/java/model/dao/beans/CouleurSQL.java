package main.java.model.dao.beans;

import java.io.Serializable;

import main.java.model.jeu.ECouleurJoueur;

/**
 * <code>{@link <a href=
 * "https://fr.wikipedia.org/wiki/JavaBeans">Java Bean</a>}</code> correspondant
 * à une couleur, permettant de faire le lien à travers la couche <code>DAO</code>
 * entre la base de données et le modèle.
 */
public class CouleurSQL implements Serializable {

	private static final long serialVersionUID = 6445870878405187419L;

	private long id_partie;
	private ECouleurJoueur couleur_j1, couleur_j2;

	/**
	 * 
	 * @return L'identifiant de la partie
	 */
	public long getIdPartie() {
		return id_partie;
	}

	/**
	 * Met à jour l'identifiant de la partie. 
	 * 
	 * @param id_partie L'identifiant de la partie
	 */
	public void setIdPartie(long id_partie) {
		this.id_partie = id_partie;
	}

	/**
	 * @return La couleur du joueur 1
	 */
	public ECouleurJoueur getCouleurJ1() {
		return couleur_j1;
	}

	/**
	 * Met à jour la couleur du joueur 1.
	 * 
	 * @param couleur_j1 La couleur du joueur 1
	 */
	public void setCouleurJ1(ECouleurJoueur couleur_j1) {
		this.couleur_j1 = couleur_j1;
	}
	
	/**
	 * @return La couleur du joueur 2
	 */
	public ECouleurJoueur getCouleurJ2() {
		return couleur_j2;
	}

	/**
	 * Met à jour la couleur du joueur 2.
	 * 
	 * @param couleur_j2 La couleur du joueur 2
	 */
	public void setCouleurJ2(ECouleurJoueur couleur_j2) {
		this.couleur_j2 = couleur_j2;
	}
	
}