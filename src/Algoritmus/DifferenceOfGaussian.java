package Algoritmus;
import Objektumok.*;
public class DifferenceOfGaussian {


    public static void CannyEdge(Raster img) {
    int height=img.height;
    int width=img.width;
    int edges[][]=new int[width][height];
    int maxGrad=-1;
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {

                int balfelso = (int) img.get(i - 1, j - 1).z;  //szomszédok lekérdezése
                int baloldal = (int) img.get(i - 1, j).z;
                int balalso = (int) img.get(i - 1, j + 1).z;
                int felette = (int) img.get(i , j - 1).z;
                int alatta = (int) img.get(i , j + 1).z;

                int jobbfelso =(int) img.get(i + 1, j - 1).z;
                int jobboldal = (int) img.get(i + 1, j ).z;
                int jobbalso =(int) img.get(i + 1, j + 1).z;

                int intensx = (int) (((-1 * balfelso)  + (1 * balalso)) + ((-2 * felette)  + (2 * alatta)) + ((-1 * jobbfelso)  + (1 * jobbalso))); //x tengely szerinti intenzitás

                int intensy =  ((-1 * balfelso) + (-2 * baloldal) + (-1 * balalso)) + ((1 * jobbfelso) + (2 * jobboldal) + (1 * jobbalso)); //y tengely szerinti intenzitás

                double gval = Math.sqrt((intensx * intensx) + (intensy * intensy)); //gradiens érték
                int grad = (int) gval;  //gradiens érték egész része

                if(maxGrad < grad) {
                    maxGrad = grad;
                }
                edges[i][j] = grad;
            }
        }

        double s = 255.0 / maxGrad; //skála érték

        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                int edgeColor = edges[i][j];
                edgeColor = (int)(edgeColor * s);      //skálázás
                img.set(i, j, edgeColor);          //képbe írás
            }
        }

    }

    public static void GaussBlur(Raster img, int weight) {

        int mHeight=img.height;
        int mWidth=img.width;
        int i,x,y;
        float sum, div, coeff[];
        Cell c, p; // aktuális cella, szomszéd cella
        if(weight<1) return;
        int myFilterHalf=weight*2;
        int myFilterSize=myFilterHalf*2+1;
        coeff=new float[myFilterSize];
        if(coeff==null) return;
        for(i=0; i<myFilterSize; i++) {
            x=i-myFilterHalf;  // x távolság a középponttól
            coeff[i]= (float) Math.exp(-0.5*x*x/(weight*weight));  //gauss függvény
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

    }


    public static Raster DifferenceOfBruls(Raster blur1, Raster blur2) {
        Raster gauss = new Raster(blur1.height, blur1.width);
        for (int y = 0; y < blur1.height; y++) {
            for (int x = 0; x < blur2.width; x++) {

                gauss.rows[y][x].z = blur2.rows[y][x].z-blur1.rows[y][x].z+128;  //különbség
            }
        }
        return gauss;

    }

    public static void GaussBlur2(Raster img, int weight) {

        int mHeight=img.height;
        int mWidth=img.width;
        int i,x,y;
        float sum, div, coeff[];
        Cell c, p; // aktuális cella, szomszéd cella
        if(weight<1) return;
        int myFilterHalf=weight*2;
        int myFilterSize=myFilterHalf*2+1;
        coeff=new float[myFilterSize];

        if(coeff==null) return;
        for(i=0; i<myFilterSize; i++) {
            x=i-myFilterHalf;  // x távolság a középponttól
            coeff[i]=  (float) Math.exp(-0.5*x*x/(weight*weight));  //gauss függvény
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

        //-------- 1D sor filter
        for(x=0; x<mWidth; x++) // oszlopok
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


    }
}