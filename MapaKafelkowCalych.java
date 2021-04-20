package pl.rafal;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MapaKafelkowCalych {
    int wielkoscKafelka = 60;
    int x = 0;
    int y = 0;
    int liczbaKafelkow = 18;

    Gracz gracz;

    BufferedImage kafelkiImage[] = new BufferedImage[liczbaKafelkow];
    int tabPoziom0[][] = new int[2500][2500];
    int tabPoziom1[][] = new int[2500][2500];
    Scanner scannerPoziom0;
    Scanner scannerPoziom1;

    MapaKafelkowCalych(){

        try {
            scannerPoziom0 = new Scanner(new File("C:\\Users\\Rafal\\Gra\\src\\pl\\rafal\\tabliceMap\\tablicaMapy.txt"));
            scannerPoziom1 = new Scanner(new File("C:\\Users\\Rafal\\Gra\\src\\pl\\rafal\\tabliceMap\\tablicaMapyPoziom1.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void wczytajKafelki(){
        for (int i = 0; i < kafelkiImage.length; i++) {
            try {
                this.kafelkiImage[i] = ImageIO.read(new File("C:\\Users\\Rafal\\Gra\\src\\pl\\rafal\\obrazki\\" + String.valueOf(i) + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    void wyswietlPoziom0(Graphics g){
        for (int i = gracz.polozenieNaTablicyY-9; i < gracz.polozenieNaTablicyY+10; i++) {
            for (int j = gracz.polozenieNaTablicyX-10; j < gracz.polozenieNaTablicyX+20; j++) {
                g.drawImage(this.kafelkiImage[tabPoziom0[i][j]], (this.x + j) * this.wielkoscKafelka, (this.y + i) * this.wielkoscKafelka, null);
            }
        }
    }
    void wyswietlPoziom1(Graphics g){
        for (int i = gracz.polozenieNaTablicyY-9; i < gracz.polozenieNaTablicyY+10; i++) {
            for (int j = gracz.polozenieNaTablicyX-10; j < gracz.polozenieNaTablicyX+20; j++) {
                g.drawImage(this.kafelkiImage[tabPoziom1[i][j]], (this.x + j) * this.wielkoscKafelka, (this.y + i) * this.wielkoscKafelka, null);
            }
        }
    }


}
