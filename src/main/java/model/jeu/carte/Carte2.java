package main.java.model.jeu.carte;

import java.util.List;
import java.util.Scanner;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Manche;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;

public class Carte2 extends Carte {

	private final String NOM_CARTE = "Clone";
	private final String TEXTE_CARTE = "Je pose devant moi une des cartes jouées par l’adversaire au tour"
			+ " précédent. Cette carte est appliquée à ce tour, comme si je l’avais jouée normalement.";
	private final int NUMERO_CARTE = 2;

	public Carte2(Partie p, Joueur j) {
		super.partie = p;
		super.joueur = j;
		super.numeroCarte = NUMERO_CARTE;
		super.nom = NOM_CARTE;
		super.description = TEXTE_CARTE;
	}

	@Override
	public void lancerEffet(Tour tour) {
		Manche mancheCourante = partie.getMancheCourante();
		if (partie.getNombreManches() != 1 && mancheCourante.getNombreTours() != 1) {
			List<Carte> cartesJoueesParAdversaire = partie.getCartesJoueesParAdversaireTourPrecedent(joueur);
			System.out.println("Entrez le numéro de la carte de l'adversaire à cloner:");
			System.out.println(cartesJoueesParAdversaire);
			boolean trouve = false;
			Scanner sc = new Scanner(System.in);
			int numCarte;
			// Tant qu'on ne trouve pas le numéro de la carte saisie par l'utilisateur dans
			// la liste des cartes jouées par l'adversaire
			while (!trouve) {

				// On demande à saisir le numéro de carte
				numCarte = sc.nextInt();

				// On parcourt les cartes jouées par l'adversaire et si celle-ci est trouvé on
				// la clone
				for (Carte carte : cartesJoueesParAdversaire) {
					if (carte.getNumeroCarte() == numCarte)
						trouve = true;
					tour.clonerCarte(joueur, carte);
				}
			}

		}
	}

}
