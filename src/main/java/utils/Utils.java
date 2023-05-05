package main.java.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.Lock;

import javax.swing.ImageIcon;
import javax.swing.Timer;

/**
 * Classe contenant diverses fonctions utilitaires
 */

public class Utils {

	private Utils() {

	}

	/**
	 * Redimensionne un objet <code>ImageIcon</code> selon les paramètres spécifiés.
	 * 
	 * @param img     L'image à redimensionner
	 * @param largeur La nouvelle largeur
	 * @param hauteur La nouvelle hauteur
	 * @return L'image redimensionnée
	 */
	public static ImageIcon redimensionnerImage(ImageIcon img, int largeur, int hauteur) {
		Image image = img.getImage(); // Transformation en Image
		Image nvimg = image.getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH); // Redimensionnement
		return new ImageIcon(nvimg); // Transformation en ImageIcon
	}

	/**
	 * Réalise un fondu d'arrière-plan sur un composant Swing
	 * 
	 * @param composant    Le composant
	 * @param couleurCible La couleur cible
	 * @param intervalle   L'intervalle de temps entre chaque étape (en ms)
	 * @param nb_etapes    Le nombre d'étapes
	 */

	public static void fonduArrierePlan(Component composant, Color couleurCible, int intervalle, int nb_etapes) {

		final float[] INITIALE_RGB = composant.getBackground().getRGBColorComponents(null);
		final float[] CIBLE_RGB = couleurCible.getRGBColorComponents(null);
		final Timer timer = new Timer(intervalle, null);
		timer.addActionListener(new ActionListener() {
			int step = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				float progress = (float) step / (float) nb_etapes;
				float[] newRGB = new float[INITIALE_RGB.length];
				for (int i = 0; i < newRGB.length; i++) {
					newRGB[i] = INITIALE_RGB[i] + progress * (CIBLE_RGB[i] - INITIALE_RGB[i]);
				}
				composant.setBackground(new Color(newRGB[0], newRGB[1], newRGB[2]));
				step++;
				if (step > nb_etapes) {
					timer.stop();
					// Effectue le fondu inverse (retour à la couleur initiale)
					final Timer timerInverse = new Timer(intervalle, null);
					timerInverse.addActionListener(new ActionListener() {
						int step = 0;

						@Override
						public void actionPerformed(ActionEvent e) {
							float progress = (float) step / (float) nb_etapes;
							float[] newRGB = new float[INITIALE_RGB.length];
							for (int i = 0; i < newRGB.length; i++) {
								newRGB[i] = CIBLE_RGB[i] + progress * (INITIALE_RGB[i] - CIBLE_RGB[i]);
							}
							composant.setBackground(new Color(newRGB[0], newRGB[1], newRGB[2]));
							step++;
							if (step > nb_etapes) {
								timerInverse.stop();
								composant.setBackground(Color.WHITE);
							}
						}
					});
					timerInverse.start();
				}
			}
		});
		timer.start();
	}
}