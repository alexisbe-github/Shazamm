package main.java.model.jeu.carte;

import java.util.HashMap;
import java.util.Map;

public class CarteFactory {

	private Map<Integer, Class<? extends Carte>> carte;

	public CarteFactory() {
		carte = new HashMap<Integer, Class<? extends Carte>>();
		carte.put(1, Carte1.class);
		carte.put(2, Carte2.class);
		carte.put(3, Carte3.class);
		carte.put(4, Carte4.class);
		carte.put(5, Carte5.class);
		carte.put(6, Carte6.class);
		carte.put(7, Carte7.class);
		carte.put(8, Carte8.class);
		carte.put(9, Carte9.class);
		carte.put(10, Carte10.class);
		carte.put(11, Carte11.class);
		carte.put(12, Carte12.class);
		carte.put(13, Carte13.class);
		carte.put(14, Carte14.class);
	}

	public Carte createCarte(Integer numeroCarte) {
		Class<? extends Carte> typeCarte = carte.get(numeroCarte); // on récupère la classe correspondante au numéro de
																	// la carte

		try {
			return typeCarte.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Erreur de création de carte", e);
		}
	}

}
