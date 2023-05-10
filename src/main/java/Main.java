package main.java;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import main.java.model.bdd.dao.Connexion;
import main.java.vue.VueLancement;

public class Main {

	public static void main(String[] args) {
		try { // Utilise l'apparence par défaut des applications système
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}

		// Lance le programme
		new VueLancement();

		// Ferme la connexion à la base de données lorsque le programme se termine
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				Connexion.getInstance().fermerConnexion();
			}
		}));
	}

}