import java.awt.image.BufferedImage;
import java.awt.Color;

public class MandelbrotImage
{
  public static BufferedImage mandelbrotImage(int width, int height, double xMin, double xMax, double yMin, double yMax, int maxIterations)
  {
    BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    
    double xCenter = (xMin + xMax)/2;
    double yCenter = (yMin + yMax)/2;
    
    for(int i = 0; i < bi.getWidth(); i++)
    {
      for(int j = 0; j < bi.getHeight(); j++)
      {
        double x = (i - bi.getWidth()/2) * (xMax-xMin) / (bi.getWidth()/2) + xCenter;
        double y = (j - bi.getHeight()/2) * (yMax-yMin) / (bi.getHeight()/2) + yCenter;
        int iterations = ComplexNumber.mandelbrotColor(new ComplexNumber(x,y), maxIterations, 2);
        bi.setRGB(i,j,iterations == maxIterations ? 0 : Color.HSBtoRGB((float)iterations / maxIterations,1,1));
      }
    }
    
    return bi;
  }
}
