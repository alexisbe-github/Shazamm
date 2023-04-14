package main.java.model.jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.java.model.jeu.carte.Carte;
import main.java.model.jeu.carte.factory.CarteFactory;
import main.java.model.jeu.partie.Partie;

public class Joueur {

	private final ECouleurJoueur COULEUR;
	private final String NOM, PRENOM, AVATAR;
	private final int MANA_MAXIMUM = 50;
	private final int NOMBRE_CARTE = 14;
	private int manaActuel;
	private List<Carte> paquet, mainDuJoueur;

	public Joueur(ECouleurJoueur couleur, String nom, String prenom, String avatar) {
		this.COULEUR = couleur;
		this.NOM = nom;
		this.PRENOM = prenom;
		this.AVATAR = avatar;
		manaActuel = 0;
		paquet = new ArrayList<>();
		mainDuJoueur = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Joueur " + COULEUR + ". nom : " + NOM + " " + PRENOM + ", mana : " + manaActuel + "\nmain :\n"
				+ this.mainString();
	}

	public List<Carte> getMainDuJoueur() {
		return mainDuJoueur;
	}

	public void retirerCarteDeLaMain(Carte c) {
		this.mainDuJoueur.remove(c);
	}

	public int getManaActuel() {
		return manaActuel;
	}

	public String mainString() {
		String res = "";
		for (Carte c : mainDuJoueur) {
			res += c + "\n";
		}
		return res;
	}

	public String getNom() {
		return this.NOM + " " + this.PRENOM;
	}

	public ECouleurJoueur getCouleur() {
		return this.COULEUR;
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
	 * @param int montant d'ajout de mana
	 */
	public void ajouterMana(int ajout) {
		manaActuel += ajout;

	}

	/**
	 * Vérifie si le mana actuel a bien une valeur correcte
	 */
	public void verifierMana() {
		if (manaActuel > MANA_MAXIMUM)
			manaActuel = MANA_MAXIMUM;
		if (manaActuel < 0)
			manaActuel = 0;
	}

	/**
	 * Remet la réserve de mana au maximum pour le joueur
	 */
	public void remplirReserveDeMana() {
		manaActuel = MANA_MAXIMUM;
	}

	public void initialiserPaquet(Partie partie) {
		// Instance par la factory puis ajout des cartes dans le paquet
		for (int i = 0; i < this.NOMBRE_CARTE; i++) {
			Carte c = CarteFactory.creerCarte(i + 1, partie, this);
			this.paquet.add(c);
		}

		// Puis on mélange le paquet
		melangerPaquet();
	}

	public void melangerPaquet() {
		Collections.shuffle(paquet);
	}

	/**
	 * Pioche nbCartes dans la main du joueur
	 * 
	 * @param nbCartes, int
	 */
	public void piocherCartes(int nbCartes) {
		// On prends les nbCartes premières cartes et on les ajoutes on à la main et on
		// les retire du paquet
		for (int i = 0; i < nbCartes && i < this.paquet.size(); i++) {
			Carte cartePiochee = this.paquet.get(i);
			this.mainDuJoueur.add(cartePiochee);
			this.paquet.remove(cartePiochee);
		}
	}

	/**
	 * Defausse une carte en la supprimant dans la main
	 * 
	 * @param carteJouee Carte
	 */
	public void defausser(Carte carteJouee) {
		this.mainDuJoueur.remove(carteJouee);
	}

}
