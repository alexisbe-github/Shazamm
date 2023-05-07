package main.java.model.jeu.partie;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.Pont;
import main.java.model.jeu.carte.Carte;
import main.java.vue.ILancementStrategy;
import main.java.vue.VueConsole;

public class Partie {

	private Joueur joueurRouge, joueurVert;
	private Pont pont;
	private List<Manche> listeManche;
	private boolean partieFinie, joueurPousse, cartesJouees;

	private ILancementStrategy strategyVert, strategyRouge;
	private PropertyChangeSupport pcs;

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
		this.joueurPousse = false;
		this.cartesJouees = false;
		this.pcs = new PropertyChangeSupport(this);
		lancerPartie();
	}

	public void addObserver(PropertyChangeListener l) {
		pcs.addPropertyChangeListener(l);
	}

	/**
	 * Etabli le pattern strategy en fonction des interfaces utilisateurs
	 * 
	 * @param strategyVert
	 * @param strategyRouge
	 */
	public void setStrategy(ILancementStrategy strategyVert, ILancementStrategy strategyRouge) {
		this.strategyVert = strategyVert;
		this.strategyRouge = strategyRouge;
	}

	/**
	 * Donne la liste des cartes jouées par un joueur passé en paramètre du tour
	 * courant
	 * 
	 * @param joueur
	 * @return List<Carte>
	 */
	public List<Carte> getListeCartesJoueesParJoueur(Joueur joueur) {
		Tour tourCourant = this.getMancheCourante().getTourCourant();
		if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE))
			return tourCourant.getCartesJoueesRouge();
		return tourCourant.getCartesJoueesVert();
	}

	public void jouerCarte(Carte carteAJouer, Joueur joueur) {
		Tour tourCourant = this.getMancheCourante().getTourCourant();
		tourCourant.jouerCarte(carteAJouer, joueur);
	}

	/**
	 * En fonction de la strategy la méthode va lancer l'effet de la carte 2 au
	 * scanner ou sur l'interface graphique
	 * 
	 * @param p
	 * @param tour
	 * @param joueur
	 */
	public void lancerClone(Partie p, Tour tour, Joueur joueur) {
		if (joueur.getCouleur().equals(ECouleurJoueur.VERT))
			strategyVert.lancerClone(p, tour, joueur);
		else
			strategyRouge.lancerClone(p, tour, joueur);
	}

	/**
	 * En fonction de strategy la méthode va lancer l'effet de la carte 6 au scanner
	 * ou sur l'interface graphique
	 * 
	 * @param p
	 * @param tour
	 * @param joueur
	 */
	public void lancerRecyclage(Partie p, Tour tour, Joueur joueur) {
		if (joueur.getCouleur().equals(ECouleurJoueur.VERT))
			strategyVert.lancerRecyclage(p, tour, joueur);
		else
			strategyRouge.lancerRecyclage(p, tour, joueur);
	}

	/**
	 * En fonction de strategy la méthode va lancer l'effet de la carte 3 au scanner
	 * ou sur l'interface graphique
	 * 
	 * @param p
	 * @param tour
	 * @param joueur
	 */
	public void lancerLarcin(Partie p, Tour tour, Joueur joueur) {
		if (joueur.getCouleur().equals(ECouleurJoueur.VERT))
			strategyVert.lancerLarcin(p, tour, joueur);
		else
			strategyRouge.lancerLarcin(p, tour, joueur);
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
	 * Place le mur de feu sur l'index en paramètre
	 * 
	 * @param index int
	 */
	public void placerMur(int index) {
		pont.placerMurDeFeu(index);
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
		joueurRouge.melangerPaquet();
		joueurVert.melangerPaquet();
		joueurRouge.piocherCartes(3);
		joueurVert.piocherCartes(3);
		joueurRouge.remplirReserveDeMana();
		joueurVert.remplirReserveDeMana();
		pont.effondrerMorceauxDuPont();
		pont.placerJoueurs();
		this.listeManche.add(new Manche());
	}

	public void lancerFinDeManche() {
		this.placerJoueurs();
		this.lancerNouvelleManche();
		this.joueurRouge.remplirReserveDeMana();
		this.joueurVert.remplirReserveDeMana();
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

	public void jouerTour() {
		Manche mancheCourante = this.getMancheCourante();
		Tour tourCourant = mancheCourante.getTourCourant();
		int miseJoueurRouge = tourCourant.getMiseJoueurRouge();
		int miseJoueurVert = tourCourant.getMiseJoueurVert();
		if (miseJoueurRouge != 0 && miseJoueurVert != 0) {
			this.cartesJouees = false;
			pcs.firePropertyChange("property", "x", "y");
			int dpMur = mancheCourante.jouerTour(joueurRouge, joueurVert);
			this.cartesJouees = true;
			pont.deplacerMurDeFeu(dpMur);
			if (pont.murDeFeuPousseUnSorcier() && this.getMancheCourante().getNombreTours() != 0) {
				this.lancerNouvelleManche();
				this.joueurPousse = true;
			} else {
				this.joueurPousse = false;
				// Si un des deux joueurs n'a plus de mana on déplace le mur de feu vers le
				// joueur avec 0 de mana
				if (this.getMancheCourante().getNombreTours() == 0) {
					joueurRouge.melangerPaquet();
					joueurVert.melangerPaquet();
					joueurRouge.piocherCartes(3);
					joueurVert.piocherCartes(3);
					joueurRouge.remplirReserveDeMana();
					joueurVert.remplirReserveDeMana();
				}
				if ((joueurRouge.getManaActuel() == 0 || joueurVert.getManaActuel() == 0)
						&& this.getMancheCourante().getNombreTours() != 0) {
					this.deplacerMurDeFeuVersJoueurAvec0Mana();
					this.lancerNouvelleManche();
				} else {
					this.lancerNouveauTour();
				}
			}

			pcs.firePropertyChange("property", "x", "y");
			if (pont.unSorcierEstTombe()) {
				this.partieFinie = true;
			}
			printPossibleGagnant();
		}
	}

	private void printPossibleGagnant() {
		if (this.strategyRouge instanceof VueConsole && this.strategyVert instanceof VueConsole) {
			System.out.println(pont.getVainqueur());
		}
	}

	public String getGagnant() {
		return this.pont.getVainqueur();
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
		if (mancheCourante.getNombreTours() == 1 && mancheCourante.getNumeroTourCourant() == 1) {
			return res;
		}

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

	public Tour getTourPrecedent() {
		if (this.getNombreManches() > 1 && this.getMancheCourante().getNombreTours() == 1) {
			Manche manchePrecedente = this.listeManche.get(this.getNombreManches() - 2);
			return manchePrecedente.getTourCourant();
		}
		if (this.getMancheCourante().getNombreTours() > 1) {
			return this.getMancheCourante().getTourPrecedent();
		}
		return null;

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

	public boolean isJoueurPousse() {
		return joueurPousse;
	}

	public boolean isCartesJouees() {
		return this.cartesJouees;
	}

}
