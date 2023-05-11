package main.java.vue.classement;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableRowSorter;

import main.java.model.bdd.Profil;
import main.java.model.bdd.dao.Connexion;
import main.java.model.bdd.dao.DAOJoueur;
import main.java.vue.menu.VueMenu;

public class VueClassement extends JFrame {

	private JScrollPane scrollPane = new JScrollPane();
	private JTable tableau = new JTable();
	private int nbJoueursClassement = 5;
	
	public VueClassement() {
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		
		majTableau(getMeilleursJoueurs(nbJoueursClassement));
		getContentPane().add(scrollPane);
		tableau.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		
		pack();
		
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				new VueMenu();
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
	}
	
	private static List<Long> getIdMeilleurs(int nbJ) {
		List<Long> liste = new ArrayList<>();
		Connection con = Connexion.getInstance().getConnexion();
		try (PreparedStatement pstmt = con.prepareStatement("SELECT id FROM joueur ORDER BY nb_parties_gagnees DESC, nom ASC LIMIT ?")) {
			pstmt.setInt(1, nbJ);
			pstmt.execute();
			try (ResultSet rs = pstmt.getResultSet()) {
				while (rs.next()) {
					long id = rs.getLong(1);
					liste.add(id);
				}
			}
		} catch (SQLException e) {
			
		}
		return liste;
	}
	
	private static List<Profil> getMeilleursJoueurs(int nbJ) {
		List<Long> listeId = getIdMeilleurs(nbJ);
		List<Profil> res = new ArrayList<>();
		DAOJoueur dao = new DAOJoueur();
		for (long id : listeId) {
			Profil p = new Profil(dao.trouver(id));
			res.add(p);
		}
		return res;
	}
	
	private void majTableau(List<Profil> profils) {
		Object[][] donnees = new Object[profils.size()][2];
		for (int i = 0; i < profils.size(); i++) {
			donnees[i][0] = String.format("%s %s", profils.get(i).getPrenom(), profils.get(i).getNom());
			donnees[i][1] = profils.get(i).getNbPartiesGagnees();
		}
		String[] nomsColonnes = {"Nom", "Nombre de victoires"};
		ModeleTableau modele = new ModeleTableau(donnees, nomsColonnes);
		TableRowSorter<ModeleTableau> trieur = new TableRowSorter<>(modele);
		FormateurTableau formateur = new FormateurTableau(tableau, new DecimalFormat("###,###"));
		tableau.setModel(modele);
		tableau.setRowSorter(trieur);
		tableau.setDefaultRenderer(Object.class, formateur);
		
		tableau.setColumnSelectionAllowed(false);
		tableau.setRowSelectionAllowed(false);
		tableau.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableau.setFillsViewportHeight(true);
		tableau.setColumnSelectionAllowed(true);
		tableau.setCellSelectionEnabled(true);
		tableau.setSelectionBackground(new Color(193, 228, 255));
		tableau.setSelectionForeground(new Color(0, 30, 255));
		tableau.setShowGrid(false);
		tableau.setShowHorizontalLines(true);
		tableau.setGridColor(Color.BLUE);
		
		scrollPane = new JScrollPane(tableau);
	}
	
}
