package main.java.model.jeu.carte;

import main.java.model.jeu.Partie;
import main.java.model.jeu.carte.effets.IMurStrategy;

public class Carte5 extends Carte implements IMurStrategy{

	protected Carte5(String path, String name, Partie p) {
		super(path, name, p);
	}

	public void jouer() {
		super.partieActuelle.deplacerMur(calculDpMur());
	}

	@Override
	public int calculDpMur() {
		Partie partie = super.partieActuelle;
		return ( partie.getPosJ2() + partie.getPosJ1() ) / 2 - partie.getPosMur();
	}
	
}
