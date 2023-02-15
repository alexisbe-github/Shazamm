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
		try {
			PreparedStatement pstmt = this.connexion.getConnexion()
					.prepareStatement("SELECT * FROM joueur WHERE id = ?;");
			pstmt.setLong(1, id);
			pstmt.execute();
			ResultSet rs = pstmt.getResultSet();
			if (rs.first()) {
				joueur.setId(id);
				joueur.setNom(rs.getString("nom"));
				joueur.setPrenom(rs.getString("prenom"));
				joueur.setAvatar(new ImageIcon(rs.getString("avatar")));
				joueur.setNbPartiesGagnees(rs.getInt("nb_parties_gagnees"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return joueur;
	}

	@Override
	public JoueurSQL creer(JoueurSQL obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JoueurSQL maj(JoueurSQL obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void supprimer(JoueurSQL obj) {
		// TODO Auto-generated method stub

	}

}
