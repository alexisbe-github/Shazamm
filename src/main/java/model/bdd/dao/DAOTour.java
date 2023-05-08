package main.java.model.bdd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Locale;

import main.java.model.bdd.dao.beans.TourSQL;

/**
 * La couche <code>DAO</code> secondaire qui fait le lien entre la base de
 * données et le <code>JavaBean</code> {@link main.java.model.bdd.dao.beans.TourSQL
 * TourSQL}, spécifiquement pour la table <code><i>tour</i></code> de la base de
 * données, qui enregistre les informations et les statistiques relatives aux
 * tours.
 * 
 * 
 * @see TourSQL <code>TourSQL</code><a role="link" aria-disabled="true"> - Le
 *      <code>JavaBean</code> géré par la classe</a>
 * @see DAO <code>DAO</code><a role="link" aria-disabled="true"> - La couche
 *      abstraite principale dont hérite cette classe</a>
 *
 */
public class DAOTour extends DAO<TourSQL> {

	/**
	 * Table <code><i>tour</i></code>, contenant différentes informations et
	 * statistiques sur les tours.
	 */
	private final String TOUR = "tour";
	/**
	 * Colonne <code><i>id</i></code>, correspondant à l'identifiant du tour.
	 */
	private final String ID = "id";
	/**
	 * Colonne <code><i>id_manche</i></code>, correspondant à l'identifiant de la
	 * manche.
	 */
	private final String ID_MANCHE = "id_manche";
	/**
	 * Colonne <code><i>position_mur_flammes</i></code>, correspondant à la position
	 * du mur de flammes.
	 */
	private final String POSITION_MUR_FLAMMES = "position_mur_flammes";
	/**
	 * Colonne <code><i>position_joueur1</i></code>, correspondant à la position du
	 * joueur 1.
	 */
	private final String POSITION_JOUEUR1 = "position_joueur1";
	/**
	 * Colonne <code><i>position_joueur2</i></code>, correspondant à la position du
	 * joueur 2.
	 */
	private final String POSITION_JOUEUR2 = "position_joueur2";
	/**
	 * Colonne <code><i>mise_joueur1</i></code>, correspondant à la mise du joueur
	 * 1.
	 */
	private final String MISE_JOUEUR1 = "mise_joueur1";
	/**
	 * Colonne <code><i>mise_joueur2</i></code>, correspondant à la mise du joueur
	 * 2.
	 */
	private final String MISE_JOUEUR2 = "mise_joueur2";
	/**
	 * Colonne <code><i>puissance_joueur1</i></code>, correspondant à la puissance
	 * du joueur 1.
	 */
	private final String PUISSANCE_JOUEUR1 = "puissance_joueur1";
	/**
	 * Colonne <code><i>puissance_joueur2</i></code>, correspondant à la puissance
	 * du joueur 2.
	 */
	private final String PUISSANCE_JOUEUR2 = "puissance_joueur2";
	/**
	 * Colonne <code><i>numero_tour</i></code>, correspondant au numéro du tour.
	 */
	private final String NUMERO_TOUR = "numero_tour";
	/**
	 * Colonne <code><i>date</i></code>, correspondant à la date du tour.
	 */
	private final String DATE = "date";

