package main.java;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import main.java.vue.VueLancement;

public class Main {

	public static void main(String[] args) {
		try { // Utilise l'apparence par défaut des applications système
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}
		new VueLancement();
	}
}