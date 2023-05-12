package main.java.model.bdd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.model.bdd.dao.beans.PartieSQL;

/**
 * La couche <code>DAO</code> secondaire qui fait le lien entre la base de
 * données et le <code>JavaBean</code>
 * {@link main.java.model.bdd.dao.beans.PartieSQL PartieSQL}, spécifiquement
 * pour la table <code><i>partie</i></code> de la base de données, qui
 * enregistre les informations relatives aux parties.
 * 
 * 
 * @see PartieSQL <code>PartieSQL</code><a role="link" aria-disabled="true"> -
 *      Le <code>JavaBean</code> géré par la classe</a>
 * @see DAO <code>DAO</code><a role="link" aria-disabled="true"> - La couche
 *      abstraite principale dont hérite cette classe</a>
 *
 */
public class DAOPartie extends DAO<PartieSQL> {

	/**
	 * Table <code><i>partie</i></code>, contenant différentes informations et
	 * statistiques sur les parties.
	 */
	private final String PARTIE = "partie";
	/**
	 * Colonne <code><i>id</i></code>, correspondant à l'identifiant de la partie.
	 */
	private final String ID = "id";
	/**
	 * Colonne <code><i>id_joueur1</i></code>, correspondant à l'identifiant du
	 * joueur 1.
	 */
	private final String ID_JOUEUR1 = "id_joueur1";
	/**
	 * Colonne <code><i>id_joueur2</i></code>, correspondant à l'identifiant du
	 * joueur 2.
	 */
	private final String ID_JOUEUR2 = "id_joueur2";
	/**
	 * Colonne <code><i>id_vainqueur</i></code>, correspondant à l'identifiant du
	 * vainqueur.
	 */
	private final String ID_VAINQUEUR = "id_vainqueur";

	/**
	 * {@inheritDoc}
	 * 
	 * @param id L'identifiant de la partie
	 * @return La partie correspondant à l'identifiant passé en paramètre
	 * 
	 */
	@Override
	public PartieSQL trouver(long id) {
		PartieSQL partie = new PartieSQL();
		Connection connexion = this.connexion.getConnexion();
		try {
			try (PreparedStatement pstmt = connexion.prepareStatement(
					"SELECT * FROM " + PARTIE + " WHERE " + ID + " = ?;", ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY)) {
				pstmt.setLong(1, id);
				pstmt.execute();
				try (ResultSet rs = pstmt.getResultSet()) {
					if (rs.first()) {
						partie.setId(id);
						partie.setIdJoueur1(rs.getLong(ID_JOUEUR1));
						partie.setIdJoueur2(rs.getLong(ID_JOUEUR2));
						partie.setIdVainqueur(rs.getLong(ID_VAINQUEUR));
					}
				}
			}
		} catch (SQLException e) {

		}
		return partie;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param partie La partie à créer dans la base de données
	 * @return La partie
	 */
	@Override
	public PartieSQL creer(PartieSQL partie) {
		Connection connexion = this.connexion.getConnexion();
		try {
			try (PreparedStatement pstmt1 = connexion
					.prepareStatement("SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES " + "WHERE table_name = '"
							+ PARTIE + "'")) {
				pstmt1.execute();

				try (ResultSet rsid = pstmt1.getResultSet()) {
					if (rsid.next()) {
						long id = rsid.getLong(1);
						partie.setId(id);
					}
				}
				pstmt1.close();

			}

			try (PreparedStatement pstmt2 = connexion
					.prepareStatement("INSERT INTO " + PARTIE + " VALUES (?, ?, ?, ?);")) {
				pstmt2.setLong(1, partie.getId());
				pstmt2.setLong(2, partie.getIdJoueur1());
				pstmt2.setLong(3, partie.getIdJoueur2());
				pstmt2.setLong(4, partie.getIdVainqueur());
				pstmt2.execute();
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return partie;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param partie La partie à mettre à jour dans la base de données
	 * @return La partie
	 */
	@Override
	public PartieSQL maj(PartieSQL partie) {
		Connection connexion = this.connexion.getConnexion();
		try (PreparedStatement pstmt = connexion.prepareStatement("UPDATE " + PARTIE + " SET " + ID_JOUEUR1 + " = ?, "
				+ ID_JOUEUR2 + " = ?, " + ID_VAINQUEUR + " = ? WHERE " + ID + " = ?")) {
			pstmt.setLong(1, partie.getIdJoueur1());
			pstmt.setLong(2, partie.getIdJoueur2());
			pstmt.setLong(3, partie.getIdVainqueur());
			pstmt.setLong(4, partie.getId());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return partie;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param partie La partie à supprimer de la base de données
	 */
	@Override
	public void supprimer(PartieSQL partie) {
		Connection connexion = this.connexion.getConnexion();
		try (PreparedStatement pstmt = connexion.prepareStatement("DELETE FROM " + PARTIE + " WHERE " + ID + " = ?;")) {
			pstmt.setLong(1, partie.getId());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