	/**
	 * {@inheritDoc}
	 * 
	 * @param id L'identifiant du tour
	 * @return Le tour correspondant à l'identifiant passé en paramètre
	 * 
	 */
	@Override
	public TourSQL trouver(long id) {
		TourSQL tour = new TourSQL();
		try (Connection connexion = this.connexion.getConnexion();
				PreparedStatement pstmt = connexion.prepareStatement("SELECT * FROM " + TOUR + " WHERE " + ID + " = ?;",
						ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			pstmt.setLong(1, id);
			pstmt.execute();
			try (ResultSet rs = pstmt.getResultSet()) {
				if (rs.first()) {
					tour.setId(id);
					tour.setIdManche(rs.getLong(ID_MANCHE));
					tour.setPositionMurFlammes(rs.getInt(POSITION_MUR_FLAMMES));
					tour.setPositionJoueur1(rs.getInt(POSITION_JOUEUR1));
					tour.setPositionJoueur2(rs.getInt(POSITION_JOUEUR2));
					tour.setMiseJoueur1(rs.getInt(MISE_JOUEUR1));
					tour.setMiseJoueur2(rs.getInt(MISE_JOUEUR2));
					tour.setPuissanceJoueur1(rs.getInt(PUISSANCE_JOUEUR1));
					tour.setPuissanceJoueur2(rs.getInt(PUISSANCE_JOUEUR2));
					tour.setNumeroTour(rs.getInt(NUMERO_TOUR));
					tour.setDate(rs.getTimestamp(DATE));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tour;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param tour Le tour à créer dans la base de données
	 * @return Le tour
	 */
	@Override
	public TourSQL creer(TourSQL tour) {
		try (Connection connexion = this.connexion.getConnexion()) {
			try (PreparedStatement pstmt1 = connexion.prepareStatement(
					"SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES " + "WHERE table_name = '" + TOUR + "'")) {
				pstmt1.execute();

				try (ResultSet rsid = pstmt1.getResultSet()) {
					if (rsid.next()) {
						long id = rsid.getLong(1);
						tour.setId(id);
					}
				}
				pstmt1.close();

			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			try (PreparedStatement pstmt2 = connexion
					.prepareStatement("INSERT INTO " + TOUR + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);")) {
				pstmt2.setLong(1, tour.getId());
				pstmt2.setLong(2, tour.getIdManche());
				pstmt2.setInt(3, tour.getPositionMurFlammes());
				pstmt2.setInt(4, tour.getPositionJoueur1());
				pstmt2.setInt(5, tour.getPositionJoueur2());
				pstmt2.setInt(6, tour.getMiseJoueur1());
				pstmt2.setInt(7, tour.getMiseJoueur2());
				pstmt2.setInt(8, tour.getPuissanceJoueur1());
				pstmt2.setInt(9, tour.getPuissanceJoueur2());
				pstmt2.setInt(10, tour.getNumeroTour());
				pstmt2.setTimestamp(11, tour.getDate(), Calendar.getInstance(Locale.FRANCE));
				pstmt2.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {

		}
		return tour;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param tour Le tour à mettre à jour dans la base de données
	 * @return Le tour
	 */
	@Override
	public TourSQL maj(TourSQL tour) {
		try (Connection connexion = this.connexion.getConnexion();
				PreparedStatement pstmt = connexion
						.prepareStatement("UPDATE " + TOUR + " SET " + ID_MANCHE + " = ?, " + POSITION_MUR_FLAMMES
								+ " = ?, " + POSITION_JOUEUR1 + " = ?, " + POSITION_JOUEUR2 + " = ?, " + MISE_JOUEUR1
								+ " = ?, " + MISE_JOUEUR2 + " = ?, " + PUISSANCE_JOUEUR1 + " = ?, " + PUISSANCE_JOUEUR2
								+ " = ?, " + NUMERO_TOUR + " = ?, " + DATE + " = ? WHERE " + ID + " = ?");) {
			pstmt.setLong(1, tour.getIdManche());
			pstmt.setInt(2, tour.getPositionMurFlammes());
			pstmt.setInt(3, tour.getPositionJoueur1());
			pstmt.setInt(4, tour.getPositionJoueur2());
			pstmt.setInt(5, tour.getMiseJoueur1());
			pstmt.setInt(6, tour.getMiseJoueur2());
			pstmt.setInt(7, tour.getPuissanceJoueur1());
			pstmt.setInt(8, tour.getPuissanceJoueur2());
			pstmt.setInt(9, tour.getNumeroTour());
			pstmt.setTimestamp(10, tour.getDate(), Calendar.getInstance(Locale.FRANCE));
			pstmt.setLong(11, tour.getId());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tour;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param tour Le tour à supprimer de la base de données
	 */
	@Override
	public void supprimer(TourSQL tour) {
		try (Connection connexion = this.connexion.getConnexion()) {
			try (PreparedStatement pstmt = connexion
					.prepareStatement("DELETE FROM " + TOUR + " WHERE " + ID + " = ?;")) {
				pstmt.setLong(1, tour.getId());
				pstmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			
		}
	}

}
