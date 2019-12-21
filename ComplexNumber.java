public class ComplexNumber
{
  private double real;
  private double imaginary;

  public ComplexNumber(double r, double i)
  {
    real = r;
    imaginary = i;
  }

  public double getReal()
  {
    return real;
  }
  public double getImaginary()
  {
    return imaginary;
  }

  public static ComplexNumber add(ComplexNumber a, ComplexNumber b)
  {
    return new ComplexNumber(a.getReal() + b.getReal(),a.getImaginary() + b.getImaginary());
  }
  public static ComplexNumber square(ComplexNumber a)
  {
    return new ComplexNumber(a.getReal() * a.getReal()-a.getImaginary() * a.getImaginary(), 2 * a.getReal() * a.getImaginary());
  }
  public static double abs(ComplexNumber a)
  {
    return Math.sqrt(a.getReal() * a.getReal() + a.getImaginary() * a.getImaginary());
  }
  public static int mandelbrotColor(ComplexNumber c, int maxIterations, double divergenceRadius)
  {
    ComplexNumber t = new ComplexNumber(c.getReal(),c.getImaginary());
    for(int i = 0; i < maxIterations; i++)
    {
      double abs = abs(t);
      if(abs > divergenceRadius)
      {
        return i;
      }else
      {
        t = add(square(t),c);
      }
    }
    return maxIterations;
  }
}
