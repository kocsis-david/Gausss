import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.awt.image.DataBufferByte;
import java.awt.image.BufferedImageOp;


public class GaussFunctions {
    public static double[][] Calculate(int lenght, double weight)
    {
        double[][] Kernel = new double [lenght][lenght];
        double sumTotal = 0;


        int kernelRadius = lenght / 2;
        double distance = 0;


        double calculatedEuler = 1.0 /
                (2.0 * Math.PI * Math.pow(weight, 2));


        for (int filterY = -kernelRadius;
             filterY <= kernelRadius; filterY++)
        {
            for (int filterX = -kernelRadius;
                 filterX <= kernelRadius; filterX++)
            {
                distance = ((filterX * filterX) +
                        (filterY * filterY)) /
                        (2 * (weight * weight));


                Kernel[filterY + kernelRadius][filterX + kernelRadius] =calculatedEuler * Math.exp(-distance);

                sumTotal += Kernel[filterY + kernelRadius][filterX + kernelRadius];
            }
        }


        for (int y = 0; y < lenght; y++)
        {
            for (int x = 0; x < lenght; x++)
            {
                Kernel[y][ x] = Kernel[y][ x] * (1.0 / sumTotal);
            }
        }


        return Kernel;
    }




/*    public static BufferedImage differenceOfGaussianFilter(BufferedImage sourceBitmap, int matrixSize, double weight1, double weight2) {
        double[][] kernel1 = Calculate(matrixSize, (weight1 > weight2 ? weight1 : weight2));
        double[][] kernel2 = Calculate(matrixSize, (weight1 > weight2 ? weight2 : weight1));

        BufferedImageOp grayscaleOp = new BufferedImageOp() {
            public BufferedImage filter(BufferedImage src, BufferedImage dest) {
                dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
                return dest;
            }
        };



        BufferedImage grayscaleImage = grayscaleOp.filter(sourceBitmap, null);

        WritableRaster sourceRaster = sourceBitmap.getRaster();
        DataBuffer sourceDataBuffer = sourceRaster.getDataBuffer();
        DataBuffer grayscaleDataBuffer = grayscaleImage.getRaster().getDataBuffer();

        if (sourceDataBuffer instanceof DataBufferInt && grayscaleDataBuffer instanceof DataBufferByte) {
            int[] pixelBuffer = ((DataBufferInt) sourceDataBuffer).getData();
            byte[] grayscaleBuffer = ((DataBufferByte) grayscaleDataBuffer).getData();

            double rgb = 0;

            int filterOffset = (matrixSize - 1) / 2;
            int calcOffset = 0;

            for (int source = 0, dst = 0; source < grayscaleBuffer.length && dst + 4 < pixelBuffer.length; source++, dst += 4) {
                rgb = grayscaleBuffer[source] & 0xFF;

                double color1 = 0.0;
                double color2 = 0.0;

                for (int filterY = -filterOffset; filterY <= filterOffset; filterY++) {
                    for (int filterX = -filterOffset; filterX <= filterOffset; filterX++) {
                        int pixelX = (dst / 4) % sourceBitmap.getWidth() + filterX;
                        int pixelY = (dst / 4) / sourceBitmap.getWidth() + filterY;

                        if (pixelX >= 0 && pixelX < sourceBitmap.getWidth() && pixelY >= 0 && pixelY < sourceBitmap.getHeight()) {
                            calcOffset = pixelX + pixelY * sourceBitmap.getWidth();
                            color1 += (grayscaleBuffer[calcOffset] & 0xFF) * kernel1[filterY + filterOffset][filterX + filterOffset];
                            color2 += (grayscaleBuffer[calcOffset] & 0xFF) * kernel2[filterY + filterOffset][filterX + filterOffset];
                        }
                    }
                }

                color1 = color1 - color2;
                color1 = (color1 >= weight1 - weight2 ? 255 : 0);

                pixelBuffer[dst] = (int) color1 << 16 | (int) color1 << 8 | (int) color1;
            }

            return sourceBitmap;
        } else {
            // Handle other data buffer types as needed.
            return sourceBitmap;
        }
    }*/

}
