package main.java.vue.profil;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import main.java.model.bdd.Profil;
import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;
import main.java.vue.jeu.VueJeu;

public class VueLancementPartie extends JFrame {
	
	private ECouleurJoueur couleurJ1;
	private ECouleurJoueur couleurJ2;
	VueProfil vueProfilJ1;
	VueProfil vueProfilJ2;

	public VueLancementPartie(ECouleurJoueur couleurJ1, ECouleurJoueur couleurJ2) {
		super();
		this.couleurJ1=couleurJ1;
		this.couleurJ2=couleurJ2;
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setVisible(true);
		vueProfilJ1 = new VueProfil(new VueCreationProfil(), new VueSelectionProfil());
		vueProfilJ2 = new VueProfil(new VueCreationProfil(), new VueSelectionProfil());
		JButton boutonLancement = new JButton("Lancer");
		boutonLancement.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VueLancementPartie vlp = VueLancementPartie.this;
				Profil pj1 = VueLancementPartie.this.vueProfilJ1.getPanelSelection().getProfilSelectionne();
				Profil pj2 = VueLancementPartie.this.vueProfilJ2.getPanelSelection().getProfilSelectionne();
				if(!pj1.equals(pj2) && pj1!=null && pj2!=null) {
					lancerPartie(pj1,pj2);
				}
			}
			
			public void lancerPartie(Profil pj1, Profil pj2) {
				Joueur joueur1 = new Joueur(couleurJ1, pj1);
				Joueur joueur2 = new Joueur(couleurJ2, pj2);
				
				Partie p = new Partie(joueur1, joueur2);
				
				VueJeu fenetreJ1 = new VueJeu(joueur1, p);
				VueJeu fenetreJ2 = new VueJeu(joueur2, p);
				
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
		});
		this.add(vueProfilJ1);
		this.add(vueProfilJ2);
		this.add(boutonLancement); 
		this.pack();
	}
	
}
