package main.java.vue;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VueLancement extends JFrame{

	//panel contenant les boutons : une ligne, deux colonnes
	private JPanel panelBoutons = new JPanel(new GridLayout(1,2));
	
	/*
	 * Label représentant le texte, bouton console et bouton interface graphique.
	 */
	private final JLabel texte = new JLabel("Voulez-vous lancer le jeu sur console ou IG ?");
	private JButton boutonConsole = new JButton("Console");
	private JButton boutonIG = new JButton("Interface Graphique");
	
	/*
	 * Constructeur initialisant le titre et les différentes propriétés de la fenêtre.
	 */
	public VueLancement(String titre) {
		super(titre);
		
		//Grid Layout contenant 2 lignes et 1 colonne
		this.setLayout(new GridLayout(2,1));
		
		/**
		 * Centrage du texte, ajout des boutons à panelBoutons
		 * ajout des éléments précédents à la fenêtre
		 */
		texte.setHorizontalAlignment(JLabel.CENTER);
		panelBoutons.add(boutonConsole);
		panelBoutons.add(boutonIG);
		this.add(texte);
		this.add(panelBoutons);
		
		setPreferredSize(new Dimension(400, 200));
	    pack();
		setVisible(true); // Rend la fenêtre visible
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Quitte le programme quand on ferme la fenêtre
		setLocationRelativeTo(null); // Centre la fenêtre par rapport à l'écran
		
	}
	
	
	
}
