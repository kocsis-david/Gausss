import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class DifferenceOfGaussian {
    public static Raster GaussBlur(Raster img, int radius) {

        int mHeight=img.height;
        int mWidth=img.width;
        int i,x,y;
        float sum, div, coeff[];
        Cell c, p; // aktuális cella, szomszéd cella
        if(radius<1) return img;
        int myFilterHalf=radius*2;
        int myFilterSize=myFilterHalf*2+1;
        coeff=new float[myFilterSize];
        if(coeff==null) return img;
        for(i=0; i<myFilterSize; i++) {
            x=i-myFilterHalf;
            coeff[i]= (float) Math.exp(-0.5*x*x/(radius*radius));
        }

        //-------- 1D oszlop filter
        for(y=0; y<mHeight; y++) // sorok ==
        {
             // kezdő cella
            for(x=0; x<mWidth; x++) // sor pixelei
            {
                sum=div=0;
                c=img.get(x, y); // aktuális cella
                for(i=0;  i<=myFilterSize-1;  i++) // 1D oszlop filter ||
                {
                    p=img.get(x, y+i-myFilterHalf); // cella lekérdezés
                    if(p!=null && p.z!=0)
                    {
                        sum+=p.z * coeff[i];  div+=coeff[i]; // egyszerű súlyozás
                    }
                }
                if(div!=0) c.s=sum/div;  else c.s=0; // eredmény tárolás s-ben
            } // end for x
        } // end for y
        i=0;
        //-------- 1D sor filter
        for(x=0; x<mWidth; x++) // oszlopok ||
        {
            for(y=0; y<mHeight; y++) // oszlop pixelei
            {
                sum=0;
                div=0;
                c=img.get(x, y); // aktuális cella
                for(i=0;  i<=myFilterSize-1;  i++) // 1D sor filter ==
                {
                    p=img.get(x+i-myFilterHalf, y); // cella lekérdezés
                    if(p!=null && p.s!=0)
                    {
                        sum+=p.s * coeff[i];  div+=coeff[i]; // egyszerű súlyozás
                    }
                }
                if(div!=0) c.z=sum/div;  else c.z=0; // eredmény tárolás z-ben
            } // end for x
        } // end for y
        return img;
    }


    public static Raster GaussianDiff(Raster image1, Raster image2) {
        Raster gauss = new Raster(image1.height, image1.width);



        return null ;
    }

    public static Raster DifferenceOfBruls(Raster blur1, Raster blur2) {
        Raster gauss = new Raster(blur1.height, blur1.width);
        for (int y = 0; y < blur1.height; y++) {
            for (int x = 0; x < blur2.width; x++) {
                gauss.rows[y][x] = new Cell( blur2.rows[y][x].z-blur1.rows[y][x].z+128, 0.0f);
            }
        }
        return gauss;

    }
}