package main.java.model.jeu.carte;

import main.java.model.jeu.Partie;
import main.java.model.jeu.carte.effets.IMurStrategy;

public class Carte9 extends Carte implements IMurStrategy{

	protected Carte9(String path, String name, String color, Partie p, int index) {
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
