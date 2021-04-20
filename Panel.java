package pl.rafal;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.SQLException;

public class Panel extends JPanel implements KeyListener{
    MapaKafelkowCalych mapaKafelkowCalych;
    MapaKafelkowKolizji mapaKafelkowKolizji;
    MapaKafelkowKanwa mapaKafelkowKanwa;

    Gracz gracz;
    GraczeOnline graczeOnline;
    Npc handlarz;
    Npc handlarz2;
    Potwor pajaki;
    ServerConnection serverConnection;
    boolean status;

    Czat czat;
    JSONObject gracze;

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public Dimension getPreferredSize() {
        return new Dimension(1000, 700);
    }

    Panel(MapaKafelkowCalych mapaKafelkowCalych, MapaKafelkowKolizji mapaKafelkowKolizji, MapaKafelkowKanwa mapaKafelkowKanwa, Logowanie logowanie){
        this.serverConnection = new ServerConnection();

        this.mapaKafelkowCalych = mapaKafelkowCalych;
        this.mapaKafelkowKolizji = mapaKafelkowKolizji;
        this.mapaKafelkowKanwa = mapaKafelkowKanwa;

        mapaKafelkowCalych.wczytajKafelki();
        mapaKafelkowKanwa.wczytajKafelki();


        mapaKafelkowKanwa.wczytajMape();
        mapaKafelkowCalych.wczytajMape();
        mapaKafelkowKolizji.wczytajMape();


        graczeOnline = new GraczeOnline(this.mapaKafelkowCalych, logowanie);
        this.status = logowanie.status;
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        mapaKafelkowCalych.wyswietlPoziom0(g);

        try {
           this.serverConnection.wyslijDaneGracza(gracz);

        } catch (IOException e) {
            e.printStackTrace();
        }


        if (gracz.poziomMapy == 0) {
            gracz.rysuj(g);
        }

        for (int i = 1; i <= serverConnection.liczbaGraczyOnline(); i++) {
            try {
                gracze =  serverConnection.graczeOnline(i);
                if (gracze.getInt("poziomMapy") == 0 && gracze.getInt("id") != gracz.logowanie.id && gracze.getBoolean("status") == true) {
                    graczeOnline.rysujGraczyOnline(g, gracze.getInt("x"), gracze.getInt("y"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mapaKafelkowKanwa.wyswietlPoziom0(g);
        if (gracz.poziomMapy == 1){
            mapaKafelkowCalych.wyswietlPoziom1(g);
            mapaKafelkowKanwa.wyswietlPoziom1(g);
        }
        if (mapaKafelkowKolizji.tabPoziom1[gracz.polozenieNaTablicyY][gracz.polozenieNaTablicyX] == 1) {
            mapaKafelkowCalych.wyswietlPoziom1(g);
            mapaKafelkowKanwa.wyswietlPoziom1(g);
        }

        for (int i = 1; i <=serverConnection.liczbaGraczyOnline(); i++) {
            try {
                gracze =  serverConnection.graczeOnline(i);
                if (gracze.getInt("poziomMapy") == 1
                        && mapaKafelkowKolizji.tabPoziom1[gracz.polozenieNaTablicyY][gracz.polozenieNaTablicyX] == 1
                        && gracz.poziomMapy == 0 && gracze.getBoolean("status") == true) {

                    if (gracze.getInt("id") != gracz.logowanie.id) {
                        graczeOnline.rysujGraczyOnline(g, gracze.getInt("x"), gracze.getInt("y"));
                    }
                }
                if (gracz.poziomMapy == 1 && gracze.getInt("id") != gracz.logowanie.id && gracze.getBoolean("status") == true){
                    graczeOnline.rysujGraczyOnline(g, gracze.getInt("x"), gracze.getInt("y"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if (gracz.poziomMapy == 1){
            gracz.rysuj(g);
            mapaKafelkowKanwa.wyswietlPoziom1(g);

        }

        try {
            for (int i = 1; i <= serverConnection.liczbaGraczyOnline(); i++) {
                gracze = serverConnection.graczeOnline(i);
                czat.wyswietlWiadomosc(g, gracze);
            }

        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }

            gracz.wejdzLubZejdz();

        repaint(0,0,screenSize.width,screenSize.height-150);

        if (gracz.smierc == true){
            g.setFont(new Font("Serif", Font.BOLD, 100));
            g.drawString("GAME OVER", 250,250);
            gracz.skin = gracz.death;
        }

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gracz.smierc == false) {
            gracz.chodzenie(e);
            if (gracz.poziomMapy == 0) {
                if (e.getKeyChar() == 'w') {
                    if (mapaKafelkowKolizji.tabPoziom0[gracz.polozenieNaTablicyY - 1][gracz.polozenieNaTablicyX] != 1) {
                        mapaKafelkowCalych.y += 1;
                        mapaKafelkowKanwa.y += 1;
                        mapaKafelkowKolizji.y += 1;
                        gracz.polozenieNaTablicyY -= 1;
                        handlarz.koordynujRuchy(e);
                        handlarz2.koordynujRuchy(e);
                    }
                }
                if (e.getKeyChar() == 'a') {
                    if (mapaKafelkowKolizji.tabPoziom0[gracz.polozenieNaTablicyY][gracz.polozenieNaTablicyX - 1] != 1) {
                        mapaKafelkowCalych.x += 1;
                        mapaKafelkowKanwa.x += 1;
                        mapaKafelkowKolizji.x += 1;
                        gracz.polozenieNaTablicyX -= 1;
                        handlarz.koordynujRuchy(e);
                        handlarz2.koordynujRuchy(e);

                    }
                }
                if (e.getKeyChar() == 's') {
                    if (mapaKafelkowKolizji.tabPoziom0[gracz.polozenieNaTablicyY + 1][gracz.polozenieNaTablicyX] != 1) {
                        mapaKafelkowCalych.y -= 1;
                        mapaKafelkowKanwa.y -= 1;
                        mapaKafelkowKolizji.y -= 1;
                        gracz.polozenieNaTablicyY += 1;
                        handlarz.koordynujRuchy(e);
                        handlarz2.koordynujRuchy(e);

                    }
                }
                if (e.getKeyChar() == 'd') {
                    if (mapaKafelkowKolizji.tabPoziom0[gracz.polozenieNaTablicyY][gracz.polozenieNaTablicyX + 1] != 1) {
                        mapaKafelkowCalych.x -= 1;
                        mapaKafelkowKanwa.x -= 1;
                        mapaKafelkowKolizji.x -= 1;
                        gracz.polozenieNaTablicyX += 1;
                        handlarz.koordynujRuchy(e);
                        handlarz2.koordynujRuchy(e);

                    }
                }
            }

            if (gracz.poziomMapy == 1) {
                if (e.getKeyChar() == 'w') {
                    if (mapaKafelkowKolizji.tabPoziom1[gracz.polozenieNaTablicyY - 1][gracz.polozenieNaTablicyX] != 1) {
                        mapaKafelkowCalych.y += 1;
                        mapaKafelkowKanwa.y += 1;
                        mapaKafelkowKolizji.y += 1;
                        gracz.polozenieNaTablicyY -= 1;
                        handlarz.koordynujRuchy(e);
                        handlarz2.koordynujRuchy(e);

                    }
                }
                if (e.getKeyChar() == 'a') {
                    if (mapaKafelkowKolizji.tabPoziom1[gracz.polozenieNaTablicyY][gracz.polozenieNaTablicyX - 1] != 1) {
                        mapaKafelkowCalych.x += 1;
                        mapaKafelkowKanwa.x += 1;
                        mapaKafelkowKolizji.x += 1;
                        gracz.polozenieNaTablicyX -= 1;
                        handlarz.koordynujRuchy(e);
                        handlarz2.koordynujRuchy(e);

                    }
                }
                if (e.getKeyChar() == 's') {
                    if (mapaKafelkowKolizji.tabPoziom1[gracz.polozenieNaTablicyY + 1][gracz.polozenieNaTablicyX] != 1) {
                        mapaKafelkowCalych.y -= 1;
                        mapaKafelkowKanwa.y -= 1;
                        mapaKafelkowKolizji.y -= 1;
                        gracz.polozenieNaTablicyY += 1;
                        handlarz.koordynujRuchy(e);
                        handlarz2.koordynujRuchy(e);

                    }
                }
                if (e.getKeyChar() == 'd') {
                    if (mapaKafelkowKolizji.tabPoziom1[gracz.polozenieNaTablicyY][gracz.polozenieNaTablicyX + 1] != 1) {
                        mapaKafelkowCalych.x -= 1;
                        mapaKafelkowKanwa.x -= 1;
                        mapaKafelkowKolizji.x -= 1;
                        gracz.polozenieNaTablicyX += 1;
                        handlarz.koordynujRuchy(e);
                        handlarz2.koordynujRuchy(e);

                    }
                }
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
