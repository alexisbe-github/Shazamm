package main.java.vue;

import java.util.Scanner;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.carte.Carte;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;

public class VueConsole implements ILancementStrategy{

	private Partie partie;

	public VueConsole(Partie p) {
		this.partie = p;
		this.lancerJeu();
	}

	@Override
	public void lancerJeu() {
		while (!partie.getPartieFinie()) {
			partie.printMancheEtTour();
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
			this.partie.jouerTour(miseRouge, miseVert);
			
		}
	}

	private void printJeu() {
		System.out.println("[PARTIE] PONT : " + partie.getPont());
	}

	private void choixCarte(Joueur j) {
		Scanner saisie = new Scanner(System.in);
		Carte carte;
		boolean validInput = false;

		int choix = 1;
		while (choix != 0 && j.getMainDuJoueur().size() > 0) {
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
				System.out.println("Numéro de carte incorrecte, veuillez ressaisir un numéro de carte valide!\n");
			}
		}
	}

	private int choixMise(Joueur j) {
		System.out.println("[" + j.getCouleur() + "] Mana de " + j.getNom() + " : " + j.getManaActuel() + ".");
		System.out.println(j.mainString());
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
		System.out.println("\n");
		return choix;
	}

}
