package Objektumok;

import java.io.File;
import java.util.Date;

public class Futtatas implements Comparable<Futtatas> {
File blur1;
File blur2;
File dog;
File canny;
int sigma1;
int sigma2;
String date;

    public Futtatas(File blur1, File blur2, File dog, File canny, int sigma1, int sigma2, String date) {
        this.blur1 = blur1;
        this.blur2 = blur2;
        this.dog = dog;
        this.canny = canny;
        this.sigma1 = sigma1;
        this.sigma2 = sigma2;
        this.date = date;
    }
    public String toString(){
        return blur1.getName()+";"+blur2.getName()+";"+dog.getName()+";"+canny.getName()+";"+sigma1+";"+sigma2+";"+date;
    }
    @Override
    public int compareTo(Futtatas o) {
        return date.compareTo(o.date);
    }

    public int getSigma1() {
        return sigma1;
    }

    public int getSigma2() {
        return sigma2;
    }

    public String getBlur1() {
        return "outpics/"+blur1.toString();
    }

    public String getBlur2() {
        return "outpics/"+blur2.toString();
    }

    public String getDog() {
        return "outpics/"+dog.toString();
    }

    public String getCanny() {
        return "outpics/"+canny.toString();
    }
}
