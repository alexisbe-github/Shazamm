package main.java.model.jeu.ia.apprentissage;

import org.deeplearning4j.rl4j.space.Encodable;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import main.java.model.jeu.Joueur;
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
		int numInputs = 14 + 1; //mise avec nb cartes
		double[] array = new double[numInputs];
		for(Integer i:joueur.getCartesPossedees()) {
			array[i-1] = 1;
		}
		array[14] = joueur.getManaActuel();
		return array;
	}

	public int getGain() {
		return this.partie.getMancheCourante().getTourPrecedent().evaluerTour(joueur,partie);
	}
	
	@Override
	public boolean isSkipped() {
		return false;
	}

	@Override
	public INDArray getData() {
		double[] data = toArray();
	    return Nd4j.create(data, new int[]{1, data.length});
	}

	@Override
	public Encodable dup() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Partie getPartie() {
		return this.partie;
	}
}
