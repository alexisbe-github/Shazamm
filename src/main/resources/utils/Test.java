package main.resources.utils;

import java.sql.Timestamp;
import java.time.Instant;

import javax.swing.ImageIcon;

import main.java.model.dao.DAOCouleur;
import main.java.model.dao.DAOJoueur;
import main.java.model.dao.DAOManche;
import main.java.model.dao.DAOPartie;
import main.java.model.dao.DAOTour;
import main.java.model.dao.beans.CouleurSQL;
import main.java.model.dao.beans.JoueurSQL;
import main.java.model.dao.beans.MancheSQL;
import main.java.model.dao.beans.PartieSQL;
import main.java.model.dao.beans.TourSQL;
import main.java.model.jeu.ECouleurJoueur;

public class Test {

	public static void main(String[] args) {
		TourSQL tour = new TourSQL(); 
		
		
		DAOTour dao = new DAOTour();
		dao.supprimer(dao.trouver(95));
	}
}
