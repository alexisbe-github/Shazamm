package main.java.model.jeu.carte.factory;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.carte.Carte;
import main.java.model.jeu.carte.Carte1;
import main.java.model.jeu.carte.Carte10;
import main.java.model.jeu.carte.Carte11;
import main.java.model.jeu.carte.Carte12;
import main.java.model.jeu.carte.Carte13;
import main.java.model.jeu.carte.Carte14;
import main.java.model.jeu.carte.Carte2;
import main.java.model.jeu.carte.Carte3;
import main.java.model.jeu.carte.Carte4;
import main.java.model.jeu.carte.Carte5;
import main.java.model.jeu.carte.Carte6;
import main.java.model.jeu.carte.Carte7;
import main.java.model.jeu.carte.Carte8;
import main.java.model.jeu.carte.Carte9;
import main.java.model.jeu.partie.Partie;

public class CarteFactory {

	public static Carte creerCarte(int numeroCarte, Partie p, Joueur j) {
		switch (numeroCarte) {
		case 1:
			return new Carte1(p, j);
		case 2:
			return new Carte2(p, j);
		case 3:
			return new Carte3(p, j);
		case 4:
			return new Carte4(p, j);
		case 5:
			return new Carte5(p, j);
		case 6:
			return new Carte6(p, j);
		case 7:
			return new Carte7(p, j);
		case 8:
			return new Carte8(p, j);
		case 9:
			return new Carte9(p, j);
		case 10:
			return new Carte10(p, j);
		case 11:
			return new Carte11(p, j);
		case 12:
			return new Carte12(p, j);
		case 13:
			return new Carte13(p, j);
		case 14:
			return new Carte14(p, j);
		default:
			return null;
		}
	}

}
