package main.java.model.dao.beans;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <code>{@link <a href=
 * "https://fr.wikipedia.org/wiki/JavaBeans">Java Bean</a>}</code> correspondant
 * à un tour, permettant de faire le lien à travers la couche <code>DAO</code>
 * entre la base de données et le modèle.
 */
public class TourSQL implements Serializable {

	private static final long serialVersionUID = 508786312480954317L;

	private long id, id_manche;
	private int position_mur_flammes, position_joueur_1, position_joueur_2, mise_joueur_1, mise_joueur_2,
			puissance_joueur_1, puissance_joueur_2, numero_tour;
	private Timestamp date;

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
	 * @return L'identifiant de la manche
	 */
	public long getIdManche() {
		return id_manche;
	}

	/**
	 * Met à jour l'identifiant de la manche
	 * 
	 * @param id_manche L'identifiant de la manche
	 */
	public void setIdManche(long id_manche) {
		this.id_manche = id_manche;
	}

	/**
	 * @return La position du mur de flammes
	 */
	public int getPositionMurFlammes() {
		return position_mur_flammes;
	}

	/**
	 * Met à jour la position du mur de flammes
	 * 
	 * @param position_mur_flammes La position du mur de flammes
	 */
	public void setPositionMurFlammes(int position_mur_flammes) {
		this.position_mur_flammes = position_mur_flammes;
	}

	/**
	 * @return La position du joueur 1
	 */
	public int getPositionJoueur1() {
		return position_joueur_1;
	}

	/**
	 * Met à jour la position du joueur 1
	 * 
	 * @param position_joueur_1 La position du joueur 1
	 */
	public void setPositionJoueur1(int position_joueur_1) {
		this.position_joueur_1 = position_joueur_1;
	}

	/**
	 * @return La position du joueur 2
	 */
	public int getPositionJoueur2() {
		return position_joueur_2;
	}

	/**
	 * Met à jour la position du joueur 2
	 * 
	 * @param position_joueur_2 La position du joueur 2
	 */
	public void setPositionJoueur2(int position_joueur_2) {
		this.position_joueur_2 = position_joueur_2;
	}

	/**
	 * @return La mise du joueur 1
	 */
	public int getMiseJoueur1() {
		return mise_joueur_1;
	}

	/**
	 * Met à jour la mise du joueur 1
	 * 
	 * @param mise_joueur_1 La mise du joueur 1
	 */
	public void setMiseJoueur1(int mise_joueur_1) {
		this.mise_joueur_1 = mise_joueur_1;
	}

	/**
	 * @return La mise du joueur 2
	 */
	public int getMiseJoueur2() {
		return mise_joueur_2;
	}

	/**
	 * Met à jour la mise du joueur 2
	 * 
	 * @param mise_joueur_2 La mise du joueur 2
	 */
	public void setMiseJoueur2(int mise_joueur_2) {
		this.mise_joueur_2 = mise_joueur_2;
	}

	/**
	 * @return La puissance du joueur 1
	 */
	public int getPuissanceJoueur1() {
		return puissance_joueur_1;
	}

	/**
	 * Met à jour la puissance du joueur 1
	 * 
	 * @param puissance_joueur_1 La puissance du joueur 1
	 */
	public void setPuissanceJoueur1(int puissance_joueur_1) {
		this.puissance_joueur_1 = puissance_joueur_1;
	}

	/**
	 * @return La puissance du joueur 2
	 */
	public int getPuissanceJoueur2() {
		return puissance_joueur_2;
	}

	/**
	 * Met à jour la puissance du joueur 2
	 * 
	 * @param puissance_joueur_2 La puissance du joueur 2
	 */
	public void setPuissanceJoueur2(int puissance_joueur_2) {
		this.puissance_joueur_2 = puissance_joueur_2;
	}

	/**
	 * @return Le numéro du tour
	 */
	public int getNumeroTour() {
		return numero_tour;
	}

	/**
	 * Met à jour le numéro du tour
	 * 
	 * @param numero_tour Le numéro du tour
	 */
	public void setNumeroTour(int numero_tour) {
		this.numero_tour = numero_tour;
	}

	/**
	 * @return La date
	 */
	public Timestamp getDate() {
		return date;
	}

	/**
	 * Met à jour la date
	 * 
	 * @param date La date
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}

}
