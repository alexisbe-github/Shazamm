package main.java.model.jeu.partie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.carte.Carte;

public class Tour {

	private int miseJoueurRouge, miseJoueurVert;
	private int attaqueJoueurRouge, attaqueJoueurVert;
	private int deplacementMur;
	private boolean mutisme, finDeManche;
	private boolean harpagonRouge, harpagonVert;
	private List<Carte> cartesJoueesVert, cartesJoueesRouge;
	private List<Carte> cartesJouees;

	public Tour(boolean mutisme) {
		this.cartesJoueesRouge = new ArrayList<>();
		this.cartesJoueesVert = new ArrayList<>();
		this.cartesJouees = new ArrayList<>();
		this.mutisme = mutisme;
		this.finDeManche = false;
		this.harpagonRouge = false;
		this.harpagonVert = false;
	}

	public void activerMutisme(boolean enable) {
		this.mutisme = enable;
	}

	public boolean getMutisme() {
		return this.mutisme;
	}

	public void activerFinDeManche() {
		this.finDeManche = true;
	}

	/**
	 * Ajoute la carte à jouer dans la liste des cartes à jouer du joueur
	 * 
	 * @param carteAJouer
	 * @param joueur      Joueur qui joue la carte
	 */
	public void jouerCarte(Carte carteAJouer, Joueur joueur) {
		if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE)) {
			this.cartesJoueesRouge.add(carteAJouer);
			joueur.retirerCarteDeLaMain(carteAJouer);
		} else {
			this.cartesJoueesVert.add(carteAJouer);
			joueur.retirerCarteDeLaMain(carteAJouer);
		}
	}

	/**
	 * Joue le tour en fonction des mises
	 * 
	 * @param miseRouge int
	 * @param miseVert  int
	 * @return int le déplacement du mur à la fin du tour
	 */
	public int jouerTour(Joueur joueurRouge, Joueur joueurVert, int miseRouge, int miseVert) {
		// Initialisation des variables pour le tour
		this.harpagonRouge = false;
		this.harpagonVert = false;
		this.miseJoueurRouge = miseRouge;
		this.miseJoueurVert = miseVert;
		this.attaqueJoueurRouge = miseRouge;
		this.attaqueJoueurVert = miseVert;
		this.calculDeplacementMur();

		// Si mutisme n'est pas activé pour la manche alors on peut jouer les cartes
		if (!this.mutisme)
			this.jouerTourDesCartes();

		// On defausse toutes les cartes jouées
		for (Carte cRouge : this.cartesJoueesRouge) {
			cRouge.defausser();
		}
		for (Carte cVert : this.cartesJoueesVert) {
			cVert.defausser();
		}

		System.out.println("Puissance attaque rouge:" + this.attaqueJoueurRouge + "     " + "Puissance attaque verte:"
				+ this.attaqueJoueurVert + "       Déplacement du mur:" + this.deplacementMur);

		if (!this.harpagonRouge) {
			joueurRouge.depenserMana(this.miseJoueurRouge);
			joueurRouge.verifierMana();
		}

		if (!this.harpagonVert) {
			joueurVert.depenserMana(this.miseJoueurVert);
			joueurVert.verifierMana();
		}

		if (this.finDeManche)
			this.deplacementMur = 0;

		return this.deplacementMur;
	}

	private void calculDeplacementMur() {
		if (this.attaqueJoueurRouge == this.attaqueJoueurVert)
			this.deplacementMur = 0;
		else
			this.deplacementMur = (this.attaqueJoueurRouge - this.attaqueJoueurVert)
					/ Math.abs(this.attaqueJoueurRouge - this.attaqueJoueurVert);
	}

	/**
	 * Joue les cartes en jeu en fonction de leur numéro de carte
	 */
	private void jouerTourDesCartes() {
		cartesJouees.addAll(this.cartesJoueesRouge);
		cartesJouees.addAll(this.cartesJoueesVert);
		this.trierCartesJouees();
		
		// Pour chaque carte jouée on regarde si la suivante est le même numéro de carte
		// et on active l'effet
		for (int i = 0; i < cartesJouees.size() && !this.finDeManche && !this.mutisme; i++) {
			Carte carteCourante = cartesJouees.get(i);
			boolean carteJoueeDeuxFois = false;

			if (i < cartesJouees.size() - 1) {
				Carte carteSuivante = cartesJouees.get(i + 1);
				int numCarteSuivante = carteSuivante.getNumeroCarte();
				if (numCarteSuivante == carteCourante.getNumeroCarte())
					carteJoueeDeuxFois = true;
			}
			if (i > 0) {
				Carte cartePrecedente = cartesJouees.get(i - 1);
				int numCartePrecedente = cartePrecedente.getNumeroCarte();
				if (numCartePrecedente == carteCourante.getNumeroCarte())
					carteJoueeDeuxFois = true;
			}

			// si la carte suivante ne concerne pas les deplacements de mur on calcule le
			// déplacement
			if (carteCourante.getNumeroCarte() < 9 && carteCourante.getNumeroCarte() != 1)
				this.calculDeplacementMur();

			// On verifie qu'une carte n'est pas jouée deux fois, sinon les deux cartes
			// s'annulent
			System.out.println(carteCourante);
			if (!carteJoueeDeuxFois) {
				carteCourante.lancerEffet(this);
			}

		}
		cartesJouees.clear();
	}

	private void trierCartesJouees() {
		// Comparateur pour trier les cartes en fonction de leur numéro
		Comparator<Carte> c = new Comparator<Carte>() {
			@Override
			public int compare(Carte o1, Carte o2) {
				return o1.getNumeroCarte() - o2.getNumeroCarte();
			}
		};

		Collections.sort(cartesJouees, c); // Tri des cartes jouées
	}

	/**
	 * Effet carte 2
	 * 
	 * @param carte
	 */
	public void activerClone(Carte carte) {
		this.cartesJouees.add(carte);
		this.trierCartesJouees();
	}

	/**
	 * Effet Carte 3 En fonction de la couleur du joueur, on donne les cartes au
	 * joueur qui a activé Larcin
	 * 
	 * @param joueur Joueur qui caste le sort Larcin
	 */
	public void activerLarcin(Joueur joueur) {
		Scanner sc = new Scanner(System.in);
		if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE)) {
			for (Carte c : this.cartesJoueesVert) {
				System.out.println(c + "\n[" + joueur.getCouleur()
						+ "]Tapez oui si vous voulez utiliser cette carte, n'importe quelle touche si vous voulez la défausser.");
				String res = sc.nextLine();
				if (res.equals("oui")) {
					c.changerDetenteurCarte(joueur);
				} else {
					c.defausser();
				}
			}
		} else {
			for (Carte c : this.cartesJoueesRouge) {
				System.out.println(c + "\n[" + joueur.getCouleur()
						+ "]Tapez oui si vous voulez utiliser cette carte, n'importe quelle touche si vous voulez la défausser.");
				String res = sc.nextLine();
				if (res.equals("oui")) {
					c.changerDetenteurCarte(joueur);
				} else {
					c.defausser();
				}
			}
		}
	}

	/**
	 * Effet carte 12
	 * 
	 * @param joueur
	 */
	public void activerHarpagon(Joueur joueur) {
		if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE))
			this.harpagonRouge = true;
		else
			this.harpagonVert = true;
	}

	/**
	 * Double le déplacement du mur dans le sens dans lequel il doit avancer
	 */
	public void doubleDeplacementMur() {
		deplacementMur *= 2;
	}

	/**
	 * Inverse le déplacement du mur dans le sens opposé où il devait avancer
	 */
	public void inverserDeplacementMur() {
		deplacementMur *= -1;
	}

	/**
	 * Change de mana la mise de mana du tour
	 * 
	 * @param caster Joueur qui a casté le sort de mise
	 * @param mana   int montant en mana à ajouter/enlever
	 */
	public void changerMise(Joueur caster, int mana) {
		if (caster.getCouleur().equals(ECouleurJoueur.ROUGE)) {
			this.miseJoueurRouge += mana;
			this.attaqueJoueurRouge += mana;
		} else {
			this.miseJoueurVert += mana;
			this.attaqueJoueurVert += mana;
		}
	}

	/**
	 * Ajoute atq au joueur
	 * 
	 * @param atq    int
	 * @param joueur ECouleurJoueur
	 */
	public void addAttaqueJoueur(ECouleurJoueur joueur, int atq) {
		if (joueur == ECouleurJoueur.ROUGE)
			this.attaqueJoueurRouge += atq;
		else
			this.attaqueJoueurVert += atq;
	}

	public int getAttaqueJoueur(ECouleurJoueur joueur) {
		if (joueur == ECouleurJoueur.ROUGE)
			return this.attaqueJoueurRouge;
		else
			return this.attaqueJoueurVert;
	}

	public void setMiseJoueur(Joueur joueur, int mise) {
		if (joueur.getCouleur().equals(ECouleurJoueur.VERT))
			this.miseJoueurVert = mise;
		else
			this.miseJoueurRouge = mise;
	}

	public void setMiseJoueurVert(int mise) {
		this.miseJoueurVert = mise;
	}

	public int getMiseJoueurRouge() {
		return this.miseJoueurRouge;
	}

	public int getMiseJoueurVert() {
		return this.miseJoueurVert;
	}

	public int getDeplacementMur() {
		return this.deplacementMur;
	}

	public void setDeplacementMur(int d) {
		deplacementMur = d;
	}

	public int getAttaqueJoueurRouge() {
		return attaqueJoueurRouge;
	}

	public int getAttaqueJoueurVert() {
		return attaqueJoueurVert;
	}

	public List<Carte> getCartesJoueesVert() {
		return cartesJoueesVert;
	}

	public List<Carte> getCartesJoueesRouge() {
		return cartesJoueesRouge;
	}

}
