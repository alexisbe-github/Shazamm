package main.java.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.model.dao.beans.CarteSQL;

/**
 * La couche <code>DAO</code> secondaire qui fait le lien entre la base de
 * données et le <code>JavaBean</code>
 * {@link main.java.model.dao.beans.CarteSQL CarteSQL}, spécifiquement pour la
 * table <code><i>carte</i></code> de la base de données, qui enregistre les
 * informations relatives aux cartes du jeu.
 * 
 * 
 * @see CarteSQL <code>CarteSQL</code><a role="link" aria-disabled="true"> -
 *      Le <code>JavaBean</code> géré par la classe</a>
 * @see DAO <code>DAO</code><a role="link" aria-disabled="true"> - La couche
 *      abstraite principale dont hérite cette classe</a>
 *
 */
public class DAOCarte extends DAO<CarteSQL> {

	/**
	 * Table <code><i>carte</i></code>, contenant différentes informations sur les cartes.
	 */
	private final String CARTE = "carte";
	/**
	 * Colonne <code><i>id</i></code>, correspondant à l'identifiant des cartes.
	 */
	private final String ID = "id";
	/**
	 * Colonne <code><i>id_tour</i></code>, correspondant à l'identifiant du tour.
	 */
	private final String ID_TOUR = "id_tour";
	/**
	 * Colonne <code><i>id_joueur</i></code>, correspondant à l'identifiant des joueurs.
	 */
	private final String ID_JOUEUR = "id_joueur";
	/**
	 * Colonne <code><i>numero_carte</i></code>, correspondant au numéro des cartes.
	 */
	private final String NUMERO_CARTE = "numero_carte";

	/**
	 * {@inheritDoc}
	 * 
	 * @param id L'identifiant de la carte
	 * @return La carte correspondant à l'identifiant passé en paramètre
	 * 
	 */
	@Override
	public CarteSQL trouver(long id) {
		CarteSQL carte = new CarteSQL();
		try (Connection connexion = this.connexion.getConnexion();
				PreparedStatement pstmt = connexion.prepareStatement(
						"SELECT * FROM " + CARTE + " WHERE " + ID + " = ?;", ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_READ_ONLY)) {
			pstmt.setLong(1, id);
			pstmt.execute();
			try (ResultSet rs = pstmt.getResultSet()) {
				if (rs.first()) {
					carte.setId(id);
					carte.setIdTour(rs.getLong(ID_TOUR));
					carte.setIdJoueur(rs.getLong(ID_JOUEUR));
					carte.setNumeroCarte(rs.getInt(NUMERO_CARTE));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.connexion.fermerConnexion();
		}
		return carte;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param carte La carte à créer dans la base de données
	 * @return La carte
	 */
	@Override
	public CarteSQL creer(CarteSQL carte) {
		try (Connection connexion = this.connexion.getConnexion();
				PreparedStatement pstmt1 = connexion
						.prepareStatement("SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES "
								+ "WHERE table_name = '" + CARTE + "'")) {
			pstmt1.execute();

			try (ResultSet rsid = pstmt1.getResultSet()) {
				if (rsid.next()) {
					long id = rsid.getLong(1);
					carte.setId(id);
				}
			}
			pstmt1.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		try (Connection connexion = this.connexion.getConnexion();
				PreparedStatement pstmt2 = connexion
						.prepareStatement("INSERT INTO " + CARTE + " VALUES (?, ?, ?, ?);")) {
			pstmt2.setLong(1, carte.getId());
			pstmt2.setLong(2, carte.getIdTour());
			pstmt2.setLong(3, carte.getIdJoueur());
			pstmt2.setInt(4, carte.getNumeroCarte());
			pstmt2.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.connexion.fermerConnexion();
		}
		return carte;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param carte La carte à mettre à jour dans la base de données
	 * @return La carte
	 */
	@Override
	public CarteSQL maj(CarteSQL carte) {
		try (Connection connexion = this.connexion.getConnexion();
				PreparedStatement pstmt = connexion.prepareStatement("UPDATE " + CARTE + " SET " + ID_TOUR + " = ?, "
						+ ID_JOUEUR + " = ?, " + NUMERO_CARTE + " = ? WHERE " + ID + " = ?")) {
			pstmt.setLong(1, carte.getIdTour());
			pstmt.setLong(2, carte.getIdJoueur());
			pstmt.setInt(3, carte.getNumeroCarte());
			pstmt.setLong(4, carte.getId());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.connexion.fermerConnexion();
		}
		return carte;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param carte La carte à supprimer de la base de données
	 */
	@Override
	public void supprimer(CarteSQL carte) {
		try (Connection connexion = this.connexion.getConnexion();
				PreparedStatement pstmt = connexion.prepareStatement("DELETE FROM " + CARTE + " WHERE " + ID + " = ?;")) {
			pstmt.setLong(1, carte.getId());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.connexion.fermerConnexion();
		}
	}

}
