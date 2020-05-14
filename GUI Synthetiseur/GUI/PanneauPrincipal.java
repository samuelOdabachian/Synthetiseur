package GUI;
/**
 * 
 * La classe PaneauPrincipale remplace
 * le contentPane.
 * 
 * @author Samuel odabachian
 * @version H2020
 *
 */
import java.awt.Container;

import GUI.PanneauClavier.MyKeyListener;
import audio.ModuleAudio;

public class PanneauPrincipal {

	private PanneauClavier claviers;
	private PanneauControle controle;
	private ModuleAudio audio;
	
	/**
	 * Constructeur du PanneauPrincipal.
	 * 
	 * @param volume est le volume inituale du synthetiseur.
	 * @param octave est l'octave initial du synthetiseur.
	 */
	public PanneauPrincipal(int volume,int octave) {
		audio = new ModuleAudio(volume); //page 4 plus explication voir
		claviers = new PanneauClavier(octave,audio);
		controle = new PanneauControle(octave,audio,claviers,volume);
	}
	
	
	
	/**
	 * Accesseur de l'octave courante.
	 */
	public int getOctave() {
		
		return controle.getOctave();
	}
	
	/**
	 * Accesseur du PanneauControle.
	 * 
	 * @return le PanneauControle.
	 */
	public PanneauControle getPanneauControle() {
		
		return controle;
	}
	
	/**
	 * Accesseur du PanneauClavier.
	 * 
	 * @return PanneauClavier.
	 */
	public PanneauClavier getPanneauClaviers() {
		
		return claviers;
	}
	
	/**
	 * L'utilisateur passera du menue pour changer le mode
	 * 
	 * @param mode est la mode voulue.
	 */
	public void setMode(int mode) {
	
		claviers.setMode(mode); 	
		controle.setMode(mode); 		
	}		
}
