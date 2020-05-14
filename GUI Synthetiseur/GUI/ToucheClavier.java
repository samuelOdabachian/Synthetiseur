package GUI;
/**
 * 
 * La classe ToucheClavier caracterise chaque les 13 touches
 * de clavier qui va apparaitre sur le synthetiseur.
 * 
 * @author Samuel odabachian
 * @version H2020
 *
 */

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ToucheClavier extends JPanel {
	
	//La frequence(note) que la touche va faire jouer.
	private String note;
	
	//L'octave de la note.
	private int octave;
	
	//La couleur de la touche.
	private Color color;
	
	//L'etiquette sur la touche indiquant la note du touche
	private JLabel noteLable;
	
	/**
	 * Constructeur du ToucheClavier permettant de
	 * construir une touche avec certain caracteristique
	 * desirer.
	 * 
	 * @param note est la frequence sur l'aquel la touche fera reference.
	 * @param est l'octave courrante.
	 * @param est la couleur choisis.
	 */
	public ToucheClavier(String note, int octave, Color color) {
		
		this.note = note;
	    this.octave = octave;
	    this.color = color;
	   
		initComposants();
	}
	
	/**
     * Sert a initier tout les composant de se classe
	 */
	public void initComposants() {
		
		noteLable = new JLabel(note + octave);
		noteLable.setBackground(Color.BLUE);
		
		setLayout(new BorderLayout());
		add(noteLable,BorderLayout.SOUTH);
		setBackground(color);
		
	}
	
	/**
	 * Utile juste pour la derniere touche,
	 * mais muttateur de l'octave de la note.
	 * 
	 * @param update est la nouvelle octave.
	 */
	public void updateOctave(int update) {
		
		noteLable.setText(note + update);
		
	}
	
	/**
	 * Accesseur de la note.
	 * 
	 * @return la note du touche.
	 */
	public String getNote() {
		
		return note;
	}
}
