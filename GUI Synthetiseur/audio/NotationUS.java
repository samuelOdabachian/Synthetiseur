package audio;

/**
 * @Author : Frédéric Boulanger Supelec - Département Informatique 3 rue
 * Joliot-Curie 91192 Gif-sur-Yvette cedex, France
 *
 * frederic.boulanger@supelec.fr
 *
 * @Adapation norme inf111 : Pierre Bélisle
 * @version : Hiver 2020
 *
 */
/**
 * La notation US représente les notes par une lettre : A = la B = si C = do D =
 * ré E = mi F = fa G = sol
 *
 * Un bémol est indiqué par un 'b' placé après la note. Un dièse est indiqué par
 * un '#' placé après la note.
 *
 * L'octave dans laquelle se trouve la note est indiquée par un chiffre situé
 * après le nom de la note.
 * 
 * Exemples: 
 *    - C#3 : do dièse à l'octave 3
 *    - F5  : fa à l'octave 5
 *    - Ab4  : la bémol à l'octave 4
 *
 * Les changements d'octave se font sur la note do. Le do situé une ligne
 * en-dessous de la clef de sol est noté "C4" et "B4" désigne le si situé sur la
 * 3e ligne de la clef de sol.
 *
 * Le do qui se situe juste au dessus est noté "C5". Ainsi, "C2" désigne le do
 * grave du violoncelle, situé sur la deuxième ligne en-dessous de la clef de
 * fa, alors que "Eb6" désigne le mi bémol aigu situé sur la 3e ligne au-dessus
 * de la clef de sol.
 *
 * Ces numéros d'octave débutent à 0, "C0" étant la note la plus grave pouvant
 * étre représentée. Ils sont supérieurs d'une unité aux numéros d'octave de la
 * notation française, qui utilise le numéro -1 pour l'octave la plus grave.
 */
public class NotationUS {

   // Valeur d'un demi-ton = 1/12 d'octave.
   private static final double demiton_ = Math.pow(2, 1.0 / 12); // racine 12e de 2

   // Fréquence du la4
   private final double diapason_;

   // Fréquence du do "du milieu"
   private final double C4_;

   /**
    * Crée une nouvelle notation US avec diapason comme fréquence de la note
    * "A4" (2e interligne de la clef de sol).
    *
    * @param diapason est la fréquence en Hertz du la du 2e interligne de la
    * clef de sol.
    */
   public NotationUS(double diapason) {

      diapason_ = diapason;

      // On détermine la fréquence du do qui est à 
      // une tierce (3 demi-tons) moins une octave de la note.
      C4_ = diapason_ * Math.pow(demiton_, 3) / 2;
   }

   /**
    * Détermine la fréquence d'une note notée en ABC.
    */
   public double frequence(String note) {

      // On commence par déterminer à combien de demi-tons
      // du do C4 se trouve la note.
      int offsetC = 0;
      try {
         offsetC = demiTonsDo(note);
      } catch (Exception e) {
         e.printStackTrace();
      }

      double freq = C4_;

      if (offsetC > 0) {

         // Si la note est au-dessus.
         while (offsetC >= 12) {

            // On multiplie sa fréquence par 2 pour chaque octave.
            freq *= 2;
            offsetC -= 12;
         }

         while (offsetC > 0) {

            // Puis on la multiplie par 1/12 d'octave pour chaque demi-ton.
            freq *= demiton_;
            offsetC--;
         }

      } else if (offsetC < 0) {

         // Si la note est en-dessous du do, on fait l'inverse.
         while (offsetC <= -12) {

            freq /= 2;
            offsetC += 12;
         }

         while (offsetC < 0) {

            freq /= demiton_;
            offsetC++;
         }
      }
      return freq;
   }

   /**
    * Détermine à combien de demi-tons du do C4 se trouve une note notée en ABC.
    */
   private int demiTonsDo(String note) {

      int offsetC = 0;

      int idx = 0;

      while (idx < note.length()) {

         switch (note.charAt(idx)) {

            case 'b':     // bémol
               offsetC--;
               break;
            case '#':     // dièze
               offsetC++;
               break;
            case 'C':
               break;
            case 'D':
               offsetC += 2;
               break;
            case 'E':
               offsetC += 4;
               break;
            case 'F':
               offsetC += 5;
               break;
            case 'G':
               offsetC += 7;
               break;
            case 'A':
               offsetC += 9;
               break;
            case 'B':
               offsetC += 11;
               break;
            default:

               if (!Character.isDigit(note.charAt(idx))) {
                  throw new Error("Symbole invalide dans un nom de note" + note);
               }

               // On trouve le numéro d'octave.
               int i = Integer.parseInt("" + note.charAt(idx));

               // Notre octave de référence est l'octave 4, 
               // et il y a 12 demi-tons par octave.
               offsetC += (i - 4) * 12;
         }
         idx++;
      }
      return offsetC;
   }
}
