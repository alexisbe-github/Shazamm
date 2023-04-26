package main.java.exceptions;

/**
 * Exception levée lorsque l'on tente d'accéder à une position inaccessible dans
 * la grille du plateau du jeu.
 */
public class PositionInaccessibleException extends Throwable {

	/**
	 * Lève une exception de type <code>PositionInaccessibleException</code>.
	 */
	public PositionInaccessibleException() {
		super("Position inaccessible !");
	}

	/**
	 * Lève une exception de type <code>PositionInaccessibleException</code> avec le
	 * message spécifié.
	 * 
	 * @param message Le message d'erreur
	 */
	public PositionInaccessibleException(String message) {
		super(message);
	}

}