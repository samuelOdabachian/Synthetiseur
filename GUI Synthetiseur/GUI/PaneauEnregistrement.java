package GUI;
/**
 * Classe utilisant arrayList pour pouvaoir enregistree 
 * des sequences de notes afin de pouvoir le rejouer.
 * 
 * @author Samuel Odabachian
 * @version 4/2/2020
 */

import java.util.ArrayList;

import audio.ModuleAudio;

/*
 
 * 
 * @author Samuel Odabachian
 * @version H2020
 */

public class PaneauEnregistrement {

	//Constante de mode de l'enregistreur.
	public static final int ON = 1;
	public static final int STOP = 0;
	public static final int PLAY = 1;
	
	//Mode de l'enregistreur.
	private int recState;
	//Tableau de notes.
	private ArrayList <String> melodieTab;
	//le module audio par lequel la melodie va faire
	//appelle pour rejouer.
	private ModuleAudio audio;
	
	
	/*
	 * Constructeur pour cree un objet enregistrement.
	 * 
	 * @param audio est le module audio.
	 */
	public PaneauEnregistrement(ModuleAudio audio) {
		
		recState = STOP;
		melodieTab = new ArrayList<String>();
		this.audio = audio;
	}
	
	
	
	/*
	 * Cela enregistre la note recu dans la liste
	 * si le mode est en mode ON.
	 * 
	 * @param note est la note recu.
	 */
	public void enregistre(String note) {
		
		if(recState == ON) {
			melodieTab.add(note);		
		}
		
	}
	
	/*
	 * Prendre la liste courant remplie et faire apelle
	 * au notes concerver un par un pour faire regouer
	 * la melodie enregistree.
	 * 
	 */
	public void playMelodie() {
		
		//Pour le debug, montre si le tableau a belle et bien ete remplie.
		//System.out.println("array size " + melodieTab.size());
		
		//Jouer les notes.
		for(int i = 0; i < melodieTab.size(); i++) {	
			if(melodieTab.get(i) == null) {
				//System.out.println("Aray notes " + melodieTab.get(i));
				audio.jouerUnSilence();
			}
			else {
				audio.jouerUneNote(melodieTab.get(i));
				//System.out.println("Aray notes " + melodieTab.get(i));
			}
		}		
	}
	
	
	/*
	 * Accesseur de mode de l'enregistreur.
	 */
	public int getState() {
		return recState;
	}
	
	/*
	 * Accesseur de mode d'enregistreur.
	 */
	public void setState(int state) {
		recState = state;
	}
}

