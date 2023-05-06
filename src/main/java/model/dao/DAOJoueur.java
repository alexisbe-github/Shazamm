package main.java.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;

import main.java.model.dao.beans.JoueurSQL;

/**
 * La couche <code>DAO</code> secondaire qui fait le lien entre la base de
 * données et le <code>JavaBean</code>
 * {@link main.java.model.dao.beans.JoueurSQL JoueurSQL}, spécifiquement pour la
 * table <code><i>joueur</i></code> de la base de données, qui enregistre les
 * informations et les statistiques relatives aux joueurs.
 * 
 * 
 * @see JoueurSQL <code>JoueurSQL</code><a role="link" aria-disabled="true"> -
 *      Le <code>JavaBean</code> géré par la classe</a>
 * @see DAO <code>DAO</code><a role="link" aria-disabled="true"> - La couche
 *      abstraite principale dont hérite cette classe</a>
 *
 */
public class DAOJoueur extends DAO<JoueurSQL> {

	/**
	 * Table <code><i>joueur</i></code>, contenant différentes informations et
	 * statistiques sur les joueurs.
	 */
	private final String JOUEUR = "joueur";
	/**
	 * Colonne <code><i>id</i></code>, correspondant à l'identifiant des joueurs.
	 */
	private final String ID = "id";
	/**
	 * Colonne <code><i>nom</i></code>, correspondant au nom des joueurs.
	 */
	private final String NOM = "nom";
	/**
	 * Colonne <code><i>prenom</i></code>, correspondant au prénom des joueurs.
	 */
	private final String PRENOM = "prenom";
	/**
	 * Colonne <code><i>avatar</i></code>, correspondant au chemin vers l'image
	 * représentant l'avatar des joueurs.
	 */
	private final String AVATAR = "avatar";
	/**
	 * Colonne <code><i>nb_parties_gagnees</i></code>, correspondant au nombre de
	 * parties remportées par les joueurs.
	 */
	private final String NB_PARTIES_GAGNEES = "nb_parties_gagnees";

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
		try (Connection connexion = this.connexion.getConnexion();
				PreparedStatement pstmt = connexion.prepareStatement(
						"SELECT * FROM " + JOUEUR + " WHERE " + ID + " = ?;", ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {
			pstmt.setLong(1, id);
			pstmt.execute();
			try (ResultSet rs = pstmt.getResultSet()) {
				if (rs.first()) {
					joueur.setId(id);
					joueur.setNom(rs.getString(NOM));
					joueur.setPrenom(rs.getString(PRENOM));
					joueur.setAvatar(new ImageIcon(rs.getString(AVATAR)));
					joueur.setNbPartiesGagnees(rs.getInt(NB_PARTIES_GAGNEES));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.connexion.fermerConnexion();
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
		try (Connection connexion = this.connexion.getConnexion()) {
			try (PreparedStatement pstmt1 = connexion
					.prepareStatement("SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES " + "WHERE table_name = '"
							+ JOUEUR + "'")) {
				pstmt1.execute();

				try (ResultSet rsid = pstmt1.getResultSet()) {
					if (rsid.next()) {
						long id = rsid.getLong(1);
						joueur.setId(id);
					}
				}
				pstmt1.close();

			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			try (PreparedStatement pstmt2 = connexion
					.prepareStatement("INSERT INTO " + JOUEUR + " VALUES (?, ?, ?, ?, ?);")) {
				pstmt2.setLong(1, joueur.getId());
				pstmt2.setString(2, joueur.getNom());
				pstmt2.setString(3, joueur.getPrenom());
				pstmt2.setString(4, joueur.getAvatar().getDescription()); // Chemin de l'image
				pstmt2.setInt(5, joueur.getNbPartiesGagnees());
				pstmt2.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			
		} finally {
			this.connexion.fermerConnexion();
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
		try (Connection connexion = this.connexion.getConnexion();
				PreparedStatement pstmt = connexion.prepareStatement("UPDATE " + JOUEUR + " SET " + NOM + " = ?, "
						+ PRENOM + " = ?, " + AVATAR + " = ?, " + NB_PARTIES_GAGNEES + " = ? WHERE " + ID + " = ?")) {
			pstmt.setString(1, joueur.getNom());
			pstmt.setString(2, joueur.getPrenom());
			pstmt.setString(3, joueur.getAvatar().getDescription()); // Chemin de l'image
			pstmt.setInt(4, joueur.getNbPartiesGagnees());
			pstmt.setLong(5, joueur.getId());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.connexion.fermerConnexion();
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
		try (Connection connexion = this.connexion.getConnexion();
				PreparedStatement pstmt = connexion
						.prepareStatement("DELETE FROM " + JOUEUR + " WHERE " + ID + " = ?;")) {
			pstmt.setLong(1, joueur.getId());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.connexion.fermerConnexion();
		}
	}

}
