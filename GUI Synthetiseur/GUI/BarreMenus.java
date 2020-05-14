package GUI;

/**
 * Classe d'ajustement de JMenuBar pour donner
 * a l'utilisateur la possibilite de choisir entre deux modes.
 * 
 * @author Samuel Odabachian
 * @version 4/2/2020
 */

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Stratégie :  
 * 
 * Une classe qui implémente ActionListener est déclarée
 * en classe interne.
 */

public class BarreMenus extends JMenuBar{
	//Reference du JFrame du Synthetiseur.
	private GUISynthetiseur synthFrame;
	//Menu qui contiendra l'accee au choix de mode.
	private JMenuBar bar;
	//Menu qui contiedra les deux modes.
	private JMenu modeJeu; 
	//les deux modes.
	private JMenuItem itemModeSouris; 
	private JMenuItem itemmodeClavier;
	
	
	/**
	 * Constructeur du BarreMenus pour cree un instence de celui si.
	 * 
	 * @param synthFrame est la reference du JFrame du Synthetiseur dans dequel 
	 * il va etre ajoute des composantes.
	 */
	public BarreMenus(GUISynthetiseur synthFrame) {
		this.synthFrame = synthFrame;
		initComposants();
	}
	
	
	/**
     * Sert a initier tout les composant de se classe
	 */
	public void initComposants(){
		
		//creation des items pour JMenueBar.
		itemModeSouris = new JMenuItem("Souris");
		itemmodeClavier = new JMenuItem("Clavier");
		bar = new JMenuBar();
		modeJeu = new JMenu("Mode de Jeu");
		
		//Class intern Action Listener.
		myActionListener ecouteurItem = new myActionListener();
		
		//Ajout de l'ecouteur sur les deux choix possible.
		itemModeSouris.addActionListener(ecouteurItem);
		itemmodeClavier.addActionListener(ecouteurItem);
		
		//Creation et l'ajout du menu.
		modeJeu.add(itemModeSouris);
		modeJeu.add(itemmodeClavier);
		bar.add(modeJeu);
		synthFrame.setJMenuBar(bar);
	}
	
	/**
	 * Classe interne pour définir l'écouteur de choix JMenu.
	 */
	public class myActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//Avour le choix de l'utilisateur
			JMenuItem source = (JMenuItem)e.getSource();
			String choix = source.getText();
			//Changement du mode
			if(choix == "Souris") {
				 
				 synthFrame.setMode(GuiConstantes.MODE_SOURIS);
			}
			else if(choix == "Clavier") {
				 
				 synthFrame.setMode(GuiConstantes.MODE_CLAVIER);			 
			}
			synthFrame.getPanneauPrincipal().getPanneauControle()
			.getPan().requestFocusInWindow();
		}	
	}	
}
