package main.java.model.jeu.carte;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.Partie;
import main.java.model.jeu.carte.effets.IMurStrategy;

public class Carte10 extends Carte implements IMurStrategy{

	protected Carte10(String path, String n, Partie p, Joueur j, int i) {
		super(path, n, p, j, i);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void jouer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int calculDpMur() {
		// TODO Auto-generated method stub
		return 0;
	}

}
