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

  public void rref() {
    for (int c=0; c<rowLength(); c++) {
      if (c<height()) {
        if (m.get(c)[c]==0) {
          boolean found=false;
          int i=c+1;
          while (!found && i<height()) {
            if (m.get(i)[c]!=0) {
              swap(i, c);
              found=true;
            } else {
              i++;
            }
          }
        }
        scl(c, 1/m.get(c)[c]);
        for (int i=0; i<height(); i++) {
          if (i!=c) {
            lincomb(i, c, -1*m.get(i)[c]);
          }
        }
      }
    }
  }

  public void invert() {
    if (rowLength()!=height()) {
      throw new IllegalArgumentException("matrix must be square");
    } else {
      Matrix aug = new Matrix(rowLength()*2);
      for (int i=0; i<height(); i++) {
        double[] row = new double[rowLength()*2];
        for (int j=0; j<rowLength(); j++) {
          row[j]=get(i)[j];
        }
        row[rowLength()+i]=1;
        aug.addRow(row);
      }
      aug.rref();
      for (int i=0; i<height(); i++) {
        double[] row = new double[rowLength()];
        for (int j=0; j<rowLength(); j++) {
          row[j]=aug.get(i)[rowLength()+j];
        }
        m.set(i, row);
      }
    }
  }

  //swap rows a and b
  private void swap(int a, int b) {
    double[] temp = Arrays.copyOf(get(a), rowLength());
    m.set(a, get(b));
    m.set(b, temp);
  }

  //mutliply row r by scalar s
  private void scl(int r, double s) {
    for (int i=0; i<rowLength(); i++) {
      get(r)[i]*=s;
    }
  }

  //add s*row b to row a
  private void lincomb(int a, int b, double s) {
    for (int i=0; i<rowLength(); i++) {
      get(a)[i]+=s*get(b)[i];
    }
  }
  
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
        mat.addRow(Arrays.copyOf(get(i), rowLength()));
    }
    return mat;
  }

  public void clear() {
    m = new ArrayList<double[] >();
  }

  public Matrix transpose() {
    Matrix mat = new Matrix(height());
    for (int i=0; i<rowLength(); i++) {
      double[] row = new double[height()];
      for (int j=0; j<height(); j++) {
        row[j]=get(j)[i];
      }
      mat.addRow(row);
    }
    return mat;
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

  public String unformattedToString() {
   String s = "";
    for (int i=0; i<height(); i++) {
      s+="|";
      for (int j=0; j<rowLength(); j++) {
        s+=m.get(i)[j];
        if (j<rowLength()-1) {
            s+=" ";
        }
      }
      s+="|\n";
    }
    return s;
  }

  public String toString() {
    String s = "";
    int[][] lengths = new int[height()][rowLength()];
    int[] maxLengths = new int[rowLength()];
    for (int c=0; c<rowLength(); c++) {
        int maxL = 0;
        for (int r=0; r<height(); r++) {
            lengths[r][c]=(int)Math.log10(get(r)[c])+3;
            if (get(r)[c]<0) {
              lengths[r][c]++;
            }
            if (get(r)[c]==0) {
              lengths[r][c]=3;
            }
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