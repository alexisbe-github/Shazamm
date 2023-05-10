package main.java.model.jeu.ia.arbre;

import java.util.ArrayList;
import java.util.List;

import main.java.model.jeu.ia.IAEtatJeu;
import main.java.model.jeu.partie.Partie;

public class Noeud {

	private int score;
	private List<Noeud> fils;
	private Partie partie;
	private int profondeur;
	
	public Noeud(Partie p,IAEtatJeu ia,int profondeur) {
		this.partie = p;
		this.score = ia.evaluationTour(p);
		this.fils = new ArrayList<>();
		this.profondeur = profondeur;
	}
	
	public void ajouterFils(Noeud fils) {
		this.fils.add(fils);
	}
	
	public List<Noeud> getFils() {
		return this.fils;
	}
	
	public void getFils(int n) {
		this.fils.get(n);
	}
	
	public void supprimerFils(int n) {
		this.fils.remove(n);
	}
	
	public Partie getPartie() {
		return this.partie;
	}
	
	public int getProfondeur() {
		return this.profondeur;
	}
	
	public boolean estFeuille() {
		return this.fils.size() == 0;
	}
}
