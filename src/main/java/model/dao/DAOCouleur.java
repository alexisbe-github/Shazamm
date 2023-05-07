package main.java.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.model.dao.beans.CouleurSQL;
import main.java.model.jeu.ECouleurJoueur;

/**
 * La couche <code>DAO</code> secondaire qui fait le lien entre la base de
 * données et le <code>JavaBean</code>
 * {@link main.java.model.dao.beans.CouleurSQL CouleurSQL}, spécifiquement pour
 * la table <code><i>couleur</i></code> de la base de données, qui enregistre
 * les informations relatives aux couleurs.
 * 
 * 
 * @see CouleurSQL <code>CouleurSQL</code><a role="link" aria-disabled="true"> -
 *      Le <code>JavaBean</code> géré par la classe</a>
 * @see DAO <code>DAO</code><a role="link" aria-disabled="true"> - La couche
 *      abstraite principale dont hérite cette classe</a>
 *
 */
public class DAOCouleur extends DAO<CouleurSQL> {

	/**
	 * Table <code><i>couleur</i></code>, contenant différentes informations sur les
	 * couleurs.
	 */
	private final String COULEUR = "couleur";
	/**
	 * Colonne <code><i>id_partie</i></code>, correspondant à l'identifiant de la
	 * partie.
	 */
	private final String ID = "id_partie";
	/**
	 * Colonne <code><i>couleur_j1</i></code>, correspondant à la couleur du couleur
	 * 1.
	 */
	private final String COULEUR_J1 = "couleur_j1";
	/**
	 * Colonne <code><i>couleur_j2</i></code>, correspondant à la couleur du couleur
	 * 2.
	 */
	private final String COULEUR_J2 = "couleur_j2";

	/**
	 * {@inheritDoc}
	 * 
	 * @param id L'identifiant de la partie
	 * @return Les couleurs des joueurs de la partie correspondant à l'identifiant
	 *         passé en paramètre
	 * 
	 */
	@Override
	public CouleurSQL trouver(long id) {
		CouleurSQL couleur = new CouleurSQL();
		try (Connection connexion = this.connexion.getConnexion()) {
			try (PreparedStatement pstmt = connexion.prepareStatement(
					"SELECT * FROM " + COULEUR + " WHERE " + ID + " = ?;", ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY)) {
				pstmt.setLong(1, id);
				pstmt.execute();
				try (ResultSet rs = pstmt.getResultSet()) {
					if (rs.first()) {
						couleur.setIdPartie(id);
						couleur.setCouleurJ1(rs.getString(COULEUR_J1).toLowerCase().equals("vert") ? ECouleurJoueur.VERT
								: ECouleurJoueur.ROUGE);
						couleur.setCouleurJ2(
								rs.getString(COULEUR_J2).toLowerCase().equals("rouge") ? ECouleurJoueur.ROUGE
										: ECouleurJoueur.VERT);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {

		} finally {
			this.connexion.fermerConnexion();
		}
		return couleur;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param couleur La couleur à créer dans la base de données
	 * @return La couleur
	 */
	@Override
	public CouleurSQL creer(CouleurSQL couleur) {
		try (Connection connexion = this.connexion.getConnexion();
				PreparedStatement pstmt = connexion.prepareStatement("INSERT INTO " + COULEUR + " VALUES (?, ?, ?);")) {
			pstmt.setLong(1, couleur.getIdPartie());
			pstmt.setString(2, couleur.getCouleurJ1().equals(ECouleurJoueur.VERT) ? "vert" : "rouge");
			pstmt.setString(3, couleur.getCouleurJ2().equals(ECouleurJoueur.ROUGE) ? "rouge" : "vert");
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.connexion.fermerConnexion();
		}
		return couleur;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param couleur La couleur à mettre à jour dans la base de données
	 * @return La couleur
	 */
	@Override
	public CouleurSQL maj(CouleurSQL couleur) {
		try (Connection connexion = this.connexion.getConnexion();
				PreparedStatement pstmt = connexion.prepareStatement("UPDATE " + COULEUR + " SET " + COULEUR_J1
						+ " = ?, " + COULEUR_J2 + " = ? WHERE " + ID + " = ?")) {
			pstmt.setString(1, couleur.getCouleurJ1().equals(ECouleurJoueur.VERT) ? "vert" : "rouge");
			pstmt.setString(2, couleur.getCouleurJ2().equals(ECouleurJoueur.ROUGE) ? "rouge" : "vert");
			pstmt.setLong(3, couleur.getIdPartie());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.connexion.fermerConnexion();
		}
		return couleur;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param couleur La couleur à supprimer de la base de données
	 */
	@Override
	public void supprimer(CouleurSQL couleur) {
		try (Connection connexion = this.connexion.getConnexion();
				PreparedStatement pstmt = connexion
						.prepareStatement("DELETE FROM " + COULEUR + " WHERE " + ID + " = ?;")) {
			pstmt.setLong(1, couleur.getIdPartie());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.connexion.fermerConnexion();
		}
	}

}
