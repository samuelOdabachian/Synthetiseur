package appli;

/**
 * Classe main du synthetiseur.
 * 
 * @author Samuel Odabachian
 * @version 4/2/2020
 */


import javax.swing.UIManager;
import GUI.BarreMenus;
import GUI.GUISynthetiseur;
import java.lang.Thread;

public class DemarreSynthetiseur {

	//Consantes d'initialisation
	public static final int VOLUME_INITIALE =5;
	public static final int OCTAVE_INITIALE = 4;
	
	/**
	 * Configurer un look and feel compatible interplateforme.
	 */
	public static void setLookAndFeel() {
	   try {
	      UIManager.setLookAndFeel(
	         UIManager.getCrossPlatformLookAndFeelClassName());

	   } catch (Exception e) {
	      e.printStackTrace();
	   }
	}

	
	/**
	 * Le processuse main de toute le projet.
	 */
	public static void main (String [] args) {
		
		setLookAndFeel();
		
		//Initialiser l'objet un GUISynthetiseur
		GUISynthetiseur gui1 = new GUISynthetiseur(VOLUME_INITIALE,OCTAVE_INITIALE);
		
		Thread t = new Thread (gui1);
		t.start();
		BarreMenus barreMenu = new BarreMenus(gui1);	
	}
}
