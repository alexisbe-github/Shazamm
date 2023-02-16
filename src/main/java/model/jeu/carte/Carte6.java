package main.java.model.jeu.carte;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.Partie;
import main.java.model.jeu.carte.effets.IManaStrategy;

public class Carte6 extends Carte implements IManaStrategy{

	protected Carte6(String path, String n, Partie p, Joueur j, int i) {
		super(path, n, p, j, i);
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
