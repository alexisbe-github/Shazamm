package main.java.model.dao.beans;

import java.io.Serializable;

/**
 * <code>{@link <a href=
 * "https://fr.wikipedia.org/wiki/JavaBeans">Java Bean</a>}</code> correspondant
 * à une carte, permettant de faire le lien à travers la couche <code>DAO</code>
 * entre la base de données et le modèle.
 */
public class CarteSQL implements Serializable {

	private static final long serialVersionUID = 5184457690548781069L;

	private long id, id_tour, id_joueur;
	private int numero_carte;

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
	 * @return L'identifiant du tour
	 */
	public long getIdTour() {
		return id_tour;
	}

	/**
	 * Met à jour l'identifiant du tour.
	 * 
	 * @param id_tour L'identifiant du tour
	 */
	public void setIdTour(long id_tour) {
		this.id_tour = id_tour;
	}

	/**
	 * 
	 * @return L'identifiant du joueur
	 */
	public long getIdJoueur() {
		return id_joueur;
	}

	/**
	 * Met à jour l'identifiant du joueur.
	 * 
	 * @param id_joueur L'identifiant du joueur
	 */
	public void setIdJoueur(long id_joueur) {
		this.id_joueur = id_joueur;
	}
	/**
	 * 
	 * @return Le numéro de la carte
	 */
	public int getNumeroCarte() {
		return numero_carte;
	}

	/**
	 * Met à jour le numéro de la carte.
	 * 
	 * @param numero_carte Le numéro de la carte
	 */
	public void setNumeroCarte(int numero_carte) {
		this.numero_carte = numero_carte;
	}

}
