package main.java.model.jeu.carte;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;

public class Carte4 extends Carte {

	private final String NOM_CARTE = "Fin de Manche";
	private final String TEXTE_CARTE = "La manche est finie ! Les sorciers se replacent à 3 pas du mur de feu "
			+ "(dans sa position actuelle), et on commence une nouvelle manche.";
	private final int NUMERO_CARTE = 4;

	public Carte4(Partie p, Joueur j) {
		super.partie = p;
		super.joueur = j;
		super.numeroCarte = NUMERO_CARTE;
		super.nom = NOM_CARTE;
		super.description = TEXTE_CARTE;
	}

	@Override
	public void lancerEffet(Tour tour) {
		partie.setupPont();
		tour.activerFinDeManche();
		partie.lancerNouvelleManche();
	}

}
