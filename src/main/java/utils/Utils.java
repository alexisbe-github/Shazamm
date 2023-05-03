package main.java.utils;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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

}
