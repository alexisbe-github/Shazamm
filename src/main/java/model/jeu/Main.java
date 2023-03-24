package main.java.model.jeu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.java.model.jeu.partie.Partie;

public class Main {

	public static void main(String[] args) {
		List<ECouleurJoueur> couleursTirees = tirerCouleurs();
		Joueur joueur1 = new Joueur(couleursTirees.get(0), "Pop", "Simoké", "blabla");
		Joueur joueur2 = new Joueur(couleursTirees.get(1), "Sorcier", "Vert", "blabla");
		Partie p = new Partie(joueur1, joueur2);
		System.out.println(joueur1);
		System.out.println(joueur2);
	}

	private static List<ECouleurJoueur> tirerCouleurs() {
		List<ECouleurJoueur> couleurs = new ArrayList<>();
		Random r = new Random();
		if (r.nextBoolean()) {
			couleurs.add(ECouleurJoueur.ROUGE);
			couleurs.add(ECouleurJoueur.VERT);
		} else {
			couleurs.add(ECouleurJoueur.VERT);
			couleurs.add(ECouleurJoueur.ROUGE);
		}
		return couleurs;
	}
}
