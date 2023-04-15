package main.java.controleur.lancement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;
import main.java.vue.VueConsole;
import main.java.vue.VueLancement;

public class ControleurLancement implements ActionListener {
	
	private VueLancement vl;
	
	public ControleurLancement(VueLancement vl) {
		this.vl = vl;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		switch(bouton.getText()) {
		case "Console":
			vl.setVisible(false);
			List<ECouleurJoueur> couleursTirees = tirerCouleurs();
			ECouleurJoueur couleurJ1 = couleursTirees.get(0);
			ECouleurJoueur couleurJ2 = couleursTirees.get(1);
			Joueur joueur1 = new Joueur(couleurJ1, "Pop", "Simoké", "blabla");
			Joueur joueur2 = new Joueur(couleurJ2, "Sorcier", "ledeux", "blabla");
			Partie p = new Partie(joueur1, joueur2);
			VueConsole vc = new VueConsole(p);
			break;
		case "Inerface Graphique":
			
			break;
		}
	}
	
	private  List<ECouleurJoueur> tirerCouleurs() {
		List<ECouleurJoueur> couleurs = new ArrayList<>();
		Random r = new Random();
		if (r.nextBoolean()) {
			couleurs.add(ECouleurJoueur.ROUGE);
			couleurs.add(ECouleurJoueur.VERT);
		} else {
			couleurs.add(ECouleurJoueur.VERT);
			couleurs.add(ECouleurJoueur.ROUGE);
		}
		return couleurs;
	}

}
