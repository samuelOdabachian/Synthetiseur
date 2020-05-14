package GUI;

/**
 * Classe principale qui herite du JFrame et qui possede
 * le paneauPrincipale qui est son contentPane.
 * 
 * @author Samuel Odabachian
 * @version 4/2/2020
 */

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class GUISynthetiseur extends JFrame implements Runnable{

	//Reference du PanneauPrincipal.
	private PanneauPrincipal paneau;
	
	/**
	 * Constructeur du GUISynthetiseur passe ses parametres au paneau principal.
	 * 
	 * @param volume est le volume initiale que le son va etre.
	 * @param octave est l'octave initial.
	 */
	public GUISynthetiseur(int volume, int octave) {
		
		paneau = new PanneauPrincipal(volume,octave);		
	}

    /**
     * Mutateur du mode.
     * 
     * @param mode est la mode voulu.
     */
	public void setMode(int mode) {
		
		paneau.setMode(mode);
	}

	/**
	 * Acesseur du PanneauPrincipal.
	 * 
	 * @return le paneau principale.
	 */
	public PanneauPrincipal getPanneauPrincipal() {
		
		return paneau;
	}
	
	@Override
	public void run() {
		
		
		//Les paramètres du cadre.
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setMinimumSize(new Dimension (600,300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//L'ajout du paneau controle sur le JFrame de se classe.
		add(paneau.getPanneauControle().getPan(),BorderLayout.NORTH);
		add(paneau.getPanneauClaviers());		
	}
}
