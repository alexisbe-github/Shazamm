package main.java.model.jeu.carte;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;

public class Carte6 extends Carte {

	private final String NOM_CARTE = "Recyclage";
	private final String TEXTE_CARTE = "Je peux rectifier ma mise, en ajoutant ou retranchant jusqu’à 5 points "
			+ "de mana.";
	private final int NUMERO_CARTE = 6;

	public Carte6(Partie p, Joueur j) {
		super.partie = p;
		super.joueur = j;
		super.numeroCarte = NUMERO_CARTE;
		super.nom = NOM_CARTE;
		super.description = TEXTE_CARTE;
	}

	@Override
	public void lancerEffet(Joueur caster) {
		
	}

}
