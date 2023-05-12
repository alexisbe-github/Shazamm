package main.java.model.jeu.ia;

import main.java.model.bdd.Profil;
import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;

public class IAEtatJeu extends Joueur {

	protected IAEtatJeu(Joueur j) {
		super(j.getCouleur(), j.getProfil());
	}
	
	protected IAEtatJeu(ECouleurJoueur couleur, Profil profil) {
		super(couleur, profil);
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
	 * Distance joueur/IA avec la lave + ou - 5 fois la distance
	 * 
	 * @param partie
	 * @return
	 */
	public int evaluationTour(Partie p) {
		int evaluation = 0;
		int evaluationMur = 0;
		int evaluationMana = 0;
		int evaluationCarte = 0;
		int evaluationLave = 0;

		Joueur joueurAdverse;
		if (this.getCouleur().equals(ECouleurJoueur.ROUGE))
			joueurAdverse = p.getJoueurVert();
		else
			joueurAdverse = p.getJoueurRouge();

		// Calcul d'évaluation par rapport au mur
		if (p.getPont().getDistanceEntreMurDeFeuEtJoueur(this) > 2) {
			evaluationMur += 5 * p.getPont().getDistanceEntreMurDeFeuEtMilieu(); // lorsque l'IA est
																								// gagnante
		}
		if (p.getPont().getDistanceEntreMurDeFeuEtJoueur(joueurAdverse) > 2) {
			evaluationMur += -5 * p.getPont().getDistanceEntreMurDeFeuEtMilieu(); // lorsque l'IA est
																								// perdante
		}

		// Calcul d'évaluation par rapport au mana restant (différence de mana IA vs
		// Joueur)
		evaluationMana += this.getManaActuel() - joueurAdverse.getManaActuel();

		// Calcul d'évaluation au nombre de cartes restantes
		evaluationCarte += (this.getMainDuJoueur().size() + this.getPaquet().size())
				- (joueurAdverse.getPaquet().size() + joueurAdverse.getMainDuJoueur().size());

		// Calcul d'évaluation par rapport au joueur le plus proche de la lave
		if (p.getPont().getDistanceEntreJoueurEtLave(this) > p.getPont()
				.getDistanceEntreJoueurEtLave(joueurAdverse)) {
			evaluationLave += 20 * p.getPont().getDistanceEntreJoueurEtLave(this); // cas où le joueur est le
																								// plus proche de la
																								// lave:
																								// 5*distance entre IA
																								// et
																								// lave
		}

		if (p.getPont().getDistanceEntreJoueurEtLave(this) < p.getPont()
				.getDistanceEntreJoueurEtLave(joueurAdverse)) {
			evaluationLave += -20 * p.getPont().getDistanceEntreJoueurEtLave(this); // cas où l'IA est le
																								// plus proche de la
																								// lave:
																								// -5*distance entre
																								// joueur
																								// et
																								// lave
		}

//		System.out.println("Mur:" + evaluationMur + " Mana:" + evaluationMana + " Carte:" + evaluationCarte + " Lave:"
//				+ evaluationLave);
		evaluation = evaluationMur + evaluationMana + evaluationCarte + evaluationLave;
//		System.out.println("Evaluation:" + evaluation);
		return evaluation;
	}

}
