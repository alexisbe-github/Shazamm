package main.java.vue.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import main.java.model.bdd.dao.DAOJoueur;
import main.java.model.bdd.dao.beans.JoueurSQL;
import main.java.model.bdd.dao.beans.PartieSQL;
import main.java.utils.Utils;
import main.java.vue.jeu.VueHistorique;

public class VueHistoriqueGeneral extends JFrame{

	private JPanel tableauPrincipal;
	
	public VueHistoriqueGeneral() {
		this.setVisible(true);
		this.setBackground(Color.BLACK);
		this.tableauPrincipal = new JPanel();
		
		this.init();
		
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width / 3, screenSize.height * 9 / 10);
	}
	
	private void init() {
		
	}
	
	private void addParties() {
		for(PartieSQL p : Utils.getParties()) {
			addPartie(p);
		}
	}
	
	private void addPartie(PartieSQL partie) {
		JPanel tableauPartie = new JPanel();
	
		
		this.add(getResultats(partie));
		
		tableauPartie.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new VueHistorique(partie);		
			}
			@Override
			public void mousePressed(MouseEvent e) {
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
	}
	
	private JSplitPane getResultats(PartieSQL partie) {
		JSplitPane panel = new JSplitPane();
		panel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		
		DAOJoueur daoj = new DAOJoueur();
		
		JPanel resRouge = this.getResultatJoueur(partie, daoj.trouver(partie.getIdJoueur1())); //REMPLACER PAR JOUEUR ROUGE/JOUEUR VERT
		JPanel resVert = this.getResultatJoueur(partie, daoj.trouver(partie.getIdJoueur2()));
		
		panel.setLeftComponent(resRouge);
		panel.setRightComponent(resVert);
		return panel;
	}
	
	private JPanel getResultatJoueur(PartieSQL p, JoueurSQL j) {
		JPanel res = new JPanel();
		
		JLabel logoJoueur = new JLabel();
		ImageIcon iconLogoJoueur = j.getAvatar();
		if(iconLogoJoueur.getIconWidth()<=0) {
			iconLogoJoueur = new ImageIcon("src/main/resources/images/icone-profil-joueur.png");
		}
		logoJoueur.setIcon(Utils.redimensionnerImage(iconLogoJoueur,this.getHeight()/14));
		JLabel nomJoueur = new JLabel(String.format("%s %s", j.getPrenom(),j.getNom()));
		nomJoueur.setFont(new Font("Verdana", Font.PLAIN, this.getWidth()/60));
		res.add(logoJoueur);
		res.add(nomJoueur);
		
		return res;
		
	}
	
}
