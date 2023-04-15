package main.java.vue;

import java.util.List;
import java.util.Scanner;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.carte.Carte;
import main.java.model.jeu.partie.Manche;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;

public class VueConsole implements ILancementStrategy {

	private Partie partie;

	public VueConsole(Partie p) {
		this.partie = p;
		this.partie.setStrategy(this);
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
				if (choix == 0) {
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

	@Override
	public void lancerClone(Partie p, Tour tour, Joueur joueur) {
		Manche mancheCourante = partie.getMancheCourante();
		if (!(partie.getNombreManches() == 1 && mancheCourante.getNombreTours() == 1)) {
			List<Carte> cartesJoueesParAdversaire = partie.getCartesJoueesParAdversaireTourPrecedent(joueur);
			if (cartesJoueesParAdversaire.size() > 0) {

				boolean trouve = false;
				Scanner sc = new Scanner(System.in);
				int numCarte;
				// Tant qu'on ne trouve pas le numéro de la carte saisie par l'utilisateur dans
				// la liste des cartes jouées par l'adversaire
				while (!trouve) {

					System.out.println(
							"[" + joueur.getCouleur() + "] Entrez le numéro de la carte de l'adversaire à cloner:");
					System.out.println(cartesJoueesParAdversaire);
					// On demande à saisir le numéro de carte
					numCarte = sc.nextInt();

					// On parcourt les cartes jouées par l'adversaire et si celle-ci est trouvé on
					// la clone
					for (Carte carte : cartesJoueesParAdversaire) {
						if (carte.getNumeroCarte() == numCarte) {
							trouve = true;
							carte.changerDetenteurCarte(joueur);
							tour.activerClone(carte);
						}
					}
				}
			}

		}
	}

	@Override
	public void lancerRecyclage(Partie p, Tour tour, Joueur joueur) {
		Scanner sc = new Scanner(System.in);
		int mana;
		do {
			System.out.println("[" + joueur.getCouleur() + "]"
					+ "Rentrez la mise de mana à modifier sur le tour (entre +5 et -5):");
			mana = sc.nextInt();
			if (mana + tour.getMiseJoueur(joueur) > joueur.getManaActuel())
				System.out.println("Vous n'avez pas assez de mana");
		} while (mana > 5 || mana < -5 || (mana + tour.getMiseJoueur(joueur) > joueur.getManaActuel()));
		tour.changerMise(joueur, mana);
	}

	@Override
	public void lancerLarcin(Partie p, Tour tour, Joueur joueur) {
		Scanner sc = new Scanner(System.in);
		if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE)) {
			for (Carte c : tour.getCartesJoueesVert()) {
				System.out.println(c + "\n[" + joueur.getCouleur()
						+ "]Tapez oui si vous voulez utiliser cette carte, n'importe quelle touche si vous voulez la défausser.");
				String res = sc.nextLine();
				if (res.equalsIgnoreCase("oui")) {
					c.changerDetenteurCarte(joueur);
				} else {
					c.defausser();
				}
			}
		} else {
			for (Carte c : tour.getCartesJoueesRouge()) {
				System.out.println(c + "\n[" + joueur.getCouleur()
						+ "]Tapez oui si vous voulez utiliser cette carte, n'importe quelle touche si vous voulez la défausser.");
				String res = sc.nextLine();
				if (res.equals("oui")) {
					c.changerDetenteurCarte(joueur);
				} else {
					c.defausser();
				}
			}
		}
	}

}
