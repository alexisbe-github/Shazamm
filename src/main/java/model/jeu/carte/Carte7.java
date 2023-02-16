package main.java.model.jeu.carte;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.Partie;
import main.java.model.jeu.carte.effets.IAttaqueStrategy;

public class Carte7 extends Carte implements IAttaqueStrategy{

	protected Carte7(String path, String n, Partie p, Joueur j, int i) {
		super(path, n, p, j, i);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int calculAjoutAttaque() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void jouer() {
		// TODO Auto-generated method stub
		
	}

}
