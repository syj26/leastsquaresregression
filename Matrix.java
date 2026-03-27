import java.util.*;

public class Matrix {

  private int rowLength;
  private ArrayList<double []>m;

  Matrix(int l) {
    m = new ArrayList<double []>();
    rowLength = l;
  }

  public void addRow(double[] row) {
    if (row.length==rowLength) {
        m.add(row);
    } else {
        throw new IllegalArgumentException("row is length "+row.length+", should be length "+rowLength);
    }
  }
  
  //left mutliplication!! so this.mult(A) would be A*this
  public void mult(Matrix a) {
    if (a.rowLength()!=height()) {
      throw new IllegalArgumentException("invalid matrix dimension for multiplying");
    }
    ArrayList<double[]> newMatrix = new ArrayList<double[]>();
    for (int i=0; i<a.height(); i++) {
      double[] row = new double[rowLength()];
      for (int j=0; j<rowLength(); j++) {
        for (int k=0; k<height(); k++) {
          row[j]+=m.get(k)[j]*a.get(i)[k];
        }
      }
      newMatrix.add(row);
    }
    m=newMatrix;
  }//mult

  public int height() {
    return m.size();
  }

  public int rowLength() {
    return rowLength;
  }

  public double[] get(int i) {
    return m.get(i);
  }

  public Matrix copy() {
    Matrix mat = new Matrix(rowLength);
    for (int i=0; i<height(); i++) {
        mat.addRow(get(i));
    }
    return mat;
  }

  public void clear() {
    m = new ArrayList<double[] >();
  }

  public double roundTenth(double x) {
    int big = (int)(x*100);
    if (x<0) {
        return -1*roundTenth(-1*x);
    }
    if (big%10>=5) {
        return ((double)(big/10+1))/10;
    } else {
        return ((double)(big/10))/10;
    }
  }

  public String toString() {
    String s = "";
    int[][] lengths = new int[height()][rowLength()];
    int[] maxLengths = new int[rowLength()];
    for (int c=0; c<rowLength(); c++) {
        int maxL = 0;
        for (int r=0; r<height(); r++) {
            lengths[r][c]=(int)Math.log10(get(r)[c])+3;
            if (lengths[r][c]>maxL) {
                maxL=lengths[r][c];
            }
        }
        maxLengths[c]=maxL;
    }
    for (int i=0; i<height(); i++) {
      s+="|";
      for (int j=0; j<rowLength(); j++) {
        s+=roundTenth(m.get(i)[j]);
        if (j<rowLength()-1) {
            s+=" ";
        }
        for (int k=0; k<maxLengths[j]-lengths[i][j]; k++) {
            s+=" ";
        }
      }
      s+="|\n";
    }
    return s;
  }
}