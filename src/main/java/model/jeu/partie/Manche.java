package main.java.model.jeu.partie;

import java.util.ArrayList;
import java.util.List;

import main.java.model.bdd.dao.DAOManche;
import main.java.model.bdd.dao.beans.MancheSQL;
import main.java.model.jeu.Joueur;

public class Manche implements Cloneable {

	private Partie partieCourante;
	private List<Tour> listeTours;
	private boolean mutismeCourant;
	private MancheSQL mancheSQL;

	public Manche(Partie partie) {
		partieCourante = partie;
		initMancheBDD();
		mutismeCourant = false;
		listeTours = new ArrayList<>();
		listeTours.add(new Tour(this, mutismeCourant));
	}

	public Tour getTourCourant() {
		if (this.listeTours.size() > 0) {
			return listeTours.get(listeTours.size() - 1);
		}
		return null;
	}

	public int getNumeroTourCourant() {
		return listeTours.size();
	}

	public void passerAuTourSuivant() {
		if (this.listeTours.size() > 0) {
			Tour tourCourant = getTourCourant();
			this.mutismeCourant = tourCourant.getMutisme();
			this.listeTours.add(new Tour(this, mutismeCourant));
		} else {
			this.listeTours.add(new Tour(this, false));
		}
	}

	public Tour getTourPrecedent() {
		if (this.getNombreTours() > 1) {
			return this.listeTours.get(this.getNombreTours() - 2);
		}
		return null;
	}

	public int jouerTour(Joueur joueurRouge, Joueur joueurVert) {
		Tour tourCourant = this.getTourCourant();
		return tourCourant.jouerTour(joueurRouge, joueurVert);
	}

	public boolean getMutismeCourant() {
		return mutismeCourant;
	}

	public int getNombreTours() {
		return listeTours.size();
	}

	public List<Tour> getListeTours() {
		return listeTours;
	}

	public void setListeTours(List<Tour> listeTours) {
		this.listeTours = listeTours;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Manche mancheClonee = (Manche) super.clone();
		return mancheClonee;
	}
	
	private void initMancheBDD() {
		if(this.partieCourante.getPartieSQL()!=null) {
			mancheSQL = new MancheSQL();
			mancheSQL.setIdPartie(partieCourante.getPartieSQL().getId());
			new DAOManche().creer(mancheSQL);
		}
	}
	
	public MancheSQL getMancheSQL() {
		return this.mancheSQL;
	}
	
	public Partie getPartieCourante() {
		return this.partieCourante;
	}

}
