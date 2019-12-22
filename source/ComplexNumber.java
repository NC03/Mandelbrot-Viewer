/**
 * Complex Number
 * 
 * @author NC
 * @version 1.0.0
 */
public final class ComplexNumber {
    /**
     * Real component of complex number
     */
    private double real;
    /**
     * Imaginary component of complex number
     */
    private double imaginary;

    /**
     * 
     * @param real      Real component of complex number
     * @param imaginary Imaginary component of complex number
     */
    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * @return Real component of complex number
     */
    public double getReal() {
        return real;
    }

    /**
     * @return Imaginary component of complex number
     */
    public double getImaginary() {
        return imaginary;
    }

    /**
     * 
     * @param a First term to be added
     * @param b Second term to be added
     * @return The sum of the two terms
     */
    public static ComplexNumber add(ComplexNumber a, ComplexNumber b) {
        return new ComplexNumber(a.getReal() + b.getReal(), a.getImaginary() + b.getImaginary());
    }

    /**
     * @param a The complex number to be squared
     * @return A new complex number with values of the argument multiplied by itself
     */
    public static ComplexNumber square(ComplexNumber a) {
        return new ComplexNumber(a.getReal() * a.getReal() - a.getImaginary() * a.getImaginary(),
                2 * a.getReal() * a.getImaginary());
    }

    /**
     * @param a The complex number to find the absolute value of
     * @return The absolute value of the complex number
     */
    public static double abs(ComplexNumber a) {
        return Math.sqrt(a.getReal() * a.getReal() + a.getImaginary() * a.getImaginary());
    }

}
