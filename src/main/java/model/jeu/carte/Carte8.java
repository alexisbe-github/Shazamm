package main.java.model.jeu.carte;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.Partie;
import main.java.model.jeu.carte.effets.IAttaqueStrategy;

public class Carte8 extends Carte implements IAttaqueStrategy{

	private final String NOM_CARTE = "Double dose";
	private final String TEXTE_CARTE = "La puissance de mon attaque est multipliée par deux.";
	private final int NUMERO_CARTE = 8;

	public Carte8(Partie p, Joueur j) {
		super.partie = p;
		super.joueur = j;
		super.numeroCarte = NUMERO_CARTE;
		super.nom = NOM_CARTE;
		super.description = TEXTE_CARTE;
	}

	@Override
	public void jouer() {
		
	}

	@Override
	public void ajouterAttaque() {
		// TODO Auto-generated method stub
	}

}
