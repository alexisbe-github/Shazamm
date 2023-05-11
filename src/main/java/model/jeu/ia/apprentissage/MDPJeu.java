package main.java.model.jeu.ia.apprentissage;

import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.ArrayObservationSpace;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.carte.Carte;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;

public class MDPJeu implements MDP<EtatPartie, Integer, DiscreteSpace> {

	private Partie partie;
	private final Joueur joueur;

	public MDPJeu(Partie partie, Joueur joueur) {
		this.partie = partie;
		this.joueur = joueur;
	}

	@Override
	public ObservationSpace<EtatPartie> getObservationSpace() {
		int[] shape = { 1 }; // On a seulement une seule observation, donc la forme est un tableau de taille
								// 1
		return new ArrayObservationSpace<>(shape);
	}

	@Override
	public DiscreteSpace getActionSpace() {
		return new DiscreteSpace(joueur.getMainDuJoueur().size());
	}

	@Override
	public EtatPartie reset() {
		partie = partie.nouvellePartie();
		return partie.getEtatPartie();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public StepReply<EtatPartie> step(Integer action) {
		int nbCartesAJouer = action; // Le nombre de cartes à jouer correspond à l'action choisie
		if (nbCartesAJouer < 0 || nbCartesAJouer > joueur.getMainDuJoueur().size()) {
	        throw new IllegalArgumentException("Nombre de cartes jouées invalide : " + nbCartesAJouer);
	    }
		partie.getMancheCourante().getTourCourant().setMiseJoueur(joueur, action);
		partie.getMancheCourante().getTourCourant().setMiseJoueur(partie.getJoueurVert(), 25);
		partie.jouerTour(); // La partie avance au prochain tour avec le joueur ayant joué ses cartes
		EtatPartie etatPartie = partie.getEtatPartie();
		Tour tour = partie.getTourPrecedent();
		double reward = etatPartie.getGain(joueur); // La récompense correspond au gain du joueur dans l'état actuel
		boolean done = partie.getPont().unSorcierEstTombe(); // La partie est terminée si une des conditions de fin est atteinte
		return new StepReply<>(etatPartie, reward, done, null); // On retourne l'état suivant, la récompense, l'état
																// final et les informations additionnelles (null ici)
	}

	@Override
	public boolean isDone() {
		return partie.getPont().unSorcierEstTombe();
	}

	@Override
	public MDP<EtatPartie, Integer, DiscreteSpace> newInstance() {
		return new MDPJeu(partie,joueur);
	}

}
