package main.java.vue.jeu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.Timer;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.carte.Carte;
import main.java.model.jeu.ia.IA;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;

public class Chrono extends JLabel{
    private Timer timer;
    private int tempsRestant;
    private final int DUREE;
    private Partie partie;
    private Joueur joueur;
    private VueJeu vj;

    public Chrono(int dsec, Partie p, Joueur j, VueJeu vj) {
    	partie = p;
        joueur = j;
    	DUREE=dsec;
    	this.vj=vj;
    	this.setFont(new Font("Verdana", Font.PLAIN, 20));
    	this.setText(String.format("%02d:%02d", DUREE/60, DUREE%60));
        this.setVisible(true);
        this.init();
        this.setForeground(Color.WHITE);
        this.setHorizontalAlignment(JLabel.CENTER);
        this.reStart();
    }

    private void finDuTemps() {
    	timer.stop();
    	
    	Joueur joueurAdverse;
		if (joueur.getCouleur().equals(ECouleurJoueur.ROUGE)) {
			joueurAdverse = this.partie.getJoueurVert();
		} else {
			joueurAdverse = this.partie.getJoueurRouge();
		}
		boolean adversaireEstUnOrdinateur = joueurAdverse instanceof IA;
		if (adversaireEstUnOrdinateur)
			((IA) joueurAdverse).jouerTour(partie);
		
		int mise = vj.getMise();
		Tour tourCourant = partie.getMancheCourante().getTourCourant();
		tourCourant.setMiseJoueur(joueur, mise);
		partie.jouerTour();
		
    	this.reStart();
    }
    
    public void reStart() {
    	timer.start();
    	tempsRestant=DUREE;
    }
    
    private void init() {
        this.timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tempsRestant--;
                if (tempsRestant==0) {
                    Chrono.this.finDuTemps();
                }else {
                	int minutes = tempsRestant/60;
                	int secondes = tempsRestant%60;
                	Chrono.this.setText(String.format("%02d:%02d", minutes, secondes));
                }
            }
        });
        
        this.timer.start();
    }
}
