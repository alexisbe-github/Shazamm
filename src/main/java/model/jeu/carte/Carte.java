package main.java.model.jeu.carte;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.Partie;

public abstract class Carte {

	protected final String PATH;
	protected final String NAME;
	protected Partie partieActuelle;
	protected final Joueur j;
	protected final int index;
	
	protected Carte(String path, String n, Partie p, Joueur j, int i) {
		this.index=i;
		this.partieActuelle = p;
		this.j=j;
		this.NAME = n;
		PATH = path;
	}
	
	protected abstract void jouer();
	
}
