package main.java.model.dao;

/**
 * La couche <code>DAO</code> principale dont vont hériter tous les
 * <code>DAO</code> de toutes les tables
 * 
 * @author Emile
 *
 * @param <T> Le type du <code>JavaBean</code>
 */
public abstract class DAO<T> {

	/**
	 * La connexion
	 * 
	 * @see main.java.model.dao.Connexion
	 */
	public Connexion connexion = Connexion.getInstance();

	/**
	 * Permet de récupérer un objet via son identifiant.
	 * 
	 * @param id L'identifiant
	 * @return L'objet correspondant à l'identifiant
	 */
	public abstract T trouver(long id);

	/**
	 * Permet de créer une entrée dans la base de données.
	 * 
	 * @param obj L'objet à créer
	 * @return L'objet créé
	 */
	public abstract T creer(T obj);

	/**
	 * Permet de mettre à jour les données d'une entrée dans la base.
	 * 
	 * @param obj L'objet à mettre à jour
	 * @return L'objet mis à jour
	 */
	public abstract T maj(T obj);

	/**
	 * Permet la suppression d'une entrée de la base.
	 * 
	 * @param obj L'objet à supprimer
	 */
	public abstract void supprimer(T obj);
}
