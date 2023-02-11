package main.java.model.jeu;

import main.java.model.jeu.carte.collection.PaquetCarte;

public class Joueur {

	private final String COULEUR;
	private final int MANA_MAXIMUM = 50;
	private int nbCasesDuMur, manaActuel;
	// ajouter attributs paquet, main, cartes jouees, defausse

	public Joueur(String couleur) {
		this.COULEUR = couleur;
		nbCasesDuMur = 3;
		manaActuel = 0;
	}

	public void depenserMana(int mise) {
		manaActuel -= mise;
		if (manaActuel < 0)
			manaActuel = 0;
	}

	public void ajouterMana(int aj) {
		manaActuel += aj;
		if (manaActuel > MANA_MAXIMUM)
			manaActuel = MANA_MAXIMUM;
	}

}
