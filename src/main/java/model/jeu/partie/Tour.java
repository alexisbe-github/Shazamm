package main.java.model.jeu.partie;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import main.java.model.bdd.dao.DAOCarte;
import main.java.model.bdd.dao.DAOTour;
import main.java.model.bdd.dao.beans.CarteSQL;
import main.java.model.bdd.dao.beans.TourSQL;
import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.carte.Carte;

public class Tour implements Cloneable {

	private Manche mancheCourante;
	private TourSQL tourSQL;
	private List<CarteSQL> listeCartesJouees = new ArrayList<>();
	
	private int miseJoueurRouge, miseJoueurVert;
	private int attaqueJoueurRouge, attaqueJoueurVert;
	private int manaRestantRouge, manaRestantVert;

	private int deplacementMur;
	private boolean mutisme, finDeManche;

	private boolean harpagonRouge, harpagonVert;
	private List<Carte> cartesJoueesVert, cartesJoueesRouge;
	private List<Carte> cartesJouees;

	public Tour(Manche manche, boolean mutisme) {
		mancheCourante = manche;
		this.cartesJoueesRouge = new ArrayList<>();
		this.cartesJoueesVert = new ArrayList<>();
		this.cartesJouees = new ArrayList<>();
		this.miseJoueurRouge = 0;
		this.miseJoueurVert = 0;
		this.mutisme = mutisme;
		this.finDeManche = false;
		this.harpagonRouge = false;
		this.harpagonVert = false;
	}

	public void activerMutisme(boolean enable) {
		this.mutisme = enable;
	}

	public boolean getMutisme() {
		return this.mutisme;
	}

	public void activerFinDeManche() {
		this.finDeManche = true;
	}

