package main.java.vue.profil;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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

	public VueSelectionAvatars() {
		super();
		getContentPane().setLayout(new GridBagLayout());
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
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
			ImageIcon imageRedimensionnee = Utils.redimensionnerImage(avatar, 40, 40);
			JLabel label = new JLabel(imageRedimensionnee);
			panelAvatars.add(label);
			ImageIcon i = (ImageIcon) label.getIcon();
			/**
			 * 
			 * 
			 * 
			 * TODO Pourquoi c'est null ????
			 * 
			 * 
			 * 
			 */
			System.out.println(i.getDescription());
			label.addMouseListener(new ControleurSelectionAvatar(this, label, null));
		}
		panelBouton.add(new JButton("Sélectionner"));
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.9;
		
		this.add(panelAvatars, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0.1;
		c.insets = new Insets(10, 0, 10, 0);
		
		this.add(panelBouton, c);
	}
	
	/**
	 * @return La liste des avatars
	 */
	public List<ImageIcon> getAvatars() {
		return this.avatars;
	}
	
	/**
	 * @return Les labels contenant les avatars
	 */
	public Component[] getLabelsAvatars() {
		return this.panelAvatars.getComponents();
	}
	
	public static void main(String[] args) {
		VueSelectionAvatars v = new VueSelectionAvatars();
	}
	
}
