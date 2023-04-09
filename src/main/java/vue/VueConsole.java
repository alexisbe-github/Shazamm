package main.java.vue;

import java.util.Scanner;

import main.java.model.jeu.Joueur;
import main.java.model.jeu.carte.Carte;
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
			System.out.println("[PARTIE] MANCHE "+this.partieActuelle.getNombreManches()+" TOUR NUMERO "+this.partieActuelle.getMancheCourante().getNombreTours());
			this.printJeu();
			this.partieActuelle.getMancheCourante().getTourCourant().jouerTour(choixMise(this.partieActuelle.getJoueurRouge()), choixMise(this.partieActuelle.getJoueurVert()));
			this.partieActuelle.getMancheCourante().getTourCourant().jouerCarte(this.choixCarte(this.partieActuelle.getJoueurRouge()), this.partieActuelle.getJoueurRouge());
			this.partieActuelle.getMancheCourante().getTourCourant().jouerCarte(this.choixCarte(this.partieActuelle.getJoueurVert()), this.partieActuelle.getJoueurVert());
			this.partieActuelle.getMancheCourante().passerAuTourSuivant();
			//partieEnCours = false;
		}
	}
	
	
	
	
	
	private void printJeu() {
		System.out.println("[PARTIE] PONT : "+partieActuelle.getPont());
	}
	
	private void jouer(Joueur j) {
		System.out.println("["+j.getCouleur()+"] TOUR DE "+j.getNom()+".");
		
		choixCarte(j);
	}
	
	//scanner non close() ça pose soucis je sais pas trop pourquoi
	private Carte choixCarte(Joueur j) {
		System.out.println("["+j.getCouleur()+"] Quelle carte souhaitez-vous jouer ? (saisir le numéro)");
		System.out.println(j.mainString());
		boolean validInput = false;
		Scanner saisie = new Scanner(System.in);
		Carte res = null;
		
		while(!validInput) {
			int choix = saisie.nextInt();
			for(int i=0 ; i<j.getMainDuJoueur().size() ; i++) {
				if(j.getMainDuJoueur().get(i).getNumeroCarte()==choix) {
					j.getMainDuJoueur().get(i).lancerEffet(this.partieActuelle.getMancheCourante().getTourCourant());
					validInput = true;
					System.out.println(j.getMainDuJoueur().get(i)); // DEBUG
					res=j.getMainDuJoueur().get(i);
				}
			}
			if(!validInput) {
				System.out.println("carte incorrecte, veuillez ressaisir une mise valide :");
			}
		}
		
		return res;
	}
	
	//scanner non close() ça pose soucis je sais pas trop pourquoi
	private int choixMise(Joueur j) {
		System.out.println("["+j.getCouleur()+"] Mana de "+j.getNom()+" : "+j.getManaActuel()+".");
		System.out.println("["+j.getCouleur()+"] "+j.getNom()+" peut saisir sa mise :");
		int choix = 0;
		boolean validInput = false;
		Scanner saisie = new Scanner(System.in);
		
		while(!validInput) {
			choix = saisie.nextInt();
			if(choix<=j.getManaActuel()&&choix>0) {
				this.partieActuelle.getMancheCourante().getTourCourant().setMiseJoueur(j, choix);
				System.out.println("Total mise de Mana : "+choix);
				validInput=true;
			}else {
				System.out.println("mise incorrecte, veuillez ressaisir une mise valide :");
			}
		}	
		
		return choix;
	}
	
}
