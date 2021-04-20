package pl.rafal;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GraczeOnline extends Gracz{

    MapaKafelkowCalych mapaKafelkowCalych;
    int liczbaGraczy;
    Logowanie logowanie;

    int x;
    int y;
    int poziomMapy = 0;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    GraczeOnline(MapaKafelkowCalych mapaKafelkowCalych, Logowanie logowanie) {
        this.mapaKafelkowCalych = mapaKafelkowCalych;
        this.logowanie = logowanie;
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

    void rysujGraczyOnline(Graphics g, int x, int y){
        g.drawImage(this.skin, (this.mapaKafelkowCalych.x + x) * 60, (this.mapaKafelkowCalych.y + y) * 60, null);
    }

}
