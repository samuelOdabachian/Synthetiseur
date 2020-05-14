package GUI;
/**
 * Classe ou les caracteristiques des touches clavier sont 
 * configurees.
 * 
 * @author Samuel Odabachian
 * @version 4/2/2020
 */

import javax.swing.JLabel;
import javax.swing.JPanel;
import audio.AudioConstantes;
import audio.ModuleAudio;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.String;


/*
 * Stratégie : 
 * 
 * On utilise un  tableau de ToucheClavier pour pouvoir utiliser une boucle
 * lorsque pour la configuration et l'ajout des panneau et ecouteur.
 * 
 * La classe qui implémente MouseListener est déclarée 
 * en classe interne.
 * 
 **/

public class PanneauClavier extends JPanel{
	
	//Touche du clavier max 13 touches
	//(nombre notes total + 1er note prochain octave)
	public static final int NOMBRE_TOUCHES_MAX = 12; 
	
	//class qui s'occupe des sons
	private ModuleAudio audio;
	//Octave Courant choisis par l'utilisateur
	private int octaveCourant;
	//Le mode choisis par l'utilisateur
	private static int modeCourant;
	
	//tableau des 13 touches claviers
	private ToucheClavier [] tabTouches;
	
	// Pour permettre d'enregistrer par souris
	private PaneauEnregistrement enregistreur;
	
	
	/**
	 * Constructeur du paneau clavier.
	 * 
	 * @param octaveCourant est l'octave a intentier le GUI avec
	 * @param audio est la classe qui permetera la sortie du son
	 */
	public PanneauClavier(int octaveCourant, ModuleAudio audio) {
		
		this.octaveCourant = octaveCourant;
		this.audio = audio;
		tabTouches = new ToucheClavier[NOMBRE_TOUCHES_MAX+1];
		modeCourant = GuiConstantes.MODE_SOURIS;
		
		//Creation de l'objet d'enregistrement.
		enregistreur = new PaneauEnregistrement(audio);
		
		//Creation de tous les composants\.
		initComposants();
	}
	
	/**
	 * Muttateur du mode courant.
	 * 
	 * @param modeCourant est le mode du panneau clavier
	 */
	public void setMode(int modeCourant) {
		
		this.modeCourant = modeCourant;
	}
	
  /**
   * Sert a initier tout les composant de se classe
   */
	public void initComposants(){
		
		//Classe interne de mouseListener
		MyMouseListener ecouteurMouse = new MyMouseListener();
		//Distance des composentes dans le paneau clavier.
		setLayout(new GridLayout(1,13,3,0));
		
		//Creation des touches blanc et noire dans un boucle.
		for(int i = 0; i <= NOMBRE_TOUCHES_MAX; i++) {
			
			if(GuiConstantes.tabNotes[i].indexOf('#') == -1) {
				
				tabTouches[i] = new ToucheClavier(GuiConstantes.tabNotes[i],
						octaveCourant,Color.white);								
				add(tabTouches[i]);
				tabTouches[i].addMouseListener(ecouteurMouse);				
			}
			else {
				
				tabTouches[i] = new ToucheClavier(GuiConstantes.tabNotes[i],
						octaveCourant,Color.BLACK);
								add(tabTouches[i]);
				tabTouches[i].addMouseListener(ecouteurMouse);
			}					
		}
		//L'octave du dernier touche doit etre toujour plus grand
		setOctaveDerniereTouche(octaveCourant);
	}

		
	/**
	 * Classe interne pour définir l'écouteur de souris.
	 */
	private class MyMouseListener implements MouseListener{

		//Temps lors du relache de la touche
		private long end;
		
		//Temps jouer d'une note.
		private long time;
		
		@Override
		public void mouseClicked(MouseEvent e) {
			
			 //savoir le temps
			time = e.getWhen()- end;
		}


		@Override
		public void mousePressed(MouseEvent e) {
			
			//Ne pas faire le mouse listener agir sauf si en mode Souris.
			if(modeCourant == GuiConstantes.MODE_SOURIS) {
				
				JPanel source = (JPanel) e.getSource();
				
				// Avoir la reference du 1er (0) composant du JPanel clickee
				JLabel note = (JLabel) source.getComponent(0); 
				audio.jouerUneNote(note.getText());
				
				//Enregistrer la note, prendre en memoire le temps appuyer de la note
				enregistreur.enregistre(note.getText());	
				end = e.getWhen();					
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
			//Jouer une silence s'il n'y a rien appuyer.
			if (modeCourant == GuiConstantes.MODE_SOURIS) {
				
				audio.jouerUnSilence();
				enregistreur.enregistre(null);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		
		}

		@Override
		public void mouseExited(MouseEvent e) {
		
		}
	}

	
    /**
     * Muttateur qui permet de changer l'octave courrante ainsi que 
     * de toutes les  touches claviers en plein execution du GUI.
     * 
     * @param update est la nouvelle octave.
     */
	public void updateOctave(int update) {
		
		octaveCourant = update;
		for (int i = 0; i < tabTouches.length; i++) {
			
			tabTouches[i].updateOctave(update);
		}		
	}

	
	/**
	 * Accesseur de l'octave courrante.
	 * 
	 * @return l'octave courant.
	 */
	public int getOctave() {
		
		return octaveCourant;
	}

	
	
	/**
	 * Muttateur specifique de l'octave du dernier touche du 
	 * clavier, octaveCourant + 1.
	 * 
	 * @param octave est l'octave initiale.
	 */
	public void setOctaveDerniereTouche(int octave) {
		
		tabTouches[NOMBRE_TOUCHES_MAX].updateOctave(++octave);
	}
	
	/**
	 * Accesseur de l'objet d'enregistrement instencier dans se class.
	 * 
	 * @return le PaneauEnregistrement.
	 */
	public PaneauEnregistrement getPaneauEnregistrement() {
		
		return enregistreur;	
	}
	
}
