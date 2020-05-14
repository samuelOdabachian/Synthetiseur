package audio;

import java.nio.ByteBuffer;
import javax.sound.sampled.*;

/**
 * Module audio permettant de jouer des notes (ou un silence) de maniere 
 * continue. On utilise ici une forme d'onde sinusoidale. 
 * 
 * Methodes publiques : 
 * 
 * - La methode jouerUneNote permet de jouer la note donnee en parametre de 
 *   maniere continue.
 * 
 * - La methode jouerUnSilence permet de jouer un silence de maniere continue.
 * 
 * - La methode setVolume permet de modifier le volume des notes jouees.
 * 
 * @author Melanie Lord
 * @revision Pierre Bélisle
 * @version H20
 */
public class ModuleAudio {
   
   //frequence de la note A4. Reference pour calculer la frequence des notes  
   //selon l'octave donne.
   public final static int DIAPASON = 440;
   
   //le volume des notes jouees
   private int volume;
   
   //le thread qui fournit continuellement des echantillons a jouer
   private ThreadEchantillons thread;
   
   //frequence (determine la note jouee. Si 0, c'est un silence)
   private double frequence = 0;
   
   //ligne audio sur laquelle on ecrit les echantillons
   private static SourceDataLine ligneAudio;
   
   //Determine le format audio
   private static AudioFormat formatAudio;
 

   /**
    * Constructeur qui initialise le volume de ce module audio avec le parametre 
    * donne, et qui demarre le thread fournissant continuellement des 
    * echantillons sur la ligne audio (et s'assure qu'elle n'est jamais vide).
    * 
    * @param volume intensite du volume entre AudioConstantes.MIN_VOLUTME et   
    *               AudioConstantes.MAX_VOLUME.
    */
   public ModuleAudio(int volume) {
	   
      setVolume(volume);
      thread = new ThreadEchantillons();
      thread.start();
   } 
   
   /**
    * Permet de modifier le volume.
    * @param volume intensite du volume entre AudioConstantes.MIN_VOLUME et   
    *               AudioConstantes.MAX_VOLUME.
    */
   public void setVolume(int volume) {
	   
      //rapporter le volume dans l'intervalle [0..AudioConstantes.MAX_VOLUME]
	  //s'il y a lieu
      if (volume < 0){
         volume = 0;
      }
      else if (volume > AudioConstantes.MAX_VOLUME) {
         volume = AudioConstantes.MAX_VOLUME;
      }
      
      //Ajuster le volume avec une regle de 3 : AudioConstantes.MAX_VOLUME
      //vaut Short.MAX_VALUE
      this.volume = volume * Short.MAX_VALUE / AudioConstantes.MAX_VOLUME;
   }
   
   /**
    * Joue la note donnee en parametre.
    * La note joue de maniere continue tant qu'on ne joue pas un silence 
    * (voir methode jouerUnSilence).
    * 
    * La note donnee doit respecter le format explique dans la classe NotationUS.
    *   Exemples: 
    *    - C#3 : do diese a l'octave 3
    *    - F5  : fa a l'octave 5
    *    - Eb4  : mi bemo a l'octave 4
    * 
    * @param note la note a jouer.
    */
   public void jouerUneNote(String note) {
	   
      //ajuster la frequence correspondant a la note recue en parametre.
      frequence = new NotationUS(DIAPASON).frequence(note);
   }
   
   /**
    * Joue un silence (aucun son) de maniere continue.
    */
   public void jouerUnSilence() {
      frequence = 0;
   }
   
   /**
    * Classe Thread dont la methode run s'assure de fournir continuellement des
    * echantillons (son ou silence) a la ligne audio.
    * 
    * Tire de l'exemple 2 donne sur le site 
    * //http://www.wolinlabs.com/blog/java.sine.wave.html
    * et adapte pour les besoins du cours INF111.
    */
   private class ThreadEchantillons extends Thread {

      //--------------------------------------
      // CONSTANTES DE CLASSE
      //--------------------------------------
      public final static int TAUX_ECHANTILLONNAGE = 44100; 
      public final static int NB_BITS = 16;
      public final static int TAILLE_ECHANTILLON = 2;       //2 octets
      
      //Une valeur plus petite diminue la latence, mais si trop petite, produit
      //du bruit dans la sortie audio parce qu'il peut venir a manquer des donnees 
      //dans le buffer.
      public final static double DUREE_BUFFER = 0.02;      
      
      //Taille (en octets) des echantillons d'onde sinuosidale qu'on va creer a 
      //chaque tour de boucle dans la methode run.
      public final static int TAILLE_ECHANTILLON_SINE = 
              (int) (DUREE_BUFFER * TAUX_ECHANTILLONNAGE * TAILLE_ECHANTILLON);

      //--------------------------------------
      // ATTRIBUT D'INSTANCE
      //--------------------------------------
      
      //Lorsque false, on entre dans le boucle (de la methode run) et la boucle
      //s'execute jusqu'a ce que cette variable devienne true.
      private boolean sortirDuThread = false;

      
      //--------------------------------------
      // METHODES
      //--------------------------------------

      /**
       * Permet de terminer ce thread. 
       */
      public void sortir() {
         sortirDuThread = true;
      }
      
