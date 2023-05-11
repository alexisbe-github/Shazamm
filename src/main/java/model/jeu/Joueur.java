package main.java.model.jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import main.java.model.bdd.Profil;
import main.java.model.jeu.carte.Carte;
import main.java.model.jeu.carte.factory.CarteFactory;
import main.java.model.jeu.partie.Partie;

public class Joueur implements Cloneable{

	private final ECouleurJoueur COULEUR;
	private Profil profil;
	public static final int MANA_MAXIMUM = 50;
	private final int NOMBRE_CARTE = 14;
	private int manaActuel;
	private List<Carte> paquet, mainDuJoueur;

	public Joueur(ECouleurJoueur couleur, Profil profil) {
		this.COULEUR = couleur;
		this.profil = profil;
		manaActuel = 0;
		paquet = new ArrayList<>();
		mainDuJoueur = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Joueur " + COULEUR + ". nom : " + profil.getNom() + " " + profil.getPrenom() + ", mana : " + manaActuel + "\nmain :\n"
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
		return this.profil.getNom() + " " + this.profil.getPrenom();
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
		// On prends les nbCartes premières cartes et on les ajoute à la main et on
		// les retire du paquet
		for (int i = 0; i < nbCartes && !this.paquet.isEmpty(); i++) {
			Carte cartePiochee = this.paquet.get(this.paquet.size() - 1);
			this.mainDuJoueur.add(cartePiochee);
			this.paquet.remove(cartePiochee);
		}
	}

	/**
	 * Defausse une carte en la supprimant de la main
	 * 
	 * @param carteJouee Carte
	 */
	public void defausser(Carte carteJouee) {
		this.mainDuJoueur.remove(carteJouee);
	}

	/**
	 * Retourne le chemin de l'image correspondant au Joueur
	 */
	public String getPath() {
		if (this.COULEUR.equals(ECouleurJoueur.ROUGE))
			return "src/main/resources/perso/rouge.png";

		return "src/main/resources/perso/vert.png";
	}

	public List<Carte> getPaquet() {
		return paquet;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Joueur joueurClone = (Joueur) super.clone();
		return joueurClone;
	}

	public void setPaquet(List<Carte> paquet) {
		this.paquet = paquet;
	}

	public void setMainDuJoueur(List<Carte> mainDuJoueur) {
		this.mainDuJoueur = mainDuJoueur;
	}
	
	public List<Integer> getCartesPossedees(){
		List<Integer> cartesPossedees = new ArrayList<>();
		List<Carte> cartes = new ArrayList<>();
		cartes.addAll(this.mainDuJoueur);
		cartes.addAll(this.paquet);
		for(Carte c:cartes) {
			cartesPossedees.add(c.getNumeroCarte());
		}
		return cartesPossedees;
	}
	
}
