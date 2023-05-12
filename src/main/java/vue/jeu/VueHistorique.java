package main.java.vue.jeu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.model.bdd.dao.DAOJoueur;
import main.java.model.bdd.dao.DAOManche;
import main.java.model.bdd.dao.beans.JoueurSQL;
import main.java.model.bdd.dao.beans.MancheSQL;
import main.java.model.bdd.dao.beans.PartieSQL;
import main.java.model.bdd.dao.beans.TourSQL;
import main.java.model.jeu.Pont;
import main.java.utils.Utils;

public class VueHistorique extends JFrame{
	
	private PartieSQL partie;
	
	private JLabel derniersCoups; //Label de l'image "Derniers coups" présente au dessus du tableau.
	private JPanel tableauPrincipal; //Tableau de une colonne dans lequel on placera Un label (manche x tour x) par tour et un panel contenant les infos
	private JoueurSQL j1SQL;
	private JoueurSQL j2SQL;
	

	public VueHistorique(PartieSQL p) {
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
		
		DAOJoueur daoj = new DAOJoueur();
		
		j1SQL = daoj.trouver(partie.getIdJoueur1());
		j2SQL = daoj.trouver(partie.getIdJoueur2());
		
		try { 
			this.addTours();
		}catch(NullPointerException e) {
			
		}
		
		this.add(tableauPrincipal);
	}
	
	
	private void addTours() {
		for(MancheSQL m : Utils.getManches(partie)) {
			for(TourSQL t : Utils.getTours(m)) {
				this.addTour(t);
			}
		}
	}
	
	private void addTour(TourSQL t) {
		JPanel panelTour = new JPanel(new GridLayout(0, 1));
		panelTour.setBackground(Color.BLACK);
		panelTour.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		DAOManche daoManche = new DAOManche();
		MancheSQL manche = daoManche.trouver(t.getIdManche());
		
		//Remplacer par les infos de la table correspondante
		JLabel headerTour = new JLabel(String.format("Manche %d - Tour %d", manche.getId(), t.getNumeroTour()));
		headerTour.setForeground(Color.WHITE);
		headerTour.setFont(new Font("Arial", Font.PLAIN, 16));
		headerTour.setHorizontalAlignment(JLabel.CENTER);
		panelTour.add(headerTour);
		
		
		panelTour.add(this.getBilanEtPont(t,true)); //parametre : tour-1 -> true (avant le tour)
		panelTour.add(this.getBilanEtPont(t,false)); //parametre : tour -> false (apres le tour)
		
		this.tableauPrincipal.add(panelTour);
	}
	
	private JPanel getEtatPont(TourSQL t) {
		JPanel panelPont = new JPanel(new GridLayout(2,Pont.TAILLE_PONT));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int l = screenSize.width/200;
		int h = screenSize.width/150;
		ImageIcon caseIcon = new ImageIcon();

		
		
		for(int i=0;i<Pont.TAILLE_PONT;i++) {
			
			if(i==t.getPositionJoueur1()) {
				//Condition à remplacer par : SI i == posJoueurRouge du tour
				caseIcon = Utils.createImageIconColor(Color.RED, l, h);
			}else if(i==t.getPositionJoueur2()){
				//Condition à remplacer par : SI i == posJoueurVert du tour
				caseIcon = Utils.createImageIconColor(Color.GREEN, l, h);
			}else if(i==t.getPositionMurFlammes()) {
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
	
	
	private JPanel getBilanEtPont(TourSQL t, boolean avant) {
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
		
		DAOJoueur daoj = new DAOJoueur(); // RAJOUTER DE QUOI CONTROLER LA COULEUR DES JOUEURS
		JoueurSQL jRouge = daoj.trouver(partie.getIdJoueur1());
		JoueurSQL jVert = daoj.trouver(partie.getIdJoueur2());
		
		bilanRouge.setText(getBilanJoueur(jRouge, t,avant));
		bilanVert.setText(getBilanJoueur(jVert, t,avant));
		
		bilanDesMises.add(bilanRouge);
		bilanDesMises.add(bilanVert);
		bilanDesMises.add(getEtatPont(t));
		
		return bilanDesMises;
	}
	
	private String getBilanJoueur(JoueurSQL j, TourSQL t,boolean avant) {
		String texteBilan;
		if(j.getId()==partie.getIdJoueur1()) {
			texteBilan = String.format("<html>Mise de %s : %s.<br/>", j.getNom(), ((Integer) t.getMiseJoueur1()).toString()); //remplacer par joueurRouge
		}else {
			texteBilan = String.format("<html>Mise de %s : %s.<br/>", j.getNom(), ((Integer) t.getMiseJoueur2()).toString()); //remplacer par joueurRouge
		}

		
		if(avant) {
			texteBilan+="Sorts :<br/>";
			/* REMPLACER PAR getcartesjouees(t) ou un truc comme ça
			for(Carte c : )) {
				if(c.getJoueur().equals(j)) {
					texteBilan+=String.format("%d - %s<br/>", c.getNumeroCarte(),c.getNom());
				}
			}
			*/
		}
		texteBilan+="</html>";
		return texteBilan;
	}
	
	
	
}
