package main.java.model.jeu.carte;

import java.util.Scanner;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;

public class Carte6 extends Carte {

	private final String NOM_CARTE = "Recyclage";
	private final String TEXTE_CARTE = "Je peux rectifier ma mise, en ajoutant ou retranchant jusqu’à 5 points "
			+ "de mana.";
	private final int NUMERO_CARTE = 6;

	public Carte6(Partie p, Joueur j) {
		super.partie = p;
		super.joueur = j;
		super.numeroCarte = NUMERO_CARTE;
		super.nom = NOM_CARTE;
		super.description = TEXTE_CARTE;
	}

	@Override
	public void lancerEffet(Tour tour) {
		// Pour l'instant on le fait au scanner
		Scanner sc = new Scanner(System.in);
		System.out.println("["+joueur.getCouleur()+"]"+"Rentrez la mise de mana à modifier sur le tour (entre +5 et -5):");
		int mana;
		do {
			mana = sc.nextInt();
		} while (mana > 5 || mana < -5);
		tour.changerMise(joueur, mana);
	}

}
