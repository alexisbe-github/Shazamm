package main.java.model.jeu.partie;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;

import main.java.model.bdd.Profil;
import main.java.model.bdd.dao.DAOJoueur;
import main.java.model.bdd.dao.DAOPartie;
import main.java.model.bdd.dao.beans.PartieSQL;
import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.Pont;
import main.java.model.jeu.carte.Carte;
import main.java.model.jeu.ia.IAFacile;
import main.java.model.jeu.ia.apprentissage.EtatPartie;
import main.java.vue.ILancementStrategy;
import main.java.vue.VueConsole;

public class Partie implements Cloneable {

	private Joueur joueurRouge, joueurVert;
	private Pont pont;
	private List<Manche> listeManche;
	private boolean partieFinie, joueurPousse, cartesJouees;
	private PartieSQL partieSQL;
	private ECouleurJoueur couleurJ1;

	public ILancementStrategy strategyVert, strategyRouge;
	private PropertyChangeSupport pcs;

	public Partie(Joueur j1, Joueur j2) {
		if (j1.getCouleur().equals(ECouleurJoueur.ROUGE)) {
			couleurJ1 = ECouleurJoueur.ROUGE;
			joueurRouge = j1;
			joueurVert = j2;
		} else {
			couleurJ1 = ECouleurJoueur.VERT;
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
	 * Etabli le pattern strategy du joueur vert
	 * 
	 * @param strategyVert
	 */
	public void setStrategyVert(ILancementStrategy strategyVert) {
		this.strategyVert = strategyVert;
	}

	/**
	 * Etabli le pattern strategy du joueur rouge
	 * 
	 * @param strategyRouge
	 */
	public void setStrategyRouge(ILancementStrategy strategyRouge) {
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
		this.setStrategyVert(new IAFacile(joueurVert));
		if (joueur.getCouleur().equals(ECouleurJoueur.VERT)) {
			strategyVert.lancerLarcin(p, tour, joueur);
		} else
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
		initPartieBDD(couleurJ1);
		this.listeManche.add(new Manche(this));
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
		this.listeManche.add(new Manche(this));
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

	public Tour getTour(int numeroManche, int numeroTour) {
		if (this.listeManche.size() >= numeroManche) {
			Manche manche = this.listeManche.get(numeroManche - 1);
			if (manche.getListeTours().size() >= numeroTour) {
				Tour tour = manche.getListeTours().get(numeroTour - 1);
				return tour;
			}
		}
		return null;
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
				if (this.getMancheCourante().getNombreTours() == 0) {
					joueurRouge.melangerPaquet();
					joueurVert.melangerPaquet();
					joueurRouge.remplirReserveDeMana();
					joueurVert.remplirReserveDeMana();
				}
				// Si un des deux joueurs n'a plus de mana on déplace le mur de feu vers le
				// joueur avec 0 de mana
				if ((joueurRouge.getManaActuel() == 0 || joueurVert.getManaActuel() == 0)
						&& this.getMancheCourante().getNombreTours() != 0
						&& !mancheCourante.getTourCourant().isFinDeManche()) {
					this.deplacerMurDeFeuVersJoueurAvec0Mana();
					this.lancerNouvelleManche();
				} else {
					this.lancerNouveauTour();
				}
			}

			pcs.firePropertyChange("property", "x", "y");
			if (pont.unSorcierEstTombe()) {
				this.partieFinie = true;
				switch(this.getGagnant()) {
				case 1 :
					this.setVainqueur(ECouleurJoueur.VERT);
					break;
				case 2 :
					this.setVainqueur(ECouleurJoueur.ROUGE);
				}
			}
			printPossibleGagnant();
		}
	}

	private void printPossibleGagnant() {
		if (this.strategyRouge instanceof VueConsole && this.strategyVert instanceof VueConsole) {
			System.out.println(pont.getVainqueurString());
		}
	}

	public String getGagnantString() {
		return this.pont.getVainqueurString();
	}

	/*
	 * @return -1:Pas de gagnant, 0:Egalité, 1:JoueurVert gagnant, 2:JoueurRouge gagnant
	 */
	public int getGagnant() {
		int res = -1;
		if (pont.getPosJoueurRouge() <= pont.getIndexLave() && pont.getPosJoueurVert() >= Pont.TAILLE_PONT - pont.getIndexLave()) {
			res = 0;
		} else {
			if (pont.getPosJoueurRouge() <= pont.getIndexLave()) {
				res = 1;
			}
			if (pont.getPosJoueurVert() >= Pont.TAILLE_PONT - pont.getIndexLave()) {
				res = 2;
			}
		}
		return res;
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

	/**
	 * Méthode de clonage pour que l'IA puisse simuler une partie. On clone aussi
	 * les manches et les tours pour éviter de toucher à ceux de la vraie partie
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		Partie partieClonee = (Partie) super.clone();
		List<Manche> tmpManche = new ArrayList<>();
		for (Manche m : this.listeManche) {
			List<Tour> tmpTour = new ArrayList<>();
			Manche mancheClonee = (Manche) m.clone();
			for (Tour t : mancheClonee.getListeTours()) {
				Tour tourClonee = (Tour) t.clone();
				tmpTour.add(tourClonee);
			}
			mancheClonee.setListeTours(tmpTour);
			tmpManche.add(mancheClonee);
		}
		partieClonee.listeManche = tmpManche;
		partieClonee.pont = (Pont) this.pont.clone();
		this.joueurRouge = (Joueur) joueurRouge.clone();

		Joueur joueurRougeTmp, joueurVertTmp;
		List<Carte> paquetTmpRouge, paquetTmpVert, mainTmpRouge, mainTmpVert;

		joueurRougeTmp = (Joueur) joueurRouge.clone();
		joueurVertTmp = (Joueur) joueurVert.clone();

		paquetTmpRouge = new ArrayList<Carte>();
		mainTmpRouge = new ArrayList<Carte>();
		paquetTmpVert = new ArrayList<Carte>();
		mainTmpVert = new ArrayList<Carte>();

		for (Carte c : joueurRouge.getPaquet()) {
			paquetTmpRouge.add((Carte) c.clone());
		}

		for (Carte c : this.joueurRouge.getMainDuJoueur()) {
			mainTmpRouge.add((Carte) c.clone());
		}

		for (Carte c : joueurRouge.getPaquet()) {
			paquetTmpVert.add((Carte) c.clone());
		}

		for (Carte c : this.joueurRouge.getMainDuJoueur()) {
			mainTmpVert.add((Carte) c.clone());
		}

		joueurRougeTmp.setMainDuJoueur(mainTmpRouge);
		joueurRougeTmp.setPaquet(paquetTmpRouge);

		joueurVertTmp.setMainDuJoueur(mainTmpVert);
		joueurVertTmp.setPaquet(paquetTmpVert);

		partieClonee.joueurRouge = joueurRougeTmp;
		partieClonee.joueurVert = joueurVertTmp;
		return partieClonee;
	}

	public List<Partie> getCoupsPossibles(Joueur joueur) {
		List<Partie> res = new ArrayList<>();

		try {
			Partie partieOriginale = (Partie) this.clone();
			Partie partieTmp;
			int manaMax = joueur.getManaActuel() + 1;
			if (manaMax < 20) {
				manaMax = joueur.getManaActuel() + 1;
			} else {
				manaMax = (int) ((joueur.getManaActuel() + 1) / 1.5);
			}
			for (int i = 1; i < manaMax; i++) {
				partieTmp = (Partie) partieOriginale.clone();
				int mise = i;

				Joueur j;
				if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE)) {
					j = partieTmp.getJoueurRouge();
				} else {
					j = partieTmp.getJoueurVert();
				}

				partieTmp = (Partie) partieOriginale.clone();
				partieTmp.getMancheCourante().getTourCourant().setMiseJoueur(j, mise);

				for (int k = 0; k < j.getMainDuJoueur().size()
						&& !partieTmp.getMancheCourante().getMutismeCourant(); k++) {
					Partie partieTmp2 = (Partie) partieTmp.clone();
					if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE)) {
						if (!(partieTmp2.joueurRouge.getMainDuJoueur().get(k).getNumeroCarte() == 5 && partieTmp2
								.getPont().getDistanceEntreMurDeFeuEtJoueur(partieTmp2.getJoueurRouge()) < 3)) {
							partieTmp.jouerCarte(partieTmp2.joueurRouge.getMainDuJoueur().get(k),
									partieTmp2.joueurRouge);
						}
					} else {
						if (!(partieTmp2.joueurVert.getMainDuJoueur().get(k).getNumeroCarte() == 5 && partieTmp2
								.getPont().getDistanceEntreMurDeFeuEtJoueur(partieTmp2.getJoueurVert()) < 3))
							partieTmp.jouerCarte(partieTmp2.joueurVert.getMainDuJoueur().get(k), partieTmp2.joueurVert);
					}

					Joueur joueurAdverse;
					if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE)) {
						joueurAdverse = partieTmp2.getJoueurVert();
					} else {
						joueurAdverse = partieTmp2.getJoueurRouge();
					}

					for (Partie partie : this.jouerCoupsPossiblesJoueur(joueurAdverse, partieTmp2)) {
						res.add(partie);
					}
				}

			}

//			for (int i = 1; i < joueur.getManaActuel() + 1; i++) {
//				partieTmp = (Partie) partieOriginale.clone();
//
//				Joueur j;
//				if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE)) {
//					j = partieTmp.getJoueurRouge();
//				} else {
//					j = partieTmp.getJoueurVert();
//				}
//				
//				partieTmp.getMancheCourante().getTourCourant().setMiseJoueur(j, i);
//				
//				Joueur joueurAdverse;
//				if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE)) {
//					joueurAdverse = partieTmp.getJoueurVert();
//				} else {
//					joueurAdverse = partieTmp.getJoueurRouge();
//				}
//				
//				for (Partie partie : this.jouerCoupsPossiblesJoueur(joueurAdverse, partieTmp)) {
//					res.add(partie);
//					partieTmp = (Partie) partieOriginale.clone();
//				}
//			}

		} catch (CloneNotSupportedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return res;
	}

	private List<Partie> jouerCoupsPossiblesJoueur(Joueur joueur, Partie p) {
		List<Partie> res = new ArrayList<>();
		Partie partieTmp;
		try {
			joueur = (Joueur) joueur.clone();
			// on essaye toutes les combinaisons de la main avec 1 carte
			for (int i = 0; i < joueur.getMainDuJoueur().size() + joueur.getPaquet().size(); i++) {

				// on part du principe qu'il va mise en moyenne la moitié de son mana actuel
				int mise = (int) Math.ceil(joueur.getManaActuel() / 2);

				// et on essaye de jouer la carte courante et on l'ajoute à la liste
				partieTmp = (Partie) p.clone();

				List<Carte> paquetEtMain = new ArrayList<>();

				if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE)) {
					paquetEtMain.addAll(partieTmp.joueurRouge.getMainDuJoueur());
					paquetEtMain.addAll(partieTmp.joueurRouge.getPaquet());
					Collections.shuffle(paquetEtMain); // l'IA ne connait pas la main du joueur donc on considère toutes
														// ses cartes et on les mélange pour tirer aléatoirement
					if (partieTmp.joueurRouge.getMainDuJoueur().size() > i)
						partieTmp.jouerCarte(paquetEtMain.get(i), partieTmp.joueurRouge);
				} else {
					paquetEtMain.addAll(partieTmp.joueurVert.getMainDuJoueur());
					paquetEtMain.addAll(partieTmp.joueurVert.getPaquet());
					Collections.shuffle(paquetEtMain);// l'IA ne connait pas la main du joueur donc on considère toutes
														// ses cartes et on les mélange pour tirer aléatoirement
					if (partieTmp.joueurVert.getMainDuJoueur().size() > i)
						partieTmp.jouerCarte(paquetEtMain.get(i), partieTmp.joueurVert);
				}
				res.add(partieTmp);
			}

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return res;
	}

	public void simulerTour() {
		Manche mancheCourante = this.getMancheCourante();
		Tour tourCourant = mancheCourante.getTourCourant();
		int miseJoueurRouge = tourCourant.getMiseJoueurRouge();
		int miseJoueurVert = tourCourant.getMiseJoueurVert();
		if (miseJoueurRouge != 0 && miseJoueurVert != 0) {
			this.cartesJouees = false;
			int dpMur = mancheCourante.jouerTour(joueurRouge, joueurVert);
			this.cartesJouees = true;
			pont.deplacerMurDeFeu(dpMur);
			if (pont.murDeFeuPousseUnSorcier() && this.getMancheCourante().getNombreTours() != 0) {
				this.lancerNouvelleManche();
				this.joueurPousse = true;
			} else {
				this.joueurPousse = false;
				if (this.getMancheCourante().getNombreTours() == 0) {
					joueurRouge.melangerPaquet();
					joueurVert.melangerPaquet();
					joueurRouge.piocherCartes(3);
					joueurVert.piocherCartes(3);
					joueurRouge.remplirReserveDeMana();
					joueurVert.remplirReserveDeMana();
				}
				// Si un des deux joueurs n'a plus de mana on déplace le mur de feu vers le
				// joueur avec 0 de mana
				if ((joueurRouge.getManaActuel() == 0 || joueurVert.getManaActuel() == 0)
						&& this.getMancheCourante().getNombreTours() != 0) {
					this.deplacerMurDeFeuVersJoueurAvec0Mana();
					this.lancerNouvelleManche();
				} else {
					this.lancerNouveauTour();
				}
			}

			if (pont.unSorcierEstTombe()) {
				this.partieFinie = true;
			}
		}
	}

	public Partie nouvellePartie() {
		Joueur jR = new Joueur(joueurRouge.getCouleur(), "IApprentissage", "IApprentissage", "IApprentissage");
		IAFacile jV = new IAFacile(joueurVert.getCouleur(),
				new Profil("IAdversaire", "IAdversaire", new ImageIcon("IAdversaire")));
		Partie p = new Partie(jR, jV);
		p.strategyVert = jV;
		System.out.println(p.strategyVert);
		return p;
	}

	public EtatPartie getEtatPartie() {
		return new EtatPartie(this, joueurRouge, new IAFacile(joueurVert));
	}

	private void initPartieBDD(ECouleurJoueur couleurJ1) {
		if(this.getJoueurRouge().getProfil()!=null && this.getJoueurVert().getProfil()!=null) {
			this.partieSQL = new PartieSQL();
			switch (couleurJ1) {
			case ROUGE:
				this.partieSQL.setIdJoueur1(joueurRouge.getProfil().getId());
				this.partieSQL.setIdJoueur2(joueurVert.getProfil().getId());
				break;
			case VERT:
				this.partieSQL.setIdJoueur1(joueurVert.getProfil().getId());
				this.partieSQL.setIdJoueur2(joueurRouge.getProfil().getId());
			}
			this.partieSQL.setIdVainqueur(joueurRouge.getProfil().getId());
			new DAOPartie().creer(this.partieSQL);
		}
	}

	private void setVainqueur(ECouleurJoueur couleur) {
		switch (couleur) {
		case ROUGE:
			int nbVictoiresRouge = this.joueurRouge.getProfil().getNbPartiesGagnees();
			nbVictoiresRouge++;
			this.joueurRouge.getProfil().setNbPartiesGagnees(nbVictoiresRouge);
			new DAOJoueur().maj(this.joueurRouge.getProfil());
			this.partieSQL.setIdVainqueur(this.joueurRouge.getProfil().getId());
			break;
		case VERT:
			int nbVictoiresVert = this.joueurVert.getProfil().getNbPartiesGagnees();
			nbVictoiresVert++;
			this.joueurVert.getProfil().setNbPartiesGagnees(nbVictoiresVert);
			new DAOJoueur().maj(this.joueurVert.getProfil());
			this.partieSQL.setIdVainqueur(this.joueurVert.getProfil().getId());
			break;
		}
	}
	
	public PartieSQL getPartieSQL() {
		return this.partieSQL;
	}
	
	public ECouleurJoueur getCouleurJ1() {
		return this.couleurJ1;
	}

}
