package GUI;
/**
 * Classe ou les les bouton de volume et octave sont configurer,
 * afin de permettre a l'utilisateur de personaliser 
 * son choix de sons.
 * 
 * @author Samuel Odabachian
 * @version 4/2/2020
 */

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import GUI.GuiConstantes;
import GUI.PanneauClavier.MyKeyListener;
import audio.ModuleAudio;

public class PanneauControle {

/*
 * Stratégie :  
 * 
 * La classe qui implémente ActionListener est déclarée 
 * en classe interne.
 * 
 **/
		
	private ModuleAudio audio;
	//Paneau principale du paneau controle 
	//Focusable True par default.
	private JPanel pan;
	
	//Bouton volume va etre mis dedans.
	private JPanel panVolume;
	
	//Boutons octave vont dedans.
	private JPanel panBoutons;
	
	//Paneau pour les bouttons d'enregistrements.
	private JPanel panRec;
	
	//Controle volume.
	private JSlider ctrlVolume;
	
	//Numeros de l'octave, un pour augementer et autre .
	//pour diminuer.
	private JButton btnOctave1;
	private JButton btnOctave2;
	
	//Boutons contrele de l'enregistrement.
	private JButton rec;
	private JButton stop;
	private JButton play;
	
	
	//Numero de l'octave afficher entre les deux Jbutton.
	private JTextField text;
	
	//Pour permettre l'identification des Bouttons
	private JLabel eticVolume;
	private JLabel eticOctave;
	
	//Mode de jeu.
	private int mode;
	
	//pour avoir accee a l'octave courante.
	private PanneauClavier referenceClaviers;
	
	// Valeur volume choisi par l'utilisateur.
	private int volume;
	
	//Nomer certain composantes.
	private JLabel volumeEtic;
	private JLabel octaveEtic;
	
	//Objet qui permet l'enregistrement d'un melodie.
	private PaneauEnregistrement enregistreur;
	
	/**
	 * Constructeurs du PanneauControle.
	 * 
	 * @param octaveInitial est l'octave initial decidee.
	 * @param audio est le module audio par lequel le sons va etre joue.
	 * @param referenceClaviers permet de communiquer avec 
	 * la class PaneauClavier.
	 * @param volume est le volume initiale de l'audio.
	 */
	public PanneauControle(int octaveInitial, ModuleAudio audio,
	PanneauClavier referenceClaviers, int volume){
		this.audio = audio;
		this.referenceClaviers = referenceClaviers;
		this.volume = volume;
		this.mode = GuiConstantes.MODE_SOURIS;;
		
		//Pour utiliser le meme objet d'enregitrement que du paneauClavier.
		enregistreur = referenceClaviers.getPaneauEnregistrement();
		initComposants(octaveInitial); 
	}
	
