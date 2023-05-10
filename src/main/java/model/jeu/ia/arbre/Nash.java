package main.java.model.jeu.ia.arbre;

import java.util.List;

import main.java.model.jeu.ia.IAEtatJeu;
import main.java.model.jeu.partie.Partie;

public class Nash {

	private Arbre arbre;
	private IAEtatJeu ia;

	public Nash(Partie p, IAEtatJeu ia) {
		this.ia = ia;
		this.arbre = new Arbre(new Noeud(p, ia, 0));
		construireArbre(arbre.getRacine(), 0);
	}

	private void construireArbre(Noeud racine, int profondeur) {
		if (racine.getPartie().getPont().unSorcierEstTombe() || profondeur == 1) {
			return;
		}

		List<Partie> parties;

		// on génère les coups de l'IA
		parties = racine.getPartie().getCoupsPossibles(ia);

		for (Partie p : parties) {
			Noeud fils = new Noeud(p, ia, profondeur + 1);
			if (p != null) {
				p.simulerTour();
			}
			racine.ajouterFils(fils);
			construireArbre(fils, profondeur + 1);
		}
	}

	public Partie meilleurCoup() {
		int alpha = Integer.MIN_VALUE;
		int valeurMax = Integer.MIN_VALUE;
		Partie meilleurCoup = arbre.getRacine().getPartie();
		for (Noeud fils : arbre.getRacine().getFils()) {
			int score = alpha(fils, alpha, 1);
			if (score >= valeurMax) {
				meilleurCoup = fils.getPartie();
			}
		}
		return meilleurCoup;
	}

	private int alpha(Noeud noeud, int alpha, int profondeur) {
		// Si le noeud est une feuille, renvoie son score
		if (noeud.getFils().isEmpty() || profondeur == 0) {
			return this.ia.evaluationTour(noeud.getPartie());
		}

		// Parcourt les noeuds fils
		for (Noeud fils : noeud.getFils()) {
			int valeur = alpha(fils, alpha, profondeur - 1);
			alpha = Math.max(alpha, valeur);
		}

		return alpha;
	}

}
