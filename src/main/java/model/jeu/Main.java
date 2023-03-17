package main.java.model.jeu;

import main.java.model.jeu.partie.Partie;

public class Main {

	public static void main(String[] args) {
		Joueur joueurRouge = new Joueur(ECouleurJoueur.ROUGE,"Pop","Simoké","blabla");
		Joueur joueurVert = new Joueur(ECouleurJoueur.VERT,"Sorcier","Vert","blabla");
		Partie p = new Partie(joueurRouge,joueurVert);
		
	}
}
