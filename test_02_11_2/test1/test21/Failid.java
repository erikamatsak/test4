/**
 * Fail Failid.java
 * @author Jaanus Poial
 * @version 0.2 kevad 99
 */

//=======================================================================
// Na"ited to"o"st failidega
//=======================================================================

import java.io.*;

public class Failid {

   public static void main (String[] parameetrid) {

      //================================================================
      // Teksti lugemine klaviatuurilt
      //================================================================

      System.out.println ("Teksti sisestamine standardsisendist.");
      try {
         BufferedReader sisse = new BufferedReader
                                   (new InputStreamReader(System.in));
         System.out.print ("Anna tekst: ");
         String s = sisse.readLine(); // rida teksti ka"es
         System.out.println ("Tippisid: " + s);
      }
      catch (IOException e) {
         System.out.println ("S/V viga: " + e);
      }
  
      //================================================================
      // Teksti kirjutamine faili
      //================================================================

      System.out.println ("Teksti kirjutamine faili.");
      try {
         PrintWriter valjund = 
            new PrintWriter (new FileWriter ("tekst.txt"), true);
         valjund.println ("Esimene rida");
         valjund.println ("Teine rida ja\nkolmas rida");
         valjund.close();
      }
      catch (IOException e) {
         System.out.println ("S/V viga: " + e);
      }

      //===============================================================
      // Teksti lugemine failist
      //===============================================================

      System.out.println ("Teksti lugemine failist.");
      try {
         BufferedReader sisend =
            new BufferedReader (new FileReader ("tekst.txt"));
         String rida;
         while ((rida = sisend.readLine()) != null) {
            // rida olemas, vo~ime to"o"delda
            System.out.println (rida);
         }
         sisend.close();
      }
      catch (IOException e) {
         System.out.println ("S/V viga: " + e);
      }

      //===============================================================
      // Suvaliste andmete kirjutamine faili
      //===============================================================

      System.out.println ("Andmete kirjutamine faili.");
      try {
         DataOutputStream valjundvoog =
            new DataOutputStream (new FileOutputStream ("andmed.bin"));
         valjundvoog.writeInt (1234);
         valjundvoog.writeDouble (1.23e4);
         valjundvoog.close();
      }
      catch (IOException e) {
         System.out.println ("S/V viga: " + e);
      }

      //===============================================================
      // Suvaliste andmete lugemine, kui faili struktuur on teada
      //===============================================================

      System.out.println ("Andmete lugemine failist.");
      try {
         DataInputStream sisendvoog =
            new DataInputStream (new FileInputStream ("andmed.bin"));
         int n = sisendvoog.readInt();
         double d = sisendvoog.readDouble();
         sisendvoog.close();
         System.out.println ("Loeti: " + n + " ja " + d);
      }
      catch (EOFException e) { // oluline to"o"delda
         System.out.println ("Faili lopp tuli ette: " + e);
      }
      catch (IOException e) {
         System.out.println ("S/V viga: " + e);
      }

      //===============================================================
      // Faili lugemine baithaaval
      //===============================================================

      System.out.println ("Andmete lugemine baidikaupa.");
      try {
         FileInputStream voog = new FileInputStream ("andmed.bin");
         System.out.println ("Baidid on: ");
         int bait;
         while ((bait = voog.read()) != -1) {
            System.out.print (Integer.toHexString (bait) + " ");
         }
         voog.close();
         System.out.println();
      }
      catch (IOException e) {
         System.out.println ("S/V viga: " + e);
      }
         
      //===============================================================
      // Faili lugemine baitmassiivi
      //===============================================================

      System.out.println ("Terve faili lugemine baitmassiivi.");
      try { 
         FileInputStream p = new FileInputStream ("tekst.txt"); 
         byte[] sisu = new byte [p.available()]; 
         p.read (sisu); 
         p.close(); 
         // siin on meil kogu fail baitmassiivina -- teeme, mis tahame
         System.out.write (sisu);
      } 
      catch (IOException e) { 
         System.out.println ("S/V viga: " + e);
      } 

      //===============================================================
      // Kataloogi sisu lugemine
      //===============================================================

      System.out.println ("Kataloogi sisu lugemine.");
      File f = new File (".."); // suval. kat. nimi
      if (!f.exists() || !f.canRead()) {
         System.out.println ("Ei saa lugeda: " + f);
         return;
      }
      if (f.isDirectory()) {
         String [] nimekiri = f.list(); // sisu String-massiivina
         for (int i=0; i < nimekiri.length; i++)
            System.out.println (nimekiri [i]);
      } else {
         System.out.println ("Ei ole kataloog: " + f);
      }

      //==============================================================
      // rot13 filtri katsetamine ja ma"lupuhver faili rollis
      //==============================================================

      byte [] bfail = null;  // bfail on baitmassiiv

      System.out.println ("Kodeeritud faili kirjutamine.");
      try {
         // malu on va"ljundvoog, mille hiljem teeme massiiviks
         ByteArrayOutputStream malu = new ByteArrayOutputStream();

         // r13 on kodeeritud voog, mille kandjaks on malu
         Rot13OutputStream r13 =
            new Rot13OutputStream (malu);
         r13.write ('A');
         r13.write ('B');
         r13.write ('C');
         r13.write ('2');
         r13.write ('n');
         r13.write ('o');
         r13.write ('p');
         r13.write ('.');
         r13.close();

         bfail = malu.toByteArray(); // teise programmi jaoks meelde
      }
      catch (IOException e) {
         System.out.println ("S/V viga: " + e);
      }

   System.out.println ("Kodeeritud fail:\n" + new String (bfail));

   System.out.println ("Faili lugemine ja dekodeerimine.");
   try {
      Rot13InputStream r13 =
         new Rot13InputStream (new ByteArrayInputStream (bfail));
      int b;
      System.out.println ("Dekodeeritud fail: ");
      while ((b = r13.read()) != -1) {
         System.out.print ((char)b);
      }
      System.out.println();
   }
   catch (IOException e) {
         System.out.println ("S/V viga: " + e);
   }

   } // main lopp

} // Failid lopp

//=======================================================================
// Filtrite kasutamine
//=======================================================================

class Rot13InputStream extends FilterInputStream {

   Rot13InputStream (InputStream i) {
      super (i);
   }

   public int read() throws IOException {
      return rot13 (in.read()); // in on isendimuutuja
   }

   static int rot13 (int c) { // sama kasutame ka po"o"rdteisenduseks
      if ((c >= 'A') && (c <= 'Z'))
         c = 'A' + (((c - 'A') + 13) % 26);
      if ((c >= 'a') && (c <= 'z'))
         c = 'a' + (((c - 'a') + 13) % 26);
      return c;
   }

} // Rot13InputStream lopp

//====================================================================

class Rot13OutputStream extends FilterOutputStream {

   Rot13OutputStream (OutputStream o) {
      super (o);
   }

   public void write (int c) throws IOException {
      out.write (Rot13InputStream.rot13 (c)); // out on isendimuutuja
   }

} // Rot13OutputStream lopp

