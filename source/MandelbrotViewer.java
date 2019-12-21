import javax.swing.*;
import java.awt.*;
import java.lang.Thread;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MandelbrotViewer extends JFrame {
    private static final long serialVersionUID = 1L;
    private double[][] complexPlaneCoordinates = { { -1, -1 }, { 1, 1 } };
    private int[][] draggingCorners = new int[2][2];
    private int[][] bounds = new int[2][2];
    private boolean showDraggingBox = false;
    private BufferedImage mb;

    public static void main(String[] args) {
        System.out.println(map(2, 1, 5, 5, 1));
        MandelbrotViewer mv = new MandelbrotViewer();
    }

    public MandelbrotViewer() {
        super("MandelbrotViewer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(600, 400);
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                System.out.println("resize");
                genImage();
            }
        });
        addMouseListener(new MouseListener() {
            public void mouseExited(MouseEvent me) {

            }

            public void mouseEntered(MouseEvent me) {

            }

            public void mouseReleased(MouseEvent me) {
                showDraggingBox = false;
            }

            public void mousePressed(MouseEvent me) {
                draggingCorners[0][0] = me.getX();
                draggingCorners[0][1] = me.getY();
                draggingCorners[1][0] = me.getX();
                draggingCorners[1][1] = me.getY();
                showDraggingBox = true;
            }

            public void mouseClicked(MouseEvent me) {

            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            public void mouseMoved(MouseEvent me) {

            }

            public void mouseDragged(MouseEvent me) {
                draggingCorners[1][0] = me.getX();
                draggingCorners[1][1] = me.getY();
                repaint();
            }

            public void mouseEntered(MouseEvent me) {

            }

            public void mouseExited(MouseEvent me) {

            }
        });
        addKeyListener(new KeyListener() {
            public void keyReleased(KeyEvent ke) {

            }

            public void keyPressed(KeyEvent ke) {

            }

            public void keyTyped(KeyEvent ke) {
                if (("" + ke.getKeyChar()).equals("\n")) {
                    showDraggingBox = false;
                    double[][] newArr = new double[2][2];// Invert y to min/max because of differing axis directions

                    newArr[0][0] = map(Math.min(draggingCorners[0][0], draggingCorners[1][0]), bounds[0][0],
                            bounds[1][0], complexPlaneCoordinates[0][0], complexPlaneCoordinates[1][0]);

                    newArr[1][0] = map(Math.max(draggingCorners[0][0], draggingCorners[1][0]), bounds[0][0],
                            bounds[1][0], complexPlaneCoordinates[0][0], complexPlaneCoordinates[1][0]);

                    newArr[0][1] = map(Math.max(draggingCorners[0][1], draggingCorners[1][1]),
                            bounds[1][1], bounds[0][1], complexPlaneCoordinates[0][1], complexPlaneCoordinates[1][1]);

                    newArr[1][1] = map(Math.min(draggingCorners[0][1], draggingCorners[1][1]),
                            bounds[1][1], bounds[0][1], complexPlaneCoordinates[0][1], complexPlaneCoordinates[1][1]);
                    
                    // newArr[0][1] = map(Math.max(draggingCorners[0][1], draggingCorners[1][1]),
                    // bounds[0][1],
                    // bounds[1][1], complexPlaneCoordinates[1][1], complexPlaneCoordinates[0][1]);
                    
                    // newArr[1][1] = map(Math.min(draggingCorners[0][1], draggingCorners[1][1]),
                    // bounds[0][1],
                    // bounds[1][1], complexPlaneCoordinates[1][1], complexPlaneCoordinates[0][1]);
                    complexPlaneCoordinates = newArr;
                    genImage();
                    repaint();
                } else if (("" + ke.getKeyChar()).equals("o")) {
                    zoomOut();
                    genImage();
                    repaint();
                } else if (("" + ke.getKeyChar()).equals("i")) {
                    zoomIn();
                    genImage();
                    repaint();
                } else if (("" + ke.getKeyChar()).equals("a")) {
                    moveLeft();
                    genImage();
                    repaint();
                } else if (("" + ke.getKeyChar()).equals("d")) {
                    moveRight();
                    genImage();
                    repaint();
                } else if (("" + ke.getKeyChar()).equals("w")) {
                    moveUp();
                    genImage();
                    repaint();
                } else if (("" + ke.getKeyChar()).equals("s")) {
                    moveDown();
                    genImage();
                    repaint();
                } else if (("" + ke.getKeyChar()).equals("u")) {
                    makeSquare();
                    genImage();
                    repaint();
                }
            }
        });
    }

    public static double map(double val, double min, double max, double newMin, double newMax) {
        // double out = newMin + (newMin < newMax ? 1 : -1) * (val - min) / (max - min)
        // * Math.abs(newMax - newMin);
        double out = newMin + (val - min) / (max - min) * (newMax - newMin);
        return out;
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.drawImage(mb, bounds[0][0], bounds[0][1], null);

        g.setColor(new Color(0, 0, 0, (float) 0.2));

        if (showDraggingBox) {
            g.fillRect(Math.min(draggingCorners[0][0], draggingCorners[1][0]),
                    Math.min(draggingCorners[0][1], draggingCorners[1][1]),
                    Math.abs(draggingCorners[1][0] - draggingCorners[0][0]),
                    Math.abs(draggingCorners[1][1] - draggingCorners[0][1]));
        }
    }

    public void makeSquare() {
        double width = complexPlaneCoordinates[1][0] - complexPlaneCoordinates[0][0];
        double height = complexPlaneCoordinates[1][1] - complexPlaneCoordinates[0][1];
        double ratio = (double) (bounds[1][0] - bounds[0][0]) / (bounds[1][1] - bounds[0][1]);
        double newWidth = height * ratio;
        double mid = 0.5 * complexPlaneCoordinates[0][0] + 0.5 * complexPlaneCoordinates[1][0];
        complexPlaneCoordinates[0][0] = mid - newWidth / 2;
        complexPlaneCoordinates[1][0] = mid + newWidth / 2;
    }

    public void zoomOut() {
        double width = complexPlaneCoordinates[1][0] - complexPlaneCoordinates[0][0];
        double height = complexPlaneCoordinates[1][1] - complexPlaneCoordinates[0][1];
        complexPlaneCoordinates[0][0] -= width / 4;
        complexPlaneCoordinates[0][1] -= height / 4;
        complexPlaneCoordinates[1][0] += width / 4;
        complexPlaneCoordinates[1][1] += height / 4;
    }

    public void zoomIn() {
        double width = complexPlaneCoordinates[1][0] - complexPlaneCoordinates[0][0];
        double height = complexPlaneCoordinates[1][1] - complexPlaneCoordinates[0][1];
        complexPlaneCoordinates[0][0] += width / 4;
        complexPlaneCoordinates[0][1] += height / 4;
        complexPlaneCoordinates[1][0] -= width / 4;
        complexPlaneCoordinates[1][1] -= height / 4;
    }

    public void moveLeft() {
        double width = complexPlaneCoordinates[1][0] - complexPlaneCoordinates[0][0];
        double height = complexPlaneCoordinates[1][1] - complexPlaneCoordinates[0][1];
        complexPlaneCoordinates[0][0] -= width / 4;
        complexPlaneCoordinates[1][0] -= width / 4;
    }

    public void moveRight() {
        double width = complexPlaneCoordinates[1][0] - complexPlaneCoordinates[0][0];
        double height = complexPlaneCoordinates[1][1] - complexPlaneCoordinates[0][1];
        complexPlaneCoordinates[0][0] += width / 4;
        complexPlaneCoordinates[1][0] += width / 4;
    }

    public void moveUp() {
        double width = complexPlaneCoordinates[1][0] - complexPlaneCoordinates[0][0];
        double height = complexPlaneCoordinates[1][1] - complexPlaneCoordinates[0][1];
        complexPlaneCoordinates[0][1] += height / 4;
        complexPlaneCoordinates[1][1] += height / 4;
    }

    public void moveDown() {
        double width = complexPlaneCoordinates[1][0] - complexPlaneCoordinates[0][0];
        double height = complexPlaneCoordinates[1][1] - complexPlaneCoordinates[0][1];
        complexPlaneCoordinates[0][1] -= height / 4;
        complexPlaneCoordinates[1][1] -= height / 4;
    }

    public void genImage() {
        bounds[0][0] = getWidth() / 8;
        bounds[0][1] = getHeight() * 2 / 8;
        bounds[1][0] = getWidth() * 7 / 8;
        bounds[1][1] = getHeight() * 7 / 8;
        mb = MandelbrotImage.mandelbrotImage(bounds[1][0] - bounds[0][0], bounds[1][1] - bounds[0][1],
                complexPlaneCoordinates[0][0], complexPlaneCoordinates[1][0], complexPlaneCoordinates[0][1],
                complexPlaneCoordinates[1][1], 200);
    }

}