package main.java.vue.jeu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.java.model.bdd.dao.DAOCouleur;
import main.java.model.bdd.dao.DAOJoueur;
import main.java.model.bdd.dao.DAOManche;
import main.java.model.bdd.dao.beans.CarteSQL;
import main.java.model.bdd.dao.beans.CouleurSQL;
import main.java.model.bdd.dao.beans.JoueurSQL;
import main.java.model.bdd.dao.beans.MancheSQL;
import main.java.model.bdd.dao.beans.PartieSQL;
import main.java.model.bdd.dao.beans.TourSQL;
import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Pont;
import main.java.utils.Utils;

public class VueHistorique extends JFrame{
	
	private PartieSQL partie;
	
	private JPanel tableauPrincipal = new JPanel(); //Tableau de une colonne dans lequel on placera Un label (manche x tour x) par tour et un panel contenant les infos
	JScrollPane scrPane = new JScrollPane(tableauPrincipal);
	private JoueurSQL jRougeSQL;
	private JoueurSQL jVertSQL;
	int hauteurElement;
	int numeroManche;
	

	public VueHistorique(PartieSQL p) {
		this.setVisible(true);
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width / 3, screenSize.height * 9 / 10);
		this.setBackground(Color.BLACK);
		this.partie = p;
		this.init();
	}
	
	private void init() {
		tableauPrincipal.setBackground(Color.BLACK);
		
		DAOJoueur daoj = new DAOJoueur();
		
		
		DAOCouleur daoc = new DAOCouleur();
		CouleurSQL col = daoc.trouver(partie.getId());
		
		try {
			if(col.getCouleurJ1().equals(ECouleurJoueur.ROUGE)) {
				jRougeSQL = daoj.trouver(partie.getIdJoueur1());
				jVertSQL = daoj.trouver(partie.getIdJoueur2());
			}else {
				jRougeSQL = daoj.trouver(partie.getIdJoueur2());
				jVertSQL = daoj.trouver(partie.getIdJoueur1());
			}
		}catch(NullPointerException e) {
			jRougeSQL = daoj.trouver(partie.getIdJoueur2());
			jVertSQL = daoj.trouver(partie.getIdJoueur1());
		}
		
		tableauPrincipal.add(this.addTours());
		this.add(scrPane);
	}
	
	
	private JPanel addTours() {
		JPanel res = new JPanel(new GridBagLayout());
		hauteurElement = 0;
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		numeroManche=0;
		
		for(MancheSQL m : Utils.getManches(partie)) {
			numeroManche++;
			for(TourSQL t : Utils.getTours(m)) {
				hauteurElement++;
				Utils.setConstraints(0,0,0,hauteurElement,c);
				res.add(addTour(t),c);
			}
		}
		
		return res;
	}
	
	private JPanel addTour(TourSQL t) {
		JPanel panelTour = new JPanel(new GridLayout(0, 1));
		panelTour.setBackground(Color.BLACK);
		panelTour.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		DAOManche daoManche = new DAOManche();
		MancheSQL manche = daoManche.trouver(t.getIdManche());
		
		//Remplacer par les infos de la table correspondante
		JLabel headerTour = new JLabel(String.format("Manche %d - Tour %d", numeroManche, t.getNumeroTour()));
		headerTour.setForeground(Color.WHITE);
		headerTour.setFont(new Font("Arial", Font.PLAIN, 16));
		headerTour.setHorizontalAlignment(JLabel.CENTER);
		panelTour.add(headerTour);
		
		
		panelTour.add(this.getBilanEtPont(t));
		
		return panelTour;
	}
	
	private int getPositionJoueur(TourSQL t, JoueurSQL j) {
		int res = 0;
		if(partie.getIdJoueur1()==j.getId()) {
			res = t.getPositionJoueur1();
		}else {
			res = t.getPositionJoueur2();
		}
		return res;
	}
	
	private JPanel getEtatPont(TourSQL t) {
		JPanel panelPont = new JPanel(new GridLayout(2,Pont.TAILLE_PONT));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int l = screenSize.width/200;
		int h = screenSize.width/150;
		ImageIcon caseIcon = new ImageIcon();

		
		
		for(int i=0;i<Pont.TAILLE_PONT;i++) {
			
			if(i==getPositionJoueur(t,jRougeSQL)) {
				caseIcon = Utils.createImageIconColor(Color.RED, l, h);
			}else if(i==getPositionJoueur(t,jVertSQL)){
				caseIcon = Utils.createImageIconColor(Color.GREEN, l, h);
			}else if(i==t.getPositionMurFlammes()) {
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
			if(i<=numeroManche-1 || i>=Pont.TAILLE_PONT-numeroManche) {
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
	
	
	private JPanel getBilanEtPont(TourSQL t) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		JPanel bilanDesMises = new JPanel();
		JLabel titre = new JLabel();
		
		titre.setText("<html>Après tour : <br/></html>");
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
		
		DAOJoueur daoj = new DAOJoueur();
		
		
		
		bilanRouge.setText(getBilanJoueur(jRougeSQL, t));
		bilanVert.setText(getBilanJoueur(jVertSQL, t));
		
		bilanDesMises.add(bilanRouge);
		bilanDesMises.add(bilanVert);
		bilanDesMises.add(getEtatPont(t));
		
		return bilanDesMises;
	}
	
	private String getBilanJoueur(JoueurSQL j, TourSQL t) {
		String texteBilan;
		if(j.getId()==partie.getIdJoueur1()) {
			texteBilan = String.format("<html>Mise de %s : %s.<br/>", j.getNom(), ((Integer) t.getMiseJoueur1()).toString());
		}else {
			texteBilan = String.format("<html>Mise de %s : %s.<br/>", j.getNom(), ((Integer) t.getMiseJoueur2()).toString());
		}

		
		texteBilan+="Sorts :<br/>";
		for(CarteSQL c : Utils.getCartes(t)) {
			if(c.getIdJoueur() == j.getId()) {
				texteBilan+=String.format("%d - %s<br/>", c.getNumeroCarte(),this.getNomCarte(c.getNumeroCarte()));
			}
		}
		texteBilan+="</html>";
		return texteBilan;
	}
	
	private String getNomCarte(int numCarte) {
		String res="";
		switch(numCarte) {
		case 1 : 
			res="Mutisme";
			break;
		case 2 : 
			res="Clone";
			break;
		case 3 : 
			res="Larcin";
			break;
		case 4 : 
			res="Fin de Manche";
			break;
		case 5 : 
			res="Milieu";
			break;
		case 6 : 
			res="Recyclage";
			break;
		case 7 : 
			res="Boost Attaque";
			break;
		case 8 : 
			res="Double Dose";
			break;
		case 9 : 
			res="Qui Perd Gagne";
			break;
		case 10 : 
			res="Brasier";
			break;
		case 11 : 
			res="Résistance";
			break;
		case 12 : 
			res="Harpagon";
			break;
		case 13 : 
			res="Boost résèrve";
			break;
		case 14 : 
			res="Aspiration";
			break;
		default :
			res="erreur";
			break;
		}
		return res;
	}
	
}
