package main.java.model.jeu.carte;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.Partie;
import main.java.model.jeu.carte.effets.IManaStrategy;

public class Carte14 extends Carte implements IManaStrategy{

	protected Carte14(String n, Partie p, Joueur j, int i) {
		super(n, p, j, i);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void jouer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int calculAjoutMana() {
		// TODO Auto-generated method stub
		return 0;
	}

}