	/**
     * Sert a initier tout les composant de se classe
	 */
	public void initComposants(int octaveInitial){
		
		//Le action listener
		myActionListener ecouteurBtn = new myActionListener();
		
		//Les panneaux
		pan = new JPanel();
		panBoutons = new JPanel();
		panVolume = new JPanel();
		panRec = new JPanel();
		
		
		
		//Les etiquettes et textfield.
		text = new JTextField(""+ octaveInitial); 
		text.setEditable(false);
	    eticVolume = new JLabel("Volume");
		eticOctave = new JLabel("Octave");
		
		//Creation de tous les bouttons.
		btnOctave1 = new JButton("<");
		btnOctave2 = new JButton(">");
		ctrlVolume = new JSlider();
		rec = new JButton("Rec");
		stop = new JButton("Stop");
		play = new JButton("Play");
	
		
		//Ajout des actions listeners.
		btnOctave1.addActionListener(ecouteurBtn);
		btnOctave2.addActionListener(ecouteurBtn);
		rec.addActionListener(ecouteurBtn);
		stop.addActionListener(ecouteurBtn);
		play.addActionListener(ecouteurBtn);
		
		//Configuration du botton volume.
	    ctrlVolume.setMinimum(0);
	    ctrlVolume.setMaximum(10);
	    ctrlVolume.setValue(volume);
	    ctrlVolume.setPaintLabels(true);
	    ctrlVolume.setPaintTicks(true);
	    ctrlVolume.setSnapToTicks(true);
	    ctrlVolume.setMajorTickSpacing(1);
     	ctrlVolume.addChangeListener((ChangeListener) new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				
				//Gestion du choix de volume de l'utilisateur
				JSlider source = (JSlider)e.getSource();
				int volumeSource = source.getValue();

				audio.setVolume(volumeSource);
				pan.requestFocusInWindow();
			}		
     	}); 
    	
     	//Ajout des boutons d'enregistrement un paneau respective.
     	panRec.add(rec);
     	panRec.add(stop);
     	panRec.add(play);
     	
    	//Ajustement des layout.
     	pan.setLayout(new BorderLayout());
     	panVolume.setLayout(new BoxLayout(panVolume,BoxLayout.Y_AXIS));
     	eticVolume.setAlignmentX(Component.LEFT_ALIGNMENT);
     	ctrlVolume.setAlignmentX(Component.LEFT_ALIGNMENT);
     	
     	//Ajouter les 2 composants dans un panneau.
     	panVolume.add(eticVolume);
     	panVolume.add(ctrlVolume);
     	
     	//Creation d'un autre configuration layout.
      	GridBagLayout layout = new GridBagLayout();
      	panBoutons.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        
        //Ajouter les composantes dans une deuxieme paneau 
        //avec les configuration gbc (GridLayout).
        gbc.gridx = 0;
        gbc.gridy = 12;
        panBoutons.add(btnOctave1, gbc);
        
        //Idem
        gbc.gridx = 1;
        gbc.gridy = 0;
        panBoutons.add(eticOctave, gbc);
          
        //Idem
        gbc.gridx = 1;
        gbc.gridy = 12;
        panBoutons.add(text, gbc);
         
        //Idem
        gbc.gridx = 12;
        gbc.gridy = 12;
        panBoutons.add(btnOctave2, gbc);

    	//Ajouter les trois paneau ensemble.
        pan.add(panRec,BorderLayout.WEST);
    	pan.add(panVolume,BorderLayout.CENTER);
    	pan.add(panBoutons,BorderLayout.EAST);
    	
    	//Ajout du keyListener.
    	MyKeyListener ecouteurKey = new MyKeyListener();
		pan.addKeyListener(ecouteurKey);	
	}
	
	
	
	/**
	 * Classe interne pour définir l'écouteur des boutons.
	 */
	public class myActionListener implements ActionListener{
		
		int octave = referenceClaviers.getOctave();
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			JButton source = (JButton) e.getSource();
			int octave = referenceClaviers.getOctave();
			
			//Si l'utilisateur veut diminumer l'octave de tous les touches.
			if(source.getText() == "<" && referenceClaviers.getOctave() > 3) {
				
				text.setText(""+ --octave); 
				referenceClaviers.updateOctave(octave);
				referenceClaviers.setOctaveDerniereTouche(octave);	
			}
			//Si l'utilisateur veut augementer l'octave de tous les touches.
			else if(source.getText() == ">" && referenceClaviers.getOctave() < 5) {
			
				text.setText(""+ ++octave);
			    referenceClaviers.updateOctave(octave);
			    referenceClaviers.setOctaveDerniereTouche(octave);
			}
			//Si le bouttons appuyer est le Rec.
			else if(source.getText() == "Rec") {
				
				enregistreur.setState(PaneauEnregistrement.ON);
			}
			//Si le bouttons appuyer est le Stop.
			else if(source.getText() == "Stop") {
				
				enregistreur.setState(PaneauEnregistrement.STOP);
			}
			//Si le bouttons appuyer est le Play.
			else if(source.getText() == "Play") {

				enregistreur.playMelodie();
				
			}
			pan.requestFocusInWindow();		
		}
	}

	/**
	 * Classe interne pour définir l'écouteur du clavier ordinateur.
	 */
	public class MyKeyListener implements KeyListener{
		
		
		String noteJouer = null;
		
		@Override
		public void keyTyped(KeyEvent e) {	
		}

		@Override
		public void keyPressed(KeyEvent e) {
			
			if(mode == GuiConstantes.MODE_CLAVIER) {
				
				//jouer la note voulu par l'utilisateur.
				noteJouer = jouerNote(e.getKeyChar());
				
				//Enregistrer la note.
				enregistreur.enregistre(noteJouer);
			
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(mode == GuiConstantes.MODE_CLAVIER) {
				
				audio.jouerUnSilence();	
				//Enregistrer le silence.
				noteJouer = null;
				enregistreur.enregistre(noteJouer);					
			}
		}
	}
	
	
	/**
	 * Fonction qui assigne chaque touche du clavier ordinateur
	 * par une note respective et qui la joue par
	 * le module audio avec sont octave. 
	 * 
	 * @param char est la touche appuyer par l'utilisateur.
	 * @return note jouer pour rendre possible l'enregistrement.
	 */
	public String jouerNote(char car) {
		
		//note a retourner
		String note = null;
		
		switch (car) {
		
		case 'a':
			audio.jouerUneNote("C"+ referenceClaviers.getOctave());
			note = "C" + referenceClaviers.getOctave();
		break;
		
		case 'w' :
			audio.jouerUneNote("C#"+ referenceClaviers.getOctave());
			note = "C#" + referenceClaviers.getOctave();
		break;
		
		case 's' :
			audio.jouerUneNote("D"+ referenceClaviers.getOctave());
			note = "D" + referenceClaviers.getOctave();
		break;
		
		case 'e' :
			audio.jouerUneNote("D#"+ referenceClaviers.getOctave());
			note = "D#" + referenceClaviers.getOctave();
		break;
		
		case 'd' :
			audio.jouerUneNote("E"+ referenceClaviers.getOctave());
			note = "E" + referenceClaviers.getOctave();
		break;
		
		case 'f' :
			audio.jouerUneNote("F"+ referenceClaviers.getOctave());
			note = "F" + referenceClaviers.getOctave();
		break;
		
		case 't' :
			audio.jouerUneNote("F#"+ referenceClaviers.getOctave());
			note = "F#" +referenceClaviers.getOctave();
		break;
		
		case 'g' :
			audio.jouerUneNote("G"+ referenceClaviers.getOctave());
			note = "G" + referenceClaviers.getOctave();
		break;
		
		case 'y' :
			audio.jouerUneNote("G#"+ referenceClaviers.getOctave());
			note = "G#" + referenceClaviers.getOctave();
		break;
		
		case 'h' :
			audio.jouerUneNote("A"+ referenceClaviers.getOctave());
			note = "A" + referenceClaviers.getOctave();
		break;
		
		case 'u' :
			audio.jouerUneNote("A#"+ referenceClaviers.getOctave());
			note = "A#" + referenceClaviers.getOctave();
		break;
		
		case 'j' :
			audio.jouerUneNote("B"+ referenceClaviers.getOctave());
			note = "B" + referenceClaviers.getOctave();
		break;
		
		case 'k' :
			audio.jouerUneNote("C"+ referenceClaviers.getOctave()+1);
			note = "C"+ referenceClaviers.getOctave()+1;
		break;
			
		}
		return note;
	}
	
	/**
	 * Accesseur d'octave.
	 * 
	 * @return l'octave courante.
	 */
	public int getOctave() {
		
		return referenceClaviers.getOctave();
	}
	
	/**
	 * Accesseur du JPanel.
	 * 
	 * @return Le panneau JPanel principale de se class.
	 */
	public JPanel getPan(){
		
		return pan;
	}
	
	/**
	 * Muttateur de mode.
	 * 
	 * @param mode est la mode nouvelle desiree.
	 */
	public void setMode(int mode) {
		
		this.mode = mode;
	}
}
	
	
	
			
	
		
		

		
	
		
	
	
	
	
	
	
	

