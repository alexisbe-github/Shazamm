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
	private VueJeu vj1,vj2;
    private int tempsRestant;
    private int duree;
    PropertyChangeSupport pcs;
    boolean relancer = false;
    
    public Chrono() {
    	this.duree=30;
    	this.pcs = new PropertyChangeSupport(this);
    	this.timer = new Timer(1000,new ControleurChrono(this));
    }
    
    public void setDuree(int d) {
    	this.duree=d;
    }
    
    public int getTempsRestant() {
    	return tempsRestant;
    }
    
    public int getDuree() {
    	return duree;
    }
    
    public void joueurJoue() {
    	if(relancer) {
    		this.tempsRestant = duree;
    		relancer = false;
    	}else {
    		relancer = true;
    	}
    }
    
    public void stopChrono() {
    	this.timer.stop();
    }
    
    public void secondePasse() {
    	this.tempsRestant--;
    	pcs.firePropertyChange("property","x","y");
    	if(tempsRestant==0) {
    		if(vj1!=null) this.finTemps(vj1);
    		if(vj2!=null) this.finTemps(vj2);    		
    	}
    }
    
    public void setVueJeu(VueJeu vj) {
    	if(this.vj1==null) {
    		this.vj1=vj;
    	}else {
    		this.vj2=vj;
    	}
    }
    
    public void startChrono() {
    	this.tempsRestant=duree;
    	this.timer.start();
    }
    
    public void finTemps(VueJeu vj) {
    	timer.stop();

    	vj.getBoutonJouer().doClick();
    	
    	this.startChrono();
    }
    
    public void addObserver(PropertyChangeListener l) {
    	pcs.addPropertyChangeListener(l);
    }
    
}
