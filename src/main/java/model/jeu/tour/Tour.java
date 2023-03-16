package main.java.model.jeu.tour;

import java.util.ArrayList;
import java.util.List;

import main.java.model.jeu.ECouleurJoueur;
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
	
	public void addAttaqueJoueur(int atq, ECouleurJoueur joueur) {
		if(joueur == ECouleurJoueur.ROUGE) {
			this.attaqueJoueurRouge+=atq;
		}else {
			this.attaqueJoueurVert+=atq;
		}
	}
	
	public int getAttaqueJoueur(ECouleurJoueur joueur) {
		if(joueur == ECouleurJoueur.ROUGE) {
			return this.attaqueJoueurRouge;
		}else {
			return this.attaqueJoueurVert;
		}
	}
	
	public void setDeplacementMur(int dpMur) {
		this.deplacementMur = dpMur;
	}
	
	public int getDeplacementMur() {
		return this.deplacementMur;
	}
	
}
