import java.awt.image.BufferedImage;
import java.awt.Color;

/**
 * Mandelbrot Set Image Generator
 * 
 * @author NC
 * @version 1.0.0
 */
public class MandelbrotImage {
    /**
     * Generates the Mandelbrot Set Image given parameters
     * 
     * @param width  The output image width in pixels
     * @param height The output image height in pixels
     * @param xMin   The minimum value of the domain of real numbers
     * @param xMax   The maximum value of the domain of real numbers
     * @param yMin   The minimum value of the domain of complex numbers
     * @param yMax   The maximum value of the domain of complex numbers
     * @see BufferedImage
     * @param maxIterations The maximum number of iterations to determine the
     *                      divergence or convergence of a point
     * @return An Image with the Mandelbrot Set
     */
    public static BufferedImage mandelbrotImage(int width, int height, double xMin, double xMax, double yMin,
            double yMax, int maxIterations) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                int iterations = mandelbrotColor(
                        new ComplexNumber(map(i, 0, bi.getWidth(), xMin, xMax), map(j, 0, bi.getHeight(), yMax, yMin)),
                        maxIterations, 2);
                bi.setRGB(i, j,
                        iterations == maxIterations ? 0 : Color.HSBtoRGB((float) iterations / maxIterations, 1, 1));
            }
        }

        return bi;
    }

    /**
     * Determines the color of a point based upon the divergence or convergence of
     * the point
     * 
     * @param c                The {@link ComplexNumber} point to evaluate
     * @param maxIterations    The maximum number of iterations to determine the
     *                         divergence or convergence of the point
     * @param divergenceRadius A radius to stop evaluations outside of for easier
     *                         computations
     * 
     */
    public static int mandelbrotColor(ComplexNumber c, int maxIterations, double divergenceRadius) {
        ComplexNumber t = new ComplexNumber(c.getReal(), c.getImaginary());
        for (int i = 0; i < maxIterations; i++) {
            double abs = ComplexNumber.abs(t);
            if (abs > divergenceRadius) {
                return i;
            } else {
                t = ComplexNumber.add(ComplexNumber.square(t), c);
            }
        }
        return maxIterations;
    }

    /**
     * Linear interpolation of double values
     * @param val The input value to interpolate
     * @param min The minimum value of the input range
     * @param max The maximum value of the input range
     * @param newMin The minimum value of the output range
     * @param newMax The maximum value of the output range
     * @return The double value interpolated from the input range to the output range
     * 
     */
    public static double map(double val, double min, double max, double newMin, double newMax) {
        double out = newMin + (val - min) / (max - min) * (newMax - newMin);
        return out;
    }
}
