package main.java.model.jeu.carte;

import main.java.model.jeu.Partie;
import main.java.model.jeu.carte.effets.IControleStrategy;

public class Carte1 extends Carte implements IControleStrategy{
	
	protected Carte1(String path, String name, Partie p, String color, int index) {
		super(path, name, p, color, index);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void jouer() {
		// TODO Auto-generated method stub
		
	}

}
