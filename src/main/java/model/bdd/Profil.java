package main.java.model.bdd;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.ImageIcon;

import main.java.model.bdd.dao.DAOJoueur;
import main.java.model.bdd.dao.beans.JoueurSQL;

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

}