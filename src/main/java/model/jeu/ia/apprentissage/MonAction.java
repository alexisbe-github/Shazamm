package main.java.model.jeu.ia.apprentissage;

public enum MonAction {

	 JOUER_CARTE1(0),
	 JOUER_CARTE2(1),
	 JOUER_CARTE3(2),
	 JOUER_CARTE4(3),
	 JOUER_CARTE5(4),
	 JOUER_CARTE6(5),
	 JOUER_CARTE7(6),
	 JOUER_CARTE8(7),
	 JOUER_CARTE9(8),
	 JOUER_CARTE10(9),
	 JOUER_CARTE11(10),
	 JOUER_CARTE12(11),
	 JOUER_CARTE13(12),
	 JOUER_CARTE14(13),
	 MISER(14);

	    private int index;

	    MonAction(int index) {
	        this.index = index;
	    }

	    public int getIndex() {
	        return index;
	    }
}
