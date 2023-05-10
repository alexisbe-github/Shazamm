package main.java.vue.profil;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.controleur.menu.ControleurSelectionAvatar;
import main.java.utils.Utils;

public class VueSelectionAvatars extends JFrame {
	
	private List<ImageIcon> avatars = new ArrayList<>();
	private JPanel panelAvatars = new JPanel(), panelBouton = new JPanel();
	private final JButton bouton = new JButton("Sélectionner");
	private String cheminImageCourante;
	private ControleurSelectionAvatar controleurActif;
	private boolean fenetreActive = true;

	public VueSelectionAvatars() {
		super();
		getContentPane().setLayout(new GridBagLayout());
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		init();
		pack();
	}

	private void init() {
		panelAvatars.setLayout(new GridLayout(0, 10, 10, 10));
		
		File dossier = new File("src/main/resources/avatars");
		for (File avatar : dossier.listFiles()) {
			ImageIcon icone = new ImageIcon(avatar.getPath());
			icone.setDescription(avatar.getPath());
			avatars.add(icone);
		}
		
		for (ImageIcon avatar : avatars) {
			String chemin = avatar.getDescription().replace('\\', '/');
			ImageIcon imageRedimensionnee = Utils.redimensionnerImage(avatar, 40, 40);
			imageRedimensionnee.setDescription(chemin);
			JLabel label = new JLabel(imageRedimensionnee);
			panelAvatars.add(label);
			label.addMouseListener(new ControleurSelectionAvatar(this, label, imageRedimensionnee));
		}
		
		this.bouton.setEnabled(false);
		this.bouton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setCheminImageCourante(controleurActif.getCheminImage());
				fenetreActive = false;
				dispose();
			}
		});
		panelBouton.add(this.bouton);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.9;
		c.insets = new Insets(10, 5, 10, 5);
		
		this.add(panelAvatars, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0.1;
		
		this.add(panelBouton, c);
	}
	
	/**
	 * @return La liste des avatars
	 */
	public List<ImageIcon> getAvatars() {
		return this.avatars;
	}
	
	/**
	 * @return Les labels
	 */
	public List<JLabel> getLabels() {
		List<JLabel> liste = new ArrayList<>();
		for (Component c : this.panelAvatars.getComponents()) {
			if (c instanceof JLabel) {
				JLabel label = (JLabel) c;
				liste.add(label);
			}
		}
		return liste;
	}
	
	/**
	 * @return Le bouton
	 */
	public JButton getBouton() {
		return this.bouton;
	}
	
	/**
	 * Met à jour le contrôleur actif
	 *
	 * @param c Le contrôleur actif
	 */
	public void setControleurActif(ControleurSelectionAvatar c) {
		this.controleurActif = c;
	}

	/**
	 * @return the cheminImageCourante
	 */
	public String getCheminImageCourante() {
		return cheminImageCourante;
	}

	/**
	 * @param cheminImageCourante the cheminImageCourante to set
	 */
	public void setCheminImageCourante(String cheminImageCourante) {
		this.cheminImageCourante = cheminImageCourante;
	}
	
	/**
	 * @return <code>true</code> si la fenêtre est active, <code>false</code> sinon
	 */
	public boolean isFenetreActive() {
		return this.fenetreActive;
	}
	
}
