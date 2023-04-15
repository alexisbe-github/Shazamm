package main.java.model.jeu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.java.vue.VueLancement;



public class Main {

	public static void main(String[] args) {
		List<ECouleurJoueur> couleursTirees = tirerCouleurs();
		ECouleurJoueur couleurJ1 = couleursTirees.get(0);
		ECouleurJoueur couleurJ2 = couleursTirees.get(1);

		new VueLancement();
		
		//Partie p = new Partie(joueur1, joueur2);
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
