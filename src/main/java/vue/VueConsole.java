package main.java.vue;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.partie.Partie;

public class VueConsole {
	
	Partie partieActuelle;
	boolean partieEnCours = true;

	public VueConsole(Partie p){
		this.partieActuelle = p;
		this.lancerJeu();
	}
	
	//Implémenter strategy
	private void lancerJeu() {
		while(partieEnCours) {
			//todo
			System.out.println(this.partieActuelle.getJoueurRouge());
			partieEnCours = false;
		}
	}
	
	private void printJeu() {
		//todo
	}
	
	private void choixCarte(ECouleurJoueur couleur) {
		System.out.println("Quelle carte souhaitez-vous jouer ?");
		System.out.println();
		//scan todo
	}
	
	private void choixMise(ECouleurJoueur couleur) {
		System.out.println("Quelle quantité de mana souhaitez-vous miser ?");
		//scan todo
	}
}
