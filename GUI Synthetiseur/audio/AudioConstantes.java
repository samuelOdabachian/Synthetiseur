
package audio;

/**
 * Constantes de l'application.
 * @author melanie lord
 * @version H20
 */
public class AudioConstantes {
   
   //Maximum et minimum pour le volume et le numéro d'octave
   public static final int MAX_OCTAVE = 5;
   public static final int MIN_OCTAVE = 3;
   public static final int MAX_VOLUME = 10;
   public static final int MIN_VOLUME = 0;
   
   //valeurs a l'initialisation de l'application
   public static final int VOLUME_INIT = MAX_VOLUME / 2;
   public static final int OCTAVE_INIT = MIN_OCTAVE + 1;
   
   //Toutes les notes du clavier
   public static final String DO = "C";
   public static final String DO_DIESE = "C#";
   public static final String RE = "D";
   public static final String RE_DIESE = "D#";
   public static final String MI = "E";
   public static final String FA = "F";
   public static final String FA_DIESE = "F#";
   public static final String SOL = "G";
   public static final String SOL_DIESE = "G#";
   public static final String LA = "A";
   public static final String LA_DIESE = "A#";
   public static final String SI = "B";
}
