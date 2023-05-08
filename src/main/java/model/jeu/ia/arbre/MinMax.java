package main.java.model.jeu.ia.arbre;

import java.util.List;

import main.java.model.jeu.ia.IA;
import main.java.model.jeu.ia.IAEtatJeu;
import main.java.model.jeu.partie.Partie;

public class MinMax {

	private Arbre arbre;
	private IAEtatJeu ia;

	public MinMax(Partie p, IAEtatJeu ia) {
		this.ia = ia;
		this.arbre = new Arbre(new Noeud(p, ia, 0));
		construireArbre(arbre.getRacine(),0);
	}

	private void construireArbre(Noeud racine, int profondeur) {
		if (racine.getPartie().getPont().unSorcierEstTombe() || profondeur == 2) {
			return;
		}
		
		List<Partie> parties;
		if(profondeur % 2 == 0) {
			parties = racine.getPartie().getCoupsPossibles(ia);
		}else {
			if(racine.getPartie().getJoueurRouge() instanceof IA) {
				parties = racine.getPartie().getCoupsPossibles(racine.getPartie().getJoueurVert());
			}else {
				parties = racine.getPartie().getCoupsPossibles(racine.getPartie().getJoueurRouge());
			}
		}
		
		for (Partie p : racine.getPartie().getCoupsPossibles(ia)) {
			p.simulerTour();
			Noeud fils = new Noeud(p, ia, profondeur + 1);
			racine.ajouterFils(fils);
			construireArbre(fils,profondeur+1);
		}
	}

	public Partie meilleurCoup() {
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		int valeurMax = Integer.MIN_VALUE;
		Partie meilleurCoup = null;
		for (Noeud fils : arbre.getRacine().getFils()) {
			int score = alphabeta(fils, alpha, beta, false);
			if (score > valeurMax) {
				meilleurCoup = fils.getPartie();
				
			}
		}
		return meilleurCoup;
	}

	private int alphabeta(Noeud noeud, int alpha, int beta, boolean maximisant) {
		// Si le noeud est une feuille, renvoie son score
		if (noeud.getFils().isEmpty()) {
			return this.ia.evaluationTour(noeud.getPartie());
		}

		// Parcourt les noeuds fils
		if (maximisant) {
			int valeurMax = Integer.MIN_VALUE;
			for (Noeud fils : noeud.getFils()) {
				int valeur = alphabeta(fils, alpha, beta, false);
				valeurMax = Math.max(valeurMax, valeur);
				alpha = Math.max(alpha, valeurMax);
				if (beta <= alpha) {
					break; // Élagage alpha
				}
			}
			return valeurMax;
		}
		int valeurMin = Integer.MAX_VALUE;
		for (Noeud fils : noeud.getFils()) {
			int valeur = alphabeta(fils, alpha, beta, true);
			valeurMin = Math.min(valeurMin, valeur);
			beta = Math.min(beta, valeurMin);
			if (beta <= alpha) {
				break; // Élagage beta
			}
		}
		return valeurMin;
	}

}
