package GUI;

import audio.AudioConstantes;

/**
 * Les constantes des modes se trouvent ici.
 * 
 * @author Samuel Odabachian
 * @version 4/2/2020
 */

public class GuiConstantes {

	//les modes du Synthetiseur
	 public static final int MODE_SOURIS = 1;
	 public static final int MODE_CLAVIER = 2;
	 
	 // Liste du toutes les notes ainsi que la premiere du 
	 // prochaine octave
	 public static final  String tabNotes[] = {
				
				AudioConstantes.DO,
				AudioConstantes.DO_DIESE,
				AudioConstantes.RE,
				AudioConstantes.RE_DIESE,
				AudioConstantes.MI,
				AudioConstantes.FA,
				AudioConstantes.FA_DIESE,
				AudioConstantes.SOL,
				AudioConstantes.SOL_DIESE,
				AudioConstantes.LA,
				AudioConstantes.LA_DIESE,
				AudioConstantes.SI,
				AudioConstantes.DO
				
		};
	 
}
