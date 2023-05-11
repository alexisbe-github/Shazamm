package main.java.model.jeu;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.Timer;

import main.java.controleur.jeu.ControleurChrono;
import main.java.model.jeu.ia.IA;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;
import main.java.vue.jeu.VueChrono;
import main.java.vue.jeu.VueJeu;

public class Chrono {

	private Timer timer;
    private int tempsRestant;
    private final int DUREE;
    PropertyChangeSupport pcs;
    
    public Chrono(int duree) {
    	this.DUREE=duree;
    	this.pcs = new PropertyChangeSupport(this);
    	this.timer = new Timer(1000,new ControleurChrono(this));
    }
    
    public int getTempsRestant() {
    	return tempsRestant;
    }
    
    public int getDuree() {
    	return DUREE;
    }
    
    public void secondePasse() {
    	this.tempsRestant--;
    	pcs.firePropertyChange("property","x","y");
    }
    
    public void startChrono() {
    	this.timer.start();
    	this.tempsRestant=DUREE;
    }
    
    public void finTemps(Partie partie, VueJeu vj) {
    	timer.stop();
    	Joueur joueurAdverse;
    	Joueur joueur = vj.getJoueur();
    	
		if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE)) {
			joueurAdverse = partie.getJoueurVert();
		} else {
			joueurAdverse = partie.getJoueurRouge();
		}
		
		boolean adversaireEstUnOrdinateur = joueurAdverse instanceof IA;
		if (adversaireEstUnOrdinateur)
			((IA) joueurAdverse).jouerTour(partie);
		
		int mise = vj.getMise();
		Tour tourCourant = partie.getMancheCourante().getTourCourant();
		tourCourant.setMiseJoueur(joueur, mise);
		
		partie.jouerTour();
    	this.startChrono();
    }
    
    public void addObserver(VueChrono l) {
    	pcs.addPropertyChangeListener(l);
    }
    
}
