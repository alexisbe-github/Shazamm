package main.java.vue.jeu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.carte.Carte;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;

public class VueHistorique extends JFrame{
	
	private Partie partie;
	
	private JLabel derniersCoups; //Label de l'image "Derniers coups" présente au dessus du tableau.
	private JPanel tableauPrincipal; //Tableau de une colonne dans lequel on placera Un label (manche x tour x) par tour et un panel contenant les infos
	

	public VueHistorique(Partie p) {
		this.setVisible(true);
		this.setSize(new Dimension(500,500));
		this.setBackground(Color.BLACK);
		this.derniersCoups = new JLabel();
		this.derniersCoups.setIcon(new ImageIcon("src/main/resources/fr_dernierscoups_r"));
		this.tableauPrincipal = new JPanel();
		this.partie = p;
		this.init();
	}
	
	private void init() {
		tableauPrincipal.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		tableauPrincipal.setBackground(Color.BLACK);
		if(partie.getNombreManches()>1 || partie.getMancheCourante().getNombreTours()>1)
			
		tableauPrincipal.add(derniersCoups);	
		try {
			this.addTour(partie.getTourPrecedent());
		}catch(NullPointerException e) {
			
		}
		this.add(tableauPrincipal);
	}
	
	private void addTour(Tour t) {
		JPanel panelTour = new JPanel();
		panelTour.setBackground(Color.GRAY);
		panelTour.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		panelTour.setLayout(new GridLayout(4,1));
		
		JPanel bilanDesMises = new JPanel();
		JLabel titre = new JLabel("<html>Bilan des Mises<br/></html>");
		titre.setForeground(Color.WHITE);
		titre.setHorizontalAlignment(JLabel.CENTER);
		bilanDesMises.add(titre);
		bilanDesMises.setLayout(new BoxLayout(bilanDesMises, BoxLayout.Y_AXIS));
		bilanDesMises.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		bilanDesMises.setBackground(Color.GRAY);
		
		JLabel bilanRouge = new JLabel();
		bilanRouge.setForeground(Color.RED);
		JLabel bilanVert = new JLabel();
		bilanVert.setForeground(Color.GREEN);
		
		bilanRouge.setText(getBilan(ECouleurJoueur.ROUGE, t));
		bilanVert.setText(getBilan(ECouleurJoueur.VERT, t));
		bilanDesMises.add(bilanRouge);
		bilanDesMises.add(bilanVert);
		
		panelTour.add(bilanDesMises);
		
		this.tableauPrincipal.add(panelTour);
	}
	
	private String getBilan(ECouleurJoueur couleur, Tour t) {
		Joueur j;
		if(couleur.equals(ECouleurJoueur.ROUGE)) 
			j = partie.getJoueurRouge(); 
		else j = partie.getJoueurVert();
		
		String texteBilan = String.format("<html>Mise de %s : %s.<br/>Sorts :<br/>", j.getNom(), ((Integer) t.getMiseJoueurRouge()).toString());
		for(Carte c : t.getCartesJouees()) {
			if(c.getJoueur().equals(j)) {
				texteBilan+=String.format("%d - %s<br/>", c.getNumeroCarte(),c.getNom());
			}
		}
		texteBilan+="<br/></html>";
		System.out.println(texteBilan);
		return texteBilan;
	}
	
	
	
}
