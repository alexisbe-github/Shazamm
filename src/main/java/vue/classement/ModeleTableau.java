package main.java.vue.classement;

import javax.swing.table.AbstractTableModel;

/**
 * Modèle du tableau
 */

public class ModeleTableau extends AbstractTableModel {

	/**
	 * Les données du tableau
	 */
	private Object[][] data;
	/**
	 * Les titres des colonnes
	 */
	private String[] title;

	/**
	 * Crée le modèle du <code>JTable</code>.
	 * 
	 * @param data  Données du tableau
	 * @param title Titres des colonnes
	 */

	public ModeleTableau(Object[][] data, String[] title) {
		this.data = data;
		this.title = title;
	}

	/**
	 * Retourne le nom de la colonne.
	 */

	@Override
	public String getColumnName(int col) {
		return this.title[col];
	}

	/**
	 * Retourne le nombre de colonnes.
	 */

	@Override
	public int getColumnCount() {
		return this.title.length;
	}

	/**
	 * Retourne le nombre de lignes.
	 */

	@Override
	public int getRowCount() {
		return this.data.length;
	}

	/**
	 * Retourne l'objet à l'intersection de la ligne et de la colonne spécifiées.
	 */

	@Override
	public Object getValueAt(int row, int col) {
		return this.data[row][col];
	}

	/**
	 * Modifie l'objet à l'intersection de la ligne et de la colonne spécifiées.
	 * 
	 */

	@Override
	public void setValueAt(Object value, int row, int col) {
		this.data[row][col] = value;
	}

	/**
	 * 
	 * Renvoie la classe de la colonne spécifiée.
	 */

	@Override
	public Class<?> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

}
