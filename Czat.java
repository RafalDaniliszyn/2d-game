package pl.rafal;

import org.json.JSONObject;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.*;

public class Czat implements KeyListener, Runnable {
    TextField textField;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Statement statement;
    Frame frame;
    Logowanie logowanie;
    GraczeOnline graczeOnline;
    MapaKafelkowCalych mapaKafelkowCalych;

    ServerConnection serverConnection;

    Gracz gracz;
    Czat(Frame frame, Logowanie logowanie,GraczeOnline graczeOnline, MapaKafelkowCalych mapaKafelkowCalych, Gracz gracz){
        this.serverConnection = new ServerConnection();
        this.gracz = gracz;
        this.mapaKafelkowCalych = mapaKafelkowCalych;
        this.graczeOnline = graczeOnline;
        this.logowanie = logowanie;
        this.frame = frame;
        textField = new TextField("");

        textField.setBounds(0,screenSize.height-150,screenSize.width,150);

        textField.addKeyListener(this);
    }

    void wyswietlWiadomosc(Graphics g, JSONObject jsonObject) throws SQLException {
        if (jsonObject.getBoolean("status") == true) {
            g.drawString(jsonObject.getString("wiadomosc"), (this.mapaKafelkowCalych.x + jsonObject.getInt("x")) * 60, (this.mapaKafelkowCalych.y + jsonObject.getInt("y")) * 60);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER){

            textField.setFocusable(false);
            this.frame.setFocusable(true);
            this.gracz.wiadomosc = textField.getText();
            textField.setText("");
            try {
                serverConnection.wyslijWiadomosc(gracz);
            } catch (IOException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void run() {
       while (true){
           if (textField.isCursorSet()){
               textField.setFocusable(true);
           }
           try {
                Thread.sleep(100);
           } catch (InterruptedException e) {
                e.printStackTrace();
           }
        }

    }
}
