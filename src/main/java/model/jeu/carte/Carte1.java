package main.java.model.jeu.carte;

import main.java.model.jeu.Partie;
import main.java.model.jeu.carte.effets.IControleStrategy;

public class Carte1 extends Carte implements IControleStrategy{

	public Carte1(String path, String name, Partie p) {
		super(path,name,p);
	}
	
	@Override
	protected void jouer() {
		// TODO Auto-generated method stub
		
	}

}
