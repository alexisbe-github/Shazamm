package main.java.vue.jeu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.Pont;
import main.java.model.jeu.partie.Partie;

/**
 * La fenêtre qui affiche les éléments du modèle sous forme de composants dans
 * une interface graphique.
 */
public class VueJeu extends JFrame {

	private Joueur joueur;
	private Partie partie;
	private JPanel panelPont;
	private JLabel logo = new JLabel();

	/**
	 * Construit un objet <code>Fenetre</code> avec le titre spécifié, qui
	 * correspond à l'interface graphique affichant le jeu.
	 */
	public VueJeu(Joueur joueur, Partie partie) {
		setVisible(true); // Rend la fenêtre visible
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Quitte le programme quand on ferme la fenêtre
		setLocationRelativeTo(null); // Centre la fenêtre par rapport à l'écran
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width / 2, (screenSize.height * 9) / 10);
		setResizable(true);
		
		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints(); // Les contraintes de positionnement des composants
		c.insets = new Insets(5, 10, 5, 10); // Marge autour des éléments en pixels
		c.fill = GridBagConstraints.HORIZONTAL;
		
		logo.setBounds(this.getWidth() / 2 - 201, 0, 402, 100);
		logo.setIcon(new ImageIcon("src/main/resources/logo_shazamm.gif"));
		
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		getContentPane().add(logo, c);
		
		panelPont = new JPanel();
		panelPont.setBounds(0,this.getHeight()*2/10,this.getWidth(),this.getHeight()*2/10);
		panelPont.setLayout(new GridLayout(1, Pont.TAILLE_PONT, 0, 0));
		panelPont.setBackground(Color.BLACK);
		paintPont();
		
		c.weightx = 1;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		getContentPane().add(panelPont, c);

	}
	
	public void paintPont() {
		for(int i=0;i<Pont.TAILLE_PONT;i++) {
			JLabel tmp = new JLabel();
			String source = "src/main/resources/pont/pont_";
			if(i<9) source += "0";
			source += (i+1) + ".gif";
			tmp.setIcon(new ImageIcon(source));
			panelPont.add(tmp);
			
			panelPont.addComponentListener(new ComponentAdapter() {
			    @Override
			    public void componentResized(ComponentEvent e) {
			        int panelWidth = panelPont.getWidth();
			        int panelHeight = panelPont.getHeight();
			        int imageWidth = tmp.getIcon().getIconWidth();
			        int imageHeight = tmp.getIcon().getIconHeight();
			        int imageCount = panelPont.getComponentCount();

			        int marginWidth = (panelWidth - imageCount * imageWidth) / 2;
			        int marginHeight = (panelHeight - imageHeight) / 2;

			        for (int i = 0; i < imageCount; i++) {
			            JLabel imageLabel = (JLabel) panelPont.getComponent(i);
			            imageLabel.setBounds(marginWidth + i * imageWidth, marginHeight, imageWidth, imageHeight);
			        }
			    }
			});

		}
	}
}