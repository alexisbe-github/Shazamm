package main.java.model.jeu.carte;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.Partie;

public class Carte1 extends Carte {

	private final String NOM_CARTE = "Mutisme";
	private final String TEXTE_CARTE = "Aucun sort n’a plus d’effet pour les deux joueurs, jusqu’à la fin de "
			+ "la manche. Les autres sorts éventuellement posés sont perdus.";
	private final int NUMERO_CARTE = 2;

	public Carte1(Partie p, Joueur j) {
		super.partie = p;
		super.joueur = j;
		super.numeroCarte = NUMERO_CARTE;
		super.nom = NOM_CARTE;
		super.description = TEXTE_CARTE;
	}

	@Override
	public void lancerEffet(Joueur caster, Joueur adversaire) {
		this.partie.getMancheCourante().enableMutisme(true);
	}

}
