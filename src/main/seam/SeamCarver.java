import java.awt.Color;
import java.util.Stack;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private static final double MAX_ENERGY = 1000;
    private Picture picture;

    private double[] energies;

    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new java.lang.IllegalArgumentException();
        }
        this.picture = picture;
        energies = new double[height() * width()];
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

    public double energy(int x, int y) {
        if (x < 0 || y < 0 || x >= width() || y >= height()) {
            throw new java.lang.IllegalArgumentException();
        }
        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) {
            return MAX_ENERGY;
        }
        int index = y * width() + x;
        if (energies[index] != 0) {
            return energies[index];
        } else {
            Color pL = picture.get(x - 1, y);
            Color pR = picture.get(x + 1, y);
            Color pT = picture.get(x, y - 1);
            Color pB = picture.get(x, y + 1);

            double energy = Math.sqrt(
                    Math.pow(pL.getRed() - pR.getRed(), 2) +
                            Math.pow(pL.getGreen() - pR.getGreen(), 2) +
                            Math.pow(pL.getBlue() - pR.getBlue(), 2) +
                            Math.pow(pT.getRed() - pB.getRed(), 2) +
                            Math.pow(pT.getGreen() - pB.getGreen(), 2) +
                            Math.pow(pT.getBlue() - pB.getBlue(), 2)
            );
            energies[index] = energy;
            return energy;
        }

    }

    public int[] findHorizontalSeam() {
        int[] hSeam = new int[width()];
        if (height() == 1) {
            for (int i = 0; i < height(); i++) {
                hSeam[i] = 0;
            }
            return hSeam;
        }

        int firstID = width() * height();
        int lastID = width() * height() + 1;

        int[] pathToH = new int[width() * height() + 2];
        double[] distToH = new double[width() * height() + 2];

        double minLastColValue = -1;
        int minLastColIndex = -1;

        for (int x1 = 0; x1 < width(); x1++) {
            for (int y1 = 0; y1 < height(); y1++) {
                int index = y1 * width() + x1;
                double energy = energy(x1, y1);

                if (x1 == 0) {
                    pathToH[index] = firstID;
                    distToH[index] = 0;
                } else {
                    if (y1 > 0) {
                        int indexTopH = (y1 - 1) * width() + x1 - 1;
                        pathToH[index] = indexTopH;
                        distToH[index] = distToH[indexTopH] + energy;
                    }
                    if (y1 < height() - 1) {
                        int indexBottomH = (y1 + 1) * width() + x1 - 1;
                        if (distToH[index] == 0 || distToH[indexBottomH] + energy < distToH[index]) {
                            pathToH[index] = indexBottomH;
                            distToH[index] = distToH[indexBottomH] + energy;
                        }
                    }

                    int indexLeftH = y1 * width() + x1 - 1;
                    if (distToH[index] == 0 || distToH[indexLeftH] + energy < distToH[index]) {
                        pathToH[index] = indexLeftH;
                        distToH[index] = distToH[indexLeftH] + energy;
                    }
                }

                if (x1 == width() - 1) {
                    if (minLastColIndex == -1 || distToH[index] < minLastColValue) {
                        minLastColValue = distToH[index];
                        minLastColIndex = index;
                    }
                }
            }
        }

        pathToH[firstID] = -1;
        distToH[firstID] = 0;

        pathToH[lastID] = minLastColIndex;
        distToH[lastID] = minLastColValue;


        Stack<Integer> stackPath = new Stack<>();
        int point = pathToH[width() * height() + 1];
        while (point >= 0) {
            stackPath.push(point);
            point = pathToH[point];
        }

        int i = 0;
        while (!stackPath.isEmpty()) {
            int val = stackPath.pop();
            if (val < width() * height()) {
                hSeam[i] = (val - i) / width();
                i++;
            }
        }
        return hSeam;
    }

    public int[] findVerticalSeam() {
        int[] vSeam = new int[height()];
        if (width() == 1) {
            for (int i = 0; i < width(); i++) {
                vSeam[i] = 0;
            }
            return vSeam;
        }

        int firstID = width() * height();
        int lastID = width() * height() + 1;

        int[] pathToV = new int[width() * height() + 2];
        double[] distToV = new double[width() * height() + 2];

        double minLastRowValue = -1;
        int minLastRowIndex = -1;

        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                int index = y * width() + x;
                double energy = energy(x, y);

                if (y == 0) {
                    pathToV[index] = firstID;
                    distToV[index] = 0;
                } else {
                    if (x > 0) {
                        int indexLeftV = (y - 1) * width() + x - 1;
                        pathToV[index] = indexLeftV;
                        distToV[index] = distToV[indexLeftV] + energy;
                    }
                    if (x < width() - 1) {
                        int indexRightV = (y - 1) * width() + x + 1;
                        if (distToV[index] == 0 || distToV[indexRightV] + energy < distToV[index]) {
                            pathToV[index] = indexRightV;
                            distToV[index] = distToV[indexRightV] + energy;
                        }
                    }
                    int indexTopV = (y - 1) * width() + x;
                    if (distToV[index] == 0 || distToV[indexTopV] + energy < distToV[index]) {
                        pathToV[index] = indexTopV;
                        distToV[index] = distToV[indexTopV] + energy;
                    }
                }

                if (y == height() - 1) {
                    if (minLastRowIndex == -1 || distToV[index] < minLastRowValue) {
                        minLastRowValue = distToV[index];
                        minLastRowIndex = index;
                    }
                }
            }
        }
        pathToV[firstID] = -1;
        distToV[firstID] = 0;

        pathToV[lastID] = minLastRowIndex;
        distToV[lastID] = minLastRowValue;


        Stack<Integer> stackPath = new Stack<>();
        int point = pathToV[width() * height() + 1];
        while (point >= 0) {
            stackPath.push(point);
            point = pathToV[point];
        }

        int i = 0;
        while (!stackPath.isEmpty()) {
            int val = stackPath.pop();
            if (val < width() * height()) {
                vSeam[i] = val - i * width();
                i++;
            }
        }

        return vSeam;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (picture.height() <= 1 || seam.length != width()) {
            throw new java.lang.IllegalArgumentException();
        }
        validateSeam(seam, height() - 1);
        Picture newPicture = new Picture(width(), height() - 1);
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height() - 1; y++) {
                if (y < seam[x]) {
                    newPicture.set(x, y, picture.get(x, y));
                } else {
                    newPicture.set(x, y, picture.get(x, y + 1));
                }
            }
        }
        picture = newPicture;
    }

    public void removeVerticalSeam(int[] seam) {
        if (picture.width() <= 1 || seam.length != height()) {
            throw new java.lang.IllegalArgumentException();
        }
        validateSeam(seam, width() - 1);
        Picture newPicture = new Picture(width() - 1, height());
        for (int x = 0; x < width() - 1; x++) {
            for (int y = 0; y < height(); y++) {
                if (x < seam[y]) {
                    newPicture.set(x, y, picture.get(x, y));
                } else {
                    newPicture.set(x, y, picture.get(x + 1, y));
                }
            }
        }
        picture = newPicture;
    }

    private void validateSeam(int[] seam, int max) {
        int prev = -1;
        for (int aSeam : seam) {
            if (aSeam < 0 || aSeam >= max) {
                throw new java.lang.IllegalArgumentException(String.valueOf(aSeam));
            }
            if (prev != -1 && Math.abs(aSeam - prev) > 1) {
                throw new java.lang.IllegalArgumentException();
            }
            prev = aSeam;
        }
    }
}