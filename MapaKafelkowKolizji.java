package pl.rafal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MapaKafelkowKolizji {
    int x = 0;
    int y = 0;
    int tabPoziom0[][] = new int[2500][2500];
    int tabPoziom1[][] = new int[2500][2500];
    Scanner scannerPoziom0;
    Scanner scannerPoziom1;

    MapaKafelkowKolizji(){
        try {
            scannerPoziom0 = new Scanner(new File("C:\\Users\\Rafal\\Gra\\src\\pl\\rafal\\tabliceMap\\tablicaKolizji.txt"));
            scannerPoziom1 = new Scanner(new File("C:\\Users\\Rafal\\Gra\\src\\pl\\rafal\\tabliceMap\\tablicaMapyKolizjiPoziom1.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void wczytajMape(){
        for (int i = 0; i < tabPoziom0.length; i++) {
            for (int j = 0; j < tabPoziom0[0].length; j++) {
                if(scannerPoziom0.hasNext()) {
                    tabPoziom0[i][j] = scannerPoziom0.nextInt();
                }
            }
        }
        for (int i = 0; i < tabPoziom1.length; i++) {
            for (int j = 0; j < tabPoziom1[0].length; j++) {
                if(scannerPoziom1.hasNext()) {
                    tabPoziom1[i][j] = scannerPoziom1.nextInt();
                }
            }
        }
    }

}
