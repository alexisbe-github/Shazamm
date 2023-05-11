package main.java.vue.classement;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Formateur du tableau
 */
public class FormateurTableau extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 6948859580940797184L;

	private JTable tableau;
	private DecimalFormat format;
	private Border bordure = BorderFactory.createDashedBorder(Color.BLACK, 3, 2);
	private Color arriereplan = Color.WHITE, premierplan = Color.BLACK;

	/**
	 * Construit le formateur de la fenêtre
	 * 
	 * @param tableau Le tableau à formater
	 * @param format  Le formateur de données
	 */
	public FormateurTableau(JTable tableau, DecimalFormat format) {
		this.tableau = tableau;
		this.format = format;
	}

	/**
	 * Construit le formateur de la fenêtre
	 * 
	 * @param tableau Le tableau à formater
	 * @param format  Le formateur de données
	 * @param bordure La bordure des cellules
	 */
	public FormateurTableau(JTable tableau, DecimalFormat format, Border bordure) {
		this.tableau = tableau;
		this.format = format;
		this.bordure = bordure;
	}

	@Override
	protected void setValue(Object v) {
		if (v instanceof Integer) {
			int val = (Integer) v;
			Double valeur = val * 1.0;
			super.setValue(format.format(valeur));
		} else
			super.setValue(v);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		JLabel cellule = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (isSelected) {
			cellule.setBackground(Color.LIGHT_GRAY);
			cellule.setForeground(Color.BLACK);
			cellule.setFont(new Font(getFont().getName(), Font.BOLD, 14));
			cellule.setBorder(bordure);
		}
		else {
			cellule.setBackground(arriereplan);
			cellule.setForeground(premierplan);
		}

		return cellule;

	}

	/**
	 * Met à jour la bordure des cellules.
	 * 
	 * @param bordure La bordure
	 */
	public void setBordureCellules(Border bordure) {
		this.bordure = bordure;
	}

	/**
	 * Met à jour la couleur d'arrière-plan du tableau.
	 * 
	 * @param arrplan La couleur d'arrière-plan
	 */
	public void setArrierePlan(Color arrplan) {
		this.arriereplan = arrplan;
	}

	/**
	 * Met à jour la couleur du texte du tableau.
	 * 
	 * @param avplan La couleur d'avant-plan
	 */
	public void setAvantPlan(Color avplan) {
		this.premierplan = avplan;
	}
}
