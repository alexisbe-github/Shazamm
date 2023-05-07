package main.java.model.jeu.ia;

import java.util.HashSet;
import java.util.Set;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;

public abstract class IAEtatJeu extends Joueur {

	protected Set<Integer> cartesPossedeesParAdversaire;
	protected Partie partieSimulee;

	protected IAEtatJeu(ECouleurJoueur couleur, String nom, String prenom, String avatar) {
		super(couleur, nom, prenom, avatar);
		this.cartesPossedeesParAdversaire = new HashSet<>();
		this.initCartesPossedeesParAdversaire();
	}

	/**
	 * Pour que l'IA puisse modéliser les et imaginer les mouvements, on simule une
	 * partie annexe qui permet de ne pas modifier la partie originelle
	 * 
	 * @param p
	 */
	public void setPartieSimulee(Partie p) {
		this.partieSimulee = p;
	}

	/**
	 * Méthode qui initialise l'ensemble des cartes possédées par l'adversaire En
	 * début de partie l'adversaire à toutes ces cartes
	 */
	private void initCartesPossedeesParAdversaire() {
		for (int i = 1; i < 15; i++) {
			this.cartesPossedeesParAdversaire.add(i);
		}
	}

	protected void enleverCartePossedeeParAdversaire(Integer i) {
		cartesPossedeesParAdversaire.remove(i);
	}

	/**
	 * Méthode d'évaluation de l'état du tour.
	 * 
	 * Pour chaque case où le mur de feu est plus proche de l'adversaire que
	 * l'ordinateur: +20
	 * 
	 * Différence de mana entre l'IA et le joueur
	 * 
	 * Différence de cartes
	 * 
	 * @param partie
	 * @return
	 */
	protected int evaluationTour() {
		int evaluation = 0;

		// Calcul d'évaluation par rapport au mur
		if (partieSimulee.getPont().getDistanceEntreMurDeFeuEtJoueur(this) > 3) {
			evaluation += 20 * partieSimulee.getPont().getDistanceEntreMurDeFeuEtMilieu(); // lorsque l'IA est gagnante
		} else {
			evaluation += -20 * partieSimulee.getPont().getDistanceEntreMurDeFeuEtMilieu(); // lorsque l'IA est perdante
		}

		if (partieSimulee.getPont().getDistanceEntreMurDeFeuEtJoueur(this) == 3) {
			evaluation = 0; // lorsque le mur est au milieu des deux joueurs
		}

		// Calcul d'évaluation par rapport au mana restant (différence de mana IA vs
		// Joueur)
		Joueur joueurAdverse;
		if (this.getCouleur().equals(ECouleurJoueur.ROUGE))
			joueurAdverse = partieSimulee.getJoueurVert();
		else
			joueurAdverse = partieSimulee.getJoueurRouge();

		evaluation += this.getManaActuel() - joueurAdverse.getManaActuel();

		// Calcul d'évaluation au nombre de cartes restantes
		evaluation += this.getMainDuJoueur().size() + this.getPaquet().size()
				- this.cartesPossedeesParAdversaire.size();
		
		

		return evaluation;
	}

}
