import java.util.*;
import java.io.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
      /* testing Matrix class
        Matrix ident = new Matrix(2);
        ident.addRow(new double[]{4, 3});
        ident.addRow(new double[]{6, 1});
        ident.addRow(new double[]{0.75, 1});
        Matrix b = ident.copy();
        b.rref();
        System.out.println(b);
        System.out.println(ident);
        System.out.println(ident.transpose());
        Matrix c = new Matrix(3);
        c.addRow(new double[]{2, 4, -6});
        c.addRow(new double[]{7, 3, 5});
        c.addRow(new double[]{1, -2, 4});
        b = c.copy();
        c.invert();
        System.out.println(c);
        b.mult(c);
        System.out.println(b);
        Matrix a = new Matrix(3);
        a.addRow(new double[]{2, 1, 6});
        a.addRow(new double[]{5, 7, -6});
        b = a.copy();
        b.rref();
        System.out.println(b);
        a.mult(ident);
        System.out.println(a);
        a.rref();
        System.out.println(a);
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
      Matrix M = new Matrix(2);
      Matrix y = new Matrix(1);
      for (int i=0; i<avgTemp.length; i++) {
        M.addRow(new double[]{avgTemp[i], 1});
      }
      for (int i=0; i<pirateAttacks.length; i++) {
        y.addRow(new double[]{pirateAttacks[i]});
      }
      Matrix MTMinv = M.copy();
      MTMinv.mult(MTMinv.transpose());
      MTMinv.invert();
      y.mult(M.transpose());
      y.mult(MTMinv);
      System.out.println(y.unformattedToString());
    }
}