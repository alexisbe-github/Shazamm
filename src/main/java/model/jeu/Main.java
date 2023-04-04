package main.java.model.jeu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.java.model.jeu.partie.Partie;
import main.java.vue.VueConsole;

public class Main {

	public static void main(String[] args) {
		List<ECouleurJoueur> couleursTirees = tirerCouleurs();
		ECouleurJoueur couleurJ1 = couleursTirees.get(0);
		ECouleurJoueur couleurJ2 = couleursTirees.get(1);
		Joueur joueur1 = new Joueur(couleurJ1, "Pop", "Simoké", "blabla");
		Joueur joueur2 = new Joueur(couleurJ2, "Sorcier", "Vert", "blabla");
		Partie p = new Partie(joueur1, joueur2);
		//System.out.println(joueur1);
		//System.out.println(joueur2);
		VueConsole vc = new VueConsole(p);
		
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
