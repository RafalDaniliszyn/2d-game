package pl.rafal;

import javax.imageio.ImageIO;
import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Potwor implements Runnable{


        MapaKafelkowCalych mapaKafelkowCalych;
        BufferedImage skin;
        String name;
        boolean gora = false;
        boolean dol = false;
        boolean lewo = false;
        boolean prawo = false;

        int x;
        int y;

        int liczbaPotworow;

        Connection c;
        Statement statement;
        String sql;
        ResultSet result;

        Potwor(MapaKafelkowCalych mapaKafelkowCalych){

            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                c = DriverManager.getConnection("jdbc:postgresql://85.202.111.227:5432/", "postgres", "rafal123");
                this.statement =  c.createStatement();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            this.sql = "SELECT * FROM pajaki";
            try {
                this.result =  statement.executeQuery(sql);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


            this.mapaKafelkowCalych = mapaKafelkowCalych;
            try {
                skin = ImageIO.read(new File("C:\\Users\\Rafal\\Gra\\src\\pl\\rafal\\skiny\\handlarzDol.png"));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

       void pobierajDane(int i){
           try {
               this.result = statement.executeQuery("SELECT x FROM pajaki WHERE id='"+ i +"'");
               result.next();
               this.x = result.getInt("x");
               this.result = statement.executeQuery("SELECT y FROM pajaki WHERE id='"+ i +"'");
               result.next();
               this.y = result.getInt("y");

           } catch (SQLException throwables) {
               throwables.printStackTrace();
               System.out.println("nie moze pobrac polozenia");
           }
       }

       void sprawdzLiczbePotworow(){
           try {
               result =  statement.executeQuery("SELECT count(*) FROM pajaki");
               result.next();
               this.liczbaPotworow = result.getInt(1);
           } catch (SQLException throwables) {
               throwables.printStackTrace();
           }
       }


        void rysujWszystkie(Graphics g){
            for (int i = 0; i < this.liczbaPotworow; i++) {
                pobierajDane(i);
                g.drawImage(skin,(this.mapaKafelkowCalych.x + x) * 60, (this.mapaKafelkowCalych.y + y) * 60, null);
            }

        }



    @Override
    public void run() {
        while (true){
            sprawdzLiczbePotworow();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


