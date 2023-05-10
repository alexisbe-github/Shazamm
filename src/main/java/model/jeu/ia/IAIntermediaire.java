package main.java.model.jeu.ia;

import java.util.List;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.carte.Carte;
import main.java.model.jeu.carte.Carte5;
import main.java.model.jeu.ia.arbre.Nash;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;
import main.java.vue.ILancementStrategy;

public class IAIntermediaire extends IAEtatJeu implements IA, ILancementStrategy {

	public IAIntermediaire(ECouleurJoueur couleur, String nom, String prenom, String avatar) {
		super(couleur, nom, prenom, avatar);
	}

	@Override
	public void jouerTour(Partie p) {
		Partie partieClone;
		try {
			partieClone = (Partie) p.clone();
			if (this.getCouleur().equals(ECouleurJoueur.ROUGE)) {
				partieClone.setStrategyVert(new SimulationStrategyLancementSort());
			} else {
				partieClone.setStrategyRouge(new SimulationStrategyLancementSort());
			}
			Nash determinationCoup = new Nash(partieClone, this);
			Partie meilleurCoup = determinationCoup.meilleurCoup();
			//System.out.println(this.evaluationTour(meilleurCoup));
			List<Carte> cartesJouees;
			int mise;
			if (meilleurCoup.getTour(p.getNombreManches(), p.getMancheCourante().getNumeroTourCourant()) != null) {
				if (this.getCouleur().equals(ECouleurJoueur.ROUGE)) {
					mise = meilleurCoup.getTour(p.getNombreManches(), p.getMancheCourante().getNumeroTourCourant())
							.getMiseJoueur(this);
					cartesJouees = meilleurCoup
							.getTour(p.getNombreManches(), p.getMancheCourante().getNumeroTourCourant())
							.getCartesJoueesRouge();
				} else {
					mise = meilleurCoup.getTour(p.getNombreManches(), p.getMancheCourante().getNumeroTourCourant())
							.getMiseJoueur(this);
					cartesJouees = meilleurCoup
							.getTour(p.getNombreManches(), p.getMancheCourante().getNumeroTourCourant())
							.getCartesJoueesVert();
				}
				for (Carte c : cartesJouees) {
					for (int i = 0; i < this.getMainDuJoueur().size(); i++) {
						if (c.getNumeroCarte() == this.getMainDuJoueur().get(i).getNumeroCarte()) {
							p.jouerCarte(this.getMainDuJoueur().get(i), this);
						}
					}
				}
				p.getMancheCourante().getTourCourant().setMiseJoueur(this, mise);
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

//		
//		// on génère une mise aléatoirement entre 1 et le mana de l'ordinateur
//		int mise = Utils.genererEntier(1, getManaActuel() + 1);
//
//		// on joue entre 0 et le nombre de cartes dans la main
//		int nbCartesAJouer = Utils.genererEntierAvecPoids(0, getMainDuJoueur().size());
//
//		for (int i = 0; i < nbCartesAJouer; i++) {
//			int index = Utils.genererEntier(0, getMainDuJoueur().size());
//			Carte carte = getMainDuJoueur().get(index);
//			p.jouerCarte(carte, this);
//		}
//		p.getMancheCourante().getTourCourant().setMiseJoueur(this, mise);
	}

	@Override
	public void lancerClone(Partie p, Tour tour, Joueur joueur) {
		// TODO Auto-generated method stub

	}

	@Override
	public void lancerRecyclage(Partie p, Tour tour, Joueur joueur) {
		// TODO Auto-generated method stub

	}

	@Override
	public void lancerLarcin(Partie p, Tour tour, Joueur joueur) {
		// TODO Auto-generated method stub

	}

}
