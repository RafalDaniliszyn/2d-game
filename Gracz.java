package pl.rafal;

import org.json.JSONObject;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Gracz implements Runnable{
    BufferedImage skin;
    BufferedImage skins[] = new BufferedImage[4];
    BufferedImage death;
    int polozenieNaMapieX = 10;
    int polozenieNaMapieY = 7;
    int polozenieNaTablicyX = 10;
    int polozenieNaTablicyY = 7;

    boolean status;

    int poziomMapy;

    boolean smierc = false;
    Color kolorZycia = Color.green;

    boolean gora = false;
    boolean dol = false;
    boolean lewo = false;
    boolean prawo = false;

    int zycie = 500;
    int zycieFull = zycie;

    Panel panel;

    Logowanie logowanie;
    String name;
    int id;
    String wiadomosc;

    ServerConnection serverConnection;

    public boolean getStatus() {
        return status;
    }

    public String getWiadomosc() {
        return wiadomosc;
    }

    public int getPoziomMapy() {
        return poziomMapy;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getPolozenieNaTablicyX() {
        return polozenieNaTablicyX;
    }

    public int getPolozenieNaTablicyY() {
        return polozenieNaTablicyY;
    }

    Gracz(Panel panel, Logowanie logowanie) {
        serverConnection = new ServerConnection();
        this.logowanie = logowanie;
        this.panel = panel;
        this.name = logowanie.name;
        this.status = logowanie.status;

        try {
            this.skins[0] = ImageIO.read(new File("C:\\Users\\Rafal\\Gra\\src\\pl\\rafal\\skiny\\graczGora.png"));
            this.skins[1] = ImageIO.read(new File("C:\\Users\\Rafal\\Gra\\src\\pl\\rafal\\skiny\\graczDol.png"));
            this.skins[2] = ImageIO.read(new File("C:\\Users\\Rafal\\Gra\\src\\pl\\rafal\\skiny\\graczLewo.png"));
            this.skins[3] = ImageIO.read(new File("C:\\Users\\Rafal\\Gra\\src\\pl\\rafal\\skiny\\graczPrawo.png"));
            this.skin = this.skins[0];
            this.death = ImageIO.read(new File("C:\\Users\\Rafal\\Gra\\src\\pl\\rafal\\skiny\\death.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Gracz(){
        try {
            this.skins[0] = ImageIO.read(new File("C:\\Users\\Rafal\\Gra\\src\\pl\\rafal\\skiny\\graczGora.png"));
            this.skins[1] = ImageIO.read(new File("C:\\Users\\Rafal\\Gra\\src\\pl\\rafal\\skiny\\graczDol.png"));
            this.skins[2] = ImageIO.read(new File("C:\\Users\\Rafal\\Gra\\src\\pl\\rafal\\skiny\\graczLewo.png"));
            this.skins[3] = ImageIO.read(new File("C:\\Users\\Rafal\\Gra\\src\\pl\\rafal\\skiny\\graczPrawo.png"));
            this.skin = this.skins[0];
            this.death = ImageIO.read(new File("C:\\Users\\Rafal\\Gra\\src\\pl\\rafal\\skiny\\death.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void pobierzSwojeXY() throws IOException {
        JSONObject jsonObject;
        jsonObject = serverConnection.graczeOnline(logowanie.id);
        przeniesGracza(jsonObject.getInt("x"), jsonObject.getInt("y"));
    }
    void pobierzPoziomMapy() throws IOException {
       JSONObject jsonObject;
       jsonObject = serverConnection.graczeOnline(logowanie.id);
       this.poziomMapy = jsonObject.getInt("poziomMapy");

    }

    void zmianaPoziomuMapyWyzej(){
        this.poziomMapy +=1;
        panel.mapaKafelkowCalych.y += 1;
        panel.mapaKafelkowKanwa.y += 1;
        panel.mapaKafelkowKolizji.y += 1;
        panel.gracz.polozenieNaTablicyY -= 1;
    }
    void zmianaPoziomuMapyNizej(){
        this.poziomMapy -=1;
        panel.mapaKafelkowCalych.y -= 1;
        panel.mapaKafelkowKanwa.y -= 1;
        panel.mapaKafelkowKolizji.y -= 1;
        panel.gracz.polozenieNaTablicyY += 1;
    }

    void wejdzLubZejdz(){
        if (this.poziomMapy == 1 && panel.mapaKafelkowKanwa.tabPoziom0[this.polozenieNaTablicyY][this.polozenieNaTablicyX] == 8){
            this.zmianaPoziomuMapyNizej();
        }
        if (this.poziomMapy == 0 && panel.mapaKafelkowKanwa.tabPoziom0[this.polozenieNaTablicyY][this.polozenieNaTablicyX] == 8){
            this.zmianaPoziomuMapyWyzej();
        }
    }

    void przeniesGracza(int x, int y) {
        panel.mapaKafelkowCalych.x = (x-10) * -1;
        panel.mapaKafelkowCalych.y = (y-7) * -1;

        panel.mapaKafelkowKolizji.x = (x-10) * -1;
        panel.mapaKafelkowKolizji.y = (y-7) * -1;

        panel.mapaKafelkowKanwa.x = (x-10) * -1;
        panel.mapaKafelkowKanwa.y = (y-7) * -1;

        this.polozenieNaTablicyX += x-10;
        this.polozenieNaTablicyY += y-7;

    }
    void sprawdzSmierc(){
        if (this.zycie <= 0){
            this.smierc = true;
            this.zycie = 0;
        }
        if (this.zycie < this.zycieFull/2){
            this.kolorZycia = Color.red;
        }
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

    void chodzenie(KeyEvent e){
        if (e.getKeyChar() == 'w'){
            this.gora = true;
            this.dol = false;
            this.lewo = false;
            this.prawo = false;
        }
        if (e.getKeyChar() == 's'){
            this.gora = false;
            this.dol = true;
            this.lewo = false;
            this.prawo = false;
        }
        if (e.getKeyChar() == 'a'){
            this.gora = false;
            this.dol = false;
            this.lewo = true;
            this.prawo = false;
        }
        if (e.getKeyChar() == 'd'){
            this.gora = false;
            this.dol = false;
            this.lewo = false;
            this.prawo = true;
        }
    }

    void rysuj(Graphics g) {
        g.setFont(new Font("Serif", Font.BOLD, 20));
        g.setColor(this.kolorZycia);
        g.drawImage(this.skin,(polozenieNaMapieX * 60), polozenieNaMapieY * 60, null);
    }

    @Override
    public void run() {
        while (true){
            sprawdzSmierc();
            if (this.smierc == false) {
                zmianaKierunkuIwizerunku();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
