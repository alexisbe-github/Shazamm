package main.java.vue.jeu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.Pont;
import main.java.model.jeu.carte.Carte;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;
import main.java.utils.Utils;

public class VueHistorique extends JFrame{
	
	private Partie partie;
	
	private JLabel derniersCoups; //Label de l'image "Derniers coups" présente au dessus du tableau.
	private JPanel tableauPrincipal; //Tableau de une colonne dans lequel on placera Un label (manche x tour x) par tour et un panel contenant les infos
	

	public VueHistorique(Partie p) {
		this.setVisible(true);
		//this.setSize(new Dimension(500,500));
		this.setBackground(Color.BLACK);
		this.derniersCoups = new JLabel();
		this.derniersCoups.setIcon(new ImageIcon("src/main/resources/fr_dernierscoups_r.gif"));
		this.tableauPrincipal = new JPanel();
		this.partie = p;
		this.init();
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width / 3, screenSize.height * 9 / 10);
	}
	
	private void init() {
		tableauPrincipal.setAlignmentX(CENTER_ALIGNMENT);
		tableauPrincipal.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		tableauPrincipal.setBackground(Color.BLACK);
		tableauPrincipal.add(derniersCoups);
		derniersCoups.setBackground(Color.BLUE);
		
		try { 
			this.addTour(partie.getTourPrecedent());
			this.addTour(partie.getTourPrecedent());
		}catch(NullPointerException e) {
			
		}
		
		this.add(tableauPrincipal);
	}
	
	
	
	
	private void addTour(Tour t) {
		JPanel panelTour = new JPanel(new GridLayout(0, 1));
		panelTour.setBackground(Color.BLACK);
		panelTour.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		//Remplacer par les infos de la table correspondante
		JLabel headerTour = new JLabel(String.format("Manche %s - Tour %s", partie.getNombreManches(), partie.getMancheCourante().getNombreTours()-1));
		headerTour.setForeground(Color.WHITE);
		headerTour.setFont(new Font("Arial", Font.PLAIN, 16));
		headerTour.setHorizontalAlignment(JLabel.CENTER);
		panelTour.add(headerTour);
		
		
		panelTour.add(this.getBilanEtPont(t,true)); //parametre : tour-1 -> true (avant le tour)
		panelTour.add(this.getBilanEtPont(t,false)); //parametre : tour -> false (apres le tour)
		
		this.tableauPrincipal.add(panelTour);
	}
	
	private JPanel getEtatPont(Tour t) {
		JPanel panelPont = new JPanel(new GridLayout(2,Pont.TAILLE_PONT));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int l = screenSize.width/200;
		int h = screenSize.width/150;
		ImageIcon caseIcon = new ImageIcon();

		for(int i=0;i<Pont.TAILLE_PONT;i++) {
			if(i==6) {
				//Condition à remplacer par : SI i == posJoueurRouge du tour
				caseIcon = Utils.createImageIconColor(Color.RED, l, h);
			}else if(i==12){
				//Condition à remplacer par : SI i == posJoueurVert du tour
				caseIcon = Utils.createImageIconColor(Color.GREEN, l, h);
			}else if(i==9) {
				//Condition à remplacer par : SI i == posMur du tour
				caseIcon = Utils.createImageIconColor(Color.ORANGE, l, h);
			}else {
				//si ce n'est aucune entité particulière
				caseIcon = new ImageIcon(new BufferedImage(l,h,BufferedImage.TYPE_INT_ARGB));
			}
			JLabel labelCase = new JLabel();
			labelCase.setIcon(caseIcon);
			labelCase.setBorder(BorderFactory.createLineBorder(Color.white));
			panelPont.add(labelCase);
		}
		for(int i=0;i<Pont.TAILLE_PONT;i++) {
			if(i==0 || i==Pont.TAILLE_PONT-1) {
				//Condition à remplacer par : SI i == indexLave-1 || SI i == TAILLE_PONT-indexLave+1
				caseIcon = Utils.createImageIconColor(Color.GRAY, l, h);
			}else {
				caseIcon = Utils.createImageIconColor(Color.LIGHT_GRAY, l, h);
			}
			JLabel labelCase = new JLabel();
			labelCase.setIcon(caseIcon);
			labelCase.setBorder(BorderFactory.createLineBorder(Color.white));
			panelPont.add(labelCase);
		}
		
		panelPont.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		return panelPont;
	}
	
	
	private JPanel getBilanEtPont(Tour t, boolean avant) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		JPanel bilanDesMises = new JPanel();
		JLabel titre = new JLabel();
		if(avant) {
			titre.setText("<html>Avant tour<br/></html>");
		}else {
			titre.setText("<html>Après tour<br/></html>");
		}
		titre.setForeground(Color.WHITE);
		bilanDesMises.add(titre);
		bilanDesMises.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		bilanDesMises.setBackground(Color.BLACK);
		JLabel bilanRouge = new JLabel();
		bilanRouge.setForeground(Color.RED);
		bilanRouge.setFont(new Font("Arial", Font.PLAIN, screenSize.width/150));
		JLabel bilanVert = new JLabel();
		bilanVert.setForeground(Color.GREEN);
		bilanVert.setFont(new Font("Arial", Font.PLAIN, screenSize.width/150));
		bilanRouge.setText(getBilanJoueur(ECouleurJoueur.ROUGE, t,avant));
		bilanVert.setText(getBilanJoueur(ECouleurJoueur.VERT, t,avant));
		
		bilanDesMises.add(bilanRouge);
		bilanDesMises.add(bilanVert);
		bilanDesMises.add(getEtatPont(t));
		
		return bilanDesMises;
	}
	
	private String getBilanJoueur(ECouleurJoueur couleur, Tour t,boolean avant) {
		Joueur j;
		if(couleur.equals(ECouleurJoueur.ROUGE)) 
			j = partie.getJoueurRouge(); 
		else j = partie.getJoueurVert();
		
		String texteBilan = String.format("<html>Mise de %s : %s.<br/>", j.getNom(), ((Integer) t.getMiseJoueur(j)).toString());
		
		if(avant) {
			texteBilan+="Sorts :<br/>";
			for(Carte c : t.getCartesJouees()) {
				if(c.getJoueur().equals(j)) {
					texteBilan+=String.format("%d - %s<br/>", c.getNumeroCarte(),c.getNom());
				}
			}
		}
		texteBilan+="</html>";
		return texteBilan;
	}
	
	
	
}
