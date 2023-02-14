package main.java.model.dao.beans;

import java.awt.Image;
import java.io.Serializable;

/**
 * <code>{@link <a href=
 * "https://fr.wikipedia.org/wiki/JavaBeans">Java Bean</a>}</code> correspondant
 * à un joueur, permettant de faire le lien à travers la couche <code>DAO</code>
 * entre la base de données et le modèle.
 */
public class JoueurDAO implements Serializable {

	private static final long serialVersionUID = 3448987482155819219L;

	private String nom, prenom;
	private Image avatar;
	private int nbPartiesGagnees;

	/**
	 * Constructeur vide
	 */
	public JoueurDAO() {

	}

	/**
	 * 
	 * @return Le nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Met à jour le nom.
	 * 
	 * @param nom Le nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * 
	 * @return Le prénom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * Met à jour le prénom.
	 * 
	 * @param prenom Le prénom
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * 
	 * @return L'avatar
	 */
	public Image getAvatar() {
		return avatar;
	}

	/**
	 * Met à jour l'avatar.
	 * 
	 * @param avatar L'avatar
	 */
	public void setAvatar(Image avatar) {
		this.avatar = avatar;
	}

	/**
	 * 
	 * @return Le nombre de parties gagnées
	 */
	public int getNbPartiesGagnees() {
		return nbPartiesGagnees;
	}

	/**
	 * Met à jour le nombre de parties gagnées.
	 * 
	 * @param nbPartiesGagnees Le nombre de parties gagnées
	 */
	public void setNbPartiesGagnees(int nbPartiesGagnees) {
		this.nbPartiesGagnees = nbPartiesGagnees;
	}

}
