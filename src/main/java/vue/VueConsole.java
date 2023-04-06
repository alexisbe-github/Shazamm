package main.java.vue;

import java.util.Scanner;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;

public class VueConsole {
	
	private Partie partieActuelle;
	private boolean partieEnCours = true;

	public VueConsole(Partie p){
		this.partieActuelle = p;
		this.lancerJeu();
	}
	
	//Implémenter strategy
	private void lancerJeu() {
		while(partieEnCours) {	
			System.out.println(this.partieActuelle.getJoueur1());
			this.jouer(this.partieActuelle.getJoueur1());
			partieEnCours = false;
		}
	}
	
	
	
	
	
	private void printJeu() {
		//todo
	}
	
	private void jouer(Joueur j) {
		System.out.println("["+j.getCouleur()+"] TOUR DE "+j.getNom()+".");
		choixCarte(j);
		choixMise(j);
	}
	
	private void choixCarte(Joueur j) {
		System.out.println("["+j.getCouleur()+"] Quelle carte souhaitez-vous jouer ?");
		System.out.println(j.mainString("main"));
		boolean validInput = false;
		
		while(!validInput) {
			Scanner saisie = new Scanner(System.in);
			String choix = saisie.next();
			for(int i=0 ; i<j.getMainDuJoueur().size() ; i++) {
				if(j.getMainDuJoueur().get(i).getNom().toLowerCase().equals(choix.toLowerCase())) {
					j.getMainDuJoueur().get(i).lancerEffet(this.partieActuelle.getMancheCourante().getTourCourant());
					validInput = true;
					System.out.println(j.getMainDuJoueur().get(i)); // DEBUG
				}
			}
			if(!validInput) {
				System.out.println("carte incorrecte, veuillez ressaisir une mise valide :");
			}
		}
	}
	
	private void choixMise(Joueur j) {
		System.out.println("["+j.getCouleur()+"] Mana de "+j.getNom()+" : "+j.getManaActuel()+".");
		System.out.println("["+j.getCouleur()+"] "+j.getNom()+" peut saisir sa mise :");
		int choix = 0;
		boolean validInput = false;
		
		while(!validInput) {
			Scanner saisie = new Scanner(System.in);
			choix = saisie.nextInt();
			if(choix<=j.getManaActuel()&&choix>0) {
				this.partieActuelle.getMancheCourante().getTourCourant().setMiseJoueur(j, choix);
				System.out.println("Total mise de Mana : "+choix);
				validInput=true;
			}else {
				System.out.println("mise incorrecte, veuillez ressaisir une mise valide :");
			}
		}	
	}
	
}
