package main.java.controleur.jeu;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.java.vue.jeu.VueJeu;

public class ControleurCartes implements MouseListener{

	private JPanel panelMain;
	private List<Integer> cartesJouees;
	private VueJeu vue;
	
	public ControleurCartes(JPanel pm, List<Integer> cj, VueJeu vj) {
		this.panelMain=pm;
		this.vue=vj;
		this.cartesJouees=cj;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JLabel tmp = (JLabel) e.getComponent();
		tmp.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JLabel tmp = (JLabel) e.getComponent();
		Integer index = (Integer) panelMain.getComponentZOrder(tmp);
		if (cartesJouees.contains(index)) {
			tmp.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
		} else {
			tmp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e)){
			JLabel tmp = (JLabel) e.getComponent();
			Integer index = (Integer) panelMain.getComponentZOrder(tmp);
			if (!cartesJouees.contains(index)) {
				cartesJouees.add(index);
				vue.displayCartesJouees();
			} else {
				cartesJouees.remove(index);
				vue.displayCartesJouees();
				tmp.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
			}
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
}