	/**
	 * Ajoute la carte à jouer dans la liste des cartes à jouer du joueur
	 * 
	 * @param carteAJouer
	 * @param joueur      Joueur qui joue la carte
	 */
	public void jouerCarte(Carte carteAJouer, Joueur joueur) {
		if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE)) {
			this.cartesJoueesRouge.add(carteAJouer);
			joueur.retirerCarteDeLaMain(carteAJouer);
		} else {
			this.cartesJoueesVert.add(carteAJouer);
			joueur.retirerCarteDeLaMain(carteAJouer);
		}
		this.cartesJouees.add(carteAJouer);
		this.trierCartesJouees();
		ajouterCarteTour(carteAJouer, joueur);
	}
	
	private void ajouterCarteTour(Carte c, Joueur j) {
		CarteSQL carte = new CarteSQL();
		carte.setIdJoueur(j.getProfil().getId());
		carte.setNumeroCarte(c.getNumeroCarte());
		listeCartesJouees.add(carte);
	}
	
	private void initCarteBDD() {
		DAOCarte dao = new DAOCarte();
		for (CarteSQL c : this.listeCartesJouees) {
			c.setIdTour(getTourSQL().getId());
			dao.creer(c);
		}
		this.listeCartesJouees.clear();
	}

	/**
	 * Joue le tour en fonction des mises
	 * 
	 * @param miseRouge int
	 * @param miseVert  int
	 * @return int le déplacement du mur à la fin du tour
	 */
	public int jouerTour(Joueur joueurRouge, Joueur joueurVert) {
		// Initialisation des variables pour le tour
		this.harpagonRouge = false;
		this.harpagonVert = false;
		this.attaqueJoueurRouge = miseJoueurRouge;
		this.attaqueJoueurVert = miseJoueurVert;
		this.calculDeplacementMur();

		// Si mutisme n'est pas activé pour la manche alors on peut jouer les cartes
		if (!this.mutisme)
			this.jouerTourDesCartes();

		// On defausse toutes les cartes jouées
		for (Carte cRouge : this.cartesJoueesRouge) {
			cRouge.defausser();
		}
		for (Carte cVert : this.cartesJoueesVert) {
			cVert.defausser();
		}

//		System.out.println("Puissance attaque rouge:" + this.attaqueJoueurRouge + "     " + "Puissance attaque verte:"
//				+ this.attaqueJoueurVert + "       Déplacement du mur:" + this.deplacementMur);

		if (!this.harpagonRouge) {
			joueurRouge.depenserMana(this.miseJoueurRouge);
		}

		if (!this.harpagonVert) {
			joueurVert.depenserMana(this.miseJoueurVert);
		}

		joueurRouge.verifierMana();
		joueurVert.verifierMana();

		if (this.finDeManche)
			this.deplacementMur = 0;

		this.manaRestantRouge = joueurRouge.getManaActuel();
		this.manaRestantVert = joueurVert.getManaActuel();
		
		initTourBDD();
		initCarteBDD();

		return this.deplacementMur;
	}

	private void calculDeplacementMur() {
		if (this.attaqueJoueurRouge == this.attaqueJoueurVert)
			this.deplacementMur = 0;
		else
			this.deplacementMur = (this.attaqueJoueurRouge - this.attaqueJoueurVert)
					/ Math.abs(this.attaqueJoueurRouge - this.attaqueJoueurVert);
	}

	/**
	 * Joue les cartes en jeu en fonction de leur numéro de carte
	 */
	private void jouerTourDesCartes() {
		// Pour chaque carte jouée on regarde si la suivante est le même numéro de carte
		// et on active l'effet
		for (int i = 0; i < cartesJouees.size() && !this.finDeManche && !this.mutisme; i++) {
			Carte carteCourante = cartesJouees.get(i);
			boolean carteJoueeDeuxFois = false;

			if (i < cartesJouees.size() - 1) {
				Carte carteSuivante = cartesJouees.get(i + 1);
				int numCarteSuivante = carteSuivante.getNumeroCarte();
				if (numCarteSuivante == carteCourante.getNumeroCarte())
					carteJoueeDeuxFois = true;
			}
			if (i > 0) {
				Carte cartePrecedente = cartesJouees.get(i - 1);
				int numCartePrecedente = cartePrecedente.getNumeroCarte();
				if (numCartePrecedente == carteCourante.getNumeroCarte())
					carteJoueeDeuxFois = true;
			}

			// On verifie qu'une carte n'est pas jouée deux fois, sinon les deux cartes
			// s'annulent
			if (!carteJoueeDeuxFois) {
//				System.out.println("[" + carteCourante.getJoueur().getCouleur() + "]" + carteCourante);
//				System.out.println(
//						"Puissance attaque rouge:" + this.attaqueJoueurRouge + "     " + "Puissance attaque verte:"
//								+ this.attaqueJoueurVert + "       Déplacement du mur:" + this.deplacementMur);
				carteCourante.lancerEffet(this);
			}

			// si la carte suivante ne concerne pas les deplacements de mur on calcule le
			// déplacement
			if (carteCourante.getNumeroCarte() < 9 && carteCourante.getNumeroCarte() != 1)
				this.calculDeplacementMur();
		}
	}

	private void trierCartesJouees() {
		// Comparateur pour trier les cartes en fonction de leur numéro
		Comparator<Carte> c = new Comparator<Carte>() {
			@Override
			public int compare(Carte o1, Carte o2) {
				return o1.getNumeroCarte() - o2.getNumeroCarte();
			}
		};

		Collections.sort(cartesJouees, c); // Tri des cartes jouées
	}

	/**
	 * Effet carte 2
	 * 
	 * @param carte
	 */
	public void activerClone(Carte carte) {
		this.cartesJouees.add(carte);
		this.trierCartesJouees();
	}

	/**
	 * Effet carte 12
	 * 
	 * @param joueur
	 */
	public void activerHarpagon(Joueur joueur) {
		if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE))
			this.harpagonRouge = true;
		else
			this.harpagonVert = true;
	}

	/**
	 * Effet carte 10 Double le déplacement du mur dans le sens dans lequel il doit
	 * avancer
	 */
	public void doubleDeplacementMur() {
		deplacementMur *= 2;
	}

	/**
	 * Effet carte 9 Inverse le déplacement du mur dans le sens opposé où il devait
	 * avancer
	 */
	public void inverserDeplacementMur() {
		deplacementMur *= -1;
	}

	/**
	 * Change de mana la mise de mana du tour
	 * 
	 * @param caster Joueur qui a casté le sort de mise
	 * @param mana   int montant en mana à ajouter/enlever
	 */
	public void changerMise(Joueur caster, int mana) {
		if (caster.getCouleur().equals(ECouleurJoueur.ROUGE)) {
			this.miseJoueurRouge += mana;
			this.attaqueJoueurRouge += mana;
		} else {
			this.miseJoueurVert += mana;
			this.attaqueJoueurVert += mana;
		}

		if (this.miseJoueurVert < 0) {
			this.miseJoueurVert = 0;
			this.attaqueJoueurVert = 0;
		}

		if (this.miseJoueurRouge < 0) {
			this.miseJoueurRouge = 0;
			this.attaqueJoueurRouge = 0;
		}
	}

	public void recyclerMise(Joueur joueur, int montant) {
		if (montant > 5)
			montant = 5;
		if (montant < -5)
			montant = -5;
		this.changerMise(joueur, montant);
	}

	/**
	 * Ajoute atq au joueur
	 * 
	 * @param atq    int
	 * @param joueur ECouleurJoueur
	 */
	public void addAttaqueJoueur(ECouleurJoueur joueur, int atq) {
		if (joueur == ECouleurJoueur.ROUGE)
			this.attaqueJoueurRouge += atq;
		else
			this.attaqueJoueurVert += atq;
	}

	public int getAttaqueJoueur(ECouleurJoueur joueur) {
		if (joueur == ECouleurJoueur.ROUGE)
			return this.attaqueJoueurRouge;
		else
			return this.attaqueJoueurVert;
	}

	public int getMiseJoueur(Joueur joueur) {
		if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE))
			return this.miseJoueurRouge;
		else
			return this.miseJoueurVert;
	}

	public void setMiseJoueur(Joueur joueur, int mise) {
		if (mise > joueur.getManaActuel()) {
			mise = joueur.getManaActuel();
		}
		if (mise < 1)
			mise = 1;
		if (joueur.getCouleur().equals(ECouleurJoueur.VERT))
			this.miseJoueurVert = mise;
		else
			this.miseJoueurRouge = mise;
	}

	public void setMiseJoueurVert(int mise) {
		this.miseJoueurVert = mise;
	}

	public void setMiseJoueurRouge(int mise) {
		this.miseJoueurRouge = mise;
	}

	public int getMiseJoueurRouge() {
		return this.miseJoueurRouge;
	}

	public int getMiseJoueurVert() {
		return this.miseJoueurVert;
	}

	public int getDeplacementMur() {
		return this.deplacementMur;
	}

	public void setDeplacementMur(int d) {
		deplacementMur = d;
	}

	public int getAttaqueJoueurRouge() {
		return attaqueJoueurRouge;
	}

	public int getAttaqueJoueurVert() {
		return attaqueJoueurVert;
	}

	public List<Carte> getCartesJoueesVert() {
		return cartesJoueesVert;
	}

	public List<Carte> getCartesJoueesRouge() {
		return cartesJoueesRouge;
	}

	public List<Carte> getCartesJouees() {
		return cartesJouees;
	}

	public boolean isFinDeManche() {
		return finDeManche;
	}

	public int getManaRestantRouge() {
		return manaRestantRouge;
	}

	public int getManaRestantVert() {
		return manaRestantVert;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Tour tourClone = (Tour) super.clone();
		List<Carte> cartesJoueesVertTmp, cartesJoueesRougeTmp, cartesJoueesTmp;
		cartesJoueesVertTmp = new ArrayList<>();
		cartesJoueesRougeTmp = new ArrayList<>();
		cartesJoueesTmp = new ArrayList<>();
		for(Carte c:cartesJoueesVert) {
			cartesJoueesVertTmp.add((Carte)c.clone());
		}
		
		for(Carte c:cartesJoueesRouge) {
			cartesJoueesRougeTmp.add((Carte)c.clone());
		}
		
		for(Carte c:cartesJouees) {
			cartesJoueesTmp.add((Carte)c.clone());
		}
		tourClone.cartesJouees = cartesJoueesTmp;
		tourClone.cartesJoueesRouge = cartesJoueesRougeTmp;
		tourClone.cartesJoueesVert = cartesJoueesVertTmp;
		return tourClone;
	}
	
	public int evaluerTour(Joueur j,Partie p) {
		int res = 0;
		if(j.getCouleur().equals(ECouleurJoueur.ROUGE)) {
			if(this.deplacementMur < 0) res = -1;
			else res =1;
			if(p.getPont().getPosJoueurVert() == p.getPont().getPosMurDeFeu()+deplacementMur) res += 10;
			if(p.getPont().getPosJoueurRouge() == p.getPont().getPosMurDeFeu()+deplacementMur) res -= 10;
		}else {
			if(this.deplacementMur < 0) res = 1;
			else res = -1;
			if(p.getPont().getPosJoueurRouge() == p.getPont().getPosMurDeFeu()+deplacementMur) res += 10;
			if(p.getPont().getPosJoueurVert() == p.getPont().getPosMurDeFeu()+deplacementMur) res -= 10;
		}
		return res;
	}
	
	private void initTourBDD() {
		if(this.mancheCourante.getMancheSQL()!=null) {
			ECouleurJoueur couleurJ1 = mancheCourante.getPartieCourante().getCouleurJ1();
			ECouleurJoueur couleurJ2 = couleurJ1 == ECouleurJoueur.ROUGE ? ECouleurJoueur.VERT : ECouleurJoueur.ROUGE;
			tourSQL = new TourSQL();
			tourSQL.setIdManche(mancheCourante.getMancheSQL().getId());
			tourSQL.setPositionMurFlammes(mancheCourante.getPartieCourante().getPont().getPosMurDeFeu());
			tourSQL.setPositionJoueur1(mancheCourante.getPartieCourante().getPosJoueur(couleurJ1));
			tourSQL.setPositionJoueur2(mancheCourante.getPartieCourante().getPosJoueur(couleurJ2));
			tourSQL.setMiseJoueur1(couleurJ1 == ECouleurJoueur.ROUGE ? miseJoueurRouge : miseJoueurVert);
			tourSQL.setMiseJoueur2(couleurJ2 == ECouleurJoueur.ROUGE ? miseJoueurRouge : miseJoueurVert);
			tourSQL.setPuissanceJoueur1(getAttaqueJoueur(couleurJ1));
			tourSQL.setPuissanceJoueur2(getAttaqueJoueur(couleurJ2));
			tourSQL.setNumeroTour(mancheCourante.getNumeroTourCourant());
			tourSQL.setDate(new Timestamp(System.currentTimeMillis()));
			
			new DAOTour().creer(tourSQL);
		}
	}
	
	public TourSQL getTourSQL() {
		return this.tourSQL;
	}
}
