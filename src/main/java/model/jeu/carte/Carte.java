package main.java.model.jeu.carte;

import main.java.model.jeu.Partie;

public abstract class Carte {

	protected final String PATH;
	protected final String NAME;
	protected Partie partieActuelle;
	protected String color;
	protected int index;
	
	protected Carte(String path, String name, Partie p, String color, int index) {
		this.index=index;
		this.partieActuelle = p;
		this.color = color;
		this.NAME = name;
		PATH = "src/resources/cartes/"+color.toLowerCase().charAt(0)+this.index;
	}
	
	protected abstract void jouer();
	
}
