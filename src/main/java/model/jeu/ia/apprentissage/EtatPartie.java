package main.java.model.jeu.ia.apprentissage;

import java.util.ArrayList;
import java.util.List;

import org.deeplearning4j.rl4j.space.Encodable;
import org.nd4j.linalg.api.ndarray.INDArray;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.carte.Carte;
import main.java.model.jeu.ia.IA;
import main.java.model.jeu.ia.IAFacile;
import main.java.model.jeu.partie.Partie;

public class EtatPartie implements Encodable {

	private final Partie partie;
	private final Joueur joueur;
	private final IAFacile adversaire;
	
	public EtatPartie(Partie p,Joueur joueur,IAFacile adversaire) {
		this.partie = p;
		this.joueur = joueur;
		this.adversaire = adversaire;
	}

	@Override
	public double[] toArray() {
		int nbCartesJoueur = 14;
		int nbCartesAdversaire = 14;
		int nbPositions = 4; //position joueur rouge, vert, mur de feu, distance lave/vert,lave/rouge
		int nbMontantMana = 2; //montant de mana des deux joueurs
		int numInputs = nbCartesJoueur + nbCartesAdversaire + nbPositions + nbMontantMana;
		double[] array = new double[numInputs];
		for(Integer i:joueur.getCartesPossedees()) {
			array[i-1] = 1;
		}
		for(Integer i:adversaire.getCartesPossedees()) {
			array[(i-1)+nbCartesJoueur] = 1;
		}
		array[28] = partie.getPont().getDistanceEntreJoueurEtLave(joueur);
		array[29] = partie.getPont().getDistanceEntreMurDeFeuEtJoueur(joueur);
		array[30] = partie.getPont().getDistanceEntreJoueurEtLave(adversaire);
		array[31] = partie.getPont().getDistanceEntreMurDeFeuEtJoueur(adversaire);
		array[32] = joueur.getManaActuel();
		array[33] = adversaire.getManaActuel();
		return array;
	}

	public int getGain() {
		return this.partie.getMancheCourante().getTourCourant().evaluerTour(joueur,partie);
	}
	
	@Override
	public boolean isSkipped() {
		return false;
	}

	@Override
	public INDArray getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Encodable dup() {
		// TODO Auto-generated method stub
		return null;
	}
}
