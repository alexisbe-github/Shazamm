package main.java.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;

import main.java.model.dao.beans.JoueurSQL;

/**
 * La couche <code>DAO</code> secondaire qui fait le lien entre la base de
 * données et le <code>JavaBean</code>
 * {@link main.java.model.dao.beans.JoueurSQL JoueurSQL}
 * 
 * @author Emile
 * 
 * @see JoueurSQL <code>JoueurSQL</code><a role="link" aria-disabled="true"> -
 *      Le <code>JavaBean</code> géré par la classe</a>
 * @see DAO <code>DAO</code><a role="link" aria-disabled="true"> - La couche
 *      abstraite principale dont hérite cette classe</a>
 *
 */
public class DAOJoueur extends DAO<JoueurSQL> {

	/**
	 * {@inheritDoc}
	 * 
	 * @param id L'identifiant du joueur
	 * @return Le joueur correspondant à l'identifiant passé en paramètre
	 * 
	 */
	@Override
	public JoueurSQL trouver(long id) {
		JoueurSQL joueur = new JoueurSQL();
		try (PreparedStatement pstmt = this.connexion.getConnexion()
				.prepareStatement("SELECT * FROM joueur WHERE id = ?;")) {
			pstmt.setLong(1, id);
			pstmt.execute();
			try (ResultSet rs = pstmt.getResultSet()) {
				if (rs.first()) {
					joueur.setId(id);
					joueur.setNom(rs.getString("nom"));
					joueur.setPrenom(rs.getString("prenom"));
					joueur.setAvatar(new ImageIcon(rs.getString("avatar")));
					joueur.setNbPartiesGagnees(rs.getInt("nb_parties_gagnees"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return joueur;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param joueur Le joueur à créer dans la base de données
	 * @return Le joueur
	 */
	@Override
	public JoueurSQL creer(JoueurSQL joueur) {
		try {
			PreparedStatement pstmt1 = this.connexion.getConnexion().prepareStatement("SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES "
					+ "WHERE table_name = 'joueur'");
			pstmt1.execute();
			try (ResultSet rsid = pstmt1.getResultSet()) {
				pstmt1.close();
				long id = rsid.getLong(1);
				joueur.setId(id);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		try (PreparedStatement pstmt2 = this.connexion.getConnexion().prepareStatement(
				"INSERT INTO joueur VALUES (?, '?', '?', '?', ?);")) {
			pstmt2.setLong(1, joueur.getId());
			pstmt2.setString(2, joueur.getNom());
			pstmt2.setString(3, joueur.getPrenom());
			pstmt2.setString(4, joueur.getAvatar().getDescription());
			pstmt2.setInt(5, joueur.getNbPartiesGagnees());
			pstmt2.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
		}
		return joueur;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param joueur Le joueur à mettre à jour dans la base de données
	 * @return Le joueur
	 */
	@Override
	public JoueurSQL maj(JoueurSQL joueur) {
		try (PreparedStatement pstmt = this.connexion.getConnexion().prepareStatement(
				"UPDATE joueur SET nom = '?', prenom = '?', avatar = '?', "
				+ "nb_parties_gagnees = ? WHERE id = ?;")) {
			pstmt.setString(1, joueur.getNom());
			pstmt.setString(2, joueur.getPrenom());
			pstmt.setString(3, joueur.getAvatar().getDescription());
			pstmt.setInt(4, joueur.getNbPartiesGagnees());
			pstmt.setLong(5, joueur.getId());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
		}
		return joueur;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param joueur Le joueur à supprimer de la base de données
	 */
	@Override
	public void supprimer(JoueurSQL joueur) {
		try (PreparedStatement pstmt = this.connexion.getConnexion().prepareStatement(
				"DELETE FROM joueur WHERE id = ?;")) {
			pstmt.setLong(1, joueur.getId());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
		}
	}

}
