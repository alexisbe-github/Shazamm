package main.java.model.jeu.carte;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;

public class Carte14 extends Carte {

	private final String NOM_CARTE = "Aspiration";
	private final String TEXTE_CARTE = "Ma réserve de Mana s’augmente du montant de la mise de l’adversaire.";
	private final int NUMERO_CARTE = 14;

	public Carte14(Partie p, Joueur j) {
		super.partie = p;
		super.joueur = j;
		super.numeroCarte = NUMERO_CARTE;
		super.nom = NOM_CARTE;
		super.description = TEXTE_CARTE;
	}

	@Override
	public void lancerEffet(Tour tour) {
		if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE))
			joueur.ajouterMana(tour.getMiseJoueurVert());
		else
			joueur.ajouterMana(tour.getMiseJoueurRouge());
	}

}
