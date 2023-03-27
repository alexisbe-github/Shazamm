package main.java.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import main.resources.utils.EnvironmentVariablesUtils;

public class Connexion {

	private static Connexion instance = null;
	private Connection con;

	/**
	 * Se connecte à la base de données spécifiée dans le fichier <code>ENVIRONMENT.properties</code>.
	 * 
	 * @see main.resources.utils.EnvironmentVariablesUtils
	 */
	private Connexion() {

		try {

			this.con = DriverManager.getConnection(EnvironmentVariablesUtils.getBDDURL(),
					EnvironmentVariablesUtils.getBDDUSER(), EnvironmentVariablesUtils.getBDDMDP());

		} catch (SQLException e) {

		}

	}

	/**
	 * Singleton renvoyant la connexion.
	 * 
	 * @return La connexion
	 */
	public static Connexion getInstance() {
		if (instance == null) {
			instance = new Connexion();
		}
		return instance;
	}

	/**
	 * Ferme la connexion.
	 */
	public void fermerConnexion() {
		try {
			this.con.close();
		} catch (SQLException e) {

		}
	}

}
