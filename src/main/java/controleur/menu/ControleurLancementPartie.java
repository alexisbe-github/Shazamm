package main.java.controleur.menu;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.JTextField;

import main.java.model.bdd.Profil;
import main.java.model.jeu.Chrono;
import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;
import main.java.vue.jeu.VueJeu;
import main.java.vue.menu.VueMenu;
import main.java.vue.profil.VueLancementPartie;

public class ControleurLancementPartie implements ActionListener, MouseMotionListener {

	VueLancementPartie vlp;
	JSpinner saisieTemps;
	Chrono timer;
	ECouleurJoueur cj1;
	ECouleurJoueur cj2;
	
	public ControleurLancementPartie(VueLancementPartie vlp, JSpinner saisie, Chrono timer, ECouleurJoueur cj1, ECouleurJoueur cj2) {
		this.vlp=vlp;
		this.timer=timer;
		this.saisieTemps = saisie;
		this.cj1 = cj1;
		this.cj2 = cj2;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		switch(bouton.getText()) {
		case "Lancer" :
			Profil pj1 = vlp.getVueProfilJ1().getPanelSelection().getProfilSelectionne();
			Profil pj2 = vlp.getVueProfilJ2().getPanelSelection().getProfilSelectionne();
			int time = 0;
			try {
				time = (Integer) saisieTemps.getValue();
				timer.setDuree(time);
			}catch(NumberFormatException ex) {
				
			}
			if(pj1.getId()!=pj2.getId() && pj1!=null && pj2!=null && time>=10 && time<600) {
				//Je vérifie que les profils existent bien, qu'ils ne sont pas identiques et que le temps est "acceptable"
				lancerPartie(pj1,pj2);
			}
			break;
		case "Retour" :
			vlp.dispose();
			new VueMenu();
			break;
		}
	}
		
	
	public void lancerPartie(Profil pj1, Profil pj2) {
		Joueur joueur1 = new Joueur(cj1, pj1);
		Joueur joueur2 = new Joueur(cj2, pj2);
		
		Partie p = new Partie(joueur1, joueur2);
		
		VueJeu fenetreJ1 = new VueJeu(joueur1, p, timer);
		VueJeu fenetreJ2 = new VueJeu(joueur2, p, timer);
		
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		fenetreJ1.setLocation(new Point(0, 0));
		fenetreJ2.setLocation(new Point(width / 2, 0));
		
		if (joueur1.getCouleur().equals(ECouleurJoueur.VERT)) {
			p.setStrategy(fenetreJ1, fenetreJ2);
		} else {
			p.setStrategy(fenetreJ2, fenetreJ1);
		}
		
		p.addObserver(fenetreJ1);
		p.addObserver(fenetreJ2);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Profil pj1 = vlp.getVueProfilJ1().getPanelSelection().getProfilSelectionne();
		Profil pj2 = vlp.getVueProfilJ2().getPanelSelection().getProfilSelectionne();
		if (pj1.equals(pj2)) {
			vlp.getBoutonLancer().setEnabled(false);
		} else vlp.getBoutonLancer().setEnabled(true);
	}
	
}
