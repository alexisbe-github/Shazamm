package main.java.model.jeu.carte;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.Partie;

public class Carte12 extends Carte {

	private final String NOM_CARTE = "Harpagon";
	private final String TEXTE_CARTE = "Si je perds ce tour (i.e. si le mur de feu avance effectivement vers"
			+ " moi), ma mise n’est pas retranchée de ma réserve de Mana";
	private final int NUMERO_CARTE = 12;

	public Carte12(Partie p, Joueur j) {
		super.partie = p;
		super.joueur = j;
		super.numeroCarte = NUMERO_CARTE;
		super.nom = NOM_CARTE;
		super.description = TEXTE_CARTE;
	}

	@Override
	public void jouer() {

	}


}
