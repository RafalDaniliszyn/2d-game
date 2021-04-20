package pl.rafal;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Npc implements Runnable{
    MapaKafelkowKolizji mapaKafelkowKolizji;
    BufferedImage skin;
    BufferedImage skins[] = new BufferedImage[4];
    String name;
    boolean gora = false;
    boolean dol = false;
    boolean lewo = false;
    boolean prawo = false;

    int x;
    int y;
    int polozenieNaTablicyX;
    int polozenieNaTablicyY;

    Npc(String name, int x, int y, MapaKafelkowKolizji mapaKafelkowKolizji, Gracz gracz){
        try {

            skins[0] = ImageIO.read(new File("C:\\Users\\Rafal\\Gra\\src\\pl\\rafal\\skiny\\handlarzGora.png"));
            skins[1] = ImageIO.read(new File("C:\\Users\\Rafal\\Gra\\src\\pl\\rafal\\skiny\\handlarzDol.png"));
            skins[2] = ImageIO.read(new File("C:\\Users\\Rafal\\Gra\\src\\pl\\rafal\\skiny\\handlarzLewo.png"));
            skins[3] = ImageIO.read(new File("C:\\Users\\Rafal\\Gra\\src\\pl\\rafal\\skiny\\handlarzPrawo.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mapaKafelkowKolizji = mapaKafelkowKolizji;
        this.x = x;
        this.y = y;
        this.polozenieNaTablicyX = x;
        this.polozenieNaTablicyY = y;
        this.name = name;
    }
    Npc(){

    }

    void zmianaKierunkuIwizerunku(){
        if (gora == true){
            skin = skins[0];
        }
        if (dol == true){
            skin = skins[1];
        }
        if (lewo == true){
            skin = skins[2];
        }
        if (prawo == true){
            skin = skins[3];
        }
    }

    void chodzenie(){
        Random random = new Random();
        int los = random.nextInt(10);
        if (los == 1 && mapaKafelkowKolizji.tabPoziom0[this.polozenieNaTablicyY][this.polozenieNaTablicyX+1] != 1){
            prawo = true;
            lewo = false;
            gora = false;
            dol = false;
            this.x +=1;
            this.polozenieNaTablicyX +=1;
        }
        if (los == 2 && mapaKafelkowKolizji.tabPoziom0[this.polozenieNaTablicyY][this.polozenieNaTablicyX-1] != 1){
            lewo = true;
            prawo = false;
            gora = false;
            dol = false;
            this.x -=1;
            this.polozenieNaTablicyX -=1;
        }
        if (los == 3 && mapaKafelkowKolizji.tabPoziom0[this.polozenieNaTablicyY+1][this.polozenieNaTablicyX] != 1){
            dol = true;
            gora = false;
            lewo = false;
            prawo = false;
            this.y +=1;
            this.polozenieNaTablicyY +=1;
        }
        if (los == 4 && mapaKafelkowKolizji.tabPoziom0[this.polozenieNaTablicyY-1][this.polozenieNaTablicyX] != 1){
            gora = true;
            dol = false;
            lewo = false;
            prawo = false;
            this.y -=1;
            this.polozenieNaTablicyY -=1;
        }
        zmianaKierunkuIwizerunku();
    }

    void rysuj(Graphics g){
        g.setColor(Color.GREEN);
        g.setFont(new Font("Courier New", Font.ITALIC, 16));
        g.drawString(name, this.x*60, this.y *60);
        g.drawImage(skin,this.x *60, this.y *60, null);
    }

    void koordynujRuchy(KeyEvent e){
        if(e.getKeyChar() == 'w'){
            this.y +=1;
        }
        if(e.getKeyChar() == 'a'){
            this.x +=1;
        }
        if (e.getKeyChar() == 's'){
            this.y -=1;
        }
        if (e.getKeyChar() == 'd'){
            this.x -=1;
        }

    }

    @Override
    public void run() {
        while (true) {
            chodzenie();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
