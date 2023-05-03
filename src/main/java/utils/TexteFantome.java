package main.java.utils;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Permet d'afficher un texte fantôme en arrière-plan d'un champ textuel
 * 
 * <a href=
 * "https://stackoverflow.com/questions/10506789/how-to-display-faint-gray-ghost-text-in-a-jtextfield">Lien
 * StackOverflow</a>
 */
public class TexteFantome implements FocusListener, DocumentListener, PropertyChangeListener {
	private final JTextField textField;
	private boolean isEmpty;
	private Color ghostColor;
	private Color foregroundColor;
	private final String ghostText;

	public TexteFantome(final JTextField textField, String ghostText) {
		super();
		this.textField = textField;
		this.ghostText = ghostText;
		this.ghostColor = Color.DARK_GRAY;
		textField.addFocusListener(this);
		registerListeners();
		updateState();
		if (!this.textField.hasFocus()) {
			focusLost(null);
		}
	}

	public void delete() {
		unregisterListeners();
		textField.removeFocusListener(this);
	}

	private void registerListeners() {
		textField.getDocument().addDocumentListener(this);
		textField.addPropertyChangeListener("foreground", this);
	}

	private void unregisterListeners() {
		textField.getDocument().removeDocumentListener(this);
		textField.removePropertyChangeListener("foreground", this);
	}

	public Color getGhostColor() {
		return ghostColor;
	}

	public void setGhostColor(Color ghostColor) {
		this.ghostColor = ghostColor;
	}

	private void updateState() {
		isEmpty = textField.getText().length() == 0;
		foregroundColor = textField.getForeground();
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (isEmpty) {
			unregisterListeners();
			try {
				textField.setText("");
				textField.setForeground(foregroundColor);
			} finally {
				registerListeners();
			}
		}

	}

	@Override
	public void focusLost(FocusEvent e) {
		if (isEmpty) {
			unregisterListeners();
			try {
				textField.setText(ghostText);
				textField.setForeground(ghostColor);
			} finally {
				registerListeners();
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		updateState();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		updateState();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		updateState();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		updateState();
	}

}
