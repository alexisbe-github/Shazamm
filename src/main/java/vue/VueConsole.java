package main.java.vue;

import java.util.Scanner;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.carte.Carte;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;

public class VueConsole {

	private Partie partie;

	public VueConsole(Partie p) {
		this.partie = p;
		this.lancerJeu();
	}

	// Implémenter strategy
	private void lancerJeu() {
		while (!partie.getPartieFinie()) {
			System.out.println("[PARTIE] MANCHE " + this.partie.getNombreManches() + " TOUR NUMERO "
					+ this.partie.getMancheCourante().getNumeroTourCourant());
			this.printJeu();

			Joueur joueurRouge = this.partie.getJoueurRouge();
			Joueur joueurVert = this.partie.getJoueurVert();

			// Choix mise et carte(s) du joueur rouge
			int miseRouge = choixMise(joueurRouge);
			choixCarte(joueurRouge);

			// Choix mise et carte(s) du joueur vert
			int miseVert = choixMise(joueurVert);
			choixCarte(joueurVert);

			// lancement du tour
			this.partie.getMancheCourante().getTourCourant().jouerTour(joueurRouge, joueurVert, miseRouge, miseVert);

			this.partie.getMancheCourante().passerAuTourSuivant();
		}
	}

	private void printJeu() {
		System.out.println("[PARTIE] PONT : " + partie.getPont());
	}

	private void choixCarte(Joueur j) {
		Scanner saisie = new Scanner(System.in);
		Carte carte;
		boolean validInput = true;

		int choix = 1;
		while (validInput && choix != 0 && j.getMainDuJoueur().size() > 0) {
			System.out.println("[" + j.getCouleur() + "] Quelle carte souhaitez-vous jouer ? (saisir le numéro)");
			System.out.println(j.mainString());
			System.out.println("0: Arrêter de choisir une carte");
			validInput = false;
			choix = saisie.nextInt();
			for (int i = 0; i < j.getMainDuJoueur().size(); i++) {
				if (j.getMainDuJoueur().get(i).getNumeroCarte() == choix) {
					validInput = true;
					carte = j.getMainDuJoueur().get(i);
					Tour tourCourant = partie.getMancheCourante().getTourCourant();
					tourCourant.jouerCarte(carte, j);
				}
				if(choix == 0) {
					validInput = true;
				}
			}

			if (!validInput) {
				System.out.println("carte incorrecte, veuillez ressaisir une mise valide :");
			}
		}
	}

	private int choixMise(Joueur j) {
		System.out.println("[" + j.getCouleur() + "] Mana de " + j.getNom() + " : " + j.getManaActuel() + ".");
		System.out.println("[" + j.getCouleur() + "] " + j.getNom() + " peut saisir sa mise :");
		int choix = 0;
		boolean validInput = false;
		Scanner saisie = new Scanner(System.in);

		while (!validInput) {
			choix = saisie.nextInt();
			if (choix <= j.getManaActuel() && choix > 0) {
				this.partie.getMancheCourante().getTourCourant().setMiseJoueur(j, choix);
				System.out.println("Mise de mana: " + choix);
				validInput = true;
			} else {
				System.out.println("mise incorrecte, veuillez ressaisir une mise valide :");
			}
		}

		return choix;
	}

}
