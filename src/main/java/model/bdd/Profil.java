package main.java.model.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.ImageIcon;

import main.java.model.bdd.dao.Connexion;
import main.java.model.bdd.dao.DAOJoueur;
import main.java.model.bdd.dao.beans.JoueurSQL;
import main.java.utils.Utils;

/**
 * Profil de joueur
 */
public class Profil extends JoueurSQL {

	/**
	 * Empêche d'instancier le constructeur vide
	 */
	private Profil() {

	}

	/**
	 * Crée un profil et l'insère dans la base de données
	 * 
	 * @param nom    Le nom du joueur
	 * @param prenom Le prénom du joueur
	 * @param avatar L'avatar du joueur
	 */
	public Profil(String nom, String prenom, ImageIcon avatar) {
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setAvatar(avatar);
		this.setNbPartiesGagnees(0);

		DAOJoueur dao = new DAOJoueur();
		dao.creer(this);
	}
	
	/**
	 * Crée un profil à partir d'un joueur
	 * 
	 * @param joueur Le joueur
	 */
	public Profil(JoueurSQL joueur) {
		this.setId(joueur.getId());
		this.setNom(joueur.getNom());
		this.setPrenom(joueur.getPrenom());
		this.setAvatar(joueur.getAvatar());
		this.setNbPartiesGagnees(joueur.getNbPartiesGagnees());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format(Locale.FRANCE, "%s %s - %d victoire%s", this.getNom(), this.getPrenom(), this.getNbPartiesGagnees(), (this.getNbPartiesGagnees() <= 1 ? "" : "s"));
	}
	
	/**
	 * @return La liste des profils contenus dans la base de données
	 */
	public static List<Profil> getListeProfils() {
		List<Profil> liste = new ArrayList<>();
		List<Long> listeIdentifiants = Profil.getListeIdentifiantsJoueurs();
		DAOJoueur dao = new DAOJoueur();
		for (long id : listeIdentifiants) {
			Profil profil = new Profil(dao.trouver(id));
			liste.add(profil);
		}
		return liste;
	}

	/**
	 * @return La liste des identifiants des joueurs contenus dans la base de
	 *         données
	 */
	public static List<Long> getListeIdentifiantsJoueurs() {
		ArrayList<Long> liste = new ArrayList<>();
		String requete = "SELECT DISTINCT id FROM joueur";
		Connection con = Connexion.getInstance().getConnexion();
		try (PreparedStatement pstmt = con.prepareStatement(requete);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				long id = rs.getLong(1);
				liste.add(id);
			}
		} catch (SQLException e) {

		}
		return liste;
	}

}