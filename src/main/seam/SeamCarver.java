import java.awt.Color;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    public static final double MAX_ENERGY = 1000;
    protected Picture picture;

    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new java.lang.IllegalArgumentException();
        }
        this.picture = new Picture(picture);
    }

    public Picture picture() {
        return picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public  double energy(int x, int y) {
        if (x < 0 || y < 0 || x >= width() || y>=height()) {
            throw new java.lang.IllegalArgumentException();
        }
        if (x == 0 || y == 0 || x == width()-1  || y == height()-1) {
            return MAX_ENERGY;
        }
        Color pL = picture.get(x-1, y);
        Color pR = picture.get(x+1, y);
        Color pT = picture.get(x, y-1);
        Color pB = picture.get(x, y+1);

        double energy = Math.sqrt(
                Math.pow(pL.getRed()-pR.getRed(),2)+
                Math.pow(pL.getGreen()-pR.getGreen(),2)+
                Math.pow(pL.getBlue()-pR.getBlue(),2)+
                Math.pow(pT.getRed()-pB.getRed(),2)+
                Math.pow(pT.getGreen()-pB.getGreen(),2)+
                Math.pow(pT.getBlue()-pB.getBlue(),2)
        );
        return energy;
    }

    public int[] findHorizontalSeam() {
        int[] hSeam = new int[width()];
        return hSeam;
        // sequence of indices for horizontal seam
    }

    public int[] findVerticalSeam() {
        int[] vSeam = new int[height()];
        return vSeam;
        // sequence of indices for vertical seam
    }
    public void removeHorizontalSeam(int[] seam) {
        // remove horizontal seam from current picture
    }
    public void removeVerticalSeam(int[] seam) {
        // remove vertical seam from current picture
    }
}