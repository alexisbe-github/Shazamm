package main.java.model.jeu.carte;

import main.java.model.jeu.Partie;

public abstract class Carte {

	protected Partie partieActuelle;
	protected final String PATH;
	protected final String NAME;
	
	protected Carte(String path, String name, Partie p) {
		this.partieActuelle = p;
		this.NAME = name;
		this.PATH = path;
	}
	
	protected abstract void jouer();
	
}
