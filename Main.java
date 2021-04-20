package pl.rafal;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, SQLException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        OknoLogowania oknoLogowania = new OknoLogowania();
        Logowanie logowanie = new Logowanie(oknoLogowania);
        OknoGry frame = new OknoGry(logowanie);

        MapaKafelkowCalych mapaKafelkowCalych = new MapaKafelkowCalych();
        MapaKafelkowKolizji mapaKafelkowKolizji = new MapaKafelkowKolizji();
        MapaKafelkowKanwa mapaKafelkowKanwa = new MapaKafelkowKanwa();
        Panel panel = new Panel(mapaKafelkowCalych, mapaKafelkowKolizji, mapaKafelkowKanwa, logowanie);
        Gracz gracz = new Gracz(panel, logowanie);
        mapaKafelkowCalych.gracz = gracz;
        mapaKafelkowKanwa.gracz = gracz;

        panel.gracz = gracz;
        try {
            gracz.pobierzSwojeXY();
            gracz.pobierzPoziomMapy();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("nie moze pobrac x,y lub poziomu mapy");
        }


        Czat czat = new Czat(frame, logowanie, panel.graczeOnline, mapaKafelkowCalych, gracz);
        panel.czat = czat;

        Npc handlarz = new Npc("Strażnik",20,12, panel.mapaKafelkowKolizji, gracz);
        Npc handlarz2 = new Npc("Strażnik",40,15, panel.mapaKafelkowKolizji, gracz);

        panel.handlarz = handlarz;
        panel.handlarz2 = handlarz2;

        ///////////////////////////////

        frame.addKeyListener(panel);

        frame.add(czat.textField);
        frame.add(panel);
        frame.setFocusable(true);

        frame.setSize(screenSize.width,screenSize.height);
        frame.setResizable(false);

        frame.setVisible(true);

        //////////////////////////////

        Thread threadCzat = new Thread(czat);
        Thread threadGracz = new Thread(gracz);

        threadCzat.start();
        threadGracz.start();
        
    }
}
