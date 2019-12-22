import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyListener;

/**
 * Mandelbrot Set Image Viewer GUI
 * 
 * @author NC
 * @version 1.0.0
 */
public class MandelbrotViewer extends JFrame {
    /**
     * JFrame Data
     */
    private static final long serialVersionUID = 1L;
    /**
     * Complex coordinates of screen endpoints in the complex plane
     */
    private double[][] complexPlaneCoordinates = new double[2][2];
    /**
     * Coordinates of the corners of the drag interface
     */
    private int[][] draggingCorners = new int[2][2];
    /**
     * coordinates of the boundaries of the screen of the image
     */
    private int[][] bounds = new int[2][2];
    /**
     * Boolean on whether to display the dragging interface
     */
    private boolean showDraggingBox = false;
    /**
     * The image stored in a fvariable so that it is not rerendered everytime
     * {@link repaint} is called
     */
    private BufferedImage mb;
    /**
     * Boolean to cause {@link repaint} to generate the image on startup
     */
    private boolean initialize = false;

    public static void main(String[] args) {
        new MandelbrotViewer();
    }

    /**
     * Default Constructor
     */
    public MandelbrotViewer() {
        super("Mandelbrot Viewer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(600, 400);
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                genImage();
                repaint();
            }
        });
        addMouseListener(new MouseListener() {
            public void mouseExited(MouseEvent me) {

            }

            public void mouseEntered(MouseEvent me) {

            }

            public void mouseReleased(MouseEvent me) {

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
                if (("" + ke.getKeyChar()).equals("\n") && showDraggingBox) {
                    showDraggingBox = false;
                    bounds[0][0] = getWidth() / 8;
                    bounds[0][1] = getHeight() * 2 / 8;
                    bounds[1][0] = getWidth() * 7 / 8;
                    bounds[1][1] = getHeight() * 7 / 8;

                    double[][] newArr = new double[2][2];

                    newArr[0][0] = MandelbrotImage.map(Math.min(draggingCorners[0][0], draggingCorners[1][0]),
                            bounds[0][0], bounds[1][0], complexPlaneCoordinates[0][0], complexPlaneCoordinates[1][0]);

                    newArr[1][0] = MandelbrotImage.map(Math.max(draggingCorners[0][0], draggingCorners[1][0]),
                            bounds[0][0], bounds[1][0], complexPlaneCoordinates[0][0], complexPlaneCoordinates[1][0]);

                    newArr[0][1] = MandelbrotImage.map(Math.max(draggingCorners[0][1], draggingCorners[1][1]),
                            bounds[1][1], bounds[0][1], complexPlaneCoordinates[0][1], complexPlaneCoordinates[1][1]);

                    newArr[1][1] = MandelbrotImage.map(Math.min(draggingCorners[0][1], draggingCorners[1][1]),
                            bounds[1][1], bounds[0][1], complexPlaneCoordinates[0][1], complexPlaneCoordinates[1][1]);

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
                } else if ((int) ke.getKeyChar() == 7) {
                    if (showDraggingBox) {
                        showDraggingBox = false;
                    } else {
                        resetDimensions();
                    }
                }
            }
        });
    }

    /**
     * Resets the complex coordinates of the screen
     */
    public void resetDimensions() {
        bounds[0][0] = getWidth() / 8;
        bounds[0][1] = getHeight() * 2 / 8;
        bounds[1][0] = getWidth() * 7 / 8;
        bounds[1][1] = getHeight() * 7 / 8;
        complexPlaneCoordinates[0][0] = -3;
        complexPlaneCoordinates[0][1] = -1.5;
        complexPlaneCoordinates[1][0] = 2;
        complexPlaneCoordinates[1][1] = 1.5;
        makeSquare();
    }

    /**
     * Paints the JFrame
     * 
     * @param g The JFrame Graphics
     */
    @Override
    public void paint(Graphics g) {
        bounds[0][0] = getWidth() / 8;
        bounds[0][1] = getHeight() * 2 / 8;
        bounds[1][0] = getWidth() * 7 / 8;
        bounds[1][1] = getHeight() * 7 / 8;
        if (!initialize) {
            initialize = true;
            resetDimensions();
        }

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

    /**
     * Changes the complex coordinates to maintain the aspect ratio of the screen
     */
    public void makeSquare() {
        double width = complexPlaneCoordinates[1][0] - complexPlaneCoordinates[0][0];
        double height = complexPlaneCoordinates[1][1] - complexPlaneCoordinates[0][1];
        double ratio = (double) (bounds[1][0] - bounds[0][0]) / (bounds[1][1] - bounds[0][1]);
        double newWidth = height * ratio;
        double mid = 0.5 * complexPlaneCoordinates[0][0] + 0.5 * complexPlaneCoordinates[1][0];
        complexPlaneCoordinates[0][0] = mid - newWidth / 2;
        complexPlaneCoordinates[1][0] = mid + newWidth / 2;
    }

    /**
     * Zooms out on the image
     */
    public void zoomOut() {
        double width = complexPlaneCoordinates[1][0] - complexPlaneCoordinates[0][0];
        double height = complexPlaneCoordinates[1][1] - complexPlaneCoordinates[0][1];
        complexPlaneCoordinates[0][0] -= width / 4;
        complexPlaneCoordinates[0][1] -= height / 4;
        complexPlaneCoordinates[1][0] += width / 4;
        complexPlaneCoordinates[1][1] += height / 4;
    }

    /**
     * Zooms in on the image
     */
    public void zoomIn() {
        double width = complexPlaneCoordinates[1][0] - complexPlaneCoordinates[0][0];
        double height = complexPlaneCoordinates[1][1] - complexPlaneCoordinates[0][1];
        complexPlaneCoordinates[0][0] += width / 4;
        complexPlaneCoordinates[0][1] += height / 4;
        complexPlaneCoordinates[1][0] -= width / 4;
        complexPlaneCoordinates[1][1] -= height / 4;
    }

    /**
     * Translates the image to the left
     */
    public void moveLeft() {
        double width = complexPlaneCoordinates[1][0] - complexPlaneCoordinates[0][0];
        double height = complexPlaneCoordinates[1][1] - complexPlaneCoordinates[0][1];
        complexPlaneCoordinates[0][0] -= width / 4;
        complexPlaneCoordinates[1][0] -= width / 4;
    }

    /**
     * Translates the image to the right
     */
    public void moveRight() {
        double width = complexPlaneCoordinates[1][0] - complexPlaneCoordinates[0][0];
        double height = complexPlaneCoordinates[1][1] - complexPlaneCoordinates[0][1];
        complexPlaneCoordinates[0][0] += width / 4;
        complexPlaneCoordinates[1][0] += width / 4;
    }

    /**
     * Translates the image up
     */
    public void moveUp() {
        double width = complexPlaneCoordinates[1][0] - complexPlaneCoordinates[0][0];
        double height = complexPlaneCoordinates[1][1] - complexPlaneCoordinates[0][1];
        complexPlaneCoordinates[0][1] += height / 4;
        complexPlaneCoordinates[1][1] += height / 4;
    }

    /**
     * Translates the image down
     */
    public void moveDown() {
        double width = complexPlaneCoordinates[1][0] - complexPlaneCoordinates[0][0];
        double height = complexPlaneCoordinates[1][1] - complexPlaneCoordinates[0][1];
        complexPlaneCoordinates[0][1] -= height / 4;
        complexPlaneCoordinates[1][1] -= height / 4;
    }

    /**
     * Generates the image based on the current arguments
     */
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