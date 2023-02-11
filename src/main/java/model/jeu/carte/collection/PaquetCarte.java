package main.java.model.jeu.carte.collection;

import java.util.ArrayList;
import java.util.List;

import main.java.model.jeu.carte.Carte;

public class PaquetCarte {
	
	private List<Carte> deck;

	public PaquetCarte() {
		deck = new ArrayList<Carte>();
	}
}
