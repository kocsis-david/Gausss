package Objektumok;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Futtatas implements Comparable<Futtatas> {

    private String blur1;
    private String blur2;
    private String dog;
    private String canny;
    private int sigma1;
    private int sigma2;
    private String date;

    public Futtatas(String blur1, String blur2, String dog, String canny, int sigma1, int sigma2, String date) {
        this.blur1 = blur1;
        this.blur2 = blur2;
        this.dog = dog;
        this.canny = canny;
        this.sigma1 = sigma1;
        this.sigma2 = sigma2;
        this.date = date;
    }

    public static LinkedList<Futtatas> futasokFilebololvas() {
        LinkedList<Futtatas> futasok = new LinkedList<>();
        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get("futasok.json"));

            // convert JSON array to list of books
            List<Futtatas> futas = Arrays.asList(gson.fromJson(reader, Futtatas[].class));

            reader.close();

            futasok.addAll(futas);

            return futasok;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Még nem történtek futások!");
        }
        return futasok;
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
        return "outpics/" + blur1;
    }

    public String getBlur2() {
        return "outpics/" + blur2;
    }

    public String getDog() {
        return "outpics/" + dog;
    }

    public String getCanny() {
        return "outpics/" + canny;
    }

    public static void futasokFilebaIr(LinkedList futasok){
        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a writer
            Writer writer = Files.newBufferedWriter(Paths.get("futasok.json"));

            // convert books object to JSON file
            gson.toJson(futasok, writer);
            // close writer
            writer.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Hiba a fájl írásakor!");
        }
    }
}

