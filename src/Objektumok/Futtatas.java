package Objektumok;

import javax.swing.*;
import java.io.*;
import java.util.Date;

public class Futtatas implements Serializable, Comparable<Futtatas> {
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

    // Implement serialization methods
    public void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(blur1);
        out.writeObject(blur2);
        out.writeObject(dog);
        out.writeObject(canny);
        out.writeInt(sigma1);
        out.writeInt(sigma2);
        out.writeObject(date);
    }

    public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        blur1 = (File) in.readObject();
        blur2 = (File) in.readObject();
        dog = (File) in.readObject();
        canny = (File) in.readObject();
        sigma1 = in.readInt();
        sigma2 = in.readInt();
        date = (String) in.readObject();
    }
    public static void writeToFile(Futtatas obj, String filename, boolean append) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename, append))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Hiba a mentés során!");
        }
    }
}
