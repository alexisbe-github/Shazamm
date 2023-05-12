package main.java.vue.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import main.java.model.bdd.dao.DAOJoueur;
import main.java.model.bdd.dao.beans.JoueurSQL;
import main.java.model.bdd.dao.beans.PartieSQL;
import main.java.utils.Utils;
import main.java.vue.jeu.VueHistorique;

public class VueHistoriqueGeneral extends JFrame{

	JPanel mainPanel = new JPanel();
	JScrollPane scrPane = new JScrollPane(mainPanel);
	int hauteurElement = 0;
	
	public VueHistoriqueGeneral() {
		
		//Intialisation taille et fond
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width / 3, screenSize.height * 9 / 10);
        this.setVisible(true);
		this.setBackground(Color.BLACK);
		this.init();
		add(scrPane);
	}
	
	private void init() {
		mainPanel.add(addParties());
		mainPanel.setBackground(Color.BLACK);
	}
	
	/**
	 * 
	 * @return JPanel contenant toutes les parties
	 */
	private JPanel addParties() {
		//GridBagLayout : permet d'aligner mes éléments au centre à la verticale malgré le JScrollPane
		
		hauteurElement = 0;
		GridBagConstraints c = new GridBagConstraints();
		JPanel res = new JPanel(new GridBagLayout());
		
		JLabel titre = new JLabel("HISTORIQUE DES PARTIES");
		titre.setFont(new Font("Verdana", Font.PLAIN, 26));
		titre.setForeground(Color.WHITE);
		res.add(titre);
		
		res.setBackground(Color.BLACK);
		//pour toutes parties je l'ajoute a leur hauteur respective
		for(PartieSQL p : Utils.getParties()) {
			hauteurElement++;
			Utils.setConstraints(0,0,0,hauteurElement,c);
			res.add(addPartie(p),c);
		}
		return res;
	}
	
	/**
	 * 
	 * @param partie à ajouter
	 * @return JPanel cliquable de la partie
	 */
	private JPanel addPartie(PartieSQL partie) {
		JPanel panelPartie = new JPanel();
		panelPartie.setBackground(Color.BLACK);
		panelPartie.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		panelPartie.add(getResultats(partie));
		
		return panelPartie;
	}
	
	/**
	 * 
	 * @param partie : partie dont on cherche le resultat
	 * @return splitpanel contenant les resultats des joueurs
	 */
	private JSplitPane getResultats(PartieSQL partie) {
		JSplitPane panel = new JSplitPane();
		panel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		panel.setDividerSize(6); // La barre séparatrice fait 3px de large
		
		DAOJoueur daoj = new DAOJoueur();
		
		JPanel resj1 = this.getResultatJoueur(partie, daoj.trouver(partie.getIdJoueur1())); //REMPLACER PAR JOUEUR ROUGE/JOUEUR VERT
		JPanel resj2 = this.getResultatJoueur(partie, daoj.trouver(partie.getIdJoueur2()));
		
		panel.setLeftComponent(resj1);
		panel.setRightComponent(resj2);
		panel.setBackground(Color.BLACK);
		panel.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				new VueHistorique(partie);	
				System.out.println("hello");
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}	
		});
		return panel;
	}
	
	/**
	 * 
	 * @param p : partie dont on cherche les resultats
	 * @param j : joueur dont on cherche les resultats
	 * @return un panel contenant le logo et le nom. Fond jaune si gagnant
	 */
	private JPanel getResultatJoueur(PartieSQL p, JoueurSQL j) {
		JPanel res = new JPanel();
		
		//Si gagnant alors fond jaune
		if(p.getIdVainqueur()==j.getId()) {
			res.setBackground(Color.YELLOW);
		}else {
			res.setBackground(Color.WHITE);
		}
		
		JLabel logoJoueur = new JLabel();
		ImageIcon iconLogoJoueur = j.getAvatar();
		//Je vérifie si l'icon est bon, si non je le remplace par un icon basique
		if(iconLogoJoueur.getIconWidth()<=0) {
			iconLogoJoueur = new ImageIcon("src/main/resources/images/icone-profil-joueur.png");
		}
		logoJoueur.setIcon(Utils.redimensionnerImage(iconLogoJoueur,this.getHeight()/14));
		//Je fais une chaîne contenant nom et prenom du joueur
		JLabel nomJoueur = new JLabel(String.format("%s %s", j.getPrenom(),j.getNom()));
		nomJoueur.setFont(new Font("Verdana", Font.PLAIN, this.getWidth()/60));
		
		res.add(logoJoueur);
		res.add(nomJoueur);
		
		return res;
		
	}
	
}
