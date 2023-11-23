package Objektumok;

import java.io.File;
import java.util.Date;

public class Futtatas implements Comparable<Futtatas> {
File nev;
int sigma1;
int sigma2;
Date date;

    public Futtatas(File nev, int sigma1, int sigma2) {
        this.nev = nev;
        this.sigma1 = sigma1;
        this.sigma2 = sigma2;
        this.date = new Date();
    }
    public String toString(){
        return nev.getName()+" "+sigma1+" "+sigma2+" "+date.toString();
    }
    @Override
    public int compareTo(Futtatas o) {
        return date.compareTo(o.date);
    }
}
