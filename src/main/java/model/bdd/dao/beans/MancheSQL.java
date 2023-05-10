package main.java.model.bdd.dao.beans;

import java.io.Serializable;

/**
 * <code>{@link <a href=
 * "https://fr.wikipedia.org/wiki/JavaBeans">Java Bean</a>}</code> correspondant
 * à une manche, permettant de faire le lien à travers la couche <code>DAO</code>
 * entre la base de données et le modèle.
 */
public class MancheSQL implements Serializable {

	private static final long serialVersionUID = 20545808867934156L;

	private long id, id_partie;

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

}
