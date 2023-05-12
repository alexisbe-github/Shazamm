package main.java.model.bdd.dao.beans;

import java.io.Serializable;

/**
 * <code>{@link <a href=
 * "https://fr.wikipedia.org/wiki/JavaBeans">Java Bean</a>}</code> correspondant
 * à une partie, permettant de faire le lien à travers la couche <code>DAO</code>
 * entre la base de données et le modèle.
 */
public class PartieSQL implements Serializable {

	private static final long serialVersionUID = 8975104814067562L;

	private long id, id_joueur_1, id_joueur_2, id_vainqueur;

	/**
	 * 
	 * @return L'identifiant
	 */
	public long getId() {
		return id;
	}

	/**
	 * Instancie l'identifiant
	 * 
	 * @param id L'identifiant
	 */
	public final void setId(long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return L'identifiant du joueur 1
	 */
	public long getIdJoueur1() {
		return id_joueur_1;
	}

	/**
	 * Met à jour l'identifiant du joueur 1.
	 * 
	 * @param id_joueur_1 L'identifiant du joueur 1
	 */
	public void setIdJoueur1(long id_joueur_1) {
		this.id_joueur_1 = id_joueur_1;
	}
	
	/**
	 * 
	 * @return L'identifiant du joueur 2
	 */
	public long getIdJoueur2() {
		return id_joueur_2;
	}

	/**
	 * Met à jour l'identifiant du joueur 2.
	 * 
	 * @param id_joueur_2 L'identifiant du joueur 2
	 */
	public void setIdJoueur2(long id_joueur_2) {
		this.id_joueur_2 = id_joueur_2;
	}
	
	/**
	 * 
	 * @return L'identifiant du vainqueur
	 */
	public long getIdVainqueur() {
		return id_vainqueur;
	}

	/**
	 * Met à jour l'identifiant du vainqueur.
	 * 
	 * @param id_vainqueur L'identifiant du vainqueur
	 */
	public void setIdVainqueur(long id_vainqueur) {
		this.id_vainqueur = id_vainqueur;
	}

}
