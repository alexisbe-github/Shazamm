package main.java.vue.profil;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.controleur.menu.ControleurLancementPartie;
import main.java.model.jeu.Chrono;
import main.java.model.jeu.ECouleurJoueur;

public class VueLancementPartie extends JFrame {
	
	private ECouleurJoueur couleurJ1;
	private ECouleurJoueur couleurJ2;
	private JTextField saisieTemps;
	private VueProfil vueProfilJ1;
	private VueProfil vueProfilJ2;
	private Chrono timer;

	public VueLancementPartie(ECouleurJoueur couleurJ1, ECouleurJoueur couleurJ2, Chrono timer) {
		super();
		this.timer=timer;
		this.couleurJ1=couleurJ1;
		this.couleurJ2=couleurJ2;
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setVisible(true);
		
		vueProfilJ1 = new VueProfil(new VueCreationProfil(), new VueSelectionProfil());
		vueProfilJ2 = new VueProfil(new VueCreationProfil(), new VueSelectionProfil());
		JButton boutonLancement = new JButton("Lancer");
		JButton boutonRetour = new JButton("Retour");
		
		saisieTemps = new JTextField("30",5);
		
		ControleurLancementPartie control = new ControleurLancementPartie(this, saisieTemps, timer, couleurJ1, couleurJ2);
		
		boutonLancement.addActionListener(control);
		boutonRetour.addActionListener(control);
		
		JPanel validerPanel = new JPanel();
		validerPanel.add(boutonRetour);
		validerPanel.add(boutonLancement);
		validerPanel.add(saisieTemps);
		
		this.add(vueProfilJ1);
		this.add(vueProfilJ2);
		this.add(validerPanel);
		this.pack();
		
	}
	
	public VueProfil getVueProfilJ1() {
		return vueProfilJ1;
	}
	
	public VueProfil getVueProfilJ2() {
		return vueProfilJ2;
	}
	
}
