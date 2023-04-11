package main.java.model.jeu.partie;

import java.util.ArrayList;
import java.util.List;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.Pont;
import main.java.model.jeu.carte.Carte;

public class Partie {

	private Joueur joueurRouge, joueurVert;
	private Pont pont;
	private List<Manche> listeManche;
	private boolean partieFinie;

	public Partie(Joueur j1, Joueur j2) {
		if (j1.getCouleur().equals(ECouleurJoueur.ROUGE)) {
			joueurRouge = j1;
			joueurVert = j2;
		} else {
			joueurRouge = j2;
			joueurVert = j1;
		}
		pont = new Pont();
		listeManche = new ArrayList<>();
		partieFinie = false;
		lancerPartie();
	}

	/**
	 * Déplace le mur de feu de dp sur le pont (dp pouvant être négatif)
	 * 
	 * @param dp int étant le déplacement
	 */
	public void deplacerMur(int dp) {
		pont.deplacerMurDeFeu(dp);
	}

	/**
	 * Faire reculer de dp le joueur vert du pont
	 * 
	 * @param dp int
	 */
	public void reculerJoueurVert(int dp) {
		pont.reculerJoueur(dp, ECouleurJoueur.VERT);
	}

	/**
	 * Faire reculer de dp le joueur rouge du pont
	 * 
	 * @param dp
	 */
	public void reculerJoueurRouge(int dp) {
		pont.reculerJoueur(dp, ECouleurJoueur.ROUGE);
	}

	public void printMancheEtTour() {
		System.out.println("[PARTIE] MANCHE " + this.getNombreManches() + " TOUR NUMERO "
				+ getMancheCourante().getNumeroTourCourant());
	}

	private void lancerPartie() {
		joueurRouge.initialiserPaquet(this);
		joueurVert.initialiserPaquet(this);
		joueurRouge.piocherCartes(5);
		joueurVert.piocherCartes(5);
		joueurRouge.remplirReserveDeMana();
		joueurVert.remplirReserveDeMana();
		this.listeManche.add(new Manche());
	}

	public void lancerNouvelleManche() {
		if (!pont.unSorcierEstTombe()) {
			joueurRouge.melangerPaquet();
			joueurVert.melangerPaquet();
			joueurRouge.piocherCartes(3);
			joueurVert.piocherCartes(3);
			joueurRouge.remplirReserveDeMana();
			joueurVert.remplirReserveDeMana();
			pont.effondrerMorceauxDuPont();
			pont.placerJoueurs();
			this.listeManche.add(new Manche());
		} else {
			partieFinie = true;
		}
	}

	public void lancerFinDeManche() {
		this.placerJoueurs();
		this.lancerNouvelleManche();
		this.getMancheCourante().getListeTours().remove(0); // on retire le premier Tour qui est auto instancié à
															// l'instanciation de manche
	}

	public void lancerNouveauTour() {
		Manche mancheCourante = this.getMancheCourante();
		if (pont.murDeFeuPousseUnSorcier())
			this.lancerNouvelleManche();
		else
			mancheCourante.passerAuTourSuivant();
	}

	public boolean getPartieFinie() {
		return this.partieFinie;
	}

	public void jouerTour(int miseRouge, int miseVert) {
		Manche mancheCourante = this.getMancheCourante();
		int dpMur = mancheCourante.jouerTour(joueurRouge, joueurVert, miseRouge, miseVert);
		pont.deplacerMurDeFeu(dpMur);
		System.out.println(pont.murDeFeuPousseUnSorcier());
		if (pont.murDeFeuPousseUnSorcier()) {
			this.lancerNouvelleManche();
		} else {
			this.lancerNouveauTour();
		}
		// Si un des deux joueurs n'a plus de mana on déplace le mur de feu vers le
		// joueur avec 0 de mana
		if (joueurRouge.getManaActuel() == 0 || joueurVert.getManaActuel() == 0) {
			this.deplacerMurDeFeuVersJoueurAvec0Mana();
			this.lancerNouvelleManche();
		}
	}

	/**
	 * Deplace le mur de feu vers le joueur perdant avec 0 de mana. S'arrête
	 * lorsqu'il croise le perdant où lorsque le gagnant a moins de mana que la
	 * différence de cases entre le mur et le perdant.
	 */
	private void deplacerMurDeFeuVersJoueurAvec0Mana() {
		// on établit qui perd, qui gagne et dans quel sens le mur va se déplacer
		int sensDeplacementMur = 0;
		Joueur gagnant, perdant;
		if (joueurRouge.getManaActuel() == 0) {
			sensDeplacementMur = -1;
			gagnant = joueurVert;
			perdant = joueurRouge;
		} else {
			sensDeplacementMur = 1;
			gagnant = joueurRouge;
			perdant = joueurVert;

		}

		// Pour chaque point de mana on deplace le mur de feu d'une case vers le perdant
		int diffMurPerdant = Math.abs(pont.getPosMurDeFeu() - pont.getPosJoueur(perdant));
		if (diffMurPerdant > gagnant.getManaActuel()) {
			pont.deplacerMurDeFeu(gagnant.getManaActuel() * sensDeplacementMur);
		} else {
			pont.deplacerMurDeFeu(diffMurPerdant * sensDeplacementMur);
		}
	}

	/**
	 * Récupère la liste des cartes jouées par l'adversaire au tour précédent. Si on
	 * est en début de manche on va chercher le dernier tour de la manche précédente
	 * 
	 * @param joueur qui demande les cartes adverses
	 * @return List<Carte> jouées par l'adversaire
	 */
	public List<Carte> getCartesJoueesParAdversaireTourPrecedent(Joueur joueur) {
		List<Carte> res = new ArrayList<Carte>();
		Tour tourPrecedent;
		Manche mancheCourante = this.getMancheCourante();

		// On calcule le tour précédent
		if (mancheCourante.getNombreTours() <= 1) {
			Manche manchePrecedente = this.listeManche.get(this.listeManche.size() - 2);
			List<Tour> tours = manchePrecedente.getListeTours();
			tourPrecedent = tours.get(manchePrecedente.getNombreTours() - 1);
		} else {
			List<Tour> tours = mancheCourante.getListeTours();
			tourPrecedent = tours.get(mancheCourante.getNombreTours() - 2);
		}

		if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE))
			return tourPrecedent.getCartesJoueesVert();
		else
			return tourPrecedent.getCartesJoueesRouge();

	}

	public Manche getMancheCourante() {
		return this.listeManche.get(this.listeManche.size() - 1);
	}

	public void placerJoueurs() {
		this.pont.placerJoueurs();
	}

	public int getPosJoueur(ECouleurJoueur couleur) {
		if (couleur.equals(ECouleurJoueur.ROUGE))
			return this.pont.getPosJoueurRouge();
		else
			return this.pont.getPosJoueurVert();
	}

	public Pont getPont() {
		return this.pont;
	}

	public Joueur getJoueurRouge() {
		return this.joueurRouge;
	}

	public Joueur getJoueurVert() {
		return this.joueurVert;
	}

	public int getNombreManches() {
		return this.listeManche.size();
	}

}
