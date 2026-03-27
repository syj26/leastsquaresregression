import java.util.*;
import java.io.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        /* testing out Matrix class
        Matrix ident = new Matrix(2);
        ident.addRow(new double[]{4, 3});
        ident.addRow(new double[]{6, 1});
        ident.addRow(new double[]{0.75, 1});
        Matrix a = new Matrix(3);
        a.addRow(new double[]{2, 1, 6});
        a.addRow(new double[]{5, 7, 2});
        a.mult(ident);
        System.out.println(a.toString());
        */
       
       double[] pirateAttacks = new double[28];
       double[] avgTemp = new double[28];
       try {
        Scanner sc = new Scanner(new File("pirate_attacks.csv"));
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            for (int i=0; i<28; i++) {
                if (line.substring(0, 4).equals((i+1993)+"")) {
                    pirateAttacks[i]+=1;
                }
            }
        }
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
      try {
        Scanner sc = new Scanner(new File("Average Temperature 1900-2023.csv"));
        while (sc.hasNextLine()) {
            String[] info = sc.nextLine().split(",");
            for (int i=0; i<28; i++) {
                try {
                    if (Integer.parseInt(info[0])==i+1993) {
                        avgTemp[i]=Double.parseDouble(info[1]);
                    }
                } catch (Exception ex){}
            }
        }
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
      /* for latex formatting table
      for (int i=0; i<28; i++) {
        System.out.println(avgTemp[i]+" & "+pirateAttacks[i]+" \\\\");
      }*/
      System.out.println("Pirate Attacks: "+Arrays.toString(pirateAttacks));
      System.out.println("Average Temperature: "+Arrays.toString(avgTemp));
    }
}