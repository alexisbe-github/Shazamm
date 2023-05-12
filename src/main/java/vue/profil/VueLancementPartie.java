package main.java.vue.profil;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import main.java.controleur.menu.ControleurLancementPartie;
import main.java.model.jeu.Chrono;
import main.java.model.jeu.ECouleurJoueur;

public class VueLancementPartie extends JFrame {
	
	private ECouleurJoueur couleurJ1;
	private ECouleurJoueur couleurJ2;
	private JSpinner saisieTemps;
	private JButton boutonLancement;
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
		boutonLancement = new JButton("Lancer");
		boutonLancement.setEnabled(false);
		
		JLabel duree = new JLabel("Durée du tour : ");
		saisieTemps = new JSpinner(new SpinnerNumberModel(10, 10, 120, 1));
		JLabel temps = new JLabel("secondes");
		
		ControleurLancementPartie control = new ControleurLancementPartie(this, saisieTemps, timer, couleurJ1, couleurJ2);
		
		boutonLancement.addActionListener(control);
		this.addMouseMotionListener(control);
		
		JPanel validerPanel = new JPanel();
		validerPanel.add(boutonLancement);
		validerPanel.add(duree);
		validerPanel.add(saisieTemps);
		validerPanel.add(temps);
		
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
	
	public JButton getBoutonLancer() {
		return boutonLancement;
	}
	
}
