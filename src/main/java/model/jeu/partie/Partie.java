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
		joueurRouge.melangerPaquet();
		joueurVert.melangerPaquet();
		joueurRouge.piocherCartes(3);
		joueurVert.piocherCartes(3);
		joueurRouge.remplirReserveDeMana();
		joueurVert.remplirReserveDeMana();
		pont.effondrerMorceauDuPont();
		pont.placerJoueurs();
		this.listeManche.add(new Manche());
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