      /**
       * Remplit continuellement le tampon de la ligne audio pour qu'il ne soit 
       * jamais vide.
       */
      @Override
      public void run() {

         //position dans l'onde sinuosidale (0 - 1 correspond a 0 - 2 * PI)
         double positionDansCycleOndeSine = 0;
         
         //fraction du cycle entre les echantillons
         double fractionCycleEntreLesEchantillons;

         //tampon utilise pour conserver les echantillons generes
         ByteBuffer buffer = ByteBuffer.allocate(TAILLE_ECHANTILLON_SINE);
         
         //ouvrir et demarrer la ligne audio pour pouvoir ecrire dedans
         ouvrirLigne();
         
         //s'assurer que le tampon de la ligne audio contient continuellement 
         //des donnees, tant qu'on est dans la boucle
         while (!sortirDuThread) {

            //calculer la fraction du cycle entre les echantillons
            fractionCycleEntreLesEchantillons = frequence / TAUX_ECHANTILLONNAGE;   
            
            //vider le buffer des donnees du dernier tour de boucle
            buffer.clear();     
            
            //generer des echantillons d'onde sinusoidale
            positionDansCycleOndeSine = genererEchantillons(volume, buffer, 
                  positionDansCycleOndeSine, fractionCycleEntreLesEchantillons);
            
            //Ecrire les echantillons generes sur la ligne audio. 
            ligneAudio.write(buffer.array(), 0, buffer.position());

            //Attendre qu'il reste moins de TAILLE_ECHANTILLON_SINE echantillons
            //dans le buffer avant d'en ecrire d'autres. Tant que ce n'est pas  
            //le cas, on donne la chance aux autres processus de s'executer.
            try {
               while (obtenirNbrEchantillonsEnfileDansLigne() 
                                          > TAILLE_ECHANTILLON_SINE) {
                  Thread.sleep(1);                          
               }
            } catch (InterruptedException e) {               
               e.printStackTrace(); //ne devrait pas se produire
            }
         }

         ligneAudio.drain();  //vider la ligne
         ligneAudio.close(); //fermer la ligne
      }
      
      /**
       * Genere des echantilonns d'onde sinusoidale, sur NB_OCTETS octets, 
       * d'un volume donne en parametre, et les place dans le buffer donne 
       * en parametre.
       * 
       * @param volume l'intensite du volume des echantillons
       * @param buffer tampon dans lequel on ecrit les echantillons generes
       * @param positionDansCycleOndeSine la position dans le cycle de l'onde 
       *        sinusoidale (varie a mesure qu'on cree des echantillons).
       * @param fractionCycleEntreLesEchantillons la fraction du cycle entre les
       *        echantillon.
       * @return la position finale courante dans le cycle de l'onde sinusoidale.
       */
      private double genererEchantillons(int volume,     		  
    		                         ByteBuffer buffer, 
                                     double positionDansCycleOndeSine, 
                                     double fractionCycleEntreLesEchantillons) {
         
         for (int i = 0; i < TAILLE_ECHANTILLON_SINE / TAILLE_ECHANTILLON; i++) {
        	 
            buffer.putShort((short) (volume * 
            		                 Math.sin(TAILLE_ECHANTILLON * Math.PI *
            		                		  positionDansCycleOndeSine)));

            //calculer / ajuster la position dans l'onde sinuosidale
            positionDansCycleOndeSine += fractionCycleEntreLesEchantillons;

            if (positionDansCycleOndeSine > 1) {
               positionDansCycleOndeSine -= 1;
            }
         }
         return positionDansCycleOndeSine;
      }
      
      /**
       * Retourne le nombre d'echantillons enfiles dans le tampon de la 
       * ligne audio.
       * 
       * @return le nombre d'echantillons enfiles dans le tampon de la 
       *         ligne audio.
       */
      private int obtenirNbrEchantillonsEnfileDansLigne() {
         return ligneAudio.getBufferSize() - ligneAudio.available();
      }
      
      /**
       * Cree une sortie audio mono, avec un taux d'echantillonnage de 
       * TAUX_ECHANTILLONNAGE hz, des 
       * echantillons sur NB_BITS bits, et l'ordonnancement des octets 
       * en "big endian".
       * 
       * La taille de la memoire tampon = NB_OCTETS * TAILLE_ECHANTILLON_SINE.
       */
      private void ouvrirLigne() {
         try {
        	
        	//pour la lisibiité
        	boolean SIGNED = true, BIG_ENDIAN = true;
        	
            formatAudio = new AudioFormat(TAUX_ECHANTILLONNAGE, 
            		                      NB_BITS, 
            		                      1, 
            		                      SIGNED, 
            		                      BIG_ENDIAN);
            
            DataLine.Info info = new DataLine.Info(SourceDataLine.class,
            		                               formatAudio, 
            		                               TAILLE_ECHANTILLON_SINE * 
            		                               TAILLE_ECHANTILLON);
            
            //lever une exception si le systeme ne supporte pas ce type de ligne 
            //audio.
            if (!AudioSystem.isLineSupported(info)) {
               throw new LineUnavailableException();
            }

            //Ouvrir la ligne audio
            ligneAudio = (SourceDataLine) AudioSystem.getLine(info);
            ligneAudio.open(formatAudio);
            ligneAudio.start();
            
         } catch (LineUnavailableException e) {
            e.printStackTrace();  //ne devrait pas se produire
         }         
      }

   } //fin classe ThreadEchantillon
     
} //fin classe ModuleAudio
