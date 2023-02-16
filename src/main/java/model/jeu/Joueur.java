package main.java.model.jeu;

import java.util.List;

import main.java.model.jeu.carte.Carte;

public class Joueur {
	
	private final ECouleurJoueur COULEUR;
	private final String NOM, PRENOM, AVATAR;
	private final int MANA_MAXIMUM = 50;
	private int manaActuel;
	private List<Carte> paquet,mainDuJoueur,cartesJouees,defausse;

	public Joueur(ECouleurJoueur couleur, String nom, String prenom, String avatar) {
		this.COULEUR = couleur;
		this.NOM = nom;
		this.PRENOM = prenom;
		this.AVATAR = avatar;
		manaActuel = 0;
	}

	/**
	 * 
	 * @param mise de mana
	 */
	public void depenserMana(int mise) {
		manaActuel -= mise;
		if (manaActuel < 0)
			manaActuel = 0;
	}

	/**
	 * 
	 * @param ajout de mana
	 */
	public void ajouterMana(int ajout) {
		manaActuel += ajout;
		if (manaActuel > MANA_MAXIMUM)
			manaActuel = MANA_MAXIMUM;
	}
	
	/**
	 * Remet la réserve de mana au maximum pour le joueur
	 */
	public void remplirReserveDeMana() {
		manaActuel = MANA_MAXIMUM;
	}
	

}
