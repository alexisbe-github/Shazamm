package main.java.model.jeu.carte;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;
import main.java.model.jeu.partie.Tour;

public class Carte12 extends Carte {

	private final String NOM_CARTE = "Harpagon";
	private final String TEXTE_CARTE = "Si je perds ce tour (i.e. si le mur de feu avance effectivement vers"
			+ " moi), ma mise n’est pas retranchée de ma réserve de Mana";
	private final int NUMERO_CARTE = 12;

	public Carte12(Partie p, Joueur j) {
		super.partie = p;
		super.joueur = j;
		super.numeroCarte = NUMERO_CARTE;
		super.nom = NOM_CARTE;
		super.description = TEXTE_CARTE;
	}

	@Override
	public void lancerEffet(Tour tour) {
		// Si le mur avance vers le détenteur de la carte
		boolean joueurRougePerdLeTour = tour.getDeplacementMur() > 0 && joueur.getCouleur().equals(ECouleurJoueur.VERT);
		boolean joueurVertPerdLeTour = tour.getDeplacementMur() < 0 && joueur.getCouleur().equals(ECouleurJoueur.ROUGE);
		if (joueurRougePerdLeTour || joueurVertPerdLeTour) {
			// Alors on ne lui retire pas sa mise
			tour.changerMise(joueur, 0);
		}
	}

}
