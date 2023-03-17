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
	private List<Carte> paquet, mainDuJoueur, defausse;

	public Joueur(ECouleurJoueur couleur, String nom, String prenom, String avatar) {
		this.COULEUR = couleur;
		this.NOM = nom;
		this.PRENOM = prenom;
		this.AVATAR = avatar;
		manaActuel = 0;
		paquet = new ArrayList<>();
		mainDuJoueur = new ArrayList<>();
		defausse = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Joueur [COULEUR=" + COULEUR + ", NOM=" + NOM + ", PRENOM=" + PRENOM + ", AVATAR=" + AVATAR
				+ ", manaActuel=" + manaActuel + ", paquet=" + paquet + ", mainDuJoueur=" + mainDuJoueur + ", defausse="
				+ defausse + "]";
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

	public void initialiserPaquet(Partie p) {
		// Instance par la factory puis ajout des cartes dans le paquet
		for (int i = 0; i < this.NOMBRE_CARTE; i++) {
			Carte c = CarteFactory.creerCarte(i + 1, p, this);
			this.paquet.add(c);
		}

		// Mélange du paquet de cartes
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
	 * Ajoute dans la défausse la carte jouée en paramètre
	 * 
	 * @param carteJouee Carte
	 */
	public void defausser(Carte carteJouee) {
		this.defausse.add(carteJouee);
		this.mainDuJoueur.remove(carteJouee);
	}

}
