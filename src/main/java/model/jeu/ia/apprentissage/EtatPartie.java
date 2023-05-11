package main.java.model.jeu.ia.apprentissage;

import java.util.ArrayList;
import java.util.List;

import org.deeplearning4j.rl4j.space.Encodable;
import org.nd4j.linalg.api.ndarray.INDArray;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.carte.Carte;
import main.java.model.jeu.partie.Partie;

public class EtatPartie implements Encodable {

	private final Partie partie;
	private final Joueur joueur;

	public EtatPartie(Partie p,Joueur joueur) {
		this.partie = p;
		this.joueur = joueur;
	}

	@Override
	public double[] toArray() {
		double[] array = new double[18];
		List<Carte> cartesPossedeesParJoueur = new ArrayList<>();
		cartesPossedeesParJoueur.addAll(joueur.getMainDuJoueur());
		cartesPossedeesParJoueur.addAll(joueur.getPaquet());
		for(int i=0;i<14;i++) {
			boolean carteTrouvee = false;
			for(Carte c:cartesPossedeesParJoueur) {
				if(c.getNumeroCarte()==i+1) {
					array[i] = 1;
					carteTrouvee = true;
				}
			}
			if(!carteTrouvee) {
				array[i] = 0;
			}
			carteTrouvee = false;
		}
		
		Joueur joueurAdverse;
		if(joueur.getCouleur().equals(ECouleurJoueur.ROUGE)) {
			joueurAdverse = partie.getJoueurVert();
		}else {
			joueurAdverse = partie.getJoueurRouge();
		}
		array[14] = partie.getPont().getDistanceEntreJoueurEtLave(joueur);
		array[15] = partie.getPont().getDistanceEntreMurDeFeuEtJoueur(joueur);
		array[16] = partie.getPont().getDistanceEntreJoueurEtLave(joueurAdverse);
		array[17] = joueur.getManaActuel();
		return array;
	}

	public int getGain(Joueur j) {
		return this.partie.getMancheCourante().getTourCourant().evaluerTour(j);
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
