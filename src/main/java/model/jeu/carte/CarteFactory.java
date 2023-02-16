package main.java.model.jeu.carte;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.Partie;

public class CarteFactory {
	
	public Carte createCarte(String nomCarte, Partie p, Joueur j) {
		if(nomCarte.equals("Mutisme")) {
			return new Carte1(nomCarte, p, j, 1);
		}else if(nomCarte.equals("Clone")) {
			return new Carte2(nomCarte, p, j, 2);
		}else if(nomCarte.equals("Larcin")) {
			return null;
		}else if(nomCarte.equals("Fin de Manche")) {
			return null;
		}else if(nomCarte.equals("Milieu")) {
			return null;
		}else if(nomCarte.equals("Recyclage")) {
			return null;
		}else if(nomCarte.equals("Boost Attaque")) {
			return null;
		}else if(nomCarte.equals("Double dose")) {
			return null;
		}else if(nomCarte.equals("Qui Perd Gagne")) {
			return null;
		}else if(nomCarte.equals("Braisier")) {
			return null;
		}else if(nomCarte.equals("Résistance")) {
			return null;
		}else if(nomCarte.equals("Harpagon")) {
			return null;
		}else if(nomCarte.equals("Boost réserve")) {
			return null;
		}else if(nomCarte.equals("Aspiration")) {
			return null;
		}else {
			return null;
		}
	}
	
}
