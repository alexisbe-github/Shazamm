package main.java.model.jeu.tour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.carte.Carte;

public class Tour {

	private int miseJoueurRouge, miseJoueurVert;
	private int attaqueJoueurRouge, attaqueJoueurVert;
	private int deplacementMur = 0;
	private List<Carte> cartesJoueesVert, cartesJoueesRouge;

	public Tour() {
		this.cartesJoueesRouge = new ArrayList<>();
		this.cartesJoueesVert = new ArrayList<>();
	}

	public void jouerTour(int miseRouge, int miseVert) {
		// Initialisation des variables pour le tour
		this.attaqueJoueurRouge = 0;
		this.attaqueJoueurVert = 0;
		this.miseJoueurRouge = miseRouge;
		this.miseJoueurVert = miseVert;
		this.jouerTourDesCartes();
	}

	private void jouerTourDesCartes() {
		// Comparateur pour trier les cartes en fonction de leur numéro
		Comparator<Carte> c = new Comparator<Carte>() {
			@Override
			public int compare(Carte o1, Carte o2) {
				return o1.getNumeroCarte() - o2.getNumeroCarte();
			}
		};
		ArrayList<Carte> cartesJouees = new ArrayList<>();
		cartesJouees.addAll(this.cartesJoueesRouge);
		cartesJouees.addAll(this.cartesJoueesVert);

		// Tri des cartes jouées
		Collections.sort(cartesJouees, c);
		for (int i = 0; i < cartesJouees.size(); i++) {
			Carte carteCourante = cartesJouees.get(i);
			if (i < cartesJouees.size() - 1) {
				Carte carteSuivante = cartesJouees.get(i - 1);
				if (carteSuivante.getNumeroCarte() != carteCourante.getNumeroCarte())
					carteCourante.lancerEffet(carteCourante.getJoueur(), carteCourante.getAdversaire());
			} else {
				carteCourante.lancerEffet(carteCourante.getJoueur(), carteCourante.getAdversaire());
			}
		}
	}

	public void addAttaqueJoueur(int atq, ECouleurJoueur joueur) {
		if (joueur == ECouleurJoueur.ROUGE) {
			this.attaqueJoueurRouge += atq;
		} else {
			this.attaqueJoueurVert += atq;
		}
	}

	public int getAttaqueJoueur(ECouleurJoueur joueur) {
		if (joueur == ECouleurJoueur.ROUGE) {
			return this.attaqueJoueurRouge;
		} else {
			return this.attaqueJoueurVert;
		}
	}

	public void setMiseJoueurRouge(int mise) {
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

	public void doubleDeplacementMur() {
		deplacementMur *= 2;
	}

	public void inverserDeplacementMur() {
		deplacementMur *= -1;
	}

	public void setDeplacementMur(int d) {
		deplacementMur = d;
	}

}
