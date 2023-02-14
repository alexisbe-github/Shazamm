package main.java.model.jeu.carte;

import main.java.model.jeu.Partie;
import main.java.model.jeu.carte.effets.IMurStrategy;

public class Carte11 extends Carte implements IMurStrategy{

	protected Carte11(String path, String name, Partie p, String color, int index) {
		super(path, name, p, color, index);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int calculDpMur() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void jouer() {
		// TODO Auto-generated method stub
		
	}

}
