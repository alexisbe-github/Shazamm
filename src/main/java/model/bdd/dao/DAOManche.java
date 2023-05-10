package main.java.model.bdd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.model.bdd.dao.beans.MancheSQL;

/**
 * La couche <code>DAO</code> secondaire qui fait le lien entre la base de
 * données et le <code>JavaBean</code>
 * {@link main.java.model.bdd.dao.beans.MancheSQL MancheSQL}, spécifiquement
 * pour la table <code><i>manche</i></code> de la base de données, qui
 * enregistre les informations relatives aux manches.
 * 
 * 
 * @see MancheSQL <code>MancheSQL</code><a role="link" aria-disabled="true"> -
 *      Le <code>JavaBean</code> géré par la classe</a>
 * @see DAO <code>DAO</code><a role="link" aria-disabled="true"> - La couche
 *      abstraite principale dont hérite cette classe</a>
 *
 */
public class DAOManche extends DAO<MancheSQL> {

	/**
	 * Table <code><i>manche</i></code>, contenant différentes informations et
	 * statistiques sur les manches.
	 */
	private final String MANCHE = "manche";
	/**
	 * Colonne <code><i>id</i></code>, correspondant à l'identifiant de la manche.
	 */
	private final String ID = "id";
	/**
	 * Colonne <code><i>id_partie</i></code>, correspondant à l'identifiant de la
	 * partie.
	 */
	private final String ID_PARTIE = "id_partie";

	/**
	 * {@inheritDoc}
	 * 
	 * @param id L'identifiant de la manche
	 * @return La manche correspondant à l'identifiant passé en paramètre
	 * 
	 */
	@Override
	public MancheSQL trouver(long id) {
		MancheSQL manche = new MancheSQL();
		Connection connexion = this.connexion.getConnexion();
		try (PreparedStatement pstmt = connexion.prepareStatement("SELECT * FROM " + MANCHE + " WHERE " + ID + " = ?;",
				ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			pstmt.setLong(1, id);
			pstmt.execute();
			try (ResultSet rs = pstmt.getResultSet()) {
				if (rs.first()) {
					manche.setId(id);
					manche.setIdPartie(rs.getLong(ID_PARTIE));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return manche;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param manche La manche à créer dans la base de données
	 * @return La manche
	 */
	@Override
	public MancheSQL creer(MancheSQL manche) {
		Connection connexion = this.connexion.getConnexion();
		try {
			try (PreparedStatement pstmt1 = connexion
					.prepareStatement("SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES " + "WHERE table_name = '"
							+ MANCHE + "'")) {
				pstmt1.execute();

				try (ResultSet rsid = pstmt1.getResultSet()) {
					if (rsid.next()) {
						long id = rsid.getLong(1);
						manche.setId(id);
					}
				}
				pstmt1.close();

			}

			try (PreparedStatement pstmt2 = connexion.prepareStatement("INSERT INTO " + MANCHE + " VALUES (?, ?);")) {
				pstmt2.setLong(1, manche.getId());
				pstmt2.setLong(2, manche.getIdPartie());
				pstmt2.execute();
			}
		} catch (SQLException e) {

		}
		return manche;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param manche La manche à mettre à jour dans la base de données
	 * @return La manche
	 */
	@Override
	public MancheSQL maj(MancheSQL manche) {
		Connection connexion = this.connexion.getConnexion();
		try (PreparedStatement pstmt = connexion
				.prepareStatement("UPDATE " + MANCHE + " SET " + ID_PARTIE + " = ? WHERE " + ID + " = ?")) {
			pstmt.setLong(1, manche.getIdPartie());
			pstmt.setLong(2, manche.getId());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return manche;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param manche La manche à supprimer de la base de données
	 */
	@Override
	public void supprimer(MancheSQL manche) {
		Connection connexion = this.connexion.getConnexion();
		try (PreparedStatement pstmt = connexion.prepareStatement("DELETE FROM " + MANCHE + " WHERE " + ID + " = ?;")) {
			pstmt.setLong(1, manche.getId());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
