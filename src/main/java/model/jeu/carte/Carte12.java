package main.java.model.jeu.carte;

import main.java.model.jeu.ECouleurJoueur;
import main.java.model.jeu.Joueur;
import main.java.model.jeu.partie.Partie;

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
	public void lancerEffet(Joueur caster) { 
		//le fait que la mise soit gérée dans le tour rend pas terrible la, genre pour mettre a jour la reserve de mana ça risque d'être le bazar
		if (partie.getMancheCourante().getTourCourant().getDeplacementMur() > 0
				&& caster.getCouleur().equals(ECouleurJoueur.VERT)
				|| partie.getMancheCourante().getTourCourant().getDeplacementMur() < 0
						&& caster.getCouleur().equals(ECouleurJoueur.ROUGE)) {
			if(caster.getCouleur().equals(ECouleurJoueur.ROUGE)) {
				partie.getMancheCourante().getTourCourant().setMiseJoueurRouge(0);
			}else {
				partie.getMancheCourante().getTourCourant().setMiseJoueurVert(0);
			}
		}
	}

}