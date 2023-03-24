package main.java.model.jeu.carte;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;

public class Carte13 extends Carte {

	private final String NOM_CARTE = "Boost réserve";
	private final String TEXTE_CARTE = "Ma réserve de Mana s’augmente de 13 points. Après que j’ai payé"
			+ " ce que je dois.";
	private final int NUMERO_CARTE = 13;

	public Carte13(Partie p, Joueur j) {
		super.partie = p;
		super.joueur = j;
		super.numeroCarte = NUMERO_CARTE;
		super.nom = NOM_CARTE;
		super.description = TEXTE_CARTE;
	}

	@Override
	public void lancerEffet() {
		joueur.ajouterMana(13); //lien avec mon com de la question 12, bien gérer l'ordre de paiement/gain de mana
	}

}
