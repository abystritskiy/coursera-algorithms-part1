import java.awt.Color;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.DijkstraSP;


public class SeamCarver {
    private static final double MAX_ENERGY = 1000;
    private Picture picture;
    private EdgeWeightedDigraph graphH;
    private EdgeWeightedDigraph graphV;

    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new java.lang.IllegalArgumentException();
        }
        this.picture = new Picture(picture);

        graphH = new EdgeWeightedDigraph(height() * width() + 2);
        graphV = new EdgeWeightedDigraph(height() * width() + 2);

        int firstID = width() * height();
        int lastID = width() * height() + 1;

        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                int vertex = y * width() + x;
                double energy = energy(x, y);

                // graphV - vertically connected graph
                if (y == 0) {
                    graphV.addEdge(new DirectedEdge(firstID, vertex, energy));
                } else {
                    if (x > 0) {
                        int indexLeftV = (y - 1) * width() + x - 1;
                        graphV.addEdge(new DirectedEdge(indexLeftV, vertex, energy));
                    }
                    if (x < width() - 1) {
                        int indexRightV = (y - 1) * width() + x + 1;
                        graphV.addEdge(new DirectedEdge(indexRightV, vertex, energy));
                    }
                    int indexTopV = (y - 1) * width() + x;
                    graphV.addEdge(new DirectedEdge(indexTopV, vertex, energy));

                    if (y == height() - 1) {
                        graphV.addEdge(new DirectedEdge(vertex, lastID, MAX_ENERGY));
                    }
                }

                // graphH - horizontally connected graph
                if (x == 0) {
                    graphH.addEdge(new DirectedEdge(firstID, vertex, energy));
                } else {
                    if (y > 0) {
                        int indexTopH = (y - 1) * width() + x - 1;
                        graphH.addEdge(new DirectedEdge(indexTopH, vertex, energy));
                    }
                    if (y < height() - 1) {
                        int indexBottomH = (y + 1) * width() + x - 1;
                        graphH.addEdge(new DirectedEdge(indexBottomH, vertex, energy));
                    }

                    int indexLeftH = y * width() + x - 1;
                    graphH.addEdge(new DirectedEdge(indexLeftH, vertex, MAX_ENERGY));

                    if (x == width() - 1) {
                        graphH.addEdge(new DirectedEdge(vertex, lastID, MAX_ENERGY));
                    }
                }
            }
        }
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
        Color pL = picture.get(x - 1, y);
        Color pR = picture.get(x + 1, y);
        Color pT = picture.get(x, y - 1);
        Color pB = picture.get(x, y + 1);

        return Math.sqrt(
                Math.pow(pL.getRed() - pR.getRed(), 2) +
                        Math.pow(pL.getGreen() - pR.getGreen(), 2) +
                        Math.pow(pL.getBlue() - pR.getBlue(), 2) +
                        Math.pow(pT.getRed() - pB.getRed(), 2) +
                        Math.pow(pT.getGreen() - pB.getGreen(), 2) +
                        Math.pow(pT.getBlue() - pB.getBlue(), 2)
        );
    }

    public int[] findHorizontalSeam() {
        int[] hSeam = new int[width()];
        int firstID = width() * height();
        int lastID = width() * height() + 1;

        DijkstraSP sp = new DijkstraSP(graphH, firstID);
        int x = 0;
        for (DirectedEdge edge : sp.pathTo(lastID)) {
            int edgeId = edge.to();
            if (edgeId != firstID && edgeId != lastID) {
                int y = (edgeId - x) / width();
                hSeam[x] = y;
                x++;
            }

        }
        return hSeam;
    }

    public int[] findVerticalSeam() {
        int[] vSeam = new int[height()];
        int firstID = width() * height();
        int lastID = width() * height() + 1;

        DijkstraSP sp = new DijkstraSP(graphV, firstID);

        int y = 0;
        for (DirectedEdge edge : sp.pathTo(lastID)) {
            int edgeId = edge.to();
            if (edgeId != firstID && edgeId != lastID) {
                int x = edgeId - y * width();
                vSeam[y] = x;
                y++;
            }
        }
        return vSeam;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (picture.height() <= 1) {
            throw new java.lang.IllegalArgumentException();
        }

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
        if (picture.height() <= 1) {
            throw new java.lang.IllegalArgumentException();
        }

        Picture newPicture = new Picture(width() - 1, height());
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height() - 1; y++) {
                if (x < seam[y]) {
                    newPicture.set(x, y, picture.get(x, y));
                } else {
                    newPicture.set(x, y, picture.get(x + 1, y));
                }
            }
        }
        picture = newPicture;
    }
}